<template>
  <div>
    <NavigationMenu/>
    <main>
      <h1>{{ titleText }}</h1>

      <ErrorListBox v-if="errors.length" :errors="errors" :heading-text="errorHeadingText" class="error-list-box"/>

      <form method="post" v-on:submit.prevent="createOrUpdateProject">
        <table>
          <tr>
            <td><label for="label">Veranstaltungsname: </label></td>
            <td><input type="text" id="label"
                       placeholder="Veranstaltungsname"
                       v-model="project.name"></td>
          </tr>
          <tr>
            <td><label for="date">Beginndatum: </label></td>
            <td><input type="text" id="date"
                       placeholder="Datum (TT.MM.JJJ)"
                       v-model="project.datum"></td>
          </tr>
          <tr>
            <td><label for="endDate">Enddatum: </label></td>
            <td><input type="text" id="endDate"
                       placeholder="Datum (TT.MM.JJJ)"
                       v-model="project.datumEnde"></td>
          </tr>
          <tr>
            <td><label for="num">Plätze (gesamt): </label></td>
            <td><input type="text" id="num" placeholder="Plätze"
                       v-model="project.slotsGesamt"></td>
          </tr>
          <tr>
            <td><label for="reserve">Plätze (reserviert): </label></td>
            <td><input type="text" id="reserve"
                       placeholder="Reservierte Plätze"
                       v-model="project.slotsReserviert"></td>
          </tr>
          <tr>
            <td><label for="minAge">Mindestalter: </label></td>
            <td><input type="text" id="minAge"
                       placeholder="Altersbegrenzung"
                       v-model="project.mindestAlter"></td>
          </tr>
          <tr>
            <td><label for="maxAge">Höchstalter: </label></td>
            <td><input type="text" id="maxAge"
                       placeholder="Altersbegrenzung"
                       v-model="project.hoechstAlter"></td>
          </tr>
          <tr>
            <td><label for="price">Preis: </label></td>
            <td><input type="text" id="price" placeholder="Preis"
                       v-model="project.kosten"></td>
          </tr>
          <tr>
            <td><label for="weblink">Weblink: </label></td>
            <td><input type="text" id="weblink" placeholder="Weblink"
                       v-model="project.webLink"></td>
          </tr>
          <tr>
            <td><label for="sponsor">Träger: </label></td>
            <td><input type="text" id="sponsor" placeholder="Träger"
                       v-model="project.traeger"></td>
          </tr>
          <tr>
            <td></td>
            <td><input type="submit" :value="submitButtonText"></td>
          </tr>
        </table>
      </form>
    </main>
    <div :class="popupClass">✔ Erfolgreich bearbeitet!</div>
  </div>
</template>

<script>
import {getProject, createProject, updateProject} from "../modules/ferienpass-api";
import ErrorListBox from "../components/ErrorListBox";
import NavigationMenu from "../components/NavigationMenu";

export default {
  name: 'Veranstaltung',
  components: {NavigationMenu, ErrorListBox},
  data() {
    return {
      id: parseInt(this.$route.query.id),
      project: {
        aktiv: true,
        anmeldungen: []
      },
      errors: [],
      updateSuccess: false,
      popupClass: 'fadeOut'
    };
  },
  computed: {
    titleText() {
      if (this.id < 0) {
        return "Veranstaltung anlegen"
      } else {
        return "Veranstaltungsbearbeitung"
      }
    },
    submitButtonText() {
      if (this.id < 0) {
        return "Anlegen"
      } else {
        return "Speichern"
      }
    },
    errorHeadingText() {
      if (this.id < 0) {
        return "Anlegen nicht möglich. Bitte beheben Sie folgende Fehler:"
      } else {
        return "Speichern nicht möglich. Bitte beheben Sie folgende Fehler:"
      }
    }
  },
  created() {
    if (this.id !== -1) {
      getProject(this.id).then(project => {
        this.project = project;
      })
    }
  },
  methods: {
    createOrUpdateProject() {
      this.errors = [];
      if (this.id < 0) {
        createProject(this.project).then(response => {
          this.fadeInAndOutAfterTimeout()
        }).catch(errorMessages => this.errors = errorMessages);
      } else {
        updateProject(this.project).then(response => {
          this.fadeInAndOutAfterTimeout()
        }).catch(errorMessages => this.errors = errorMessages);
      }
    },
    fadeInAndOutAfterTimeout() {
      this.popupClass = 'fadeIn';
      var self = this;
      setTimeout(function () {
        self.popupClass = 'fadeOut';
      }, 2000);
    },
    kill(event) {
      event.target.parentElement.parentElement.remove();
    }
  }
}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .error-list-box {
    margin: 20px;
  }
</style>

