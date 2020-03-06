<template>
  <b-form
    ref="form"
    class="user-editor"
    novalidate
    :validated="showValidationStatus"
    @submit.prevent="onSubmit"
    @keydown.enter="preventAccidentalSubmit"
  >
    <FormSection
      v-if="isAdminView"
      label="Verwaltungsaufgaben"
    >
      <Verwaltungsaufgaben />
    </FormSection>

    <FormSection label="Grunddaten">
      <Grunddaten
        :nachname="value.nachname"
        :vorname="value.vorname"
        :geburtsdatum="value.geburtsdatum"
        :strasse="value.strasse"
        :haus-nummer="'TODO'"
        :postleitzahl="value.postleitzahl"
        :stadt="value.stadt"
        :telefon="value.telefon"
        :email="value.email"
        @update:nachname="updateValue('nachname', $event)"
        @update:vorname="updateValue('vorname', $event)"
        @update:geburtsdatum="updateValue('geburtsdatum', $event)"
        @update:strasse="updateValue('strasse', $event)"
        @update:hausNummer="updateValue('TODO', $event)"
        @update:postleitzahl="updateValue('postleitzahl', $event)"
        @update:stadt="updateValue('stadt', $event)"
        @update:telefon="updateValue('telefon', $event)"
        @update:email="updateValue('email', $event)"
      />
    </FormSection>

    <FormSection label="Pflichtangaben">
      <Pflichtangaben
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
            :name="value.notfallKontakt.name"
            :anschrift="value.notfallKontakt.address"
            :telefon="value.notfallKontakt.telephone"
            @update:name="updateValue('notfallKontakt.name', $event)"
            @update:anschrift="updateValue('notfallKontakt.address', $event)"
            @update:telefon="updateValue('notfallKontakt.telephone', $event)"
          />
        </Group>
      </Pflichtangaben>
    </FormSection>

    <FormSection label="Allergien, Krankheiten, ...">
      <Gesundheit
        :allergien="value.allergien"
        :krankheiten="value.krankheiten"
        :medikamente="value.medikamente"
        :krankenkasse="value.krankenkasse"
        :hitzeempfindlichkeiten="value.hitzeempfindlichkeiten"
        :vegetarier="value.vegetarier"
        :laktose-unvertraeglichkeit="value.laktoseUnvertraeglichkeit"
        :eier-unvertraeglichkeit="value.eierUnvertraeglichkeit"
        :essen-limitierungen="value.essenLimitierungen"
        @update:allergien="updateValue('allergien', $event)"
        @update:krankheiten="updateValue('krankheiten', $event)"
        @update:medikamente="updateValue('medikamente', $event)"
        @update:krankenkasse="updateValue('krankenkasse', $event)"
        @update:hitzeempfindlichkeiten="updateValue('hitzeempfindlichkeiten', $event)"
        @update:vegetarier="updateValue('vegetarier', $event)"
        @update:laktoseUnvertraeglichkeit="updateValue('laktoseUnvertraeglichkeit', $event)"
        @update:eierUnvertraeglichkeit="updateValue('eierUnvertraeglichkeit', $event)"
        @update:essenLimitierungen="updateValue('essenLimitierungen', $event)"
      >
        <Group label="Hausarzt">
          <Kontakt
            base="hausarzt"
            :name="value.arzt.name"
            :anschrift="value.arzt.address"
            :telefon="value.arzt.telephone"
            @update:name="updateValue('arzt.name', $event)"
            @update:anschrift="updateValue('arzt.address', $event)"
            @update:telefon="updateValue('arzt.telephone', $event)"
          />
        </Group>
      </Gesundheit>
    </FormSection>

    <FormSection label="Angaben bei Behinderung">
      <!-- eslint-disable max-len -->
      <Behinderung
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
        :eingeschraenkte-sinne="value.behinderung.eingeschraenkteSinne"
        :hinweise-zum-umgang-mit-dem-kind="value.behinderung.hinweiseZumUmgangMitDemKind"
        :unterstuetzung-suche-begleitperson-notwendig="value.behinderung.unterstuetzungSucheBegleitpersonNotwendig"
        :gewohnter-begleitpersonen-dienstleister="value.behinderung.gewohnterBegleitpersonenDienstleister"
        :beantragung-kostenuebernahme-begleitperson-notwendig="value.behinderung.beantragungKostenuebernahmeBegleitpersonNotwendig"
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
        @update:eingeschraenkteSinne="updateValue('behinderung.eingeschraenkteSinne', $event)"
        @update:hinweiseZumUmgangMitDemKind="updateValue('behinderung.hinweiseZumUmgangMitDemKind', $event)"
        @update:unterstuetzungSucheBegleitpersonNotwendig="updateValue('behinderung.unterstuetzungSucheBegleitpersonNotwendig', $event)"
        @update:gewohnterBegleitpersonenDienstleister="updateValue('behinderung.gewohnterBegleitpersonenDienstleister', $event)"
        @update:beantragungKostenuebernahmeBegleitpersonNotwendig="updateValue('behinderung.beantragungKostenuebernahmeBegleitpersonNotwendig', $event)"
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
      base="confirmation"
    >
      <CheckBox
        v-model="confirmation"
        base="confirmation"
        :required="true"
      >
        Ich bestätige die Richtigkeit meiner Angaben. Wurden wissentlich falsche Angaben gemacht,
        darf die Organisation das angemeldete Kind von den Angeboten ausschließen.
      </CheckBox>
    </CheckBoxGroup>

    <b-button
      type="submit"
      variant="primary"
      class="mb-3"
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
      confirmation: false,
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
        console.log('go for it');
        this.$emit('submit');
      } else {
        console.log('go nit for it');
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
