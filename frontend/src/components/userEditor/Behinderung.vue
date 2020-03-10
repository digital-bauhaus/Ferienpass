<template>
  <div>
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

    <CheckBoxGroup
      base="behinderung-liegtBehinderungVor"
      :disabled="disabled"
    >
      <CheckBox
        base="behinderung-liegtBehinderungVor"
        :aria-expanded="liegtBehinderungVor"
        aria-controls="behinderung-collapse"
        :checked="liegtBehinderungVor"
        @input="$emit('update:liegtBehinderungVor', $event)"
      >
        Bei meinem Kind liegt eine Beeinträchtigung vor
      </CheckBox>
    </CheckBoxGroup>

    <b-collapse
      id="behinderung-collapse"
      :visible="liegtBehinderungVor"
    >
      <CheckBoxGroup
        base="behinderung-merkzeichen"
        label="Merkzeichen"
        :disabled="disabled"
      >
        <CheckBox
          base="behinderung-merkzeichenAussergewoehnlicheGehbehinderungaG"
          :checked="merkzeichenAussergewoehnlicheGehbehinderungaG"
          @input="$emit('update:merkzeichenAussergewoehnlicheGehbehinderungaG', $event)"
        >
          „aG“ (Außergewöhnliche Gehbehinderung)
        </CheckBox>
        <CheckBox
          base="behinderung-merkzeichenHilflosigkeitH"
          :checked="merkzeichenHilflosigkeitH"
          @input="$emit('update:merkzeichenHilflosigkeitH', $event)"
        >
          „H“ (Hilflosigkeit)
        </CheckBox>
        <CheckBox
          base="behinderung-merkzeichenBlindBl"
          :checked="merkzeichenBlindBl"
          @input="$emit('update:merkzeichenBlindBl', $event)"
        >
          „Bl“ (Blind)
        </CheckBox>
        <CheckBox
          base="behinderung-merkzeichenGehoerlosGl"
          :checked="merkzeichenGehoerlosGl"
          @input="$emit('update:merkzeichenGehoerlosGl', $event)"
        >
          „Gl“ (Gehörlos)
        </CheckBox>
        <CheckBox
          base="behinderung-merkzeichenBerechtigtZurMitnahmeEinerBegleitpersonB"
          :checked="merkzeichenBerechtigtZurMitnahmeEinerBegleitpersonB"
          @input="$emit('update:merkzeichenBerechtigtZurMitnahmeEinerBegleitpersonB', $event)"
        >
          „B“ (Berechtigung zur Mitnahme einer Begleitperson)
        </CheckBox>
        <CheckBox
          base="behinderung-merkzeichenBeeintraechtigungImStrassenverkehrG"
          :checked="merkzeichenBeeintraechtigungImStrassenverkehrG"
          @input="$emit('update:merkzeichenBeeintraechtigungImStrassenverkehrG', $event)"
        >
          „G“ (Erhebliche Beeinträchtigung der Bewegungsfähigkeit im Straßenverkehr)
        </CheckBox>
        <CheckBox
          base="behinderung-merkzeichenTaubblindTBL"
          :checked="merkzeichenTaubblindTBL"
          @input="$emit('update:merkzeichenTaubblindTBL', $event)"
        >
          „TBL“ (Taubblind)
        </CheckBox>
      </CheckBoxGroup>

      <CheckBoxGroup
        base="behinderung-rollstuhlNutzungNotwendig"
        label="Hilfsmittel"
        :disabled="disabled"
      >
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
        :disabled="disabled"
        :value="weitereHilfsmittel"
        @update="$emit('update:weitereHilfsmittel', $event)"
      />

      <CheckBoxGroup
        base="behinderung-wertmarkeVorhanden"
        label="Wertmarke"
        :disabled="disabled"
      >
        <CheckBox
          base="behinderung-wertmarkeVorhanden"
          :checked="wertmarkeVorhanden"
          @input="$emit('update:wertmarkeVorhanden', $event)"
        >
          Wertmarke vorhanden
        </CheckBox>
      </CheckBoxGroup>

      <CheckBoxGroup
        base="behinderung-begleitungNotwendig"
        label="Begleitperson"
        :disabled="disabled"
      >
        <CheckBox
          base="behinderung-begleitungNotwendig"
          :aria-expanded="begleitungNotwendig"
          aria-controls="behinderung-begleitperson-collapse"
          :checked="begleitungNotwendig"
          @input="$emit('update:begleitungNotwendig', $event)"
        >
          Mein Kind hat im Schulalltag eine Schulbegleitung und benötigt auch während der
          Ferienfreizeit eine Begleitperson
        </CheckBox>
      </CheckBoxGroup>

      <b-collapse
        id="behinderung-begleitperson-collapse"
        :visible="begleitungNotwendig"
      >
        <CheckBoxGroup
          base="behinderung-begleitperson"
          label="Wofür wird die Begleitperson benötigt?"
          :disabled="disabled"
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
            base="behinderung-isEingeschraenkteSinne"
            :aria-expanded="begleitpersonSinneswahrnehmung"
            aria-controls="behinderung-eingeschraenkteSinne-collapse"
            :checked="begleitpersonSinneswahrnehmung"
            @input="$emit('update:begleitpersonSinneswahrnehmung', $event)"
          >
            Sinneswahrnehmung
          </CheckBox>
        </CheckBoxGroup>

        <b-collapse
          id="behinderung-eingeschraenkteSinne-collapse"
          :visible="begleitpersonSinneswahrnehmung"
        >
          <FieldInput
            base="behinderung-eingeschraenkteSinne"
            label="Beeinträchtigte Sinneswahrnehmung angeben:"
            :disabled="disabled"
            :value="eingeschraenkteSinne"
            @update="$emit('update:eingeschraenkteSinne', $event)"
          />
        </b-collapse>

        <TextAreaInput
          base="behinderung-hinweiseZumUmgangMitDemKind"
          label="Darauf ist im Umgang mit meinem Kind unbedingt zu achten:"
          :disabled="disabled"
          :value="hinweiseZumUmgangMitDemKind"
          @update="$emit('update:hinweiseZumUmgangMitDemKind', $event)"
        />

        <CheckBoxGroup
          base="behinderung-unterstuetzungSucheBegleitperson"
          :disabled="disabled"
        >
          <CheckBox
            base="behinderung-unterstuetzungSucheBegleitperson"
            :aria-expanded="unterstuetzungSucheBegleitperson"
            aria-controls="behinderung-gewohnterBegleitpersonenDienstleister-collapse"
            :checked="unterstuetzungSucheBegleitperson"
            @input="$emit('update:unterstuetzungSucheBegleitperson', $event)"
          >
            Wir benötigen Unterstützung bei der Organisation der Begleitperson
          </CheckBox>
        </CheckBoxGroup>

        <b-collapse
          id="behinderung-gewohnterBegleitpersonenDienstleister-collapse"
          :visible="unterstuetzungSucheBegleitperson"
        >
          <TextAreaInput
            base="behinderung-gewohnterBegleitpersonenDienstleister"
            label="Welcher Dienst ist in der Regel für die Begleitung/Betreuung
               zuständig? (Bitte Kontaktdaten des Dienstes/der Dienste angeben):"
            :disabled="disabled"
            :value="gewohnterBegleitpersonenDienstleister"
            @update="$emit('update:gewohnterBegleitpersonenDienstleister', $event)"
          />
        </b-collapse>

        <CheckBoxGroup
          base="behinderung-beantragungKostenuebernahmeBegleitperson"
          :disabled="disabled"
        >
          <CheckBox
            base="behinderung-beantragungKostenuebernahmeBegleitperson"
            :checked="beantragungKostenuebernahmeBegleitperson"
            @input="$emit('update:beantragungKostenuebernahmeBegleitperson', $event)"
          >
            Hiermit beantrage ich die Übernahme der Kosten für die Begleitung/Betreuung o.g. Kindes
            während der Teilnahme an den Ferienfreizeiten gemäß der Anmeldung unter Punkt 5.
            Auf die Förderung einer Begleitperson für Ferienfreizeiten besteht kein Rechtsanspruch.
            Die bedürftigkeitsabhängige Prüfung erfolgt am Einzelfall durch das Amt für Familie und
            Soziales der Stadt Weimar. Voraussetzung hierfür ist, dass das Kind als Hauptwohnsitz in
            Weimar lebt.
            <br>
            Hiermit stimme ich außerdem zu, dass meine hier gemachten Angaben an das Amt für Familie
            und Soziales der Stadt Weimar weitergegeben werden dürfen.
          </CheckBox>
        </CheckBoxGroup>
      </b-collapse>
    </b-collapse>
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
    FieldInput,
    CheckBoxGroup,
    TextAreaInput,
    CheckBox,
  },
  props: {
    disabled: {
      type: Boolean,
      default: false,
    },
    liegtBehinderungVor: {
      type: Boolean,
      required: true,
    },
    merkzeichenAussergewoehnlicheGehbehinderungaG: {
      type: Boolean,
      required: true,
    },
    merkzeichenHilflosigkeitH: {
      type: Boolean,
      required: true,
    },
    merkzeichenBlindBl: {
      type: Boolean,
      required: true,
    },
    merkzeichenGehoerlosGl: {
      type: Boolean,
      required: true,
    },
    merkzeichenBerechtigtZurMitnahmeEinerBegleitpersonB: {
      type: Boolean,
      required: true,
    },
    merkzeichenBeeintraechtigungImStrassenverkehrG: {
      type: Boolean,
      required: true,
    },
    merkzeichenTaubblindTBL: {
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
    begleitpersonPflege: {
      type: Boolean,
      required: true,
    },
    begleitpersonMedizinischeVersorgung: {
      type: Boolean,
      required: true,
    },
    begleitpersonMobilitaet: {
      type: Boolean,
      required: true,
    },
    begleitpersonOrientierung: {
      type: Boolean,
      required: true,
    },
    begleitpersonSozialeBegleitung: {
      type: Boolean,
      required: true,
    },
    begleitpersonSinneswahrnehmung: {
      type: Boolean,
      required: true,
    },
    eingeschraenkteSinne: {
      type: String,
      required: true,
    },
    hinweiseZumUmgangMitDemKind: {
      type: String,
      required: true,
    },
    unterstuetzungSucheBegleitperson: {
      type: Boolean,
      required: true,
    },
    gewohnterBegleitpersonenDienstleister: {
      type: String,
      required: true,
    },
    beantragungKostenuebernahmeBegleitperson: {
      type: Boolean,
      required: true,
    },
  },
};
</script>

<style scoped>

</style>
