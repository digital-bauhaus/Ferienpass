<template>
  <b-form-group
    :id="`${base}-group`"
    :label-for="`${base}-value`"
    :label="label"
  >
    <b-form-input
      :id="`${base}-value`"
      :type="type"
      :required="required"
      trim
      :placeholder="placeholder"
      :value="value"
      @update="$emit('update', $event)"
    />
  </b-form-group>
</template>

<script>
export default {
  name: 'TextInput',
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
    type: {
      type: String,
      default: 'text',
    },
    required: {
      type: Boolean,
      default: false,
    },
    placeholder: {
      type: String,
      default: '',
    },
  },
};
</script>

<style scoped>

</style>
