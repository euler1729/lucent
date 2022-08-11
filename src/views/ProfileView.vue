<template>
  <DefaultLayout>
    <div class="flex flex-col items-center">
      <div class="grid md:w-3/4 grid-cols-1 mx-10 my-10">
        <!-- Name -->
        <div>
          <label
            for="name"
            class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
            >Name
          </label>
          <input
            type="text"
            name="name"
            id="name"
            v-model="name"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white px-6"
          />
        </div>
        <!-- phone -->
        <div class="mt-6">
          <label
            for="name"
            class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
            >Phone
          </label>
          <input
            disabled
            name="phone"
            id="phone"
            v-model="phone"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white px-6"
          />
        </div>

        <!-- Verified -->
        <div class="mt-6">
          <SwitchGroup>
            <div class="w-full flex flex-row items-center justify-between">
              <SwitchLabel class="mr-4">Verified</SwitchLabel>
              <Switch
                v-model="verified"
                :class="verified ? 'bg-blue-600' : 'bg-gray-200'"
                class="relative inline-flex h-6 w-11 items-center rounded-full transition-colors focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
              >
                <span
                  :class="verified ? 'translate-x-6' : 'translate-x-1'"
                  class="inline-block h-4 w-4 transform rounded-full bg-white transition-transform"
                />
              </Switch>
            </div>
          </SwitchGroup>
        </div>
      </div>
      <div>
        <Btn class="mt-6" @click="logout">{{ logoutBtlLabel }}</Btn>
      </div>
    </div>
  </DefaultLayout>
</template>

<script setup>
import DefaultLayout from "../layout/Default.vue";
import Btn from "../components/Btn.vue";
import { onMounted, ref } from "vue";
import { useUserStore } from "../stores/user.js";
import { useRouter } from "vue-router";
import { Switch, SwitchGroup, SwitchLabel } from "@headlessui/vue";
const user = useUserStore();
const router = useRouter();

const name = ref(user.name);
const phone = ref(user.phone);
const verified = user.verified;

const logoutBtlLabel = ref("Log out");

onMounted(() => {
  if (!user.loggedIn) router.push({ name: "home" });
});

function logout() {
  user.logout();
  router.push({ name: "home" });
}
</script>
