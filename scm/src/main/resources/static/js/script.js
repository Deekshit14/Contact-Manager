console.log("Script loaded");

let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded", () => {
  changeTheme();
});

function changeTheme() {
    // Apply the current theme to the web page
    document.querySelector('html').classList.add(currentTheme);

    // Set the listener for the theme change button
    let changeThemeButton = document.querySelector('#theme_change_button');

    // Change the text of the button based on the current theme
    changeThemeButton.querySelector('span').textContent = currentTheme === 'light' ? 'Dark' : 'Light';

    changeThemeButton.addEventListener("click", () => {
        console.log('Theme changed');

        // Toggle the theme
        document.querySelector('html').classList.toggle('dark');
        document.querySelector('html').classList.toggle('light');

        // Update the current theme
        currentTheme = currentTheme === 'dark' ? 'light' : 'dark';

        // Save the updated theme in local storage
        setTheme(currentTheme);

        // Update the button text
        changeThemeButton.querySelector('span').textContent = currentTheme === 'light' ? 'Dark' : 'Light';
    });
}

// Set theme to local storage
function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

// Get theme from local storage
function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}