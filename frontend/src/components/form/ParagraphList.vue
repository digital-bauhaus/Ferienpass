<template>
  <ol class="paragraph-list">
    <slot />
  </ol>
</template>

<script>
export default {
  name: 'ParagraphList',
};
</script>

<style scoped>
.paragraph-list {
  margin-left: 0;
  padding-left: 0;
  list-style-type: none;
  counter-reset: paragraph-list;
}
</style>
