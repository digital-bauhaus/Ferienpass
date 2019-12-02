<template>
  <div>
    <NavigationMenu />
    <main v-if="user">
      <h1>Teilnehmerbearbeitung</h1>

      <form
        method="post"
        @submit.prevent="updateUser"
      >
        <h2>Allgemeine Informationen</h2>
        <table border="0">
          <tr>
            <td><label for="firstName">Vorname: </label></td>
            <td>
              <input
                id="firstName"
                v-model="user.vorname"
                type="text"
                placeholder="-"
              >
            </td>
            <td><label for="street">Straße: </label></td>
            <td>
              <input
                id="street"
                v-model="user.strasse"
                type="text"
                placeholder="-"
              >
            </td>
          </tr>
          <tr>
            <td><label for="lastName">Nachname: </label></td>
            <td>
              <input
                id="lastName"
                v-model="user.nachname"
                type="text"
                placeholder="-"
              >
            </td>
            <td><label for="city">Stadt: </label></td>
            <td>
              <input
                id="city"
                v-model="user.stadt"
                type="text"
                placeholder="-"
              >
            </td>
          </tr>
          <tr>
            <td><label for="birthDate">Geburtsdatum (Jahr,Monat,Tag): </label></td>
            <td>
              <input
                id="birthDate"
                v-model="user.geburtsdatum"
                type="text"
                placeholder="-"
              >
            </td>
            <td><label for="postcode">Postleitzahl: </label></td>
            <td>
              <input
                id="postcode"
                v-model="user.postleitzahl"
                type="text"
                placeholder="-"
              >
            </td>
          </tr>
          <tr>
            <td><label for="telephone">Telefonnummer: </label></td>
            <td>
              <input
                id="telephone"
                v-model="user.telefon"
                type="text"
                placeholder="-"
              >
            </td>
            <td><label for="healthcare">Krankenkasse: </label></td>
            <td>
              <input
                id="healthcare"
                v-model="user.krankenkasse"
                type="text"
              >
            </td>
          </tr>
          <tr>
            <td><label for="email">eMail</label></td>
            <td>
              <input
                id="email"
                v-model="user.email"
                type="text"
                placeholder="-"
              >
            </td>
            <td><label for="checkBezahlt">Hat bezahlt</label></td>
            <td>
              <input
                id="checkBezahlt"
                v-model="user.bezahlt"
                type="checkbox"
                class="regular-checkbox"
              >
            </td>
          </tr>
        </table>

        <br>
        <hr>
        <h2>Weitere Angaben:</h2>
        <table>
          <tr>
            <td><label for="checkMedikation">Erlaube Medikation</label></td>
            <td>
              <input
                id="checkMedikation"
                v-model="user.erlaubeMedikamentation"
                type="checkbox"
                class="regular-checkbox"
              >
            </td>
            <td><label for="checkHause">Darf alleine nach Hause gehen</label></td>
            <td>
              <input
                id="checkHause"
                v-model="user.darfAlleinNachHause"
                type="checkbox"
                class="regular-checkbox"
              >
            </td>
          </tr>
          <tr>
            <td><label for="checkSchwimmen">Darf schwimmen</label></td>
            <td>
              <input
                id="checkSchwimmen"
                v-model="user.darfSchwimmen"
                type="checkbox"
                class="regular-checkbox"
              >
            </td>
            <td><label for="schwimmAbzeichen">Schwimmstufe:</label></td>
            <td>
              <input
                id="schwimmAbzeichen"
                v-model="user.schwimmAbzeichen"
                type="text"
                class="regular-checkbox"
              >
            </td>
          </tr>
          <tr>
            <td><label for="checkReiten">Darf reiten</label></td>
            <td>
              <input
                id="checkReiten"
                v-model="user.darfReiten"
                type="checkbox"
                class="regular-checkbox"
              >
            </td>
            <td>
              <label for="checkBehandlung">Behandlungserlaubnis bei Erkrankungen und
                Unfällen</label>
            </td>
            <td>
              <input
                id="checkBehandlung"
                v-model="user.darfBehandeltWerden"
                type="checkbox"
                class="regular-checkbox"
              >
            </td>
          </tr>
        </table>

        <br>
        <hr>
        <h2>Notfallkontaktdaten</h2>
        <table>
          <tr>
            <th colspan="2">
              Notfallkontakt
            </th>
            <th colspan="2">
              Arzt
            </th>
          </tr>
          <tr>
            <td colspan="2">
              <table>
                <tr>
                  <td><label for="contactName">Name, Vorname:</label></td>
                  <td>
                    <input
                      id="contactName"
                      v-model="user.notfallKontakt.name"
                      type="text"
                    >
                  </td>
                </tr>
                <tr>
                  <td><label for="contactaddress">Addresse:</label></td>
                  <td>
                    <input
                      id="contactaddress"
                      v-model="user.notfallKontakt.address"
                      type="text"
                    >
                  </td>
                </tr>
                <tr>
                  <td><label for="contacttelephone">Telefon:</label></td>
                  <td>
                    <input
                      id="contacttelephone"
                      v-model="user.notfallKontakt.telephone"
                      type="text"
                    >
                  </td>
                </tr>
              </table>
            </td>
            <td colspan="2">
              <table>
                <tr>
                  <td><label for="doctorname">Name, Vorname:</label></td>
                  <td>
                    <input
                      id="doctorname"
                      v-model="user.arzt.name"
                      type="text"
                      placeholder="Name"
                    >
                  </td>
                </tr>
                <tr>
                  <td><label for="doctoraddress">Adresse:</label></td>
                  <td>
                    <input
                      id="doctoraddress"
                      v-model="user.arzt.address"
                      type="text"
                      placeholder="Addresse"
                    >
                  </td>
                </tr>
                <tr>
                  <td><label for="doctortelephone">Telefon:</label></td>
                  <td>
                    <input
                      id="doctortelephone"
                      v-model="user.arzt.telephone"
                      type="text"
                      placeholder="Telefon"
                    >
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>

        <input
          type="submit"
          value="Änderung speichern"
        >
      </form>
      <ErrorListBox
        v-if="errorMessages.length"
        :errors="errorMessages"
        :heading-text="errorHeadingText"
        class="error-list-box"
      />

      <br>
      <hr>
      <form
        method="post"
        @submit.prevent="updateUser"
      >
        <h2>Einschränkungen</h2>
        <table>
          <tr>
            <th>Allergien</th>
            <th>Krankheiten</th>
          </tr>
          <tr>
            <td>
              <textarea
                v-model="user.allergien"
                rows="4"
                cols="50"
              />
            </td>
            <td>
              <textarea
                v-model="user.krankheiten"
                rows="4"
                cols="50"
              />
            </td>
          </tr>
        </table>
        <table>
          <tr>
            <th>Hitzeempfindlichkeit</th>
            <th>Medikamente</th>
          </tr>
          <tr>
            <td>
              <textarea
                v-model="user.hitzeempfindlichkeiten"
                rows="4"
                cols="50"
              />
            </td>
            <td>
              <textarea
                v-model="user.medikamente"
                rows="4"
                cols="50"
              />
            </td>
          </tr>
        </table>

        <table>
          <tr>
            <th>Ernährungsbesonderheiten</th>
          </tr>
          <tr>
            <td>
              <textarea
                v-model="user.essenLimitierungen"
                rows="4"
                cols="50"
              />
            </td>
          </tr>
        </table>

        <input
          type="submit"
          value="Änderung speichern"
        >
      </form>
      <ErrorListBox
        v-if="errorMessages.length"
        :errors="errorMessages"
        :heading-text="errorHeadingText"
        class="error-list-box"
      />

      <br>
      <hr>
      <form
        method="post"
        @submit.prevent="updateUser"
      >
        <h3>Behinderungen</h3>
        <div>
          <label for="checkBehinderung"><b>Behinderungsausweis liegt vor:</b></label>
          <input
            id="checkBehinderung"
            v-model="user.liegtBehinderungVor"
            type="checkbox"
            class="regular-checkbox"
          >
        </div>
        <div v-if="user.liegtBehinderungVor">
          <table>
            <tr>
              <th colspan="6">
                Art der Behinderung
              </th>
            </tr>
            <tr>
              <td><label for="checkBehinderungG">„G“ (erhebliche Gehbehinderung) </label></td>
              <td>
                <input
                  id="checkBehinderungG"
                  v-model="user.behinderung.merkzeichen_BeeintraechtigungImStrassenverkehr_G"
                  type="checkbox"
                  class="regular-checkbox"
                >
              </td>
              <td><label for="checkBehinderungH">„H“ (Hilflosigkeit) </label></td>
              <td>
                <input
                  id="checkBehinderungH"
                  v-model="user.behinderung.merkzeichen_Hilflosigkeit_H"
                  type="checkbox"
                  class="regular-checkbox"
                >
              </td>
              <td>
                <label for="checkBehinderungaG">„aG“ (außergewöhnliche Gehbehinderung) </label>
              </td>
              <td>
                <input
                  id="checkBehinderungaG"
                  v-model="user.behinderung.merkzeichen_AussergewoehnlicheGehbehinderung_aG"
                  type="checkbox"
                  class="regular-checkbox"
                >
              </td>
            </tr>
            <tr>
              <td><label for="checkBehinderungBl">„Bl“ (Blindheit) </label></td>
              <td>
                <input
                  id="checkBehinderungBl"
                  v-model="user.behinderung.merkzeichen_Blind_Bl"
                  type="checkbox"
                  class="regular-checkbox"
                >
              </td>
              <td><label for="checkBehinderungGl">„Gl“ (Gehörlosigkeit) </label></td>
              <td>
                <input
                  id="checkBehinderungGl"
                  v-model="user.behinderung.merkzeichen_Gehoerlos_Gl"
                  type="checkbox"
                  class="regular-checkbox"
                >
              </td>
              <td>
                <label for="checkBehinderungB">„B“ (Berechtigung zur Mitnahme einer
                  Begleitperson) </label>
              </td>
              <td>
                <input
                  id="checkBehinderungB"
                  v-model="user.behinderung.merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B"
                  type="checkbox"
                >
              </td>
            </tr>
            <tr>
              <td><label for="checkBehinderungTBL">„TBL“ (Taubblindheit)</label></td>
              <td>
                <input
                  id="checkBehinderungTBL"
                  v-model="user.behinderung.merkzeichen_Taubblind_TBL"
                  type="checkbox"
                  class="regular-checkbox"
                >
              </td>
              <td><label for="checkBehinderungRollstuhl">Rollstuhlnutzung </label></td>
              <td>
                <input
                  id="checkBehinderungRollstuhl"
                  v-model="user.behinderung.rollstuhlNutzungNotwendig"
                  type="checkbox"
                  class="regular-checkbox"
                >
              </td>
              <td><label for="hilfsmittel">anderes Hilfsmittel:</label></td>
              <td>
                <input
                  id="hilfsmittel"
                  v-model="user.behinderung.weitereHilfsmittel"
                  type="text"
                >
              </td>
            </tr>
          </table>
          <br>
          <div>
            <label for="checkWertmarke"><b>Wertmarke vorhanden:</b></label>
            <input
              id="checkWertmarke"
              v-model="user.behinderung.wertmarkeVorhanden"
              type="checkbox"
              class="regular-checkbox"
            >
          </div>
          <br>
          <div>
            <label for="checkBegleitung"><b>Begleitung notwending:</b></label>
            <input
              id="checkBegleitung"
              v-model="user.behinderung.begleitungNotwendig"
              type="checkbox"
              class="regular-checkbox"
            >
          </div>
          <div v-if="user.behinderung.begleitungNotwendig">
            <table>
              <tr>
                <td><label for="checkBegleitungPflege">Pflege </label></td>
                <td>
                  <input
                    id="checkBegleitungPflege"
                    v-model="user.behinderung.begleitpersonPflege"
                    type="checkbox"
                    class="regular-checkbox"
                  >
                </td>
                <td><label for="checkBegleitungMedi">Medizinische Versorgung </label></td>
                <td>
                  <input
                    id="checkBegleitungMedi"
                    v-model="user.behinderung.begleitpersonMedizinischeVersorgung"
                    type="checkbox"
                    class="regular-checkbox"
                  >
                </td>
                <td><label for="checkBegleitungMobil">Mobilität</label></td>
                <td>
                  <input
                    id="checkBegleitungMobil"
                    v-model="user.behinderung.begleitpersonMobilitaet"
                    type="checkbox"
                    class="regular-checkbox"
                  >
                </td>
              </tr>
              <tr>
                <td><label for="checkBegleitungOrientierung">Orientierung</label></td>
                <td>
                  <input
                    id="checkBegleitungOrientierung"
                    v-model="user.behinderung.begleitpersonOrientierung"
                    type="checkbox"
                    class="regular-checkbox"
                  >
                </td>
                <td><label for="checkBegleitSozial">Soziale Begleitung</label></td>
                <td>
                  <input
                    id="checkBegleitSozial"
                    v-model="user.behinderung.begleitpersonSozialeBegleitung"
                    type="checkbox"
                    class="regular-checkbox"
                  >
                </td>
                <td><label for="eingeschraenkteSinne">Sinneswahrnehmung</label></td>
                <td>
                  <input
                    id="eingeschraenkteSinne"
                    v-model="user.behinderung.eingeschraenkteSinne"
                    type="text"
                  >
                </td>
              </tr>
            </table>
          </div>
        </div>
        <br>
        <div>
          <label for="hinweiseZumUmgangMitDemKind"><b>Hinweise zum Umgang mit Kind:</b></label>
          <textarea
            id="hinweiseZumUmgangMitDemKind"
            v-model="user.behinderung.hinweiseZumUmgangMitDemKind"
            rows="4"
            cols="50"
          />
        </div>
        <br>
        <div>
          <label for="checkUnterstuetzungSucheBegleitpersonNotwendig"><b>Benötigt Unterstützung bei
            der Organisation der Begleitperson</b></label>
          <input
            id="checkUnterstuetzungSucheBegleitpersonNotwendig"
            v-model="user.behinderung.unterstuetzungSucheBegleitpersonNotwendig"
            type="checkbox"
          >
        </div>
        <br>
        <div v-if="user.behinderung.unterstuetzungSucheBegleitpersonNotwendig">
          <table>
            <tr>
              <th>Kontaktdaten des regulären Dienstes:</th>
            </tr>
            <tr>
              <td>
                <textarea
                  id="behinderungKontaktdaten"
                  v-model="user.behinderung.gewohnterBegleitpersonenDienstleister"
                  rows="4"
                  cols="50"
                />
              </td>
            </tr>
          </table>
        </div>
        <br>
        <label for="checkKostenuebernahme"><b>Beantragung der Kostenübernahme</b><input
          id="checkKostenuebernahme"
          v-model="user.behinderung.beantragungKostenuebernahmeBegleitpersonNotwendig"
          type="checkbox"
          class="regular-checkbox"
        ></label> <br>
        <br>
        <input
          type="submit"
          value="Änderung speichern"
        >
      </form>
      <ErrorListBox
        v-if="errorMessages.length"
        :errors="errorMessages"
        :heading-text="errorHeadingText"
        class="error-list-box"
      />

      <hr>
      <h2>Angemeldete Projekte</h2>
      <div v-if="projectsOfUser">
        <table>
          <tr>
            <th>Name</th>
            <th>Stornieren</th>
          </tr>
          <tr
            v-for="projekt of projectsOfUser"
            :key="projekt.id"
          >
            <td><label> {{ projekt.name }}</label></td>
            <td>
              <button
                type="button"
                @click="unassignFromProject(projekt.id, user.id)"
              >
                Stornieren
              </button>
            </td>
          </tr>
        </table>
      </div>

      <h2>Stornierte Projekte</h2>
      <div v-if="cancelledProjectsOfUser">
        <table>
          <tr>
            <th>Name</th>
            <th>Aktivieren</th>
          </tr>
          <tr
            v-for="projekt of cancelledProjectsOfUser"
            :key="projekt.id"
          >
            <td><label>{{ projekt.name }}</label></td>
            <td>
              <button
                type="button"
                @click="reactivateProject(projekt.id, user.id)"
              >
                Reaktivieren
              </button>
            </td>
          </tr>
        </table>
      </div>

      <br>
      <hr>
      <h2>Zu folgendem Projekt eintragen:</h2>
      <div v-if="availableProjects">
        <table>
          <tr>
            <th>Name</th>
            <th>Anmelden</th>
          </tr>
          <tr
            v-for="projekt of availableProjects"
            :key="projekt.id"
          >
            <td><label> {{ projekt.name }}</label></td>
            <td>
              <button
                type="button"
                @click="assignToProject(projekt.id, user.id)"
              >
                Eintragen
              </button>
            </td>
          </tr>
        </table>
      </div>
      <br>
      <hr>
    </main>
    <div :class="popupClass">
      ✔ Erfolgreich!
    </div>
  </div>
