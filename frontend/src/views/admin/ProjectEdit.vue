<template>
  <BaseLayout class="projects-edit">
    <h1>
      {{ titleText }}
    </h1>
    <ProjectEditor
      v-model="project"
      :submit-button-text="submitButtonText"
      @submit="createOrUpdateProject"
    />

    <div v-if="!isNewProject">
      <hr>

      <h2>Angemeldete Nutzer:</h2>
      <UserList
        :users="angemeldeteTeilnehmer"
        :show-projects="false"
      >
        <template v-slot:actions="{ row }">
          <b-button
            size="sm"
            class="m-1"
            variant="warning"
            @click="unassignFromProject(project.id, row.item.id)"
          >
            Stornieren
          </b-button>
        </template>
      </UserList>

      <h2>Stornierte Nutzer:</h2>
      <UserList
        :users="stornierteTeilnehmer"
        :show-projects="false"
      >
        <template v-slot:actions="{ row }">
          <b-button
            size="sm"
            class="m-1"
            variant="warning"
            :disabled="project.plaetzeFrei === 0"
            @click="reactivateProject(project.id, row.item.id)"
          >
            Reaktivieren
          </b-button>
        </template>
      </UserList>
    </div>
  </BaseLayout>
</template>

<script>
import ProjectEditor from '@/components/ProjectEditor.vue';
import api from '@/modules/ferienpass-api';
import UserList from '@/components/UserList.vue';
import BaseLayout from '@/views/layouts/BaseLayout.vue';
import { defaultProject } from '@/modules/models';
import { FailureToast, SuccessToast } from '@/modules/sweet-alert';
import handleCommonServerError from '@/modules/error-handling';

export default {
  name: 'ProjectEdit',
  components: {
    BaseLayout,
    ProjectEditor,
    UserList,
  },
  data() {
    return {
      project: defaultProject,
      angemeldeteTeilnehmer: [],
      stornierteTeilnehmer: [],
      loaded: false,
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
  },
  created() {
    if (!this.isNewProject) {
      const dataPromises = [];
      dataPromises.push(this.loadProjectData());
      dataPromises.push(this.loadRegisteredUsers());
      dataPromises.push(this.loadCancelledUsers());
      Promise.all(dataPromises).then(() => { this.loaded = true; });
    }
  },
  methods: {
    async loadProjectData() {
      return api.getProjectById(this.projectId).then((project) => {
        this.project = project;
      }).catch(() => {
        FailureToast.fire({
          text: 'Fehler: Projekt konnte nicht geladen werden.',
        });
      });
    },
    async loadRegisteredUsers() {
      return api.getRegisteredUsersOfProject(this.projectId).then((users) => {
        this.angemeldeteTeilnehmer = users;
      }).catch(() => {
        FailureToast.fire({
          text: 'Fehler: Angemeldete Teilnehmers des Projektes konnten nicht geladen werden.',
        });
      });
    },
    async loadCancelledUsers() {
      return api.getCancelledUsersOfProject(this.projectId).then((users) => {
        this.stornierteTeilnehmer = users;
      }).catch(() => {
        FailureToast.fire({
          text: 'Fehler: Stornierte Teilnehmers des Projektes konnten nicht geladen werden.',
        });
      });
    },
    reloadAllProjectData() {
      this.loadProjectData();
      this.loadRegisteredUsers();
      this.loadCancelledUsers();
    },
    createOrUpdateProject() {
      if (this.isNewProject) {
        api.createProject(this.project).then(() => {
          SuccessToast.fire({ text: 'Anlegen erfolgreich' });
        }).catch((error) => {
          handleCommonServerError(error);
        });
      } else {
        api.updateProject(this.project).then(() => {
          SuccessToast.fire({ text: 'Speichern erfolgreich' });
        }).catch((error) => {
          handleCommonServerError(error);
        });
      }
    },
    unassignFromProject(projectId, userId) {
      api.unassignUserFromProject(projectId, userId).then(() => {
        SuccessToast.fire({ text: 'Stornierung erfolgreich' });
        this.reloadAllProjectData();
      }).catch((error) => {
        handleCommonServerError(error);
      });
    },
    assignToProject(projectId, userId) {
      api.assignUserToProject(projectId, userId).then(() => {
        SuccessToast.fire({ text: 'Anmeldung erfolgreich' });
        this.reloadAllProjectData();
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
