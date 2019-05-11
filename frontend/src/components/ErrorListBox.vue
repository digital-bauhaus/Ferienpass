<template>
  <div class="error-list-box-container">
    <b>{{ headingText }}</b>
    <ul>
      <li v-for="error in errors">{{ error }}</li>
    </ul>
  </div>
</template>

<script>
  export default {
    name: "ErrorListBox",
    props: {
      headingText: {
        type: String,
        required: true
      },
      errors: {
        type: Array,
        required: true
      }
    }
  }
</script>

<style scoped>
  .error-list-box-container {
    color: #721c24;
    background-color: #f8d7da;
    border: 1px solid #f5c6cb;
    border-radius: .25rem;
    padding: .75rem 1.25rem;
  }

  .error-list-box-container ul {
    margin-bottom: 0;
  }
</style>