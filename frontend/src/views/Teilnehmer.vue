<template>
  <div>
    <NavigationMenu />
    <main>
      <h1>Teilnehmerübersicht</h1>
      <UserList
        :users="users"
        :show-projects="true"
        @user-deleted="loadUsers"
      />
    </main>
  </div>
</template>

<script>
import api from '@/modules/ferienpass-api';
import NavigationMenu from '@/components/NavigationMenu.vue';
import UserList from '@/components/UserList.vue';

export default {
  name: 'Teilnehmer',
  components: { UserList, NavigationMenu },
  data() {
    return {
      errors: [],
      users: [],
    };
  },
  created() {
    this.loadUsers();
  },
  methods: {
    loadUsers() {
      this.errors = [];
      api.getUsers().then((users) => { this.users = users; }).catch((e) => this.errors.push(e));
    },
  },
};
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

main {
  width: 80%;
  position: absolute;
  right: 0px;
  height: 100%;
}

</style>
