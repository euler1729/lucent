<template>
  <Loading v-if="loading" msg="Loading Memberships" />
  <DefaultLayout
    ><div class="px-10 py-10 flex flex-col">
      <div class="text-xl font-semibold">Pending Organizations</div>

      <div
        class="overflow-x-auto relative shadow-md rounded-lg mt-10 md:w-3/4 self-center w-full"
      >
        <table
          class="w-full text-sm text-left text-gray-500 dark:text-gray-400"
        >
          <thead
            class="text-white bg-darkblue text-lg bg-gray-50 dark:bg-gray-700 dark:text-gray-400"
          >
            <tr>
              <th scope="col" class="py-3 px-6">Organizations Name</th>
              <th scope="col" class="py-3 px-6">Description</th>
              <th scope="col" class="py-3 px-6">Approval</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="org in organizations"
              :key="org.id"
              class="bg-white border-b dark:bg-gray-900 dark:border-gray-700 text-lg"
            >
              <th
                scope="row"
                class="py-4 px-6 font-medium text-gray-900 whitespace-nowrap dark:text-white"
              >
                {{ org.name }}
              </th>
              <td class="py-4 px-6">{{ org.description }}</td>

              <td
                @click="handlePublish(org.id)"
                v-if="org.published == false"
                class="py-4 px-6 text-blue-400 cursor-pointer"
              >
                <font-awesome-icon class="mr-2" icon="hourglass-half" /> Pending
              </td>
              <td
                @click="handleUnpublish(org.id)"
                v-if="org.published"
                class="py-4 px-6 text-green-400 cursor-pointer"
              >
                <font-awesome-icon class="mr-2" icon="circle-check" /> Published
              </td>
            </tr>

            <tr>
              <td
                @click="loadMore"
                colspan="4"
                class="py-4 px-6 font-medium text-gray-400 whitespace-nowrap text-center cursor-pointer"
              >
                {{ loadLabel[inf.lang] }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div></DefaultLayout
  >
  <Modal
    :isOpen="publishModal"
    title="Membership Approval"
    :key="publishModalKey"
  >
    <div class="relative bg-white rounded-lg dark:bg-gray-700">
      <div class="pb-6 px-4 dark:text-gray-300">
        Do you want to publish the organization?
      </div>
    </div>
    <div class="flex flex-col items-center">
      <Btn class="text-center" @click="publishOrganization(0)">Accept</Btn>
    </div>
  </Modal>

  <Modal
    :isOpen="unpublishModal"
    title="Membership Approval"
    :key="unpublishModalKey"
  >
    <div class="relative bg-white rounded-lg dark:bg-gray-700">
      <div class="pb-6 px-4 dark:text-gray-300">
        Do you want to ban/unpublish the organization?
      </div>
    </div>
    <div class="flex flex-col items-center">
      <Btn class="text-center" @click="unpublishOrganization(0)">Accept</Btn>
    </div>
  </Modal>
</template>

<script setup>
import DefaultLayout from "../layout/Default.vue";
import Loading from "../components/Loading.vue";
import Modal from "../components/Modal.vue";
import { useRoute, useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import api from "../api";
import Btn from "../components/Btn.vue";
import { useUserStore } from "../stores/user";
import { useInf } from "../stores/inf";

const user = useUserStore();
const route = useRoute();
const router = useRouter();
const inf = useInf();

const loading = ref(false);
const loadLabel = ref({
  en: "Loading",
  bn: "Loading",
});

const page = ref(0);
const organizations = ref([]);
const selectedId = ref(null);

const publishModal = ref(false);
const publishModalKey = ref(0);
const unpublishModal = ref(false);
const unpublishModalKey = ref(0);

onMounted(() => {
  if (!user.loggedIn || user.role != user.adminRole)
    router.push({ name: "home" });
  loadRequests(0);
});

function loadRequests(counter) {
  if (counter < 2) {
    loadLabel.value["en"] = "Loading";
    loadLabel.value["bn"] = "Loading";
    api
      .get(`/org/all?page=${page.value}&size=10&sortBy=autoApprove`, {
        headers: {
          AUTHORIZATION: `Bearer ${user.refresh_token}`,
        },
      })
      .then((response) => {
        response.data.forEach((element) => {
          organizations.value.push(element);
        });
        if (response.data.length == 0) {
          loadLabel.value["en"] = "No More Organizations";
          loadLabel.value["bn"] = "No More Organizations Request";
        } else {
          loadLabel.value["en"] = "Load More";
          loadLabel.value["bn"] = "Load More";
        }
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
              loadRequests(counter + 1);
            })
            .catch((err) => {
              user.logout();
              loading.value = false;
            });
        }
      });
  }
}

function loadMore() {
  page.value += 1;
  loadRequests(0);
}

function handlePublish(id) {
  console.log("To publish");
  selectedId.value = id;
  publishModal.value = true;
  publishModalKey.value = Math.random();
}

function handleUnpublish(id) {
  selectedId.value = id;
  unpublishModal.value = true;
  unpublishModalKey.value = Math.random();
}

function publishOrganization(counter) {
  if (counter < 2) {
    api
      .post(
        `/org/publish/${selectedId.value}`,
        {},
        {
          headers: {
            AUTHORIZATION: `Bearer ${user.refresh_token}`,
          },
        }
      )
      .then((response) => {
        organizations.value = [];
        loadRequests(0);
        publishModal.value = false;
        publishModalKey.value = Math.random();
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
              publishOrganization(counter + 1);
            })
            .catch((err) => {
              user.logout();
              loading.value = false;
            });
        }
      });
  }
}

function unpublishOrganization(counter) {
  if (counter < 2) {
    api
      .post(
        `/org/unpublish/${selectedId.value}`,
        {},
        {
          headers: {
            AUTHORIZATION: `Bearer ${user.refresh_token}`,
          },
        }
      )
      .then((response) => {
        organizations.value = [];
        loadRequests(0);
        unpublishModal.value = false;
        unpublishModalKey.value = Math.random();
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
              unpublishOrganization(counter + 1);
            })
            .catch((err) => {
              user.logout();
              loading.value = false;
            });
        }
      });
  }
}
</script>
