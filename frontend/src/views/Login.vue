<template>
  <div id="login">
    <h1>{{ title }}</h1>
    <form>
      <input
        v-model="name"
        type="text"
        name="Admin-Name"
        placeholder="Admin"
      > <br>
      <input
        v-model="password"
        type="password"
        name="Passwort"
        placeholder="Passwort"
      ><br>
      <button
        type="button"
        @click="doLogin"
      >
        einloggen
      </button>
      <ErrorListBox
        v-if="errors.length"
        heading-text="Login nicht möglich. Folgende Fehler sind aufgetreten: "
        :errors="errors"
      />
    </form>
  </div>
</template>


<script>
import { LOGIN } from '../store/action-types';
import ErrorListBox from '../components/ErrorListBox';

export default {
  name: 'Login',
  components: { ErrorListBox },
  data() {
    return {
      title: 'Ferienpass Weimar: Administration',
      errors: [],
      name: '',
      password: '',
    };
  },
  methods: {
    doLogin() {
      this.errors = [];
      this.$store.dispatch(LOGIN, { name: this.name, password: this.password })
        .then(() => this.$router.push('/Verwaltung'))
        .catch((e) => {
          this.errors.push(e);
        });
    },
  },
};
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

h1 {
  text-align: center;
}

#login {
  position: absolute;
  width: 450px;
  height: 400px;
  margin: auto;
  padding: 30px;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  border: 2px solid #383754;
  border-radius: 10px;

}

#login form label {
  margin: 0px;
  float: left;
  display: block;
}

#login form button {
  background-color: #383754;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: block;
  font-size: 16px;
  margin: auto;
}

#login form input {
  font-size: 20px;
  width: 60%;
  margin: auto;
  height: 30px;
  margin-bottom: 5px;
  display: block;
  border: 1px solid #181819;
  border-radius: 5px;
}

</style>
