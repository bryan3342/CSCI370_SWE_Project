package heartscope_final.com.heartscope;

import java.util.ArrayList;

/**
 * @author Nicholas Farkash
 */

public class UserData extends ArrayList<Number> {
	
	/**
	 * 
	 * 	User Input straight from the website/dataset
	 * -----------------------------------------------------------------------------------------------------------------------------------------------------------
	 * |  Attribute | Age | Sex | ChestPainType | RestingBP | Cholesterol | FastingBS | RestingECG | MaxHR | ExerciseAngina | Oldpeak | ST_Slope | HeartDisease |	
	 * -----------------------------------------------------------------------------------------------------------------------------------------------------------
	 * |     Index  |  0  |  1  |       2       |     3     |       4     |     5     |     6      |   7   |        8       |    9    |    10    |      11      |
	 * -----------------------------------------------------------------------------------------------------------------------------------------------------------
	 *
	 * 1) Sex: 0 - M, 1 - F
	 * 2) ChestPainType: 0 - ATA, 1 - NAP, 2 - ASY, 3 - TA
	 * 6) RestingECG: 0 - Normal, 1 - ST, 2 - LVH
	 * 8) ExerciseAngina: 0 - N, 1 - Y
	 * 10) ST_Slope: 0 - Down, 1 - Flat, 2 - Up
	 * 
	 * 
	 * 
	 * UserData, what this class represents
	 * --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * |  Attribute | Age | Sex | ChestPainATA | ChestPainNAP | ChestPainASY | ChestPainTA | RestingBP | Cholesterol | FastingBS | ECGNormal | ECGST | ECGLVH | MaxHR | ExerciseAngina | Oldpeak | ST_Down | ST_Flat | ST_Up | HeartDisease |	
	 * --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * |     Index  |  0  |  1  |       2      |       3      |       4      |      5      |     6     |      7      |     8     |     9     |   10  |   11   |   12  |       13       |    14   |    15   |   16    |   17  |      18      |
	 * --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 */
	
	
	
	// Instance variables
	protected int numIndices;	// Number of indices in an instance of UserData (length of this)
	private static int DESIRED_NUM_INPUT_INDICES = 12;	// Number of features in the incoming dataset
	private static int DESIRED_NUM_OUTPUT_INDICES = 19;	// Number of features in the UserData instance
	
	public UserData() {
		// Initialize the UserData with all 0 and return
		for(int index=0; index<DESIRED_NUM_OUTPUT_INDICES; index++) {
			this.add(0);
		}
		this.numIndices = DESIRED_NUM_OUTPUT_INDICES;
	}
	
	public UserData(String userInput) {
		/**
		 * Constructor of UserData
		 * 
		 * @param userInput A String containing the necessary information to create an entry in the dataset (an instance of UserData)
		 */

		// Split the String into a String[] and call the other constructor
		this(userInput.split(","));
	}
	
	
	
	public UserData(String[] userInput) {
		/**
		 * Constructor of UserData
		 * 
		 * @param userInput A String array containing the necessary information to create an entry in the dataset (an instance of UserData)
		 */
		// Set the number of indices to be this.DESIRED_NUM_INDICES
		this.numIndices = DESIRED_NUM_OUTPUT_INDICES;
		
		// If the userInput String[] is null or does not have enough entries...
		if(userInput == null || userInput.length != DESIRED_NUM_INPUT_INDICES) {
			
			// Initialize the UserData with all -1 sentinel and return
			for(int index=0; index<this.numIndices; index++) {
				this.add(null);
			}
			
			return;
		}
		
		// Otherwise, if the String[] is the correct length...
		else {

			// Initialize to be an ArrayList of all 0
			for(int index=0; index<this.numIndices; index++) {
				this.add(0f);
			}
			
			// Parse the String[] to extract the values 
			parseUserInput(userInput);
		}

	}

	
	
