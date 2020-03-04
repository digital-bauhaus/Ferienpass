<template>
  <div>
    <b-form-group
      id="base-familiyName-group"
      label-for="base-familiyName-value"
      label="Familienname"
    >
      <b-form-input
        id="base-familiyName-value"
        required
        trim
        :value="familiyName"
        @update="$emit('update:familiyName', $event)"
      />
    </b-form-group>

    <b-form-group
      id="base-firstName-group"
      label-for="base-firstName-value"
      label="Vorname meines Kindes"
    >
      <b-form-input
        id="base-firstName-value"
        required
        trim
        :value="firstName"
        @update="$emit('update:firstName', $event)"
      />
    </b-form-group>

    <div>
      TODO: BIRTHDATE
    </div>

    <b-form-group
      id="base-street-group"
      label-for="base-street-value"
      label="Straße"
    >
      <b-form-input
        id="base-street-value"
        required
        trim
        :value="street"
        @update="$emit('update:street', $event)"
      />
    </b-form-group>

    <b-form-group
      id="base-streetNumber-group"
      label-for="base-streetNumber-value"
      label="Hausnummer"
    >
      <b-form-input
        id="base-streetNumber-value"
        required
        trim
        :value="streetNumber"
        @update="$emit('update:streetNumber', $event)"
      />
    </b-form-group>

    <b-form-group
      id="base-zipCode-group"
      label-for="base-zipCode-value"
      label="Postleitzahl"
    >
      <b-form-input
        id="base-zipCode-value"
        required
        trim
        :value="zipCode"
        @update="$emit('update:zipCode', $event)"
      />
    </b-form-group>

    <b-form-group
      id="base-city-group"
      label-for="base-city-value"
      label="Wohnort"
    >
      <b-form-input
        id="base-city-value"
        required
        trim
        :value="city"
        @update="$emit('update:city', $event)"
      />
    </b-form-group>

    <b-form-group
      id="base-phone-group"
      label-for="base-phone-value"
      label="Telefon"
    >
      <b-form-input
        id="base-phone-value"
        type="tel"
        required
        trim
        :value="phone"
        @update="$emit('update:phone', $event)"
      />
    </b-form-group>

    <b-form-group
      id="base-email-group"
      label-for="base-email-value"
      label="eMail"
    >
      <b-form-input
        id="base-email-value"
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
  name: 'BaseSection',
  props: {
    familiyName: {
      type: String,
      required: true,
    },
    firstName: {
      type: String,
      required: true,
    },
    birthDate: {
      type: String,
      required: true,
    },
    street: {
      type: String,
      required: true,
    },
    streetNumber: {
      type: String,
      required: true,
    },
    zipCode: {
      type: String,
      required: true,
    },
    city: {
      type: String,
      required: true,
    },
    phone: {
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
