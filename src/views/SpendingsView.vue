<template>
  <DefaultLayout>
    <div class="px-10 py-10 flex flex-col">
      <div class="text-xl font-semibold">{{ orgInfo.name }} Spendings</div>

      <div
        class="overflow-x-auto relative shadow-md rounded-lg mt-10 md:w-3/4 self-center w-full"
      >
        <table
          class="w-full text-sm text-left text-gray-500 dark:text-gray-400"
        >
          <thead
            class="text-lg text-gray-700 bg-darkblue text-white dark:text-gray-400"
          >
            <tr>
              <th scope="col" class="py-3 px-6">Description</th>
              <th scope="col" class="py-3 px-6">Amount</th>
              <th scope="col" class="py-3 px-6">Time</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="spending in latestSpendings"
              :key="spending.id"
              class="bg-white text-lg border-b dark:bg-gray-900 dark:border-gray-700"
            >
              <th
                scope="row"
                class="py-4 px-6 font-medium text-gray-900 whitespace-nowrap dark:text-white"
              >
                {{ spending.description }}
              </th>
              <td class="py-4 px-6">{{ spending.amount }}</td>
              <td class="py-4 px-6">{{ getTimeDiff(spending.created) }}</td>
            </tr>

            <tr>
              <td
                @click="loadMore"
                colspan="3"
                class="py-4 px-6 font-medium text-gray-400 whitespace-nowrap text-center cursor-pointer"
              >
                {{ loadLabel[inf.lang] }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </DefaultLayout>
</template>

<script setup>
import DefaultLayout from "../layout/Default.vue";
import api from "../api";
import { useInf } from "../stores/inf.js";
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";

const route = useRoute();
const inf = useInf();

const loading = ref(false);
const orgInfo = ref({});
const latestSpendings = ref([]);

onMounted(() => {
  loadOrg();
});

const page = ref(0);

const loadLabel = ref({
  en: "Loading",
  bn: "Loading",
});

function getTimeDiff(created) {
  let createdTime = new Date(created);
  return createdTime;
}

function loadlatestSpendingss() {
  loadLabel.value["en"] = "Loading";
  loadLabel.value["bn"] = "Loading";
  api
    .get(`/spending/latest/${route.params.orgId}?page=${page.value}&size=10`)
    .then((response) => {
      response.data.forEach((element) => {
        latestSpendings.value.push(element);
      });

      if (response.data.length == 0) {
        loadLabel.value["en"] = "No More Spendings";
        loadLabel.value["bn"] = "No More Spendings";
      } else {
        loadLabel.value["en"] = "Load More";
        loadLabel.value["bn"] = "Load More";
      }
    })
    .catch((err) => {
      console.log("Latest Donation", err);
    });
}

function loadOrg() {
  loading.value = true;
  api
    .get(`/org/det/${route.params.orgId}`)
    .then((response) => {
      loading.value = false;
      orgInfo.value = response.data;

      loadlatestSpendingss();
    })
    .catch((err) => {
      loading.value = false;
    });
}

function loadMore() {
  page.value += 1;
  loadlatestSpendingss();
}
</script>
