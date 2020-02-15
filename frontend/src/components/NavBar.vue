<template>
  <b-navbar
    toggleable="lg"
    type="dark"
    variant="dark"
  >
    <b-navbar-brand :to="{ path: '/Verwaltung' }">
      Ferienpass: Verwaltung
    </b-navbar-brand>

    <b-navbar-toggle target="nav-collapse" />

    <b-collapse
      id="nav-collapse"
      is-nav
    >
      <b-navbar-nav>
        <b-nav-item active-class="active" :to="{ path: '/Veranstaltungen' }">
          Alle Veranstaltungen
        </b-nav-item>
        <b-nav-item active-class="active" :to="{ path: '/VeranstaltungEdit', query: { id: '-1' } }">
          Veranstaltung erstellen
        </b-nav-item>
        <b-nav-item active-class="active" :to="{ path: '/Teilnehmer' }">
          Alle Teilnehmer
        </b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
export default {
  name: 'NavBar',
};
</script>

<style scoped>
</style>
