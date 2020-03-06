<template>
  <div>
    <h3 v-if="false">{{ heading }}</h3>
    <b-form-group
      :id="`${base}-group`"
      :label-for="`${base}-value`"
      :label="label"
    >
      <b-form-tags
        :id="`${base}-tags`"
        :input-id="`${base}-value`"
        tag-variant="primary"
        no-outer-focus
        :placeholder="placeholder"
        add-button-text="Hinzufügen"
        duplicate-tag-text="Doppelter Eintrag"
        :value="value"
        @input="$emit('input', $event)"
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
  </div>
</template>

<script>
export default {
  name: 'DynamicInputList',
  model: {
    prop: 'value',
    event: 'input',
  },
  props: {
    base: {
      type: String,
      required: true,
    },
    value: {
      type: Array,
      default: () => [],
    },
    heading: {
      type: String,
      required: true,
    },
    label: {
      type: String,
      required: true,
    },
    placeholder: {
      type: String,
      default: '',
    },
  },
};
</script>

<style scoped>

</style>
