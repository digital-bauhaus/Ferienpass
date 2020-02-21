<template>
  <div>
    <NavigationMenu />
    <b-container>
      <Veranstaltung
        :veranstaltung-id="routeId"
      />
    </b-container>
  </div>
</template>

<script>
import NavigationMenu from '@/components/NavBar.vue';
import Veranstaltung from '@/components/VeranstaltungEditor.vue';

export default {
  name: 'VeranstaltungEditNew',
  components: { Veranstaltung, NavigationMenu },
  computed: {
    routeId() {
      return parseInt(this.$route.query.id, 10);
    },
  },
};
</script>

<style scoped>
</style>
