<template>
  <RegistrationLayout class="registration">
    <h1>
      {{ titleText }}
    </h1>
    <ErrorAlert
      v-if="showServerErrorAlert"
      :heading-text="serverErrorHeadingText"
      :errors="serverErrorMessages"
    />
    <CheckBoxGroup base="schoolkid">
      <CheckBox
        v-model="isSchoolKid"
        base="schoolkid"
        :required="true"
      >
        Mein Kind geht zur Schule
      </CheckBox>
    </CheckBoxGroup>
    <UserEditor
      v-model="user"
      :disabled="!isSchoolKid"
      :submit-button-text="submitButtonText"
      @submit="createUser"
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
  </RegistrationLayout>
</template>

<script>
import api from '@/modules/ferienpass-api';
import ErrorAlert from '@/components/ErrorAlert.vue';
import UserEditor from '@/components/UserEditor.vue';
import CheckBox from '@/components/wrapper/CheckBox.vue';
import CheckBoxGroup from '@/components/wrapper/CheckBoxGroup.vue';
import RegistrationLayout from '@/views/layouts/RegistrationLayout.vue';

export default {
  name: 'Registration',
  components: {
    RegistrationLayout,
    CheckBoxGroup,
    CheckBox,
    UserEditor,
    ErrorAlert,
  },
  data() {
    return {
      user: {
        notfallKontakt: {},
        arzt: {},
        behinderung: {},
      },
      serverErrorMessages: [],
      successAutomaticDismissCountDown: 0,
      isSchoolKid: false,
      allProjects: [],
      loaded: false,
    };
  },
  computed: {
    titleText() {
      return 'Ferienpass Weimar – Anmeldung';
    },
    submitButtonText() {
      return 'Absenden';
    },
    successText() {
      return 'Anmeldung erfolgreich.';
    },
    serverErrorHeadingText() {
      // TODO ???
      return 'Anmeldung war nicht erfolgreich. Bitte beheben Sie folgende Fehler:';
    },
    showServerErrorAlert() {
      return this.serverErrorMessages.length > 0;
    },
    availableProjects() {
      return this.allProjects.filter((project) => {
        const isProjectOfUser = this.projectsOfUser.map((userProject) => userProject.id).includes(
          project.id,
        );
        const isCancelledProjectOfuser = this.cancelledProjectsOfUser.map(
          (userProject) => userProject.id,
        ).includes(project.id);
        return !isProjectOfUser && !isCancelledProjectOfuser;
      });
    },
  },
  created() {
    this.loadProjects();
  },
  methods: {
    loadProjects() {
      // TODO we need to split the project api into /projects and /public/projects
      return api.getProjects().then((projects) => { this.allProjects = projects; });
    },
    createUser() {
      this.serverErrorMessages = [];
      api.updateUser(this.user).then(() => {
        this.showSuccessInfo();
      }).catch((errorMessages) => { this.serverErrorMessages = errorMessages; });
    },
    showSuccessInfo() {
      this.successAutomaticDismissCountDown = 5;
    },
    modalSuccess() {
      this.$swal('Geschafft!',
        'Deine Anmeldung war erfolgreich!\n Sie erhalten eine eMail mit der Zahlungsaufforderung.',
        'success');
    },
    modalProjectOverbooked() {
      this.$swal('Oh nein!', 'Eines der Angebote ist leider schon belegt!', 'warning');
    },
  },
};
</script>

<style scoped>

</style>
