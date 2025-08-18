var section2 = document.querySelector(".section-2");
var section3 = document.querySelector(".section-3");
section3.style.display = "none";
var loginEmail = document.getElementById("email");
var loginLink = document.getElementById("login");
var password = document.getElementById("password");
var signEmail = document.getElementById("sign-email");
var fName = document.getElementById("fullName");
var signPswd = document.getElementById("sign-password");
var confirmPswd = document.getElementById("confirmPassword");
const API_BASE_URL = "http://localhost:8080";
loginLink.addEventListener("click", () => {
  section3.style.display = "none";
  section2.style.display = "flex";
});

var signLink = document.getElementById("sign");
signLink.addEventListener("click", () => {
  section3.style.display = "flex";
  section2.style.display = "none";
});

document.getElementById("login-form").addEventListener("submit", (e) => {
  e.preventDefault();
  console.log(password.value);
  console.log(loginEmail.value);
  if (!isEmailValid(loginEmail.value)) {
    loginEmail.style.border = "2px solid orangered";
    document.getElementById(
      "login-email-error"
    ).innerText = `Please enter a valid email address`;
  } else if (!isValidPassword(password.value)) {
    password.style.border = "2px solid red";
    document.getElementById(
      "login-pswd-error"
    ).innerText = `Include at least 2 numbers and 1 uppercase letter in your password (min 5 characters).`;
  } else {
    const userData = {
      email: loginEmail.value,
      password: password.value,
    };
    fetch(`${API_BASE_URL}/api/auth/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userData),
    })
      .then((response) => {
        console.log("status: ", response.status);
        if (!response.ok) {
          throw new Error(`${response.status}: ${response.statusText}`);
        }
        return response.json();
      })
      .then((data) => {
        console.log("success: ", data);
        window.location.href = "/index.html";
        console.log("About to store user:", storedUser);
      })
      .catch((error) => {
        console.error(`Error: ${error}`);
      });
  }
});

function isEmailValid(email) {
  const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  return regex.test(email);
}

function isValidPassword(password) {
  return password.length > 5;
}
// Advanced password validation
// function isValidPassword(password) {
//   const regex = /^(?=(?:.*\d){2,})(?=.*[A-Z]).{5,}$/;
//   return regex.test(password);
// }

document.getElementById("sign-up-form").addEventListener("submit", (e) => {
  e.preventDefault();
  if (!isEmailValid(signEmail.value)) {
    signEmail.style.border = "2px solid red";
    document.getElementById(
      "signup-email-error"
    ).innerText = `please enter a valid email(eg: example@mail.com)`;
  } else if (!isFullNameValid(fName.value)) {
    fName.style.border = "2px solid red";
    document.getElementById(
      "signup-name-error"
    ).innerText = `full name must be at least 3 characters long`;
  } else if (!isValidPassword(signPswd.value)) {
    signPswd.style.border = "2px solid red";
    document.getElementById(
      "signup-pswd-error"
    ).innerText = `Include at least 2 numbers and 1 uppercase letter in your password (min 5 characters).`;
  } else if (!isValidPassword(confirmPswd.value)) {
    confirmPswd.style.border = "2px solid red";
    document.getElementById(
      "signup-confirm-error"
    ).innerText = `Include at least 2 numbers and 1 uppercase letter in your password (min 5 characters).`;
  } else if (confirmPswd.value !== signPswd.value) {
    confirmPswd.style.border = "2px solid red";
    document.getElementById(
      "signup-confirm-error"
    ).innerText = `Passwords don't match`;
  } else {
    const userData = {
      email: signEmail.value,
      name: fName.value,
      password: signPswd.value,
    };
    console.log("User data: ", userData);
    fetch(`${API_BASE_URL}/api/auth/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(userData),
    })
      .then((response) => {
        console.log("Response status:", response.status);
        console.log("Response ok:", response.ok);
        if (!response.ok) {
          throw new Error(`Status: ${response.status},${response.statusText}`);
        }
        return response.json();
      })
      .then((data) => {
        console.log("Success data:", data);
        window.location.href = "/index.html";
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }
});

function isFullNameValid(name) {
  return name.length >= 3;
}
