package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Nicholas Farkash
 */

public class Dataset extends ArrayList<UserData> {

	// Instance Variables
	private String[] headers = null;	// The names of attributes/features of the dataset
	private int numFeatures = UserData.getDESIRED_NUM_OUTPUT_INDICES();	// The number of features in the dataset
	private String filePath = null;		// The path to the .csv file the dataset imported
	private ArrayList<Number> featureMaxValues;	// ArrayList storing the max values for each feature
	private ArrayList<Number> featureMinValues;	// ArrayList storing the min values for each feature
	
	
	public Dataset() {
		/**
		 * Constructor of Dataset
		 */
		
		this.featureMaxValues = new ArrayList<Number>() {{
			for(int i=0; i<numFeatures; i++) {
				add(0f);
			}
		}};
		
		this.featureMinValues = new ArrayList<Number>() {{
			for(int i=0; i<numFeatures; i++) {
				add(0f);
			}
		}};

	}
	
	
	
	public Dataset(String file, boolean containsHeader) {
		/**
		 * Constructor of Dataset
		 * 
		 * @param file Path to the file containing the .csv file to import
		 * @param containsHeader Boolean flag for whether the .csv file has a header row
		 * 
		 */
		this.featureMaxValues = new UserData();
		this.featureMinValues = new UserData();
		
		int status = this.linkCSV(file);
		if(status == 0) this.importData(containsHeader);
		
	}
	
	
	
	public int linkCSV(String file) {
		/**
		 * Method to import the data from a .csv file
		 * 
		 * @param file Path to the file containing the .csv file to import
		 * @param containsHeader Boolean flag for whether the .csv file has a header row
		 * 
		 */
		
		// Ensure that the file exists
		File f = new File(file);
		if (!f.exists()) {
			SOP("File '" + file + "' not found. Dataset was not linked.");
			return -1;	// return -1 as an error key
		}
			
		
		
		// Ensure that the file is a .csv file
		String fileExtension = file.substring(file.length() - 4);
		if(!fileExtension.equals(".csv")) {
			SOP("File '" + file + "' is not a .csv file. Dataset was not linked.");
			return -2;	// return -2 as an error key
		}
		
		
		// Update the filePath and flag the Dataset as being linked to a CSV file
		this.filePath = file;
		
		// File successfully linked: return 0
		return 0;
		
	}	// importCSV
	
	
	
	public void importData(boolean containsHeader) {
		/**
		 * Method to import and format the data from the linked .csv file
		 * 
		 * @param containsHeader Boolean flag for whether the .csv file has a header row
		 *
		 */

			
		// Read through the CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			
			// Manage the header line
        	if(containsHeader) {
	            this.headers = br.readLine().split(",");
	            this.numFeatures = headers.length;
        	}

            // Create a UserData item for each entry in the original dataset and add it to this
        	String line;            
            while ((line = br.readLine()) != null) {

            	// Tokenize the dataset entry
                String[] rawUserDataValues = line.split(",");
                
                // Create a new UserData entry out of the original dataset entry and add it to the dataset
                UserData e = new UserData(rawUserDataValues);
                this.add(e);

            } // end of reading
            
            // Set the number of entries in the Database
        }
        catch (IOException e) {
            e.printStackTrace();
        }

	}	//importData
		
	
	
	public Dataset deepCopy() {
		/**
		 * Method to create a new Dataset object that is a copy of this one
		 */
		
		Dataset copyDataset = new Dataset();
		
		for(int i=0; i<this.size(); i++) {
			UserData toAdd = this.get(i);	
			copyDataset.add(toAdd);	
		}
		
		return copyDataset;
		
	}
	
	
	@Override
 	public String toString() {
		String s = "";
		
		for(UserData entry : this) {
			s += entry + "\n";
		}
		
		return s;
			
	}
	
	@Override
	public boolean add(UserData u) {
		if (super.add(u)) {
			return true;
		}
		return false;
	}
	
	public String getShape() {
		/**
		 * Method to get the dimensions of the Dataset
		 */
		
		return this.size() + ", " + this.numFeatures + "\n";
	}

		
	
	public int getNumFeatures() {
		return this.numFeatures ;
	}
	
	public ArrayList<Number> getFeatureMaxValues() {
		return this.featureMaxValues;
	}
	
	public ArrayList<Number> getFeatureMinValues() {
		return this.featureMinValues;
	}
		
	private static void SOP(Object O) {
		System.out.println(O);
	}
	
	private static void SOP(String[] O) {
		for(String s : O) {
			System.out.print(s + ", ");
		}
		SOP("\n");
	}

	
}


