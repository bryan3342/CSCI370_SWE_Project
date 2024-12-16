package heartscope_final.com.heartscope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@SpringBootApplication(scanBasePackages = {"heartscope_final.com"})
@RestController
@RequestMapping("/predict")
public class HeartscopeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeartscopeApplication.class, args);
    }

    // Define a POJO to handle the incoming user input (all 12 responses)
    public static class UserInput {
        private String age;
        private String sex;
        private String chestPain;
        private String bloodPressure;
        private String cholesterol;
        private String fastingSugar;
        private String electrocardiogram;
        private String maxHeartRate;
        private String angina;
        private String oldPeak;
        private String stSlope;
        private String heartDiseaseRisk;

        // Getters and setters for each field
        public String getAge() { return age; }
        public void setAge(String age) { this.age = age; }

        public String getSex() { return sex; }
        public void setSex(String sex) { this.sex = sex; }

        public String getChestPain() { return chestPain; }
        public void setChestPain(String chestPain) { this.chestPain = chestPain; }

        public String getBloodPressure() { return bloodPressure; }
        public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }

        public String getCholesterol() { return cholesterol; }
        public void setCholesterol(String cholesterol) { this.cholesterol = cholesterol; }

        public String getFastingSugar() { return fastingSugar; }
        public void setFastingSugar(String fastingSugar) { this.fastingSugar = fastingSugar; }

        public String getElectrocardiogram() { return electrocardiogram; }
        public void setElectrocardiogram(String electrocardiogram) { this.electrocardiogram = electrocardiogram; }

        public String getMaxHeartRate() { return maxHeartRate; }
        public void setMaxHeartRate(String maxHeartRate) { this.maxHeartRate = maxHeartRate; }

        public String getAngina() { return angina; }
        public void setAngina(String angina) { this.angina = angina; }

        public String getOldPeak() { return oldPeak; }
        public void setOldPeak(String oldPeak) { this.oldPeak = oldPeak; }

        public String getStSlope() { return stSlope; }
        public void setStSlope(String stSlope) { this.stSlope = stSlope; }

        public String getHeartDiseaseRisk() { return heartDiseaseRisk; }
        public void setHeartDiseaseRisk(String heartDiseaseRisk) { this.heartDiseaseRisk = heartDiseaseRisk; }
    }

    // Prediction endpoint that accepts all the user's input as JSON
    @PostMapping
    public PredictionResponse predict(@RequestBody UserInput userInput) {
        // Process the user input here to make a prediction
        String prediction = makePrediction(userInput);

        // Use Map.ofEntries to handle more than 10 entries
        return new PredictionResponse(prediction, Map.ofEntries(
            Map.entry("age", userInput.getAge()),
            Map.entry("sex", userInput.getSex()),
            Map.entry("chestPain", userInput.getChestPain()),
            Map.entry("bloodPressure", userInput.getBloodPressure()),
            Map.entry("cholesterol", userInput.getCholesterol()),
            Map.entry("fastingSugar", userInput.getFastingSugar()),
            Map.entry("electrocardiogram", userInput.getElectrocardiogram()),
            Map.entry("maxHeartRate", userInput.getMaxHeartRate()),
            Map.entry("angina", userInput.getAngina()),
            Map.entry("oldPeak", userInput.getOldPeak()),
            Map.entry("stSlope", userInput.getStSlope()),
            Map.entry("heartDiseaseRisk", userInput.getHeartDiseaseRisk())
        ));
    }

    // A method to simulate a prediction based on the input
    private String makePrediction(UserInput input) {
        // Simulate prediction logic. You should replace this with your actual model logic
        // Here, we simulate predicting "Yes" for heart disease if the heartDiseaseRisk is "1"
        if ("1".equals(input.getHeartDiseaseRisk())) {
            return "Prediction: Heart Disease Detected!";
        } else {
            return "Prediction: No Heart Disease Detected.";
        }
    }

    // Define the response structure to send back to the frontend
    public static class PredictionResponse {
        private String prediction;
        private Map<String, String> userInput;

        public PredictionResponse(String prediction, Map<String, String> userInput) {
            this.prediction = prediction;
            this.userInput = userInput;
        }

        public String getPrediction() {
            return prediction;
        }

        public void setPrediction(String prediction) {
            this.prediction = prediction;
        }

        public Map<String, String> getUserInput() {
            return userInput;
        }

        public void setUserInput(Map<String, String> userInput) {
            this.userInput = userInput;
        }
    }
}