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
                <td><input type="text" id="firstName" placeholder="Vorname" v-model="user.vorname"></td>
                <td><label for ="street">Straße: </label></td>
                <td><input type="text" id="street" placeholder="Straße" v-model="user.strasse"></td>
            </tr>
            <tr>
                <td><label for ="lastName">Nachname: </label></td>
                <td><input type="text" id ="lastName" placeholder="Nachname" v-model="user.nachname"></td>
                <td><label for ="city">Stadt: </label></td>
                <td><input type="text" id="city" placeholder="Stadt" v-model="user.stadt"></td>
            </tr>
            <tr>
                <td><label for ="birthDate">Geburtsdatum (Jahr,Monat,Tag): </label></td>
                <td><input type="text" id="birthDate" placeholder="Geburtstag" v-model="user.geburtsdatum"></td>
                <td><label for ="postcode">Postleitzahl: </label></td>
                <td><input type="text" id="postcode" placeholder="Postleitzahl" v-model="user.postleitzahl"></td>
            </tr>
            <tr>
                <td><label for ="telephone">Telefonnummer: </label></td>
                <td><input type="text" id="telephone" placeholder="Telefonnummer" v-model="user.telefon"></td>
                <td><label for ="healthcare">Krankenkasse: </label></td>
                <td><input type="text" id="healthcare" v-model="user.krankenkasse"></td>
            </tr>
        </table>
        <br />
        <hr />
        <h2>Weitere Angaben:</h2>
        <table>
            <tr>
                <td><label><input v-model="user.erlaubeMedikamentation" type="checkbox" id="check">Erlaube Medikation</label></td>
                <td><label><input v-model="user.darfAlleinNachHause" type="checkbox" id="check">Darf alleine nach Hause gehen</label></td>
                <td><label><input v-model="user.darfReiten" type="checkbox" id="check">Darf reiten</label><br/></td>
            </tr>
            <tr>
                <td><label><input v-model="user.darfSchwimmen" type="checkbox" id="check">Darf schwimmen</label><br/></td>
                <td>Schwimmstufe:</td><td><input type="text" id="schwimmAbzeichen" v-model="user.schwimmAbzeichen"></td>
                </tr><tr>
                <td><label><input v-model="user.bezahlt" type="checkbox" id="check">Hat bezahlt</label></td><td />
                <td><label><input v-model="user.darfBehandeltWerden" type="checkbox" id="check">Behandlungserlaubnis bei Erkrankungen und Unfällen</label></td><td />
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
      <label><b>Behinderungsausweis liegt vor:</b><input v-model="user.liegtBehinderungVor" type="checkbox" id="check"></label> <br />
      <div v-if="user.liegtBehinderungVor">
      <table>
      <tr><th colspan="4">Art der Behinderung</th></tr>
      <tr>
      <td><label><input v-model="user.behinderung.merkzeichen_BeeintraechtigungImStrassenverkehr_G" type="checkbox" id="check">„G“ (erhebliche Gehbehinderung) </label></td>
      <td><label><input v-model="user.behinderung.merkzeichen_Hilflosigkeit_H" type="checkbox" id="check">„H“ (Hilflosigkeit) </label></td>
      <td><label><input v-model="user.behinderung.merkzeichen_AussergewoehnlicheGehbehinderung_aG" type="checkbox" id="check">„aG“ (außergewöhnliche Gehbehinderung) </label></td>
      </tr><tr>
      <td><label><input v-model="user.behinderung.merkzeichen_Blind_Bl" type="checkbox" id="check">„Bl“ (Blindheit) </label></td>
      <td><label><input v-model="user.behinderung.merkzeichen_Gehoerlos_Gl" type="checkbox" id="check">„Gl“ (Gehörlosigkeit) </label></td>
      <td><label><input v-model="user.behinderung.merkzeichen_BerechtigtZurMitnahmeEinerBegleitperson_B" type="checkbox" id="check">„B“ (Berechtigung zur Mitnahme einer Begleitperson) </label></td>
      </tr><tr>
      <td><label><input v-model="user.behinderung.merkzeichen_Taubblind_TBL" type="checkbox" id="check">„TBL“ (Taubblindheit)</label></td>
      <td><label><input v-model="user.behinderung.rollstuhlNutzungNotwendig" type="checkbox" id="check">Rollstuhlnutzung </label></td>
      <td>anderes Hilfsmittel:<input type="text" id="hilfsmittel" v-model="user.behinderung.weitereHilfsmittel"></td></tr>
      </table>
      <br />
      <label><b>Wertmarke vorhanden:</b><input v-model="user.behinderung.wertmarkeVorhanden" type="checkbox" id="check"></label> <br />
      <br />
      <label><b>Begleitung notwending:</b><input v-model="user.behinderung.begleitungNotwendig" type="checkbox" id="check"></label> <br />

      <div v-if="user.behinderung.begleitungNotwendig">
      <table><tr>
      <td><label><input v-model="user.behinderung.begleitpersonPflege" type="checkbox" id="check">Pflege </label></td>
      <td><label><input v-model="user.behinderung.begleitpersonMedizinischeVersorgung" type="checkbox" id="check">Medizinische Versorgung </label></td>
      <td><label><input v-model="user.behinderung.begleitpersonMobilitaet" type="checkbox" id="check">Mobilität</label></td>
      </tr><tr>
      <td><label><input v-model="user.behinderung.begleitpersonOrientierung" type="checkbox" id="check">Orientierung</label></td>
      <td><label><input v-model="user.behinderung.begleitpersonSozialeBegleitung" type="checkbox" id="check">Soziale Begleitung</label></td>
      <td><label>Sinneswahrnehmung</label><input type="text" v-model="user.behinderung.eingeschraenkteSinne"></td>
      </tr>
      </table>
      </div>
      </div>
      <br />
      <table><tr><td>Hinweise zum Umgang mit Kind</td><td><textarea rows="4" cols="50" v-model="user.behinderung.hinweiseZumUmgangMitDemKind" /></td></tr></table>
      <br />
      <label><b>Benötigt Unterstützung bei der Organisation der Begleitperson</b><input v-model="user.behinderung.unterstuetzungSucheBegleitpersonNotwendig" type="checkbox" id="check"></label> <br />

      <div v-if="user.behinderung.unterstuetzungSucheBegleitpersonNotwendig">
      <table><tr>
      <td>Kontaktdaten des regulären Dienstes:</td><td><textarea rows="4" cols="50" v-model="user.behinderung.gewohnterBegleitpersonenDienstleister" /></td></tr></table>
      </div>
      <br />
      <label><b>Beantragung der Kostenübernahme</b><input v-model="user.behinderung.beantragungKostenuebernahmeBegleitpersonNotwendig" type="checkbox" id="check"></label> <br />
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
      AXIOS.post('/cancelproject', {
        user_id: id,
        project: projectId
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
        })
          .catch(e => {
            this.errors.push(e)
          })
      this.getProjectsOfUser()
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
</style>

