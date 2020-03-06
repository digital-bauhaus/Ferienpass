<template>
  <b-form
    ref="form"
    novalidate
    :validated="showValidationStatus"
    @submit.prevent="onSubmit"
  >
    <b-form-row>
      <b-col sm="12">
        <b-form-group
          id="project-name-group"
          label-for="project-name-value"
          label="Name"
        >
          <b-form-input
            id="project-name-value"
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
          id="project-beginDate-group"
          label-for="project-beginDate-value"
          label="Beginndatum"
        >
          <b-form-input
            id="project-beginDate-value"
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
          id="project-endDate-group"
          label-for="project-endDate-value"
          label="Enddatum"
        >
          <b-form-input
            id="project-endDate-value"
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
          id="project-slotsTotal-group"
          label-for="project-slotsTotal-value"
          label="Plätze (gesamt)"
        >
          <b-form-input
            id="project-slotsTotal-value"
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
          id="project-slotsReserved-group"
          label-for="project-slotsReserved-value"
          label="Plätze (reserviert)"
        >
          <b-form-input
            id="project-slotsReserved-value"
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
          id="project-minAge-group"
          label-for="project-minAge-value"
          label="Mindestalter"
        >
          <b-form-input
            id="project-minAge-value"
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
          id="project-maxAge-group"
          label-for="project-maxAge-value"
          label="Höchstalter"
        >
          <b-form-input
            id="project-maxAge-value"
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
    updateValue(propName, newPropValue) {
      this.$emit('input', {
        ...this.value,
        [propName]: newPropValue,
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
