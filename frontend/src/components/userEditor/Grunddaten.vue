<template>
  <div>
    <h2>Grunddaten</h2>
    <b-form-group
      id="grunddaten-nachname-group"
      label-for="grunddaten-nachname-value"
      label="Familienname"
    >
      <b-form-input
        id="grunddaten-nachname-value"
        required
        trim
        :value="nachname"
        @update="$emit('update:nachname', $event)"
      />
    </b-form-group>

    <b-form-group
      id="grunddaten-vorname-group"
      label-for="grunddaten-vorname-value"
      label="Vorname meines Kindes"
    >
      <b-form-input
        id="grunddaten-vorname-value"
        required
        trim
        :value="vorname"
        @update="$emit('update:vorname', $event)"
      />
    </b-form-group>

    <div>
      TODO: geburtsdatum
    </div>

    <b-form-group
      id="grunddaten-strasse-group"
      label-for="grunddaten-strasse-value"
      label="Straße"
    >
      <b-form-input
        id="grunddaten-strasse-value"
        required
        trim
        :value="strasse"
        @update="$emit('update:strasse', $event)"
      />
    </b-form-group>

    <b-form-group
      id="grunddaten-hausNummer-group"
      label-for="grunddaten-hausNummer-value"
      label="Hausnummer"
    >
      <b-form-input
        id="grunddaten-hausNummer-value"
        required
        trim
        :value="hausNummer"
        @update="$emit('update:hausNummer', $event)"
      />
    </b-form-group>

    <b-form-group
      id="grunddaten-postleitzahl-group"
      label-for="grunddaten-postleitzahl-value"
      label="Postleitzahl"
    >
      <b-form-input
        id="grunddaten-postleitzahl-value"
        required
        trim
        :value="postleitzahl"
        @update="$emit('update:postleitzahl', $event)"
      />
    </b-form-group>

    <b-form-group
      id="grunddaten-stadt-group"
      label-for="grunddaten-stadt-value"
      label="Wohnort"
    >
      <b-form-input
        id="grunddaten-stadt-value"
        required
        trim
        :value="stadt"
        @update="$emit('update:stadt', $event)"
      />
    </b-form-group>

    <b-form-group
      id="grunddaten-telefon-group"
      label-for="grunddaten-telefon-value"
      label="Telefon"
    >
      <b-form-input
        id="grunddaten-telefon-value"
        type="tel"
        required
        trim
        :value="telefon"
        @update="$emit('update:telefon', $event)"
      />
    </b-form-group>

    <b-form-group
      id="grunddaten-email-group"
      label-for="grunddaten-email-value"
      label="eMail"
    >
      <b-form-input
        id="grunddaten-email-value"
        type="email"
        required
        trim
        :value="email"
        @update="$emit('update:email', $event)"
      />
    </b-form-group>
  </div>
</template>

<script>
export default {
  name: 'Grunddaten',
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
