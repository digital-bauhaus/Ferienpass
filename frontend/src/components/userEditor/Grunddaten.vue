<template>
  <div>
    <h2>Grunddaten</h2>

    <FieldInput
      base="grunddaten-nachname"
      label="Familienname"
      :required="true"
      :value="nachname"
      @update="$emit('update:nachname', $event)"
    />

    <FieldInput
      base="grunddaten-vorname"
      label="Vorname meines Kindes"
      :required="true"
      :value="vorname"
      @update="$emit('update:vorname', $event)"
    />

    <FieldInput
      base="grunddaten-geburtsdatum"
      label="Geburtsdatum meines Kindes"
      type="date"
      :required="true"
      :value="geburtsdatum"
      @update="$emit('update:geburtsdatum', $event)"
    />

    <FieldInput
      base="grunddaten-strasse"
      label="Straße"
      :required="true"
      :value="strasse"
      @update="$emit('update:strasse', $event)"
    />

    <FieldInput
      base="grunddaten-hausNummer"
      label="Hausnummer"
      :required="true"
      :value="hausNummer"
      @update="$emit('update:hausNummer', $event)"
    />

    <FieldInput
      base="grunddaten-postleitzahl"
      label="Postleitzahl"
      :required="true"
      :value="postleitzahl"
      @update="$emit('update:postleitzahl', $event)"
    />

    <FieldInput
      base="grunddaten-stadt"
      label="Wohnort"
      :required="true"
      :value="stadt"
      @update="$emit('update:stadt', $event)"
    />

    <FieldInput
      base="grunddaten-telefon"
      label="Telefon"
      type="tel"
      :required="true"
      :value="telefon"
      @update="$emit('update:telefon', $event)"
    />

    <FieldInput
      base="grunddaten-email"
      label="eMail"
      type="email"
      :required="true"
      :value="email"
      @update="$emit('update:email', $event)"
    />
  </div>
</template>

<script>
import FieldInput from '@/components/wrapper/FieldInput.vue';

export default {
  name: 'Grunddaten',
  components: { FieldInput },
  props: {
    nachname: {
      type: String,
      required: true,
    },
    vorname: {
      type: String,
      required: true,
    },
    geburtsdatum: {
      type: String,
      required: true,
    },
    strasse: {
      type: String,
      required: true,
    },
    hausNummer: {
      type: String,
      required: true,
    },
    postleitzahl: {
      type: String,
      required: true,
    },
    stadt: {
      type: String,
      required: true,
    },
    telefon: {
      type: String,
      required: true,
    },
    email: {
      type: String,
      required: true,
    },
  },
};
</script>

<style scoped>

</style>
