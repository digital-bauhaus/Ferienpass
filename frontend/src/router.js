import Vue from 'vue'
import Router from 'vue-router'
import Registration from "./views/Registration";
import Login from "./views/Login";
import Teilnehmer from "./views/Teilnehmer";
import Verwaltung from "./views/Verwaltung";
import Veranstaltungen from "./views/Veranstaltungen";
import VeranstaltungEdit from "./views/VeranstaltungEdit";
import TeilnehmerEdit from "./views/TeilnehmerEdit";

Vue.use(Router)

export default new Router({
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
      component: Teilnehmer
    },
    {
      path: '/Verwaltung',
      name: 'Verwaltung',
      component: Verwaltung
    },
    {
      path: '/Veranstaltungen',
      name: 'Veranstaltungen',
      component: Veranstaltungen
    },
    {
      path: '/VeranstaltungEdit',
      name: 'VeranstaltungEdit',
      component: VeranstaltungEdit
    },
    {
      path: '/TeilnehmerEdit',
      name: 'TeilnehmerEdit',
      component: TeilnehmerEdit
    }
  ]
})
