<template>
  <BaseLayout class="projects-edit">
    <h1>
      {{ titleText }}
    </h1>
    <ErrorBox
      v-if="showServerErrorAlert"
      :heading-text="serverErrorHeadingText"
      :error-messages="serverErrorMessages"
    />
    <ProjectEditor
      v-model="project"
      :submit-button-text="submitButtonText"
      @submit="createOrUpdateProject"
    />

    <div v-if="!isNewProject">
      <h2>Angemeldete Nutzer:</h2>
      <UserList
        :users="angemeldeteTeilnehmer"
        :show-projects="false"
      />
    </div>

    <div v-if="!isNewProject">
      <h2>Stornierte Nutzer:</h2>
      <UserList
        :users="stornierteTeilnehmer"
        :show-projects="false"
      />
    </div>

    <div v-if="!isNewProject">
      <h2>Liste für Export:</h2>
      <UserListForExport
        :users="angemeldeteTeilnehmer"
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
  </BaseLayout>
</template>

<script>
import ProjectEditor from '@/components/ProjectEditor.vue';
import api from '@/modules/ferienpass-api';
import ErrorBox from '@/components/ErrorBox.vue';
import UserList from '@/components/UserList.vue';
import BaseLayout from '@/views/layouts/BaseLayout.vue';
import UserListForExport from '@/components/UserListForExport.vue';
import { defaultProject } from '@/modules/models';

export default {
  name: 'ProjectEdit',
  components: {
    UserListForExport,
    BaseLayout,
    ProjectEditor,
    ErrorBox,
    UserList,
  },
  data() {
    return {
      project: defaultProject,
      angemeldeteTeilnehmer: [],
      stornierteTeilnehmer: [],
      serverErrorMessages: [],
      successAutomaticDismissCountDown: 0,
    };
  },
  computed: {
    projectId() {
      return parseInt(this.$route.query.id, 10);
    },
    isNewProject() {
      return this.projectId <= 0;
    },
    titleText() {
      if (this.isNewProject) {
        return 'Veranstaltung anlegen';
      }
      return 'Veranstaltung bearbeiten';
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
      this.loadRegisteredUsers();
      this.loadCancelledUsers();
    }
  },
  methods: {
    loadProjectData() {
      this.serverErrorMessages = [];
      api.getProjectById(this.projectId).then((project) => {
        this.project = project;
      }).catch((e) => this.serverErrorMessages.push(e.toString()));
    },
    loadRegisteredUsers() {
      this.serverErrorMessages = [];
      api.getRegisteredUsersOfProject(this.projectId).then((users) => {
        this.angemeldeteTeilnehmer = users;
      }).catch((e) => this.serverErrorMessages.push(e.toString()));
    },
    loadCancelledUsers() {
      this.serverErrorMessages = [];
      api.getCancelledUsersOfProject(this.projectId).then((users) => {
        this.stornierteTeilnehmer = users;
      }).catch((e) => this.serverErrorMessages.push(e.toString()));
    },
    createOrUpdateProject() {
      this.serverErrorMessages = [];
      if (this.isNewProject) {
        api.createProject(this.project).then(() => {
          this.showSuccessInfo();
        }).catch((errorMessages) => { this.serverErrorMessages = errorMessages; });
      } else {
        api.updateProject(this.project).then(() => {
          this.showSuccessInfo();
        }).catch((errorMessages) => { this.serverErrorMessages = errorMessages; });
      }
    },
    showSuccessInfo() {
      this.successAutomaticDismissCountDown = 5;
    },
  },
};
</script>

<style scoped>

</style>
