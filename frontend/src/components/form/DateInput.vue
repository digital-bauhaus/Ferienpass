<template>
  <b-form-group
    :id="`${base}-group`"
    :label-for="`${base}-value`"
    :label="`${label}${required ? ' *' : ''}`"
  >
    <b-input-group>
      <flat-pickr
        ref="flatpickr"
        :value="date"
        :config="config"
        class="form-control"
        placeholder="TT.MM.JJJJ"
        @input="onDatePickerInput"
        @on-change="onDatePickerChange"
        @on-close="onClose"
      />
      <b-input-group-append>
        <b-button data-toggle>
          <BIconCalendar />
        </b-button>
      </b-input-group-append>
    </b-input-group>
    <p>{{ date }}</p>
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
      type: [String, Number],
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
  },
  data() {
    return {
      date: null,
      config: {
        wrap: true, // set wrap to true only when using 'input-group'
        dateFormat: 'd.m.Y',
        allowInput: true,
        locale: German,
        maxDate: Date.now(),
        onKeyDown: this.onKeyDown,
        onValueUpdate: this.onValueUpdate,
        onChange: this.onChange,
      },
    };
  },
  methods: {
    onDatePickerInput(event) {
      console.log(`onDatePickerInput : ${event}: ${this.date}`);
      this.date = event;
    },
    onDatePickerChange(event) {
      console.log(`onDatePickerChange: ${event} | ${this.date}`);
    },
    onClose() {
      console.log('onClose');
      // this.$refs.flatpickr.fp.setDate(this.date, false, 'd.m.Y');
    },
  },
};
</script>

<style scoped>

</style>
