<template>
  <div class="getPartner">
    <h3>Partner ausgeben</h3>
    <div class="holder">
      <form @submit.prevent="getPartner">
        <input type="number" placeholder="Enter a id for the partner..." v-model="partnerId" name="partnerId">
      </form>
    </div>

    <!-- Falls der Partner gefunden wird -->
    <div class="partnerfound" v-if="Object.keys(partner).length > 0">
    <table class="table table-striped">
      <tr>
        <td>Id</td>
        <td>Vorname</td>
        <td>Nachname</td>
        <td>Geschlecht</td>
      </tr>
      <tr>
        <td>{{this.partner.Id}}</td>
        <td>{{this.partner.firstName}}</td>
        <td>{{this.partner.lastName}}</td>
        <td>{{this.partner.gender}}</td>
      </tr>
     </table>
    </div>

    <!-- Falls ein Fehler entsteht -->
    <div class="errormessage" v-if="Object.keys(this.errors).length > 0">
      <ul>
        <li v-for="error in errors">
          {{error.message}}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      partnerId: "",
      partner: [],
      errors: []
    };
  },
  methods: {
    getPartner() {
      var config = {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json"
        }
      };
      axios
        .get("http://localhost:8080/partners/" + this.partnerId, config)
        .then(response => {
          this.partner = response.data;
          this.errors = [];
        })
        .catch(e => {
          this.partner = "";
          this.errors.push(e);
        });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style src="./Project.css" scoped></style>
