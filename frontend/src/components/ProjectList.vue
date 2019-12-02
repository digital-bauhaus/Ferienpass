<template>
  <table
    v-if="projects && projects.length"
    id="projectTable"
  >
    <tr>
      <th
        class="clickable"
        @click="sortTable(0)"
      >
        Veranstaltung
      </th>
      <th
        class="clickable"
        @click="sortDate()"
      >
        Datum
      </th>
      <th>Plätze frei / gesamt / [reserviert]</th>
      <th>Bearbeiten</th>
    </tr>
    <tr
      v-for="(project, index) of projects"
      :key="project.id"
    >
      <td>{{ project.name }}</td>
      <td>{{ project.datum }}</td>
      <td>{{ project.slotsFrei }} / {{ project.slotsGesamt }} / [{{ project.slotsReserviert }}]</td>
      <td class="nobr">
        <router-link
          :to="{path: '../VeranstaltungEdit', query: {id: project.id }}"
          class="fakebutton"
        >
          Bearbeiten
        </router-link>
        <span
          class="fakebutton"
          @click="exportPDF(index)"
        ><a>PDF exportieren</a></span>
        <span
          class="fakebutton"
          @click="deleteProject(project.id)"
        >Löschen</span>
      </td>
    </tr>
  </table>
</template>

<script>
import jsPDF from 'jspdf';
import api from '../modules/ferienpass-api';

export default {
  name: 'ProjectList',
  props: {
    projects: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      errors: [],
    };
  },
  methods: {
    deleteProject(projectId) {
      this.$swal({
        title: 'Wirklich löschen?',
        text: 'Das Projekt wird vollständig gelöscht!',
        icon: 'warning',
        buttons: true,
        dangerMode: true,
      })
        .then((willDelete) => {
          if (willDelete) {
            this.errors = [];
            api.deleteProject(projectId).then(() => {
              this.$emit('project-deleted');
              return this.$swal('Projekt wurde gelöscht!', {
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
    exportPDF(projectID) {
      /*eslint-disable */
      var doc = new jsPDF()
      /* eslint-enable */
      /* var ta = document.getElementById(projectID) */
      let y = 10;
      const deltaLine = 10;
      doc.text('Projektdaten', 20, y += deltaLine);
      doc.text(`Name: ${this.allprojects[projectID].name}`, 20, y += deltaLine);
      doc.text(`Veranstaltungsdatum: ${this.allprojects[projectID].datum}`, 20, y += deltaLine);
      doc.text(`Altersbeschränkung: ${this.allprojects[projectID].mindestAlter}`, 20,
        y += deltaLine);
      doc.text(`Regulärer Preis: ${this.allprojects[projectID].kosten}`, 20, y += deltaLine);
      doc.text(`Freie Plätze: ${this.allprojects[projectID].slotsFrei}`, 20, y += deltaLine);
      doc.text(`Belegte Plätze: ${this.allprojects[projectID].slotsReserviert}`, 20,
        y += deltaLine);
      doc.text(`Plätze gesamt: ${this.allprojects[projectID].slotsGesamt}`, 20, y += deltaLine);
      doc.text(`Web Link: ${this.allprojects[projectID].webLink}`, 20, y += deltaLine);
      doc.text(`Projekt aktiv: ${this.allprojects[projectID].aktiv}`, 20, y += deltaLine);

      api.getAllUsersAssignedToProject(this.allprojects[projectID].id).then(
        (users) => { this.teilnehmerOfProject = users; },
      );

      for (let index = 0; index < this.teilnehmerOfProject.length; index += 1) {
        doc.text(`Angemeldete Person: ${this.teilnehmerOfProject[index].name}`, 20,
          y += deltaLine);
      }
      doc.save(`projekt_${projectID}.pdf`);
    },
    sortTable(n) {
      let rows; let i; let x; let y; let shouldSwitch;
      let switchcount = 0;
      const table = document.getElementById('projectTable');
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
      const table = document.getElementById('projectTable');
      let switching = true;
      let dir = 'asc';
      while (switching) {
        switching = false;
        rows = table.getElementsByTagName('TR');

        for (i = 1; i < (rows.length - 1); i += 1) {
          shouldSwitch = false;

          const tmpx = rows[i].getElementsByTagName('TD')[1].innerHTML;
          x = tmpx.toString();
          const patternx = /(\d{2})\.(\d{2})\.(\d{4})/;
          const dx = new Date(x.replace(patternx, '$3-$2-$1'));

          const tmpy = rows[i + 1].getElementsByTagName('TD')[1].innerHTML;
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

</style>
