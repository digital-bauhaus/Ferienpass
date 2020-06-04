<template>
  <b-table
    striped
    hover
    outlined
    :responsive="true"
    primary-key="id"
    :fields="fields"
    :items="users"
    :sticky-header="'700px'"
  >
    <template v-slot:cell(bezahlt)="row">
      <b-checkbox
        disabled
        :checked="row.item.bezahlt"
      />
    </template>
    <template v-slot:cell(vorname)="row">
      <b-link
        :to="{path: '../UserEdit', query: {id: row.item.id }}"
      >
        {{ row.item.vorname }}
      </b-link>
    </template>
    <template v-slot:cell(nachname)="row">
      <b-link
        :to="{path: '../UserEdit', query: {id: row.item.id }}"
      >
        {{ row.item.nachname }}
      </b-link>
    </template>
    <template v-slot:cell(projects)="row">
      <ul>
        <li
          v-for="projectName of row.item.angemeldeteProjektNamen"
          :key="projectName"
        >
          {{ projectName }}
        </li>
      </ul>
    </template>
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
  name: 'UserList',
  props: {
    users: {
      type: Array,
      required: true,
    },
    showProjects: {
      type: Boolean,
      required: false,
      default: false,
    },
  },
  data() {
    return {
      serverErrorMessages: [],
      projectNamesByUserId: [],
      projectsLoaded: false,
      fields: [
        { key: 'bezahlt', label: 'Bezahlt', sortable: true },
        { key: 'vorname', label: 'Vorname', sortable: true },
        { key: 'nachname', label: 'Nachname', sortable: true },
        {
          key: 'registrierungsdatum', label: 'Registrierungsdatum', sortable: true, formatter: 'formatDate',
        },
        {
          key: 'geburtsdatum', label: 'Geburtsdatum', sortable: true, formatter: 'formatDate',
        },
        { key: 'strasse', label: 'Straße', sortable: true },
        { key: 'hausnummer', label: 'Hausnummer', sortable: true },
        { key: 'postleitzahl', label: 'Postleitzahl', sortable: true },
        { key: 'wohnort', label: 'Wohnort', sortable: true },
        { key: 'telefon', label: 'Telefon', sortable: true },
        { key: 'email', label: 'Email', sortable: true },
        this.showProjects && { key: 'projects', label: 'Projekte', sortable: false },
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
