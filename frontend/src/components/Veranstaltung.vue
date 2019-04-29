<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <html>
    <nav>
      <a href="/#/Verwaltung/" >Alle Veranstaltungen</a>
      <a href="/#/VeranstaltungEdit?id=-1" class="selected" >Veranstaltung erstellen </a>
      <a href="/#/Teilnehmer/" >Alle Teilnehmer</a>
      <a href="/#/TeilnehmerAdd/" >Teilnehmer erstellen</a>
    </nav>
    <main>
      <h1>Veranstaltungsformular</h1>
      <form method="post" v-on:submit.prevent="postProject">
            <span class="caption">Veranstaltung hinzufügen:</span> <br/>
            <label for ="label">Veranstaltungsname: </label>
            <input type="text" name="label" v-model="projectName" placeholder="Veranstaltungsname" required>
            <label for ="date">Datum: </label>
            <input type="text" name="org" v-model="projectDate" placeholder="Datum (TT.MM.JJJJ)" required>
            <label for ="num">Plätze (gesamt): </label>
            <input type="text" name="num" v-model="projectSlots" placeholder="Plätze" required>
            <label for ="reserve">Plätze (reserviert): </label>
            <input type="text" name="num" v-model="projectSlotsreserved" placeholder="Reservierte Plätze" required>
            <label for ="age">Alter: </label>
            <input type="text" name="num" v-model="projectAge" placeholder="Altersbegrenzung" required>
            <label for ="price">Preis: </label>
            <input type="text" name="num" v-model="projectPrice" placeholder="Preis" required>
            <label for ="weblink">Weblink: </label>
            <input type="text" name="num" v-model="projectWeblink" placeholder="Weblink" required>
            <input type="submit" v-on:click="" value="Hinzufügen">
          </form>
    </main>
    <div :class="popupClass">✔ Erfolgreich</div>
    </html>
</template>


<script>
import { AXIOS } from './http-common';

export default {
  name: 'Veranstaltung-erstellen',
  data () {
    return {
      title: 'Veranstaltung erstellen',
      projectName: '',
      projectDate: '',
      projectAge: '',
      projectPrice: '',
      projectSlots: '',
      projectSlotsreserved: '',
      projectWeblink: '',
      errors: [],
      popupClass: 'fadeOut'
    }
  },
  methods: {
    postProject () {
      var params = new URLSearchParams();
      params.append('name', this.projectName);
      params.append('date', this.projectDate);
      params.append('age', parseInt(this.projectAge));
      params.append('price', parseInt(this.projectPrice));
      params.append('slots', parseInt(this.projectSlots));
      params.append('slotsReserved', parseInt(this.projectSlotsreserved));
      params.append('weblink', this.projectWeblink);
      AXIOS.post('/createproject', params)
      .then(response => {})
      .catch(e => {
        this.errors.push(e)
      })
      this.popupClass = 'fadeIn'
      var self = this;
      setTimeout(function () {
        self.popupClass = 'fadeOut';
      }, 2000);
    },
    create () {
      alert('Sie haben das Event erstellt');
    }
  }
}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

