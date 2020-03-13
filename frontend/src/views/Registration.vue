<template>
  <RegistrationLayout class="registration">
    <h1>
      {{ titleText }}
    </h1>
    <UserEditor
      v-model="user"
      :disabled="formDisabled"
      :submit-button-text="submitButtonText"
      @submit="createUser"
    >
      <template v-slot:before>
        <CheckBoxGroup base="user-schulkind">
          <CheckBox
            v-model="user.schulkind"
            base="user-schulkind"
            :required="true"
          >
            Mein Kind geht zur Schule *
          </CheckBox>
        </CheckBoxGroup>
      </template>
      <template v-slot:after>
        <FormSection label="Angebote">
          <Angebote>
            <ProjektAuswahl v-if="projectsLoaded">
              <ProjektAuswahlItem
                v-for="projekt in allProjects"
                :key="projekt.id"
                :projekt="projekt"
                :geburtsdatum="user.geburtsdatum"
                :alle-projekte="allProjects"
                :gewuenschte-projekte="gewuenschteProjekte"
                :disabled="formDisabled"
                :checked="gewuenschteProjekte[projekt.id]"
                @input="updateGewuenschtesProjekt(projekt.id, $event)"
              />
            </ProjektAuswahl>
          </Angebote>
        </FormSection>

        <FormSection label="Datenschutzerklärung">
          <Datenschutz />
        </FormSection>

        <FormSection label="Teilnahmebedingungen">
          <Teilnahmebedingungen />
        </FormSection>

        <CheckBoxGroup
          base="akzeptanz"
          :disabled="formDisabled"
        >
          <CheckBox
            v-model="user.datenschutzErklaerungAkzeptiert"
            base="datenschutzErklaerungAkzeptiert"
            :required="true"
          >
            Ich habe die Datenschutzerklärung gelesen und akzeptiert. *
          </CheckBox>
          <CheckBox
            v-model="user.teilnahmeBedingungAkzeptiert"
            base="teilnahmeBedingungAkzeptiert"
            :required="true"
          >
            Ich habe die Teilnahmebedingungen gelesen und akzeptiert. Ich bestätige die
            Richtigkeit meiner Angaben. Wurden wissentlich falsche Angaben gemacht, darf die
            Organisation das angemeldete Kind von den Angeboten ausschließen. *
          </CheckBox>
        </CheckBoxGroup>
      </template>
    </UserEditor>
  </RegistrationLayout>
</template>

<script>
import publicApi from '@/modules/ferienpass-public-api';
import UserEditor from '@/components/UserEditor.vue';
import CheckBox from '@/components/form/CheckBox.vue';
import CheckBoxGroup from '@/components/form/CheckBoxGroup.vue';
import RegistrationLayout from '@/views/layouts/RegistrationLayout.vue';
import ProjektAuswahl from '@/components/userEditor/ProjektAuswahl.vue';
import ProjektAuswahlItem from '@/components/userEditor/ProjektAuswahlItem.vue';
import { defaultUser } from '@/modules/models';
import {
  FailureDialog,
  SuccessDialog,
  TechnicalProblemsDialog,
  TechnicalProblemsModal,
} from '@/modules/sweet-alert';
import FormSection from '@/components/form/FormSection.vue';
import Angebote from '@/components/userEditor/Angebote.vue';
import Datenschutz from '@/components/userEditor/Datenschutz.vue';
import Teilnahmebedingungen from '@/components/userEditor/Teilnahmebedingungen.vue';

