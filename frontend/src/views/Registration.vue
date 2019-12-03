<template>
  <form
    v-if="formData"
    class="form page-content"
    action="/api/register"
    method="post"
    :data-age="age ? age : ''"
    :data-zip-code="zipCode ? zipCode : ''"
    @keydown.enter="preventAccidentalSubmit"
    @submit="delegatePost"
  >
    <header>
      <img
        src="../assets/ferienpasslogo_ohne_hg.png"
        alt="Ferienpass Weimar"
        class="site-logo"
      >
    </header>

    <h1>Ferienpass Weimar – Anmeldung</h1>

    <checkbox
      class="school-child-checkbox"
      :params="{ 'label': 'Mein Kind geht zur Schule *' }"
      @change="onSchoolChildChange"
    />
    <!-- Grunddaten -->
    <section
      v-model="grunddaten"
      class="form-section"
      :aria-labelledby="`${toIdentifier(grunddaten.title)}`"
    >
      <h2
        :id="`${toIdentifier(grunddaten.title)}`"
        class="form-section__title"
      >
        <button
          type="button"
          :aria-expanded="grunddaten.expandOnStart"
          @click="toggleSectionExpanded"
        >
          Grunddaten
          <!-- Small triangle to switch section view -->
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="18"
            height="18"
            role="img"
            aria-hidden="true"
            focusable="false"
            class="triangle"
          >
            <path
              d="m0,0l0,18 15.588,-9 Z"
              fill="#000"
            />
          </svg>
        </button>
      </h2>

      <div :hidden="!grunddaten.expandOnStart">
        <component
          :is="component.component"
          v-for="(component, index) of grunddaten.components"
          :key="index"
          :params="component.params"
        />
      </div>
    </section>

    <!-- Angebote -->
    <section
      v-model="angebote"
      class="form-section"
      :aria-labelledby="`${toIdentifier(angebote.title)}`"
    >
      <h2
        :id="`${toIdentifier(angebote.title)}`"
        class="form-section__title"
      >
        <button
          type="button"
          :aria-expanded="angebote.expandOnStart"
          @click="toggleSectionExpanded"
        >
          Angebote
          <!-- Small triangle to switch section view -->
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="18"
            height="18"
            role="img"
            aria-hidden="true"
            focusable="false"
            class="triangle"
          >
            <path
              d="m0,0l0,18 15.588,-9 Z"
              fill="#000"
            />
          </svg>
        </button>
      </h2>

      <div :hidden="!angebote.expandOnStart">
        <p>
          Mein Kind möchte an folgenden Veranstaltungen teilnehmen:
          <br><b>Hinweis:</b> Grau hinterlegte Projekte sind ausgebucht oder können aufgrund der
          Altersbeschränkung nicht gewählt werden.
        </p>
        <component
          :is="component_checkbox"
          v-for="(projektParams, index) of alleAnmeldungProjekte"
          :key="index"
          :params="projektParams"
          @change="projectChecked(index)"
        />
        <p>
          <b>Hinweis:</b> Die Bestätigung des Platzes erfolgt bei der Anmeldung entsprechend der zur
          Verfügung stehenden Kapazitäten für die Angebote. Sollte ein Angebot seitens der
          Veranstalter aus unvorhergesehenen Gründen abgesagt werden, besteht kein Anspruch
          auf ein Ersatzangebot. Der gezahlte Beitrag für dieses Angebot wird Ihnen komplett
          zurück erstattet.
        </p>
      </div>
    </section>

    <!-- Allergien, Krankheiten, … -->
    <section
      v-model="allergienkrankheiten"
      class="form-section"
      :aria-labelledby="`${toIdentifier(allergienkrankheiten.title)}`"
    >
      <h2
        :id="`${toIdentifier(allergienkrankheiten.title)}`"
        class="form-section__title"
      >
        <button
          type="button"
          :aria-expanded="allergienkrankheiten.expandOnStart"
          @click="toggleSectionExpanded"
        >
          Allergien, Krankheiten, …
          <!-- Small triangle to switch section view -->
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="18"
            height="18"
            role="img"
            aria-hidden="true"
            focusable="false"
            class="triangle"
          >
            <path
              d="m0,0l0,18 15.588,-9 Z"
              fill="#000"
            />
          </svg>
        </button>
      </h2>

      <div :hidden="!allergienkrankheiten.expandOnStart">
        <component
          :is="component.component"
          v-for="(component, index) of allergienkrankheiten.components"
          :key="index"
          :params="component.params"
        />
      </div>
    </section>

    <!-- Angaben bei Behinderung -->
    <section
      v-model="behinderung"
      class="form-section"
      :aria-labelledby="`${toIdentifier(behinderung.title)}`"
    >
      <h2
        :id="`${toIdentifier(behinderung.title)}`"
        class="form-section__title"
      >
        <button
          type="button"
          :aria-expanded="behinderung.expandOnStart"
          @click="toggleSectionExpanded"
        >
          Angaben bei Behinderung
          <!-- Small triangle to switch section view -->
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="18"
            height="18"
            role="img"
            aria-hidden="true"
            focusable="false"
            class="triangle"
          >
            <path
              d="m0,0l0,18 15.588,-9 Z"
              fill="#000"
            />
          </svg>
        </button>
      </h2>

      <div :hidden="!behinderung.expandOnStart">
        <component
          :is="component.component"
          v-for="(component, index) of behinderung.components"
          :key="index"
          :params="component.params"
        />
      </div>
    </section>

    <!-- Erklärung -->
    <section
      v-model="erklaerung"
      class="form-section"
      :aria-labelledby="`${toIdentifier(erklaerung.title)}`"
    >
      <h2
        :id="`${toIdentifier(erklaerung.title)}`"
        class="form-section__title"
      >
        <button
          type="button"
          :aria-expanded="erklaerung.expandOnStart"
          @click="toggleSectionExpanded"
        >
          Erklärung
          <!-- Small triangle to switch section view -->
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="18"
            height="18"
            role="img"
            aria-hidden="true"
            focusable="false"
            class="triangle"
          >
            <path
              d="m0,0l0,18 15.588,-9 Z"
              fill="#000"
            />
          </svg>
        </button>
      </h2>

      <div :hidden="!erklaerung.expandOnStart">
        <component
          :is="component.component"
          v-for="(component, index) of erklaerung.components"
          :key="index"
          :params="component.params"
        />
      </div>
    </section>

    <!-- Datenschutzerklärung -->
    <section
      v-model="datenschutz"
      class="form-section"
      :aria-labelledby="`${toIdentifier(datenschutz.title)}`"
    >
      <h2
        :id="`${toIdentifier(datenschutz.title)}`"
        class="form-section__title"
      >
        <button
          type="button"
          :aria-expanded="datenschutz.expandOnStart"
          @click="toggleSectionExpanded"
        >
          Datenschutzerklärung
          <!-- Small triangle to switch section view -->
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="18"
            height="18"
            role="img"
            aria-hidden="true"
            focusable="false"
            class="triangle"
          >
            <path
              d="m0,0l0,18 15.588,-9 Z"
              fill="#000"
            />
          </svg>
        </button>
      </h2>

      <div :hidden="!datenschutz.expandOnStart">
        <component
          :is="component.component"
          v-for="(component, index) of datenschutz.components"
          :key="index"
          :params="component.params"
        />
      </div>
    </section>

    <input
      class="button button--wide"
      type="submit"
      name="Submit"
      value="Absenden"
    >
  </form>
