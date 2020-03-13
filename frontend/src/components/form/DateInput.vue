<template>
  <b-form-group
    :id="`${base}-group`"
    class="date-input"
    :label-for="`${base}-value`"
    :label="`${label}${required ? ' *' : ''}`"
  >
    <b-input-group class="date-input__input-group">
      <flat-pickr
        ref="flatpickr"
        :value="value"
        :config="config"
        :placeholder="placeholder"
        :disabled="disabled"
        :required="required"
        @input="onDatePickerInput"
        @on-close="onClose"
      />
      <b-input-group-append>
        <b-button
          :disabled="disabled"
          data-toggle
        >
          <BIconCalendar />
        </b-button>
      </b-input-group-append>
    </b-input-group>
  </b-form-group>
</template>

<script>
import flatPickr from 'vue-flatpickr-component';
// eslint-disable-next-line import/extensions
import { German } from 'flatpickr/dist/l10n/de.js';
import 'flatpickr/dist/flatpickr.css';
import { BIconCalendar } from 'bootstrap-vue';

export default {
  name: 'DateInput',
  components: {
    flatPickr,
    BIconCalendar,
  },
  model: {
    prop: 'value',
    event: 'update',
  },
  props: {
    base: {
      type: String,
      required: true,
    },
    value: {
      type: [String, Date],
      required: true,
    },
    label: {
      type: String,
      required: true,
    },
    required: {
      type: Boolean,
      default: false,
    },
    placeholder: {
      type: String,
      default: '',
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    maxDate: {
      type: Date,
      default: null,
    },
  },
  data() {
    return {
      config: {
        wrap: true, // true, because we use it around bootstrap
        dateFormat: 'Y-m-d', // the 'internal' format, used as value
        altFormat: 'd.m.Y', // the 'display' format that is shown to the user
        altInput: true,
        allowInput: true,
        locale: German,
        maxDate: this.maxDate,
      },
    };
  },
  methods: {
    onDatePickerInput(dateInInternalFormat) {
      this.$emit('update', dateInInternalFormat);
    },
    onClose(selectedDates, dateInInternalFormat, flatPickrInstance) {
      // When the calender dialog is closed, we explicitly set the current text (user input)
      // as date for flatPickr
      // Idea taken from here: https://github.com/flatpickr/flatpickr/issues/1551
      flatPickrInstance.setDate(
        flatPickrInstance.altInput.value, false, flatPickrInstance.config.altFormat,
      );
      this.onDatePickerInput(dateInInternalFormat);
    },
  },
};
</script>

<style scoped>
.date-input__input-group > .form-control {
  /*the normal bootstrap css is faulty because of the hidden flatpickr input element*/
  border-top-left-radius: 0.5rem;
  border-bottom-left-radius: 0.5rem;
}

</style>
