<template>
  <ParagraphList>
    <ParagraphListItem>
      Hiermit bestätige ich die verbindliche Anmeldung meines Kindes bei allen gewählten
      Veranstaltungen des Ferienpasses. Den Teilnehmerbeitrag bezahle ich nach der
      Zahlungsaufforderung.
    </ParagraphListItem>
    <ParagraphListItem>
      Bei allen Angeboten sind die Vorgaben bzw. Anweisungen des Betreuungspersonals
      einzuhalten (insbesondere im Zusammenhang mit gesetzlichen Regelungen, wie z.B.
      Aufsichtspflicht oder Jugendschutzgesetz, …). Bei der Veranstaltung gilt das
      Jugendschutzgesetz sowie ein striktes Rauch- und Alkoholverbot. Bei entsprechenden
      Verstößen behalten wir uns vor, Teilnehmende ggf. vorzeitig auszuschließen und sie
      nach Hause zu schicken bzw. abholen zu lassen. In diesen Fällen versuchen wir, Sie
      rechtzeitig darüber in Kenntnis zu setzen.
    </ParagraphListItem>
    <ParagraphListItem>
      Für etwaige Schäden (z.B. auf dem Hin- oder Rückweg) übernehmen wir keine Haftung.
    </ParagraphListItem>
    <ParagraphListItem>
      Wir übernehmen keine Haftung für verlorene Gegenstände. Bitte geben Sie Ihrem Kind
      keine Wertgegenstände, elektronisches Spielzeug oder Ähnliches mit.
    </ParagraphListItem>
    <ParagraphListItem>
      Bitte informieren Sie Ihr Kind über diese Inhalte, damit die Veranstaltung für
      alle Beteiligten möglichst konfliktfrei verläuft und dadurch in guter Erinnerung bleibt.
    </ParagraphListItem>
    <ParagraphListItem>
      Hiermit gebe ich mein Einverständnis, dass mein Kind zum Zwecke der Veröffentlichung und
      der Dokumentation im Rahmen des Ferienpasses gefilmt und fotografiert werden darf. Die
      Fotos dürfen auf der Internetseite des Ferienpasses und im Ferienpass-Heft verwendet werden.
    </ParagraphListItem>
    <ParagraphListItem>
      <span>
        <b>Stornierungsregelung</b>: Nach der Bezahlung entstehen Stornierungskosten. Bei
        Stornierung einer Aktion bis einer Woche vor der Veranstaltung werden 50% der Kosten
        berechnet. Ab einer Woche vor der Veranstaltung werden 100% berechnet. Die
        Stornierungskosten können umgangen werden, wenn ein anderes Kind für den freien
        Platz benannt wird. Nehmen Sie für die Stornierung Kontakt mit uns auf:
        <a href="mailto:ferienpass@stadtweimar.de">ferienpass@stadtweimar.de</a>
      </span>
    </ParagraphListItem>
  </ParagraphList>
</template>

<script>
import ParagraphList from '@/components/wrapper/ParagraphList.vue';
import ParagraphListItem from '@/components/wrapper/ParagraphListItem.vue';

export default {
  name: 'Teilnahmebedingungen',
  components: { ParagraphListItem, ParagraphList },
};
</script>

<style scoped>

</style>
