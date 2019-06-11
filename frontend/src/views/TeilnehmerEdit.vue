<template>
  <div>
    <NavigationMenu/>
    <main v-if="user">
      <h1>Teilnehmerbearbeitung</h1>

      <form method="post" v-on:submit.prevent="updateUser">

        <h2>Allgemeine Informationen</h2>
        <table border="0">
          <tr>
            <td><label for="firstName">Vorname: </label></td>
            <td><input type="text" id="firstName" placeholder="-" v-model="user.vorname"></td>
            <td><label for="street">Straße: </label></td>
            <td><input type="text" id="street" placeholder="-" v-model="user.strasse"></td>
          </tr>
          <tr>
            <td><label for="lastName">Nachname: </label></td>
            <td><input type="text" id="lastName" placeholder="-" v-model="user.nachname"></td>
            <td><label for="city">Stadt: </label></td>
            <td><input type="text" id="city" placeholder="-" v-model="user.stadt"></td>
          </tr>
          <tr>
            <td><label for="birthDate">Geburtsdatum (Jahr,Monat,Tag): </label></td>
            <td><input type="text" id="birthDate" placeholder="-" v-model="user.geburtsdatum"></td>
            <td><label for="postcode">Postleitzahl: </label></td>
            <td><input type="text" id="postcode" placeholder="-" v-model="user.postleitzahl"></td>
          </tr>
          <tr>
            <td><label for="telephone">Telefonnummer: </label></td>
            <td><input type="text" id="telephone" placeholder="-" v-model="user.telefon"></td>
            <td><label for="healthcare">Krankenkasse: </label></td>
            <td><input type="text" id="healthcare" v-model="user.krankenkasse"></td>
          </tr>
          <tr>
            <td><label for="email">eMail</label></td>
            <td><input type="text" id="email" placeholder="-" v-model="user.email"></td>
            <td><label for="checkBezahlt">Hat bezahlt</label></td>
            <td><input v-model="user.bezahlt" type="checkbox" id="checkBezahlt" class="regular-checkbox">
            </td>
          </tr>
        </table>

        <br/>
        <hr/>
        <h2>Weitere Angaben:</h2>
        <table>
          <tr>
            <td><label for="checkMedikation">Erlaube Medikation</label></td>
            <td><input type="checkbox" v-model="user.erlaubeMedikamentation" class="regular-checkbox"
                       id="checkMedikation"></td>
            <td><label for="checkHause">Darf alleine nach Hause gehen</label></td>
            <td><input v-model="user.darfAlleinNachHause" type="checkbox" id="checkHause"
                       class="regular-checkbox"></td>
          </tr>
          <tr>
            <td><label for="checkSchwimmen">Darf schwimmen</label></td>
            <td><input v-model="user.darfSchwimmen" type="checkbox" id="checkSchwimmen"
                       class="regular-checkbox"></td>
            <td><label for="schwimmAbzeichen">Schwimmstufe:</label></td>
            <td><input type="text" id="schwimmAbzeichen" v-model="user.schwimmAbzeichen"
                       class="regular-checkbox"></td>
          </tr>
          <tr>

            <td><label for="checkReiten">Darf reiten</label></td>
            <td><input v-model="user.darfReiten" type="checkbox" class="regular-checkbox"
                       id="checkReiten"></td>
            <td><label for="checkBehandlung">Behandlungserlaubnis bei Erkrankungen und Unfällen</label></td>
            <td><input v-model="user.darfBehandeltWerden" type="checkbox" id="checkBehandlung"
                       class="regular-checkbox"></td>
          </tr>
        </table>

        <br/>
        <hr/>
        <h2>Notfallkontaktdaten</h2>
        <table>
          <tr>
            <th colspan="2">Notfallkontakt</th>
            <th colspan="2">Arzt</th>
          </tr>
          <tr>
            <td colspan="2">
              <table>
                <tr>
                  <td><label for="contactName">Name, Vorname:</label></td>
                  <td><input id="contactName" type="text" v-model="user.notfallKontakt.name"></td>
                </tr>
                <tr>
                  <td><label for="contactaddress">Addresse:</label></td>
                  <td><input type="text" id="contactaddress" v-model="user.notfallKontakt.address">
                  </td>
                </tr>
                <tr>
                  <td><label for="contacttelephone">Telefon:</label></td>
                  <td><input type="text" id="contacttelephone"
                             v-model="user.notfallKontakt.telephone"></td>
                </tr>
              </table>
            </td>
            <td colspan="2">
              <table>
                <tr>
                  <td><label for="doctorname">Name, Vorname:</label></td>
                  <td><input type="text" id="doctorname" placeholder="Name" v-model="user.arzt.name">
                  </td>
                </tr>
                <tr>
                  <td><label for="doctoraddress">Adresse:</label></td>
                  <td><input type="text" id="doctoraddress" v-model="user.arzt.address"
                             placeholder="Addresse"></td>
                </tr>
                <tr>
                  <td><label for="doctortelephone">Telefon:</label></td>
                  <td><input type="text" id="doctortelephone" v-model="user.arzt.telephone"
                             placeholder="Telefon"></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>

        <input type="submit" value="Änderung speichern">
      </form>
      <ErrorListBox v-if="errorMessages.length" :errors="errorMessages" :heading-text="errorHeadingText" class="error-list-box"/>

      <br/>
      <hr/>
      <form method="post" v-on:submit.prevent="updateUser">
        <h2>Einschränkungen</h2>
        <table>
          <tr>
            <th>Allergien</th>
            <th>Krankheiten</th>
          </tr>
          <tr>
            <td><textarea rows="4" cols="50" v-model="user.allergien"/></td>
            <td><textarea rows="4" cols="50" v-model="user.krankheiten"/></td>
          </tr>
        </table>
        <table>
          <tr>
            <th>Hitzeempfindlichkeit</th>
            <th>Medikamente</th>
          </tr>
          <tr>
            <td><textarea rows="4" cols="50" v-model="user.hitzeempfindlichkeiten"/></td>
            <td><textarea rows="4" cols="50" v-model="user.medikamente"/></td>
          </tr>
        </table>

        <table>
          <tr>
            <th>Ernährungsbesonderheiten</th>
          </tr>
          <tr>
            <td><textarea rows="4" cols="50" v-model="user.essenLimitierungen"/></td>
          </tr>
        </table>

        <input type="submit" value="Änderung speichern">
      </form>
      <ErrorListBox v-if="errorMessages.length" :errors="errorMessages" :heading-text="errorHeadingText" class="error-list-box"/>

      <br>
      <hr/>
      <form method="post" v-on:submit.prevent="updateUser">

        <h3>Behinderungen</h3>
        <div>
          <label for="checkBehinderung"><b>Behinderungsausweis liegt vor:</b></label>
          <input v-model="user.liegtBehinderungVor" type="checkbox" id="checkBehinderung" class="regular-checkbox">
        </div>
        <div v-if="user.liegtBehinderungVor">
          <table>
            <tr>
              <th colspan="6">Art der Behinderung</th>
            </tr>
            <tr>
              <td><label for="checkBehinderungG">„G“ (erhebliche Gehbehinderung) </label></td>
              <td><input v-model="user.behinderung.merkzeichen_BeeintraechtigungImStrassenverkehr_G"
                         type="checkbox" class="regular-checkbox" id="checkBehinderungG"></td>
              <td><label for="checkBehinderungH">„H“ (Hilflosigkeit) </label></td>
              <td><input v-model="user.behinderung.merkzeichen_Hilflosigkeit_H" type="checkbox"
                         class="regular-checkbox" id="checkBehinderungH"></td>
              <td><label for="checkBehinderungaG">„aG“ (außergewöhnliche Gehbehinderung) </label></td>
              <td><input v-model="user.behinderung.merkzeichen_AussergewoehnlicheGehbehinderung_aG"
                         type="checkbox" class="regular-checkbox" id="checkBehinderungaG"></td>
            </tr>
            <tr>
              <td><label for="checkBehinderungBl">„Bl“ (Blindheit) </label></td>
              <td><input v-model="user.behinderung.merkzeichen_Blind_Bl" type="checkbox"
                         class="regular-checkbox" id="checkBehinderungBl"></td>
              <td><label for="checkBehinderungGl">„Gl“ (Gehörlosigkeit) </label></td>
              <td><input v-model="user.behinderung.merkzeichen_Gehoerlos_Gl" type="checkbox"
                         class="regular-checkbox" id="checkBehinderungGl"></td>
              <td><label for="checkBehinderungB">„B“ (Berechtigung zur Mitnahme einer Begleitperson) </label></td>
              <td><input
                  v-model="user.behinderung.merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B"
                  type="checkbox" id="checkBehinderungB"></td>
            </tr>
            <tr>
              <td><label for="checkBehinderungTBL">„TBL“ (Taubblindheit)</label></td>
              <td><input v-model="user.behinderung.merkzeichen_Taubblind_TBL" type="checkbox"
                         class="regular-checkbox" id="checkBehinderungTBL"></td>
              <td><label for="checkBehinderungRollstuhl">Rollstuhlnutzung </label></td>
              <td><input v-model="user.behinderung.rollstuhlNutzungNotwendig" type="checkbox"
                         class="regular-checkbox" id="checkBehinderungRollstuhl"></td>
              <td><label for="hilfsmittel">anderes Hilfsmittel:</label></td>
              <td><input type="text" id="hilfsmittel" v-model="user.behinderung.weitereHilfsmittel">
              </td>
            </tr>
          </table>
          <br>
          <div>
            <label for="checkWertmarke"><b>Wertmarke vorhanden:</b></label>
            <input v-model="user.behinderung.wertmarkeVorhanden"
                   type="checkbox" class="regular-checkbox"
                   id="checkWertmarke">
          </div>
          <br>
          <div>
            <label for="checkBegleitung"><b>Begleitung notwending:</b></label>
            <input v-model="user.behinderung.begleitungNotwendig"
                   type="checkbox" class="regular-checkbox"
                   id="checkBegleitung">
          </div>
          <div v-if="user.behinderung.begleitungNotwendig">
            <table>
              <tr>
                <td><label for="checkBegleitungPflege">Pflege </label></td>
                <td><input v-model="user.behinderung.begleitpersonPflege" type="checkbox"
                           class="regular-checkbox" id="checkBegleitungPflege"></td>
                <td><label for="checkBegleitungMedi">Medizinische Versorgung </label></td>
                <td><input v-model="user.behinderung.begleitpersonMedizinischeVersorgung"
                           type="checkbox" class="regular-checkbox" id="checkBegleitungMedi"></td>
                <td><label for="checkBegleitungMobil">Mobilität</label></td>
                <td><input v-model="user.behinderung.begleitpersonMobilitaet" type="checkbox"
                           class="regular-checkbox" id="checkBegleitungMobil"></td>
              </tr>
              <tr>
                <td><label for="checkBegleitungOrientierung">Orientierung</label></td>
                <td><input v-model="user.behinderung.begleitpersonOrientierung" type="checkbox"
                           class="regular-checkbox" id="checkBegleitungOrientierung"></td>
                <td><label for="checkBegleitSozial">Soziale Begleitung</label></td>
                <td><input v-model="user.behinderung.begleitpersonSozialeBegleitung" type="checkbox"
                           class="regular-checkbox" id="checkBegleitSozial"></td>
                <td><label for="eingeschraenkteSinne">Sinneswahrnehmung</label></td>
                <td><input type="text" v-model="user.behinderung.eingeschraenkteSinne" id="eingeschraenkteSinne"></td>
              </tr>
            </table>
          </div>
        </div>
        <br/>
        <div>
          <label for="hinweiseZumUmgangMitDemKind"><b>Hinweise zum Umgang mit Kind:</b></label>
          <textarea id="hinweiseZumUmgangMitDemKind" rows="4" cols="50" v-model="user.behinderung.hinweiseZumUmgangMitDemKind"/>
        </div>
        <br/>
        <div>
          <label for="checkUnterstuetzungSucheBegleitpersonNotwendig"><b>Benötigt Unterstützung bei der Organisation der Begleitperson</b></label>
          <input v-model="user.behinderung.unterstuetzungSucheBegleitpersonNotwendig" type="checkbox"
              id="checkUnterstuetzungSucheBegleitpersonNotwendig">
        </div>
        <br/>
        <div v-if="user.behinderung.unterstuetzungSucheBegleitpersonNotwendig">
          <table>
            <tr>
              <th>Kontaktdaten des regulären Dienstes:</th>
            </tr>
            <tr>
              <td><textarea id="behinderungKontaktdaten" rows="4" cols="50"
                            v-model="user.behinderung.gewohnterBegleitpersonenDienstleister"/></td>
            </tr>
          </table>
        </div>
        <br/>
        <label for="checkKostenuebernahme"><b>Beantragung der Kostenübernahme</b><input
            v-model="user.behinderung.beantragungKostenuebernahmeBegleitpersonNotwendig"
            type="checkbox" class="regular-checkbox" id="checkKostenuebernahme"></label> <br/>
        <br/>
        <input type="submit" value="Änderung speichern">
      </form>
      <ErrorListBox v-if="errorMessages.length" :errors="errorMessages" :heading-text="errorHeadingText" class="error-list-box"/>

      <hr/>
      <h2>Angemeldete Projekte</h2>
      <div v-if="projectsOfUser">
        <table>
          <tr>
            <th>Name</th>
            <th>Stornieren</th>
          </tr>
          <tr v-for="(projekt, index) of projectsOfUser">
            <td><label> {{projekt.name}}</label></td>
            <td>
              <button type="button" v-on:click="unassignFromProject(projekt.id, user.id)">Stornieren</button>
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
          <tr v-for="(projekt, index) of cancelledProjectsOfUser">
            <td><label>{{projekt.name}}</label></td>
            <td>
              <button type="button" v-on:click="reactivateProject(projekt.id, user.id)">Reaktivieren</button>
            </td>
          </tr>
        </table>
      </div>

      <br/>
      <hr/>
      <h2>Zu folgendem Projekt eintragen:</h2>
      <div v-if="availableProjects">
        <table>
          <tr>
            <th>Name</th>
            <th>Anmelden</th>
          </tr>
          <tr v-for="(projekt, index) of availableProjects">
            <td><label> {{projekt.name}}</label></td>
            <td>
              <button type="button" v-on:click="assignToProject(projekt.id, user.id)">Eintragen</button>
            </td>
          </tr>
        </table>
      </div>
      <br/>
      <hr/>
    </main>
    <div :class="popupClass">✔ Erfolgreich!</div>
  </div>
