import axios from 'axios/index';

export const AXIOS = axios.create({
  baseURL: '/api/public',
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
