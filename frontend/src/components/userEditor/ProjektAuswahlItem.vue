<template>
  <b-form-group
    :id="`$projekt-${projekt.id}-group`"
    class="projekt-auswahl-item"
    :invalid-feedback="invalidFeedback"
    :state="false"
  >
    <b-form-checkbox
      :id="`$projekt-${projekt.id}-value`"
      :checked="checked"
      @input="$emit('input', $event)"
    >
      <ul class="projekt-auswahl-item__list">
        <li class="font-weight-bold">
          {{ projekt.name }}
        </li>
        <li>{{ formatDate(projekt.datum) }} - {{ formatDate(projekt.datumEnde) }}</li>
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
  },
  computed: {
    ageAtProjectStart() {
      return this.calculateAgeFromBirthdayAtReferenceDay(this.geburtsdatum,
        this.projekt.datum);
    },
    state() {
      return this.name.length >= 4;
    },
    isTooYoung() {
      return this.ageAtProjectStart < this.projekt.mindestAlter;
    },
    isTooOld() {
      return this.ageAtProjectStart > this.projekt.hoechstAlter;
    },
    isFullyBooked() {
      return this.projekt.slotsFrei <= 0;
    },
    invalidFeedback() {
      if (this.isFullyBooked) {
        return 'Projekt ist leider schon ausgebucht.';
      }
      if (this.isTooYoung) {
        return 'Zu jung';
      }
      if (this.isTooOld) {
        return 'Zu alt';
      }
      return '';
    },
  },
  methods: {
    formatDate(stringDate) {
      return dayjs(stringDate).format(SHORT_DATE_FORMAT);
    },
    calculateAgeFromBirthdayAtReferenceDay(birthday, referenceDay) {
      const reference = dayjs(referenceDay);
      return reference.diff(dayjs(birthday), 'year');
    },
  },
};
</script>

<style scoped>
.projekt-auswahl-item__list {
  list-style: none;
  margin: 0;
  font-size: 0.9em;
}
</style>
