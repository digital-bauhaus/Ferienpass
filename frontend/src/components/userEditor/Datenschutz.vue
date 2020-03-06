<template>
  <div>
    <h2>Datenschutzerklärung</h2>
    <ol class="paragraph-list">
      <li class="paragraph-list__item">
        Mit Ihrer Anmeldung nimmt das Kinderbüro Weimar die oben genannten Daten und Kontakte auf.
        Diese Informationen werden im EDV-System des Kinderbüros gespeichert.
        Personenbezogene Daten werden dabei durch geeignete technische und organisatorische
        Maßnahmen vor der Kenntnis Dritter geschützt. Sonstige Informationen zu den Kindern
        werden vom Kinderbüro nur verarbeitet oder genutzt, wenn sie zur Umsetzung der Ziele
        des Ferienpasses nützlich sind und keine Anhaltspunkte bestehen, dass die betroffene
        Person ein schutzwürdiges Interesse hat, das der Verarbeitung oder Nutzung entgegensteht.
      </li>
      <li class="paragraph-list__item">
        Nur das Kinderbüro und Personen, die zur Durchführung der Ferienpass-Angebote Kenntnis
        über bestimmte Informationen haben müssen, haben Zugang zu den Daten.
      </li>
      <li class="paragraph-list__item">
        Bei Abmeldung des Kindes werden die in §1 genannten Daten aus dem Verzeichnis gelöscht.
        Alle personenbezogenen Daten werden nach Ablauf eines Jahres gelöscht.
      </li>
      <li class="paragraph-list__item">
        <span>
          Es gilt die allgemeine Datenschutzerklärung des Ferienpasses zu finden unter: <a
            href="http://www.ferienpass-weimar.de/?content=datenschutz"
            target="_blank"
            rel="noopener noreferrer"
          >http://www.ferienpass-weimar.de/?content=datenschutz</a>
        </span>
      </li>
    </ol>
  </div>
</template>

<script>
export default {
  name: 'Datenschutz',
};
</script>

<style scoped>
.paragraph-list {
  margin-left: 0;
  list-style-type: none;
  counter-reset: paragraph-list;
}

.paragraph-list__item {
  counter-increment: paragraph-list;
  display: flex;
}

.paragraph-list__item::before {
  content: '§' counter(paragraph-list);
  min-width: 2em;
  text-align: right;
  margin-right: 0.75rem;
}

.paragraph-list__item:not(:last-child) {
  margin-bottom: 0.75rem;
}
</style>