	private void parseUserInput(String[] userInput) {
		/**
		 * Method to extract the String values from the String[] and convert them to floating point numbers to store in this
		 * 
		 * @param userInput A String array containing the necessary information to create an entry in the dataset (an instance of UserData)
		 */
	
		// For each index of the userInput...
		for(int i=0; i<userInput.length; i++) {
					
        	// Store the value in variable d
        	String d = userInput[i].strip();

        	// Go to the corresponding index and convert the value
        	switch(i) {
        		
        	
        	
        		// Age (index 0)
        		case 0:
        			// Set the user's age to index 0
        			if(d.equals("")) {
        				this.set(0, 0);
        			}
        			else {
    	        		this.set(0, Integer.parseInt(d));
        			}
        			break;

        		
	        		
	        		
        		// Sex (index 1)
        		case 1:
    				// Set the user's sex to index 1
        			switch(d) {
						case "M": this.set(1, 0); break;
						case "F": this.set(1, 1); break;
						default: this.set(1,  0); break;
        			} break;
        			
        			
        			
        		// ChestPainType -> one hot encoded to CP_ATA (index 2), CP_NAP (index 3), CP_ASY (index 4), CP_TA (index 5)
        		case 2:
        			switch(d) {
        				case "ATA": 
        					this.set(2, 1);
        					this.set(3, 0);
        					this.set(4, 0);
        					this.set(5, 0);
        					break;
        					
        				case "NAP":
        					this.set(2, 0);
        					this.set(3, 1);
        					this.set(4, 0);
        					this.set(5, 0);
        					break;
        					
        				case "ASY":
        					this.set(2, 0);
        					this.set(3, 0);
        					this.set(4, 1);
        					this.set(5, 0);
        					break;
        					
        				case "TA":
        					this.set(2, 0);
        					this.set(3, 0);
        					this.set(4, 0);
        					this.set(5, 1);
        					break;
        				
    					default:
    						this.set(2, 1);
        					this.set(3, 0);
        					this.set(4, 0);
        					this.set(5, 0);
        					break;
    						
        			}break;
        		
        			
        			
        		// RestingBP (index 6) - handle null value 0 by setting entry to average non-zero value in dataset, which is 133
        		case 3:
        			// Set the user's RestingBP to index 6
        			switch(d) {
        				case "0":
        					this.set(6, 133);
        					break;
        					
        				default: 
        					if(d.equals("")) {
                				this.set(6, 133);
                			}
                			else {
                				this.set(6, Integer.parseInt(d));                			
                			}
                			break;    						
        			} break;
    			
        			
        			
    			// Cholesterol (index 7) - handle null value 0 by setting entry to average non-zero value in dataset, which is 245
        		case 4:
        			// Set the user's Cholesterol to index 7
        			switch(d) {
        				case "0":
        					this.set(7, 245);
        					break;
        					
        				default: 
        					if(d.equals("")) {
                				this.set(7, 245);
                			}
                			else {
                				this.set(7, Integer.parseInt(d));                			
                			}
                			break;    	
        			} break;
        			
        			
        			
        		// FastingBS (index 8)
        		case 5:
        			// Set the user's FastingBS to index 8
        			if(d.equals("")) {
        				this.set(8, 0);	// Default value 0
        			}
        			else {
        				this.set(8, Integer.parseInt(d));
        			}
        			break;
    			
	        		
	        		
        		// RestingECG -> one hot encoded to ECG_Normal (index 9), ECG_ST (index 10), ECG_LVH (index 11)
        		case 6:
        			switch(d) {
        				case "Normal":
        					this.set(9, 1); 
        					this.set(10, 0); 
        					this.set(11, 0); 
        					break;
        						
        				case "ST":
        					this.set(9, 0); 
        					this.set(10, 1); 
        					this.set(11, 0); 
        					break;
        					
        				case "LVH":
        					this.set(9, 0); 
        					this.set(10, 0); 
        					this.set(11, 1); 
        					break;
        					
        				default:
        					this.set(9, 1); 
        					this.set(10, 0); 
        					this.set(11, 0); 
        					break;
        					
        			} break;
        			
        			
        			
        		// MaxHR (index 12)
        		case 7:
        			// Set the user's MaxHR to index 12
        			if(d.equals("")) {
        				this.set(12, 137);	// Default value 137 - average value in dataset
        			}
        			else {
    	        		this.set(12, Integer.parseInt(d));
        			}
        			break;
	        	
	        		
	        	// ExerciseAngina (index 13)
        		case 8:
        			switch(d) {
						case "N": this.set(13, 0); break;
						case "Y": this.set(13, 1); break;
						default: this.set(13,  0); break;	// Default is mode value N
    			} break;
        		
    			
    			
    			// Oldpeak (index 14)
        		case 9:
        			// Set the user's Oldpeak to index 14
	        		if(d.equals("")) {
        				this.set(14, 0f);	// Default value 0
        			}
        			else {
    	        		this.set(14, Float.parseFloat(d));
        			}
        			break;
	        		
	        		
	        	// ST_Slope	-> one hot encoded to ST_Down (index 15), ST_Flat (index 16), ST_Up (index 17)
        		case 10:
        			switch(d) {
        				case "Down":
        					this.set(15, 1);
        					this.set(16, 0);
        					this.set(17, 0);
        					break;
        					
        				case "Flat":
           					this.set(15, 0);
        					this.set(16, 1);
        					this.set(17, 0);
        					break;
        					
        				case "Up":
          					this.set(15, 0);
        					this.set(16, 0);
        					this.set(17, 1);
        					break;
        					
        				default:	// default is mode value, Flat
        					this.set(15, 0);
        					this.set(16, 1);
        					this.set(17, 0);
        					break;
        					
        			} break;
        		
        			
        			
        		// HeartDisease (index 18)
        		case 11:
        			// Set the user's HeartDisease to index 18
	        		if(d.equals("")) {
        				this.set(18, 0);	// Default value 0
        			}
        			else {
    	        		this.set(18, Integer.parseInt(d));
        			}
        			break;
        			
        	} // end of switch

		}	// end of for-loop that formats a UserData

	}	// parseUserInput
	

	
	public UserData getAdditionalInformation(Dataset d) {
		/**
		 * Method to get additional information to provide for the user
		 * 
		 * @param d The dataset the UserData belongs to
		 */
		
		// Create a FilteredDataset around the parameters of the UserData
		FilteredDataset filteredDataset = new FilteredDataset(d, this);
    	
		// Return the average values
    	return filteredDataset.getComparisonValues();
	}
	
	
	
