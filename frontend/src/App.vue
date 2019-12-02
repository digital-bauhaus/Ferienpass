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
  Anmeldung
  */

  html {
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell,
    'Open Sans', 'Helvetica Neue', sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
  }

  main {
    width: 80%;
    position: absolute;
    right: 0px;
    height: 100%;
  }

  main form {
    margin: auto;
    top: 40px; left: 0; bottom: 0; right: 0;
    width: 90%;
  }

  main form .caption {
    font-size: 30px;
    font-weight: bold;
  }

  main form input[type=text]{
    font-size: 16px;
    width: 200;
    height: 30px;
    margin-bottom: 0px;
    display: block;
    background-color: #FAEBD7;
    border-radius:5px;
    -moz-border-radius:5px;
    -webkit-border-radius:5px;
  }


  main form label {
    margin: 5px;
    float: left;
    display: block;
  }

  main h2 {
    margin: 20px;
  }

  main h1 {
    margin: 20px;
  }


  fieldset label {
    position: relative;
    display:block;
    font-size: 15px;
    margin: 25px;
  }

  input[type=checkbox]{
    height: 15px;
    width: 20px;
    vertical-align: bottom;
    margin:5px;
  }

  input[type=submit] {
    margin: 20px;
    background-color: #84845e;
    border: none;
    color: white;
    padding: 10px 24px;
    border-radius: 8px;
    text-align: center;
    text-decoration: none;
    font-size: 16px;
  }


  .fadeIn {
    text-align: center;
    vertical-align: middle;
    line-height: 90px;
    color: white;
    display: block;
    background-color: #6bbc6b;
    position: fixed;
    bottom: 20px;
    right: -10px;
    overflow-x: hidden;
    transition: .5s;
    width: 200px;
    height: 100px;
    border-radius: 10px;
  }

  .fadeOut {
    text-align: center;
    vertical-align: middle;
    line-height: 90px;
    color: #6bbc6b;
    display: box;
    background-color: #6bbc6b;
    position: fixed;
    bottom: 20px;
    right: -10px;
    overflow-x: hidden;
    transition: .5s;
    width: 0px;
    height: 100px;
  }

  th, td {
    min-width: 150px;
    padding: 10px 20px;
  }

  /*
  Test.vue
  */

  .front {
    -webkit-touch-callout: none;
    -webkit-user-select: none;
    -khtml-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    margin: auto;
    position: absolute;
    top: 0; left: 0; bottom: 0; right: 0;
    width: 480px;
    height: 480px;
    text-align: center;
    font-weight: bold;
  }


  .front .title {
    font-size: 50px;
  }

  .front hr {
    border-top: 2px solid #2c3e50;
    width: 60%;
  }

  .front .subtitle {
    color: #707a8c;
    margin-top: 15px;
    font-size: 30px;

  }

  /*
  Verwaltung.vue
  */

  table {
    border-collapse: collapse;
    margin-top: 20px;
    margin-left: auto;
    margin-right: auto;
    width: 80%;
    border-radius: 15px;
    border: 0px;
  }

  th {
    background:  #333435;
    color: white;
    font-weight: bold;
  }

  .clickable {
    cursor: pointer;
  }

  .clickable:after {
    font-weight: normal;
    position: relative;
    left: 5px;
    content: '▼';
  }

  td, th {
    padding: 6px;
    border: 1px solid #ccc;
    text-align: left;
    border: 0px;
  }

  tr:nth-child(even) {
    background: #eee;
  }

  .fakebutton {
    color: black;
    background: lightgrey;
    border-radius: 7px;
    padding: 4px;
    cursor: pointer;
  }

  .fakebutton:hover {
    text-decoration: none;
    background: #3a4372;
    color: white;
    transition: .10s;
  }




  /*
   * Admin
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

  /*
  base.form-elements.css
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
  trumps.visually-hidden.css
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

  .nobr { white-space: nowrap }

</style>

<style scoped>
  .page-content {
    width: 80%;
    max-width: 1000px;
    margin: 3rem auto;
  }
</style>
