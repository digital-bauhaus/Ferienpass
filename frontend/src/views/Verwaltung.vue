<template>
  <div>
    <NavigationMenu />
    <main>
      <div class="front">
        <div class="title">
          {{ title }}
        </div>
        <hr>
        <div class="subtitle">
          {{ subtitle }}
        </div>
      </div>
    </main>
  </div>
</template>


<script>

import NavigationMenu from '../components/NavigationMenu';

export default {
  name: 'Verwaltung',
  components: { NavigationMenu },
  data() {
    return {
      title: 'Ferienpass Weimar',
      subtitle: 'Administration',
    };
  },
};
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.front {
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  margin: auto;
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  width: 480px;
  height: 480px;
  text-align: center;
  font-weight: bold;
}


.front .title {
  font-size: 50px;
}

.front hr {
  border-top: 2px solid #2c3e50;
  width: 60%;
}

.front .subtitle {
  color: #707a8c;
  margin-top: 15px;
  font-size: 30px;

}

</style>
