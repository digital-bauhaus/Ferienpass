<template>
  <b-form-group
    :id="`${base}-group`"
    :label="`${label}${required ? ' *' : ''}`"
  >
    <b-form-radio-group
      :id="`${base}-value`"
      :name="`${base}-name`"
      :required="required"
      stacked
      :disabled="disabled"
      :checked="checked"
      @input="$emit('input', $event)"
    >
      <b-form-radio :value="true">
        {{ yesText }}
      </b-form-radio>
      <b-form-radio :value="false">
        {{ noText }}
      </b-form-radio>
    </b-form-radio-group>
  </b-form-group>
</template>

<script>
export default {
  name: 'YesNoRadio',
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
      required: true,
      validator: (prop) => typeof prop === 'boolean' || prop === null,
    },
    label: {
      type: String,
      required: true,
    },
    required: {
      type: Boolean,
      default: false,
    },
    yesText: {
      type: String,
      default: 'Ja',
    },
    noText: {
      type: String,
      default: 'Nein',
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
};
</script>

<style scoped>

</style>
