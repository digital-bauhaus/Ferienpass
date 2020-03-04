<template>
  <b-form
    ref="form"
    novalidate
    :validated="showValidationStatus"
    @submit="onSubmit"
  >
    <BaseSection
      :familiy-name="value.nachname"
      :first-name="value.vorname"
      :birth-date="value.geburtsdatum"
      :street="value.strasse"
      :street-number="TODO"
      :zip-code="value.postleitzahl"
      :city="value.stadt"
      :phone="value.telefon"
      :email="value.email"
    />
    <MandatorySection />
    <HealthSection />
    <DisabilitySection />
    <ProjectSelectionSection />
    <PrivacyPolicySection />
    <ParticipationConditionSection />
    <b-button
      type="submit"
      variant="primary"
    >
      {{ submitButtonText }}
    </b-button>
  </b-form>
</template>

<script>

import BaseSection from '@/components/userEditor/BaseSection.vue';
import MandatorySection from '@/components/userEditor/MandatorySection.vue';
import HealthSection from '@/components/userEditor/HealthSection.vue';
import DisabilitySection from '@/components/userEditor/DisabilitySection.vue';
import PrivacyPolicySection from '@/components/userEditor/PrivacyPolicySection.vue';
import ProjectSelectionSection from '@/components/userEditor/ProjectSelectionSection.vue';
import ParticipationConditionSection from '@/components/userEditor/ParticipationConditionSection.vue';

export default {
  name: 'UserEditor',
  components: {
    ParticipationConditionSection,
    ProjectSelectionSection,
    PrivacyPolicySection,
    DisabilitySection,
    HealthSection,
    MandatorySection,
    BaseSection,
  },
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