</template>

<script>
import api from '../modules/ferienpass-api';
import ErrorListBox from "../components/ErrorListBox";
import NavigationMenu from "../components/NavigationMenu";

export default {
  name: 'Teilnehmer',
  components: {NavigationMenu, ErrorListBox},
  data() {
    return {
      errorMessages: [],
      loaded: false,
      id: parseInt(this.$route.query.id),
      user: null,
      allProjects: [],
      projectsOfUser: [],
      cancelledProjectsOfUser: [],
      popupClass: 'fadeOut'
    };
  },
  computed: {
    errorHeadingText() {
      return "Speichern nicht möglich. Bitte beheben Sie folgende Fehler:"
    },
    availableProjects() {
      return this.allProjects.filter(project => {
        const isProjectOfUser = this.projectsOfUser.map(userProject => userProject.id).includes(project.id);
        const isCancelledProjectOfuser = this.cancelledProjectsOfUser.map(userProject => userProject.id).includes(project.id);
        return !isProjectOfUser && !isCancelledProjectOfuser;
      })
    }
  },
  created() {
    const dataPromises = [];
    dataPromises.push(this.loadUserData());
    dataPromises.push(this.loadProjects());
    dataPromises.push(this.loadProjectsOfUser());
    dataPromises.push(this.loadCancelledProjectsOfUser());
    Promise.all(dataPromises).then(response => this.loaded = true).catch(e => this.errorMessages.push(e.toString()));
  },
  methods: {
    loadUserData() {
      return api.getUser(this.id).then(user => this.user = user);
    },
    loadProjects() {
      return api.getProjects().then(projects => this.allProjects = projects)
    },
    loadProjectsOfUser() {
      return api.getUsersProjects(this.id).then(projects => this.projectsOfUser = projects)
    },
    loadCancelledProjectsOfUser() {
      return api.getUsersCancelledProjects(this.id).then(projects => this.cancelledProjectsOfUser = projects)
    },
    reloadProjectsOfUser() {
      this.loadProjectsOfUser();
      this.loadCancelledProjectsOfUser();
    },
    updateUser() {
      this.errorMessages = [];
      api.updateUser(this.user).then(response => {
        this.fadeInAndOutAfterTimeout()
      }).catch(errorMessages => this.errorMessages = errorMessages)
    },
    unassignFromProject(projectId, userId) {
      api.deleteUserFromProject(projectId, userId).then(response => {
        this.fadeInAndOutAfterTimeout();
        this.reloadProjectsOfUser();
      })
    },
    assignToProject(projectId, userId) {
      api.addUserToProject(projectId, userId).then(response => {
        this.fadeInAndOutAfterTimeout();
        this.reloadProjectsOfUser();
      })
    },
    reactivateProject(projectId, userId) {
      this.assignToProject(projectId, userId);
    },
    fadeInAndOutAfterTimeout() {
      this.popupClass = 'fadeIn';
      var self = this;
      setTimeout(function () {
        self.popupClass = 'fadeOut';
      }, 2000);
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>


.limit {
  display: -block;
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
	box-shadow: 0 1px 2px rgba(0,0,0,0.05), inset 0px -15px 10px -12px rgba(0,0,0,0.05);
	padding: 9px;
	border-radius: 3px;
	display: inline-block;
	position: relative;
	z-index: +1;
}
.regular-checkbox:active, .regular-checkbox:checked:active {
	box-shadow: 0 1px 2px rgba(0,0,0,0.05), inset 0px 1px 3px rgba(0,0,0,0.1);
}

.regular-checkbox:checked {
	background-color: #e9ecee;
	border: 1px solid #adb8c0;
	box-shadow: 0 1px 2px rgba(0,0,0,0.05), inset 0px -15px 10px -12px rgba(0,0,0,0.05), inset 15px 10px -12px rgba(255,255,255,0.1);
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
  background-image:linear-gradient(
     hsl(190,10%,98%), hsl(190,40%,74%)
  );
  padding:1ex;
  font-size:1em;
  border-radius: 8px;
  box-sizing:border-box;
  color:black;
}

button {
  border-radius: 8px;
  background: #CD853F;
  color: #FFFFFF;
  font-size: medium;
  width:100px;
}

</style>

