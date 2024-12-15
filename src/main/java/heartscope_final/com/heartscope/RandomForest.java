package heartscope_final.com.heartscope;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Nicholas Farkash
 */

public class RandomForest {

	private ArrayList<DecisionTree> decisionTreeList;	// Stores decision trees that make up the RF
	private ArrayList<Integer> decisionTreePreds;	// Stores the predictions from each DecisionTree in the RF
	protected Dataset trainingData;	// Training data used
	protected Dataset testingData;	// Testing data used
	

	
	public RandomForest(Validation data) {
		/**
		 * Constructor for RandomForest
		 */
		
		
		this.trainingData = data.getTrainingData();
		this.testingData = data.getTestingData();
		this.setDecisionTreeList(new ArrayList<DecisionTree>());
		this.setDecisionTreePreds(new ArrayList<Integer>());
		
		if(this.trainingData.isEmpty() || this.testingData.isEmpty()) {
			return;
		}
		
		// Populate the RandomForest
		createTrees(this.trainingData);
	}
	
	
	
	private void createTrees(Dataset data) {
		/**
		 * Method to create 50 DecisionTrees
		 */
		
		for(int i=0; i<50; i++) {
			DecisionTree tree = new DecisionTree(data);
			this.addTree(tree);
		}
	}
	
	public void addTree(DecisionTree tree) {
		this.getDecisionTreeList().add(tree);
	}
	
	public int predict(UserData u) {
		/**
		 * Method to make a prediction by passing the UserData through each DecisionTree in the RF
		 */
		
		
		// Ensure the decisionTreePreds ArrayList is empty
		getDecisionTreePreds().clear();
		
		// Get a prediction from each DT and store it in decisionTreePreds
		for(DecisionTree tree : this.getDecisionTreeList()) {
			int prediction = tree.getClassification(u);
			getDecisionTreePreds().add(prediction);
		}
		
		// Return whichever binary classification appears most
		return aggregatePredictions();
	}
	
	
	
	public int aggregatePredictions() {
		/**
		 * Method to return the value in the predictions array that appears most often
		 */
		
		// Count the freuency with which each binary result appears in decisionTreePreds
		int num0 = Collections.frequency(this.getDecisionTreePreds(), 0);
		int num1 = Collections.frequency(this.getDecisionTreePreds(), 1);
		
		// Return the one that appears most often
		if(num0 > num1) return 0;
		else return 1;
	}
	
	
	public int aggregatePredictions(ArrayList<Integer> preds) {
		/**
		 * Method to return the value in the predictions array that appears most often
		 */
		
		// Count the freuency with which each binary result appears in decisionTreePreds
		int num0 = Collections.frequency(preds, 0);
		int num1 = Collections.frequency(preds, 1);
		
		// Return the one that appears most often
		if(num0 > num1) return 0;
		else return 1;
	}
	
	
	
	public float calculateInSampleError() {
		/**
		 * Method to calculate the in-sample error of the RandomForest
		 */
		
		// Store the number of wrong predictions to calculate error
		int wrongPredictions = 0;
	
		// Loop through all entries in the training data...
		for(UserData entry : this.trainingData) {
			
			// Get the actual and predicted labels
			int actual = entry.getLast();
			int prediction = predict(entry);
			
			// If they don't match, increase the number of wrong predictions
			if(actual != prediction) {
				wrongPredictions++;
			}	
		}
		
		// Calculate and return error percentage, wrongPredictions / training_data/size
		float errorPercent = (float) wrongPredictions / this.trainingData.size();
		return errorPercent;
	}



	public ArrayList<Integer> getDecisionTreePreds() {
		return decisionTreePreds;
	}



	public void setDecisionTreePreds(ArrayList<Integer> decisionTreePreds) {
		this.decisionTreePreds = decisionTreePreds;
	}



	public ArrayList<DecisionTree> getDecisionTreeList() {
		return decisionTreeList;
	}



	public void setDecisionTreeList(ArrayList<DecisionTree> decisionTreeList) {
		this.decisionTreeList = decisionTreeList;
	}
	
	
	
	
}
