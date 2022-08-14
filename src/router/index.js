import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/profile",
      name: "profile",
      component: () => import("../views/ProfileView.vue"),
    },

    {
      path: "/orgsettings",
      name: "orgsettings",
      component: () => import("../views/OrganizationSettingsView.vue"),
    },
    {
      path: "/org/:id",
      name: "organization",
      component: () => import("../views/OrganizationDetailView.vue"),
    },
    {
      path: "/donations/:orgId",
      name: "donations",
      component: () => import("../views/DonationsView.vue"),
    },
    {
      path: "/spendings/:orgId",
      name: "spendings",
      component: () => import("../views/SpendingsView.vue"),
    },
    {
      path: "/membership-requests/:orgId",
      name: "membership-request",
      component: () => import("../views/MembeshipRequests.vue"),
    },
    {
      path: "/admin",
      name: "adminDashboard",
      component: () => import("../views/AdminDashboard.vue"),
    },
    {
      path: "/orgreg",
      name: "orgreg",
      component: () => import("../views/OrganizationRegistrationView.vue"),
    },
  ],
});

export default router;
