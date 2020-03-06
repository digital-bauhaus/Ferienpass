<template>
  <BaseLayout class="user-edit">
    <h1>
      {{ titleText }}
    </h1>
    <ErrorAlert
      v-if="showServerErrorAlert"
      :heading-text="serverErrorHeadingText"
      :errors="serverErrorMessages"
    />

    <UserEditor
      v-model="user"
      :is-admin-view="true"
      :submit-button-text="submitButtonText"
      @submit="updateUser"
    />

    <hr>

    <h2>Angemeldete Projekte</h2>
    <ProjectList :projects="projectsOfUser">
      <template v-slot:actions="{ row }">
        <b-button
          size="sm"
          class="m-1"
          variant="warning"
          @click="unassignFromProject(row.item.id, user.id)"
        >
          Stornieren
        </b-button>
      </template>
    </ProjectList>

    <h2>Stornierte Projekte</h2>
    <ProjectList :projects="cancelledProjectsOfUser">
      <template v-slot:actions="{ row }">
        <b-button
          size="sm"
          class="m-1"
          variant="warning"
          @click="reactivateProject(row.item.id, user.id)"
        >
          Reaktivieren
        </b-button>
      </template>
    </ProjectList>

    <h2>Verfügbare Projekte</h2>
    <ProjectList :projects="availableProjects">
      <template v-slot:actions="{ row }">
        <b-button
          size="sm"
          class="m-1"
          variant="warning"
          @click="assignToProject(row.item.id, user.id)"
        >
          Anmelden
        </b-button>
      </template>
    </ProjectList>

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
import api from '@/modules/ferienpass-api';
import ErrorAlert from '@/components/ErrorAlert.vue';
import UserEditor from '@/components/UserEditor.vue';
import BaseLayout from '@/views/layouts/BaseLayout.vue';
import ProjectList from '@/components/ProjectList.vue';

export default {
  name: 'UserEdit',
  components: {
    ProjectList,
    BaseLayout,
    UserEditor,
    ErrorAlert,
  },
  data() {
    return {
      user: {},
      serverErrorMessages: [],
      successAutomaticDismissCountDown: 0,
      isSchoolKid: false,
      allProjects: [],
      projectsOfUser: [],
      cancelledProjectsOfUser: [],
      loaded: false,
    };
  },
  computed: {
    userId() {
      return parseInt(this.$route.query.id, 10);
    },
    titleText() {
      return 'Teilnehmer bearbeiten';
    },
    submitButtonText() {
      return 'Speichern';
    },
    successText() {
      return 'Teilnehmer erfolgreich gespeichert.';
    },
    serverErrorHeadingText() {
      return 'Speichern nicht möglich. Bitte beheben Sie folgende Fehler:';
    },
    showServerErrorAlert() {
      return this.serverErrorMessages.length > 0;
    },
    availableProjects() {
      return this.allProjects.filter((project) => {
        const isProjectOfUser = this.projectsOfUser.map((userProject) => userProject.id).includes(
          project.id,
        );
        const isCancelledProjectOfuser = this.cancelledProjectsOfUser.map(
          (userProject) => userProject.id,
        ).includes(project.id);
        return !isProjectOfUser && !isCancelledProjectOfuser;
      });
    },
  },
  created() {
    const dataPromises = [];
    dataPromises.push(this.loadUserData());
    dataPromises.push(this.loadProjects());
    dataPromises.push(this.loadProjectsOfUser());
    dataPromises.push(this.loadCancelledProjectsOfUser());
    Promise.all(dataPromises).then(() => { this.loaded = true; }).catch(
      (e) => this.errorMessages.push(e.toString()),
    );
  },
  methods: {
    loadUserData() {
      this.serverErrorMessages = [];
      return api.getUser(this.userId).then((user) => {
        this.user = user;
      }).catch((e) => this.serverErrorMessages.push(e.toString()));
    },
    loadProjects() {
      return api.getProjects().then((projects) => { this.allProjects = projects; });
    },
    loadProjectsOfUser() {
      return api.getUsersProjects(this.userId).then(
        (projects) => { this.projectsOfUser = projects; },
      );
    },
    loadCancelledProjectsOfUser() {
      return api.getUsersCancelledProjects(this.userId).then(
        (projects) => { this.cancelledProjectsOfUser = projects; },
      );
    },
    reloadProjectsOfUser() {
      this.loadProjectsOfUser();
      this.loadCancelledProjectsOfUser();
    },
    updateUser() {
      console.log('Updating user');
      this.serverErrorMessages = [];
      api.updateUser(this.user).then(() => {
        this.showSuccessInfo();
      }).catch((errorMessages) => { this.serverErrorMessages = errorMessages; });
    },
    unassignFromProject(projectId, userId) {
      api.deleteUserFromProject(projectId, userId).then(() => {
        this.reloadProjectsOfUser();
      });
    },
    assignToProject(projectId, userId) {
      api.addUserToProject(projectId, userId).then(() => {
        this.reloadProjectsOfUser();
      });
    },
    reactivateProject(projectId, userId) {
      this.assignToProject(projectId, userId);
    },
    showSuccessInfo() {
      this.successAutomaticDismissCountDown = 5;
    },
  },
};
</script>

<style scoped>

</style>
