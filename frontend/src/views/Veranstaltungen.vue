<template>
  <div>
    <NavigationMenu />
    <main>
      <h1>Veranstaltungsübersicht</h1>
      <ProjectList
        :projects="projects"
        @project-deleted="loadProjects"
      />
    </main>
  </div>
</template>

<script>
import api from '../modules/ferienpass-api';
import NavigationMenu from '../components/NavigationMenu';
import ProjectList from '../components/ProjectList';

export default {
  name: 'Veranstaltungen',
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
      api.getProjects().then((projects) => this.projects = projects).catch((e) => this.errors.push(e));
    },
  },
};
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

main {
  width: 80%;
  position: absolute;
  right: 0px;
  height: 100%;
}

</style>
