<template>
  <div
    class="dynamic-list"
    :aria-labelledby="`${toIdentifier(params.title)}-label`"
  >
    <h3
      :id="`${toIdentifier(params.title)}-label`"
      class="dynamic-list__title"
      tabindex="-1"
    >
      {{ params.title }}
    </h3>

    <ul class="dynamic-list__items">
      <li
        v-for="(item, index) of listItems"
        :key="index"
        class="list-item"
      >
        <span class="list-item__content">
          {{ item }}
          <input
            type="hidden"
            :name="`${params.name}-${index}`"
            :value="item"
          >
        </span>

        <button
          class="list-item__control button button--inline delete-button"
          type="button"
          :aria-label="`Entferne ${item}.`"
          @keydown.enter="remove(index, item)"
          @click="remove(index, item)"
        >
          ×
        </button>
      </li>
    </ul>

    <div class="dynamic-list__empty-message">
      {{ params.emptyMessage }}
    </div>

    <div class="add-item">
      <div class="add-item__label">
        <text-field
          :params="params.textField.params"
          :value="newItem"
          @input="updateValue"
          @enter="handleEnter"
        />
      </div>

      <button
        class="button add-item__control"
        type="button"
        :disabled="inputIsEmpty"
        @click="add"
      >
        Hinzufügen
      </button>
    </div>

    <div
      class="visually-hidden"
      role="status"
      aria-live="polite"
    >
      {{ feedback }}
    </div>
  </div>
</template>

<script>
import TextField from './TextField';

export default {
  name: 'DynamicList',
  components: {
    TextField,
  },
  props: ['params'],
  data() {
    return {
      newItem: '',
      feedback: '',
      listItems: [],
    };
  },
  computed: {
    inputIsEmpty() {
      return this.newItem.trim() === '';
    },
  },
  methods: {
    updateValue(event) {
      this.newItem = event.target.value;
    },
    handleEnter() {
      if (!this.inputIsEmpty) {
        this.add();
      }
    },
    add() {
      this.listItems.push(this.newItem);
      this.feedback = `${this.newItem} hinzugefügt.`;
      this.newItem = '';
    },
    remove(index, item) {
      this.listItems.splice(index, 1);
      document.querySelector(`#${this.toIdentifier(this.params.title)}-label`).focus();
      this.feedback = `${item} entfernt.`;
    },
  },
};
</script>


<style scoped>
.dynamic-list__title {
  margin-bottom: 0.75rem;
}

.dynamic-list__items:empty,
.dynamic-list__items:not(:empty) ~ .dynamic-list__empty-message {
  display: none;
}

.dynamic-list__items {
  margin-top: 0;
  margin-bottom: 0;
}

.list-item {
  display: flex;
}

.list-item::before {
  content: '\2012';
  position: relative;
  margin-right: 0.75rem;
}

.list-item:not(:last-child) {
  margin-bottom: 0.75rem;
}

.list-item__content {
  flex-grow: 1;
}

.add-item {
  margin-top: 0.75rem;
  display: flex;
  align-items: start;
}

.add-item__label {
  flex-grow: 1;
  margin-right: 1rem;
}
</style>
