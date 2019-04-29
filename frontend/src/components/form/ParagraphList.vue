<template>
  <ul class="paragraph-list">
    <li class="paragraph-list__item" v-for="(entry, index) of params.entries" :key="index">
      {{ entry }}
    </li>
  </ul>
</template>

<script>
export default {
  name: 'ParagraphList',
  props: ['params']
};
</script>

<style scoped>
.paragraph-list {
  margin-left: 0;
  list-style-type: none;
  counter-reset: paragraph-list;
}

.paragraph-list__item {
  counter-increment: paragraph-list;
  display: flex;
}

.paragraph-list__item::before {
  content: '$' counter(paragraph-list);
  min-width: 2em;
  text-align: right;
  margin-right: 0.75rem;
}

.paragraph-list__item:not(:last-child) {
  margin-bottom: 0.75rem;
}
</style>
