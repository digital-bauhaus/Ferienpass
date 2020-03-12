<template>
  <b-card
    :title="label"
    title-tag="h5"
    class="group mb-3"
  >
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
.group >>> .form-group:last-child {
  margin-bottom: 0;
}
</style>
