<template>
  <div>
    <NavigationMenu />
    <main>
      <div class="front">
        <div class="title">
          {{ title }}
        </div><hr>
        <div class="subtitle">
          {{ subtitle }}
        </div>
      </div>
    </main>
  </div>
</template>


<script>

import NavigationMenu from "../components/NavigationMenu";
export default {
  name: 'Verwaltung',
  components: {NavigationMenu},
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

