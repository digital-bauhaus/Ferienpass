<template>
  <div>
    <FieldInput
      :base="`${base}-name`"
      label="Name"
      :disabled="disabled"
      :value="name"
      @update="$emit('update:name', $event)"
    />

    <FieldInput
      :base="`${base}-anschrift`"
      label="Anschrift"
      :disabled="disabled"
      :value="anschrift"
      @update="$emit('update:anschrift', $event)"
    />

    <FieldInput
      :base="`${base}-telefon`"
      label="Telefon"
      :disabled="disabled"
      :required="telefonRequired"
      :value="telefon"
      @update="$emit('update:telefon', $event)"
    />
  </div>
</template>

<script>
import FieldInput from '@/components/form/FieldInput.vue';

export default {
  name: 'Kontakt',
  components: { FieldInput },
  props: {
    disabled: {
      type: Boolean,
      default: false,
    },
    base: {
      type: String,
      required: true,
    },
    name: {
      type: String,
      required: true,
    },
    anschrift: {
      type: String,
      required: true,
    },
    telefon: {
      type: String,
      required: true,
    },
    telefonRequired: {
      type: Boolean,
      default: false,
    },
  },
};
</script>

<style scoped>

</style>
