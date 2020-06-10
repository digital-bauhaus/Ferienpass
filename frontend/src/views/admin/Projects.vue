<template>
  <BaseLayout class="projects">
    <h1>
      Übersicht Veranstaltungen
    </h1>
    <ProjectList
      :projects="projects"
    >
      <template v-slot:actions="{ row }">
        <b-button
          size="sm"
          class="m-1"
          :to="{path: '../ProjectEdit', query: {id: row.item.id }}"
        >
          Bearbeiten
        </b-button>
        <b-button
          size="sm"
          class="m-1"
          :to="{path: '../ProjectUsers', query: {id: row.item.id }}"
        >
          Teilnehmerliste
        </b-button>
        <b-button
          size="sm"
          class="m-1"
          variant="danger"
          @click="deleteProject(row.item.id)"
        >
          Löschen
        </b-button>
      </template>
    </ProjectList>
  </BaseLayout>
</template>

<script>
import api from '@/modules/ferienpass-api';
import ProjectList from '@/components/ProjectList.vue';
import BaseLayout from '@/views/layouts/BaseLayout.vue';
import { DeleteDialog, FailureToast, SuccessDialog } from '@/modules/sweet-alert';
import handleCommonServerError from '@/modules/error-handling';

export default {
  name: 'Projects',
  components: { BaseLayout, ProjectList },
  data() {
    return {
      projects: [],
    };
  },
  created() {
    this.loadProjects();
  },
  methods: {
    loadProjects() {
      api.getProjects().then((projects) => { this.projects = projects; })
        .catch(() => {
          FailureToast.fire({
            text: 'Fehler: Veranstaltungen konnten nicht geladen werden.',
          });
        });
    },
    deleteProject(projectId) {
      DeleteDialog.fire({
        text: 'Die Veranstaltung wird vollständig gelöscht und die Daten sind verloren!',
      }).then((result) => {
        if (result.value) {
          this.doDeleteProject(projectId);
        }
      });
    },
    doDeleteProject(projectId) {
      api.deleteProject(projectId).then(() => {
        SuccessDialog.fire({
          text: 'Veranstaltung wurde gelöscht!',
        });
        this.loadProjects();
      }).catch((error) => {
        handleCommonServerError(error);
      });
    },
  },
};
</script>

<style scoped>
</style>
