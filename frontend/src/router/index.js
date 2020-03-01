import Vue from 'vue';
import VueRouter from 'vue-router';
import Registration from '@/views/Registration.vue';
import Login from '@/views/Login.vue';
import Teilnehmer from '@/views/Teilnehmer.vue';
import Verwaltung from '@/views/Verwaltung.vue';
import Veranstaltungen from '@/views/Veranstaltungen.vue';
import VeranstaltungEdit from '@/views/VeranstaltungEdit.vue';
import TeilnehmerEdit from '@/views/TeilnehmerEdit.vue';
import VeranstaltungEditNew from '@/views/VeranstaltungEditNew.vue';
import VeranstaltungenNew from '@/views/VeranstaltungenNew.vue';

import store from '@/store';

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
    path: '/VeranstaltungenNew',
    component: VeranstaltungenNew,
    meta: { requiresAuth: true },
  },
  {
    path: '/VeranstaltungEdit',
    component: VeranstaltungEdit,
    meta: { requiresAuth: true },
  },
  {
    path: '/VeranstaltungEditNew',
    component: VeranstaltungEditNew,
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
