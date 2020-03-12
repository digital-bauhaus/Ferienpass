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
// see: https://gist.github.com/nuxodin/73a2c2423cbbf6818c28ad803985d5c7
if (!HTMLFormElement.prototype.reportValidity) {
  HTMLFormElement.prototype.reportValidity = function reportValidityPoly() {
    if (this.checkValidity()) return true;
    const btn = document.createElement('button');
    this.appendChild(btn);
    btn.click();
    this.removeChild(btn);
    return false;
  };
}

if (!HTMLInputElement.prototype.reportValidity) {
  HTMLInputElement.prototype.reportValidity = function reportValidityPoly() {
    if (this.checkValidity()) return true;
    let tmpForm;
    if (!this.form) {
      tmpForm = document.createElement('form');
      tmpForm.style.display = 'inline';
      this.before(tmpForm);
      tmpForm.append(this);
    }
    const siblings = Array.from(this.form.elements).filter(function doFilter(input) {
      return input !== this && !!input.checkValidity && !input.disabled;
    }, this);
    siblings.forEach((input) => {
      // eslint-disable-next-line no-param-reassign
      input.disabled = true;
    });
    this.form.reportValidity();
    siblings.forEach((input) => {
      // eslint-disable-next-line no-param-reassign
      input.disabled = false;
    });
    if (tmpForm) {
      tmpForm.before(this);
      tmpForm.remove();
    }
    this.focus();
    this.selectionStart = 0;
    return false;
  };
}

// Note: There are other polyfills/fixes that need to be applied after mounting the app in App.vue
