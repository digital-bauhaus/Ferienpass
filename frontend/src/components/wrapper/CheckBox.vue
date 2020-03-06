<template>
  <div>
    <b-form-group
      :id="`${base}-group`"
      :label="heading"
    >
      <b-form-checkbox
        :id="`${base}-value`"
        :required="required"
        :checked="checked"
        @input="$emit('input', $event)"
      >
        <slot>
          {{ label }}
        </slot>
      </b-form-checkbox>
    </b-form-group>
  </div>
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
    label: {
      type: String,
      default: '',
    },
    heading: {
      type: String,
      default: null,
    },
    required: {
      type: Boolean,
      default: false,
    },
  },
};
</script>

<style scoped>

</style>
