import { defineStore } from "pinia";


export const uiStore = defineStore('uiStore', {
    state: () => ({
        dark: true,
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
            this.isLoginPageOpen = !this.isLoginPageOpen;
        },
        showRegistrationPage(){
            this.isLoginPageOpen = false;
            this.isOrgRegPageOpen = false;
            this.isRegisterPageOpen = !this.isRegisterPageOpen;
        },
        showOrgRegPage(){
            this.isLoginPageOpen = false;
            this.isRegisterPageOpen = false;
            this.isOrgRegPageOpen = !this.isOrgRegPageOpen;
        }
    },
    // persist:{
    //     enabled:true
    // }
});
