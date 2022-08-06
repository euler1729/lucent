import {defineStore} from 'pinia'

export const userStore = defineStore('userStore',{

    state:()=>({
        form:{
            name:"",
            email:"",
            phone:"",
            password:""
        }
    }),
    persist:{
        enabled:true
    }
    
});