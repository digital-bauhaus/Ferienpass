import axios from 'axios/index';
import store from '../store';

export const AXIOS = axios.create({
  baseURL: '/api',
});

export default {

  // Login API
  login(user, password) {
    return AXIOS.get('/login', {
      auth: {
        username: user,
        password,
      },
    });
  },

  // Anmeldung to Admin API

  registerTeilnehmer(userAsJson) {
    console.log('register new User from Anmeldung');
    return AXIOS.post('/register', userAsJson);
  },

  // Project API

  getProjects() {
    return AXIOS.get('/projects').then((response) => response.data);
  },
  getProject(id) {
    return AXIOS.get(`/projects/${id}`).then((response) => response.data);
  },
  createProject(project) {
    console.log('creating new project');
    return AXIOS.post('/projects', project).then().catch((e) => {
      if (e.response.data.errors) {
        // validation errors
        return Promise.reject(
          e.response.data.errors.map((error) => `${error.field}: ${error.defaultMessage}`),
        );
      }
      // other errors
      return Promise.reject([e.toString()]);
    });
  },
  updateProject(project) {
    console.log('updating existing project');
    return AXIOS.put('/projects', project).then().catch((e) => {
      if (e.response.data.errors) {
        // validation errors
        return Promise.reject(
          e.response.data.errors.map((error) => `${error.field}: ${error.defaultMessage}`),
        );
      }
      // other errors
      return Promise.reject([e.toString()]);
    });
  },
  deleteProject(projectId) {
    console.log(`Deleting project with id ${projectId}`);
    return AXIOS.delete(`/projects/${projectId}`);
  },
  getAllUsersAssignedToProject(projectId) {
    console.log(`Fetch users that are assigned to project with id ${projectId}`);
    return AXIOS.get(`/projects/${projectId}/users`).then((response) => response.data);
  },

  // User API

  getUser(userId) {
    console.log(`Fetch data of user with id ${userId}`);
    return AXIOS.get(`/users/${userId}`).then((response) => response.data);
  },
  getUsers() {
    console.log('Get all users from backend');
    return AXIOS
      .get('/users',
        {
          auth: {
            username: store.getters.userName,
            password: store.getters.userPass,
          },
        })
      .then((response) => response.data);
  },
  getUsersProjects(userId) {
    console.log(`Fetch projects of user with id ${userId}`);
    return AXIOS
      .get(`/users/${userId}/projects`,
        {
          auth: {
            username: store.getters.userName,
            password: store.getters.userPass,
          },
        })
      .then((response) => response.data);
  },
  getUsersCancelledProjects(userId) {
    console.log(`Fetch cancelled projects of user with id ${userId}`);
    return AXIOS
      .get(`/users/${userId}/cancelledprojects`,
        {
          auth: {
            username: store.getters.userName,
            password: store.getters.userPass,
          },
        })
      .then((response) => response.data);
  },
  updateUser(user) {
    console.log('updating existing user');
    return AXIOS
      .put('/users', user,
        {
          auth: {
            username: store.getters.userName,
            password: store.getters.userPass,
          },
        })
      .then().catch((e) => {
        if (e.response.data.errors) {
        // validation errors
          return Promise.reject(
            e.response.data.errors.map((error) => `${error.field}: ${error.defaultMessage}`),
          );
        }
        // other errors
        return Promise.reject([e.toString()]);
      });
  },
  deleteUser(userId) {
    console.log('Deleting existing user');
    return AXIOS.delete(`/users/${userId}`,
      {
        auth: {
          username: store.getters.userName,
          password: store.getters.userPass,
        },
      });
  },
  deleteUserFromProject(projectId, userId) {
    console.log(`Deleting user with id ${userId} from project with id ${projectId}`);
    return AXIOS.delete(`projects/${projectId}/users/${userId}`);
  },
  addUserToProject(projectId, userId) {
    console.log(`Adding user with id ${userId} to project with id ${projectId}`);
    return AXIOS.put(`projects/${projectId}/users/${userId}`);
  },
};
