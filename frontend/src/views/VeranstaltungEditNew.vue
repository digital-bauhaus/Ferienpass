<template>
  <div>
    <NavigationMenu />
    <b-container>
      <h1>
        {{ titleText }}
      </h1>
      <ErrorAlert
        v-if="showServerErrorAlert"
        :heading-text="serverErrorHeadingText"
        :errors="serverErrorMessages"
      />
      <Veranstaltung
        v-model="veranstaltung"
        :submit-button-text="submitButtonText"
        @submit="createOrUpdateProject"
      />
      <div v-if="!isNewProject">
        <h2>Angemeldete Nutzer:</h2>
        <UserList
          :users="veranstaltung.anmeldungen"
          :show-projects="false"
          :allow-delete="false"
        />
      </div>

      <div v-if="!isNewProject">
        <h2>Stornierte Nutzer:</h2>
        <UserList
          :users="veranstaltung.stornierteTeilnehmer"
          :show-projects="false"
          :allow-delete="false"
        />
      </div>

      <b-alert
        class="fixed-bottom w-50 mx-auto"
        :show="successAutomaticDismissCountDown"
        dismissible
        variant="success"
        @dismissed="successAutomaticDismissCountDown=0"
        @dismiss-count-down="successAutomaticDismissCountDown = $event"
      >
        {{ successText }}
      </b-alert>
    </b-container>
  </div>
</template>

<script>
import NavigationMenu from '@/components/NavBar.vue';
import Veranstaltung from '@/components/VeranstaltungEditor.vue';
import api from '@/modules/ferienpass-api';
import ErrorAlert from '@/components/ErrorAlert.vue';
import UserList from '@/components/UserList.vue';

export default {
  name: 'VeranstaltungEditNew',
  components: {
    Veranstaltung, NavigationMenu, ErrorAlert, UserList,
  },
  data() {
    return {
      veranstaltung: {
        /* TODO these have to be default values on the server model */
        aktiv: true,
        anmeldungen: [],
        stornierteTeilnehmer: [],
      },
      serverErrorMessages: [],
      successAutomaticDismissCountDown: 0,
    };
  },
  computed: {
    veranstaltungId() {
      return parseInt(this.$route.query.id, 10);
    },
    isNewProject() {
      return this.veranstaltungId <= 0;
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
    successText() {
      if (this.isNewProject) {
        return 'Veranstaltung erfolgreich angelegt.';
      }
      return 'Veranstaltung erfolgreich gespeichert.';
    },
    serverErrorHeadingText() {
      if (this.isNewProject) {
        return 'Anlegen nicht möglich. Bitte beheben Sie folgende Fehler:';
      }
      return 'Speichern nicht möglich. Bitte beheben Sie folgende Fehler:';
    },
    showServerErrorAlert() {
      return this.serverErrorMessages.length > 0;
    },
  },
  created() {
    if (!this.isNewProject) {
      this.loadProjectData();
    }
  },
  methods: {
    loadProjectData() {
      this.serverErrorMessages = [];
      api.getProject(this.veranstaltungId).then((project) => {
        this.veranstaltung = project;
      }).catch((e) => this.serverErrorMessages.push(e.toString()));
    },
    createOrUpdateProject() {
      this.serverErrorMessages = [];
      if (this.isNewProject) {
        api.createProject(this.veranstaltung).then(() => {
          this.successAutomaticDismissCountDown = 5;
        }).catch((errorMessages) => { this.serverErrorMessages = errorMessages; });
      } else {
        api.updateProject(this.veranstaltung).then(() => {
          this.successAutomaticDismissCountDown = 5;
        }).catch((errorMessages) => { this.serverErrorMessages = errorMessages; });
      }
    },
  },
};
</script>

<style scoped>
</style>
