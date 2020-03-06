<template>
  <div>
    <h2>Allergien, Krankheiten, ...</h2>
    <b-form-group
      id="gesundheit-allergien-group"
      label-for="gesundheit-allergien-value"
      label="Bei meinem Kind muss auf folgende Allergie(n) geachtet werden:"
    >
      <b-form-tags
        id="gesundheit-allergien-tags"
        input-id="gesundheit-allergien-value"
        tag-variant="primary"
        tag-pills
        placeholder="z.B. Heuschnupfen"
        add-button-text="Hinzufügen"
        duplicate-tag-text="Doppelter Eintrag"
        :value="allergien"
        @input="$emit('update:allergien', $event)"
      />
    </b-form-group>

    <b-form-group
      id="gesundheit-krankenkasse-group"
      label-for="gesundheit-krankenkasse-value"
      label="Krankenkasse"
    >
      <b-form-input
        id="gesundheit-krankenkasse-value"
        required
        trim
        :value="krankenkasse"
        @update="$emit('update:krankenkasse', $event)"
      />
    </b-form-group>

    <slot />
  </div>
</template>

<script>
export default {
  name: 'Gesundheit',
  props: {
    allergien: {
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
