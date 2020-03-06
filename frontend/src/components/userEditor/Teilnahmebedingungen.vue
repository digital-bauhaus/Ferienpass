<template>
  <div>
    <h2>Teilnahmebedingungen</h2>
    <ol class="paragraph-list">
      <li class="paragraph-list__item">
        Hiermit bestätige ich die verbindliche Anmeldung meines Kindes bei allen gewählten
        Veranstaltungen des Ferienpasses. Den Teilnehmerbeitrag bezahle ich nach der
        Zahlungsaufforderung.
      </li>
      <li class="paragraph-list__item">
        Bei allen Angeboten sind die Vorgaben bzw. Anweisungen des Betreuungspersonals
        einzuhalten (insbesondere im Zusammenhang mit gesetzlichen Regelungen, wie z.B.
        Aufsichtspflicht oder Jugendschutzgesetz, …). Bei der Veranstaltung gilt das
        Jugendschutzgesetz sowie ein striktes Rauch- und Alkoholverbot. Bei entsprechenden
        Verstößen behalten wir uns vor, Teilnehmende ggf. vorzeitig auszuschließen und sie
        nach Hause zu schicken bzw. abholen zu lassen. In diesen Fällen versuchen wir, Sie
        rechtzeitig darüber in Kenntnis zu setzen.
      </li>
      <li class="paragraph-list__item">
        Für etwaige Schäden (z.B. auf dem Hin- oder Rückweg) übernehmen wir keine Haftung.
      </li>
      <li class="paragraph-list__item">
        Wir übernehmen keine Haftung für verlorene Gegenstände. Bitte geben Sie Ihrem Kind
        keine Wertgegenstände, elektronisches Spielzeug oder Ähnliches mit.
      </li>
      <li class="paragraph-list__item">
        Bitte informieren Sie Ihr Kind über diese Inhalte, damit die Veranstaltung für
        alle Beteiligten möglichst konfliktfrei verläuft und dadurch in guter Erinnerung bleibt.
      </li>
      <li class="paragraph-list__item">
        Hiermit gebe ich mein Einverständnis, dass mein Kind zum Zwecke der Veröffentlichung und
        der Dokumentation im Rahmen des Ferienpasses gefilmt und fotografiert werden darf. Die
        Fotos dürfen auf der Internetseite des Ferienpasses und im Ferienpass-Heft verwendet werden.
      </li>
      <li class="paragraph-list__item">
        Die Datenschutzerklärung habe ich zur Kenntnis genommen.
      </li>
      <li class="paragraph-list__item">
        <span>
          <b>Stornierungsregelung</b>: Nach der Bezahlung entstehen Stornierungskosten. Bei
          Stornierung einer Aktion bis einer Woche vor der Veranstaltung werden 50% der Kosten
          berechnet. Ab einer Woche vor der Veranstaltung werden 100% berechnet. Die
          Stornierungskosten können umgangen werden, wenn ein anderes Kind für den freien
          Platz benannt wird. Nehmen Sie für die Stornierung Kontakt mit uns auf:
          <a href="mailto:ferienpass@stadtweimar.de">ferienpass@stadtweimar.de</a>
        </span>
      </li>
    </ol>
  </div>
</template>

<script>
export default {
  name: 'Teilnahmebedingungen',
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
