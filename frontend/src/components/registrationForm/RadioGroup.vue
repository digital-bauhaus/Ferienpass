<template>
  <div
    class="radio-group"
    role="radiogroup"
    :aria-labelledby="`${toIdentifier(params.title)}-label`"
  >
    <h3
      :class="`radio-group__title ${params.hideTitle ? 'visually-hidden' : ''}`"
      :id="`${toIdentifier(params.title)}-label`"
    >
      {{ params.title }}
    </h3>

    <radio-button
      v-for="(component, index) of params.components" :key="index"
      :params="component.params"
      :name="params.name"
    />
  </div>
</template>

<script>
import RadioButton from './RadioButton';

export default {
  name: 'RadioGroup',
  props: ['params'],
  components: {
    RadioButton
  }
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
