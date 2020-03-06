<template>
  <div class="user-list">
    <b-button @click="onButtonClick" class="mb-3">
      PDF Erzeugen
    </b-button>
    <b-table
      id="user-table"
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
        {{ row.item.vorname }} {{ row.item.nachname }}
      </template>
      <template v-slot:cell(notfall)="row">
        <ul>
          <li> Name: {{ row.item.notfallKontakt.name }} </li>
          <li> Tel: {{ row.item.notfallKontakt.telefon }} </li>
        </ul>
      </template>
      <template v-slot:cell(zustimmung)="row">
        <ul>
          <li> Behandlungserlaubnis? {{ row.item.darfBehandeltWerden ? 'Ja' : 'Nein' }} </li>
          <li> Allein heimgehen? {{ row.item.darfAlleinNachHause ? 'Ja' : 'Nein' }} </li>
          <li> Reiten? {{ row.item.darfReiten ? 'Ja' : 'Nein' }} </li>
          <li> Schwimmen? {{ row.item.darfSchwimmen ? 'Ja' : 'Nein' }} </li>
          <li> Schwimmabzeichen? {{ row.item.schwimmAbzeichen ? 'Ja' : 'Nein' }} </li>
        </ul>
      </template>
      <template v-slot:cell(gesundheit)="row">
        <ul>
          <li> Allergien: {{ row.item.allergien }} </li>
          <li> Krankheiten: {{ row.item.krankheiten }} </li>
          <li> Medikamente: {{ row.item.krankheiten }} </li>
          <li> Hitzeempfindlich? {{ row.item.hitzeempfindlichkeiten ? 'Ja' : 'Nein' }} </li>
          <li> Vegetarier? {{ row.item.vegetarier ? 'Ja' : 'Nein' }} </li>
          <li> Laktose-Unverträgl.? {{ row.item.laktoseUnvertraeglichkeit ? 'Ja' : 'Nein' }} </li>
          <li> Eier-Unverträgl.? {{ row.item.eierUnvertraeglichkeit ? 'Ja' : 'Nein' }} </li>
          <li> Ernährung: {{ row.item.essenLimitierungen }} </li>
        </ul>
      </template>
      <template v-slot:cell(behinderung)="row">
        <ul>
          <li> Behinderung: {{ row.item.liegtBehinderungVor ? 'Ja' : 'Nein' }} </li>
          <li> Begleitperson notwendig: {{ row.item.behinderung.begleitungNotwendig ? 'Ja' : 'Nein' }} </li>
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
  props: {
    users: {
      type: Array,
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
      pfdDocument.autoTable({ html: '#user-table', rowPageBreak: 'auto', includeHiddenHtml: false });
      pfdDocument.save('table.pdf');
    },
    formatDate(stringDate) {
      return dayjs(stringDate).format(SHORT_DATE_FORMAT);
    },
  },
};
</script>

<style scoped>
.user-list >>> .sr-only {
  display: none;
}
</style>
