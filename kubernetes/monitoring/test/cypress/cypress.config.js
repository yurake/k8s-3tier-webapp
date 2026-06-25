const { defineConfig } = require("cypress");

module.exports = defineConfig({
  projectId: "7rgxn6",
  e2e: {
    specPattern: "cypress/integration/**/*.{js,jsx,ts,tsx}",
    supportFile: false,
  },
});
