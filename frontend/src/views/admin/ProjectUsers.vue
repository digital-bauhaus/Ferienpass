<template>
  <BaseLayout class="projects-edit">
    <h1>
      {{ titleText }}
    </h1>
    <UserListForExport
      :users="angemeldeteTeilnehmer"
      :project="project"
    />
  </BaseLayout>
</template>

<script>
import api from '@/modules/ferienpass-api';
import BaseLayout from '@/views/layouts/BaseLayout.vue';
import UserListForExport from '@/components/UserListForExport.vue';
import { defaultProject } from '@/modules/models';
import { FailureToast } from '@/modules/sweet-alert';

export default {
  name: 'ProjectUsers',
  components: {
    UserListForExport,
    BaseLayout,
  },
  data() {
    return {
      project: defaultProject,
      angemeldeteTeilnehmer: [],
      loaded: false,
    };
  },
  computed: {
    projectId() {
      return parseInt(this.$route.query.id, 10);
    },
    titleText() {
      return this.createHeaderText();
    },
  },
  created() {
    const dataPromises = [];
    dataPromises.push(this.loadProjectData());
    dataPromises.push(this.loadRegisteredUsers());
    Promise.all(dataPromises).then(() => { this.loaded = true; });
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
    createHeaderText(project) {
      if (project) {
        return `Teilnehmerliste: ${project.name} (${this.formatDate(project.datumBeginn)} - ${this.formatDate(project.datumEnde)})`;
      }
      return 'Teilnehmerliste';
    },
  },
};
</script>

<style scoped>

</style>
