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
import api from '../modules/ferienpass-api';
import jsPDF from 'jspdf'

export default {
  name: "ProjectList",
  props: {
    projects: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      errors: []
    };
  },
  methods: {
    deleteProject(projectId) {
      this.$swal({
        title: "Wirklich löschen?",
        text: "Das Projekt wird vollständig gelöscht!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
      })
      .then((willDelete) => {
        if (willDelete) {
          this.errors = [];
          api.deleteProject(projectId).then(() => {
            this.$emit("project-deleted");
            return this.$swal("Projekt wurde gelöscht!", {
              icon: "success"
            });
          }).catch(e => {
            this.errors.push(e);
            return this.$swal("Da ist was schief gegangen :(", {
              icon: "error"
            });
          })
        }
      });
    },
    exportPDF(projectID) {
      /*eslint-disable */
      var doc = new jsPDF()
      /*eslint-enable */
      /* var ta = document.getElementById(projectID) */
      let y = 10
      let deltaLine = 10
      doc.text('Projektdaten', 20, y += deltaLine)
      doc.text('Name: ' + this.allprojects[projectID].name, 20, y += deltaLine)
      doc.text('Veranstaltungsdatum: ' + this.allprojects[projectID].datum, 20, y += deltaLine)
      doc.text('Altersbeschränkung: ' + this.allprojects[projectID].mindestAlter, 20,
          y += deltaLine)
      doc.text('Regulärer Preis: ' + this.allprojects[projectID].kosten, 20, y += deltaLine)
      doc.text('Freie Plätze: ' + this.allprojects[projectID].slotsFrei, 20, y += deltaLine)
      doc.text('Belegte Plätze: ' + this.allprojects[projectID].slotsReserviert, 20,
          y += deltaLine)
      doc.text('Plätze gesamt: ' + this.allprojects[projectID].slotsGesamt, 20, y += deltaLine)
      doc.text('Web Link: ' + this.allprojects[projectID].webLink, 20, y += deltaLine)
      doc.text('Projekt aktiv: ' + this.allprojects[projectID].aktiv, 20, y += deltaLine)

      api.getAllUsersAssignedToProject(this.allprojects[projectID].id).then(
          users => this.teilnehmerOfProject = users)

      for (let index = 0; index < this.teilnehmerOfProject.length; ++index) {
        doc.text('Angemeldete Person: ' + this.teilnehmerOfProject[index].name, 20,
            y += deltaLine)
      }
      doc.save('projekt_' + projectID + '.pdf')
    },
    sortTable(n) {
      var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount;
      switchcount = 0;
      table = document.getElementById('projectTable');
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
    sortDate() {
      var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount;
      switchcount = 0;
      table = document.getElementById('projectTable');
      switching = true;
      dir = 'asc';
      while (switching) {
        switching = false;
        rows = table.getElementsByTagName('TR');

        for (i = 1; i < (rows.length - 1); i++) {
          shouldSwitch = false;

          var tmpx = rows[i].getElementsByTagName('TD')[1].innerHTML;
          x = tmpx.toString();
          var patternx = /(\d{2})\.(\d{2})\.(\d{4})/;
          var dx = new Date(x.replace(patternx, '$3-$2-$1'));

          var tmpy = rows[i + 1].getElementsByTagName('TD')[1].innerHTML;
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

</style>
