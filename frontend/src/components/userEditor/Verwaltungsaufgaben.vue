<template>
  <div>
    <!--read-only-->
    <FieldInput
      base="verwaltung-registrierungsdatum"
      type="date"
      label="Registrierungsdatum"
      :value="registrierungsdatum"
      :disabled="true"
    />

    <!--read-only-->
    <CheckBoxGroup base="verwaltung-datenschutzErklaerungAkzeptiert">
      <CheckBox
        base="verwaltung-datenschutzErklaerungAkzeptiert"
        :disabled="true"
        :checked="datenschutzErklaerungAkzeptiert"
      >
        Datenschutzerklärung akzeptiert
      </CheckBox>
    </CheckBoxGroup>

    <!--read-only-->
    <CheckBoxGroup base="verwaltung-teilnahmeBedingungAkzeptiert">
      <CheckBox
        base="verwaltung-teilnahmeBedingungAkzeptiert"
        :disabled="true"
        :checked="teilnahmeBedingungAkzeptiert"
      >
        Teilnahmebedingungen akzeptiert
      </CheckBox>
    </CheckBoxGroup>

    <CheckBoxGroup base="verwaltung-bezahlt">
      <CheckBox
        base="verwaltung-bezahlt"
        :checked="bezahlt"
        @input="$emit('update:bezahlt', $event)"
      >
        Hat Bezahlt
      </CheckBox>
    </CheckBoxGroup>
  </div>
</template>

<script>
import CheckBox from '@/components/form/CheckBox.vue';
import CheckBoxGroup from '@/components/form/CheckBoxGroup.vue';
import FieldInput from '@/components/form/FieldInput.vue';

export default {
  name: 'Verwaltungsaufgaben',
  components: {
    FieldInput,
    CheckBoxGroup,
    CheckBox,
  },
  props: {
    registrierungsdatum: {
      type: String,
      required: true,
    },
    datenschutzErklaerungAkzeptiert: {
      type: Boolean,
      required: true,
    },
    teilnahmeBedingungAkzeptiert: {
      type: Boolean,
      required: true,
    },
    bezahlt: {
      type: Boolean,
      required: true,
    },
  },
};
</script>

<style scoped>

</style>
