<template>
  <div>
    <NavigationMenu />
    <b-container
      fluid
      class="verwaltung--container"
    >
      <b-row
        align-v="center"
        class="h-100"
      >
        <b-col>
          <h1 class="text-center">
            {{ title }}
          </h1>
          <hr class="verwaltung--divider">
          <h2 class="text-center text-secondary">
            {{ subtitle }}
          </h2>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>


<script>

import NavigationMenu from '@/components/NavBar.vue';

export default {
  name: 'Verwaltung',
  components: { NavigationMenu },
  data() {
    return {
      title: 'Ferienpass Weimar',
      subtitle: 'Administration',
    };
  },
};
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.verwaltung--container {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
}

.verwaltung--divider {
  border-top: 2px solid #2c3e50;
  width: 60%;
}

</style>
