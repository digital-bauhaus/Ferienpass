import Vue from 'vue'
import Router from 'vue-router'
import Registration from "./views/Registration";
import Login from "./views/Login";
import Teilnehmer from "./views/Teilnehmer";
import Verwaltung from "./views/Verwaltung";
import Veranstaltungen from "./views/Veranstaltungen";
import VeranstaltungEdit from "./views/VeranstaltungEdit";
import TeilnehmerEdit from "./views/TeilnehmerEdit";

import store from './store'

Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Anmeldung',
      component: Registration
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/Teilnehmer',
      name: 'Teilnehmer',
      component: Teilnehmer,
      meta: { requiresAuth: true }
    },
    {
      path: '/Verwaltung',
      name: 'Verwaltung',
      component: Verwaltung,
      meta: { requiresAuth: true }
    },
    {
      path: '/Veranstaltungen',
      name: 'Veranstaltungen',
      component: Veranstaltungen,
      meta: { requiresAuth: true }
    },
    {
      path: '/VeranstaltungEdit',
      name: 'VeranstaltungEdit',
      component: VeranstaltungEdit,
      meta: { requiresAuth: true }
    },
    {
      path: '/TeilnehmerEdit',
      name: 'TeilnehmerEdit',
      component: TeilnehmerEdit,
      meta: { requiresAuth: true }
    }
  ]
});

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!store.getters.isLoggedIn) {
      next({
        path: '/login'
      })
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
