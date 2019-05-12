<template>
	<div>
    <NavigationMenu/>
    <main>
      <h1>Veranstaltungsübersicht</h1>
      <ProjectList v-on:event-deleted="getProjects" :projects="projects"/>
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
      projects: [],
      errors: []
    };
  },
  created () {
    this.getProjects()
  },
  methods: {
    getProjects () {
        this.errors = [];
        this.allprojects = [];
        getProjects().then(projects => this.projects = projects).catch(e => this.errors.push(e));
    }
  }
}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.button {
display: inline-block;
float: left;
}
</style>

