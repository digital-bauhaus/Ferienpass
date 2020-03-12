const storestate = {
  user: {
    name: null,
    pass: null,
    loggedIn: false,
  },
};

const storegetters = {
  isLoggedIn: (state) => state.user.loggedIn,
};

export { storestate as state, storegetters as getters };
