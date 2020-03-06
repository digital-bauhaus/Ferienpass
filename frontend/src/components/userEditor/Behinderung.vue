<template>
  <div>
    <h2>Angaben bei Behinderung</h2>

    <CheckBox
      base="behinderung-liegtBehinderungVor"
      :required="true"
      :checked="liegtBehinderungVor"
      @input="$emit('update:liegtBehinderungVor', $event)"
    >
      Bei meinem Kind liegt eine Beeinträchtigung vor
    </CheckBox>

    <div v-if="liegtBehinderungVor">
      <CheckBox
        base="behinderung-rollstuhlNutzungNotwendig"
        :checked="rollstuhlNutzungNotwendig"
        @input="$emit('update:rollstuhlNutzungNotwendig', $event)"
      >
        Rollstuhlnutzung
      </CheckBox>

      <TextAreaInput
        base="behinderung-weitereHilfsmittel"
        label="Weitere Hilfsmittel beschreiben:"
        :value="weitereHilfsmittel"
        @update="$emit('update:weitereHilfsmittel', $event)"
      />

      <CheckBox
        base="behinderung-wertmarkeVorhanden"
        :checked="wertmarkeVorhanden"
        @input="$emit('update:wertmarkeVorhanden', $event)"
      >
        Wertmarke vorhanden
      </CheckBox>

      <CheckBox
        base="behinderung-begleitungNotwendig"
        :checked="begleitungNotwendig"
        @input="$emit('update:begleitungNotwendig', $event)"
      >
        Mein Kind hat im Schulalltag eine Schulbegleitung und benötigt auch während der
        Ferienfreizeit eine Begleitperson
      </CheckBox>

    </div>
  </div>
</template>

<script>
import CheckBox from '@/components/wrapper/CheckBox.vue';
import TextAreaInput from '@/components/wrapper/TextAreaInput.vue';

export default {
  name: 'Behinderung',
  components: { TextAreaInput, CheckBox },
  props: {
    liegtBehinderungVor: {
      type: Boolean,
      required: true,
    },
    rollstuhlNutzungNotwendig: {
      type: Boolean,
      required: true,
    },
    weitereHilfsmittel: {
      type: String,
      required: true,
    },
    wertmarkeVorhanden: {
      type: Boolean,
      required: true,
    },
    begleitungNotwendig: {
      type: Boolean,
      required: true,
    },
  },
};
</script>

<style scoped>

</style>
