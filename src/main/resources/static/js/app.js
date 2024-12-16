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

// Validation rules for each question
const validationRules = [
    input => /^\d+$/.test(input), // Age: Integer only
    input => /^[mf]$/i.test(input), // Sex: 'M' or 'F'
    input => /^(ata|nap|asy|ta)$/i.test(input), // Chest Pain: ATA, NAP, ASY, TA
    input => /^\d+$/.test(input), // Resting BP: Integer
    input => /^\d+$/.test(input), // Cholesterol: Integer
    input => /^[01]$/.test(input), // Fasting Sugar: 0 or 1
    input => /^(normal|st|lvh)$/i.test(input), // ECG Results: Normal, ST, LVH
    input => /^\d+$/.test(input), // Max Heart Rate: Integer
    input => /^[yn]$/i.test(input), // Angina: 'Y' or 'N'
    input => /^\d+(\.\d+)?$/.test(input), // Oldpeak: Decimal number
    input => /^(down|flat|up)$/i.test(input), // ST Slope: Down, Flat, Up
    input => /^[01]$/.test(input) // Heart Disease Risk: 0 or 1
];

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

// Function to clear user input and restart the process
function clearUserDataAndRestart(chatBotUI, resetFunction) {
    chatBotUI.userData.clearHistory();
    chatBotUI.userInput.storeInput(''); // Clear input
    resetFunction();
    console.log("User data cleared due to 25 seconds of inactivity.");
}

function displayUserResponsesWithObject(userResponses, prediction) {
    // Construct a user data object
    const userDataObject = {
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
        heartDiseaseRisk: userResponses[11],
    };

    console.log("User Responses Array: ", userResponses);
    console.log("User Data Object: ", userDataObject);

    // Display prediction status
    if (prediction === 1 || prediction.toString() === "1") {
        console.log("Heart Disease Detected: Yes");
    } else {
        console.log("Heart Disease Detected: No");
    }
}

// Idle timer setup
function setupIdleTimer(chatBotUI, resetFunction) {
    let idleTimer;

    const resetIdleTimer = () => {
        clearTimeout(idleTimer);
        idleTimer = setTimeout(() => {
            clearUserDataAndRestart(chatBotUI, resetFunction);
        }, 25000); // 25 seconds
    };

    document.addEventListener("keydown", resetIdleTimer);
    document.addEventListener("mousemove", resetIdleTimer);

    resetIdleTimer(); // Start the timer initially
}

// Function to initialize the idle timer in DOMContentLoaded
function initializeIdleTimer(chatBotUI, resetFunction) {
    setupIdleTimer(chatBotUI, resetFunction);
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

    const resetChat = () => {
        currentIndex = 0;
        userResponses.length = 0;
        updateQuestion();
        console.log("Chat reset due to inactivity.");
    };

    const submitToBackend = (responses) => {
        fetch("http://localhost:8080/predict", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
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
            .then(predictionData => {
                console.log("Prediction Data:", predictionData);
        // Step 1: Send userResponses to backend to convert to UserData
        fetch("http://localhost:8080/api/convert-user-input", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(responses)
        })
            .then(response => response.json())
            .then(convertedUserData => {
                console.log("Converted UserData:", convertedUserData);
    
                // Step 2: Fetch comparison values
                fetch("http://localhost:8080/api/comparison-values", {
                    method: "GET",
                    headers: { "Content-Type": "application/json" }
                })
                    .then(response => response.json())
                    .then(comparisonData => {
                        console.log("Comparison Values:", comparisonData);
    
                        // Step 3: Display results and prediction
                        displayFinalResults(responses, convertedUserData, comparisonData);
                    })
                    .catch(error => console.error("Error fetching comparison values:", error));
            })
            .catch(error => console.error("Error converting user input:", error));
        })
    };
    
    const displayFinalResults = (prediction, convertedUserData, comparisonValues) => {
        mainContainer.innerHTML = ''; // Clear the container
        
        const responseDiv = document.createElement("div");
        responseDiv.style.padding = "20px";
        responseDiv.style.fontSize = "18px";
        responseDiv.style.lineHeight = "1.8";
        responseDiv.style.backgroundColor = "#f9f9f9";
        responseDiv.style.borderRadius = "8px";
        responseDiv.style.fontFamily = "monospace";
    
        // Format data as strings
        const convertedUserDataString = JSON.stringify(convertedUserData);
        const comparisonValuesString = JSON.stringify(comparisonValues);
    
        // Display the data
        responseDiv.innerHTML = `
            <h2>Prediction</h2>
            <p>${prediction === 1 ? "Yes (Heart Disease Detected)" : "No (Heart Disease Not Detected)"}</p>
            <h3>Processed User Data</h3>
            <pre style="white-space: pre-wrap;">${convertedUserDataString}</pre>
            <h3>Comparison Values</h3>
            <pre style="white-space: pre-wrap;">${comparisonValuesString}</pre>
        `;
    
        mainContainer.appendChild(responseDiv);
    };
    
    const submitAnswer = () => {
        const userMessage = input.value.trim();
        if (userMessage === "") return;
    
        // Validate input
        if (!validationRules[currentIndex](userMessage)) {
            alert(`Invalid input. Please try again for: ${questions[currentIndex]}`);
            input.value = '';
            return;
        }
    
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
    
    

    input.addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            event.preventDefault();
            submitAnswer();
        }
    });

    button.addEventListener("click", submitAnswer);

    // Idle Timer Setup
    let idleTimer;
    const resetIdleTimer = () => {
        clearTimeout(idleTimer);
        idleTimer = setTimeout(() => {
            resetChat();
        }, 15000); // 25 seconds
    };

    document.addEventListener("keydown", resetIdleTimer);
    document.addEventListener("mousemove", resetIdleTimer);
    resetIdleTimer(); // Start the idle timer

    updateQuestion(); // Display the first question
});
