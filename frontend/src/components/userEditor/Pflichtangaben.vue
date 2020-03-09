<template>
  <div>
    <YesNoRadio
      base="pflichtangaben-darfBehandeltWerden"
      label="Behandlungserlaubnis bei Erkrankungen und Unfällen?"
      :required="true"
      :disabled="disabled"
      :checked="darfBehandeltWerden"
      @input="$emit('update:darfBehandeltWerden', $event)"
    />

    <YesNoRadio
      base="pflichtangaben-darfAlleinNachHause"
      label="Mein Kind darf alleine heimgehen?"
      :required="true"
      :disabled="disabled"
      no-text="Nein, es wird abgeholt"
      :checked="darfAlleinNachHause"
      @input="$emit('update:darfAlleinNachHause', $event)"
    />

    <YesNoRadio
      base="pflichtangaben-darfReiten"
      label="Mein Kind darf reiten?"
      :required="true"
      :disabled="disabled"
      :checked="darfReiten"
      @input="$emit('update:darfReiten', $event)"
    />

    <YesNoRadio
      base="pflichtangaben-darfSchwimmen"
      label="Mein Kind darf schwimmen?"
      :required="true"
      :disabled="disabled"
      :aria-expanded="darfSchwimmen"
      aria-controls="pflichtangaben-schwimmAbzeichen-collapse"
      :checked="darfSchwimmen"
      @input="$emit('update:darfSchwimmen', $event)"
    />

    <b-collapse
      id="pflichtangaben-schwimmAbzeichen-collapse"
      :visible="darfSchwimmen"
    >
      <FieldInput
        base="pflichtangaben-schwimmAbzeichen"
        label="Schwimmabzeichen"
        :required="darfSchwimmen"
        :disabled="disabled"
        :value="schwimmAbzeichen"
        @update="$emit('update:schwimmAbzeichen', $event)"
      />
    </b-collapse>

    <slot />
  </div>
</template>

<script>
import YesNoRadio from '@/components/wrapper/YesNoRadio.vue';
import FieldInput from '@/components/wrapper/FieldInput.vue';

export default {
  name: 'Pflichtangaben',
  components: { FieldInput, YesNoRadio },
  props: {
    disabled: {
      type: Boolean,
      default: false,
    },
    darfBehandeltWerden: {
      required: true,
      validator: (prop) => typeof prop === 'boolean' || prop === null,
    },
    darfAlleinNachHause: {
      required: true,
      validator: (prop) => typeof prop === 'boolean' || prop === null,
    },
    darfReiten: {
      required: true,
      validator: (prop) => typeof prop === 'boolean' || prop === null,
    },
    darfSchwimmen: {
      required: true,
      validator: (prop) => typeof prop === 'boolean' || prop === null,
    },
    schwimmAbzeichen: {
      type: String,
      required: true,
    },
  },
};
</script>

<style scoped>

</style>
