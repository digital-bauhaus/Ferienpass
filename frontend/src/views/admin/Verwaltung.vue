<template>
  <CenteredLayout class="verwaltung">
    <b-row
      align-v="center"
      class="h-100"
    >
      <b-col>
        <h1 class="text-center">
          {{ title }}
        </h1>
        <hr class="verwaltung__divider">
        <h2 class="text-center text-secondary">
          {{ subtitle }}
        </h2>
      </b-col>
    </b-row>
  </CenteredLayout>
</template>

<script>
import CenteredLayout from '@/views/layouts/CenteredLayout.vue';

export default {
  name: 'Verwaltung',
  components: { CenteredLayout },
  data() {
    return {
      title: 'Ferienpass Weimar',
      subtitle: 'Administration',
    };
  },
};
</script>

<style scoped>

.verwaltung__divider {
  border-top: 2px solid #2c3e50;
  width: 40%;
}

</style>
