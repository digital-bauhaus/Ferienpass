<template>
  <b-table
    striped
    hover
    outlined
    :responsive="true"
    primary-key="id"
    :items="projects"
    :fields="fields"
  >
    <template v-slot:cell(actions)="row">
      <b-button
        size="sm"
        :to="{path: '../VeranstaltungEdit', query: {id: row.item.id }}"
      >
        Bearbeiten
      </b-button>
      <b-button
        size="sm"
        @click="deleteProject(row.item.id)"
      >
        Löschen
      </b-button>
    </template>
  </b-table>
</template>

<script>
import api from '../modules/ferienpass-api';

export default {
  name: 'ProjectListNew',
  props: {
    projects: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      errors: [],
      fields: [
        { key: 'name', label: 'Name', sortable: true },
        { key: 'datum', label: 'Von', sortable: true },
        { key: 'datumEnde', label: 'Bis', sortable: true },
        { key: 'slotsFrei', label: '#Frei', sortable: true },
        { key: 'slotsGesamt', label: '#Gesamt', sortable: true },
        { key: 'slotsReserviert', label: '#Reserviert', sortable: true },
        { key: 'mindestAlter', label: 'Mindestalter', sortable: true },
        { key: 'hoechstAlter', label: 'Höchstalter', sortable: true },
        { key: 'actions', label: 'Aktionen', sortable: false },
      ],
    };
  },
  mounted() {
    console.log(this.projects);
  },
  methods: {
    deleteProject(projectId) {
      this.$swal({
        title: 'Wirklich löschen?',
        text: 'Das Projekt wird vollständig gelöscht!',
        icon: 'warning',
        buttons: true,
        dangerMode: true,
      })
        .then((willDelete) => {
          if (willDelete) {
            this.errors = [];
            api.deleteProject(projectId).then(() => {
              this.$emit('project-deleted');
              return this.$swal('Projekt wurde gelöscht!', {
                icon: 'success',
              });
            }).catch((e) => {
              this.errors.push(e);
              return this.$swal('Da ist was schief gegangen :(', {
                icon: 'error',
              });
            });
          }
        });
    },
  },
};
</script>

<style scoped>

</style>
