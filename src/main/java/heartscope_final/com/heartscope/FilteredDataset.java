package heartscope_final.com.heartscope;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Nicholas Farkash
 */
public class FilteredDataset extends ArrayList<UserData> {

	UserData userFilteredAround;
	
	public FilteredDataset() {
		/**
		 * Empty constructor for FilteredDataset
		 */
	}
	
	public FilteredDataset(Dataset originalDataset, UserData user) {
		/**
		 * Constructor for FilteredDataset
		 */
		
		this.userFilteredAround = user; 
				
		// Copy the contents from the originalDataset to here
		for(UserData entry : originalDataset) {
			this.add(entry);
		}
		
		// Filter the dataset
		filterAgeAndSex(user);
	}
	
	public boolean filterAgeAndSex(UserData curUser) {
		/**
		 * Method to return a new, modified Databse where all entires are of the same sex of a given user and within a 2 year age span
		 * Returns true if age filtering is applied, false otherwise
		 * 
		 * @param curUser The user whose age and sex are being filtered by
		 */
		
		// Create ArrayLists to store the entries to remove
		ArrayList<UserData> wrongSex = new ArrayList<UserData>();
		ArrayList<UserData> rightSexWrongAge = new ArrayList<UserData>();
		ArrayList<UserData> rightSexAndAge = new ArrayList<UserData>();

		
		// Get the current user's age and sex
		float curUserAge = curUser.get(0).floatValue();
		float curUserSex = curUser.get(1).floatValue();
				
		// Iterate through all entries in the Dataset 
		for(UserData dataEntry : this) {
						
			// Extract the entry's age and sex
			float age = dataEntry.get(0).floatValue();
			float sex = dataEntry.get(1).floatValue();
						
			// Compare the entry's data to curUser's data
			boolean validAge = Math.abs(curUserAge - age) <= 2.0; 
			boolean validSex = curUserSex == sex;
			
			// If the sex does not match, add the entry to wrongSex
			if(!validSex) {
				wrongSex.add(dataEntry);
			}
			// If the sex do match but the age does not, add the entry to rightSexWrongAge
			else if(!validAge) {
				rightSexWrongAge.add(dataEntry);
			}
			// If the both the sex and age match, add the entry to rightSexAndAge
			else {
				rightSexAndAge.add(dataEntry);
			}
	    }
		
		// Remove all entries with the wrong sex
		for(UserData entry : wrongSex){
			this.remove(entry);
		}
		
		// Remove all entries with the wrong age. However, if that results in removing everything, then remove nothing
		
		// If the dataset is the same size as what we want to remove, remove nothing and just return false to signal no age filter applied
		if(this.size() == rightSexWrongAge.size()) {
			return false;
		}
		
		// Otherwise, remove the entries with the wrong age
		else {
			for(UserData entry : rightSexWrongAge){
				this.remove(entry);
			}
			return true;
		}
		
		
		
			    
	}	// filterAgeAndSex
	
	
	public UserData getComparisonValues() {
		/**
		 * Method to get the average values for each feature in a Dataset
		 */
		
		// If there are no entries in the database, return a UserData with null values
		if(this.size() == 0) {
			return UserData.getNegs();
		}
		
		// Create a UserData to store the calculated average values
		UserData avgValues = new UserData();
		
		// Manage the one-hot encoded categories
		int[] chestPainIndices = {2, 3, 4, 5};	// Indices for ChestPain
		int[] ecgIndices = {9, 10, 11};			// Indices for RestingECG
		int[] stIndices = {15, 16, 17};			// Indices for ST_Slope
		ArrayList<Integer> allIndices = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 5, 9, 10, 11, 15, 16, 17));
		
		// Add all the indices arrays to one ArrayList
		ArrayList<int[]> intArraysForEachOneHotEncoded = new ArrayList<int[]>();
		intArraysForEachOneHotEncoded.add(chestPainIndices);
		intArraysForEachOneHotEncoded.add(ecgIndices);
		intArraysForEachOneHotEncoded.add(stIndices);
		
		
		// Avg over each feature
		for(int index=0; index<UserData.getDESIRED_NUM_OUTPUT_INDICES(); index++) {
			
			float sum = 0.0f;
			for(UserData entry : this) {
				sum += entry.get(index).floatValue();
			}
			
			// Handle float vs int representation
			Number avg = sum/this.size();
			if(!(index == 14 || allIndices.contains(index))) {	// want float value for one hot and oldpeak, int value for everything else
				avg = avg.intValue();
			}
			avgValues.set(index, avg);
			
		}

		// One-hot encoded section
		
		// For each indexList
		for(int[] indexList : intArraysForEachOneHotEncoded) {
			
			// Initialize the max value and max value index
			float max = avgValues.get(indexList[0]).floatValue();
			int maxIndex = indexList[0];

			// For each entry in the indexList, find the index of the highest value
			for(int index : indexList) {
				float curVal = avgValues.get(index).floatValue();		// Get the value at that index
				
				// If curValue is higher than the max, update
				if(curVal > max) {
					max = curVal;		// Update the max value
					maxIndex = index;	// Update the max index
				}
			}
			
			for(int index : indexList) {
				if(index == maxIndex) {
					avgValues.set(maxIndex, 1);
				}
				else {
					avgValues.set(index, 0);
				}
			}
		}
		
		return avgValues;	

	}	// getAvgValues
	
	
}
