package heartscope_final.com.heartscope;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;



/**
 * @author Nicholas Farkash
 */

class ValidationTest {
	

	
	@Test
	void DataSplit80_20() {
		
		Dataset mother = new Dataset();

		String[] originalTestEntries = new String[]{
				"40,M,ATA,140,289,0,Normal,172,N,0,Up,0",
				"50,F,NAP,160,180,0,Normal,156,N,1,Flat,1",
				"38,M,NAP,130,211,0,Normal,142,N,0,Up,0",
				"63,F,ATA,120,204,0,Normal,145,N,0,Up,0",
				"41,M,ATA,130,283,0,ST,98,N,0,Up,0",
				"44,F,ASY,138,214,0,Normal,108,Y,1.5,Flat,1",
				"41,M,ATA,136,164,0,ST,99,Y,2,Flat,1",
				"51,F,ASY,140,234,0,Normal,140,Y,1,Flat,1",
				"56,M,NAP,150,195,0,Normal,122,N,0,Up,0",
				"52,F,NAP,120,339,0,Normal,170,N,0,Up,0",
		};
		 
		for(String e : originalTestEntries) {
			mother.add(new UserData(e));
		}
		
		// Create the expectation
		int expected_train = 8;
		int expected_test = 2;
		int[] expected = {expected_train, expected_test};
		
		// Create the actual
		Validation a = new Validation(mother);		
		
		int actual_train = a.getTrainingData().size();
		int actual_test = a.getTestingData().size();
		int[] actual = {actual_train, actual_test};
			
		// Assert that expected equals actual 	
		assertArrayEquals(expected, actual);	
	}
	
	
	
	
}
