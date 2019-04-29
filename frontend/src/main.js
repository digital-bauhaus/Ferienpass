// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import VueSwal from 'vue-swal';
import { toIdentifier } from './mixins/to-identifier';
import * as formComponents from './components/form';

for (const component in formComponents) {
  Vue.component(formComponents[component].name, formComponents[component]);
}

Vue.config.productionTip = false

Vue.mixin(toIdentifier);

Vue.use(VueSwal);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
