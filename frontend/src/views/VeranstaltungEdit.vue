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
                v-model="displayDatum"
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
                v-model="displayDatumEnde"
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
          :allow-delete="false"
        />
      </div>

      <div v-if="!isNewProject">
        <h2>Stornierte Nutzer:</h2>
        <UserList
          :users="project.stornierteTeilnehmer"
          :show-projects="false"
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
import * as moment from 'moment';
import api from '@/modules/ferienpass-api';
import ErrorListBox from '@/components/ErrorListBox.vue';
import NavigationMenu from '@/components/NavBar.vue';
import UserList from '@/components/UserList.vue';

export default {
  name: 'Veranstaltung',
  components: { UserList, NavigationMenu, ErrorListBox },
  data() {
    return {
      errorMessages: [],
      displayDatum: '',
      displayDatumEnde: '',
      id: parseInt(this.$route.query.id, 10),
      project: null,
      popupClass: 'fadeOut',
    };
  },
  computed: {
    isNewProject() {
      return this.id < 0;
    },
    titleText() {
      if (this.isNewProject) {
        return 'Veranstaltung anlegen';
      }
      return 'Veranstaltungsbearbeitung';
    },
    submitButtonText() {
      if (this.isNewProject) {
        return 'Anlegen';
      }
      return 'Speichern';
    },
    errorHeadingText() {
      if (this.isNewProject) {
        return 'Anlegen nicht möglich. Bitte beheben Sie folgende Fehler:';
      }
      return 'Speichern nicht möglich. Bitte beheben Sie folgende Fehler:';
    },
  },
  created() {
    if (this.isNewProject) {
      this.project = {
        aktiv: true,
        anmeldungen: [],
        stornierteTeilnehmer: [],
      };
    } else {
      this.loadProjectData();
    }
  },
  methods: {
    loadProjectData() {
      this.errorMessages = [];
      api.getProject(this.id).then((project) => {
        this.project = project;
        this.displayDatum = moment(this.project.datum).format('DD.MM.YYYY');
        this.displayDatumEnde = moment(this.project.datumEnde).format('DD.MM.YYYY');
      }).catch((e) => this.errorMessages.push(e.toString()));
    },
    createOrUpdateProject() {
      this.errorMessages = [];

      // parse dates
      const parsedDatum = moment(this.displayDatum, 'DD.MM.YYYY', true);
      if (!parsedDatum.isValid()) {
        this.errorMessages.push('Ungültiges Datumsformat für Beginndatum');
      }
      const parsedDatumEnde = moment(this.displayDatumEnde, 'DD.MM.YYYY', true);
      if (!parsedDatumEnde.isValid()) {
        this.errorMessages.push('Ungültiges Datumsformat für Enddatum');
      }

      if (parsedDatum.isValid() && parsedDatumEnde.isValid()) {
        this.project.datum = parsedDatum.toDate();
        this.project.datumEnde = parsedDatumEnde.toDate();

        if (this.isNewProject) {
          api.createProject(this.project).then(() => {
            this.fadeInAndOutAfterTimeout();
          }).catch((errorMessages) => { this.errorMessages = errorMessages; });
        } else {
          api.updateProject(this.project).then(() => {
            this.fadeInAndOutAfterTimeout();
          }).catch((errorMessages) => { this.errorMessages = errorMessages; });
        }
      }
    },
    fadeInAndOutAfterTimeout() {
      this.popupClass = 'fadeIn';
      const self = this;
      setTimeout(() => {
        self.popupClass = 'fadeOut';
      }, 2000);
    },
  },
};
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

main {
  width: 80%;
  position: absolute;
  right: 0px;
  height: 100%;
}

main form {
  margin: auto;
  top: 40px;
  left: 0;
  bottom: 0;
  right: 0;
  width: 90%;
}

main form .caption {
  font-size: 30px;
  font-weight: bold;
}

main form input[type=text] {
  font-size: 16px;
  width: 200;
  height: 30px;
  margin-bottom: 0px;
  display: block;
  background-color: #FAEBD7;
  border-radius: 5px;
  -moz-border-radius: 5px;
  -webkit-border-radius: 5px;
}


main form label {
  margin: 5px;
  float: left;
  display: block;
}

main h2 {
  margin: 20px;
}

main h1 {
  margin: 20px;
}

input[type=submit] {
  margin: 20px;
  background-color: #84845e;
  border: none;
  color: white;
  padding: 10px 24px;
  border-radius: 8px;
  text-align: center;
  text-decoration: none;
  font-size: 16px;
}

.error-list-box {
  margin: 20px;
}

th, td {
  min-width: 150px;
  padding: 10px 20px;
}

.fadeIn {
  text-align: center;
  vertical-align: middle;
  line-height: 90px;
  color: white;
  display: block;
  background-color: #6bbc6b;
  position: fixed;
  bottom: 20px;
  right: -10px;
  overflow-x: hidden;
  transition: .5s;
  width: 200px;
  height: 100px;
  border-radius: 10px;
}

.fadeOut {
  text-align: center;
  vertical-align: middle;
  line-height: 90px;
  color: #6bbc6b;
  display: box;
  background-color: #6bbc6b;
  position: fixed;
  bottom: 20px;
  right: -10px;
  overflow-x: hidden;
  transition: .5s;
  width: 0px;
  height: 100px;
}

</style>
