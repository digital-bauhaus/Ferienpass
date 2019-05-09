<template>
  <form
    v-if="formData"
    @keydown.enter="preventAccidentalSubmit"
    @submit="delegatePost"
    class="form"
    action="/api/register"
    method="post"
    :data-age="age ? age : ''"
    :data-zip-code="zipCode ? zipCode : ''"
  >
    <header>
      <img src="../assets/ferienpasslogo_ohne_hg.png" alt="Ferienpass Weimar" class="site-logo">
    </header>

    <h1>Ferienpass Weimar – Anmeldung</h1>

    <checkbox
      class="school-child-checkbox"
      :params="{ 'label': 'Mein Kind geht zur Schule *' }"
      @change="onSchoolChildChange"
    />
    <!-- Grunddaten -->
    <section v-model="grunddaten" class="form-section" :aria-labelledby="`${toIdentifier(grunddaten.title)}`">
      <h2 class="form-section__title" :id="`${toIdentifier(grunddaten.title)}`">
        <button type="button" :aria-expanded="grunddaten.expandOnStart" @click="toggleSectionExpanded">
          Grunddaten
          <!-- Small triangle to switch section view -->
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" role="img" aria-hidden="true" focusable="false" class="triangle">
            <path d="m0,0l0,18 15.588,-9 Z" fill="#000"/>
          </svg>
        </button>
      </h2>

      <div :hidden="!grunddaten.expandOnStart">
        <component v-for="(component, index) of grunddaten.components" :key="index" :is="component.component" :params="component.params"/>
      </div>
    </section>

    <!-- Angebote -->
    <section v-model="angebote" class="form-section" :aria-labelledby="`${toIdentifier(angebote.title)}`">
      <h2 class="form-section__title" :id="`${toIdentifier(angebote.title)}`">
        <button type="button" :aria-expanded="angebote.expandOnStart" @click="toggleSectionExpanded">
          Angebote
          <!-- Small triangle to switch section view -->
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" role="img" aria-hidden="true" focusable="false" class="triangle">
            <path d="m0,0l0,18 15.588,-9 Z" fill="#000"/>
          </svg>
        </button>
      </h2>

      <div :hidden="!angebote.expandOnStart">
        <p>Mein Kind möchte an folgenden Veranstaltungen teilnehmen:
        <br />Hinweis: Grau hinterlegte Projekte sind ausgebucht oder können aufgrund der Altersbeschränkung nicht gewählt werden.</p>
        <component
          v-for="(projektParams, index) of alleAnmeldungProjekte" :key="index"
          :is="component_checkbox"
          :params="projektParams"
          v-on:change="projectChecked(index)"
        />

      </div>
    </section>

    <!-- Allergien, Krankheiten, … -->
    <section v-model="allergienkrankheiten" class="form-section" :aria-labelledby="`${toIdentifier(allergienkrankheiten.title)}`">
      <h2 class="form-section__title" :id="`${toIdentifier(allergienkrankheiten.title)}`">
        <button type="button" :aria-expanded="allergienkrankheiten.expandOnStart" @click="toggleSectionExpanded">
          Allergien, Krankheiten, …
          <!-- Small triangle to switch section view -->
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" role="img" aria-hidden="true" focusable="false" class="triangle">
            <path d="m0,0l0,18 15.588,-9 Z" fill="#000"/>
          </svg>
        </button>
      </h2>

      <div :hidden="!allergienkrankheiten.expandOnStart">
        <component v-for="(component, index) of allergienkrankheiten.components" :key="index" :is="component.component" :params="component.params"/>
      </div>
    </section>

    <!-- Angaben bei Behinderung -->
    <section v-model="behinderung" class="form-section" :aria-labelledby="`${toIdentifier(behinderung.title)}`">
      <h2 class="form-section__title" :id="`${toIdentifier(behinderung.title)}`">
        <button type="button" :aria-expanded="behinderung.expandOnStart" @click="toggleSectionExpanded">
          Angaben bei Behinderung
          <!-- Small triangle to switch section view -->
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" role="img" aria-hidden="true" focusable="false" class="triangle">
            <path d="m0,0l0,18 15.588,-9 Z" fill="#000"/>
          </svg>
        </button>
      </h2>

      <div :hidden="!behinderung.expandOnStart">
        <component v-for="(component, index) of behinderung.components" :key="index" :is="component.component" :params="component.params"/>
      </div>
    </section>

    <!-- Erklärung -->
    <section v-model="erklaerung" class="form-section" :aria-labelledby="`${toIdentifier(erklaerung.title)}`">
      <h2 class="form-section__title" :id="`${toIdentifier(erklaerung.title)}`">
        <button type="button" :aria-expanded="erklaerung.expandOnStart" @click="toggleSectionExpanded">
          Erklärung
          <!-- Small triangle to switch section view -->
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" role="img" aria-hidden="true" focusable="false" class="triangle">
            <path d="m0,0l0,18 15.588,-9 Z" fill="#000"/>
          </svg>
        </button>
      </h2>

      <div :hidden="!erklaerung.expandOnStart">
        <component v-for="(component, index) of erklaerung.components" :key="index" :is="component.component" :params="component.params"/>
      </div>
    </section>

    <!-- Datenschutzerklärung -->
    <section v-model="datenschutz" class="form-section" :aria-labelledby="`${toIdentifier(datenschutz.title)}`">
      <h2 class="form-section__title" :id="`${toIdentifier(datenschutz.title)}`">
        <button type="button" :aria-expanded="datenschutz.expandOnStart" @click="toggleSectionExpanded">
          Datenschutzerklärung
          <!-- Small triangle to switch section view -->
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" role="img" aria-hidden="true" focusable="false" class="triangle">
            <path d="m0,0l0,18 15.588,-9 Z" fill="#000"/>
          </svg>
        </button>
      </h2>

      <div :hidden="!datenschutz.expandOnStart">
        <component v-for="(component, index) of datenschutz.components" :key="index" :is="component.component" :params="component.params"/>
      </div>
    </section>

    <input class="button button--wide" type="submit" name="Submit" value="Absenden">
  </form>
