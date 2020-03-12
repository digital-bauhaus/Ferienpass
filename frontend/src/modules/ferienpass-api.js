import axios from 'axios/index';

export const AXIOS = axios.create({
  baseURL: '/api',
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

  login(user, password) {
    return AXIOS.get('/login', {
      auth: {
        username: user,
        password,
      },
    });
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
          auth,
        })
      .then((response) => response.data);
  },
  getUsersProjects(userId) {
    console.log(`Fetch projects of user with id ${userId}`);
    return AXIOS
      .get(`/users/${userId}/projects`,
        {
          auth,
        })
      .then((response) => response.data);
  },
  getUsersCancelledProjects(userId) {
    console.log(`Fetch cancelled projects of user with id ${userId}`);
    return AXIOS
      .get(`/users/${userId}/cancelledprojects`,
        {
          auth,
        })
      .then((response) => response.data);
  },
  addUser(user) {
    console.log('adding user');
    return AXIOS
      .post('/users', user,
        {
          auth,
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
  updateUser(user) {
    console.log('updating existing user');
    return AXIOS
      .put('/users', user,
        {
          auth,
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
        auth,
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
