import Vue from 'vue'
import App from './App.vue'
import router from './router'
import VueSwal from 'vue-swal';
import { toIdentifier } from './mixins/to-identifier';
import * as formComponents from './components/registrationForm';

for (const component in formComponents) {
  Vue.component(formComponents[component].name, formComponents[component]);
}

Vue.config.productionTip = false

Vue.mixin(toIdentifier);

Vue.use(VueSwal);

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
