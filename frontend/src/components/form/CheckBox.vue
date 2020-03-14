<template>
  <b-form-checkbox
    :id="`${base}-value`"
    :name="`${base}-value`"
    :required="required"
    :checked="checked"
    :disabled="disabled"
    :size="size"
    @input="$emit('input', $event)"
  >
    <slot />
  </b-form-checkbox>
</template>

<script>
export default {
  name: 'CheckBox',
  model: {
    prop: 'checked',
    event: 'input',
  },
  props: {
    base: {
      type: String,
      required: true,
    },
    checked: {
      type: Boolean,
      required: true,
    },
    required: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    size: {
      type: String,
      default: '',
    },
  },
};
</script>

<style scoped>

</style>
