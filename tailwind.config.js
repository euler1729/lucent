/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: "class",
  content: ["./src/**/*.{html,js,vue}"],
  theme: {
    extend: {},
    fontFamily: {
      display: ["Yesteryear"],
      body: ['"Quicksand"', '"Open Sans"'],
      inter: ['"Inter"', '"Open Sans"'],
      hero: ['"Unna"', '"serif"'],
    },
    colors: {
      bg: "#EEEDE4",
      white: "#FFFFFF",
      purple1: "#6E2CDB",
      purple2: "#8D5BF8",
      black: "#000000",
      darkblue: "#192F45",
    },
  },
  plugins: [],
};
