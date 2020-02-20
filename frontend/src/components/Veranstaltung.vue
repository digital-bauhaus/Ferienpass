<template>
  <div>
    <div>
      Title
    </div>
    <b-form
      @submit="onSubmit"
    >
      <b-form-group
        id="veranstaltung-name-group"
        label-for="veranstaltung-name-value"
        label="Name"
      >
        <b-form-input
          id="veranstaltung-name-value"
          v-model.trim="veranstaltung.name"
          required
          placeholder="Name der Veranstaltung"
        />
      </b-form-group>

      <b-form-group
        id="veranstaltung-begindate-group"
        label-for="veranstaltung-begindate-value"
        label="Beginndatum"
      >
        <b-form-input
          id="veranstaltung-begindate-value"
          v-model.trim="veranstaltung.datum"
          required
          placeholder="Datum (TT.MM.JJJ)"
          type="date"
        />
      </b-form-group>

      <b-form-group
        id="veranstaltung-enddate-group"
        label-for="veranstaltung-enddate-value"
        label="Enddatum"
      >
        <b-form-input
          id="veranstaltung-enddate-value"
          v-model.trim="veranstaltung.datumEnde"
          required
          placeholder="Datum (TT.MM.JJJ)"
          type="date"
        />
      </b-form-group>

      <b-form-group
        id="veranstaltung-slotstotal-group"
        label-for="veranstaltung-slotstotal-value"
        label="Plätze (gesamt)"
      >
        <b-form-input
          id="veranstaltung-slotstotal-value"
          v-model.trim="veranstaltung.slotsGesamt"
          required
          placeholder="Plätze"
          type="number"
        />
      </b-form-group>

      <b-form-group
        id="veranstaltung-slotsreserved-group"
        label-for="veranstaltung-slotsreserved-value"
        label="Plätze (reserviert)"
      >
        <b-form-input
          id="veranstaltung-slotsreserved-value"
          v-model.trim="veranstaltung.slotsReserviert"
          placeholder="Reservierte Plätze"
          type="number"
        />
      </b-form-group>

      <b-form-group
        id="veranstaltung-minage-group"
        label-for="veranstaltung-minage-value"
        label="Mindestalter"
      >
        <b-form-input
          id="veranstaltung-minage-value"
          v-model.trim="veranstaltung.mindestAlter"
          required
          placeholder="Altersbegrenzung"
          type="number"
        />
      </b-form-group>

      <b-form-group
        id="veranstaltung-maxage-group"
        label-for="veranstaltung-maxage-value"
        label="Höchstalter"
      >
        <b-form-input
          id="veranstaltung-maxage-value"
          v-model.trim="veranstaltung.hoechstAlter"
          required
          placeholder="Altersbegrenzung"
          type="number"
        />
      </b-form-group>

      <b-button
        type="submit"
        variant="primary"
      >
        Anlegen
      </b-button>
    </b-form>
  </div>
</template>

<script>
export default {
  name: 'Veranstaltung',
  props: {
    veranstaltung: {
      type: Object,
      required: true,
    },
  },
  methods: {
    onSubmit() {
    },
  },
};
</script>

<style scoped>

</style>
