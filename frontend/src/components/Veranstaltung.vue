<template>
  <div>
    <div>
      Title
    </div>
    <b-form @submit="onSubmit">
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
