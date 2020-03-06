<template>
  <b-form
    v-if="hasUser"
    ref="form"
    novalidate
    :validated="showValidationStatus"
    @submit.prevent="onSubmit"
  >
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
      <Kontakt
        heading="In Notfällen zu informieren"
        base="notfallkontakt"
        :name="value.notfallKontakt.name"
        :anschrift="value.notfallKontakt.address"
        :telefon="value.notfallKontakt.telephone"
        @update:name="updateValue('notfallKontakt.name', $event)"
        @update:anschrift="updateValue('notfallKontakt.address', $event)"
        @update:telefon="updateValue('notfallKontakt.telephone', $event)"
      />
    </Pflichtangaben>

    <Gesundheit
      :allergien="value.allergien"
      :krankheiten="value.krankheiten"
      :medikamente="value.medikamente"
      :krankenkasse="value.krankenkasse"
      :hitzeempfindlichkeiten="value.hitzeempfindlichkeiten"
      @update:allergien="updateValue('allergien', $event)"
      @update:krankheiten="updateValue('krankheiten', $event)"
      @update:medikamente="updateValue('medikamente', $event)"
      @update:krankenkasse="updateValue('krankenkasse', $event)"
      @update:hitzeempfindlichkeiten="updateValue('hitzeempfindlichkeiten', $event)"
    >
      <Kontakt
        heading="Hausarzt"
        base="hausarzt"
        :name="value.arzt.name"
        :anschrift="value.arzt.address"
        :telefon="value.arzt.telephone"
        @update:name="updateValue('arzt.name', $event)"
        @update:anschrift="updateValue('arzt.address', $event)"
        @update:telefon="updateValue('arzt.telephone', $event)"
      />
    </Gesundheit>
    <Behinderung />
    <Angebote />
    <Datenschutz />
    <Teilnahmebedingungen />

    <CheckBox
      v-model="confirmation"
      base="confirmation"
    >
      Ich bestätige die Richtigkeit meiner Angaben. Wurden wissentlich falsche Angaben gemacht,
      darf die Organisation das angemeldete Kind von den Angeboten ausschließen.
    </CheckBox>

    <b-button
      type="submit"
      variant="primary"
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

export default {
  name: 'UserEditor',
  components: {
    CheckBox,
    Kontakt,
    Teilnahmebedingungen,
    Angebote,
    Datenschutz,
    Behinderung,
    Gesundheit,
    Pflichtangaben,
    Grunddaten,
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
      confirmation: false,
    };
  },
  computed: {
    hasUser() {
      return this.value && this.value.id && this.value.id > 0;
    },
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
        // this.$emit('submit');
      } else {
        this.$refs.form.reportValidity();
      }
    },
  },
};
</script>

<style scoped>

</style>
