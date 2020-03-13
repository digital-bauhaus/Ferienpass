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
          <ErrorBox
            v-if="showErrorAlert"
            heading-text="Login nicht möglich. Folgende Fehler sind aufgetreten: "
            :error-messages="serverErrors"
          />

          <b-form
            ref="form"
            @submit.prevent="onSubmit"
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
import ErrorBox from '@/components/ErrorBox.vue';

export default {
  name: 'Login',
  components: { ErrorBox },
  data() {
    return {
      title: 'Ferienpass Weimar: Administration',
      serverErrors: [],
      form: {
        name: '',
        password: '',
      },
    };
  },
  computed: {
    showErrorAlert() {
      return this.serverErrors.length > 0;
    },
  },
  methods: {
    onSubmit() {
      this.serverErrors = [];
      this.$store.dispatch(LOGIN, { name: this.form.name, password: this.form.password })
        .then(() => this.$router.push('/Verwaltung'))
        .catch((e) => {
          this.serverErrors.push(e.toString());
        });
    },
  },
};
</script>

<style scoped>

</style>
