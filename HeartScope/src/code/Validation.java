package code;

import java.util.ArrayList;

/**
 * @author Nicholas Farkash
 */

public class Validation{

	private Dataset trainingData;		// Dataset storing the training dataset
	private Dataset testingData;		// Dataset storing each testing dataset
	

	
	public Validation(Dataset motherDataset) {
		/**
		 * Constructor for ValidationData
		 * 
		 * @param motherDataset The dataset this was created from
		 */
		
		// Initialize instance variables
		setTrainingData(new Dataset());
		setTestingData(new Dataset());
			
		// Split the data into an 80-20 split
		splitData(motherDataset);
	}
	
	
	private void splitData(Dataset motherDataset) {
		/**
		 * Method to split the dataset into 80% training and 20% validation
		 */
		
		Dataset copy = motherDataset.deepCopy();
		
		int numTotalTrainingPoints = (int) Math.floor(copy.size() * .8);
		
		// Pick numTotalTrainingPoints datapoints and move them to training Data
		for(int i=0; i < numTotalTrainingPoints; i++) {
			
			// Pick a random number between 0 and copy.size()
			int n = (int) Math.floor(Math.random() * (copy.size()-1));
			
			// Remove that data point from copy and add it to training
			UserData moveThis = copy.remove(n);
			this.getTrainingData().add(moveThis);
		}
				
		// The remaining points are the testing dataset
		this.setTestingData(copy);
		
	}	// splitData()
	

	
	private static void SOP(Object o) {
    	System.out.println(o + "\n");
    }
	
	private static void SOP(ArrayList<Dataset> o) {		
		for(Dataset d : o) {
			SOP(d);
		}
    }


	public Dataset getTestingData() {
		return testingData;
	}


	public void setTestingData(Dataset testingData) {
		this.testingData = testingData;
	}


	public Dataset getTrainingData() {
		return trainingData;
	}


	public void setTrainingData(Dataset trainingData) {
		this.trainingData = trainingData;
	}
	
}
