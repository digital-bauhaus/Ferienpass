<template>
  <div>Kontakt</div>
</template>

<script>
export default {
  name: 'Kontakt',
  props: {
    name: {
      type: String,
      required: true,
    },
    anschrift: {
      type: String,
      required: true,
    },
    telefon: {
      type: String,
      required: true,
    },
  },
};
</script>

<style scoped>

</style>
