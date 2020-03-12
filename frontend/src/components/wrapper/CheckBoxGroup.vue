<template>
  <b-form-group
    :id="`${base}-group`"
    :label="label"
    label-class="font-weight-bold"
  >
    <slot />
  </b-form-group>
</template>

<script>
export default {
  name: 'CheckBoxGroup',
  props: {
    base: {
      type: String,
      required: true,
    },
    label: {
      type: String,
      default: null,
    },
  },
};
</script>

<style scoped>

</style>
