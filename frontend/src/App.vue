<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>

/*
 * Border-Box for all elements
 */
*,
*::before,
*::after {
  box-sizing: border-box;
}

/*
* Resets
*/
body,
h1,
h2,
h3,
h4,
h5,
h6,
ul,
ol,
dl {
  margin: 0;
  padding: 0;
}

fieldset {
  margin: 0;
  padding: 0;
  border: 0;
}

legend {
  padding: 0;
  border: 0;
}

button,
input,
textarea,
select {
  -webkit-appearance: none;
  -moz-appearance: none;
  overflow: visible;
  color: inherit;
  font: inherit;
  border: 0;
  margin: 0;
  padding: 0;
}

/*
 * base layout (originally intended for registration)
 */
html {
  font-size: 1.25rem;
  line-height: 1.5;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell,
  'Open Sans', 'Helvetica Neue', sans-serif;
  overflow-y: scroll;
}

h1,
h2,
h3,
h4,
h5,
h6,
p {
  margin-top: 1.5rem;
  margin-bottom: 1.5rem;
}

h3,
h4,
h5,
h6 {
  font-weight: normal;
}

ul,
ol {
  margin-bottom: 1.5rem;
  margin-left: 1.5rem;
}

/**
styles for the registration-components (but global!)
 */
button:disabled,
input:disabled,
select:disabled,
option:disabled {
  color: #888;
  background-color: #eee;
}

button::placeholder,
input::placeholder {
  color: #444;
  opacity: 1;
}

textarea {
  display: block;
  width: 100%;
  resize: vertical;
}

select:-moz-focusring {
  /* border: 0; */
  color: transparent;
  text-shadow: 0 0 0 #000;
}

button::-moz-focus-inner,
[type='button']::-moz-focus-inner,
[type='submit']::-moz-focus-inner {
  border-style: none;
  padding: 0;
}

:focus {
  outline: 2px solid orange;
}

[tabindex='-1']:focus {
  outline: none;
}

.button {
  padding: 4px;
  border: 2px solid;
  border-radius: 8px;
  background-color: #fff;
}

.button:focus {
  outline: none;
  box-shadow: 0 0 0 2px cornflowerblue;
}

.button--wide {
  padding-right: 48px;
  padding-left: 48px;
}

.button--inline {
  width: 32px;
  height: 32px;
  line-height: 1;
}

.delete-button:hover {
  background-color: tomato;
  border-color: #222;
}

/*
general utility classes
*/
.visually-hidden {
  position: absolute !important;
  clip: rect(1px, 1px, 1px, 1px);
  padding: 0 !important;
  border: 0 !important;
  height: 1px !important;
  width: 1px !important;
  overflow: hidden;
}

.nobr {
  white-space: nowrap
}

</style>

<style scoped>

</style>
