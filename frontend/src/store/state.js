const storestate = {
  user: {
    name: null,
    loggedIn: false,
  }
};

const storegetters = {
  isLoggedIn: state => state.user.loggedIn
};

export { storestate as state, storegetters as getters };
