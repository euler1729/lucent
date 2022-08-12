<template>
  <Loading v-if="loading" msg="Loading Memberships" />
  <DefaultLayout
    ><div class="px-10 py-10 flex flex-col">
      <div class="text-xl font-semibold">Pending Members</div>

      <div
        class="overflow-x-auto relative shadow-md rounded-lg mt-10 md:w-3/4 self-center w-full"
      >
        <table
          class="w-full text-sm text-left text-gray-500 dark:text-gray-400"
        >
          <thead
            class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400"
          >
            <tr>
              <th scope="col" class="py-3 px-6">Member Name</th>
              <th scope="col" class="py-3 px-6">Membership Code</th>
              <th scope="col" class="py-3 px-6">NID</th>
              <th scope="col" class="py-3 px-6">Approval</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="member in unapprovedMembers"
              :key="member.id"
              class="bg-white border-b dark:bg-gray-900 dark:border-gray-700"
            >
              <th
                scope="row"
                class="py-4 px-6 font-medium text-gray-900 whitespace-nowrap dark:text-white"
              >
                {{ member.donor }}
              </th>
              <td class="py-4 px-6">{{ member.membershipCode }}</td>
              <td class="py-4 px-6">{{ member.nid }}</td>

              <td
                @click="handleAccept(member.id)"
                v-if="member.approved == false"
                class="py-4 px-6 text-blue-400 cursor-pointer"
              >
                <font-awesome-icon class="mr-2" icon="hourglass-half" /> Pending
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
  <Modal :isOpen="isOpen" title="Membership Approval" :key="isOpenKey">
    <div class="relative bg-white rounded-lg dark:bg-gray-700 font-bangla">
      <div class="pb-6 px-4 dark:text-gray-300">
        Do you want to accept the membership request?
      </div>
    </div>
    <div class="flex flex-col items-center">
      <Btn class="text-center" @click="acceptMembershipRequest(0)">Accept</Btn>
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
const unapprovedMembers = ref([]);
const approvedMembers = ref([]);
const toAcceptId = ref(null);

const isOpen = ref(false);
const isOpenKey = ref(0);

onMounted(() => {
  if (!user.loggedIn) router.push({ name: "home" });
  loadRequests(0);
});

function loadRequests(counter) {
  if (counter < 2) {
    loadLabel.value["en"] = "Loading";
    loadLabel.value["bn"] = "Loading";
    api
      .get(
        `/membership/unapproved/${route.params.orgId}?page=${page.value}&size=10&sortBy=id`,
        {
          headers: {
            AUTHORIZATION: `Bearer ${user.refresh_token}`,
          },
        }
      )
      .then((response) => {
        response.data.forEach((element) => {
          unapprovedMembers.value.push(element);
        });
        if (response.data.length == 0) {
          loadLabel.value["en"] = "No More Pending Request";
          loadLabel.value["bn"] = "No More Pending Request";
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

function handleAccept(id) {
  toAcceptId.value = id;
  isOpen.value = true;
  isOpenKey.value = Math.random();
}

function acceptMembershipRequest(counter) {
  if (counter < 2) {
    api
      .put(
        `/membership/approve/${toAcceptId.value}`,
        {},
        {
          headers: {
            AUTHORIZATION: `Bearer ${user.refresh_token}`,
          },
        }
      )
      .then((response) => {
        unapprovedMembers.value = [];
        loadRequests(0);
        isOpen.value = false;
        isOpenKey.value = Math.random();
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
              acceptMembershipRequest(counter + 1);
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
