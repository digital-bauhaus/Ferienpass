<template>
  <CheckBoxGroup
    base="verwaltung-bezahlt"
  >
    <CheckBox
      base="verwaltung-bezahlt"
      :disabled="disabled"
      :checked="bezahlt"
      @input="$emit('update:bezahlt', $event)"
    >
      Hat Bezahlt
    </CheckBox>
  </CheckBoxGroup>
</template>

<script>
import CheckBox from '@/components/form/CheckBox.vue';
import CheckBoxGroup from '@/components/form/CheckBoxGroup.vue';

// TODO restliche Felder etc.
export default {
  name: 'Verwaltungsaufgaben',
  components: {
    CheckBoxGroup,
    CheckBox,
  },
  props: {
    disabled: {
      type: Boolean,
      default: false,
    },
  },
};
</script>

<style scoped>

</style>
