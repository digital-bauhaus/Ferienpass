<template>
    <div>
    <nav>
        <a href="/#/Veranstaltungen/">Alle Veranstaltungen</a>
        <a href="/#/Veranstaltung/" >Veranstaltung erstellen </a>
        <a href="/#/Teilnehmer/" >Alle Teilnehmer</a>
    </nav>
    <main>
      <div class="front">
      <div class="title">{{title}}</div><hr />
      <div class="subtitle">{{subtitle}}</div>
      </div>
    </main>
    </div>
</template>


<script>

export default {
  name: 'Verwaltung',

  data () {
    return {
      title: 'Ferienpass Weimar',
      subtitle: 'Administration'
    }
  }
}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