</template>

<script>
import api from '@/modules/ferienpass-api';
import ErrorListBox from '@/components/ErrorListBox.vue';
import NavigationMenu from '@/components/NavigationMenu.vue';

export default {
  name: 'Teilnehmer',
  components: { NavigationMenu, ErrorListBox },
  data() {
    return {
      errorMessages: [],
      loaded: false,
      id: parseInt(this.$route.query.id, 10),
      user: null,
      allProjects: [],
      projectsOfUser: [],
      cancelledProjectsOfUser: [],
      popupClass: 'fadeOut',
    };
  },
  computed: {
    errorHeadingText() {
      return 'Speichern nicht möglich. Bitte beheben Sie folgende Fehler:';
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
    const dataPromises = [];
    dataPromises.push(this.loadUserData());
    dataPromises.push(this.loadProjects());
    dataPromises.push(this.loadProjectsOfUser());
    dataPromises.push(this.loadCancelledProjectsOfUser());
    Promise.all(dataPromises).then(() => { this.loaded = true; }).catch(
      (e) => this.errorMessages.push(e.toString()),
    );
  },
  methods: {
    loadUserData() {
      return api.getUser(this.id).then((user) => { this.user = user; });
    },
    loadProjects() {
      return api.getProjects().then((projects) => { this.allProjects = projects; });
    },
    loadProjectsOfUser() {
      return api.getUsersProjects(this.id).then((projects) => { this.projectsOfUser = projects; });
    },
    loadCancelledProjectsOfUser() {
      return api.getUsersCancelledProjects(this.id).then(
        (projects) => { this.cancelledProjectsOfUser = projects; },
      );
    },
    reloadProjectsOfUser() {
      this.loadProjectsOfUser();
      this.loadCancelledProjectsOfUser();
    },
    updateUser() {
      this.errorMessages = [];
      api.updateUser(this.user).then(() => {
        this.fadeInAndOutAfterTimeout();
      }).catch((errorMessages) => { this.errorMessages = errorMessages; });
    },
    unassignFromProject(projectId, userId) {
      api.deleteUserFromProject(projectId, userId).then(() => {
        this.fadeInAndOutAfterTimeout();
        this.reloadProjectsOfUser();
      });
    },
    assignToProject(projectId, userId) {
      api.addUserToProject(projectId, userId).then(() => {
        this.fadeInAndOutAfterTimeout();
        this.reloadProjectsOfUser();
      });
    },
    reactivateProject(projectId, userId) {
      this.assignToProject(projectId, userId);
    },
    fadeInAndOutAfterTimeout() {
      this.popupClass = 'fadeIn';
      const self = this;
      setTimeout(() => {
        self.popupClass = 'fadeOut';
      }, 2000);
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

main form {
  margin: auto;
  top: 40px;
  left: 0;
  bottom: 0;
  right: 0;
  width: 90%;
}

main form .caption {
  font-size: 30px;
  font-weight: bold;
}

main form input[type=text] {
  font-size: 16px;
  width: 200px;
  height: 30px;
  margin-bottom: 0px;
  display: block;
  background-color: #FAEBD7;
  border-radius: 5px;
  -moz-border-radius: 5px;
  -webkit-border-radius: 5px;
}


main form label {
  margin: 5px;
  float: left;
  display: block;
}

main h2 {
  margin: 20px;
}

main h1 {
  margin: 20px;
}

input[type=submit] {
  margin: 20px;
  background-color: #84845e;
  border: none;
  color: white;
  padding: 10px 24px;
  border-radius: 8px;
  text-align: center;
  text-decoration: none;
  font-size: 16px;
}

input[type=checkbox] {
  height: 15px;
  width: 20px;
  vertical-align: bottom;
  margin: 5px;
}

.limit {
  display: block;
  background-color: #e5d8ae;
  padding: 5px;
  border-radius: 5px;
  margin: 2px;
  font-size: 15px;
}


.regular-checkbox {
  -webkit-appearance: none;
  background-color: #fafafa;
  border: 1px solid #cacece;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05), inset 0px -15px 10px -12px rgba(0, 0, 0, 0.05);
  padding: 9px;
  border-radius: 3px;
  display: inline-block;
  position: relative;
  z-index: +1;
}

.regular-checkbox:active, .regular-checkbox:checked:active {
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05), inset 0px 1px 3px rgba(0, 0, 0, 0.1);
}

.regular-checkbox:checked {
  background-color: #e9ecee;
  border: 1px solid #adb8c0;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05),
    inset 0px -15px 10px -12px rgba(0, 0, 0, 0.05),
    inset 15px 10px -12px rgba(255, 255, 255, 0.1);
  color: #99a1a7;
}

.regular-checkbox:checked:after {
  content: '14';
  font-size: 14px;
  position: absolute;
  top: 0px;
  left: 3px;
  color: #99a1a7;
}

input[type=checkbox] {
  position: relative;
  visibility: hidden;
  cursor: pointer;
}

input[type=checkbox]:after {
  display: block;
  content: "Nein";
  position: absolute;
  top: 0;
  visibility: visible;
  height: 30px;
  line-height: 30px;
  width: 40px;
  text-align: center;
  border-radius: 4px;
  background: #FF7256;
  color: #000000;
  font-weight: 400;
  cursor: pointer;
  font-size: medium;
}

input[type=checkbox]:checked:after {
  content: "Ja";
  background: #76EE00;
  position: absolute;
  top: 0;
  visibility: visible;
  height: 30px;
  line-height: 30px;
  width: 40px;
  text-align: center;
  border-radius: 4px;
  font-weight: 400;
  color: #000000;
  cursor: pointer;
  font-size: large;
}

textarea {
  background-image: linear-gradient(
    hsl(190, 10%, 98%), hsl(190, 40%, 74%)
  );
  padding: 1ex;
  font-size: 1em;
  border-radius: 8px;
  box-sizing: border-box;
  color: black;
}

button {
  border-radius: 8px;
  background: #CD853F;
  color: #FFFFFF;
  font-size: medium;
  width: 100px;
}

th, td {
  min-width: 150px;
  padding: 10px 20px;
}

.fadeIn {
  text-align: center;
  vertical-align: middle;
  line-height: 90px;
  color: white;
  display: block;
  background-color: #6bbc6b;
  position: fixed;
  bottom: 20px;
  right: -10px;
  overflow-x: hidden;
  transition: .5s;
  width: 200px;
  height: 100px;
  border-radius: 10px;
}

.fadeOut {
  text-align: center;
  vertical-align: middle;
  line-height: 90px;
  color: #6bbc6b;
  display: block;
  background-color: #6bbc6b;
  position: fixed;
  bottom: 20px;
  right: -10px;
  overflow-x: hidden;
  transition: .5s;
  width: 0px;
  height: 100px;
}

</style>
