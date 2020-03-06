<template>
  <b-form-group
    :id="`${base}-group`"
    :label-for="`${base}-value`"
    :label="label"
  >
    <b-form-textarea
      :id="`${base}-value`"
      :required="required"
      trim
      max-rows="8"
      :value="value"
      @update="$emit('update', $event)"
    />
  </b-form-group>
</template>

<script>
export default {
  name: 'TextAreaInput',
  model: {
    prop: 'value',
    event: 'update',
  },
  props: {
    base: {
      type: String,
      required: true,
    },
    value: {
      type: String,
      required: true,
    },
    label: {
      type: String,
      required: true,
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
