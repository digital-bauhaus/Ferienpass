import { shallowMount } from '@vue/test-utils'
import Verwaltung from '@/components/Verwaltung.vue'

describe('Verwaltung.vue', () => {
  it('renders props.msg when passed', () => {
    const msg = 'Alle Veranstaltungen'
    const wrapper = shallowMount(Verwaltung, {
      propsData: { msg }
    })
    expect(wrapper.text()).toContain(msg)
  })
})