</template>

<script>
import api from '@/modules/ferienpass-api';
import formDataJson from '@/assets/form-data.json';

export default {
  name: 'Registration',
  data() {
    return {
      formData: null,
      initiallyDisabled: false,
      'base__birthdate-day': null,
      'base__birthdate-month': null,
      'base__birthdate-year': null,
      'base__zip-code': null,
      grunddaten: null,
      angebote: null,
      allergienkrankheiten: null,
      behinderung: null,
      erklaerung: null,
      datenschutz: null,
      component_checkbox: 'Checkbox',
      alleAdminProjekte: [],
      alleAnmeldungProjekte: [],
      reservierteProjekte: [],
    };
  },
  computed: {
    age() {
      if (
        this['base__birthdate-day']
        && this['base__birthdate-month']
        && this['base__birthdate-year']
      ) {
        const day = parseInt(this['base__birthdate-day'], 10);
        const month = parseInt(this['base__birthdate-month'], 10);
        const year = parseInt(this['base__birthdate-year'], 10);

        if ([day, month, year].every((el) => typeof el === 'number' && el % 1 === 0)) {
          const birthdate = new Date(year, month - 1, day);
          const today = new Date();

          let age = today.getFullYear() - birthdate.getFullYear();
          const m = today.getMonth() - birthdate.getMonth();
          if (m < 0 || (m === 0 && today.getDate() < birthdate.getDate())) {
            age -= 1;
          }

          this.disableUnavailableProjectsIfToYoungOrOld(age);
          return age;
        }
      }

      return null;
    },
    zipCode() {
      if (this['base__zip-code']) {
        const value = parseInt(this['base__zip-code'], 10);
        if (typeof value === 'number' && value % 1 === 0) {
          return value;
        }
      }

      return null;
    },
  },
  created() {
    this.fetchFormData();
    this.retrieveAllAdminProjects();
  },
  updated() {
    this.$nextTick(function () {
      if (this.formData && !this.initiallyDisabled) {
        const checkbox = document.querySelector('.school-child-checkbox > input');
        const formElements = this.getFormElements();
        this.disableFormElements(formElements, [checkbox]);
        this.initiallyDisabled = true;
      }
    });
  },
  methods: {
    projectChecked(index) {
      console.log(this.alleAnmeldungProjekte[index].registered);
      console.log(index);
      this.alleAnmeldungProjekte[index].registered = !this.alleAnmeldungProjekte[index].registered;
      console.log(this.alleAnmeldungProjekte[index].registered);
      console.log(this.alleAnmeldungProjekte);
    },
    modalSuccess() {
      this.$swal('Geschafft!',
        'Deine Anmeldung war erfolgreich!\n Sie erhalten eine eMail mit der Zahlungsaufforderung.',
        'success');
    },
    modalProjectOverbooked() {
      this.$swal('Oh nein!', 'Eines der Angebote ist leider schon belegt!', 'warning');
    },
    fetchFormData() {
      this.formData = formDataJson;
      console.log('formData fetched!');
      this.grunddaten = this.formData.sections[0];
      this.angebote = this.formData.sections[1];
      this.allergienkrankheiten = this.formData.sections[2];
      this.behinderung = this.formData.sections[3];
      this.erklaerung = this.formData.sections[4];
      this.datenschutz = this.formData.sections[5];
    },
    retrieveAllAdminProjects() {
      api.getProjects().then((projects) => {
        console.log('Retrieve projects from Admin-Microservice');
        console.log(projects);
        this.alleAdminProjekte = projects;
        this.mappeAdminProjekteAufAnmeldungProjekte();
      });
    },
    mappeAdminProjekteAufAnmeldungProjekte() {
      this.alleAdminProjekte.forEach((adminProjekt) => {
        console.log(adminProjekt);
        const projektParam = {
          label: adminProjekt.name,
          name: `projekt-id${adminProjekt.id}`,
          registered: false,
          projekt: {
            date: new Date(adminProjekt.datum),
            endDate: new Date(adminProjekt.datumEnde),
            id: adminProjekt.id,
            org: adminProjekt.traeger,
            minimumAge: adminProjekt.mindestAlter,
            maximumAge: adminProjekt.hoechstAlter,
          },
        };
        this.alleAnmeldungProjekte.push(projektParam);
      });
      console.log('Map Admin projects to Anmeldung projects');
      console.log(this.alleAnmeldungProjekte);
    },
    preventAccidentalSubmit(event) {
      if (['textarea', 'submit'].includes(event.target.type)) {
        return;
      }

      event.preventDefault();
    },
    delegatePost(event) {
      event.preventDefault();

      const form = event.target;
      const jsonObject = {};
      const jsonProjects = [];
      for (let i = 0; i < this.alleAnmeldungProjekte.length; i += 1) {
        const jsonProject = {};
        jsonProject.id = this.alleAnmeldungProjekte[i].projekt.id;
        jsonProject.name = this.alleAnmeldungProjekte[i].label;
        jsonProject.registered = this.alleAnmeldungProjekte[i].registered;
        jsonProjects.push(jsonProject);
      }

      console.log(jsonProjects);

      Array.prototype.slice.call(form.elements).forEach((element) => {
        if (element.name && element.type !== 'submit') {
          jsonObject[element.name] = element.type === 'checkbox' ? element.checked : element.value;
        }
      });
      jsonObject.projects = jsonProjects;
      console.log(jsonObject);

      api.registerTeilnehmer(jsonObject).then((response) => {
        console.log(response);
        if (response) {
          if (response.status === 201) {
            // Admin-Backend successfully added new Teilnehmer
            this.modalSuccess();
          }
        }
      }).catch((error) => {
        console.error(error);
        console.log(`Error, HTTP-Status: ${error.response.status}`);
        if (error.response) {
          if (error.response.status === 409) {
            // Admin-Backend said that one or more projectrs aren´t available
            // and gave a list of them back
            this.reservierteProjekte = error.response.data;
            this.disableProjectsWithoutFreeSlots();
            this.modalProjectOverbooked();
          }
        }
      });
    },
    getFormElements() {
      const form = document.querySelector('.form');

      if (form) {
        return Array.prototype.slice.call(form.elements);
      }

      return null;
    },
    onSchoolChildChange(event) {
      const formElements = this.getFormElements();

      if (event.currentTarget.checked) {
        this.enableFormElements(formElements);
      } else {
        this.disableFormElements(formElements, [event.currentTarget]);
      }
    },
    enableFormElements(formElements) {
      formElements.forEach((element) => {
        if (element.hasAttribute('data-newly-disabled')) {
          element.removeAttribute('data-newly-disabled');
          element.removeAttribute('disabled');
        }
      });
    },
    disableFormElements(formElements, exceptions = []) {
      formElements.forEach((element) => {
        if (!exceptions.includes(element) && element.type !== 'button' && !element.disabled) {
          element.setAttribute('data-newly-disabled', null);
          element.setAttribute('disabled', null);
        }
      });
    },
    toggleSectionExpanded(event) {
      const button = event.currentTarget;
      const expanded = button.getAttribute('aria-expanded') === 'true' || false;
      button.setAttribute('aria-expanded', !expanded);

      const heading = button.parentElement;
      const targetSection = heading.nextElementSibling;
      targetSection.hidden = expanded;
    },
    disableUnavailableProjectsIfToYoungOrOld(age) {
      const projectControls = Array.prototype.slice.call(
        document.querySelectorAll('[name^="projekt-id"]'),
      );
      projectControls.forEach((projectControl) => {
        projectControl.removeAttribute('disabled');
        const minimumAge = parseInt(projectControl.dataset.minimumAge, 10);
        const maximumAge = parseInt(projectControl.dataset.maximumAge, 10);
        if (age < minimumAge || age > maximumAge) {
          projectControl.setAttribute('disabled', null);
        }
      });
    },
    disableProjectsWithoutFreeSlots() {
      const projectControls = Array.prototype.slice.call(
        document.querySelectorAll('[name^="projekt-id"]'),
      );
      for (let i = 0; i < this.reservierteProjekte.length; i += 1) {
        projectControls.forEach((projectControl) => {
          const checkBoxId = parseInt(projectControl.dataset.id, 10);
          if (this.reservierteProjekte[i] === checkBoxId) {
            projectControl.setAttribute('disabled', null);
            // eslint-disable-next-line no-param-reassign
            projectControl.checked = false; // TODO check eslint-problem
            this.unregisterProject(checkBoxId);
          }
        });
      }
    },
    unregisterProject(projectId) {
      for (let j = 0; j < this.alleAnmeldungProjekte.length; j += 1) {
        if (projectId === this.alleAnmeldungProjekte[j].projekt.id) {
          console.log(`unregistering ${this.alleAnmeldungProjekte[j].label}`);
          this.alleAnmeldungProjekte[j].registered = false;
        }
      }
    },
  },
};
</script>

<style>

.page-content {
  width: 80%;
  max-width: 1000px;
  margin: 3rem auto;
}

header {
  max-width: 100%;
  background-color: rgba(51, 122, 183, 1);
  text-align: center;
}

.site-logo {
  padding: 20px 0;
}

.form {
  /* Initialize the form section counter */
  counter-reset: form-section;
}

.form-section {
  /* Count each .form-section element */
  counter-increment: form-section;
}

.form-section__title::before {
  /* Display each .form-section’s counter value */
  content: counter(form-section) "." "\A0";
}

.form-section__title > button {
  all: inherit;
  margin-top: 0;
  margin-bottom: 0;
  display: inline-block;
}

.form-section__title > button:focus > .triangle {
  outline: 2px solid cornflowerblue;
}

.form-item:not(:last-child) {
  margin-bottom: 0.75rem;
}

.triangle {
  margin-left: 0.25rem;
  transform-origin: 6px 9px;
  transition: transform 0.1s;
}

[aria-expanded="true"] .triangle {
  transform: rotate(90deg);
  transition: transform 0.25s;
}

[aria-expanded] path {
  fill: currentColor;
}
</style>
