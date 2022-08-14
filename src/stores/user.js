import { defineStore } from "pinia";
import { useRouter } from "vue-router";

const router = useRouter();

export const useUserStore = defineStore({
  id: "user",
  state: () => ({
    loggedIn: false,
    id: 0,
    name: "",
    phone: "",
    access_token: "",
    refresh_token: "",
    verified: true,
    role: "",

    managerRole: "ROLE_MANAGER",
    donorRole: "ROLE_DONOR",
    adminRole: "ROLE_ADMIN",
  }),
  getters: {},
  actions: {
    setToken(new_access_token, new_refresh_token) {
      this.loggedIn = true;
      this.access_token = new_access_token;
      this.refresh_token = new_refresh_token;

      localStorage.setItem("refresh_token", new_refresh_token);
    },

    setUser(userInf) {
      this.id = userInf.id;
      this.name = userInf.name;
      this.phone = userInf.phone;
      this.verified = userInf.verified;
      this.role = userInf.role.name;
    },

    logout() {
      this.loggedIn = false;
      this.name = "";
      this.phone = "";
      this.access_token = "";
      this.refresh_token = "";
      this.verified = true;
      this.role = "";
      localStorage.removeItem("refresh_token");
      console.log("Loading");
      location.reload();
    },
  },
});
