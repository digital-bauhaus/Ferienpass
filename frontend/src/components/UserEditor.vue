<template>
  <b-form
    v-if="hasUser"
    ref="form"
    novalidate
    :validated="showValidationStatus"
    @submit="onSubmit"
  >
    <BaseSection
      :family-name="value.nachname"
      :first-name="value.vorname"
      :birth-date="value.geburtsdatum"
      :street="value.strasse"
      :street-number="TODO"
      :zip-code="value.postleitzahl"
      :city="value.stadt"
      :phone="value.telefon"
      :email="value.email"
      @update:familyName="updateValue('nachname', $event)"
      @update:firstName="updateValue('vorname', $event)"
      @update:birthDate="updateValue('geburtsdatum', $event)"
      @update:street="updateValue('strasse', $event)"
      @update:streetNumber="updateValue('TODO', $event)"
      @update:zipCode="updateValue('postleitzahl', $event)"
      @update:city="updateValue('stadt', $event)"
      @update:phone="updateValue('telefon', $event)"
      @update:email="updateValue('email', $event)"
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
  computed: {
    hasUser() {
      return this.value && this.value.id && this.value.id > 0;
    },
  },
  methods: {
    updateValue(propName, newValue) {
      console.log('foo');
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
