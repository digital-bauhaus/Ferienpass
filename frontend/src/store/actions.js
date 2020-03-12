import api from '@/modules/ferienpass-api';

import { LOGIN_FAILED, LOGIN_SUCCESS, } from './mutation-types';

import { LOGIN, } from './action-types';

const actions = {
  [LOGIN]({ commit }, { name, password }) {
    return new Promise((resolve, reject) => {
      console.log('Logging in...');
      api.login(name, password)
        .then((response) => {
          console.log(`Response: '${response.data}' with Statuscode ${response.status}`);
          if (response.status === 200) {
            console.log('Login successful');
            commit(LOGIN_SUCCESS, {
              name,
              password,
            });
            api.setAuthentication(name, password);
          }
          resolve(response);
        })
        .catch((error) => {
          console.log(`Fehler: ${error}`);
          commit(LOGIN_FAILED);
          reject(new Error('Fehlerhafte Anmeldedaten (User und/oder Passwort)!'));
          api.setAuthentication('', '');
        });
    });
  },
};

export default actions;
