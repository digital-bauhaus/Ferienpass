<template>
  <b-container class="login vh-100">
    <b-row
      class="h-100"
      align-v="center"
    >
      <b-col>
        <b-card
          :title="title"
          bg-variant="light"
        >
          <ErrorAlert
            v-if="showErrorAlert"
            heading-text="Login nicht möglich. Folgende Fehler sind aufgetreten: "
            :errors="serverErrorMessages"
          />

          <b-form
            ref="form"
            @submit="onSubmit"
          >
            <b-form-group
              id="login-name-group"
              horizontal
              label-for="login-name-value"
              :label-cols="4"
              label="Name"
            >
              <b-form-input
                id="login-name-value"
                v-model.trim="form.name"
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
                v-model.trim="form.password"
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
      </b-col>
    </b-row>
  </b-container>
</template>


<script>
import { LOGIN } from '@/store/action-types';
import ErrorAlert from '@/components/ErrorAlert.vue';

export default {
  name: 'Login',
  components: { ErrorAlert },
  data() {
    return {
      title: 'Ferienpass Weimar: Administration',
      serverErrorMessages: [],
      form: {
        name: '',
        password: '',
      },
    };
  },
  computed: {
    showErrorAlert() {
      return this.serverErrorMessages.length > 0;
    },
  },
  methods: {
    onSubmit() {
      this.serverErrorMessages = [];
      this.$store.dispatch(LOGIN, { name: this.form.name, password: this.form.password })
        .then(() => this.$router.push('/Verwaltung'))
        .catch((e) => {
          this.serverErrorMessages.push(e);
        });
    },
  },
};
</script>

<style scoped>

</style>
