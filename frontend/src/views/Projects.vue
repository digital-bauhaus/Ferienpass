<template>
  <BaseLayout class="projects">
    <h1>
      Übersicht Veranstaltungen
    </h1>
    <ProjectList
      :projects="projects"
      @project-deleted="loadProjects"
    >
      <template v-slot:actions="{ row }">
        <b-button
          size="sm"
          class="m-1"
          :to="{path: '../ProjectsEdit', query: {id: row.item.id }}"
        >
          Bearbeiten
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

export default {
  name: 'Projects',
  components: { BaseLayout, ProjectList },
  data() {
    return {
      errors: [],
      projects: [],
    };
  },
  created() {
    this.loadProjects();
  },
  methods: {
    loadProjects() {
      this.errors = [];
      api.getProjects().then((projects) => { this.projects = projects; })
        .catch((e) => this.errors.push(e));
    },
    deleteProject(projectId) {
      this.$swal({
        title: 'Wirklich löschen?',
        text: 'Das Projekt wird vollständig gelöscht!',
        icon: 'warning',
        buttons: true,
        dangerMode: true,
      })
        .then((willDelete) => {
          if (willDelete) {
            this.errors = [];
            api.deleteProject(projectId).then(() => {
              this.loadProjects();
              return this.$swal('Projekt wurde gelöscht!', {
                icon: 'success',
              });
            }).catch((e) => {
              this.errors.push(e);
              return this.$swal('Da ist was schief gegangen :(', {
                icon: 'error',
              });
            });
          }
        });
    },
  },
};
</script>

<style scoped>
</style>
