<template>
    <html>
    <nav>
      <a href="/#/Veranstaltungen/" >Alle Veranstaltungen</a>
      <a href="/#/VeranstaltungEdit?id=-1">Veranstaltung erstellen </a>
      <a href="/#/Teilnehmer/" class="selected" >Alle Teilnehmer</a>
    </nav>
    <main>
      <h1>Teilnehmerbearbeitung</h1>
      <form method="post" v-on:submit.prevent="updateUser">
        <h2>Allgemeine Informationen</h2>
        <table border="0">
            <tr>
                <td><label for ="firstName">Vorname: </label></td>
                <td><input type="text" id="firstName" placeholder="-" v-model="user.vorname"></td>
                <td><label for ="street">Straße: </label></td>
                <td><input type="text" id="street" placeholder="-" v-model="user.strasse"></td>
            </tr>
            <tr>
                <td><label for ="lastName">Nachname: </label></td>
                <td><input type="text" id ="lastName" placeholder="-" v-model="user.nachname"></td>
                <td><label for ="city">Stadt: </label></td>
                <td><input type="text" id="city" placeholder="-" v-model="user.stadt"></td>
            </tr>
            <tr>
                <td><label for ="birthDate">Geburtsdatum (Jahr,Monat,Tag): </label></td>
                <td><input type="text" id="birthDate" placeholder="-" v-model="user.geburtsdatum"></td>
                <td><label for ="postcode">Postleitzahl: </label></td>
                <td><input type="text" id="postcode" placeholder="-" v-model="user.postleitzahl"></td>
            </tr>
            <tr>
                <td><label for ="telephone">Telefonnummer: </label></td>
                <td><input type="text" id="telephone" placeholder="-" v-model="user.telefon"></td>
                <td><label for ="healthcare">Krankenkasse: </label></td>
                <td><input type="text" id="healthcare" v-model="user.krankenkasse"></td>
            </tr>
            <tr>
                <td><label for="email">eMail</label></td>
                <td><input type="text" id="email" placeholder="-" v-model="user.email"></td>
                <td><label>Hat bezahlt</label></td><td><input v-model="user.bezahlt" type="checkbox" id="check" class="regular-checkbox"></td>
            </tr>
        </table>
        <br />
        <hr />
        <h2>Weitere Angaben:</h2>
        <table>
            <tr>
                <td><label>Erlaube Medikation</label></td><td><input type="checkbox" v-model="user.erlaubeMedikamentation" class="regular-checkbox" id="checkMedikation"></input></td>
                <td><label>Darf alleine nach Hause gehen</label></td><td><input v-model="user.darfAlleinNachHause" type="checkbox" id="checkHause" class="regular-checkbox"></td>
            </tr>
            <tr>
                <td><label>Darf schwimmen</label></td><td><input v-model="user.darfSchwimmen" type="checkbox" id="checkSchwimmen" class="regular-checkbox"></td>
                <td>Schwimmstufe:</td><td><input type="text" id="schwimmAbzeichen" v-model="user.schwimmAbzeichen" class="regular-checkbox"></td>
            </tr>
            <tr>

                <td><label>Darf reiten</label></td><td><input v-model="user.darfReiten" type="checkbox" class="regular-checkbox" id="checkReiten"></td>
                <td><label>Behandlungserlaubnis bei Erkrankungen und Unfällen</label></td><td><input v-model="user.darfBehandeltWerden" type="checkbox" id="checkBehandlung" class="regular-checkbox"></td>
            </tr>
        </table>
        <br />
        <hr />
        <h2>Notfallkontaktdaten</h2>
        <table><tr><th colspan="2">Notfallkontakt</th><th colspan="2">Arzt</th></tr>
        <tr>
        <td colspan="2">
        <table>
        <tr>
        <td><label>Name, Vorname:</label></td>
        <td><input type="text" v-model="user.notfallKontakt.name"></td>
        </tr><tr>
        <td><label>Addresse:</label></td>
        <td><input type="text" id="contactaddress" v-model="user.notfallKontakt.address"></td>
        </tr><tr>
        <td><label>Telefon:</label></td>
        <td><input type="text" id="contacttelephone" v-model="user.notfallKontakt.telephone"></td>
        </tr>
        </table></td>
        <td colspan="2">
        <table><tr>
        <td><label>Name, Vorname:</label></td>
        <td><input type="text" id="doctorname" placeholder="Name" v-model="user.arzt.name"></td>
        </tr><tr>
        <td><label>Adresse:</label></td>
        <td><input type="text" id="doctoraddress" v-model="user.arzt.address" placeholder="Addresse"></td>
        </tr><tr>
        <td><label>Telefon:</label></td>
        <td><input type="text" id="doctortelephone" v-model="user.arzt.telephone" placeholder="Telefon"></td>
        </tr></table></td>
        </tr></table>

        <input type="submit" value="Änderung speichern">
        </form>


        <br />
        <hr />
        <form method="post" v-on:submit.prevent="updateUser">
        <h2>Einschränkungen</h2>
        <table><tr>
        <th>Allergien</th><th>Krankheiten</th></tr>
        <tr>
        <td><textarea rows="4" cols="50" v-model="user.allergien" /></td>
        <td><textarea rows="4" cols="50" v-model="user.krankheiten" /></td>
        </tr>
        </table>
        <table><tr>
        <th>Hitzeempfindlichkeit</th><th>Medikamente</th></tr>
        <tr>
        <td><textarea rows="4" cols="50" v-model="user.hitzeempfindlichkeiten" /></td>
        <td><textarea rows="4" cols="50" v-model="user.medikamente" /></td>
        </tr>
       </table>

        <table><tr><th>Ernährungsbesonderheiten</th></tr>
        <tr><td><textarea rows="4" cols="50" v-model="user.essenLimitierungen" /></td>
        </tr>
        </table>
        <input type="submit" value="Änderung speichern">
