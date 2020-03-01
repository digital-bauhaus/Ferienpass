<template>
  <div>
    <NavigationMenu />
    <b-container>
      <h1>Veranstaltungsübersicht</h1>
      <ProjectListNew
        :projects="projects"
        @project-deleted="loadProjects"
      />
    </b-container>
  </div>
</template>

<script>
import api from '@/modules/ferienpass-api';
import NavigationMenu from '@/components/NavBar.vue';
import ProjectListNew from '@/components/ProjectListNew.vue';

export default {
  name: 'VeranstaltungenNew',
  components: { ProjectListNew, NavigationMenu },
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


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
