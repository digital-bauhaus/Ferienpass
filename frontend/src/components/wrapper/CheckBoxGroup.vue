<template>
  <b-form-group
    :id="`${base}-group`"
    :label="label"
    label-class="font-weight-bold"
    :disabled="disabled"
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
    disabled: {
      type: Boolean,
      default: false,
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