<br />
</form>
<hr />
<form method="post" v-on:submit.prevent="updateUser">
      <h3>Behinderungen</h3>
      <label><b>Behinderungsausweis liegt vor:</b><input v-model="user.liegtBehinderungVor" type="checkbox" id="check" class="regular-checkbox"></label> <br />
      <div v-if="user.liegtBehinderungVor">
      <table>
      <tr><th colspan="4">Art der Behinderung</th></tr>
      <tr>
      <td><label>„G“ (erhebliche Gehbehinderung) </label></td><td><input v-model="user.behinderung.merkzeichen_BeeintraechtigungImStrassenverkehr_G" type="checkbox" class="regular-checkbox" id="check"></td>
      <td><label>„H“ (Hilflosigkeit) </label></td><td><input v-model="user.behinderung.merkzeichen_Hilflosigkeit_H" type="checkbox" class="regular-checkbox" id="check"></td>
      <td><label>„aG“ (außergewöhnliche Gehbehinderung) </label></td><td><input v-model="user.behinderung.merkzeichen_AussergewoehnlicheGehbehinderung_aG" type="checkbox" class="regular-checkbox" id="check"></td>
      </tr><tr>
      <td><label>„Bl“ (Blindheit) </label></td><td><input v-model="user.behinderung.merkzeichen_Blind_Bl" type="checkbox" class="regular-checkbox" id="check"></td>
      <td><label>„Gl“ (Gehörlosigkeit) </label></td><td><input v-model="user.behinderung.merkzeichen_Gehoerlos_Gl" type="checkbox" class="regular-checkbox" id="check"></td>
      <td><label>„B“ (Berechtigung zur Mitnahme einer Begleitperson) </label></td><td><input v-model="user.behinderung.merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B" type="checkbox" id="check"></td>
      </tr><tr>
      <td><label>„TBL“ (Taubblindheit)</label></td><td><input v-model="user.behinderung.merkzeichen_Taubblind_TBL" type="checkbox" class="regular-checkbox" id="check"></td>
      <td><label>Rollstuhlnutzung </label></td><td><input v-model="user.behinderung.rollstuhlNutzungNotwendig" type="checkbox" class="regular-checkbox" id="check"></td>
      <td>anderes Hilfsmittel:</td><td><input type="text" id="hilfsmittel" v-model="user.behinderung.weitereHilfsmittel"></td></tr>
      </table>
      <br />
      <label><b>Wertmarke vorhanden:</b><input v-model="user.behinderung.wertmarkeVorhanden" type="checkbox" class="regular-checkbox" id="check"></label> <br />
      <br />
      <label><b>Begleitung notwending:</b><input v-model="user.behinderung.begleitungNotwendig" type="checkbox" class="regular-checkbox" id="check"></label> <br />

      <div v-if="user.behinderung.begleitungNotwendig">
      <table><tr>
      <td><label>Pflege </label></td><td><input v-model="user.behinderung.begleitpersonPflege" type="checkbox" class="regular-checkbox" id="check"></td>
      <td><label>Medizinische Versorgung </label></td><td><input v-model="user.behinderung.begleitpersonMedizinischeVersorgung" type="checkbox" class="regular-checkbox" id="check"></td>
      <td><label>Mobilität</label></td><td><input v-model="user.behinderung.begleitpersonMobilitaet" type="checkbox" class="regular-checkbox" id="check"></td>
      </tr><tr>
      <td><label>Orientierung</label></td><td><input v-model="user.behinderung.begleitpersonOrientierung" type="checkbox" class="regular-checkbox" id="check"></td>
      <td><label>Soziale Begleitung</label></td><td><input v-model="user.behinderung.begleitpersonSozialeBegleitung" type="checkbox" class="regular-checkbox" id="check"></td>
      <td><label>Sinneswahrnehmung</label></td><td><input type="text" v-model="user.behinderung.eingeschraenkteSinne"></td>
      </tr>
      </table>
      </div>
      </div>
      <br />

      <table><tr><th /><th/></tr><tr><td>Hinweise zum Umgang mit Kind:</td><td><textarea rows="4" cols="50" v-model="user.behinderung.hinweiseZumUmgangMitDemKind" /></td></tr></table>
      <br />
      <label><b>Benötigt Unterstützung bei der Organisation der Begleitperson</b><input v-model="user.behinderung.unterstuetzungSucheBegleitpersonNotwendig" type="checkbox" id="check"></label> <br />

      <div v-if="user.behinderung.unterstuetzungSucheBegleitpersonNotwendig">
      <table><tr><th /><th /></tr><tr>
      <td>Kontaktdaten des regulären Dienstes:</td><td><textarea rows="4" cols="50" v-model="user.behinderung.gewohnterBegleitpersonenDienstleister" /></td></tr></table>
      </div>
      <br />
      <label><b>Beantragung der Kostenübernahme</b><input v-model="user.behinderung.beantragungKostenuebernahmeBegleitpersonNotwendig" type="checkbox" class="regular-checkbox" id="check"></label> <br />
