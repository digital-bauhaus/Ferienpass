<template>
  <div class="base-layout">
    <NavigationMenu />
    <b-container
      fluid
      class="base-layout__content"
    >
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
.base-layout__content {
  padding-top: 70px;
}
</style>
