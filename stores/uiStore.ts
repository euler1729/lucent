import { defineStore } from "pinia";


export const uiStore = defineStore('uiStore', {
    state: () => ({
        dark: false,
        isLoggedIn:false,
        isLoginPageOpen:false,
        isRegisterPageOpen:false,
        isOrgRegPageOpen:false,
    }),
    actions: {
        switch() {
            this.dark = !this.dark;
            if (this.dark) {
                localStorage.theme = 'dark';
            }
            else {
                localStorage.theme = 'light';
            }
            if (localStorage.theme === 'dark' || (!('theme' in localStorage) && window.matchMedia('(prefers-color-scheme: dark)').matches)) {
                document.documentElement.classList.add('dark')
            } else {
                document.documentElement.classList.remove('dark')
            }
        },
        logOut(){
            this.isLoggedIn = false;
            
        },
        logIn(){
            this.isLoggedIn = true;
        },
        showLogin(){
            this.isRegisterPageOpen = false;
            this.isOrgRegPageOpen = false;
            this.isLoginPageOpen = true;
        },
        showRegistrationPage(){
            this.isLoginPageOpen = false;
            this.isOrgRegPageOpen = false;
            this.isRegisterPageOpen = true;
        },
        showOrgRegPage(){
            this.isLoginPageOpen = false;
            this.isRegisterPageOpen = false;
            this.isOrgRegPageOpen = true;
        }
    },
    persist:{
        enabled:true
    }
});
