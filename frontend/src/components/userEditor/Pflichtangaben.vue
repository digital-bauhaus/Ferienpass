<template>
  <div>
    <h2>Pflichtangaben</h2>

    <YesNoRadio
      base="pflichtangaben-darfBehandeltWerden"
      label="Behandlungserlaubnis bei Erkrankungen und Unfällen?"
      :required="true"
      :checked="darfBehandeltWerden"
      @input="$emit('update:darfBehandeltWerden', $event)"
    />

    <YesNoRadio
      base="pflichtangaben-darfAlleinNachHause"
      label="Mein Kind darf alleine heimgehen?"
      :required="true"
      no-text="Nein, es wird abgeholt"
      :checked="darfAlleinNachHause"
      @input="$emit('update:darfAlleinNachHause', $event)"
    />

    <YesNoRadio
      base="pflichtangaben-darfReiten"
      label="Mein Kind darf reiten?"
      :required="true"
      :checked="darfReiten"
      @input="$emit('update:darfReiten', $event)"
    />

    <YesNoRadio
      base="pflichtangaben-darfSchwimmen"
      label="Mein Kind darf schwimmen?"
      :required="true"
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
        :required="true"
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
    darfBehandeltWerden: {
      type: Boolean,
      required: true,
    },
    darfAlleinNachHause: {
      type: Boolean,
      required: true,
    },
    darfReiten: {
      type: Boolean,
      required: true,
    },
    darfSchwimmen: {
      type: Boolean,
      required: true,
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
