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
import { DeleteDialog, FailureToast, SuccessDialog, } from '@/modules/sweet-alert';
import handleCommonServerError from '@/modules/error-handling';

export default {
  name: 'Users',
  components: { UserList, BaseLayout },
  data() {
    return {
      users: [],
    };
  },
  created() {
    this.loadUsers();
  },
  methods: {
    loadUsers() {
      api.getUsers().then((users) => { this.users = users; })
        .catch(() => {
          FailureToast.fire({
            text: 'Fehler: Teilnehmer konnten nicht geladen werden.',
          });
        });
    },
    deleteUser(userId) {
      DeleteDialog.fire({
        text: 'Der Teilnehmer wird vollständig gelöscht und die Daten sind verloren! Er muss sich über die Anmeldung wieder NEU anmelden!',
      }).then((result) => {
        if (result.value) {
          this.doDeleteUser(userId);
        }
      });
    },
    doDeleteUser(userId) {
      api.deleteUser(userId).then(() => {
        SuccessDialog.fire({
          text: 'Teilnehmer wurde gelöscht!',
        });
        this.loadUsers();
      }).catch((error) => {
        handleCommonServerError(error);
      });
    },
  },
};
</script>

<style scoped>
</style>
