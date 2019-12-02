import Vue from 'vue';
import VueRouter from 'vue-router';
import Registration from '../views/Registration';
import Login from '../views/Login';
import Teilnehmer from '../views/Teilnehmer';
import Verwaltung from '../views/Verwaltung';
import Veranstaltungen from '../views/Veranstaltungen';
import VeranstaltungEdit from '../views/VeranstaltungEdit';
import TeilnehmerEdit from '../views/TeilnehmerEdit';

import store from '../store';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    component: Registration,
  },
  {
    path: '/login',
    component: Login,
  },
  {
    path: '/Teilnehmer',
    component: Teilnehmer,
    meta: { requiresAuth: true },
  },
  {
    path: '/Verwaltung',
    component: Verwaltung,
    meta: { requiresAuth: true },
  },
  {
    path: '/Veranstaltungen',
    component: Veranstaltungen,
    meta: { requiresAuth: true },
  },
  {
    path: '/VeranstaltungEdit',
    component: VeranstaltungEdit,
    meta: { requiresAuth: true },
  },
  {
    path: '/TeilnehmerEdit',
    component: TeilnehmerEdit,
    meta: { requiresAuth: true },
  },

  // otherwise redirect to home
  { path: '*', redirect: '/' },
];

const router = new VueRouter({
  // mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (!store.getters.isLoggedIn) {
      next({
        path: '/login',
      });
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
