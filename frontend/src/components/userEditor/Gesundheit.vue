<template>
  <div>
    <DynamicInputList
      base="gesundheit-allergien"
      label="Bei meinem Kind muss auf folgende Allergie(n) geachtet werden:"
      placeholder="z.B. Heuschnupfen"
      :value="allergien ? allergien.split('|') : []"
      @input="$emit('update:allergien', $event.join('|'))"
    />

    <DynamicInputList
      base="gesundheit-krankheiten"
      label="Krankheiten des Kindes bitte hier angeben:"
      placeholder="z.B. Epilepsie"
      :value="krankheiten ? krankheiten.split('|') : []"
      @input="$emit('update:krankheiten', $event.join('|'))"
    />

    <DynamicInputList
      base="gesundheit-medikamente"
      label="Mein Kind nimmt folgende Medikamente ein:"
      placeholder="z.B. Diazepam"
      :value="medikamente ? medikamente.split('|') : []"
      @input="$emit('update:medikamente', $event.join('|'))"
    />

    <CheckBoxGroup
      base="gesundheit-hitzeempfindlichkeiten"
      label="Hitzeempfindlichkeit"
    >
      <CheckBox
        base="gesundheit-hitzeempfindlichkeiten"
        :checked="hitzeempfindlichkeiten"
        @input="$emit('update:hitzeempfindlichkeiten', $event)"
      >
        Mein Kind ist hitzeempfindlich
      </CheckBox>
    </CheckBoxGroup>

    <CheckBoxGroup
      base="gesundheit-ernaehrung"
      label="Ernährungsbesonderheiten"
    >
      <CheckBox
        base="gesundheit-vegetarier"
        :checked="vegetarier"
        @input="$emit('update:vegetarier', $event)"
      >
        Vegetarier
      </CheckBox>
      <CheckBox
        base="gesundheit-laktoseUnvertraeglichkeit"
        :checked="laktoseUnvertraeglichkeit"
        @input="$emit('update:laktoseUnvertraeglichkeit', $event)"
      >
        Laktose-Unverträglichkeit
      </CheckBox>
      <CheckBox
        base="gesundheit-eierUnvertraeglichkeit"
        :checked="eierUnvertraeglichkeit"
        @input="$emit('update:eierUnvertraeglichkeit', $event)"
      >
        Eier-Unverträglichkeit
      </CheckBox>
    </CheckBoxGroup>

    <DynamicInputList
      base="gesundheit-essenLimitierungen"
      label="Weitere Ernährungsbesonderheiten:"
      placeholder="z.B. Milchpulver-Unverträglichkeit"
      :value="essenLimitierungen ? essenLimitierungen.split('|') : []"
      @input="$emit('update:essenLimitierungen', $event.join('|'))"
    />

    <FieldInput
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
import FieldInput from '@/components/wrapper/FieldInput.vue';
import CheckBox from '@/components/wrapper/CheckBox.vue';
import CheckBoxGroup from '@/components/wrapper/CheckBoxGroup.vue';

export default {
  name: 'Gesundheit',
  components: {
    CheckBoxGroup, CheckBox, FieldInput, DynamicInputList,
  },
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
    hitzeempfindlichkeiten: {
      type: Boolean,
      required: true,
    },
    vegetarier: {
      type: Boolean,
      required: true,
    },
    laktoseUnvertraeglichkeit: {
      type: Boolean,
      required: true,
    },
    eierUnvertraeglichkeit: {
      type: Boolean,
      required: true,
    },
    essenLimitierungen: {
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
