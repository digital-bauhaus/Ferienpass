<template>
  <div>
    <NavigationMenu />
    <main v-if="project">
      <h1>{{ titleText }}</h1>

      <form
        method="post"
        @submit.prevent="createOrUpdateProject"
      >
        <table>
          <tr>
            <td><label for="label">Veranstaltungsname: </label></td>
            <td>
              <input
                id="label"
                v-model="project.name"
                type="text"
                placeholder="Veranstaltungsname"
              >
            </td>
          </tr>
          <tr>
            <td><label for="date">Beginndatum: </label></td>
            <td>
              <input
                id="date"
                v-model="project.datum"
                type="text"
                placeholder="Datum (TT.MM.JJJ)"
              >
            </td>
          </tr>
          <tr>
            <td><label for="endDate">Enddatum: </label></td>
            <td>
              <input
                id="endDate"
                v-model="project.datumEnde"
                type="text"
                placeholder="Datum (TT.MM.JJJ)"
              >
            </td>
          </tr>
          <tr>
            <td><label for="num">Plätze (gesamt): </label></td>
            <td>
              <input
                id="num"
                v-model="project.slotsGesamt"
                type="text"
                placeholder="Plätze"
              >
            </td>
          </tr>
          <tr>
            <td><label for="reserve">Plätze (reserviert): </label></td>
            <td>
              <input
                id="reserve"
                v-model="project.slotsReserviert"
                type="text"
                placeholder="Reservierte Plätze"
              >
            </td>
          </tr>
          <tr>
            <td><label for="minAge">Mindestalter: </label></td>
            <td>
              <input
                id="minAge"
                v-model="project.mindestAlter"
                type="text"
                placeholder="Altersbegrenzung"
              >
            </td>
          </tr>
          <tr>
            <td><label for="maxAge">Höchstalter: </label></td>
            <td>
              <input
                id="maxAge"
                v-model="project.hoechstAlter"
                type="text"
                placeholder="Altersbegrenzung"
              >
            </td>
          </tr>
          <tr>
            <td><label for="price">Preis: </label></td>
            <td>
              <input
                id="price"
                v-model="project.kosten"
                type="text"
                placeholder="Preis"
              >
            </td>
          </tr>
          <tr>
            <td><label for="weblink">Weblink: </label></td>
            <td>
              <input
                id="weblink"
                v-model="project.webLink"
                type="text"
                placeholder="Weblink"
              >
            </td>
          </tr>
          <tr>
            <td><label for="sponsor">Träger: </label></td>
            <td>
              <input
                id="sponsor"
                v-model="project.traeger"
                type="text"
                placeholder="Träger"
              >
            </td>
          </tr>
          <tr>
            <td />
            <td>
              <input
                type="submit"
                :value="submitButtonText"
              >
            </td>
          </tr>
        </table>
      </form>
      <ErrorListBox
        v-if="errorMessages.length"
        :errors="errorMessages"
        :heading-text="errorHeadingText"
        class="error-list-box"
      />

      <div v-if="!isNewProject">
        <h2>Angemeldete Nutzer:</h2>
        <UserList
          :users="project.anmeldungen"
          :show-projects="false"
          :allow-export-pdf="false"
          :allow-delete="false"
        />
      </div>

      <div v-if="!isNewProject">
        <h2>Stornierte Nutzer:</h2>
        <UserList
          :users="project.stornierteTeilnehmer"
          :show-projects="false"
          :allow-export-pdf="false"
          :allow-delete="false"
        />
      </div>
    </main>
    <div :class="popupClass">
      ✔ Erfolgreich bearbeitet!
    </div>
  </div>
</template>

<script>
import api from "../modules/ferienpass-api";
import ErrorListBox from "../components/ErrorListBox";
import NavigationMenu from "../components/NavigationMenu";
import UserList from "../components/UserList";

export default {
  name: 'Veranstaltung',
  components: {UserList, NavigationMenu, ErrorListBox},
  data() {
    return {
      errorMessages: [],
      id: parseInt(this.$route.query.id),
      project: null,
      popupClass: 'fadeOut'
    };
  },
  computed: {
    isNewProject() {
      return this.id < 0;
    },
    titleText() {
      if (this.isNewProject) {
        return "Veranstaltung anlegen"
      } else {
        return "Veranstaltungsbearbeitung"
      }
    },
    submitButtonText() {
      if (this.isNewProject) {
        return "Anlegen"
      } else {
        return "Speichern"
      }
    },
    errorHeadingText() {
      if (this.isNewProject) {
        return "Anlegen nicht möglich. Bitte beheben Sie folgende Fehler:"
      } else {
        return "Speichern nicht möglich. Bitte beheben Sie folgende Fehler:"
      }
    }
  },
  created() {
    if (this.isNewProject) {
      this.project = {
        aktiv: true,
        anmeldungen: [],
        stornierteTeilnehmer: []
      };
    } else {
      this.loadProjectData();
    }
  },
  methods: {
    loadProjectData() {
      this.errorMessages = [];
      api.getProject(this.id).then(project => {
        this.project = project;
      }).catch(e => this.errorMessages.push(e.toString()));
    },
    createOrUpdateProject() {
      this.errorMessages = [];
      if (this.isNewProject) {
        api.createProject(this.project).then(() => {
          this.fadeInAndOutAfterTimeout()
        }).catch(errorMessages => this.errorMessages = errorMessages);
      } else {
        api.updateProject(this.project).then(() => {
          this.fadeInAndOutAfterTimeout()
        }).catch(errorMessages => this.errorMessages = errorMessages);
      }
    },
    fadeInAndOutAfterTimeout() {
      this.popupClass = 'fadeIn';
      var self = this;
      setTimeout(function () {
        self.popupClass = 'fadeOut';
      }, 2000);
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

