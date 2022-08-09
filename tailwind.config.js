/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: "class",
  content: ["./src/**/*.{html,js,vue}"],
  theme: {
    extend: {},
    fontFamily: {
      display: ["Lobster"],
      body: ['"Inter"', '"Open Sans"'],
      bangla: ["SheikhHasina", '"Open Sans"'],
    },
  },
  plugins: [],
};
