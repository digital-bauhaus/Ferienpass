import Vue from 'vue';
import './modules/polyfills';
import './modules/bootstrap-vue';
import VueSwal from 'vue-swal';
import router from './router';
import store from './store';
import App from './App.vue';
import './design/design.scss';

Vue.config.productionTip = false;

Vue.use(VueSwal);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
