<template>
  <div>
    <h3>{{ heading }}</h3>

    <b-form-group
      :id="`${base}-name-group`"
      :label-for="`${base}-name-value`"
      label="Name"
    >
      <b-form-input
        :id="`${base}-name-value`"
        required
        trim
        :value="name"
        @update="$emit('update:name', $event)"
      />
    </b-form-group>

    <b-form-group
      :id="`${base}-anschrift-group`"
      :label-for="`${base}-anschrift-value`"
      label="Anschrift"
    >
      <b-form-input
        :id="`${base}-anschrift-value`"
        required
        trim
        :value="anschrift"
        @update="$emit('update:anschrift', $event)"
      />
    </b-form-group>

    <b-form-group
      :id="`${base}-telefon-group`"
      :label-for="`${base}-telefon-value`"
      label="Telefon"
    >
      <b-form-input
        :id="`${base}-telefon-value`"
        type="tel"
        required
        trim
        :value="telefon"
        @update="$emit('update:telefon', $event)"
      />
    </b-form-group>
  </div>
</template>

<script>
export default {
  name: 'Kontakt',
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
