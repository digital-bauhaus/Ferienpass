import axios from 'axios/index';

export const AXIOS = axios.create({
  baseURL: '/api/public',
});

export default {

  // Registration API

  registerUser(user) {
    console.log('called public api: registerUser');
    return AXIOS.post('/register', user);
  },

  // Projects API

  getProjects() {
    console.log('called public api: getProjects');
    return AXIOS.get('/projects').then((response) => response.data);
  },

};
