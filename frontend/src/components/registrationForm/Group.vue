<template>
  <div
    class="group"
    :role="params.role ?  params.role : 'group'"
    :aria-labelledby="`${toIdentifier(params.title)}-label`"
  >
    <h3 :class="`group__title ${params.hideTitle ? 'visually-hidden' : ''}`" :id="`${toIdentifier(params.title)}-label`">
      {{ params.title }}
    </h3>

    <div :class="`group__body ${params.horizontal ? 'group__body--horizontal' : ''}`">
      <component
        v-for="(component, index) of params.components" :key="index"
        :is="component.component"
        :params="component.params"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: 'Group',
  props: ['params']
};
</script>

<style scoped>
.group__body--horizontal {
  display: flex;
  align-items: start;
}

.group__body--horizontal > * {
  flex: 1;
}

.group__body--horizontal > *:not(:last-child) {
  margin-right: 0.75rem;
}

.group__title {
  margin-bottom: 0.75rem;
}
</style>

