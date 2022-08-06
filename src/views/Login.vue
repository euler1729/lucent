<template>
  <div v-if="ui.isLoginPageOpen" class="z-0 relative mt-20 flex justify-center items-center bg-texture bg-cover">
    <div
      class=" p-6 rounded-lg shadow-lg  max-w-sm opacity-80 bg-white dark:bg-gray-700 text-gray-700 dark:text-white ">

      <form @submit="handleSubmit">
        <h1 class="flex justify-center mb-2 text-2xl">Login</h1>
        <div class="form-group z-0 ">
          <label class="form-label inline-block mb-2">Phone Number</label>
          <input type="phone" v-model="form.phone" class="form-control
                  block
                  w-full
                  px-3
                  py-1.5
                  text-base
                  font-normal
                  text-gray-700
                  bg-white bg-clip-padding
                  border border-solid border-gray-300
                  rounded
                  transition
                  ease-in-out
                  m-0
                  focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none" id="exampleInputEmail2"
            aria-describedby="emailHelp" placeholder="Phone Number">
        </div>
        <div class="form-group mb-6">
          <label for="exampleInputPassword2"
            class="form-label inline-block mb-2 text-gray-700 dark:text-white">Password</label>
          <input type="password" v-model="form.password" class="form-control block
                        w-full
                        px-3
                        py-1.5
                        text-base
                        font-normal
                        text-gray-700
                        bg-white bg-clip-padding
                        border border-solid border-gray-300
                        rounded
                        transition
                        ease-in-out
                        m-0
                        focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
            id="exampleInputPassword2" placeholder="Password" />
        </div>

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
                ease-in-out">
          Sign in
        </button>
        <p class=" mt-6 text-center bg-white dark:bg-gray-700 text-gray-700 dark:text-white">Don't have an account?
          <router-link to="/register" @click="ui.showRegistrationPage()"
            class="text-blue-600 hover:text-blue-700 focus:text-blue-700  transition duration-200 ease-in-out">Register
          </router-link>
        </p>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { userStore } from "../../stores/userStore";
import { uiStore } from "../../stores/uiStore";
import { useRouter } from "vue-router";
import axios from 'axios';
export default defineComponent({
  setup() {
    const user = userStore();
    const ui = uiStore();
    const router = useRouter();
    const form = {
      name: "",
      phone: "",
      password: ""
    }
    return { user, ui, form , router};
  },
  methods: {
    async handleSubmit(e: { preventDefault: () => void; }) {
      e.preventDefault();
      try {
        await axios.get(`/user/login?phone=${this.form.phone}&password=${this.form.password}`,
          )
          .then(response => {
            axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.access_token}`;
            localStorage.setItem('refresh_token',response.data.refresh_token);
          })
          .then(()=>{
             this.router.push('/');
          })
          .catch((err)=>{
            console.log(err);
          })

      } catch (err) {
        console.log(err);
      }
    }
  }
});
</script>

<style>
</style>