<br />
<input type="submit" value="Änderung speichern">
</form>
<hr />
      <h2>Angemeldete Projekte</h2>
      <div v-if="projectsOfUser">
      <table>
      <tr><th>Name</th><th>Stornieren</th></tr>
      <tr v-for="(projekt, index) of projectsOfUser">
      <td><label> {{projekt.name}}</label></td>
      <td><button v-on:click="cancelProject(user.id,projekt.id)">Stornieren</button></td>
      </tr>
      </table>
      </div>

      <h2>Stornierte Projekte</h2>
      <div v-if="user.stornierteTeilnehmer">
      <table>
      <tr><th>Name</th><th>Aktivieren</th></tr>
      <tr v-for="(projekt, index) of user.angemeldeteProjekte">
      <td><label>{{projekt.name}}</label></td>
      <td><button v-on:click="activateProject(user.id, projekt.id)">Reaktivieren</button></td>
      </tr>
      </table>
      </div>
<br />
<hr />
      <h2>Zu folgendem Projekt eintragen:</h2>
      <div v-if="projectsOfUser">
      <table>
      <tr><th>Name</th><th>Anmelden</th></tr>
      <tr v-for="(projekt, index) of allAvailableProjects">
      <td><label> {{projekt.name}}</label></td>
      <td><button v-on:click="assignProject(user.id,projekt.id)">Eintragen</button></td>
      </tr>
      </table>
      </div>
