<template>
  <b-form
    ref="form"
    class="user-editor"
    novalidate
    :validated="showValidationStatus"
    @submit.prevent="onSubmit"
    @keydown.enter="preventAccidentalSubmit"
  >
    <!-- eslint-disable max-len -->

    <FormSection
      v-if="isAdminView"
      label="Verwaltungsaufgaben"
    >
      <Verwaltungsaufgaben />
    </FormSection>

    <FormSection label="Grunddaten">
      <Grunddaten
        :disabled="disabled"
        :nachname="value.nachname"
        :vorname="value.vorname"
        :geburtsdatum="value.geburtsdatum"
        :strasse="value.strasse"
        :haus-nummer="value.hausnummer"
        :postleitzahl="value.postleitzahl"
        :wohnort="value.wohnort"
        :telefon="value.telefon"
        :email="value.email"
        :darf-ermaessigten-preis-zahlen="value.darfErmaessigtenPreisZahlen"
        @update:nachname="updateValue('nachname', $event)"
        @update:vorname="updateValue('vorname', $event)"
        @update:geburtsdatum="updateValue('geburtsdatum', $event)"
        @update:strasse="updateValue('strasse', $event)"
        @update:hausNummer="updateValue('hausnummer', $event)"
        @update:postleitzahl="updateValue('postleitzahl', $event)"
        @update:wohnort="updateValue('wohnort', $event)"
        @update:telefon="updateValue('telefon', $event)"
        @update:email="updateValue('email', $event)"
        @update:darfErmaessigtenPreisZahlen="updateValue('darfErmaessigtenPreisZahlen', $event)"
      />
    </FormSection>

    <FormSection label="Pflichtangaben">
      <Pflichtangaben
        :disabled="disabled"
        :darf-behandelt-werden="value.darfBehandeltWerden"
        :darf-allein-nach-hause="value.darfAlleinNachHause"
        :darf-reiten="value.darfReiten"
        :darf-schwimmen="value.darfSchwimmen"
        :schwimm-abzeichen="value.schwimmAbzeichen"
        @update:darfBehandeltWerden="updateValue('darfBehandeltWerden', $event)"
        @update:darfAlleinNachHause="updateValue('darfAlleinNachHause', $event)"
        @update:darfReiten="updateValue('darfReiten', $event)"
        @update:darfSchwimmen="updateValue('darfSchwimmen', $event)"
        @update:schwimmAbzeichen="updateValue('schwimmAbzeichen', $event)"
      >
        <Group label="In Notfällen zu informieren">
          <Kontakt
            base="notfallkontakt"
            :disabled="disabled"
            :telefon-required="true"
            :name="value.notfallKontakt.name"
            :anschrift="value.notfallKontakt.anschrift"
            :telefon="value.notfallKontakt.telefon"
            @update:name="updateValue('notfallKontakt.name', $event)"
            @update:anschrift="updateValue('notfallKontakt.anschrift', $event)"
            @update:telefon="updateValue('notfallKontakt.telefon', $event)"
          />
        </Group>
      </Pflichtangaben>
    </FormSection>

    <FormSection label="Allergien, Krankheiten, ...">
      <Gesundheit
        :disabled="disabled"
        :allergien="value.allergien"
        :krankheiten="value.krankheiten"
        :medikamente="value.medikamente"
        :krankenkasse="value.krankenkasse"
        :hitzeempfindlich="value.hitzeempfindlich"
        :essen-vegetarier="value.essenVegetarier"
        :essen-laktose-unvertraeglichkeit="value.essenLaktoseUnvertraeglichkeit"
        :essen-eier-unvertraeglichkeit="value.essenEierUnvertraeglichkeit"
        :essen-weitere-limitierungen="value.essenWeitereLimitierungen"
        @update:allergien="updateValue('allergien', $event)"
        @update:krankheiten="updateValue('krankheiten', $event)"
        @update:medikamente="updateValue('medikamente', $event)"
        @update:krankenkasse="updateValue('krankenkasse', $event)"
        @update:hitzeempfindlich="updateValue('hitzeempfindlich', $event)"
        @update:essenVegetarier="updateValue('essenVegetarier', $event)"
        @update:essenLaktoseUnvertraeglichkeit="updateValue('essenLaktoseUnvertraeglichkeit', $event)"
        @update:essenEierUnvertraeglichkeit="updateValue('essenEierUnvertraeglichkeit', $event)"
        @update:essenWeitereLimitierungen="updateValue('essenWeitereLimitierungen', $event)"
      >
        <Group label="Hausarzt">
          <Kontakt
            base="hausarzt"
            :disabled="disabled"
            :name="value.arzt.name"
            :anschrift="value.arzt.anschrift"
            :telefon="value.arzt.telefon"
            @update:name="updateValue('arzt.name', $event)"
            @update:anschrift="updateValue('arzt.anschrift', $event)"
            @update:telefon="updateValue('arzt.telefon', $event)"
          />
        </Group>
      </Gesundheit>
    </FormSection>

    <FormSection label="Angaben bei Behinderung">
      <Behinderung
        :disabled="disabled"
        :liegt-behinderung-vor="value.liegtBehinderungVor"
        :merkzeichen-aussergewoehnliche-gehbehinderunga-g="value.behinderung.merkzeichen_AussergewoehnlicheGehbehinderung_aG"
        :merkzeichen-hilflosigkeit-h="value.behinderung.merkzeichen_Hilflosigkeit_H"
        :merkzeichen-blind-bl="value.behinderung.merkzeichen_Blind_Bl"
        :merkzeichen-gehoerlos-gl="value.behinderung.merkzeichen_Gehoerlos_Gl"
        :merkzeichen-berechtigt-zur-mitnahme-einer-begleitperson-b="value.behinderung.merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B"
        :merkzeichen-beeintraechtigung-im-strassenverkehr-g="value.behinderung.merkzeichen_BeeintraechtigungImStrassenverkehr_G"
        :merkzeichen-taubblind-t-b-l="value.behinderung.merkzeichen_Taubblind_TBL"
        :rollstuhl-nutzung-notwendig="value.behinderung.rollstuhlNutzungNotwendig"
        :weitere-hilfsmittel="value.behinderung.weitereHilfsmittel"
        :wertmarke-vorhanden="value.behinderung.wertmarkeVorhanden"
        :begleitung-notwendig="value.behinderung.begleitungNotwendig"
        :begleitperson-pflege="value.behinderung.begleitpersonPflege"
        :begleitperson-medizinische-versorgung="value.behinderung.begleitpersonMedizinischeVersorgung"
        :begleitperson-mobilitaet="value.behinderung.begleitpersonMobilitaet"
        :begleitperson-orientierung="value.behinderung.begleitpersonOrientierung"
        :begleitperson-soziale-begleitung="value.behinderung.begleitpersonSozialeBegleitung"
        :begleitperson-sinneswahrnehmung="value.behinderung.begleitpersonSinneswahrnehmung"
        :eingeschraenkte-sinne="value.behinderung.eingeschraenkteSinne"
        :hinweise-zum-umgang-mit-dem-kind="value.behinderung.hinweiseZumUmgangMitDemKind"
        :unterstuetzung-suche-begleitperson="value.behinderung.unterstuetzungSucheBegleitperson"
        :gewohnter-begleitpersonen-dienstleister="value.behinderung.gewohnterBegleitpersonenDienstleister"
        :beantragung-kostenuebernahme-begleitperson="value.behinderung.beantragungKostenuebernahmeBegleitperson"
        :zustimmung-weitergabe-daten-amt-familie-und-soziales="value.behinderung.zustimmungWeitergabeDatenAmtFamilieUndSoziales"
        @update:liegtBehinderungVor="updateValue('liegtBehinderungVor', $event)"
        @update:merkzeichenAussergewoehnlicheGehbehinderungaG="updateValue('behinderung.merkzeichen_AussergewoehnlicheGehbehinderung_aG', $event)"
        @update:merkzeichenHilflosigkeitH="updateValue('behinderung.merkzeichen_Hilflosigkeit_H', $event)"
        @update:merkzeichenBlindBl="updateValue('behinderung.merkzeichen_Blind_Bl', $event)"
        @update:merkzeichenGehoerlosGl="updateValue('behinderung.merkzeichen_Gehoerlos_Gl', $event)"
        @update:merkzeichenBerechtigtZurMitnahmeEinerBegleitpersonB="updateValue('behinderung.merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B', $event)"
        @update:merkzeichenBeeintraechtigungImStrassenverkehrG="updateValue('behinderung.merkzeichen_BeeintraechtigungImStrassenverkehr_G', $event)"
        @update:merkzeichenTaubblindTBL="updateValue('behinderung.merkzeichen_Taubblind_TBL', $event)"
        @update:rollstuhlNutzungNotwendig="updateValue('behinderung.rollstuhlNutzungNotwendig', $event)"
        @update:weitereHilfsmittel="updateValue('behinderung.weitereHilfsmittel', $event)"
        @update:wertmarkeVorhanden="updateValue('behinderung.wertmarkeVorhanden', $event)"
        @update:begleitungNotwendig="updateValue('behinderung.begleitungNotwendig', $event)"
        @update:begleitpersonPflege="updateValue('behinderung.begleitpersonPflege', $event)"
        @update:begleitpersonMedizinischeVersorgung="updateValue('behinderung.begleitpersonMedizinischeVersorgung', $event)"
        @update:begleitpersonMobilitaet="updateValue('behinderung.begleitpersonMobilitaet', $event)"
        @update:begleitpersonOrientierung="updateValue('behinderung.begleitpersonOrientierung', $event)"
        @update:begleitpersonSozialeBegleitung="updateValue('behinderung.begleitpersonSozialeBegleitung', $event)"
        @update:begleitpersonSinneswahrnehmung="updateValue('behinderung.begleitpersonSinneswahrnehmung', $event)"
        @update:eingeschraenkteSinne="updateValue('behinderung.eingeschraenkteSinne', $event)"
        @update:hinweiseZumUmgangMitDemKind="updateValue('behinderung.hinweiseZumUmgangMitDemKind', $event)"
        @update:unterstuetzungSucheBegleitperson="updateValue('behinderung.unterstuetzungSucheBegleitperson', $event)"
        @update:gewohnterBegleitpersonenDienstleister="updateValue('behinderung.gewohnterBegleitpersonenDienstleister', $event)"
        @update:beantragungKostenuebernahmeBegleitperson="updateValue('behinderung.beantragungKostenuebernahmeBegleitperson', $event)"
        @update:zustimmungWeitergabeDatenAmtFamilieUndSoziales="updateValue('behinderung.zustimmungWeitergabeDatenAmtFamilieUndSoziales', $event)"
      />
    </FormSection>

    <FormSection
      v-if="!isAdminView"
      label="Angebote"
    >
      <Angebote>
        <slot />
      </Angebote>
    </FormSection>

    <FormSection
      v-if="!isAdminView"
      label="Datenschutzerklärung"
    >
      <Datenschutz />
    </FormSection>

    <FormSection
      v-if="!isAdminView"
      label="Teilnahmebedingungen"
    >
      <Teilnahmebedingungen />
    </FormSection>

    <CheckBoxGroup
      v-if="!isAdminView"
      base="akzeptanz"
    >
      <CheckBox
        base="datenschutzErklaerungAkzeptiert"
        :required="true"
        :disabled="disabled"
        :checked="value.datenschutzErklaerungAkzeptiert"
        @input="updateValue('datenschutzErklaerungAkzeptiert', $event)"
      >
        Ich habe die Datenschutzerklärung gelesen und akzeptiert. *
      </CheckBox>
      <CheckBox
        base="teilnahmeBedingungAkzeptiert"
        :required="true"
        :disabled="disabled"
        :checked="value.teilnahmeBedingungAkzeptiert"
        @input="updateValue('teilnahmeBedingungAkzeptiert', $event)"
      >
        Ich habe die Teilnahmebedingungen gelesen und akzeptiert. Ich bestätige die Richtigkeit meiner
        Angaben. Wurden wissentlich falsche Angaben gemacht, darf die Organisation das angemeldete
        Kind von den Angeboten ausschließen. *
      </CheckBox>
    </CheckBoxGroup>

    <b-button
      type="submit"
      variant="primary"
      class="mb-3"
      :disabled="disabled"
    >
      {{ submitButtonText }}
    </b-button>
  </b-form>
