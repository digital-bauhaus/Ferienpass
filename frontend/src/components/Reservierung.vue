<template>
  <div>
    <h2>{{title}}</h2>
    <br/>
    <form v-if="formDataLoaded" class="form">
      <br/>
      <table>

        <tr>
          <th></th>
          <th>Anzahl der Plätze</th>
          <th>Reservieren</th>
        </tr>
        <tr v-for="entry in formData.sections[1].components[0].params.components">
          <td><b>{{ entry.params.label }}</b></td>
          <td><input type="text" placeholder="Anzahl"></td>
          <td><button>reservieren</button></td>
        </tr>
      </table>
    </form>

    <footer>
      <a href="/#/Test/" >Zurück zur Übersicht</a>
    </footer>

  </div>
</template>

<script>

export default {
  name: 'Reservierung',

  data () {
    return {
      title: 'Reservierung',
      formDataLoaded: false,
      formData: null
    };
  },
  created () {
    this.fetchData();
  },
  methods: {
    fetchData () {
      fetch('/static/form-data.json')
        .then(response => {
          return response.json();
        })
        .then(json => {
          this.formData = json;
          this.formDataLoaded = true;
        });
    }
  }
}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 100%;
}

td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}

tr:nth-child(even) {
    background-color: #dddddd;
}
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
footer {
    clear: both;
    background: White;
    padding: 0;
    text-align: center;
    vertical-align: middle;
    line-height: normal;
    margin: 0;
    position: fixed;
    bottom: 0px;
    width: 100%;
}
</style>
