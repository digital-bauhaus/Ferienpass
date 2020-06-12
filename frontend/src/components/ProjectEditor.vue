<template>
  <b-form
    ref="form"
    novalidate
    :validated="showValidationStatus"
    @submit.prevent="onSubmit"
  >
    <b-form-row>
      <b-col sm="12">
        <FieldInput
          base="project-name"
          label="Name"
          :required="true"
          placeholder="Name der Veranstaltung"
          :value="value.name"
          @update="updateValue('name', $event)"
        />
      </b-col>

      <b-col sm="6">
        <DateInput
          base="project-beginDate"
          label="Beginndatum"
          :required="true"
          placeholder="Datum (TT.MM.JJJJ)"
          :value="value.datumBeginn"
          @update="updateValue('datumBeginn', $event)"
        />
      </b-col>
      <b-col sm="6">
        <DateInput
          base="project-endDate"
          label="Enddatum"
          :required="true"
          placeholder="Datum (TT.MM.JJJJ)"
          :value="value.datumEnde"
          @update="updateValue('datumEnde', $event)"
        />
      </b-col>

      <b-col sm="6">
        <FieldInput
          base="project-slotsTotal"
          type="number"
          label="Plätze (gesamt)"
          :required="true"
          placeholder="Plätze"
          :value="value.plaetzeGesamt"
          @update="updateValue('plaetzeGesamt', $event)"
        />
      </b-col>
      <b-col sm="6">
        <FieldInput
          base="project-slotsReserved"
          type="number"
          label="Plätze (reserviert)"
          placeholder="Reservierte Plätze"
          :value="value.plaetzeReserviert"
          @update="updateValue('plaetzeReserviert', $event)"
        />
      </b-col>

      <b-col sm="6">
        <FieldInput
          base="project-minAge"
          type="number"
          label="Mindestalter"
          :required="true"
          placeholder="Altersbegrenzung"
          :value="value.mindestAlter"
          @update="updateValue('mindestAlter', $event)"
        />
      </b-col>
      <b-col sm="6">
        <FieldInput
          base="project-maxAge"
          type="number"
          label="Höchstalter"
          :required="true"
          placeholder="Altersbegrenzung"
          :value="value.hoechstAlter"
          @update="updateValue('hoechstAlter', $event)"
        />
      </b-col>

      <b-col sm="6">
        <FieldInput
          base="project-group"
          label="Gruppe"
          placeholder="Name der Gruppe zu der diese Veranstaltung gehört"
          :value="value.gruppe"
          @update="updateValue('gruppe', $event)"
        />
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
import FieldInput from '@/components/form/FieldInput.vue';
import DateInput from '@/components/form/DateInput.vue';

export default {
  name: 'ProjectEditor',
  components: { DateInput, FieldInput },
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
