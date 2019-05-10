import axios from 'axios'

export const AXIOS = axios.create({
  baseURL: `/api`
})

// Anmeldung to Admin API

export function registerTeilnehmer(userAsJson) {
  console.log("register new User from Anmeldung");
  return AXIOS.post('/register', userAsJson).then().catch(e => this.errors.push(e));
}

// Project API

export function getProjects() {
  return AXIOS.get('/allprojects').then(response => response.data).catch(e => this.errors.push(e));
}

export function getProject(id) {
  return AXIOS.get('/project/' + id).then(response => response.data).catch(e => this.errors.push(e));
}

export function createProject(project) {
  console.log("creating new project");
  return AXIOS.post('/projekt', project).then().catch(e => this.errors.push(e));
}

export function updateProject(project) {
  console.log("updating existing project");
  return AXIOS.put('/projekt', project).then().catch(e => this.errors.push(e));
}

export function deleteProject(projectId) {
  console.log("Deleting project with id " + projectId);
  return AXIOS.delete('/projekt/' + projectId).then().catch(e => this.errors.push(e));
}

export function getAllUsersAssignedToProject(projectId) {
  console.log("Fetch users that are assigned to project with id " + projectId)
  return AXIOS.get('/projekt/' + projectId + '/users').then(response => response.data).catch(e => this.errors.push(e));
}

// User API

export function getUser(userId) {
  console.log("Fetch data of user with id " + userId)
  return AXIOS.get('/user/' + userId).then(response => response.data).catch(e => this.errors.push(e));
}

export function getUsers() {
  return AXIOS.get('/allusers').then(response => response.data).catch(e => this.errors.push(e));
}

export function getUsersProjects(userId) {
  console.log("Fetch projects of user with id " + userId)
  return AXIOS.get('/user/' + userId + '/projekte').then(response => response.data).catch(e => this.errors.push(e));
}

export function updateUser(user) {
  console.log("updating existing user")
  return AXIOS.put('/user', user).then().catch(e => this.errors.push(e));
}

export function deleteUser(userId) {
  console.log("Deleting existing user")
  return AXIOS.delete('/user/'+ userId).then();
}

export function deleteUserFromProject(projectId, userId) {
  console.log("Deleting user with id " + userId + " from project with id " + projectId)
  return AXIOS.delete('projekt/' + projectId + '/user/' + userId).then().catch(e => this.errors.push(e));
}

export function addUserToProject(projectId, userId) {
  console.log("Adding user with id " + userId + " to project with id " + projectId)
  return AXIOS.put('projekt/' + projectId + '/user/' + userId).then().catch(e => this.errors.push(e));
}