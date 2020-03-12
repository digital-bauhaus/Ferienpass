<template>
  <div>
    <DynamicInputList
      base="gesundheit-allergien"
      label="Bei meinem Kind muss auf folgende Allergie(n) geachtet werden:"
      placeholder="z.B. Heuschnupfen"
      separator="|"
      :disabled="disabled"
      :value="allergien ? allergien.split('|') : []"
      @input="$emit('update:allergien', $event.join('|'))"
    />

    <DynamicInputList
      base="gesundheit-krankheiten"
      label="Krankheiten des Kindes bitte hier angeben:"
      placeholder="z.B. Epilepsie"
      separator="|"
      :disabled="disabled"
      :value="krankheiten ? krankheiten.split('|') : []"
      @input="$emit('update:krankheiten', $event.join('|'))"
    />

    <DynamicInputList
      base="gesundheit-medikamente"
      label="Mein Kind nimmt folgende Medikamente ein:"
      placeholder="z.B. Diazepam"
      separator="|"
      :disabled="disabled"
      :value="medikamente ? medikamente.split('|') : []"
      @input="$emit('update:medikamente', $event.join('|'))"
    />

    <CheckBoxGroup
      base="gesundheit-hitzeempfindlich"
      label="Hitzeempfindlichkeit"
    >
      <CheckBox
        base="gesundheit-hitzeempfindlich"
        :disabled="disabled"
        :checked="hitzeempfindlich"
        @input="$emit('update:hitzeempfindlich', $event)"
      >
        Mein Kind ist hitzeempfindlich
      </CheckBox>
    </CheckBoxGroup>

    <CheckBoxGroup
      base="gesundheit-ernaehrung"
      label="Ernährungsbesonderheiten"
    >
      <CheckBox
        base="gesundheit-essenVegetarier"
        :disabled="disabled"
        :checked="essenVegetarier"
        @input="$emit('update:essenVegetarier', $event)"
      >
        Vegetarier
      </CheckBox>
      <CheckBox
        base="gesundheit-essenLaktoseUnvertraeglichkeit"
        :disabled="disabled"
        :checked="essenLaktoseUnvertraeglichkeit"
        @input="$emit('update:essenLaktoseUnvertraeglichkeit', $event)"
      >
        Laktose-Unverträglichkeit
      </CheckBox>
      <CheckBox
        base="gesundheit-essenEierUnvertraeglichkeit"
        :disabled="disabled"
        :checked="essenEierUnvertraeglichkeit"
        @input="$emit('update:essenEierUnvertraeglichkeit', $event)"
      >
        Eier-Unverträglichkeit
      </CheckBox>
    </CheckBoxGroup>

    <DynamicInputList
      base="gesundheit-essenWeitereLimitierungen"
      label="Weitere Ernährungsbesonderheiten:"
      placeholder="z.B. Milchpulver-Unverträglichkeit"
      separator="|"
      :disabled="disabled"
      :value="essenWeitereLimitierungen ? essenWeitereLimitierungen.split('|') : []"
      @input="$emit('update:essenWeitereLimitierungen', $event.join('|'))"
    />

    <FieldInput
      base="gesundheit-krankenkasse"
      label="Krankenkasse"
      :disabled="disabled"
      :value="krankenkasse"
      @update="$emit('update:krankenkasse', $event)"
    />

    <slot />
  </div>
</template>

<script>
import DynamicInputList from '@/components/wrapper/DynamicInputList.vue';
import FieldInput from '@/components/wrapper/FieldInput.vue';
import CheckBox from '@/components/wrapper/CheckBox.vue';
import CheckBoxGroup from '@/components/wrapper/CheckBoxGroup.vue';

export default {
  name: 'Gesundheit',
  components: {
    CheckBoxGroup, CheckBox, FieldInput, DynamicInputList,
  },
  props: {
    disabled: {
      type: Boolean,
      default: false,
    },
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
    hitzeempfindlich: {
      type: Boolean,
      required: true,
    },
    essenVegetarier: {
      type: Boolean,
      required: true,
    },
    essenLaktoseUnvertraeglichkeit: {
      type: Boolean,
      required: true,
    },
    essenEierUnvertraeglichkeit: {
      type: Boolean,
      required: true,
    },
    essenWeitereLimitierungen: {
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
