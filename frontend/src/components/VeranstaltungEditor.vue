<template>
  <div>
    <h1>
      {{ titleText }}
    </h1>

    <ErrorAlert
      v-if="showErrorAlert"
      :heading-text=errorHeadingText
      :errors="errorMessages"
    />

    <b-form
      ref="form"
      novalidate
      :validated="showValidationStatus"
      @submit="onSubmit"
    >
      <b-form-row>
        <b-col sm="12">
          <b-form-group
            id="veranstaltung-name-group"
            label-for="veranstaltung-name-value"
            label="Name"
          >
            <b-form-input
              id="veranstaltung-name-value"
              v-model.trim="veranstaltung.name"
              required
              placeholder="Name der Veranstaltung"
            />
          </b-form-group>
        </b-col>

        <b-col sm="6">
          <b-form-group
            id="veranstaltung-begindate-group"
            label-for="veranstaltung-begindate-value"
            label="Beginndatum"
          >
            <b-form-input
              id="veranstaltung-begindate-value"
              v-model.trim="veranstaltung.datum"
              required
              placeholder="Datum (TT.MM.JJJ)"
              type="date"
            />
          </b-form-group>
        </b-col>
        <b-col sm="6">
          <b-form-group
            id="veranstaltung-enddate-group"
            label-for="veranstaltung-enddate-value"
            label="Enddatum"
          >
            <b-form-input
              id="veranstaltung-enddate-value"
              v-model.trim="veranstaltung.datumEnde"
              required
              placeholder="Datum (TT.MM.JJJ)"
              type="date"
            />
          </b-form-group>
        </b-col>

        <b-col sm="6">
          <b-form-group
            id="veranstaltung-slotstotal-group"
            label-for="veranstaltung-slotstotal-value"
            label="Plätze (gesamt)"
          >
            <b-form-input
              id="veranstaltung-slotstotal-value"
              v-model.trim="veranstaltung.slotsGesamt"
              required
              placeholder="Plätze"
              type="number"
            />
          </b-form-group>
        </b-col>
        <b-col sm="6">
          <b-form-group
            id="veranstaltung-slotsreserved-group"
            label-for="veranstaltung-slotsreserved-value"
            label="Plätze (reserviert)"
          >
            <b-form-input
              id="veranstaltung-slotsreserved-value"
              v-model.trim="veranstaltung.slotsReserviert"
              placeholder="Reservierte Plätze"
              type="number"
            />
          </b-form-group>
        </b-col>

        <b-col sm="6">
          <b-form-group
            id="veranstaltung-minage-group"
            label-for="veranstaltung-minage-value"
            label="Mindestalter"
          >
            <b-form-input
              id="veranstaltung-minage-value"
              v-model.trim="veranstaltung.mindestAlter"
              required
              placeholder="Altersbegrenzung"
              type="number"
            />
          </b-form-group>
        </b-col>
        <b-col sm="6">
          <b-form-group
            id="veranstaltung-maxage-group"
            label-for="veranstaltung-maxage-value"
            label="Höchstalter"
          >
            <b-form-input
              id="veranstaltung-maxage-value"
              v-model.trim="veranstaltung.hoechstAlter"
              required
              placeholder="Altersbegrenzung"
              type="number"
            />
          </b-form-group>
        </b-col>
      </b-form-row>

      <b-button
        type="submit"
        variant="primary"
      >
        {{ submitButtonText }}
      </b-button>
    </b-form>

    <b-alert
      class="fixed-bottom w-50 mx-auto"
      :show="dismissCountDown"
      dismissible
      variant="success"
      @dismissed="dismissCountDown=0"
      @dismiss-count-down="dismissCountDown = $event"
    >
      {{ successText }}
    </b-alert>
  </div>
</template>

<script>
import api from '@/modules/ferienpass-api';
import ErrorAlert from '@/components/ErrorAlert.vue';

export default {
  name: 'Veranstaltung',
  components: { ErrorAlert },
  props: {
    veranstaltungId: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      veranstaltung: {},
      showValidationStatus: false,
      errorMessages: [],
      dismissCountDown: 0,
    };
  },
  computed: {
    showErrorAlert() {
      return this.errorMessages.length > 0;
    },
    isNewProject() {
      return this.veranstaltungId <= 0;
    },
    titleText() {
      if (this.isNewProject) {
        return 'Veranstaltung anlegen';
      }
      return 'Veranstaltungsbearbeitung';
    },
    submitButtonText() {
      if (this.isNewProject) {
        return 'Anlegen';
      }
      return 'Speichern';
    },
    successText() {
      if (this.isNewProject) {
        return 'Veranstaltung erfolgreich angelegt.';
      }
      return 'Veranstaltung erfolgreich gespeichert.';
    },
    errorHeadingText() {
      if (this.isNewProject) {
        return 'Anlegen nicht möglich. Bitte beheben Sie folgende Fehler:';
      }
      return 'Speichern nicht möglich. Bitte beheben Sie folgende Fehler:';
    },
  },
  created() {
    if (this.isNewProject) {
      // TODO check why?
      this.veranstaltung = {
        aktiv: true,
        anmeldungen: [],
        stornierteTeilnehmer: [],
      };
    } else {
      this.loadProjectData();
    }
  },
  methods: {
    onSubmit() {
      this.showValidationStatus = true;
      if (this.$refs.form.checkValidity()) {
        this.createOrUpdateProject();
      } else {
        this.$refs.form.reportValidity();
      }
    },
    loadProjectData() {
      this.errorMessages = [];
      api.getProject(this.veranstaltungId).then((project) => {
        this.veranstaltung = project;
      }).catch((e) => this.errorMessages.push(e.toString()));
    },
    createOrUpdateProject() {
      this.errorMessages = [];
      if (this.isNewProject) {
        api.createProject(this.veranstaltung).then(() => {
          this.dismissCountDown = 5;
        }).catch((errorMessages) => { this.errorMessages = errorMessages; });
      } else {
        api.updateProject(this.veranstaltung).then(() => {
          this.dismissCountDown = 5;
        }).catch((errorMessages) => { this.errorMessages = errorMessages; });
      }
    },
  },
};
</script>

<style scoped>

</style>
