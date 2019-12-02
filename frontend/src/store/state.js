const storestate = {
  user: {
    name: null,
    pass: null,
    loggedIn: false,
  },
};

const storegetters = {
  isLoggedIn: (state) => state.user.loggedIn,
  userName: (state) => state.user.name,
  userPass: (state) => state.user.pass,
};

export { storestate as state, storegetters as getters };
