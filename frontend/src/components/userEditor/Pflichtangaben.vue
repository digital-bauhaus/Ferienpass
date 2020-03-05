<template>
  <div>
    <h2>Pflichtangaben</h2>

    <b-form-group
      id="pflichtangaben-darfBehandeltWerden"
      label="Behandlungserlaubnis bei Erkrankungen und Unfällen?"
    >
      <b-form-radio-group
        id="pflichtangaben-darfBehandeltWerden-value"
        name="pflichtangaben-darfBehandeltWerden"
        required
        stacked
        :checked="darfBehandeltWerden"
        @input="$emit('update:darfBehandeltWerden', $event)"
      >
        <b-form-radio :value="true">
          Ja
        </b-form-radio>
        <b-form-radio :value="false">
          Nein
        </b-form-radio>
      </b-form-radio-group>
    </b-form-group>

    <b-form-group
      id="pflichtangaben-darfAlleinNachHause"
      label="Mein Kind darf alleine heimgehen?"
    >
      <b-form-radio-group
        id="pflichtangaben-darfAlleinNachHause-value"
        name="pflichtangaben-darfAlleinNachHause"
        required
        stacked
        :checked="darfAlleinNachHause"
        @input="$emit('update:darfAlleinNachHause', $event)"
      >
        <b-form-radio :value="true">
          Ja
        </b-form-radio>
        <b-form-radio :value="false">
          Nein, es wird abgeholt
        </b-form-radio>
      </b-form-radio-group>
    </b-form-group>

    <b-form-group
      id="pflichtangaben-darfReiten"
      label="Mein Kind darf reiten?"
    >
      <b-form-radio-group
        id="pflichtangaben-darfReiten-value"
        name="pflichtangaben-darfReiten"
        required
        stacked
        :checked="darfReiten"
        @input="$emit('update:darfReiten', $event)"
      >
        <b-form-radio :value="true">
          Ja
        </b-form-radio>
        <b-form-radio :value="false">
          Nein
        </b-form-radio>
      </b-form-radio-group>
    </b-form-group>

    <b-form-group
      id="pflichtangaben-darfSchwimmen"
      label="Mein Kind darf schwimmen?"
    >
      <b-form-radio-group
        id="pflichtangaben-darfSchwimmen-value"
        name="pflichtangaben-darfSchwimmen"
        required
        stacked
        :checked="darfSchwimmen"
        @input="$emit('update:darfSchwimmen', $event)"
      >
        <b-form-radio :value="true">
          Ja
        </b-form-radio>
        <b-form-radio :value="false">
          Nein
        </b-form-radio>
      </b-form-radio-group>
    </b-form-group>

    <b-form-group
      v-if="darfSchwimmen"
      id="pflichtangaben-schwimmAbzeichen-group"
      label-for="pflichtangaben-schwimmAbzeichen-value"
      label="Schwimmabzeichen"
    >
      <b-form-input
        id="pflichtangaben-schwimmAbzeichen-value"
        required
        trim
        :value="schwimmAbzeichen"
        @update="$emit('update:schwimmAbzeichen', $event)"
      />
    </b-form-group>

  </div>
</template>

<script>
export default {
  name: 'Pflichtangaben',
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
