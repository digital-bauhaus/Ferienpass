<template>
  <div>
    <h2>Allergien, Krankheiten, ...</h2>

    <DynamicInputList
      base="gesundheit-allergien"
      heading="Allergien"
      label="Bei meinem Kind muss auf folgende Allergie(n) geachtet werden:"
      placeholder="z.B. Heuschnupfen"
      :value="allergien ? allergien.split('|') : []"
      @input="$emit('update:allergien', $event.join('|'))"
    />

    <DynamicInputList
      base="gesundheit-krankheiten"
      heading="Krankheiten"
      label="Krankheiten des Kindes bitte hier angeben:"
      placeholder="z.B. Epilepsie"
      :value="krankheiten ? krankheiten.split('|') : []"
      @input="$emit('update:krankheiten', $event.join('|'))"
    />

    <DynamicInputList
      base="gesundheit-medikamente"
      heading="Medikamente"
      label="Mein Kind nimmt folgende Medikamente ein:"
      placeholder="z.B. Diazepam"
      :value="medikamente ? medikamente.split('|') : []"
      @input="$emit('update:medikamente', $event.join('|'))"
    />

    <TextInput
      base="gesundheit-krankenkasse"
      label="Krankenkasse"
      :value="krankenkasse"
      @update="$emit('update:krankenkasse', $event)"
    />

    <slot />
  </div>
</template>

<script>
import DynamicInputList from '@/components/wrapper/DynamicInputList.vue';
import TextInput from '@/components/wrapper/TextInput.vue';

export default {
  name: 'Gesundheit',
  components: { TextInput, DynamicInputList },
  props: {
    allergien: {
      type: String,
      required: true,
    },
    krankheiten: {
      type: String,
      required: true,
    },
    medikamente: {
      type: String,
      required: true,
    },
    krankenkasse: {
      type: String,
      required: true,
    },
  },
};
</script>

<style scoped>

</style>
