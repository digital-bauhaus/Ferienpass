<template>
    <html>
    <nav>
      <a href="/#/Veranstaltungen/" >Alle Veranstaltungen</a>
      <a href="/#/VeranstaltungEdit?id=-1" class="selected" >Veranstaltung erstellen </a>
      <a href="/#/Teilnehmer/" >Alle Teilnehmer</a>
    </nav>
    <main>
    <h1 v-if="this.id<0">Veranstaltung anlegen</h1>
    <h1 v-else>Veranstaltungsbearbeitung</h1>

      <form method="post" v-on:submit.prevent="createOrUpdateProject">
          <table>
              <tr>
                  <td><label>Veranstaltungsname: </label></td>
                  <td><input type="text" id="label"
                             placeholder="Veranstaltungsname"
                             v-model="project.name"></td>
              </tr>
              <tr>
                  <td><label>Beginndatum: </label></td>
                  <td><input type="text" id="date"
                             placeholder="Datum (TT.MM.JJJ)"
                             v-model="project.datum"></td>
              </tr>
              <tr>
                  <td><label>Enddatum: </label></td>
                  <td><input type="text" id="endDate"
                             placeholder="Datum (TT.MM.JJJ)"
                             v-model="project.datumEnde"></td>
              </tr>
              <tr>
                  <td><label>Plätze (gesamt): </label></td>
                  <td><input type="text" id="num" placeholder="Plätze"
                             v-model="project.slotsGesamt"></td>
              </tr>
              <tr>
                  <td><label>Plätze (reserviert): </label></td>
                  <td><input type="text" id="reserve"
                             placeholder="Reservierte Plätze"
                             v-model="project.slotsReserviert"></td>
              </tr>
              <tr>
                  <td><label>Mindestalter: </label></td>
                  <td><input type="text" id="minAge"
                             placeholder="Altersbegrenzung"
                             v-model="project.mindestAlter"></td>
              </tr>
              <tr>
                  <td><label>Höchstalter: </label></td>
                  <td><input type="text" id="maxAge"
                             placeholder="Altersbegrenzung"
                             v-model="project.hoechstAlter"></td>
              </tr>
              <tr>
                  <td><label>Preis: </label></td>
                  <td><input type="text" id="price" placeholder="Preis"
                             v-model="project.kosten"></td>
              </tr>
              <tr>
                  <td><label>Weblink: </label></td>
                  <td><input type="text" id="weblink" placeholder="Weblink"
                             v-model="project.webLink"></td>
              </tr>
              <tr>
                  <td><label>Träger: </label></td>
                  <td><input type="text" id="sponsor" placeholder="Träger"
                             v-model="project.traeger"></td>
              </tr>
              <tr>
                  <td></td>
                  <td><input v-if="this.id<0" type="submit"
                             value="Eintragen"><input v-else type="submit"
                                                      value="Bearbeiten"></td>
              </tr>
          </table>
      </form>
    </main>
    <div :class="popupClass">✔ Erfolgreich bearbeitet!</div>
    </html>
</template>

<script>
import { AXIOS } from './http-common';

export default {
  name: 'Veranstaltung',
  data () {
    return {
      project: {
          aktiv: true,
          anmeldungen:[]
      },
      errors: [],
      updateSuccess: false,
      popupClass: 'fadeOut'
    };
  },
  created () {
    var id = parseInt(this.$route.query.id);
    this.id = id;
    if (id !== -1) {
      AXIOS.get('/project/' + id)
      .then(response => {
        this.project = response.data
      })
      .catch(e => {
        this.errors.push(e)
      })
    }
  },
  methods: {
    createOrUpdateProject () {
      var id = parseInt(this.$route.query.id);

      if (this.id < 0) {
          console.log("creating new project")

          AXIOS.post('/projekt', this.project)
              .then(response => {
                  this.popupClass = 'fadeIn';
                  var self = this;
                  setTimeout(function () {
                      self.popupClass = 'fadeOut';
                  }, 2000);
              })
              .catch(e => {
                  this.errors.push(e)
              })

      } else {
          console.log("updating existing project")
          AXIOS.put('/projekt', this.project)
              .then(response => {
                  this.popupClass = 'fadeIn'
                  var self = this;
                  setTimeout(function () {
                      self.popupClass = 'fadeOut';
                  }, 2000);
                  this.getProjectsOfUser()
              })
              .catch(e => {
                  this.errors.push(e)
              });
      }
    },
    kill (event) {
      event.target.parentElement.parentElement.remove();
    }
  }
}

</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

