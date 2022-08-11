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
            disabled
            type="text"
            name="name"
            id="name"
            v-model="orgInfo.name"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white px-6"
          />
        </div>
        <!-- description -->
        <div class="mt-6">
          <label
            for="name"
            class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
            >Description
          </label>
          <textarea
            rows="3"
            type="textarea"
            name="description"
            id="description"
            v-model="orgInfo.description"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white px-6"
          />
        </div>
        <!-- description -->
        <div class="mt-6">
          <SwitchGroup>
            <div class="w-full flex flex-row items-center justify-between">
              <SwitchLabel class="mr-4">Auto Approve</SwitchLabel>
              <Switch
                v-model="orgInfo.autoApprove"
                :class="orgInfo.autoApprove ? 'bg-blue-600' : 'bg-gray-200'"
                class="relative inline-flex h-6 w-11 items-center rounded-full transition-colors focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
              >
                <span
                  :class="
                    orgInfo.autoApprove ? 'translate-x-6' : 'translate-x-1'
                  "
                  class="inline-block h-4 w-4 transform rounded-full bg-white transition-transform"
                />
              </Switch>
            </div>
          </SwitchGroup>
        </div>
        <div class="mt-6">
          <SwitchGroup>
            <div class="flex flex-row items-center justify-between">
              <SwitchLabel class="mr-4">Require Membership Code</SwitchLabel>
              <Switch
                v-model="orgInfo.requireCode"
                :class="orgInfo.requireCode ? 'bg-blue-600' : 'bg-gray-200'"
                class="relative inline-flex h-6 w-11 items-center rounded-full transition-colors focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
              >
                <span
                  :class="
                    orgInfo.requireCode ? 'translate-x-6' : 'translate-x-1'
                  "
                  class="inline-block h-4 w-4 transform rounded-full bg-white transition-transform"
                />
              </Switch>
            </div>
          </SwitchGroup>
        </div>
        <div class="mt-6">
          <SwitchGroup>
            <div class="flex flex-row items-center justify-between">
              <SwitchLabel class="mr-4">Require NID</SwitchLabel>
              <Switch
                v-model="orgInfo.requireNID"
                :class="orgInfo.requireNID ? 'bg-blue-600' : 'bg-gray-200'"
                class="relative inline-flex h-6 w-11 items-center rounded-full transition-colors focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
              >
                <span
                  :class="
                    orgInfo.requireNID ? 'translate-x-6' : 'translate-x-1'
                  "
                  class="inline-block h-4 w-4 transform rounded-full bg-white transition-transform"
                />
              </Switch>
            </div>
          </SwitchGroup>
        </div>
      </div>
      <div>
        <Btn class="mt-6" @click="update">{{ updateBtnLabel }}</Btn>
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
import api from "../api";

const user = useUserStore();
const router = useRouter();

const orgInfo = ref({});
const autoApprove = ref(false);

const updateBtnLabel = ref("Update");

onMounted(() => {
  if (!user.loggedIn) router.push({ name: "home" });
  else if (user.role !== user.managerRole) router.push({ name: "home" });
  else {
    getOrg(0);
  }
});

function getOrg(counter) {
  if (counter < 2) {
    updateBtnLabel.value = "Loading";
    api
      .get("/org/find", {
        headers: {
          AUTHORIZATION: `Bearer ${user.access_token}`,
        },
      })
      .then((response) => {
        orgInfo.value = response.data;
        updateBtnLabel.value = "Update";
      })
      .catch((err) => {
        if (err.response.status == 403) {
          // refresh token
          api
            .get("/token/refresh", {
              headers: {
                AUTHORIZATION: `Bearer ${user.refresh_token}`,
              },
            })
            .then((refreshResponse) => {
              user.setToken(
                refreshResponse.data.access_token,
                refreshResponse.data.refresh_token
              );
              getOrg(counter + 1);
            })
            .catch((err) => {
              user.logout();
              updateBtnLabel.value = "Update";
            });
        }
      });
  }
}

function update() {
  updateBtnLabel.value = "Updating";

  api
    .put(
      `/org/update/${orgInfo.value.id}`,
      {
        description: orgInfo.value.description,
        autoApprove: orgInfo.value.autoApprove,
        requireNID: orgInfo.value.requireNID,
        requireCode: orgInfo.value.requireCode,
      },
      {
        headers: {
          AUTHORIZATION: `Bearer ${user.access_token}`,
        },
      }
    )
    .then((response) => {
      updateBtnLabel.value = "Updated";
    })

    .catch((err) => {
      updateBtnLabel.value = "Update Failed";
    });
}
</script>
