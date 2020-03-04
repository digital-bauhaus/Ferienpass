<template>
  <b-form
    ref="form"
    novalidate
    :validated="showValidationStatus"
    @submit="onSubmit"
  >
    <b-form-row />

    <b-button
      type="submit"
      variant="primary"
    >
      {{ submitButtonText }}
    </b-button>
  </b-form>
</template>

<script>

export default {
  name: 'UserEditor',
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
    };
  },
  methods: {
    updateValue(propName, newValue) {
      this.$emit('input', {
        ...this.value,
        [propName]: newValue,
      });
    },
    onSubmit() {
      this.showValidationStatus = true;
      if (this.$refs.form.checkValidity()) {
        this.$emit('submit');
      } else {
        this.$refs.form.reportValidity();
      }
    },
  },
};
</script>

<style scoped>

</style>
