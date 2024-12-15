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

    clearHistory() {
        this.userInputHistory = [];
    }
}

class ChatBotUI {
    constructor() {
        this.userInput = new UserInput();
        this.userData = new UserData();
    }

    createUserData(input) {
        this.userInput.storeInput(input);
        this.userData.addInput(this.userInput.getInput());
    }

    processData() {
        console.log("User Input History: ", this.userData.getHistory());
    }
}

// Idle timer setup
function setupIdleTimer(chatBotUI) {
    let idleTimer;

    const resetIdleTimer = () => {
        clearTimeout(idleTimer);
        idleTimer = setTimeout(() => {
            chatBotUI.userData.clearHistory();
            console.log("User data logs cleared due to inactivity.");
        }, 5000);
    };

    document.addEventListener("keydown", resetIdleTimer);
    document.addEventListener("mousemove", resetIdleTimer);
}

function updateDivWithStrings() {
    const strings = [
        "1) What is your Age? (Enter an integer value)",
        "2) What is your Sex? (Enter 'M' for Male, 'F' for Female)",
        "3) Which type of Chest Pain have you been diagnosed with? (ATA, NAP, ASY, TA)",
        "4) What is your Resting Blood Pressure? (Enter an integer value)",
        "5) What is your Cholesterol? (Enter an integer value)",
        "6) Is your Fasting Blood Sugar greater than 120 mg/dl? (Enter 1 if Yes, 0 if No)",
        "7) What are your Resting Electrocardiogram results? (Normal, ST, LVH)",
        "8) What is your Max Heart Rate? (Enter an integer value)",
        "9) Do you experience exercise-induced angina? (Y/N)",
        "10) What is your Oldpeak value? (Enter a decimal number)",
        "11) What is your ST Slope on your ECG? (Down, Flat, Up)",
        "12) Would you say that you are at risk of Heart Disease? (Enter 1 if Yes, 0 if No)"
    ];

    let currentIndex = 0;
    const userResponses = [];
    const chatBotUI = new ChatBotUI();
    setupIdleTimer(chatBotUI);

    const div = document.getElementById("output__text");
    const input = document.getElementById("textbar");
    const mainContainer = document.getElementById("main-container");

    div.textContent = strings[currentIndex];

    input.addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            const userMessage = input.value.trim();

            userResponses.push(userMessage);
            chatBotUI.createUserData(userMessage);

            if (currentIndex === strings.length - 1) {
                displayFinalResponses(userResponses, mainContainer, chatBotUI);
                return;
            }

            currentIndex++;
            div.textContent = strings[currentIndex];
            input.value = '';
        }
    });
}

function displayFinalResponses(userResponses, container, chatBotUI) {
    container.innerHTML = '';

    const responseDiv = document.createElement("div");
    responseDiv.style.padding = "20px";
    responseDiv.style.fontSize = "18px";
    responseDiv.style.lineHeight = "1.6";
    responseDiv.style.backgroundColor = "#f9f9f9";
    responseDiv.style.borderRadius = "8px";

    fetch("http://localhost:8080/predict", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            age: userResponses[0],
            sex: userResponses[1],
            chestPain: userResponses[2],
            bloodPressure: userResponses[3],
            cholesterol: userResponses[4],
            fastingSugar: userResponses[5],
            electrocardiogram: userResponses[6],
            maxHeartRate: userResponses[7],
            angina: userResponses[8],
            oldPeak: userResponses[9],
            stSlope: userResponses[10],
            heartDiseaseRisk: userResponses[11]
        })
    })
        .then(response => response.json())
        .then(data => {
            responseDiv.innerHTML = `<h2>Prediction</h2><p>${data.prediction}</p>`;
            container.appendChild(responseDiv);
        })
        .catch(error => console.error("Error fetching response:", error));
}

document.addEventListener("DOMContentLoaded", function () {
    const button = document.getElementById("button");
    const input = document.getElementById("textbar");
    const div = document.getElementById("output__text");
    const mainContainer = document.getElementById("main-container");

    let currentIndex = 0;
    const userResponses = [];

    const questions = [
        "1) What is your Age? (Enter an integer value)",
        "2) What is your Sex? (Enter 'M' for Male, 'F' for Female)",
        "3) Which type of Chest Pain have you been diagnosed with? (ATA, NAP, ASY, TA)",
        "4) What is your Resting Blood Pressure? (Enter an integer value)",
        "5) What is your Cholesterol? (Enter an integer value)",
        "6) Is your Fasting Blood Sugar greater than 120 mg/dl? (Enter 1 if Yes, 0 if No)",
        "7) What are your Resting Electrocardiogram results? (Normal, ST, LVH)",
        "8) What is your Max Heart Rate? (Enter an integer value)",
        "9) Do you experience exercise-induced angina? (Y/N)",
        "10) What is your Oldpeak value? (Enter a decimal number)",
        "11) What is your ST Slope on your ECG? (Down, Flat, Up)",
        "12) Would you say that you are at risk of Heart Disease? (Enter 1 if Yes, 0 if No)"
    ];

    const updateQuestion = () => {
        div.textContent = questions[currentIndex];
        input.value = '';
    };

    const submitAnswer = () => {
        const userMessage = input.value.trim();
        if (userMessage === "") return;

        // Store the user input
        userResponses.push(userMessage);
        currentIndex++;

        // If all questions are answered, submit to backend
        if (currentIndex === questions.length) {
            submitToBackend(userResponses);
            return;
        }

        // Update the question after input
        updateQuestion();
    };

    const submitToBackend = (responses) => {
        fetch("http://localhost:8080/predict", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                age: responses[0],
                sex: responses[1],
                chestPain: responses[2],
                bloodPressure: responses[3],
                cholesterol: responses[4],
                fastingSugar: responses[5],
                electrocardiogram: responses[6],
                maxHeartRate: responses[7],
                angina: responses[8],
                oldPeak: responses[9],
                stSlope: responses[10],
                heartDiseaseRisk: responses[11]
            })
        })
            .then(response => response.json())
            .then(data => {
                displayPrediction(data.prediction);
            })
            .catch(error => console.error("Error submitting data:", error));
    };

    const displayPrediction = (prediction) => {
        // Clear any previous content
        mainContainer.innerHTML = '';

        // Create a div to display the prediction
        const responseDiv = document.createElement("div");
        responseDiv.style.padding = "20px";
        responseDiv.style.fontSize = "18px";
        responseDiv.style.lineHeight = "1.6";
        responseDiv.style.backgroundColor = "#f9f9f9";
        responseDiv.style.borderRadius = "8px";

        // Show the prediction result
        responseDiv.innerHTML = `<h2>Prediction</h2><p>${prediction}</p>`;
        mainContainer.appendChild(responseDiv);
    };

    // Event listeners
    input.addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            event.preventDefault();
            submitAnswer();
        }
    });

    button.addEventListener("click", submitAnswer);

    // Initial setup: display the first question
    updateQuestion();
});
