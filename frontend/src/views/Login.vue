<template>
  <b-container class="vh-100">
    <b-row
      class="vh-100"
      align-v="center"
    >
      <b-col>
        <b-card bg-variant="light">
          <b-form @submit="onSubmit">
            <b-form-group
              id="login-name-group"
              horizontal
              label-for="login-name-value"
              :label-cols="4"
              label="Name"
            >
              <b-form-input
                id="login-name-value"
                v-model.trim="name"
                required
                placeholder="Bitte Name eingeben"
              />
            </b-form-group>
            <b-form-group
              id="login-password-group"
              label-for="login-password-value"
              horizontal
              :label-cols="4"
              label="Passwort"
            >
              <b-form-input
                id="login-password"
                v-model.trim="password"
                type="password"
                required
                placeholder="Bitte Passwort eingeben"
              />
            </b-form-group>
            <b-button
              type="submit"
              variant="primary"
            >
              Einloggen
            </b-button>
          </b-form>
        </b-card>
        <ErrorListBox
          v-if="errors.length"
          heading-text="Login nicht möglich. Folgende Fehler sind aufgetreten: "
          :errors="errors"
        />
      </b-col>
    </b-row>
  </b-container>
</template>


<script>
import { LOGIN } from '@/store/action-types';
import ErrorListBox from '@/components/ErrorListBox.vue';

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
    onSubmit() {
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
</style>
