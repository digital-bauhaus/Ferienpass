// actually a project created with vue-cli already adds all needed polyfills for
// your target browser (via browserslist.rc) by usage
// however: it can be tricky if your dependencies need polyfills too
// in this case we follow the official bootstrap documentation
// see: https://cli.vuejs.org/guide/browser-compatibility.html#usebuiltins-usage
// see: https://bootstrap-vue.js.org/docs/#browser-support

import 'core-js/stable';
import 'regenerator-runtime/runtime';
// import 'intersection-observer' // Optional

// Polyfill for reportValidity for IE
// see: https://www.tjvantoll.com/2015/01/28/reportvalidity/
if (!HTMLFormElement.prototype.reportValidity) {
  HTMLFormElement.prototype.reportValidity = () => this.checkValidity() || this.submit();
}
