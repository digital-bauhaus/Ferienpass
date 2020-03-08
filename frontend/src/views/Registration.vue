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
    >
      <ProjektAuswahl v-if="loaded">
        <ProjektAuswahlItem
          v-for="projekt in allProjects"
          :key="projekt.id"
          :projekt="projekt"
          :geburtsdatum="user.geburtsdatum"
          :alle-projekte="allProjects"
          :gewuenschte-projekte="gewuenschteProjekte"
          :checked="gewuenschteProjekte[projekt.id]"
          @input="updateGewuenschtesProjekt(projekt.id, $event)"
        />
      </ProjektAuswahl>
    </UserEditor>

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
import ProjektAuswahl from '@/components/userEditor/ProjektAuswahl.vue';
import ProjektAuswahlItem from '@/components/userEditor/ProjektAuswahlItem.vue';


export default {
  name: 'Registration',
  components: {
    ProjektAuswahlItem,
    ProjektAuswahl,
    RegistrationLayout,
    CheckBoxGroup,
    CheckBox,
    UserEditor,
    ErrorAlert,
  },
  data() {
    return {
      user: {
        vorname: '',
        nachname: '',
        geburtsdatum: '',
        strasse: '',
        stadt: '',
        postleitzahl: '',
        telefon: '',
        email: '',
        darfBehandeltWerden: null,
        darfAlleinNachHause: null,
        darfReiten: null,
        darfSchwimmen: null,
        schwimmAbzeichen: '',
        allergien: '',
        medikamente: '',
        krankheiten: '',
        hitzeempfindlichkeiten: false,
        krankenkasse: '',
        vegetarier: false,
        laktoseUnvertraeglichkeit: false,
        eierUnvertraeglichkeit: false,
        essenLimitierungen: '',
        liegtBehinderungVor: false,
        notfallKontakt: {
          name: '',
          address: '',
          telephone: '',
        },
        arzt: {
          name: '',
          address: '',
          telephone: '',
        },
        behinderung: {
          merkzeichen_AussergewoehnlicheGehbehinderung_aG: false,
          merkzeichen_Hilflosigkeit_H: false,
          merkzeichen_Blind_Bl: false,
          merkzeichen_Gehoerlos_Gl: false,
          merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B: false,
          merkzeichen_BeeintraechtigungImStrassenverkehr_G: false,
          merkzeichen_Taubblind_TBL: false,
          rollstuhlNutzungNotwendig: false,
          weitereHilfsmittel: '',
          wertmarkeVorhanden: false,
          begleitungNotwendig: false,
          begleitpersonPflege: false,
          begleitpersonMedizinischeVersorgung: false,
          begleitpersonMobilitaet: false,
          begleitpersonOrientierung: false,
          begleitpersonSozialeBegleitung: false,
          eingeschraenkteSinne: '',
          hinweiseZumUmgangMitDemKind: '',
          unterstuetzungSucheBegleitpersonNotwendig: false,
          gewohnterBegleitpersonenDienstleister: '',
          beantragungKostenuebernahmeBegleitpersonNotwendig: false,
        },
      },
      serverErrorMessages: [],
      successAutomaticDismissCountDown: 0,
      isSchoolKid: false,
      allProjects: [],
      loaded: false,
      gewuenschteProjekte: {},
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
      return api.getProjects().then((projects) => {
        this.allProjects = projects;
        this.initGewuenschteProjekteHelper();
        this.loaded = true;
      });
    },
    createUser() {
      this.serverErrorMessages = [];
      const gewuenschteProjekteIds = Object.entries(this.gewuenschteProjekte)
        .filter((entry) => entry[1]).map((entry) => entry[0]);
      api.registerUser({
        ...this.user,
        gewuenschteProjekte: gewuenschteProjekteIds,
      }).then(() => {
        this.showSuccessInfo();
      }).catch((errorMessages) => { this.serverErrorMessages = errorMessages; });
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
