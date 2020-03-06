<template>
  <div>
    <FieldInput
      :base="`${base}-name`"
      label="Name"
      :value="name"
      @update="$emit('update:name', $event)"
    />

    <FieldInput
      :base="`${base}-anschrift`"
      label="Anschrift"
      :value="anschrift"
      @update="$emit('update:anschrift', $event)"
    />

    <FieldInput
      :base="`${base}-telefon`"
      label="Telefon"
      :value="telefon"
      @update="$emit('update:telefon', $event)"
    />
  </div>
</template>

<script>
import FieldInput from '@/components/wrapper/FieldInput.vue';

export default {
  name: 'Kontakt',
  components: { FieldInput },
  props: {
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
  },
};
</script>

<style scoped>

</style>
