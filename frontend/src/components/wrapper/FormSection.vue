<template>
  <section class="form-section">
    <h2 class="form-section__title">{{ label }}</h2>
    <slot />
  </section>
</template>

<script>
export default {
  name: 'FormSection',
  props: {
    label: {
      type: String,
      required: true,
    },
  },
};
</script>

<style scoped>

.form-section {
  /* Count each .form-section element */
  counter-increment: form-section;
}

.form-section__title::before {
  /* Display each .form-section’s counter value */
  content: counter(form-section) "." "\A0";
}

</style>
