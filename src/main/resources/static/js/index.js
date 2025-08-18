const API_BASE_URL = "http://localhost:8080";
// GET STORED USER DATA
// const storedUser = localStorage.getItem("user");
// if (storedUser) {
//   console.log("user exists");
//   const currentUser = JSON.parse(storedUser);

//   console.log(currentUser);
//   // DISPLAY USER INFO
//   document.getElementById(
//     "user-info"
//   ).textContent = `welcome ${currentUser.name}`;
// }

// // If no user data, redirect to login
// else {
//   window.location.href = "auth/login.html";
// }
document.getElementById("logout").addEventListener("click", (e) => {
  fetch(`${API_BASE_URL}/api/auth/logout`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (!response.ok) {
        if (response.status === 401) {
          window.location.href = "/auth/login.html";
          return;
        }
        throw new Error(`${response.status}: ${response.statusText}`);
      }
      return response.json();
    })
    .then((data) => {
      console.log("success", data);
      window.location.href = "/auth/login.html";
    })
    .catch((error) => {
      console.error(`Error while trying to logout`, error);
    });
});

fetch(`${API_BASE_URL}/api/todo/get`, {
  method: "GET",
  headers: {
    "Content-Type": "application/json",
  },
})
  .then((response) => {
    if (!response.ok) {
      if (response.status === 401) {
        window.location.href = "/auth/login.html";
        return;
      }
      throw new Error(`${response.status}:${response.statusText}`);
    }

    return response.json();
  })
  .then((data) => {
    console.log("success:", data);

    data.forEach((item) => {
      tasks.push(item);
    });
    loadList();
  })
  .catch((error) => {
    console.error("Error while fetching the todos list", error);
  });

function addListElement(content) {
  var listElement = document.createElement("div");
  listElement.classList.add("list-element");
  var taskContent = document.createElement("div");
  taskContent.classList.add("task-content");
  var check = document.createElement("input");
  check.type = "checkbox";
  var title = document.createElement("h2");
  title.className = "task-title";
  title.innerText = `${content.description}`;
  var deleteButton = document.createElement("h2");
  deleteButton.classList.add("delete-button");
  deleteButton.textContent = "x";
  deleteButton.addEventListener("click", function () {
    deleteTask(content);
  });
  // Prevent clicks on checkbox or delete from triggering the edit modal
  check.addEventListener("click", function (e) {
    e.stopPropagation();
  });
  deleteButton.addEventListener("click", function (e) {
    e.stopPropagation();
  });
  // Open edit modal on task click
  listElement.addEventListener("click", function (e) {
    // Ignore clicks originating from delete or input elements
    if (e.target.closest(".delete-button") || e.target.tagName === "INPUT")
      return;
    openEditModal(content);
  });
  taskContent.appendChild(check);
  taskContent.appendChild(title);
  listElement.appendChild(taskContent);
  listElement.appendChild(deleteButton);
  document.querySelector(".list-container").appendChild(listElement);
}

var taskField = document.querySelector(".task-field");
var listContainer = document.querySelector(".list-container");

function saveTask() {
  // addListElement(taskField.value);
  tasks.push({
    id: nextId,
    description: `${taskField.value}`,
  });

  //Increment the next Id
  nextId++;

  // Print the list of tasks for debugging
  console.log(tasks);
  //Save the task in the backend
  let todo = {
    description: `${taskField.value}`,
  };
  fetch(`${API_BASE_URL}/api/todo/save`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(todo),
  })
    .then((response) => {
      if (!response.ok) {
        if (response.status === 401) {
          window.location.href = "/auth/login.html";
          return;
        }
        throw new Error(`${response.status}: ${response.statusText}`);
      }

      return response.json();
    })
    .then((data) => {
      console.log("success", data);
      tasks.pop();
      tasks.push(data);
      //Clear the displayed list and display the new list
      listContainer.innerHTML = "";
      loadList();
    })
    .catch((error) => {
      console.error("error", error);
      tasks.pop();
    });

  //Clear the task field
  taskField.value = "";
}

function deleteTask(task) {
  tasks = tasks.filter((t) => t.id != task.id);

  // delete the task for the database
  fetch(`${API_BASE_URL}/api/todo/delete`, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(task),
  })
    .then((response) => {
      if (!response.ok) {
        if (response.status === 401) {
          window.location.href = "/auth/login.html";
          return;
        }
        throw new Error(`${response.status}:${response.statusText}`);
      }
      return response.json();
    })
    .then((data) => {
      console.log("success:", data);
      //Clear the displayed and display the new list
      listContainer.innerHTML = "";
      loadList();
    })
    .catch((error) => {
      console.error(`Error while deleting todo:`, error);
      tasks.push(task);
    });
}
var addbutton = document.querySelector(".add-button");

addbutton.addEventListener("click", function () {
  if (taskField.value !== "") saveTask();
});

taskField.addEventListener("keydown", (e) => {
  if (e.key == "Enter") {
    if (taskField.value !== "") saveTask();
  }
});

let tasks = [
  // { id: 1, description: "Doing the dishes" },
  // { id: 2, description: "Wipe the floor" },
  // { id: 3, description: "Wash some clothes" },
];

var nextId = tasks.length + 1;

function loadList() {
  tasks.forEach((item) => {
    addListElement(item);
  });
}

loadList();
console.log(tasks);

// Modal elements
const editModal = document.getElementById("edit-modal");
const editDescriptionInput = document.getElementById("edit-description");
const editForm = document.getElementById("edit-form");
const editCancelBtn = document.getElementById("edit-cancel");
const editCloseBtn = document.getElementById("edit-modal-close");

let editingTask = null;

function openEditModal(task) {
  editingTask = task;
  editDescriptionInput.value = task.description || "";
  editModal.style.display = "flex";
  // Trigger animation after display is set
  requestAnimationFrame(() => {
    editModal.classList.add("show");
  });
  editDescriptionInput.focus();
}

function closeEditModal() {
  editModal.classList.remove("show");
  // Wait for animation to complete before hiding
  setTimeout(() => {
    editModal.style.display = "none";
  }, 300);
  editingTask = null;
  editForm.reset();
}

// Close handlers
editCancelBtn?.addEventListener("click", closeEditModal);
editCloseBtn?.addEventListener("click", closeEditModal);
window.addEventListener("click", (e) => {
  if (e.target === editModal) closeEditModal();
});

// Save handler
editForm?.addEventListener("submit", (e) => {
  e.preventDefault();
  if (!editingTask) return;
  const newDesc = editDescriptionInput.value.trim();
  if (newDesc === "") return;

  // Update locally
  tasks = tasks.map((t) =>
    t.id === editingTask.id ? { ...t, description: newDesc } : t
  );
  listContainer.innerHTML = "";
  loadList();
  const updatedTask = tasks.find((t) => t.id === editingTask.id);
  fetch(`${API_BASE_URL}/api/todo/update`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(updatedTask),
  })
    .then((response) => {
      if (!response.ok) {
        if (response.status === 401) {
          window.location.href = "/auth/login.html";
          return;
        }
        throw new Error(`${response.status}: ${response.statusText}`);
      }
      return response.json();
    })
    .then((data) => {
      console.log("succes", data);
    })
    .catch((error) => {
      console.error("Error while updating task", error);
    });

  closeEditModal();
});
