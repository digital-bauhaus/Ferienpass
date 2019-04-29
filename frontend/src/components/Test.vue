<template>
    <html>
    <nav>
        <a href="/#/Verwaltung/">Alle Veranstaltungen</a>
        <a href="/#/Veranstaltung/" >Veranstaltung erstellen </a>
        <a href="/#/Teilnehmer/" >Alle Teilnehmer</a>
        <a href="/#/TeilnehmerAdd/" >Teilnehmer erstellen</a>
    </nav>
    <main>
      <div class="front">
      <div class="title">{{title}}</div><hr />
      <div class="subtitle">{{subtitle}}</div>
      </div>
    </main>
    </html>
</template>


<script>

export default {
  name: 'Test',

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

