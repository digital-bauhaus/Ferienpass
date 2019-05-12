<template>
	<div>
    <NavigationMenu/>
    <main>
      <h1>Veranstaltungsübersicht</h1>
      <ProjectList v-on:project-deleted="loadProjects" :projects="projects"/>
    </main>
	</div>
</template>

<script>
import { getProjects } from '../modules/ferienpass-api';
import NavigationMenu from "../components/NavigationMenu";
import ProjectList from "../components/ProjectList";

export default {
  name: 'Veranstaltungen',
  components: {ProjectList, NavigationMenu},
  data () {
    return {
      errors: [],
      projects: []
    };
  },
  created () {
    this.loadProjects()
  },
  methods: {
    loadProjects () {
        this.errors = [];
        getProjects().then(projects => this.projects = projects).catch(e => this.errors.push(e));
    }
  }
}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

