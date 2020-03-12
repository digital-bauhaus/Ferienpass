import axios from 'axios/index';

export const AXIOS = axios.create({
  baseURL: '/api/public',
  headers: {
    // disable browser popup (https://stackoverflow.com/questions/37763186/spring-boot-security-shows-http-basic-auth-popup-after-failed-login)
    common: {
      'X-Requested-With': 'XMLHttpRequest',
    },
  },
});

export default {

  // Registration API

  registerUser(user) {
    return AXIOS.post('/register', user);
  },

  // Projects API

  getProjects() {
    return AXIOS.get('/projects').then((response) => response.data);
  },

};
