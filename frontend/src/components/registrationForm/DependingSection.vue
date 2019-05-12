<template>
  <div>
    <h3>
      {{ params.title }}
    </h3>

    <checkbox :params="params.checkbox.params" @change="updateChecked" />

    <template v-if="checked">
      <component
        v-for="(component, index) of params.components" :key="index"
        :is="component.component"
        :params="component.params"
      />
    </template>
  </div>
</template>

<script>
export default {
  name: 'DependingSection',
  props: ['params'],
  data () {
    return {
      checked: undefined
    };
  },
  methods: {
    updateChecked (event) {
      this.checked = event.target.checked;
    }
  }
};
</script>
