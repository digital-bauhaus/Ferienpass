import {
  LOGIN_SUCCESS,
  LOGIN_FAILED
} from "./mutation-types";

import {
  LOGIN,
} from "./action-types";

const actions = {
  async [LOGIN]({ commit }, { name, password }) {
    if (name === "test" && password === "test") {
      // TODO actually log in into application here!
      return commit(LOGIN_SUCCESS, { name });
    } else {
      commit(LOGIN_FAILED);
      return Promise.reject("Invalid credentials. Hint: Use 'test' for both...")
    }
  }
};

export default actions;
