<template>
  <table
    v-if="users && users.length"
    id="userTable"
  >
    <tr>
      <th
        class="clickable"
        @click="sortTable(0)"
      >
        Status
      </th>
      <th
        class="clickable"
        @click="sortTable(1)"
      >
        Name
      </th>
      <th v-if="showProjects">
        Projekte
      </th>
      <th
        class="clickable"
        @click="sortDate()"
      >
        Buchung
      </th>
      <th>Addresse</th>
      <th>Telefon</th>
      <th>eMail</th>
      <th>Geburtsdatum</th>
      <th>Bearbeiten</th>
    </tr>
    <tr
      v-for="user of users"
      :key="user.id"
    >
      <td :id="user.bezahlt">
        <span :id="user.bezahlt">{{ user.bezahlt }}</span>
      </td>
      <td>{{ user.nachname }}, {{ user.vorname }}</td>
      <td v-if="showProjects && projectsLoaded">
        <ul>
          <li
            v-for="projectName of projectNamesByUserId[user.id]"
            :key="projectName"
          >
            {{ projectName }}
          </li>
        </ul>
      </td>
      <td>{{ user.registrierungsdatum }}</td>
      <td>{{ user.strasse }}, {{ user.stadt }}</td>
      <td>{{ user.telefon }}</td>
      <td>{{ user.email }}</td>
      <td>{{ user.geburtsdatum }}</td>
      <td class="nobr">
        <router-link
          v-if="allowEdit"
          :to="{path: '../TeilnehmerEdit', query: {id: user.id }}"
          class="fakebutton"
        >
          Bearbeiten
        </router-link>
        <router-link
          v-if="allowEdit"
          :to="{path: '../UserEdit', query: {id: user.id }}"
          class="fakebutton"
        >
          Bearbeiten NEU
        </router-link>
        <span
          v-if="allowDelete"
          class="fakebutton"
          @click="deleteUser(user.id)"
        >Teilnehmer löschen</span>
      </td>
    </tr>
  </table>
</template>

<script>
import api from '../modules/ferienpass-api';

export default {
  name: 'UserList',
  props: {
    users: {
      type: Array,
      required: true,
    },
    showProjects: {
      type: Boolean,
      required: false,
      default: false,
    },
    allowEdit: {
      type: Boolean,
      required: false,
      default: true,
    },
    allowDelete: {
      type: Boolean,
      required: false,
      default: true,
    },
  },
  data() {
    return {
      errors: [],
      projectNamesByUserId: [],
      projectsLoaded: false,
    };
  },
  created() {
    if (this.showProjects) {
      this.loadProjectsOfUsers();
    }
  },
  methods: {
    loadProjectsOfUsers() {
      this.errors = [];
      // instead of one api-call per user,
      // we request ALL projects and build a lookup-table ourselves
      api.getProjects().then((projects) => {
        this.users.forEach((user) => {
          this.projectNamesByUserId[user.id] = this.findProjectNamesForUserId(projects, user.id);
        });
        this.projectsLoaded = true;
      }).catch((e) => this.errors.push(e));
    },
    findProjectNamesForUserId(projects, userId) {
      const projectNames = [];
      projects.forEach((project) => {
        if (project.anmeldungen.map((user) => user.id).includes(userId)) {
          projectNames.push(project.name);
        }
      });
      return projectNames;
    },
    deleteUser(userId) {
      this.$swal({
        title: 'Wirklich löschen?',
        text: 'Der Teilnehmer wird vollständig gelöscht und die Daten sind verloren! Er muss sich über die Anmeldung wieder NEU anmelden!',
        icon: 'warning',
        buttons: true,
        dangerMode: true,
      })
        .then((willDelete) => {
          if (willDelete) {
            this.errors = [];
            api.deleteUser(userId).then(() => {
              this.$emit('user-deleted');
              return this.$swal('Teilnehmer wurde gelöscht!', {
                icon: 'success',
              });
            }).catch((e) => {
              this.errors.push(e);
              return this.$swal('Da ist was schief gegangen :(', {
                icon: 'error',
              });
            });
          }
        });
    },
    sortTable(n) {
      let rows; let i; let x; let y; let shouldSwitch;
      let switchcount = 0;
      const table = document.getElementById('userTable');
      let switching = true;
      let dir = 'asc';
      while (switching) {
        switching = false;
        rows = table.getElementsByTagName('TR');

        for (i = 1; i < (rows.length - 1); i += 1) {
          shouldSwitch = false;
          x = rows[i].getElementsByTagName('TD')[n];
          y = rows[i + 1].getElementsByTagName('TD')[n];

          if (dir === 'asc') {
            if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
              shouldSwitch = true;
              break;
            }
          } else if (dir === 'desc') {
            if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
              shouldSwitch = true;
              break;
            }
          }
        }
        if (shouldSwitch) {
          rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
          switching = true;
          switchcount += 1;
        } else if (switchcount === 0 && dir === 'asc') {
          dir = 'desc';
          switching = true;
        }
      }
    },
    sortDate() {
      let rows; let i; let x; let y; let shouldSwitch;
      let switchcount = 0;
      const table = document.getElementById('userTable');
      let switching = true;
      let dir = 'asc';
      while (switching) {
        switching = false;
        rows = table.getElementsByTagName('TR');

        for (i = 1; i < (rows.length - 1); i += 1) {
          shouldSwitch = false;

          const tmpx = rows[i].getElementsByTagName('TD')[2].innerHTML;
          x = tmpx.toString();
          const patternx = /(\d{2})\.(\d{2})\.(\d{4})/;
          const dx = new Date(x.replace(patternx, '$3-$2-$1'));

          const tmpy = rows[i + 1].getElementsByTagName('TD')[2].innerHTML;
          y = tmpy.toString();
          const patterny = /(\d{2})\.(\d{2})\.(\d{4})/;
          const dy = new Date(y.replace(patterny, '$3-$2-$1'));

          if (dir === 'asc') {
            if (dx > dy) {
              shouldSwitch = true;
              break;
            }
          } else if (dir === 'desc') {
            if (dx < dy) {
              shouldSwitch = true;
              break;
            }
          }
        }
        if (shouldSwitch) {
          rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
          switching = true;
          switchcount += 1;
        } else if (switchcount === 0 && dir === 'asc') {
          dir = 'desc';
          switching = true;
        }
      }
    },
  },
};
</script>

<style scoped>

table {
  border-collapse: collapse;
  margin-top: 20px;
  margin-left: auto;
  margin-right: auto;
  width: 80%;
  border-radius: 15px;
  border: 0px;
}

th {
  background: #333435;
  color: white;
  font-weight: bold;
}

.clickable {
  cursor: pointer;
}

.clickable:after {
  font-weight: normal;
  position: relative;
  left: 5px;
  content: '▼';
}

td, th {
  min-width: 150px;
  padding: 6px;
  border: 1px solid #ccc;
  text-align: left;
  border: 0px;
}

tr:nth-child(even) {
  background: #eee;
}

.fakebutton {
  color: black;
  background: lightgrey;
  border-radius: 7px;
  padding: 4px;
  cursor: pointer;
}

.fakebutton:hover {
  text-decoration: none;
  background: #3a4372;
  color: white;
  transition: .10s;
}

ul {
  margin-bottom: 0;
}

td:nth-child(1) {
  background-color: #fce553;
  color: #fce553;
  cursor: default;
}

</style>
