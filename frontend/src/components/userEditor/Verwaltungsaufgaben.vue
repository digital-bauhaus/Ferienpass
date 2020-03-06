<template>
  <CheckBoxGroup base="verwaltung-bezahlt">
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

export default {
  name: 'Verwaltungsaufgaben',
  components: {
    CheckBoxGroup,
    CheckBox,
  },
};
</script>

<style scoped>

</style>