<br />
<hr />
    </main>
      <div :class="popupClass">✔ Erfolgreich!</div>
    </html>
</template>

<script>
import { AXIOS } from './http-common';

export default {
  name: 'Teilnehmer',
  data () {
    return {
      user: [],
      projectsOfUser: [],
      allAvailableProjects: [],
      allRawProjects: [],
      canceldProjectsOfUser: [],
      newBehinderung: [],
      popupClass: 'fadeOut',
      errors: []
    };
  },
  created () {
    this.getUserData()
    this.getProjectsOfUser()
    this.getAllProjects()
  },
  methods: {
    updateUser() {
      this.user.id = parseInt(this.$route.query.id);
      AXIOS.put('/user', this.user)
          .then(response => {
              this.popupClass = 'fadeIn'
              var self = this;
              setTimeout(function () {
                  self.popupClass = 'fadeOut';
              }, 2000);
          })
          .catch(e => {
              this.errors.push(e)
          })
    },
    deleteListItem (id, typeList, itemPos) {
    /* note that the numbers of variable typeList correspond to the index value of the enumeration ListType in the backend */
      AXIOS.post('/deletelistitem', {
        user_id: id,
        type: typeList,
        item: itemPos
      })
      .then(response => {
        this.popupClass = 'fadeIn'
        var self = this;
        setTimeout(function () {
          self.popupClass = 'fadeOut';
        }, 2000);
      })
      .catch(e => {
        this.errors.push(e)
      })
    },
    cancelProject (id, projectId) {
      AXIOS.post('/removefromproject', {
        userId: id,
        projectId: projectId
      })
        .then(response => {
          this.popupClass = 'fadeIn';
          var self = this;
          setTimeout(function () {
            self.popupClass = 'fadeOut';
          }, 2000);
          this.getProjectsOfUser()
        })
          .catch(e => {
            this.errors.push(e)
          });
    },
    assignProject (id, projectId) {
      AXIOS.post('/assignProject', {
        user: id,
        project: projectId
      })
        .then(response => {
          this.popupClass = 'fadeIn'
          var self = this;
          setTimeout(function () {
            self.popupClass = 'fadeOut';
          }, 2000);
          this.getProjectsOfUser()
        })
          .catch(e => {
            this.errors.push(e)
          });
    },
    getUserData () {
      var id = parseInt(this.$route.query.id);
      AXIOS.get('/user/' + id)
        .then(response => {
          this.user = response.data
        })
        .catch(e => {
          this.errors.push(e)
        })
    },
    getAllProjects () {
      AXIOS.get('/allprojects')
        .then(response => {
          this.allAvailableProjects = response.data
        })
        .catch(e => {
          this.errors.push(e)
        })
      console.log(this.allRawProjects)
      /* for (var i = 0; i < this.allRawProjects.length; i++) {
        this.projectsOfUser.forEach(function (project) {
          if (project.id === this.allAvailableProjects[i].id) {
            this.allAvailableProjects[this.allAvailableProjects.length] = project
          }
        })
      } */
    },
    getProjectsOfUser () {
      var id = parseInt(this.$route.query.id);
      AXIOS.get('/projectsofid', {
        params: {
          userID: id
        }
      })
        .then(response => {
          this.projectsOfUser = response.data
        })
        .catch(e => {
          this.errors.push(e)
        })
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

