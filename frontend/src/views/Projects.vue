<template>
  <div>
    <NavigationMenu />
    <b-container>
      <h1>Veranstaltungsübersicht</h1>
      <ProjectList
        :projects="projects"
        @project-deleted="loadProjects"
      />
    </b-container>
  </div>
</template>

<script>
import api from '@/modules/ferienpass-api';
import NavigationMenu from '@/components/NavBar.vue';
import ProjectList from '@/components/ProjectList.vue';

export default {
  name: 'Projects',
  components: { ProjectList, NavigationMenu },
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
