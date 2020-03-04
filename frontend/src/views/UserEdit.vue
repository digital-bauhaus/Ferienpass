<template>
  <div>
    <NavigationMenu />
    <b-container>
      <h1>
        {{ titleText }}
      </h1>
      <ErrorAlert
        v-if="showServerErrorAlert"
        :heading-text="serverErrorHeadingText"
        :errors="serverErrorMessages"
      />
      <UserEditor
        v-model="user"
        :submit-button-text="submitButtonText"
        @submit="updateUser"
      />
      <b-alert
        class="fixed-bottom w-50 mx-auto"
        :show="successAutomaticDismissCountDown"
        dismissible
        variant="success"
        @dismissed="successAutomaticDismissCountDown=0"
        @dismiss-count-down="successAutomaticDismissCountDown = $event"
      >
        {{ successText }}
      </b-alert>
    </b-container>
  </div>
</template>

<script>
import api from '@/modules/ferienpass-api';
import ErrorAlert from '@/components/ErrorAlert.vue';
import NavigationMenu from '@/components/NavBar.vue';
import UserEditor from '@/components/UserEditor.vue';

export default {
  name: 'UserEdit',
  components: { UserEditor, NavigationMenu, ErrorAlert },
  data() {
    return {
      user: {},
      serverErrorMessages: [],
      successAutomaticDismissCountDown: 0,
    };
  },
  computed: {
    userId() {
      return parseInt(this.$route.query.id, 10);
    },
    titleText() {
      return 'Teilnehmerbearbeitung';
    },
    submitButtonText() {
      return 'Speichern';
    },
    successText() {
      return 'Teilnehmer erfolgreich gespeichert.';
    },
    serverErrorHeadingText() {
      return 'Speichern nicht möglich. Bitte beheben Sie folgende Fehler:';
    },
    showServerErrorAlert() {
      return this.serverErrorMessages.length > 0;
    },
  },
  created() {
    this.loadUserData();
  },
  methods: {
    loadUserData() {
      this.serverErrorMessages = [];
      api.getUser(this.userId).then((user) => {
        this.user = user;
      }).catch((e) => this.serverErrorMessages.push(e.toString()));
    },
    updateUser() {
      this.serverErrorMessages = [];
      api.updateUser(this.user).then(() => {
        this.showSuccessInfo();
      }).catch((errorMessages) => { this.serverErrorMessages = errorMessages; });
    },
    showSuccessInfo() {
      this.successAutomaticDismissCountDown = 5;
    },
  },
};
</script>

<style scoped>
</style>
