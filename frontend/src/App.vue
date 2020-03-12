<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>

export default {
  name: 'App',
  created() {
    this.applyIeRoutingFix();
  },
  methods: {
    isInternetExplorer11() {
      // https://stackoverflow.com/questions/21825157/internet-explorer-11-detection
      return !!window.MSInputMethodContext && !!document.documentMode;
    },
    applyIeRoutingFix() {
      // https://github.com/vuejs/vue-router/issues/1849
      if (this.isInternetExplorer11()) {
        window.addEventListener('hashchange', () => {
          const currentPath = window.location.hash.slice(1);
          if (this.$route.path !== currentPath) {
            this.$router.push(currentPath);
          }
        }, false);
      }
    },
  },
};
</script>

<style>

</style>
