<template>
  <div>
    <p>
      Mein Kind möchte an folgenden Veranstaltungen teilnehmen:
      <br>
      <b>Hinweis:</b>
      Grau hinterlegte Projekte sind ausgebucht oder können aufgrund der Altersbeschränkung nicht
      gewählt werden.
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
