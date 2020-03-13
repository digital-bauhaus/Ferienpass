<template>
  <li class="paragraph-list__item">
    <slot />
  </li>
</template>

<script>
export default {
  name: 'ParagraphListItem',
};
</script>

<style scoped>
.paragraph-list__item {
  counter-increment: paragraph-list;
  display: flex;
}

.paragraph-list__item::before {
  content: '§' counter(paragraph-list);
  min-width: 2em;
  text-align: right;
  margin-right: 0.75rem;
}

.paragraph-list__item:not(:last-child) {
  margin-bottom: 0.75rem;
}
</style>
