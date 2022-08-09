import { defineStore } from "pinia";

export const useInf = defineStore({
  id: "inf",
  state: () => ({
    lang: "en",
  }),
  getters: {},
  actions: {
    setLang(to_lang) {
      this.lang = to_lang;
    },
  },
});
