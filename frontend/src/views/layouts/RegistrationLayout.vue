<template>
  <b-container
    class="registration-layout"
  >
    <header class="registration-layout__header">
      <img
        src="../../assets/ferienpasslogo_ohne_hg.png"
        alt="Ferienpass Weimar"
      >
    </header>
    <slot />
  </b-container>
</template>

<script>

export default {
  name: 'RegistrationLayout',
};
</script>

<style scoped>
.registration-layout__header {
  max-width: 100%;
  background-color: rgba(51, 122, 183, 1);
  text-align: center;
  padding: 20px 0;
}
</style>
