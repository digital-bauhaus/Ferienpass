<template>
  <div
    class="radio-group"
    role="radiogroup"
    :aria-labelledby="`${toIdentifier(params.title)}-label`"
  >
    <h3
      :id="`${toIdentifier(params.title)}-label`"
      :class="`radio-group__title ${params.hideTitle ? 'visually-hidden' : ''}`"
    >
      {{ params.title }}
    </h3>

    <radio-button
      v-for="(component, index) of params.components"
      :key="index"
      :params="component.params"
      :name="params.name"
    />
  </div>
</template>

<script>
import RadioButton from './RadioButton';

export default {
  name: 'RadioGroup',
  components: {
    RadioButton,
  },
  props: ['params'],
};
</script>

<style scoped>
.radio-group {
  margin-bottom: 1.5rem;
}

.radio-group__title {
  margin-bottom: 0.75rem;
}
</style>
