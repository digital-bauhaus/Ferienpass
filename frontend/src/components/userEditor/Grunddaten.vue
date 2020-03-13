<template>
  <div>
    <FieldInput
      base="grunddaten-nachname"
      label="Familienname"
      :required="true"
      :disabled="disabled"
      :value="nachname"
      @update="$emit('update:nachname', $event)"
    />

    <FieldInput
      base="grunddaten-vorname"
      label="Vorname meines Kindes"
      :required="true"
      :disabled="disabled"
      :value="vorname"
      @update="$emit('update:vorname', $event)"
    />

    <DateInput
      base="grunddaten-geburtsdatum"
      label="Geburtsdatum meines Kindes"
      :required="true"
      :disabled="disabled"
      :value="geburtsdatum"
      @update="$emit('update:geburtsdatum', $event)"
    />

    <FieldInput
      base="grunddaten-strasse"
      label="Straße"
      :required="true"
      :disabled="disabled"
      :value="strasse"
      @update="$emit('update:strasse', $event)"
    />

    <FieldInput
      base="grunddaten-hausNummer"
      label="Hausnummer"
      :required="true"
      :disabled="disabled"
      :value="hausNummer"
      @update="$emit('update:hausNummer', $event)"
    />

    <FieldInput
      base="grunddaten-postleitzahl"
      label="Postleitzahl"
      :required="true"
      :disabled="disabled"
      :value="postleitzahl"
      @update="$emit('update:postleitzahl', $event)"
    />

    <FieldInput
      base="grunddaten-wohnort"
      label="Wohnort"
      :required="true"
      :disabled="disabled"
      :value="wohnort"
      @update="$emit('update:wohnort', $event)"
    />

    <FieldInput
      base="grunddaten-telefon"
      label="Telefon"
      type="tel"
      :required="true"
      :disabled="disabled"
      :value="telefon"
      @update="$emit('update:telefon', $event)"
    />

    <FieldInput
      base="grunddaten-email"
      label="eMail"
      type="email"
      :required="true"
      :disabled="disabled"
      :value="email"
      @update="$emit('update:email', $event)"
    />

    <CheckBoxGroup
      base="grunddaten-darfErmaessigtenPreisZahlen"
    >
      <CheckBox
        base="grunddaten-darfErmaessigtenPreisZahlen"
        :disabled="disabled"
        :checked="darfErmaessigtenPreisZahlen"
        @input="$emit('update:darfErmaessigtenPreisZahlen', $event)"
      >
        Ich bin berechtig den ermäßigten Preis zu zahlen. Ich beziehe finanzielle Leistungen des
        Sozialamtes (Wohngeld, Kinderzuschlag, Bildung- und Teilhabe) oder des
        Jobcenters (ALG II, Harz IV).
      </CheckBox>
    </CheckBoxGroup>
  </div>
</template>

<script>
import FieldInput from '@/components/form/FieldInput.vue';
import CheckBoxGroup from '@/components/form/CheckBoxGroup.vue';
import CheckBox from '@/components/form/CheckBox.vue';
import DateInput from '@/components/form/DateInput.vue';

export default {
  name: 'Grunddaten',
  components: {
    DateInput, CheckBox, CheckBoxGroup, FieldInput,
  },
  props: {
    disabled: {
      type: Boolean,
      default: false,
    },
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
    wohnort: {
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
    darfErmaessigtenPreisZahlen: {
      type: Boolean,
      required: true,
    },
  },
};
</script>

<style scoped>

</style>
