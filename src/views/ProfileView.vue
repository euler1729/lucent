<template>
  <DefaultLayout>
    <div class="flex flex-col items-center text-xl font-body font-semibold">
      <div class="grid md:w-3/4 grid-cols-1 mx-10 my-10">
        <!-- Name -->
        <div>
          <label
            for="name"
            class="block mb-2 text-xl font-body font-semibold text-gray-900 dark:text-gray-300"
            >{{ labels.name[inf.lang] }}
          </label>
          <input
            type="text"
            name="name"
            id="name"
            v-model="name"
            class="bg-white border-2 shadow-lg border-darkblue text-gray-900 text-xl font-body font-semibold rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white px-6"
            disabled
          />
        </div>
        <!-- phone -->
        <div class="mt-6">
          <label
            for="name"
            class="block mb-2 text-xl font-body font-semibold text-gray-900 dark:text-gray-300"
            >{{ labels.phone[inf.lang] }}
          </label>
          <input
            disabled
            name="phone"
            id="phone"
            v-model="phone"
            class="bg-white border-2 shadow-lg border-darkblue text-gray-900 text-xl font-body font-semibold rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white px-6"
          />
        </div>

        <!-- Verified -->
        <div class="mt-6">
          <SwitchGroup>
            <div class="w-full flex flex-row items-center justify-between">
              <SwitchLabel class="mr-4">{{
                labels.verified[inf.lang]
              }}</SwitchLabel>
              <Switch
                v-model="verified"
                :class="verified ? 'bg-darkblue' : 'bg-darkblue'"
                class="relative inline-flex h-6 w-11 items-center rounded-full transition-colors focus:outline-none focus:ring-2 focus:ring-darkblue focus:ring-offset-2"
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
        <Btn class="mt-6" @click="logout">{{ labels.logout[inf.lang] }}</Btn>
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
import { useInf } from "../stores/inf";

const inf = useInf();
const user = useUserStore();
const router = useRouter();

const name = ref(user.name);
const phone = ref(user.phone);
const verified = user.verified;

const labels = ref({
  name: {
    bn: "নাম ",
    en: "Name",
  },
  phone: {
    bn: "ফোন ",
    en: "Phone",
  },
  verified: {
    bn: "ভেরিফাইড ",
    en: "Verified",
  },
  logout: {
    bn: "লগ আউট ",
    en: "Log out",
  },
});

const logoutBtlLabel = ref("Log out");

onMounted(() => {
  if (!user.loggedIn) router.push({ name: "home" });
});

function logout() {
  user.logout();
  router.push({ name: "home" });
}
</script>
