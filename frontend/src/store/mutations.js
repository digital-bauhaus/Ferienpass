import {
  LOGIN_FAILED,
  LOGIN_SUCCESS
} from "./mutation-types";

const mutations = {
  [LOGIN_SUCCESS](state, payload ) {
    state.user.name = payload.name;
    state.user.pass = payload.password;
    state.user.loggedIn = true;
  },
  [LOGIN_FAILED](state) {
    state.user.name = "";
    state.user.loggedIn = false;
  },
};

export default mutations;
