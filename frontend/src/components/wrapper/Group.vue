<template>
  <b-card :header="label">
    <slot />
  </b-card>
</template>

<script>
export default {
  name: 'Group',
  props: {
    label: {
      type: String,
      required: true,
    },
  },
};
</script>

<style scoped>

</style>
