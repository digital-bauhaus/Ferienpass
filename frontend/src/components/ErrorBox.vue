<template>
  <b-alert
    :show="true"
    :dismissible="dismissible"
    variant="danger"
    fade
  >
    <b>{{ headingText }}</b>
    <ul>
      <li
        v-for="error in errorMessages"
        :key="error"
      >
        {{ error }}
      </li>
    </ul>
  </b-alert>
</template>

<script>
export default {
  name: 'ErrorBox',
  props: {
    headingText: {
      type: String,
      required: true,
    },
    errorMessages: {
      type: Array,
      required: true,
    },
    dismissible: {
      type: Boolean,
      required: false,
      default: true,
    },
  },
};
</script>

<style scoped>

</style>
