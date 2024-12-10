package code;

/**
 * @author Nicholas Farkash
 */

public class Main {
	

	
    public static void main(String[] args){
    	
    	// Get a time stamp as soon as the backend is reached
    	long before = System.currentTimeMillis();

		
    	
    	//FIXME: Replace with the user's actual input from the GUI
    	String[] userInput =  {"55", "M", "ATA", "166", "243", "0", "Normal", "180", "N", "0", "Up", "0"};
    	
    	Object[] results = run(userInput);
    	int prediction = (int) results[0];
    	UserData averageValues = (UserData) results[1];
    	
    	// Get a time stamp when command is returned to the frontend and calculate the duration
    	long after = System.currentTimeMillis();		
        long duration = after - before;
        System.out.print("Elapsed Time: " + (float)duration/1000 + "s");
        		
        
        
    	// FIXME: Return the prediction and averageValues to the GUI
    	
    }
    
    private static Object[] run(String[] userInput) {
    	
        // Import the dataset
    	SOP("Importing Dataset...");
        String originalDataFilePath = "src/data/HeartScope_Dataset.csv";
    	Dataset dataset = new Dataset(originalDataFilePath, true);
    	SOP("Dataset Created\n");

    	
    	
    	// Split the Dataset
    	SOP("Splitting Dataset...");
    	Validation validation = new Validation(dataset);
    	SOP("Dataset Split\n");
    	
    	
    	
    	// Create and Train Random Forest
    	SOP("Creating and Training Random Forest...");
    	RandomForest rf = new RandomForest(validation);
    	SOP("Random Forest Created and Trained\n");

    	
    	
    	// Validate the RandomForest
    	SOP("Validating Random Forest...");
    	float error = rf.calculateInSampleError();
    	SOP("Validation Complete - Error: " + error + "\n");
    	
    	
    	
    	// Convert user's input to a UserData
    	SOP("Converting user's input to UserData...");
        UserData user = new UserData(userInput);
    	SOP("UserData: " + user + "\n");

    	
   
    	// Make a prediction for the user
    	SOP("Making Prediction...");
        int prediction = rf.predict(user);
    	SOP("Prediction: " + prediction + "\n");

    	
    	
    	// Get additional information for that user
    	SOP("Calculating Hypothetical 'Average' User...");
    	UserData averageValues = user.getAdditionalInformation(dataset);
    	SOP("Avg Values: " + averageValues + "\n");   	
    	
    	
    	
    	// Return the prediction and additional information to the GUI
    	Object[] results = {prediction, averageValues};
    	return results;
    }
    
    
    
    private static void SOP(Object o) {
    	System.out.println(o);
    }
    
    private static void SOP(String[] O) {
		for(String s : O) {
			System.out.print(s + ", ");
		}
		SOP("");
	}
}

