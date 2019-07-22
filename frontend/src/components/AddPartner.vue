<template>
  <div class="addPartner">
    <h3>Partner anlegen</h3>
    <div class="holder">
      <form @submit.prevent="addPartner">
        <input type="text" placeholder="Enter a firstname" v-model="firstname" name="fristname">
        <input type="text" placeholder="Enter a lastname"  v-model="lastname"  name="lastname">
        <label for="male"> Male</label>
        <input id="male" type="radio" name="gender" value="male" v-model="gender">
        <label for="female"> Female</label>
        <input id="female" type="radio" name="gender" value="female" v-model="gender">
        <button type="submit">Submit</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "addPartner",
  data() {
    return {
      lastname: "",
      firstname: "",
      gender: "",
      partnerid: ""
    };
  },
  methods: {
    addPartner() {
      var config = {
        headers: {
          "Content-Type": "application/json",
          "Accept": "application/json"
        }
      }

      var payload = {
        firstName: this.firstname,
        lastName: this.lastname,
        gender: this.gender
      }

      axios.post("http://localhost:8080/partners/", payload, config)
      .then(response => {
        console.log(response.data)
      }).catch(e => {
        console.log(e)
      })
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style src="./Project.css" scoped>
</style>
