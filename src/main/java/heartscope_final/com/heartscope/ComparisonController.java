package heartscope_final.com.heartscope;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class ComparisonController {

    @GetMapping("/comparison-values")
    public ResponseEntity<UserData> getComparisonValues() {
        // Mock a FilteredDataset for simplicity
        FilteredDataset filteredDataset = new FilteredDataset();

        // Add mock UserData objects to simulate data
        UserData mockUser1 = new UserData(new String[]{"45", "M", "ATA", "120", "220", "0", "Normal", "150", "N", "1.5", "Flat", "1"});
        UserData mockUser2 = new UserData(new String[]{"50", "F", "NAP", "130", "250", "1", "ST", "160", "Y", "2.0", "Down", "1"});

        filteredDataset.add(mockUser1);
        filteredDataset.add(mockUser2);

        // Call getComparisonValues() to return average/mock results
        UserData comparisonValues = filteredDataset.getComparisonValues();

        // Return the response
        return ResponseEntity.ok(comparisonValues);
    }

    @PostMapping("/convert-user-input")
    public ResponseEntity<UserData> convertUserInput(@RequestBody String[] userInput) {

        UserData convertedUserData = new UserData(userInput);
        return ResponseEntity.ok(convertedUserData);
    }
    
}