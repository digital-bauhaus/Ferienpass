<template>
<html>
    <div id="login">
    <h1>{{title}}</h1>
    <form >
      <input type="text" name="Admin-Name" value="" placeholder="Admin"> <br/>
      <input type="password" name="Passwort" value="" placeholder="Passwort"><br/>
      <button onclick="window.location.href='/#/Verwaltung'">einloggen</button>
    </form>
    </div>
</html>
</template>


<script>

export default {
  name: 'Login',

  data () {
    return {
      title: 'Ferienpass Weimar: Administration'
    }
  }
}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

h1 {
  text-align: center;
}

#login {
  position: absolute;
  width: 450px;
  height: 400px;
  margin: auto;
  padding: 30px;
  top: 0; left: 0; bottom: 0; right: 0;
  border: 2px solid #383754;
  border-radius: 10px;

}

#login form label {
margin: 0px;
  float: left;
  display: block;
}

#login form button {
background-color: #383754;
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: block;
    font-size: 16px;
    margin: auto;
}

#login form input{
  font-size: 20px;
  width: 60%;
  margin: auto;
  height: 30px;
  margin-bottom: 5px;
  display: block;
}

</style>

