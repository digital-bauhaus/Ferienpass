import {
  LOGIN_SUCCESS,
  LOGIN_FAILED
} from "./mutation-types";

import {
  LOGIN,
} from "./action-types";

import api from '../modules/ferienpass-api';

const actions = {
  [LOGIN]({ commit }, { name, password }) {
    return new Promise((resolve, reject) => {
      console.log("Accessing backend with user: '" + name + ' and password ' + password);
      api.login(name, password)
          .then(response => {
              console.log("Response: '" + response.data + "' with Statuscode " + response.status);
              if(response.status == 200) {
                console.log("Login successful");
                commit(LOGIN_SUCCESS, {
                  name: name,
                  password: password
                })
              }
              resolve(response)
            })
          .catch(error => {
              console.log("Fehler: " + error)
              commit(LOGIN_FAILED);
              reject("Fehlerhafte Anmeldedaten (User und/oder Passwort)!");
          })
    })
  }
};

export default actions;