</template>

<script>
import { AXIOS } from './http-common';
import formDataJson from './../assets/form-data'

export default {
  name: 'Registration',
  data () {
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
      reservierteProjekte: []
    };
  },
  computed: {
    age () {
      if (
        this['base__birthdate-day'] &&
        this['base__birthdate-month'] &&
        this['base__birthdate-year']
      ) {
        const day = parseInt(this['base__birthdate-day']);
        const month = parseInt(this['base__birthdate-month']);
        const year = parseInt(this['base__birthdate-year']);

        if ([day, month, year].every(el => typeof el === 'number' && el % 1 === 0)) {
          const birthdate = new Date(year, month - 1, day);
          const today = new Date();

          let age = today.getFullYear() - birthdate.getFullYear();
          const m = today.getMonth() - birthdate.getMonth();
          if (m < 0 || (m === 0 && today.getDate() < birthdate.getDate())) {
            age--;
          }

          this.disableUnavailableProjectsIfToYoungOrOld(age);
          return age;
        }
      }

      return null;
    },
    zipCode () {
      if (this['base__zip-code']) {
        const value = parseInt(this['base__zip-code']);
        if (typeof value === 'number' && value % 1 === 0) {
          return value;
        }
      }

      return null;
    }
  },
  created () {
    this.fetchFormData();
    this.retrieveAllAdminProjects();
  },
  updated () {
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
    projectChecked (index) {
      console.log(this.alleAnmeldungProjekte[index].registered);
      console.log(index);
      this.alleAnmeldungProjekte[index].registered = !this.alleAnmeldungProjekte[index].registered;
      console.log(this.alleAnmeldungProjekte[index].registered);
      console.log(this.alleAnmeldungProjekte);
    },
    modalSuccess () {
      this.$swal('Geschafft!', 'Deine Anmeldung war erfolgreich!\n Sie erhalten eine eMail mit der Zahlungsaufforderung.', 'success')
    },
    modalProjectOverbooked () {
      this.$swal('Oh nein!', 'Eines der Angebote ist leider schon belegt!', 'warning')
    },
    fetchFormData () {
      this.formData = formDataJson;
      console.log("formData fetched!");
      this.grunddaten = this.formData.sections[0];
      this.angebote = this.formData.sections[1];
      this.allergienkrankheiten = this.formData.sections[2];
      this.behinderung = this.formData.sections[3];
      this.erklaerung = this.formData.sections[4];
      this.datenschutz = this.formData.sections[5];
    },
    retrieveAllAdminProjects () {
      AXIOS.get('/allprojects')
        .then(response => {
          console.log('Retrieve projects from Admin-Microservice');
          console.log(response);
          this.alleAdminProjekte = response.data;
          this.mappeAdminProjekteAufAnmeldungProjekte();
        })
        .catch(error => {
          console.error(error);
        });
    },
    mappeAdminProjekteAufAnmeldungProjekte () {
      this.alleAdminProjekte.forEach(adminProjekt => {
        console.log(adminProjekt)
        var projektParam = {
          label: adminProjekt.name,
          name: 'projekt-id' + adminProjekt.id,
          registered: false,
          projekt: {
            date: new Date(adminProjekt.datum),
            endDate: new Date(adminProjekt.datumEnde),
            id: adminProjekt.id,
            org: adminProjekt.traeger,
            minimumAge: adminProjekt.mindestAlter,
            maximumAge: adminProjekt.hoechstAlter
          }
        }
        this.alleAnmeldungProjekte.push(projektParam);
      });
      console.log('Map Admin projects to Anmeldung projects')
      console.log(this.alleAnmeldungProjekte)
    },
    preventAccidentalSubmit (event) {
      if (['textarea', 'submit'].includes(event.target.type)) {
        return;
      }

      event.preventDefault();
    },
    delegatePost (event) {
      event.preventDefault();

      const form = event.target;
      let jsonObject = {};
      let jsonProjects = [];
      for (var i = 0; i < this.alleAnmeldungProjekte.length; i++) {
        let jsonProject = {};
        jsonProject['id'] = this.alleAnmeldungProjekte[i].projekt.id;
        jsonProject['name'] = this.alleAnmeldungProjekte[i].label;
        jsonProject['registered'] = this.alleAnmeldungProjekte[i].registered;
        jsonProjects.push(jsonProject);
      }

      console.log(jsonProjects);

      Array.prototype.slice.call(form.elements).forEach(element => {
        if (element.name && element.type !== 'submit') {
          jsonObject[element.name] = element.type === 'checkbox' ? element.checked : element.value;
        }
      });
      jsonObject['projects'] = jsonProjects;
      console.log(jsonObject)
      AXIOS.post('/register', jsonObject)
        .then(response => {
          console.log(response);
          if (response) {
            if (response.status === 201) {
              // Admin-Backend successfully added new Teilnehmer
              this.modalSuccess();
            }
          }
        })
        .catch(error => {
          console.error(error);
          console.log('Error, HTTP-Status: ' + error.response.status);
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
    getFormElements () {
      const form = document.querySelector('.form');

      if (form) {
        return Array.prototype.slice.call(form.elements);
      }

      return null;
    },
    onSchoolChildChange (event) {
      const formElements = this.getFormElements();

      if (event.currentTarget.checked) {
        this.enableFormElements(formElements);
      } else {
        this.disableFormElements(formElements, [event.currentTarget]);
      }
    },
    enableFormElements (formElements) {
      formElements.forEach(element => {
        if (element.hasAttribute('data-newly-disabled')) {
          element.removeAttribute('data-newly-disabled');
          element.removeAttribute('disabled');
        }
      });
    },
    disableFormElements (formElements, exceptions = []) {
      formElements.forEach(element => {
        if (!exceptions.includes(element) && element.type !== 'button' && !element.disabled) {
          element.setAttribute('data-newly-disabled', null);
          element.setAttribute('disabled', null);
        }
      });
    },
    toggleSectionExpanded (event) {
      const button = event.currentTarget;
      const expanded = button.getAttribute('aria-expanded') === 'true' || false;
      button.setAttribute('aria-expanded', !expanded);

      const heading = button.parentElement;
      const targetSection = heading.nextElementSibling;
      targetSection.hidden = expanded;
    },
    disableUnavailableProjectsIfToYoungOrOld (age) {
      const projectControls = Array.prototype.slice.call(
        document.querySelectorAll('[name^="projekt-id"]')
      );
      projectControls.forEach(projectControl => {
        projectControl.removeAttribute('disabled');
        const minimumAge = parseInt(projectControl.dataset.minimumAge);
        const maximumAge = parseInt(projectControl.dataset.maximumAge);
        if (age < minimumAge || age > maximumAge) {
          projectControl.setAttribute('disabled', null);
        }
      });
    },
    disableProjectsWithoutFreeSlots () {
      var projectControls = Array.prototype.slice.call(
        document.querySelectorAll('[name^="projekt-id"]')
      );
      for (var i = 0; i < this.reservierteProjekte.length; i++) {
        projectControls.forEach(projectControl => {
          var checkBoxId = parseInt(projectControl.dataset.id);
          if (this.reservierteProjekte[i] === checkBoxId) {
            projectControl.setAttribute('disabled', null);
            projectControl.checked = false;
            this.unregisterProject(checkBoxId);
          }
        })
      }
    },
    unregisterProject (projectId) {
      for (var j = 0; j < this.alleAnmeldungProjekte.length; j++) {
        if (projectId === this.alleAnmeldungProjekte[j].projekt.id) {
          console.log('unregistering ' + this.alleAnmeldungProjekte[j].label)
          this.alleAnmeldungProjekte[j].registered = false;
        }
      }
    }
  }
};
</script>

<style>
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
