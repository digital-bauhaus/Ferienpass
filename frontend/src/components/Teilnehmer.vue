<template>
  <html>
    <nav>
      <input type="text" class="searchbar" placeholder="Suche ...">
      <a href="/#/Veranstaltungen/" >Alle Veranstaltungen</a>
      <a href="/#/VeranstaltungEdit?id=-1" >Veranstaltung erstellen </a>
      <a href="/#/Teilnehmer/"  class="selected">Alle Teilnehmer</a>
    </nav>
    <main>
    <h1>Teilnehmerübersicht</h1>

       <table v-if="allusers && allusers.length" id="myTable">
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
       <tr v-for="user of allusers">
         <td :id="user.bezahlt"><span :id="user.bezahlt">{{user.bezahlt}}</span></td>
         <td>{{user.nachname}}, {{user.vorname}}</td>
         <td><div v-html="getProjectName(user.id)"></div></td>
         <td>{{user.registrierungsdatum}}</td>
         <td>{{user.strasse}}, {{user.stadt}}</td>
         <td>{{user.telefon}}</td>
         <td>{{user.email}}</td>
         <td>{{user.geburtsdatum}}</td>
         <td>
           <router-link :to="{path: '../TeilnehmerEdit', query: {id: user.id }}" class="fakebutton">Bearbeiten</router-link>
           <span class="fakebutton"><a>PDF</a></span>
           <span class="fakebutton" v-on:click="deleteUser(user.id)">Teilnehmer löschen</span>
         </td>
       </tr>
     </table>
    </main>
    <!-- The Modal -->
    <div id="delete" class="modal">

      <!-- Modal content -->
      <div class="modal-content">
        <h4>Sind sie sicher das Sie den Teilnehmer stornieren wollen?</h4>
        <div class="center"><button>Bestätigen</button><button v-on:click="closeModal()">Abbrechen</button></div>
      </div>
    </div>

  </html>
</template>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>

import { AXIOS } from './http-common';

export default {
  name: 'Teilnehmer',
  data () {
    return {
      allusers: [],
      errors: [],
      projectsOfUser: [],
      allAvailableProjects: []
    };
  },
  created () {
    AXIOS.get('/allusers')
    .then(response => {
      this.allusers = response.data
    })
    .catch(e => {
      this.errors.push(e)
    })
    AXIOS.get('/allprojects')
    .then(response => {
      this.allAvailableProjects = response.data
    })
    .catch(e => {
      this.errors.push(e)
    })
  },
  methods: {
    deleteUser(userId) {

      swal({
        title: "Wirklich löschen?",
        text: "Der Teilnehmer wird vollständig gelöscht und die Daten sind verloren! Er muss sich über die Anmeldung wieder NEU anmelden!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
      })
      .then((willDelete) => {
        if (willDelete) {
          AXIOS.delete('/user/' + userId)
            .then(response => {
              this.retrieveAllUsersFromBackend()
              swal("Teilnehmer wurde gelöscht!", {
                icon: "success",
              });
            })
            .catch(e => {
              this.errors.push(e)
              swal("Da ist was schief gegangen :(");
            })
        } else {
          //swal("Keine Bange, der Teilnehmer wurde NICHT gelöscht :)");
        }
      });
    },
    retrieveAllUsersFromBackend() {
      AXIOS.get('/allusers')
              .then(response => {
                this.allusers = response.data
              })
              .catch(e => {
                this.errors.push(e)
              })
    },
    sortTable (n) {
      var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount;
      switchcount = 0;
      table = document.getElementById('myTable');
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
      table = document.getElementById('myTable');
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
    },
    kill (event) {
      var modal = document.getElementById('delete');
      modal.style.display = 'block';
    /* event.target.parentElement.parentElement.parentElement.remove(); */
    },

    closeModal () {
      var modal = document.getElementById('delete');
      modal.style.display = 'none';
    },
    computeAge (dateOfBirth, projectDate) {
      var projectDateFormat = new Date(dateOfBirth)
      var dateOfBirthFormat = new Date(projectDate)
      var diff = projectDateFormat - dateOfBirthFormat
      return diff
    },
    getProjectName (userId) {
      var result = ''
      this.allAvailableProjects.forEach(function (project) {
        project.anmeldungen.forEach(function (teilnehmer) {
          if (userId === teilnehmer.id) {
            result += project.name + ' '
          }
        })
      })
      return result
    },
    getProjectNames (projectList) {
      var result = ''
      projectList.forEach(function (project) {
        result += '<div v-for=project in projectList>' + project.name + '</div>'
      })
      return result
    }
  }
}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

td:nth-child(1) {
    background-color: #fce553;
    color: #fce553;
    cursor: default;
}
#true {
    background-color: #8eef8b;
    color: #8eef8b;
    cursor: default;
}


</style>
