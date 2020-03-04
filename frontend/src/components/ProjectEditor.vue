<template>
  <b-form
    ref="form"
    novalidate
    :validated="showValidationStatus"
    @submit="onSubmit"
  >
    <b-form-row>
      <b-col sm="12">
        <b-form-group
          id="veranstaltung-name-group"
          label-for="veranstaltung-name-value"
          label="Name"
        >
          <b-form-input
            id="veranstaltung-name-value"
            required
            trim
            placeholder="Name der Veranstaltung"
            :value="value.name"
            @update="updateValue('name', $event)"
          />
        </b-form-group>
      </b-col>

      <b-col sm="6">
        <b-form-group
          id="veranstaltung-begindate-group"
          label-for="veranstaltung-begindate-value"
          label="Beginndatum"
        >
          <b-form-input
            id="veranstaltung-begindate-value"
            type="date"
            required
            placeholder="Datum (TT.MM.JJJ)"
            :value="value.datum"
            @update="updateValue('datum', $event)"
          />
        </b-form-group>
      </b-col>
      <b-col sm="6">
        <b-form-group
          id="veranstaltung-enddate-group"
          label-for="veranstaltung-enddate-value"
          label="Enddatum"
        >
          <b-form-input
            id="veranstaltung-enddate-value"
            type="date"
            placeholder="Datum (TT.MM.JJJ)"
            required
            :value="value.datumEnde"
            @update="updateValue('datumEnde', $event)"
          />
        </b-form-group>
      </b-col>

      <b-col sm="6">
        <b-form-group
          id="veranstaltung-slotstotal-group"
          label-for="veranstaltung-slotstotal-value"
          label="Plätze (gesamt)"
        >
          <b-form-input
            id="veranstaltung-slotstotal-value"
            type="number"
            required
            trim
            placeholder="Plätze"
            :value="value.slotsGesamt"
            @update="updateValue('slotsGesamt', $event)"
          />
        </b-form-group>
      </b-col>
      <b-col sm="6">
        <b-form-group
          id="veranstaltung-slotsreserved-group"
          label-for="veranstaltung-slotsreserved-value"
          label="Plätze (reserviert)"
        >
          <b-form-input
            id="veranstaltung-slotsreserved-value"
            type="number"
            trim
            placeholder="Reservierte Plätze"
            :value="value.slotsReserviert"
            @update="updateValue('slotsReserviert', $event)"
          />
        </b-form-group>
      </b-col>

      <b-col sm="6">
        <b-form-group
          id="veranstaltung-minage-group"
          label-for="veranstaltung-minage-value"
          label="Mindestalter"
        >
          <b-form-input
            id="veranstaltung-minage-value"
            type="number"
            required
            trim
            placeholder="Altersbegrenzung"
            :value="value.mindestAlter"
            @update="updateValue('mindestAlter', $event)"
          />
        </b-form-group>
      </b-col>
      <b-col sm="6">
        <b-form-group
          id="veranstaltung-maxage-group"
          label-for="veranstaltung-maxage-value"
          label="Höchstalter"
        >
          <b-form-input
            id="veranstaltung-maxage-value"
            type="number"
            required
            trim
            placeholder="Altersbegrenzung"
            :value="value.hoechstAlter"
            @update="updateValue('hoechstAlter', $event)"
          />
        </b-form-group>
      </b-col>
    </b-form-row>

    <b-button
      type="submit"
      variant="primary"
    >
      {{ submitButtonText }}
    </b-button>
  </b-form>
</template>

<script>

export default {
  name: 'ProjectEditor',
  props: {
    value: {
      type: Object,
      required: true,
    },
    submitButtonText: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      showValidationStatus: false,
    };
  },
  methods: {
    updateValue(propName, newValue) {
      this.$emit('input', {
        ...this.value,
        [propName]: newValue,
      });
    },
    onSubmit() {
      this.showValidationStatus = true;
      if (this.$refs.form.checkValidity()) {
        this.$emit('submit');
      } else {
        this.$refs.form.reportValidity();
      }
    },
  },
};
</script>

<style scoped>
</style>
