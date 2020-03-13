<template>
  <BaseLayout class="user-edit">
    <h1>
      {{ titleText }}
    </h1>

    <UserEditor
      v-model="user"
      :submit-button-text="submitButtonText"
      @submit="updateUser"
    >
      <template v-slot:before>
        <FormSection label="Verwaltungsaufgaben">
          <Verwaltungsaufgaben />
        </FormSection>
      </template>
    </UserEditor>

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
  </BaseLayout>
</template>

<script>
import api from '@/modules/ferienpass-api';
import UserEditor from '@/components/UserEditor.vue';
import BaseLayout from '@/views/layouts/BaseLayout.vue';
import ProjectList from '@/components/ProjectList.vue';
import Verwaltungsaufgaben from '@/components/userEditor/Verwaltungsaufgaben.vue';
import FormSection from '@/components/form/FormSection.vue';
import { defaultUser } from '@/modules/models';
import { FailureToast, SuccessToast } from '@/modules/sweet-alert';
import handleCommonServerError from '@/modules/error-handling';

export default {
  name: 'UserEdit',
  components: {
    ProjectList,
    BaseLayout,
    UserEditor,
    Verwaltungsaufgaben,
    FormSection,
  },
  data() {
    return {
      user: defaultUser,
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
    Promise.all(dataPromises).then(() => { this.loaded = true; });
  },
  methods: {
    loadUserData() {
      return api.getUserById(this.userId).then((user) => {
        this.user = user;
      }).catch(() => {
        FailureToast.fire({
          text: 'Fehler: Teilnehmer konnte nicht geladen werden.',
        });
      });
    },
    loadProjects() {
      return api.getProjects().then((projects) => { this.allProjects = projects; }).catch(() => {
        FailureToast.fire({
          text: 'Fehler: Projekte konnten nicht geladen werden.',
        });
      });
    },
    loadRegisteredProjectsOfUser() {
      return api.getRegisteredProjectsOfUser(this.userId).then(
        (projects) => { this.registeredProjectsOfUser = projects; },
      ).catch(() => {
        FailureToast.fire({
          text: 'Fehler: Angemeldete Projekte des Teilnehmers konnten nicht geladen werden.',
        });
      });
    },
    loadCancelledProjectsOfUser() {
      return api.getCancelledProjectsOfUser(this.userId).then(
        (projects) => { this.cancelledProjectsOfUser = projects; },
      ).catch(() => {
        FailureToast.fire({
          text: 'Fehler: Stornierte Projekte des Teilnehmers konnten nicht geladen werden.',
        });
      });
    },
    reloadProjectsOfUser() {
      this.loadRegisteredProjectsOfUser();
      this.loadCancelledProjectsOfUser();
    },
    updateUser() {
      api.updateUser(this.user).then(() => {
        SuccessToast.fire({ text: 'Speichern erfolgreich' });
      }).catch((error) => {
        handleCommonServerError(error);
      });
    },
    unassignFromProject(projectId, userId) {
      api.unassignUserFromProject(projectId, userId).then(() => {
        SuccessToast.fire({ text: 'Stornierung erfolgreich' });
        this.reloadProjectsOfUser();
      }).catch((error) => {
        handleCommonServerError(error);
      });
    },
    assignToProject(projectId, userId) {
      api.assignUserToProject(projectId, userId).then(() => {
        SuccessToast.fire({ text: 'Anmeldung erfolgreich' });
        this.reloadProjectsOfUser();
      }).catch((error) => {
        handleCommonServerError(error);
      });
    },
    reactivateProject(projectId, userId) {
      this.assignToProject(projectId, userId);
    },
  },
};
</script>

<style scoped>

</style>
