<template>
  <html>
    <nav>
          <input type="text" class="searchbar" placeholder="Suche ...">
          <a href="/#/Verwaltung/" >Alle Veranstaltungen</a>
          <a href="/#/VeranstaltungEdit?id=-1" >Veranstaltung erstellen </a>
          <a href="/#/Teilnehmer/" >Alle Teilnehmer</a>
          <a href="/#/TeilnehmerAdd/" class="selected">Teilnehmer erstellen</a>
        </nav>
    <main>
    <h1> Microservice 1 :)</h1>
    </main>
  </html>
</template>

<script>
import { AXIOS } from './http-common';

export default {
  name: 'Teilnehmer',
  data () {
    return {
      user: [],
      errors: []
    };
  },
  created () {
    var id = parseFloat(this.$route.query.id);
    AXIOS.get('/user/' + id)
    .then(response => {
      this.user = response.data
    })
    .catch(e => {
      this.errors.push(e)
    })
  },
  methods: {
    kill (event) {
      event.target.parentElement.parentElement.remove();
    }
  }
}

</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

