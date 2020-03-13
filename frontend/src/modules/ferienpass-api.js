import axios from 'axios/index';

const AXIOS = axios.create({
  baseURL: '/api',
  headers: {
    // disable browser popup (https://stackoverflow.com/questions/37763186/spring-boot-security-shows-http-basic-auth-popup-after-failed-login)
    common: {
      'X-Requested-With': 'XMLHttpRequest',
    },
  },
});

const auth = {
  username: '',
  password: '',
};

export default {

  setAuthentication(username, password) {
    auth.username = username;
    auth.password = password;
  },

  // Login API
  // --------------------------------------

  login(username, password) {
    return AXIOS.get('/login', {
      auth: {
        username,
        password,
      },
    });
  },

  // Project API
  // --------------------------------------

  getProjects() {
    return AXIOS.get('/projects', { auth }).then((response) => response.data);
  },
  getProjectById(id) {
    return AXIOS.get(`/projects/${id}`, { auth }).then((response) => response.data);
  },
  createProject(project) {
    return AXIOS.post('/projects', project, { auth });
  },
  updateProject(project) {
    return AXIOS.put('/projects', project, { auth });
  },
  deleteProject(projectId) {
    return AXIOS.delete(`/projects/${projectId}`, { auth });
  },
  assignUserToProject(projectId, userId) {
    return AXIOS.put(`projects/${projectId}/users/${userId}`, { auth });
  },
  unassignUserFromProject(projectId, userId) {
    return AXIOS.delete(`projects/${projectId}/users/${userId}`, { auth });
  },
  getRegisteredUsersOfProject(projectId) {
    return AXIOS
      .get(`/projects/${projectId}/users`, { auth })
      .then((response) => response.data);
  },
  getCancelledUsersOfProject(projectId) {
    return AXIOS
      .get(`/projects/${projectId}/cancelledusers`, { auth })
      .then((response) => response.data);
  },

  // User API
  // --------------------------------------

  getUserById(userId) {
    return AXIOS.get(`/users/${userId}`, { auth }).then((response) => response.data);
  },
  getUsers() {
    return AXIOS
      .get('/users', { auth })
      .then((response) => response.data);
  },
  updateUser(user) {
    return AXIOS
      .put('/users', user, { auth });
  },
  deleteUser(userId) {
    return AXIOS.delete(`/users/${userId}`, { auth });
  },
  getRegisteredProjectsOfUser(userId) {
    return AXIOS
      .get(`/users/${userId}/projects`, { auth })
      .then((response) => response.data);
  },
  getCancelledProjectsOfUser(userId) {
    return AXIOS
      .get(`/users/${userId}/cancelledprojects`, { auth })
      .then((response) => response.data);
  },

};
