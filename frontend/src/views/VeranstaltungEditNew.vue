<template>
  <div>
    <NavigationMenu />
    <b-container>
      <Veranstaltung :veranstaltung="project" />
    </b-container>
  </div>
</template>

<script>
import * as moment from 'moment';
import api from '@/modules/ferienpass-api';
import NavigationMenu from '@/components/NavBar.vue';
import Veranstaltung from '@/components/Veranstaltung.vue';

export default {
  name: 'VeranstaltungEditNew',
  components: { Veranstaltung, NavigationMenu },
  data() {
    return {
      errorMessages: [],
      displayDatum: '',
      displayDatumEnde: '',
      id: parseInt(this.$route.query.id, 10),
      project: null,
      popupClass: 'fadeOut',
    };
  },
  computed: {
    isNewProject() {
      return this.id < 0;
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
    errorHeadingText() {
      if (this.isNewProject) {
        return 'Anlegen nicht möglich. Bitte beheben Sie folgende Fehler:';
      }
      return 'Speichern nicht möglich. Bitte beheben Sie folgende Fehler:';
    },
  },
  created() {
    if (this.isNewProject) {
      this.project = {
        aktiv: true,
        anmeldungen: [],
        stornierteTeilnehmer: [],
      };
    } else {
      this.loadProjectData();
    }
  },
  methods: {
    loadProjectData() {
      this.errorMessages = [];
      api.getProject(this.id).then((project) => {
        this.project = project;
        this.displayDatum = moment(this.project.datum).format('DD.MM.YYYY');
        this.displayDatumEnde = moment(this.project.datumEnde).format('DD.MM.YYYY');
      }).catch((e) => this.errorMessages.push(e.toString()));
    },
    createOrUpdateProject() {
      this.errorMessages = [];

      // parse dates
      const parsedDatum = moment(this.displayDatum, 'DD.MM.YYYY', true);
      if (!parsedDatum.isValid()) {
        this.errorMessages.push('Ungültiges Datumsformat für Beginndatum');
      }
      const parsedDatumEnde = moment(this.displayDatumEnde, 'DD.MM.YYYY', true);
      if (!parsedDatumEnde.isValid()) {
        this.errorMessages.push('Ungültiges Datumsformat für Enddatum');
      }

      if (parsedDatum.isValid() && parsedDatumEnde.isValid()) {
        this.project.datum = parsedDatum.toDate();
        this.project.datumEnde = parsedDatumEnde.toDate();

        if (this.isNewProject) {
          api.createProject(this.project).then(() => {
            this.fadeInAndOutAfterTimeout();
          }).catch((errorMessages) => { this.errorMessages = errorMessages; });
        } else {
          api.updateProject(this.project).then(() => {
            this.fadeInAndOutAfterTimeout();
          }).catch((errorMessages) => { this.errorMessages = errorMessages; });
        }
      }
    },
  },
};
</script>

<style scoped>
</style>
