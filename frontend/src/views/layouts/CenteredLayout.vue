<template>
  <div class="centered-layout">
    <NavigationMenu />
    <b-container
      fluid
      class="centered-layout__content"
    >
      <slot />
    </b-container>
  </div>
</template>

<script>
import NavigationMenu from '@/components/NavBar.vue';

export default {
  name: 'CenteredLayout',
  components: { NavigationMenu },
};
</script>

<style scoped>

.centered-layout__content {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  padding-top: 70px;
}

</style>