	public String[] returnUserData() {
		/**
		 * Method to return the UserData in the form of a String[]
		 */
		
		if(this.get(0) == null) return null;
		
		String[] s = new String[this.numIndices];
		
		for(int i=0; i<this.numIndices; i++) {
			s[i] = this.get(i).toString();
		}
		
		return s;
	}
	
	
	
	public int getLast() {
		return this.get(18).intValue();
	}
	
	@Override
	public String toString() {

		if(this.get(0) == null) return null;
		
		String s = "[";
		for(int index = 0; index<this.size(); index++) {
			
			if(this.get(index) == null){
				s += "NaN" + ", ";
			}
			else {
				s += this.get(index).toString() + ", ";
			}
		}
		
		s = s.substring(0, s.length()-2) + "]";
		
		return s;
		
	}
	
	public static UserData getNegs() {
		UserData nulled = new UserData();
		
		for(int i=0; i<DESIRED_NUM_OUTPUT_INDICES; i++) {
			nulled.set(i, -1);
		}
		return nulled;
	}
	
	public static int getDESIRED_NUM_OUTPUT_INDICES() {
		return DESIRED_NUM_OUTPUT_INDICES;
	}
	
	private static void SOP(Object O) {
		System.out.println(O);
	}

	@SuppressWarnings("unused")
	private static void SOP(String[] O) {
		for(String s : O) {
			System.out.print(s + ", ");
		}
		SOP("\n");
	}

	

	


	
}
