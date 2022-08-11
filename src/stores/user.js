import { defineStore } from "pinia";

export const useUserStore = defineStore({
  id: "user",
  state: () => ({
    loggedIn: false,
    name: "",
    phone: "",
    access_token: "",
    refresh_token: "",
    verified: true,
    role: "",

    managerRole: "ROLE_MANAGER",
    donorRule: "ROLE_DONOR",
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
      this.name = userInf.name;
      this.phone = userInf.phone;
      this.verified = userInf.verified;
      this.role = userInf.role.name;
    },

    logout() {
      this.loggedIn = false;
    },
  },
});
