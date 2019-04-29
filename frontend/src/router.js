import Vue from 'vue'
import Router from 'vue-router'
import Registration from '@/components/Registration';
import Verwaltung from '@/components/Verwaltung'
import Veranstaltungen from '@/components/Veranstaltungen'
import Login from '@/components/Login'
import Teilnehmer from '@/components/Teilnehmer'
import VeranstaltungEdit from '@/components/VeranstaltungEdit'
import TeilnehmerEdit from '@/components/TeilnehmerEdit'

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
