<template>
  <!-- eslint-disable-next-line vue/no-v-html -->
  <p><span v-html="params.html" /></p>
</template>

<script>
export default {
  name: 'ParagraphWithHtml',
  props: {
    params: {
      type: Object,
      required: true,
    },
  },
};
</script>

<style scoped lang="scss">

</style>
