<template>
  <b-table
    striped
    hover
    outlined
    :responsive="true"
    primary-key="id"
    :items="projects"
    :fields="fields"
    :sticky-header="'700px'"
  >
    <template v-slot:cell(actions)="row">
      <slot
        name="actions"
        :row="row"
      />
    </template>
  </b-table>
</template>

<script>
import dayjs, { SHORT_DATE_FORMAT } from '../modules/dayjs';

export default {
  name: 'ProjectList',
  props: {
    projects: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      fields: [
        { key: 'name', label: 'Name', sortable: true },
        {
          key: 'datumBeginn', label: 'Von', sortable: true, formatter: 'formatDate',
        },
        {
          key: 'datumEnde', label: 'Bis', sortable: true, formatter: 'formatDate',
        },
        { key: 'plaetzeFrei', label: '#Frei', sortable: true },
        { key: 'plaetzeGesamt', label: '#Gesamt', sortable: true },
        { key: 'plaetzeReserviert', label: '#Reserviert', sortable: true },
        { key: 'mindestAlter', label: 'Mindestalter', sortable: true },
        { key: 'hoechstAlter', label: 'Höchstalter', sortable: true },
        { key: 'actions', label: 'Aktionen', sortable: false },
      ],
    };
  },
  methods: {
    formatDate(stringDate) {
      return dayjs(stringDate).format(SHORT_DATE_FORMAT);
    },
  },
};
</script>

<style scoped>

</style>
