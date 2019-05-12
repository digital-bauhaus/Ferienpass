<template>
  <table v-if="users && users.length" id="userTable">
    <tr>
      <th v-on:click="sortTable(0)" class="clickable">Status</th>
      <th v-on:click="sortTable(1)" class="clickable">Name</th>
      <th>Projekte</th>
      <th v-on:click="sortDate()" class="clickable">Buchung</th>
      <th>Addresse</th>
      <th>Telefon</th>
      <th>eMail</th>
      <th>Geburtsdatum</th>
      <th>Bearbeiten</th>
    </tr>
    <tr v-for="user of users">
      <td :id="user.bezahlt"><span :id="user.bezahlt">{{user.bezahlt}}</span></td>
      <td>{{user.nachname}}, {{user.vorname}}</td>
      <td><div v-html="getProjectName(user.id)"></div></td>
      <td>{{user.registrierungsdatum}}</td>
      <td>{{user.strasse}}, {{user.stadt}}</td>
      <td>{{user.telefon}}</td>
      <td>{{user.email}}</td>
      <td>{{user.geburtsdatum}}</td>
      <td class="nobr">
        <router-link :to="{path: '../TeilnehmerEdit', query: {id: user.id }}" class="fakebutton">Bearbeiten</router-link>
        <span class="fakebutton"><a>PDF</a></span>
        <span class="fakebutton" v-on:click="deleteUser(user.id)">Teilnehmer löschen</span>
      </td>
    </tr>
  </table>
</template>

<script>
import { getProjects, deleteUser } from '../modules/ferienpass-api';

export default {
  name: "UserList",
  data() {
    return {
      errors: [],
      projectsOfUser: [],
      allAvailableProjects: []
    };
  },
  props: {
    users: {
      type: Array,
      required: true
    }
  },
  created() {
    getProjects().then(projects => this.allAvailableProjects = projects)
  },
  methods: {
    deleteUser(userId) {
      this.$swal({
        title: "Wirklich löschen?",
        text: "Der Teilnehmer wird vollständig gelöscht und die Daten sind verloren! Er muss sich über die Anmeldung wieder NEU anmelden!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
      })
      .then((willDelete) => {
        if (willDelete) {
          this.errors = [];
          deleteUser(userId).then(response => {
            this.$emit("user-deleted");
            return this.$swal("Teilnehmer wurde gelöscht!", {
              icon: "success",
            });
          }).catch(e => {
            this.errors.push(e);
            return this.$swal("Da ist was schief gegangen :(", {
              icon: "error",
            });
          })
        }
      });
    },
    getProjectName (userId) {
      var result = '';
      this.allAvailableProjects.forEach(function (project) {
        project.anmeldungen.forEach(function (teilnehmer) {
          if (userId === teilnehmer.id) {
            result += project.name + ' '
          }
        })
      });
      return result
    },
    getProjectNames (projectList) {
      var result = ''
      projectList.forEach(function (project) {
        result += '<div v-for=project in projectList>' + project.name + '</div>'
      });
      return result
    },
    sortTable (n) {
      var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount;
      switchcount = 0;
      table = document.getElementById('userTable');
      switching = true;
      dir = 'asc';
      while (switching) {
        switching = false;
        rows = table.getElementsByTagName('TR');

        for (i = 1; i < (rows.length - 1); i++) {
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
          switchcount++;
        } else {
          if (switchcount === 0 && dir === 'asc') {
            dir = 'desc';
            switching = true;
          }
        }
      }
    },
    sortDate () {
      var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount;
      switchcount = 0;
      table = document.getElementById('userTable');
      switching = true;
      dir = 'asc';
      while (switching) {
        switching = false;
        rows = table.getElementsByTagName('TR');

        for (i = 1; i < (rows.length - 1); i++) {
          shouldSwitch = false;

          var tmpx = rows[i].getElementsByTagName('TD')[2].innerHTML;
          x = tmpx.toString();
          var patternx = /(\d{2})\.(\d{2})\.(\d{4})/;
          var dx = new Date(x.replace(patternx, '$3-$2-$1'));

          var tmpy = rows[i + 1].getElementsByTagName('TD')[2].innerHTML;
          y = tmpy.toString();
          var patterny = /(\d{2})\.(\d{2})\.(\d{4})/;
          var dy = new Date(y.replace(patterny, '$3-$2-$1'));

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
          switchcount++;
        } else {
          if (switchcount === 0 && dir === 'asc') {
            dir = 'desc';
            switching = true;
          }
        }
      }
    }
  }
}
</script>

<style scoped>

td:nth-child(1) {
  background-color: #fce553;
  color: #fce553;
  cursor: default;
}

</style>