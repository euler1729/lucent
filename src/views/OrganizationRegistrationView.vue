<template>
  <Loading v-if="loading" msg="Getting Organization" />
  <DefaultLayout>
    <div class="flex flex-col items-center">
      <div
        class="grid md:w-3/4 grid-cols-1 mx-10 my-10 font-semibold text-lg font-body"
      >
        <!-- Name -->
        <div>
          <label
            for="name"
            class="block mb-2 text-xl font-body font-semibold text-gray-900 dark:text-gray-300"
            >Your Name
          </label>
          <input
            type="text"
            name="name"
            id="name"
            v-model="orgInfo.name"
            class="bg-white border-2 shadow-lg border-darkblue/60 text-gray-900 text-xl font-body font-semibold rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white px-6"
          />
        </div>
        <div class="mt-6">
          <label
            for="phone"
            class="block mb-2 text-xl font-body font-semibold text-gray-900 dark:text-gray-300"
            >Your Phone
          </label>
          <input
            type="text"
            name="phone"
            id="phone"
            v-model="orgInfo.phone"
            class="bg-white border-2 shadow-lg border-darkblue/60 text-gray-900 text-xl font-body font-semibold rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white px-6"
          />
        </div>
        <div class="mt-6">
          <label
            for="password"
            class="block mb-2 text-xl font-body font-semibold text-gray-900 dark:text-gray-300"
            >Choose a password
          </label>
          <input
            type="password"
            name="password"
            id="password"
            v-model="orgInfo.password"
            class="bg-white border-2 shadow-lg border-darkblue/60 text-gray-900 text-xl font-body font-semibold rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white px-6"
          />
        </div>
        <!-- Name -->
        <div class="mt-6">
          <label
            for="orgname"
            class="block mb-2 text-xl font-body font-semibold text-gray-900 dark:text-gray-300"
            >Organiztion Name
          </label>
          <input
            type="text"
            name="orgname"
            id="orgname"
            v-model="orgInfo.orgname"
            class="bg-white border-2 shadow-lg border-darkblue/60 text-gray-900 text-xl font-body font-semibold rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white px-6"
          />
        </div>
        <!-- description -->
        <div class="mt-6">
          <label
            for="name"
            class="block mb-2 text-xl font-body font-semibold text-gray-900 dark:text-gray-300"
            >Organization Description
          </label>
          <textarea
            rows="3"
            type="textarea"
            name="description"
            id="description"
            v-model="orgInfo.orgdesc"
            class="bg-white border-2 shadow-lg border-darkblue/70 text-gray-900 text-xl font-body font-semibold rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white px-6"
          />
        </div>
        <!-- Auto Approve -->
        <div class="mt-6">
          <SwitchGroup>
            <div class="w-full flex flex-row items-center justify-between">
              <SwitchLabel class="mr-4"> Auto Approve? </SwitchLabel>
              <Switch
                v-model="orgInfo.autoApprove"
                :class="orgInfo.autoApprove ? 'bg-darkblue' : 'bg-darkblue'"
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
                :class="orgInfo.requireCode ? 'bg-darkblue' : 'bg-darkblue'"
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
                :class="orgInfo.requireNID ? 'bg-darkblue' : 'bg-darkblue'"
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

        <div>
          <label class="block mb-2 pt-6" for="file_input"
            >Profile Picture</label
          >
          <input
            class="block w-full bg-gray-50 rounded-lg border border-gray-300 cursor-pointer dark:text-gray-400 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400"
            id="file_input"
            type="file"
            @change="onProPic"
          />
        </div>

        <div>
          <label class="block mb-2 pt-6" for="file_input">Cover Photo</label>
          <input
            class="block w-full bg-gray-50 rounded-lg border border-gray-300 cursor-pointer dark:text-gray-400 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400"
            id="file_input"
            type="file"
            @change="onCoverPic"
          />
        </div>
        <div class="mt-6">{{ errlabel }}</div>
      </div>
      <div>
        <Btn class="mt-6" @click="orgRegistration">{{ updateBtnLabel }}</Btn>
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
import Loading from "../components/Loading.vue";
import { useInf } from "../stores/inf";

const user = useUserStore();
const inf = useInf();
const router = useRouter();

const orgInfo = ref({
  name: "",
  phone: "",
  password: "",
  orgname: "",
  orgdesc: "",
  autoApprove: false,
  requireCode: true,
  requireNID: false,
  propic: "",
  coverpic: "",
});
const loading = ref(false);

const updateBtnLabel = ref("Register");

const errlabel = ref("");

function login() {
  api
    .get("/user/login", {
      params: {
        phone: orgInfo.value.phone,
        password: orgInfo.value.name,
      },
    })
    .then((response) => {
      user.setToken(response.data.access_token, response.data.refresh_token);

      api
        .get("/user/profile", {
          headers: {
            AUTHORIZATION: `Bearer ${response.data.access_token}`,
          },
        })
        .then((response) => {
          user.setUser(response.data);
          router.push("/");
        });
    })
    .catch((err) => {});
}

function orgRegistration() {
  console.log(orgInfo.value);

  let formData = new FormData();
  formData.append("orgName", orgInfo.value.orgname);
  formData.append("orgDescription", orgInfo.value.orgdesc);
  formData.append("name", orgInfo.value.name);
  formData.append("phone", orgInfo.value.phone);
  formData.append("password", orgInfo.value.password);
  formData.append("profilePic", orgInfo.value.propic);
  formData.append("coverPic", orgInfo.value.coverpic);

  updateBtnLabel.value = "Creating Organization";
  api
    .post("/org/registration", formData, {
      headers: {
        "Content-Type": `multipart/form-data; boundary=${formData._boundary}`,
      },
    })
    .then((response) => {
      updateBtnLabel.value = "Organization Created  | Waiting for Admin Approval.";
      errlabel.value = "";
      login();
      // router.push({ name: "home" });
    })
    .catch((err) => {
      for (let key in err.response.data) {
        errlabel.value = err.response.data[key];
      }
      updateBtnLabel.value = "Create Organization";
    });
}

function onProPic(e) {
  var files = e.target.files || e.dataTransfer.files;
  if (!files.length) return;
  orgInfo.value.propic = files[0];
  // createProPic(files[0]);
}

function onCoverPic(e) {
  var files = e.target.files || e.dataTransfer.files;
  if (!files.length) return;
  orgInfo.value.coverpic = files[0];
  // createCoverImage(files[0]);
}

function createProPic(file) {
  var image = new Image();
  var reader = new FileReader();

  reader.onload = (e) => {
    orgInfo.value.propic = e.target.result;
    console.log(e);
  };
  reader.readAsDataURL(file);
}

function createCoverImage(file) {
  var image = new Image();
  var reader = new FileReader();

  reader.onload = (e) => {
    orgInfo.value.coverpic = e.target.result;
  };
  reader.readAsDataURL(file);
}
</script>
