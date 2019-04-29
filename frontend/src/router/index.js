import Vue from 'vue'
import Router from 'vue-router'
import Registration from '@/components/Registration';
import Test from '@/components/Test'
import Verwaltung from '@/components/Verwaltung'
import Login from '@/components/Login'
import Teilnehmer from '@/components/Teilnehmer'
import Reservierung from '@/components/Reservierung'
import Veranstaltung from '@/components/Veranstaltung'
import VeranstaltungEdit from '@/components/VeranstaltungEdit'
import TeilnehmerEdit from '@/components/TeilnehmerEdit'
import TeilnehmerAdd from '@/components/TeilnehmerAdd'

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
      path: '/Reservierung',
      name: 'Reservierung',
      component: Reservierung
    },
    {
      path: '/Teilnehmer',
      name: 'Teilnehmer',
      component: Teilnehmer
    },
    {
      path: '/Veranstaltung',
      name: 'Veranstaltung',
      component: Veranstaltung
    },

    {
      path: '/Test',
      name: 'Test',
      component: Test
    },
    {
      path: '/Verwaltung',
      name: 'Verwaltung',
      component: Verwaltung
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
    },
    {
      path: '/TeilnehmerAdd',
      name: 'TeilnehmerAdd',
      component: TeilnehmerAdd
    }
  ]
})
