// actually a project created with vue-cli already adds all needed polyfills for
// your target browser (via browserslist.rc) by usage
// however: it can be tricky if your dependencies need polyfills too
// in this case we follow the official bootstrap documentation
// see: https://cli.vuejs.org/guide/browser-compatibility.html#usebuiltins-usage
// see: https://bootstrap-vue.js.org/docs/#browser-support

import 'core-js/stable';
import 'regenerator-runtime/runtime';
// import 'intersection-observer' // Optional
