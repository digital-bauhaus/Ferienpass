<template>
  <div class="user-list-export">
    <b-button
      class="mb-3"
      @click="onButtonClick"
    >
      PDF Erzeugen
    </b-button>
    <b-table
      id="user-list-export-table"
      striped
      hover
      outlined
      :responsive="true"
      primary-key="id"
      :fields="fields"
      :items="users"
    >
      <!-- eslint-disable max-len -->
      <template v-slot:cell(name)="row">
        {{ row.item.vorname }} {{ row.item.nachname }}
      </template>
      <template v-slot:cell(alter)="row">
        {{ calculateAgeFromBirthday(row.item.geburtsdatum) }}
      </template>
      <template v-slot:cell(notfall)="row">
        <ul>
          <li>Name: {{ row.item.notfallKontakt.name }}<br></li>
          <li>Tel: {{ row.item.notfallKontakt.telefon }}<br></li>
        </ul>
      </template>
      <template v-slot:cell(zustimmung)="row">
        <ul>
          <li>Behandlungserlaubnis? {{ row.item.darfBehandeltWerden ? 'Ja' : 'Nein' }}<br></li>
          <li>Allein heimgehen? {{ row.item.darfAlleinNachHause ? 'Ja' : 'Nein' }}<br></li>
          <li>Reiten? {{ row.item.darfReiten ? 'Ja' : 'Nein' }}<br></li>
          <li>Schwimmen? {{ row.item.darfSchwimmen ? 'Ja' : 'Nein' }}<br></li>
          <li>Schwimmabzeichen: {{ row.item.schwimmAbzeichen }}<br></li>
        </ul>
      </template>
      <template v-slot:cell(gesundheit)="row">
        <ul>
          <li>Allergien: {{ row.item.allergien | formatArray }}<br></li>
          <li>Krankheiten: {{ row.item.krankheiten | formatArray }}<br></li>
          <li>Medikamente: {{ row.item.medikamente | formatArray }}<br></li>
          <li>Hitzeempfindlich? {{ row.item.hitzeempfindlich ? 'Ja' : 'Nein' }}<br></li>
          <li>Vegetarier? {{ row.item.vegetarier ? 'Ja' : 'Nein' }}<br></li>
          <li>Laktose-Unverträgl.? {{ row.item.laktoseUnvertraeglichkeit ? 'Ja' : 'Nein' }}<br></li>
          <li>Eier-Unverträgl.? {{ row.item.eierUnvertraeglichkeit ? 'Ja' : 'Nein' }}<br></li>
          <li>Ernährung: {{ row.item.essenWeitereLimitierungen | formatArray }}<br></li>
        </ul>
      </template>
      <template v-slot:cell(behinderung)="row">
        <ul>
          <li>Behinderung: {{ row.item.liegtBehinderungVor ? 'Ja' : 'Nein' }}<br></li>
          <li>Begleitperson notwendig: {{ row.item.behinderung.begleitungNotwendig ? 'Ja' : 'Nein' }}<br></li>
        </ul>
      </template>
    </b-table>
  </div>
</template>

<script>
import JsPDF from 'jspdf';
import dayjs, { SHORT_DATE_FORMAT } from '../modules/dayjs';
import 'jspdf-autotable';

export default {
  name: 'UserListForExport',
  filters: {
    formatArray(array) {
      if (!array) return '';
      const splittedArray = array.split('|');
      if (Array.isArray(splittedArray)) {
        return splittedArray.join('; ');
      }
      return splittedArray;
    },
  },
  props: {
    users: {
      type: Array,
      required: true,
    },
    projectName: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      fields: [
        { key: 'name', label: 'Name', sortable: false },
        { key: 'alter', label: 'Alter', sortable: false },
        { key: 'notfall', label: 'Notfall Kontakt', sortable: false },
        { key: 'zustimmung', label: 'Zustimmung', sortable: false },
        { key: 'gesundheit', label: 'Einschränkungen', sortable: false },
        { key: 'behinderung', label: 'Behinderung', sortable: false },
      ],
    };
  },
  methods: {
    onButtonClick() {
      const pfdDocument = new JsPDF('l');
      pfdDocument.autoTable({
        html: '#user-list-export-table', rowPageBreak: 'auto', includeHiddenHtml: false,
      });
      // TODO safe file with project name as filename (sanitize before)
      pfdDocument.save('table.pdf');
    },
    formatDate(stringDate) {
      return dayjs(stringDate).format(SHORT_DATE_FORMAT);
    },
    calculateAgeFromBirthday(birthday) {
      const today = dayjs();
      return today.diff(dayjs(birthday), 'year');
    },
  },
};
</script>

<style>
/*IMPORTANT: We cannot use scoped css here, because JsPDF-autotable cannot handle this correctly!*/
.user-list-export >>> .sr-only {
  display: none;
}
</style>
