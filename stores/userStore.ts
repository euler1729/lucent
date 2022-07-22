import {defineStore} from 'pinia'

export const userStore = defineStore('userStore',{

    state:()=>({
        form:{
            name:null,
            email:null,
            password:null
        }
    }),
    
});