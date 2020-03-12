<template>
  <CheckBoxGroup
    base="verwaltung-bezahlt"
    :disabled="disabled"
  >
    <CheckBox
      base="verwaltung-bezahlt"
      :checked="bezahlt"
      @input="$emit('update:bezahlt', $event)"
    >
      Hat Bezahlt
    </CheckBox>
  </CheckBoxGroup>
</template>

<script>
import CheckBox from '@/components/wrapper/CheckBox.vue';
import CheckBoxGroup from '@/components/wrapper/CheckBoxGroup.vue';

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
