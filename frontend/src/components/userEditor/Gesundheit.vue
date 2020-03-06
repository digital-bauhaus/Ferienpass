<template>
  <div>
    <h2>Allergien, Krankheiten, ...</h2>

    <b-form-group
      id="gesundheit-allergien2-group"
      label-for="gesundheit-allergien2-value"
      label="Bei meinem Kind muss auf folgende Allergie(n) geachtet werden:"
    >
      <b-form-tags
        id="gesundheit-allergien2-tags"
        input-id="gesundheit-allergien2-value"
        tag-variant="primary"
        placeholder="z.B. Heuschnupfen"
        add-button-text="Hinzufügen"
        duplicate-tag-text="Doppelter Eintrag"
        :value="allergien"
        @input="$emit('update:allergien', $event)"
      />
    </b-form-group>

    <b-form-group
      id="gesundheit-allergien-group"
      label-for="gesundheit-allergien-value"
      label="Bei meinem Kind muss auf folgende Allergie(n) geachtet werden:"
    >
      <b-form-tags
        id="gesundheit-allergien-tags"
        input-id="gesundheit-allergien-value"
        tag-variant="primary"
        no-outer-focus
        placeholder="z.B. Heuschnupfen"
        add-button-text="Hinzufügen"
        duplicate-tag-text="Doppelter Eintrag"
        :value="allergien"
        @input="$emit('update:allergien', $event)"
      >
        <template
          v-slot="{ tags, inputAttrs, inputHandlers, tagVariant, addTag, removeTag,
                    addButtonText, placeholder }"
        >
          <div class="mb-2">
            <b-form-tag
              v-for="tag in tags"
              :key="tag"
              :title="tag"
              :variant="tagVariant"
              class="mr-1"
              @remove="removeTag(tag)"
            >
              {{ tag }}
            </b-form-tag>
          </div>
          <b-input-group>
            <b-form-input
              v-bind="inputAttrs"
              :placeholder="placeholder"
              v-on="inputHandlers"
            />
            <b-input-group-append>
              <b-button
                variant="primary"
                @click="addTag()"
              >
                {{ addButtonText }}
              </b-button>
            </b-input-group-append>
          </b-input-group>
        </template>
      </b-form-tags>
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
