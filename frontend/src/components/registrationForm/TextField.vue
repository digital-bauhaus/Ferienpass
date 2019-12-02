<template>
  <label :class="`text-field ${params.required ? 'required' : ''}`">
    <span :class="`text-field__label ${params.hideLabel ? 'visually-hidden' : ''}`">
      {{ params.label }}
    </span>

    <input
      class="text-field__control"
      :type="params.type ? params.type : 'text'"
      :name="params.name"
      :value="value"
      :placeholder="params.placeholder"
      :pattern="params.pattern"
      :maxlength="params.maxlength"
      :required="params.required"
      @input="onInput"
      @keydown.enter="onEnter"
    >
  </label>
</template>

<script>
export default {
  name: 'TextField',
  props: ['params', 'value'],
  methods: {
    onInput(event) {
      this.$emit('input', event);

      if (this.params.storeValue) {
        const registrationData = this.$root.$children[0].$children[0].$data;
        registrationData[event.target.name] = event.target.value;
      }
    },
    onEnter() {
      this.$emit('enter');
    }
  }
};
</script>

<style scoped>
.text-field {
  display: block;
  margin-bottom: 0.75rem;
}

.required .text-field__label::after {
  content: '*';
  color: #444;
}

.text-field__control {
  display: block;
  width: 100%;
  padding: 4px 8px;
  border: 2px solid;
  border-radius: 8px;
}

.text-field__control:focus {
  outline: none;
  /* box-shadow: 0 0 0 2px #fff, 0 0 0 4px cornflowerblue; */
  box-shadow: 0 0 0 2px cornflowerblue;
}
</style>
