<template>
  <BaseLayout class="projects">
    <h1>
      Übersicht Teilnehmer
    </h1>
    <UserList
      :users="users"
      :show-projects="true"
    >
      <template v-slot:actions="{ row }">
        <b-button
          size="sm"
          class="m-1"
          :to="{path: '../UserEdit', query: {id: row.item.id }}"
        >
          Bearbeiten
        </b-button>
        <b-button
          size="sm"
          class="m-1"
          variant="danger"
          @click="deleteUser(row.item.id)"
        >
          Löschen
        </b-button>
      </template>
    </UserList>
  </BaseLayout>
</template>

<script>
import api from '@/modules/ferienpass-api';
import BaseLayout from '@/views/layouts/BaseLayout.vue';
import UserList from '@/components/UserList.vue';

export default {
  name: 'Users',
  components: { UserList, BaseLayout },
  data() {
    return {
      serverErrorMessages: [],
      users: [],
    };
  },
  created() {
    this.loadProjects();
  },
  methods: {
    loadProjects() {
      this.serverErrorMessages = [];
      api.getUsers().then((users) => { this.users = users; })
        .catch((e) => this.serverErrorMessages.push(e));
    },
    deleteUser(userId) {
      this.$swal({
        title: 'Wirklich löschen?',
        text: 'Der Teilnehmer wird vollständig gelöscht und die Daten sind verloren! Er muss sich über die Anmeldung wieder NEU anmelden!',
        icon: 'warning',
        buttons: true,
        dangerMode: true,
      })
        .then((willDelete) => {
          if (willDelete) {
            this.errors = [];
            api.deleteUser(userId).then(() => this.$swal('Teilnehmer wurde gelöscht!', {
              icon: 'success',
            })).catch((e) => {
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
