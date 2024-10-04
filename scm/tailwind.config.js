/** @type {import('tailwindcss').Config} */
/*  npx tailwindcss -i src/main/resources/static/css/input.css -o src/main/resources/static/css/output.css --watch */
export default {
  content: ["./src/main/resources/**/*.{html,js}"],
  theme: {
    extend: {},
  },
  plugins: [],
  darkMode: "selector",
}

