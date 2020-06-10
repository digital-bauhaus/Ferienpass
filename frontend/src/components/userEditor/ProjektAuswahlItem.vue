<template>
  <b-form-group
    :id="`projekt-${projekt.id}-group`"
    class="projekt-auswahl-item"
    :invalid-feedback="invalidFeedback"
    :state="state"
  >
    <b-form-checkbox
      :id="`projekt-${projekt.id}-value`"
      :disabled="disabled || !isSelectable"
      :checked="checked"
      :aria-label="projekt.name"
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

const MAX_NUMBER_OF_PROJECTS_PER_USER = 4;

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
      return !(this.isTooYoung
        || this.isTooOld
        || this.isFullyBooked
        || this.isTooManyProjects
        || this.isSameGroupAsAnother
        || this.atTheSameTimeAsAnother);
    },
    isTooYoung() {
      return this.ageAtProjectStart < this.projekt.mindestAlter;
    },
    isTooOld() {
      return this.ageAtProjectStart > this.projekt.hoechstAlter;
    },
    isFullyBooked() {
      return this.projekt.plaetzeFrei <= 0;
    },
    isTooManyProjects() {
      const currentNumberOfProjects = Object.entries(this.gewuenschteProjekte)
      // eslint-disable-next-line no-unused-vars
        .filter(([key, value]) => value).length;
      return !this.checked && currentNumberOfProjects >= MAX_NUMBER_OF_PROJECTS_PER_USER;
    },
    isSameGroupAsAnother() {
      if (this.projekt.gruppe === '') {
        return false;
      }
      return this.alleProjekte.some((otherProjekt) => {
        if (this.projekt.id !== otherProjekt.id && this.gewuenschteProjekte[otherProjekt.id]) {
          // pruefe auf gleiche Gruppe
          if (this.projekt.gruppe === otherProjekt.gruppe) {
            return true;
          }
        }
        return false;
      });
    },
    atTheSameTimeAsAnother() {
      return this.alleProjekte.some((otherProjekt) => {
        if (this.projekt.id !== otherProjekt.id && this.gewuenschteProjekte[otherProjekt.id]) {
          const thisProjektBeginDatum = dayjs(this.projekt.datumBeginn);
          const thisProjektEndDatum = dayjs(this.projekt.datumEnde);
          const otherProjektBeginDatum = dayjs(otherProjekt.datumBeginn);
          const otherProjektEndDatum = dayjs(otherProjekt.datumEnde);

          // pruefe zeitliche Ueberschneidungen
          if (thisProjektBeginDatum.isSameOrBefore(otherProjektBeginDatum)
              && thisProjektEndDatum.isSameOrAfter(otherProjektEndDatum)) {
            return true;
          }
          if (thisProjektEndDatum.isSameOrAfter(otherProjektBeginDatum)
              && thisProjektEndDatum.isSameOrBefore(otherProjektEndDatum)) {
            return true;
          }
          if (thisProjektBeginDatum.isSameOrBefore(otherProjektEndDatum)
              && thisProjektEndDatum.isSameOrAfter(otherProjektBeginDatum)) {
            return true;
          }
          if (thisProjektEndDatum.isSameOrAfter(otherProjektBeginDatum)
              && thisProjektBeginDatum.isSameOrBefore(otherProjektEndDatum)) {
            return true;
          }
        }
        return false;
      });
    },
    invalidFeedback() {
      if (this.isTooYoung) {
        return 'Altersbeschränkung nicht erfüllt.';
      }
      if (this.isTooOld) {
        return 'Altersbeschränkung nicht erfüllt.';
      }
      if (this.isFullyBooked) {
        return 'Veranstaltung ist leider schon ausgebucht.';
      }
      if (this.isTooManyProjects) {
        return `Sie dürfen sich für maximal ${MAX_NUMBER_OF_PROJECTS_PER_USER} Veranstaltungen anmelden.`;
      }
      if (this.isSameGroupAsAnother) {
        return `Sie dürfen sich nur für eine Veranstaltung der Gruppe "${this.projekt.gruppe}" anmelden.`;
      }
      if (this.atTheSameTimeAsAnother) {
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
