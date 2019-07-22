<template>
  <div id="app">
     <nav>
        <router-link to="/">Home</router-link>
        <router-link to="/getPartner">Partner ausgeben</router-link>
        <router-link to="/addPartner">Partner hinzufügen</router-link>
      </nav>
    <router-view/>
  </div>
</template>

<script>
export default {
}
</script>
/
<style>
body {
  background-color: #EEEEEE;
  font-family: 'Montserrat', sans-serif;
  display: grid;
  grid-template-rows: auto;
  justify-items: center;
  padding-top: 50px;
}

nav {
  padding: 20px 20px 20px 0;
}

nav a {
  padding: 10px;
  text-decoration: none;
  background: #fff;
  border-radius: 3px;
  color: rgb(0, 110, 255);
  font-weight: bold;
  margin-right: 15px;
}
</style>
