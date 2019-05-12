<template>
  <nav>
    <!--<input type="text" class="searchbar" placeholder="Suche ...">-->
    <router-link :to="{ path: '/Veranstaltungen' }">Alle Veranstaltungen</router-link>
    <router-link :to="{ path: '/VeranstaltungEdit', query: { id: '-1' } }">Veranstaltung erstellen</router-link>
    <router-link :to="{ path: '/Teilnehmer' }">Alle Teilnehmer</router-link>
  </nav>
</template>

<script>
  export default {
    name: "NavigationMenu"
  }
</script>

<style scoped>
  nav {
    height: 100%;
    width: 20%;
    position: fixed;
    z-index: 1;
    top: 0;
    background-color: #181819;
    overflow-x: hidden;
    padding-top: 60px;
  }

  nav a {
    padding: 15px 15px 15px 32px;
    text-decoration: none;
    font-size: 14px;
    color: #818181;
    display: block;
    transition: 0.3s;
    font-weight: bold;
  }

  nav a:hover {
    color: #f1f1f1;
    text-decoration: none;
    background-color: #3a4372;
    border-left: 3px solid #e2bf3d;
    transition: .5s;
  }

  /*nav .searchbar {
    position: absolute;
    left: 10px; top: 10px; right: 20px;
    width: 90%;
    margin-bottom: 20px;
    padding: 10px;
    border-radius: 15px;
    background-color: #4c4c4c;
    color: white;
    border: 0px solid black;
  }

  nav .searchbar:focus {

    color: #4c4c4c;
    background-color: #f7fcff;
    transition: .5s;
  }*/

  .router-link-active {
    background-color: #252626;
    border-left: 3px solid #e2bf3d;
  }
</style>