</template>

<script>
import _ from 'lodash-es';
import Grunddaten from '@/components/userEditor/Grunddaten.vue';
import Pflichtangaben from '@/components/userEditor/Pflichtangaben.vue';
import Gesundheit from '@/components/userEditor/Gesundheit.vue';
import Behinderung from '@/components/userEditor/Behinderung.vue';
import Datenschutz from '@/components/userEditor/Datenschutz.vue';
import Angebote from '@/components/userEditor/Angebote.vue';
import Teilnahmebedingungen from '@/components/userEditor/Teilnahmebedingungen.vue';
import Kontakt from '@/components/userEditor/Kontakt.vue';
import CheckBox from '@/components/wrapper/CheckBox.vue';
import CheckBoxGroup from '@/components/wrapper/CheckBoxGroup.vue';
import Group from '@/components/wrapper/Group.vue';
import FormSection from '@/components/wrapper/FormSection.vue';
import Verwaltungsaufgaben from '@/components/userEditor/Verwaltungsaufgaben.vue';

export default {
  name: 'UserEditor',
  components: {
    Verwaltungsaufgaben,
    FormSection,
    CheckBoxGroup,
    CheckBox,
    Kontakt,
    Teilnahmebedingungen,
    Angebote,
    Datenschutz,
    Behinderung,
    Gesundheit,
    Pflichtangaben,
    Grunddaten,
    Group,
  },
  props: {
    isAdminView: {
      type: Boolean,
      default: false,
    },
    value: {
      type: Object,
      required: true,
    },
    submitButtonText: {
      type: String,
      required: true,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      showValidationStatus: false,
    };
  },
  methods: {
    updateValue(propName, newPropValue) {
      // since user is a nested object, we have to be more careful when updating values
      const newValue = _.cloneDeep(this.value);
      _.set(newValue, propName, newPropValue);
      this.$emit('input', newValue);
    },
    onSubmit() {
      this.showValidationStatus = true;
      if (this.$refs.form.checkValidity()) {
        this.$emit('submit');
      } else {
        this.$refs.form.reportValidity();
      }
    },
    preventAccidentalSubmit(event) {
      if (['textarea', 'submit'].includes(event.target.type)) {
        return;
      }

      event.preventDefault();
    },
  },
};
</script>

<style scoped>
.user-editor {
  /* Initialize the form section counter */
  counter-reset: form-section;
}
</style>
