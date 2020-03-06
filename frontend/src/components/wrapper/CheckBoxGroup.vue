<template>
  <div>
    <b-form-group
      :id="`${base}-group`"
      :label="label"
    >
      <slot />
    </b-form-group>
  </div>
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
