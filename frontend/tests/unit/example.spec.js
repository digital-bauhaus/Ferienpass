import { shallowMount } from '@vue/test-utils';
import Verwaltung from '@/views/admin/Verwaltung.vue';

describe('Verwaltung.vue', () => {
  it('renders props.msg when passed', () => {
    const msg = 'Ferienpass Weimar';
    const wrapper = shallowMount(Verwaltung, {
      propsData: { msg },
    });
    expect(wrapper.text()).toMatch(msg);
  });
});