export default {
  name: 'Registration',
  components: {
    Teilnahmebedingungen,
    Datenschutz,
    Angebote,
    FormSection,
    ProjektAuswahlItem,
    ProjektAuswahl,
    RegistrationLayout,
    CheckBoxGroup,
    CheckBox,
    UserEditor,
  },
  data() {
    return {
      user: defaultUser,
      gewuenschteProjekte: {},
      allProjects: [],
      projectsLoaded: false,
    };
  },
  computed: {
    formDisabled() {
      return !this.user.schulkind;
    },
    titleText() {
      return 'Ferienpass Weimar – Anmeldung';
    },
    submitButtonText() {
      return 'Absenden';
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
      return publicApi.getProjects().then((projects) => {
        this.initGewuenschteProjekte(projects);
        this.allProjects = projects;
        this.projectsLoaded = true;
      }).catch(() => {
        TechnicalProblemsModal.fire();
      });
    },
    reloadProjects() {
      return publicApi.getProjects().then((projects) => {
        this.projectsLoaded = false;
        this.updateGewuenschteProjekte(projects);
        this.allProjects = projects;
        this.projectsLoaded = true;
      }).catch(() => {
        TechnicalProblemsModal.fire();
      });
    },
    createUser() {
      const gewuenschteProjekteIds = this.mapGewuenschteProjekteToIdArray();
      publicApi.registerUser({
        ...this.user,
        gewuenschteProjekte: gewuenschteProjekteIds,
      }).then(() => {
        this.handleRegistrationSuccess();
      }).catch((error) => {
        this.handleRegistrationError(error);
      });
    },
    initGewuenschteProjekte(projectsFromServer) {
      const gewuenschteProjekte = {};
      projectsFromServer.forEach((project) => {
        gewuenschteProjekte[project.id] = false;
      });
      this.gewuenschteProjekte = gewuenschteProjekte;
      console.log(JSON.stringify(this.gewuenschteProjekte));
    },
    updateGewuenschteProjekte(projectsFromServer) {
      console.log(JSON.stringify(this.gewuenschteProjekte));
      const updatedGewuenschteProjekte = {};
      projectsFromServer.forEach((project) => {
        updatedGewuenschteProjekte[project.id] = false;
      });
      Object.entries(this.gewuenschteProjekte).forEach(([key, value]) => {
        if (projectsFromServer.some((project) => `${project.id}` === key)) {
          updatedGewuenschteProjekte[key] = value;
        }
      });
      this.gewuenschteProjekte = updatedGewuenschteProjekte;
      console.log(JSON.stringify(this.gewuenschteProjekte));
    },
    updateGewuenschtesProjekt(projektId, newValue) {
      this.gewuenschteProjekte[projektId] = newValue;
    },
    mapGewuenschteProjekteToIdArray() {
      return Object.entries(this.gewuenschteProjekte)
      // eslint-disable-next-line no-unused-vars
        .filter(([key, value]) => value).map(([key, value]) => key);
    },
    handleRegistrationSuccess() {
      SuccessDialog.fire({
        html: 'Ihre Anmeldung war erfolgreich!<br>Sie erhalten eine eMail mit der Zahlungsaufforderung.',
      });
    },
    handleRegistrationError(error) {
      if (error?.response?.status === 409) {
        // fully booked
        FailureDialog.fire({
          icon: 'warning',
          titleText: 'Oh nein!',
          html: 'Leider ist eines der gewählten Angebote schon ausgebucht.<br>Bitte überprüfen Sie ihre Auswahl und senden Sie die Anmeldung erneut ab.',
        }).then(() => {
          // we wait until the dialog is closed, so we do not show two modals at the same time if
          // there is an error when loading the projects
          this.reloadProjects();
        });
      } else {
        this.handleCommonServerError(error);
      }
    },
    handleCommonServerError(error) {
      // see: https://github.com/axios/axios#handling-errors
      if (error.response) {
        console.log(error.response);
        switch (error.response.status) {
          case 400:
            if (error.response.data?.errors) {
              // validation error
              FailureDialog.fire({
                icon: 'warning',
                titleText: 'Bitte korrigieren Sie folgende Fehler: ',
                html: this.buildListHtmlFromErrors(error.response.data.errors),
              });
            } else {
              // other spring errorMessages
              TechnicalProblemsDialog.fire();
            }
            break;
          case 422:
            // custom validation error
            FailureDialog.fire({
              icon: 'warning',
              text: error.response.data.message,
            });
            break;
          default:
            TechnicalProblemsDialog.fire();
            break;
        }
      } else {
        // Problem is with Axios or we got no response at all
        TechnicalProblemsDialog.fire();
      }
    },
    buildListHtmlFromErrors(errors) {
      let errorHtml = '<ul class="text-left">';
      errors.forEach((error) => {
        errorHtml += `<li>${error.defaultMessage}</li>`;
      });
      errorHtml += '</ul>';
      return errorHtml;
    },
  },
};
</script>

<style scoped>

</style>
