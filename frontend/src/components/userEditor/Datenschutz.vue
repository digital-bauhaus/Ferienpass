<template>
  <ParagraphList>
    <ParagraphListItem>
      Mit Ihrer Anmeldung nimmt das Kinderbüro Weimar die oben genannten Daten und Kontakte auf.
      Diese Informationen werden im EDV-System des Kinderbüros gespeichert.
      Personenbezogene Daten werden dabei durch geeignete technische und organisatorische
      Maßnahmen vor der Kenntnis Dritter geschützt. Sonstige Informationen zu den Kindern
      werden vom Kinderbüro nur verarbeitet oder genutzt, wenn sie zur Umsetzung der Ziele
      des Ferienpasses nützlich sind und keine Anhaltspunkte bestehen, dass die betroffene
      Person ein schutzwürdiges Interesse hat, das der Verarbeitung oder Nutzung entgegensteht.
    </ParagraphListItem>
    <ParagraphListItem>
      Nur das Kinderbüro und Personen, die zur Durchführung der Ferienpass-Angebote Kenntnis
      über bestimmte Informationen haben müssen, haben Zugang zu den Daten.
    </ParagraphListItem>
    <ParagraphListItem>
      Bei Abmeldung des Kindes werden die in §1 genannten Daten aus dem Verzeichnis gelöscht.
      Alle personenbezogenen Daten werden nach Ablauf eines Jahres gelöscht.
    </ParagraphListItem>
    <ParagraphListItem>
      <span>
        Es gilt die allgemeine Datenschutzerklärung des Ferienpasses zu finden unter: <a
          href="http://www.ferienpass-weimar.de/?content=datenschutz"
          target="_blank"
          rel="noopener noreferrer"
        >http://www.ferienpass-weimar.de/?content=datenschutz</a>
      </span>
    </ParagraphListItem>
  </ParagraphList>
</template>

<script>
import ParagraphList from '@/components/wrapper/ParagraphList.vue';
import ParagraphListItem from '@/components/wrapper/ParagraphListItem.vue';

export default {
  name: 'Datenschutz',
  components: { ParagraphListItem, ParagraphList },
};
</script>

<style scoped>

</style>
