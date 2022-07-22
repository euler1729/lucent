import { createRouter, createWebHistory } from "vue-router";
import ActivitiesView from "@/views/Activities.vue";
import ExploreView from "@/views/Explore.vue";
import HomePageView from "@/views/HomePage.vue";
import ProfileView from "@/views/Profile.vue";
import SubscriptionsView from "@/views/Subscriptions.vue";
import Register from "@/views/Register.vue";
import Login from "@/views/Login.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:'/',
      name:'Home',
      component:HomePageView
    },
    {
      path: '/explore',
      name: 'Explore',
      component: ExploreView
    },
    {
      path: '/activities',
      name: 'Activities',
      component: ActivitiesView
    },
    {
      path: '/profile',
      name: 'Profile',
      component: ProfileView
    },
    {
      path: '/subscriptions',
      name: 'Subscriptions',
      component: SubscriptionsView
    },
    {
      path:'/register',
      name:'Register',
      component:Register
    },
    {
      path:'/login',
      name:'Login',
      component: Login
    }
  ],
});

export default router;
