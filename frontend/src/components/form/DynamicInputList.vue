<template>
  <b-form-group
    :id="`${base}-group`"
    class="dynamic-input-list"
    :label-for="`${base}-value`"
    :label="label"
  >
    <b-form-tags
      :id="`${base}-tags`"
      class="dynamic-input-list__tags"
      :input-id="`${base}-value`"
      no-outer-focus
      :placeholder="placeholder"
      add-button-text="Hinzufügen"
      duplicate-tag-text="Doppelter Eintrag"
      :separator="separator"
      add-on-change
      :value="value"
      @input="$emit('input', $event)"
    >
      <template
        v-slot="{ tags, inputAttrs, inputHandlers, addTag, removeTag,
                  addButtonText, placeHolder: placeholderProp }"
      >
        <div class="mb-2">
          <b-form-tag
            v-for="tag in tags"
            :key="tag"
            :title="tag"
            :disabled="disabled"
            class="mr-1"
            @remove="removeTag(tag)"
          >
            {{ tag }}
          </b-form-tag>
        </div>
        <b-input-group>
          <b-form-input
            v-bind="inputAttrs"
            :placeholder="placeholderProp"
            :disabled="disabled"
            v-on="inputHandlers"
          />
          <b-input-group-append>
            <b-button
              :disabled="disabled"
              @click="addTag()"
            >
              {{ addButtonText }}
            </b-button>
          </b-input-group-append>
        </b-input-group>
      </template>
    </b-form-tags>
  </b-form-group>
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
    disabled: {
      type: Boolean,
      default: false,
    },
    value: {
      type: Array,
      default: () => [],
    },
    label: {
      type: String,
      required: true,
    },
    placeholder: {
      type: String,
      default: '',
    },
    separator: {
      type: String,
      default: null,
    },
  },
};
</script>

<style scoped lang="scss">
@import "../../design/variables.scss";

.dynamic-input-list__tags {
  border: none;
}

</style>
