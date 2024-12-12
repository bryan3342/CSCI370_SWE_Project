// Class for managing user input
class UserInput {
    constructor() {
        this.input = '';
    }

    storeInput(inputValue) {
        this.input = inputValue;
    }

    getInput() {
        return this.input;
    }
}

// Class for storing user data/history
class UserData {
    constructor() {
        this.userInputHistory = [];
    }

    addInput(input) {
        this.userInputHistory.push(input);
    }

    getHistory() {
        return this.userInputHistory;
    }
}

// Class for managing the UI, handling input, and processing user data
class ChatBotUI {
    constructor() {
        this.userInput = new UserInput();
        this.userData = new UserData();
    }

    // Create and store user data
    CreateUserData(input) {
        this.userInput.storeInput(input);
        this.userData.addInput(this.userInput.getInput());
    }

    // Process and show the stored user data
    ProcessData() {
        console.log("User Input History: ", this.userData.getHistory());
    }
}

// Function to get the value from the input field and display chat bubbles
function getInputValue() {
    const inputText = document.getElementById("textbar");
    const userMessage = inputText.value.trim(); // Get user input and trim spaces

    if (userMessage === '') {
        console.log("Empty input ignored.");
        return; // Do nothing if input is empty
    }

    // Create a new instance of ChatBotUI to manage user input and data
    const chatBotUI = new ChatBotUI();

    // Store the user input and process it
    chatBotUI.CreateUserData(userMessage);
    chatBotUI.ProcessData();

    // Add the user message bubble (self)
    const chatContainer = document.getElementById("chat-container");

    const userBubble = document.createElement("div");
    userBubble.classList.add("chat-bubble", "self"); // self class for user's bubble
    userBubble.textContent = userMessage; // Set message content
    chatContainer.appendChild(userBubble); // Add the bubble to the container

    // Add bot response bubble (opposite)
    const botResponse = `Welcome to Heartscope!`; // Simulated bot response
    const botBubble = document.createElement("div");
    botBubble.classList.add("chat-bubble", "opposite"); // opposite class for bot's bubble
    botBubble.textContent = botResponse; // Set bot response content
    chatContainer.appendChild(botBubble); // Add the bubble to the container

    // Scroll to the bottom of the chat container after adding new messages
    chatContainer.scrollTop = chatContainer.scrollHeight;

    // Clear the input field for the next message
    inputText.value = '';
}

// Event listener for the button to handle input
document.addEventListener("DOMContentLoaded", function () {
    const button = document.getElementById("button");
    const input = document.getElementById("textbar");


    if (button) {
        button.addEventListener("click", function () {
            getInputValue(); // Trigger the function when button is clicked
        });
    } else {
        console.log("Button not found!");
    }

    if(input) {
        input.addEventListener("keypress", function (event) {
            if(event.key == "Enter") {
                event.preventDefault();
                getInputValue();
            }
        });
    } else {
        console.log("Input text field not found!");
    }
});

document.addEventListener("DOMContentLoaded", () => {
    fetch("http://localhost:8080/api/data")
        .then(response => response.json())
        .then(data => {
            console.log(data); // Handle the response data
        })
        .catch(error => console.error('Error:', error));
});
