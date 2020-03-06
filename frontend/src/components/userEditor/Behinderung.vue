<template>
  <div>
    <h2>Angaben bei Behinderung</h2>

    <p>
      Im Ferienpass sollen möglichst alle Kinder, egal ob mit oder ohne Behinderung,
      die Möglichkeit für tolle Ferien geboten bekommen. Leider sind nicht alle Angebote des
      Ferienpasses für alle Kinder mit körperlichen oder geistigen Einschränkungen geeignet.
      Sorgeberechtigte prüfen, ob das Angebot und die räumlichen Gegebenheiten den Fähigkeiten
      des Kindes entsprechen. Ein Elterngespräch zur Abklärung räumlicher Voraussetzung ist
      unbedingt zu empfehlen.
      <br><br>
      Bitte kontaktieren Sie uns direkt, wenn Ihr Kind eine Begleitung benötigt und Sie dafür
      finanzielle Unterstützung beantragen möchten.
    </p>

    <CheckBoxGroup base="behinderung-liegtBehinderungVor">
      <CheckBox
        base="behinderung-liegtBehinderungVor"
        :required="true"
        :checked="liegtBehinderungVor"
        @input="$emit('update:liegtBehinderungVor', $event)"
      >
        Bei meinem Kind liegt eine Beeinträchtigung vor
      </CheckBox>
    </CheckBoxGroup>

    <div v-if="liegtBehinderungVor">
      <CheckBoxGroup
        base="behinderung-merkzeichen"
        label="Merkzeichen"
      >
        <CheckBox
          base="behinderung-merkzeichen_AussergewoehnlicheGehbehinderung_aG"
          :checked="merkzeichen_AussergewoehnlicheGehbehinderung_aG"
          @input="$emit('update:merkzeichen_AussergewoehnlicheGehbehinderung_aG', $event)"
        >
          „aG“ (Außergewöhnliche Gehbehinderung)
        </CheckBox>
        <CheckBox
          base="behinderung-merkzeichen_Hilflosigkeit_H"
          :checked="merkzeichen_Hilflosigkeit_H"
          @input="$emit('update:merkzeichen_Hilflosigkeit_H', $event)"
        >
          „H“ (Hilflosigkeit)
        </CheckBox>
        <CheckBox
          base="behinderung-merkzeichen_Blind_Bl"
          :checked="merkzeichen_Blind_Bl"
          @input="$emit('update:merkzeichen_Blind_Bl', $event)"
        >
          „Bl“ (Blind)
        </CheckBox>
        <CheckBox
          base="behinderung-merkzeichen_Gehoerlos_Gl"
          :checked="merkzeichen_Gehoerlos_Gl"
          @input="$emit('update:merkzeichen_Gehoerlos_Gl', $event)"
        >
          „Gl“ (Gehörlos)
        </CheckBox>
        <CheckBox
          base="behinderung-merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B"
          :checked="merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B"
          @input="$emit('update:merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B', $event)"
        >
          „B“ (Berechtigung zur Mitnahme einer Begleitperson)
        </CheckBox>
        <CheckBox
          base="behinderung-merkzeichen_BeeintraechtigungImStrassenverkehr_G"
          :checked="merkzeichen_BeeintraechtigungImStrassenverkehr_G"
          @input="$emit('update:merkzeichen_BeeintraechtigungImStrassenverkehr_G', $event)"
        >
          „G“ (Erhebliche Beeinträchtigung der Bewegungsfähigkeit im Straßenverkehr)
        </CheckBox>
        <CheckBox
          base="behinderung-merkzeichen_Taubblind_TBL"
          :checked="merkzeichen_Taubblind_TBL"
          @input="$emit('update:merkzeichen_Taubblind_TBL', $event)"
        >
          „TBL“ (Taubblind)
        </CheckBox>
      </CheckBoxGroup>

      <CheckBoxGroup base="behinderung-rollstuhlNutzungNotwendig">
        <CheckBox
          base="behinderung-rollstuhlNutzungNotwendig"
          :checked="rollstuhlNutzungNotwendig"
          @input="$emit('update:rollstuhlNutzungNotwendig', $event)"
        >
          Rollstuhlnutzung
        </CheckBox>
      </CheckBoxGroup>

      <TextAreaInput
        base="behinderung-weitereHilfsmittel"
        label="Weitere Hilfsmittel beschreiben:"
        :value="weitereHilfsmittel"
        @update="$emit('update:weitereHilfsmittel', $event)"
      />

      <CheckBoxGroup base="behinderung-wertmarkeVorhanden">
        <CheckBox
          base="behinderung-wertmarkeVorhanden"
          :checked="wertmarkeVorhanden"
          @input="$emit('update:wertmarkeVorhanden', $event)"
        >
          Wertmarke vorhanden
        </CheckBox>
      </CheckBoxGroup>

      <CheckBoxGroup base="behinderung-begleitungNotwendig">
        <CheckBox
          base="behinderung-begleitungNotwendig"
          :checked="begleitungNotwendig"
          @input="$emit('update:begleitungNotwendig', $event)"
        >
          Mein Kind hat im Schulalltag eine Schulbegleitung und benötigt auch während der
          Ferienfreizeit eine Begleitperson
        </CheckBox>
      </CheckBoxGroup>

      <CheckBoxGroup
        base="behinderung-begleitperson"
        label="Wofür wird die Begleitperson benötigt?"
      >
        <CheckBox
          base="behinderung-begleitpersonPflege"
          :checked="begleitpersonPflege"
          @input="$emit('update:begleitpersonPflege', $event)"
        >
          Pflege
        </CheckBox>
        <CheckBox
          base="behinderung-begleitpersonMedizinischeVersorgung"
          :checked="begleitpersonMedizinischeVersorgung"
          @input="$emit('update:begleitpersonMedizinischeVersorgung', $event)"
        >
          Medizinische Versorgung
        </CheckBox>
        <CheckBox
          base="behinderung-begleitpersonMobilitaet"
          :checked="begleitpersonMobilitaet"
          @input="$emit('update:begleitpersonMobilitaet', $event)"
        >
          Mobilität
        </CheckBox>
        <CheckBox
          base="behinderung-begleitpersonOrientierung"
          :checked="begleitpersonOrientierung"
          @input="$emit('update:begleitpersonOrientierung', $event)"
        >
          Orientierung
        </CheckBox>
        <CheckBox
          base="behinderung-begleitpersonSozialeBegleitung"
          :checked="begleitpersonSozialeBegleitung"
          @input="$emit('update:begleitpersonSozialeBegleitung', $event)"
        >
          Soziale Begleitung
        </CheckBox>
        <CheckBox
          v-model="isEingeschraenkteSinne"
          base="behinderung-isEingeschraenkteSinne"
        >
          Sinneswahrnehmung
        </CheckBox>
      </CheckBoxGroup>

      <FieldInput
        v-if="isEingeschraenkteSinne"
        base="behinderung-eingeschraenkteSinne"
        label="Beeinträchtigte Sinneswahrnehmung angeben:"
        :value="eingeschraenkteSinne"
        @update="$emit('update:eingeschraenkteSinne', $event)"
      />

      <TextAreaInput
        base="behinderung-hinweiseZumUmgangMitDemKind"
        label="Darauf ist im Umgang mit meinem Kind unbedingt zu achten:"
        :value="hinweiseZumUmgangMitDemKind"
        @update="$emit('update:hinweiseZumUmgangMitDemKind', $event)"
      />

      <CheckBoxGroup base="behinderung-unterstuetzungSucheBegleitpersonNotwendig">
        <CheckBox
          base="behinderung-unterstuetzungSucheBegleitpersonNotwendig"
          :checked="unterstuetzungSucheBegleitpersonNotwendig"
          @input="$emit('update:unterstuetzungSucheBegleitpersonNotwendig', $event)"
        >
          Wir benötigen Unterstützung bei der Organisation der Begleitperson
        </CheckBox>
      </CheckBoxGroup>

      <TextAreaInput
        v-if="unterstuetzungSucheBegleitpersonNotwendig"
        base="behinderung-gewohnterBegleitpersonenDienstleister"
        label="Welcher Dienst ist in der Regel für die Begleitung/Betreuung
               zuständig? (Bitte Kontaktdaten des Dienstes/der Dienste angeben):"
        :value="gewohnterBegleitpersonenDienstleister"
        @update="$emit('update:gewohnterBegleitpersonenDienstleister', $event)"
      />

      <CheckBoxGroup base="behinderung-beantragungKostenuebernahmeBegleitpersonNotwendig">
        <CheckBox
          base="behinderung-beantragungKostenuebernahmeBegleitpersonNotwendig"
          :checked="beantragungKostenuebernahmeBegleitpersonNotwendig"
          @input="$emit('update:beantragungKostenuebernahmeBegleitpersonNotwendig', $event)"
        >
          Hiermit beantrage ich die Übernahme der Kosten für die Begleitung/Betreuung o.g. Kindes
          während der Teilnahme an den Ferienfreizeiten gemäß der Anmeldung unter Punkt TODO.
          Auf die Förderung einer Begleitperson für Ferienfreizeiten besteht kein Rechtsanspruch.
          Die bedürftigkeitsabhängige Prüfung erfolgt am Einzelfall durch das Amt für Familie und
          Soziales der Stadt Weimar. Voraussetzung hierfür ist, dass das Kind als Hauptwohnsitz in
          Weimar lebt.
          <br>
          Hiermit stimme ich außerdem zu, dass meine hier gemachten Angaben an das Amt für Familie
          und Soziales der Stadt Weimar weitergegeben werden dürfen.
        </CheckBox>
      </CheckBoxGroup>
    </div>
  </div>
</template>

<script>
import CheckBox from '@/components/wrapper/CheckBox.vue';
import TextAreaInput from '@/components/wrapper/TextAreaInput.vue';
import CheckBoxGroup from '@/components/wrapper/CheckBoxGroup.vue';
import FieldInput from '@/components/wrapper/FieldInput.vue';

export default {
  name: 'Behinderung',
  components: {
    FieldInput, CheckBoxGroup, TextAreaInput, CheckBox,
  },
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
  data() {
    return {
      isEingeschraenkteSinne: false,
    };
  },
};
</script>

<style scoped>

</style>
