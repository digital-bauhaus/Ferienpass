import Vue from 'vue';
import VueRouter from 'vue-router';
import Registration from '@/views/Registration.vue';
import Login from '@/views/Login.vue';
import Teilnehmer from '@/views/Teilnehmer.vue';
import Verwaltung from '@/views/Verwaltung.vue';
import Projects from '@/views/Projects.vue';
import ProjectsEdit from '@/views/ProjectsEdit.vue';
import UserEdit from '@/views/UserEdit.vue';
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
    path: '/Projects',
    component: Projects,
    meta: { requiresAuth: true },
  },
  {
    path: '/ProjectsEdit',
    component: ProjectsEdit,
    meta: { requiresAuth: true },
  },
  {
    path: '/UserEdit',
    component: UserEdit,
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
