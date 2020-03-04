<template>
  <div class="base-layout">
    <NavigationMenu />
    <b-container>
      <slot />
    </b-container>
  </div>
</template>

<script>
import NavigationMenu from '@/components/NavBar.vue';

export default {
  name: 'BaseLayout',
  components: { NavigationMenu },
};
</script>

<style scoped>

</style>
