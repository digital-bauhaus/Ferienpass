<template>
  <div>
    <h2>Grunddaten</h2>

    <TextInput
      base="grunddaten-nachname"
      label="Familienname"
      :required="true"
      :value="nachname"
      @update="$emit('update:nachname', $event)"
    />

    <TextInput
      base="grunddaten-vorname"
      label="Vorname meines Kindes"
      :required="true"
      :value="vorname"
      @update="$emit('update:vorname', $event)"
    />

    <div>
      TODO: geburtsdatum
    </div>

    <TextInput
      base="grunddaten-strasse"
      label="Straße"
      :required="true"
      :value="strasse"
      @update="$emit('update:strasse', $event)"
    />

    <TextInput
      base="grunddaten-hausNummer"
      label="Hausnummer"
      :required="true"
      :value="hausNummer"
      @update="$emit('update:hausNummer', $event)"
    />

    <TextInput
      base="grunddaten-postleitzahl"
      label="Postleitzahl"
      :required="true"
      :value="postleitzahl"
      @update="$emit('update:postleitzahl', $event)"
    />

    <TextInput
      base="grunddaten-stadt"
      label="Wohnort"
      :required="true"
      :value="stadt"
      @update="$emit('update:stadt', $event)"
    />

    <TextInput
      base="grunddaten-telefon"
      label="Telefon"
      type="tel"
      :required="true"
      :value="telefon"
      @update="$emit('update:telefon', $event)"
    />

    <TextInput
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
import TextInput from '@/components/wrapper/TextInput.vue';

export default {
  name: 'Grunddaten',
  components: { TextInput },
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
