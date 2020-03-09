import './modules/polyfills';
import Vue from 'vue';
import './modules/bootstrap-vue';
import VueSwal from 'vue-swal';
import App from './App.vue';
import router from './router';
import toIdentifier from './mixins/to-identifier';
import * as formComponents from './components/registrationForm';
import store from './store';
import './design/design.scss';

Object.values(formComponents).forEach((component) => Vue.component(component.name, component));

Vue.config.productionTip = false;

Vue.mixin(toIdentifier);

Vue.use(VueSwal);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
