<template>
  <b-form-group
    :id="`$projekt-${projekt.id}-group`"
    class="projekt-auswahl-item"
    :invalid-feedback="invalidFeedback"
    :state="state"
  >
    <b-form-checkbox
      :id="`$projekt-${projekt.id}-value`"
      :disabled="disabled || !isSelectable"
      :checked="checked"
      @input="$emit('input', $event)"
    >
      <ul class="projekt-auswahl-item__list">
        <li class="font-weight-bold">
          {{ projekt.name }}
        </li>
        <li>{{ formatDate(projekt.datumBeginn) }} - {{ formatDate(projekt.datumEnde) }}</li>
        <li v-if="projekt.mindestAlter > 0">
          Ab {{ projekt.mindestAlter }} Jahren
        </li>
        <li v-if="projekt.hoechstAlter > 0">
          Bis {{ projekt.hoechstAlter }} Jahre
        </li>
      </ul>
    </b-form-checkbox>
  </b-form-group>
</template>

<script>
import dayjs, { SHORT_DATE_FORMAT } from '../../modules/dayjs';

export default {
  name: 'ProjektAuswahlItem',
  model: {
    prop: 'checked',
    event: 'input',
  },
  props: {
    disabled: {
      type: Boolean,
      default: false,
    },
    checked: {
      type: Boolean,
      required: true,
    },
    projekt: {
      type: Object,
      required: true,
    },
    geburtsdatum: {
      type: String,
      required: true,
    },
    alleProjekte: {
      type: Array,
      required: true,
    },
    gewuenschteProjekte: {
      type: Object,
      required: true,
    },
  },
  computed: {
    ageAtProjectStart() {
      return this.calculateAgeFromBirthdayAtReferenceDay(this.geburtsdatum,
        this.projekt.datumBeginn);
    },
    isSelectable() {
      return this.state;
    },
    state() {
      return !(this.isTooYoung || this.isTooOld
        || this.isFullyBooked || this.beginsDuringAnother);
    },
    isTooYoung() {
      return this.ageAtProjectStart < this.projekt.mindestAlter;
    },
    isTooOld() {
      return this.ageAtProjectStart > this.projekt.hoechstAlter;
    },
    beginsDuringAnother() {
      return this.alleProjekte.some((otherProjekt) => {
        if (this.projekt.id !== otherProjekt.id && this.gewuenschteProjekte[otherProjekt.id]) {
          const thisProjektBeginDatum = dayjs(this.projekt.datumBeginn);
          const thisProjektEndDatum = dayjs(this.projekt.datumEnde);
          const otherProjektBeginDatum = dayjs(otherProjekt.datumBeginn);
          const otherProjektEndDatum = dayjs(otherProjekt.datumEnde);

          // pruefe zeitliche Ueberschneidungen
          if (thisProjektBeginDatum.isBefore(otherProjektBeginDatum)
              && thisProjektEndDatum.isAfter(otherProjektEndDatum)) {
            return true;
          }
          if (thisProjektEndDatum.isAfter(otherProjektBeginDatum)
              && thisProjektEndDatum.isBefore(otherProjektEndDatum)) {
            return true;
          }
          if (thisProjektBeginDatum.isBefore(otherProjektEndDatum)
              && thisProjektEndDatum.isAfter(otherProjektBeginDatum)) {
            return true;
          }
          if (thisProjektEndDatum.isAfter(otherProjektBeginDatum)
              && thisProjektBeginDatum.isBefore(otherProjektEndDatum)) {
            return true;
          }
        }
        return false;
      });
    },
    isFullyBooked() {
      return this.projekt.plaetzeFrei <= 0;
    },
    invalidFeedback() {
      if (this.isFullyBooked) {
        return 'Projekt ist leider schon ausgebucht.';
      }
      if (this.isTooYoung) {
        return 'Altersbeschränkung nicht erfüllt.';
      }
      if (this.isTooOld) {
        return 'Altersbeschränkung nicht erfüllt.';
      }
      if (this.beginsDuringAnother) {
        return 'Zeitliche Überschneidung.';
      }
      return '';
    },
  },
  watch: {
    isSelectable() {
      if (!this.isSelectable && this.checked) {
        // we need to automatically deselect the project if we are not allowed to book it
        this.$emit('input', false);
      }
    },
  },
  methods: {
    formatDate(stringDate) {
      return dayjs(stringDate).format(SHORT_DATE_FORMAT);
    },
    calculateAgeFromBirthdayAtReferenceDay(birthday, referenceDay) {
      if (birthday === '') return 0;
      const reference = dayjs(referenceDay);
      return reference.diff(dayjs(birthday), 'year');
    },
  },
};
</script>

<style scoped>
.projekt-auswahl-item__list {
  list-style: none;
  padding-left:  0;
  margin: 0;
  font-size: 0.9em;
}
.projekt-auswahl-item >>> .invalid-feedback {
  padding-left: 1.75rem;
}
</style>
