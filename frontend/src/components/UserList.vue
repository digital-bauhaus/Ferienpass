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
    <template v-slot:cell(projects)="row">
      <ul v-if="projectsLoaded">
        <li
          v-for="projectName of projectNamesByUserId[row.item.id]"
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
import api from '../modules/ferienpass-api';
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
        { key: 'hausNummer', label: 'Hausnummer', sortable: true },
        { key: 'postleitzahl', label: 'Postleitzahl', sortable: true },
        { key: 'stadt', label: 'Wohnort', sortable: true },
        { key: 'telefon', label: 'Telefon', sortable: true },
        { key: 'email', label: 'Email', sortable: true },
        this.showProjects && { key: 'projects', label: 'Projekte', sortable: false },
        { key: 'actions', label: 'Aktionen', sortable: false },
      ],
    };
  },
  created() {
    if (this.showProjects) {
      this.loadProjectsOfUsers();
    }
  },
  methods: {
    formatDate(stringDate) {
      return dayjs(stringDate).format(SHORT_DATE_FORMAT);
    },
    loadProjectsOfUsers() {
      this.serverErrorMessages = [];
      // instead of one api-call per user,
      // we request ALL projects and build a lookup-table ourselves
      api.getProjects().then((projects) => {
        this.users.forEach((user) => {
          this.projectNamesByUserId[user.id] = this.findProjectNamesForUserId(projects, user.id);
        });
        this.projectsLoaded = true;
      }).catch((e) => this.serverErrorMessages.push(e));
    },
    findProjectNamesForUserId(projects, userId) {
      const projectNames = [];
      projects.forEach((project) => {
        if (project.anmeldungen.map((user) => user.id).includes(userId)) {
          projectNames.push(project.name);
        }
      });
      return projectNames;
    },

  },
};
</script>

<style scoped>

</style>
