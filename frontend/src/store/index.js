import Vue from 'vue';
import Vuex from 'vuex';

import { state, getters } from './state';
import actions from './actions';
import mutations from './mutations';

Vue.use(Vuex);

export default new Vuex.Store({
  state,
  getters,
  mutations,
  actions,
});
