<template>
  <div>
    <h3>{{ heading }}</h3>

    <TextInput
      :base="`${base}-name`"
      label="Name"
      :value="name"
      @update="$emit('update:name', $event)"
    />

    <TextInput
      :base="`${base}-anschrift`"
      label="Anschrift"
      :value="anschrift"
      @update="$emit('update:anschrift', $event)"
    />

    <TextInput
      :base="`${base}-telefon`"
      label="Telefon"
      :value="telefon"
      @update="$emit('update:telefon', $event)"
    />
  </div>
</template>

<script>
import TextInput from '@/components/wrapper/TextInput.vue';

export default {
  name: 'Kontakt',
  components: { TextInput },
  props: {
    base: {
      type: String,
      required: true,
    },
    heading: {
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
