<template>
  <BaseLayout class="projects">
    <h1>Veranstaltungsübersicht</h1>
    <ProjectList
      :projects="projects"
      @project-deleted="loadProjects"
    />
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
  },
};
</script>

<style scoped>
</style>
