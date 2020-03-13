<template>
  <RegistrationLayout class="registration">
    <h1>
      {{ titleText }}
    </h1>
    <CheckBoxGroup base="user-schulkind">
      <CheckBox
        v-model="user.schulkind"
        base="user-schulkind"
        :required="true"
      >
        Mein Kind geht zur Schule *
      </CheckBox>
    </CheckBoxGroup>
    <UserEditor
      v-model="user"
      :disabled="!user.schulkind"
      :submit-button-text="submitButtonText"
      @submit="createUser"
    >
      <ProjektAuswahl v-if="loaded">
        <ProjektAuswahlItem
          v-for="projekt in allProjects"
          :key="projekt.id"
          :projekt="projekt"
          :geburtsdatum="user.geburtsdatum"
          :alle-projekte="allProjects"
          :gewuenschte-projekte="gewuenschteProjekte"
          :disabled="!user.schulkind"
          :checked="gewuenschteProjekte[projekt.id]"
          @input="updateGewuenschtesProjekt(projekt.id, $event)"
        />
      </ProjektAuswahl>
    </UserEditor>
    <ErrorBox
      v-if="showServerErrorAlert"
      :heading-text="serverErrorHeadingText"
      :errors="serverErrorMessages"
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
import publicApi from '@/modules/ferienpass-public-api';
import ErrorBox from '@/components/ErrorBox.vue';
import UserEditor from '@/components/UserEditor.vue';
import CheckBox from '@/components/form/CheckBox.vue';
import CheckBoxGroup from '@/components/form/CheckBoxGroup.vue';
import RegistrationLayout from '@/views/layouts/RegistrationLayout.vue';
import ProjektAuswahl from '@/components/userEditor/ProjektAuswahl.vue';
import ProjektAuswahlItem from '@/components/userEditor/ProjektAuswahlItem.vue';
import { defaultUser } from '@/modules/models';
import { SuccessDialog } from '@/modules/sweet-alert';

export default {
  name: 'Registration',
  components: {
    ProjektAuswahlItem,
    ProjektAuswahl,
    RegistrationLayout,
    CheckBoxGroup,
    CheckBox,
    UserEditor,
    ErrorBox,
  },
  data() {
    return {
      user: defaultUser,
      gewuenschteProjekte: {},
      allProjects: [],
      loaded: false,
      serverErrorMessages: [],
      successAutomaticDismissCountDown: 0,
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
      return publicApi.getProjects().then((projects) => {
        this.allProjects = projects;
        this.initGewuenschteProjekteHelper();
        this.loaded = true;
      });
      // TODO errorhandling
    },
    createUser() {
      this.serverErrorMessages = [];
      const gewuenschteProjekteIds = Object.entries(this.gewuenschteProjekte)
        .filter((entry) => entry[1]).map((entry) => entry[0]);
      publicApi.registerUser({
        ...this.user,
        gewuenschteProjekte: gewuenschteProjekteIds,
      }).then(() => {
        this.showSuccessInfo();
      }).catch((errorMessages) => { this.serverErrorMessages = errorMessages; });
      // TODO errorhandling
    },
    initGewuenschteProjekteHelper() {
      this.allProjects.forEach((project) => {
        this.gewuenschteProjekte[project.id] = false;
      });
    },
    updateGewuenschtesProjekt(projektId, newValue) {
      // we need to completely replace the object so the single projectItems notice the change
      // this.$set(this.gewuenschteProjekte, projektId, newValue);
      this.gewuenschteProjekte = {
        ...this.gewuenschteProjekte,
        [projektId]: newValue,
      };
    },
    showSuccessInfo() {
      SuccessDialog.fire({
        html: 'Ihre Anmeldung war erfolgreich!<br>Sie erhalten eine eMail mit der Zahlungsaufforderung.',
      });
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
