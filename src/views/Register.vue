<template>
  <div v-if="ui.isRegisterPageOpen || ui.isOrgRegPageOpen"
    class="relative  mt-20 flex justify-center items-center bg-texture bg-cover">

    <div class="rounded-lg shadow-lg  max-w-sm opacity-80 bg-white dark:bg-gray-700 text-gray-700 dark:text-white">
      <div class=" flex-1 ">
        <div>

          <div class="px-10 p-4 rounded-3xl shadow-xl">
            <div v-if="ui.isRegisterPageOpen" class="mx-auto text-center p-6">
              <h3 class="text-2xl text-gray-800 dark:text-white">Register as Donor</h3>
              <span class="inline-flex text-xs text-blue-400">
                <button @click="ui.showOrgRegPage()">
                  <h5>Register as Organization</h5>
                </button>
              </span>
            </div>
            <div v-if="ui.isOrgRegPageOpen" class="mx-auto text-center p-6">
              <h3 class="text-xl text-gray-800 dark:text-white">Register as Organization</h3>
              <span class="inline-flex text-xs text-blue-400">
                <button @click="ui.showRegistrationPage()">
                  <h5>Register as Donor</h5>
                </button>
              </span>
            </div>

            <form @submit.prevent="handleSubmit">

              <div class="relative" ret>
                <input id="name" name="name" type="text" v-model="form.name" class="peer w-full px-0.5 border-0 rounded-lg border-b-2 dark:text-gray-700 
                  border-gray-300 placeholder-transparent focus:ring-0 focus:border-purple-600" placeholder="Name"
                  required>
                <label for="name" class="absolute left-1 -top-5 
                  text-gray-700 dark:text-white text-sm 
                  transition-all peer-placeholder-shown:text-base 
                  peer-placeholder-shown:text-gray-400 
                  peer-placeholder-shown:top-0
                  peer-focus:-top-3.5
                  peer-focus:text-purple-600 peer-focus:text-sm">
                  User Name
                </label>
              </div>

              <div v-if="ui.isOrgRegPageOpen" class="relative mt-10" ret>
                <input id="orgName" name="orgName" type="text" v-model="form.orgName" class="peer w-full px-0.5 border-0 rounded-lg border-b-2 dark:text-gray-700 
                  border-gray-300 placeholder-transparent focus:ring-0 focus:border-purple-600" placeholder="Name"
                  required>
                <label for="name" class="absolute left-1 -top-5 
                  text-gray-700 dark:text-white text-sm 
                  transition-all peer-placeholder-shown:text-base 
                  peer-placeholder-shown:text-gray-400 
                  peer-placeholder-shown:top-0
                  peer-focus:-top-3.5
                  peer-focus:text-purple-600 peer-focus:text-sm">
                  Organization Name
                </label>
              </div>

              <div class="mt-10 relative">
                <input id="phone" name="phone" type="text" v-model="form.phone" required
                  class="peer w-full px-0.5 dark:text-gray-700 border-0 rounded-lg border-b-2 border-gray-300 placeholder-transparent focus:ring-0 focus:border-purple-600"
                  placeholder="willPig@tailwind.com">
                <label for="phone" class="absolute left-1 -top-5 
                  text-gray-700 dark:text-white text-sm transition-all 
                  peer-placeholder-shown:text-base 
                  peer-placeholder-shown:text-gray-400 
                  peer-placeholder-shown:top-0
                  peer-focus:-top-3.5 peer-focus:text-purple-600 
                  peer-focus:text-sm">Phone Number</label>
              </div>

              <div v-if="ui.isOrgRegPageOpen" class="mt-10 relative">
                <textarea id="description" name="description" type="text" v-model.lazy="form.orgDescription" class="peer w-full px-0.5 border-0 rounded-lg border-b-2 dark:text-gray-700 
                  border-gray-300 placeholder-transparent focus:ring-0 focus:border-purple-600"
                  placeholder="Description"></textarea>
                <label for="name" class="absolute left-1 -top-5 
                  text-gray-700 dark:text-white text-sm 
                  transition-all peer-placeholder-shown:text-base 
                  peer-placeholder-shown:text-gray-400 
                  peer-placeholder-shown:top-0
                  peer-focus:-top-3.5
                  peer-focus:text-purple-600 peer-focus:text-sm">
                  Description
                </label>
              </div>

              <div class="mt-10 relative">
                <input id="password" type="password" name="password" v-model="form.password" class="peer w-full dark:text-gray-700 px-0.5 
                  border-0 border-b-2 border-gray-300 
                  placeholder-transparent focus:ring-0 rounded-lg
                  focus:border-purple-600" placeholder="Password" />
                <label for="password" class="absolute left-1  -top-5 text-gray-700 dark:text-white 
                  text-sm transition-all
                  peer-placeholder-shown:text-base 
                  peer-placeholder-shown:text-gray-400 
                  peer-placeholder-shown:top-0 peer-focus:-top-3 
                  peer-focus:text-purple-600 peer-focus:text-sm">
                  Password
                </label>
              </div>

              <div class="mt-10 relative">
                <input id="repassword" type="password" name="repassword" v-model="form.repassword" class="peer w-full dark:text-gray-700 px-1 
                  border-0 border-b-2 rounded-lg
                  border-gray-300 placeholder-transparent 
                  focus:ring-0 focus:border-purple-600" placeholder="Retype Password" />
                <label for="repassword" class="absolute left-1 -top-5 text-gray-700 
                  dark:text-white text-sm transition-all 
                  peer-placeholder-shown:text-base 
                  peer-placeholder-shown:text-gray-400 
                  peer-placeholder-shown:top-0 peer-focus:-top-3.5 
                  peer-focus:text-purple-600 peer-focus:text-sm">
                  Retype Password
                </label>
              </div>

              <div v-if="false" class="mt-10">
                <label class="inline-flex items-center">
                  <input type="checkbox"
                    class="rounded border-gray-300 text-purple-600 focus:border-purple-300 focus:ring focus:ring-offset-0 focus:ring-purple-200/50"
                    checked />
                  <span class="ml-2 text-sm dark:text-white">Check here that you have agree to <a href="#"
                      class="font-semibold text-purple-600 hover:underline">the terms.</a></span>
                </label>
              </div><br />

              <button type="submit" class="w-full
                      px-6
                      py-2.5
                      bg-blue-600
                      text-white
                      font-medium
                      text-xs
                      leading-tight
                      uppercase
                      rounded
                      shadow-md
                      hover:bg-blue-700 hover:shadow-lg
                      focus:bg-blue-700 focus:shadow-lg focus:outline-none focus:ring-0
                      active:bg-blue-800 active:shadow-lg
                      transition
                      duration-150
                      ease-in-out"> Sign up
              </button>
              <p class="text-center text-sm text-gray-400 mt-4">
                Have an account ?
                <router-link to="/login" @click="ui.showLogin()" class="font-semibold text-blue-600 hover:underline">Log
                  in</router-link>
              </p>
            </form>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { uiStore } from "../../stores/uiStore";
import { userStore } from "../../stores/userStore";
import axios from 'axios';
// import VueAxios from 'vue-axios';

export default defineComponent({
  setup() {
    const ui = uiStore();
    const user = userStore();
    const form = {
      name: "",
      email: "",
      phone: "",
      password: "",
      repassword: "",
      orgName: "",
      orgDescription: "",
    }
    return { ui, user, form };
  },
  computed: {

  },
  methods: {
    async handleSubmit() {
      console.log(this.form);
      if (this.ui.isOrgRegPageOpen) {
        try {
          console.log('Org');
          const response = await axios.post('org/registration', {
            name: this.form.name,
            phone: this.form.phone,
            password: this.form.password,
            orgName: this.form.orgName,
            orgDescription: this.form.orgDescription,
          });
          console.log(response);
        } catch (err) {
          console.log(err);
        }
      }
      else {
        try {
          console.log('Donor');
          const response = await axios.post('/user/registration',{
              name:this.form.name,
              phone:this.form.phone,
              password:this.form.password
          })
          console.log(response.data);
        } catch (err) {
          console.log(err);
        }
      }

    }
  }
});
</script>

<style>
</style>