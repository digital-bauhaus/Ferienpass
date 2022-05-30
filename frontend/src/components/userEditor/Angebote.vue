<template>
  <div>
    <p>
      <b>Hinweis:</b>
      Grau hinterlegte Veranstaltungen sind ausgebucht oder können aufgrund der Altersbeschränkung
      nicht gewählt werden.<br>
      Doppelt oder mehrfach angebotene Projekte (z.B. Klettern oder Angeln) dürfen pro Kind nur
      einmal gebucht werden.<br>
      <br>
      Mein Kind möchte an folgenden Veranstaltungen teilnehmen:
    </p>
    <slot />
    <p>
      <b>Hinweis:</b>
      Die Bestätigung des Platzes erfolgt bei der Anmeldung entsprechend der zur Verfügung
      stehenden Kapazitäten für die Angebote. Sollte ein Angebot seitens der Veranstalter aus
      unvorhergesehenen Gründen abgesagt werden, besteht kein Anspruch auf ein Ersatzangebot.
      Der gezahlte Beitrag für dieses Angebot wird Ihnen komplett zurück erstattet.
    </p>
  </div>
</template>

<script>
export default {
  name: 'Angebote',
};
</script>

<style scoped>

</style>
