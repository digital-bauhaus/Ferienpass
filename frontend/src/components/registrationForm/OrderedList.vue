<template>
  <ol>
    <li v-for="(entry, index) of params.entries" :key="index">
      {{ entry }}
    </li>
  </ol>
</template>

<script>
export default {
  name: 'OrderedList',
  props: ['params']
};
</script>
