<template>
  <BaseLayout class="user-edit">
    <h1>
      {{ titleText }}
    </h1>
    <ErrorBox
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
    <ProjectList :projects="registeredProjectsOfUser">
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
import ErrorBox from '@/components/ErrorBox.vue';
import UserEditor from '@/components/UserEditor.vue';
import BaseLayout from '@/views/layouts/BaseLayout.vue';
import ProjectList from '@/components/ProjectList.vue';
import { defaultUser } from '@/modules/models';

export default {
  name: 'UserEdit',
  components: {
    ProjectList,
    BaseLayout,
    UserEditor,
    ErrorBox,
  },
  data() {
    return {
      user: defaultUser,
      serverErrorMessages: [],
      successAutomaticDismissCountDown: 0,
      allProjects: [],
      registeredProjectsOfUser: [],
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
        const isProjectOfUser = this.registeredProjectsOfUser
          .map((userProject) => userProject.id).includes(
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
    dataPromises.push(this.loadRegisteredProjectsOfUser());
    dataPromises.push(this.loadCancelledProjectsOfUser());
    Promise.all(dataPromises).then(() => { this.loaded = true; }).catch(
      (e) => this.serverErrorMessages.push(e.toString()),
    );
  },
  methods: {
    loadUserData() {
      this.serverErrorMessages = [];
      return api.getUserById(this.userId).then((user) => {
        this.user = user;
      }).catch((e) => this.serverErrorMessages.push(e.toString()));
    },
    loadProjects() {
      return api.getProjects().then((projects) => { this.allProjects = projects; });
    },
    loadRegisteredProjectsOfUser() {
      return api.getRegisteredProjectsOfUser(this.userId).then(
        (projects) => { this.registeredProjectsOfUser = projects; },
      );
    },
    loadCancelledProjectsOfUser() {
      return api.getCancelledProjectsOfUser(this.userId).then(
        (projects) => { this.cancelledProjectsOfUser = projects; },
      );
    },
    reloadProjectsOfUser() {
      this.loadRegisteredProjectsOfUser();
      this.loadCancelledProjectsOfUser();
    },
    updateUser() {
      this.serverErrorMessages = [];
      api.updateUser(this.user).then(() => {
        this.showSuccessInfo();
      }).catch((errorMessages) => { this.serverErrorMessages = errorMessages; });
    },
    unassignFromProject(projectId, userId) {
      api.unassignUserFromProject(projectId, userId).then(() => {
        this.reloadProjectsOfUser();
      });
    },
    assignToProject(projectId, userId) {
      api.assignUserToProject(projectId, userId).then(() => {
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
