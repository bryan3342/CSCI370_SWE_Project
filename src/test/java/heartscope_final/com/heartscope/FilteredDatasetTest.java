package heartscope_final.com.heartscope;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;



/**
 * @author Nicholas Farkash
 */

class FilteredDatasetTest {
	
	@Test
	void _1FilterAgeAndSexMatchTest() {
		Dataset originalDataset = new Dataset();

		String[] originalTestEntries = new String[]{
				"44,M,ATA,140,289,0,Normal,172,N,0,Up,0",
				"46,M,ATA,130,283,0,ST,98,N,0,Up,0",
				"48,M,NAP,150,195,0,Normal,122,N,0,Up,0",
				"50,M,ASY,140,207,0,Normal,130,Y,1.5,Flat,1",
				"45,F,NAP,160,180,0,Normal,156,N,1,Flat,1",
				"47,F,ASY,138,214,0,Normal,108,Y,1.5,Flat,1",
				"49,F,NAP,120,339,0,Normal,170,N,0,Up,0",
		};
		 
		for(String e : originalTestEntries) {
			originalDataset.add(new UserData(e));
		}
		 
		
		// Create the expectation
		UserData testUser = new UserData("47,M,ASY,138,214,0,Normal,108,Y,1.5,Flat,1");
		Dataset expectedFilteredDataset = new Dataset();
		 
		
		String[] expectedTestEntries = new String[]{
				"46,M,ATA,130,283,0,ST,98,N,0,Up,0",
				"48,M,NAP,150,195,0,Normal,122,N,0,Up,0",
		};
		 
		for(String e : expectedTestEntries) {
			expectedFilteredDataset.add(new UserData(e));
		}
		 
		
		
		// Create the actual
		FilteredDataset actualFilteredDataset = new FilteredDataset(originalDataset, testUser);
		System.out.print(actualFilteredDataset);
	   	// Assert that expected equals actual 	
		assertEquals(expectedFilteredDataset, actualFilteredDataset);
		
	}
	
	
	@Test
	void _2FilterAgeAndSexTestNoMatches() {
		Dataset originalDataset = new Dataset();

		String[] originalTestEntries = new String[]{
				"44,M,ATA,140,289,0,Normal,172,N,0,Up,0",
				"46,M,ATA,130,283,0,ST,98,N,0,Up,0",
				"48,M,NAP,150,195,0,Normal,122,N,0,Up,0",
				"50,M,ASY,140,207,0,Normal,130,Y,1.5,Flat,1",
				"45,F,NAP,160,180,0,Normal,156,N,1,Flat,1",
				"47,F,ASY,138,214,0,Normal,108,Y,1.5,Flat,1",
				"49,F,NAP,120,339,0,Normal,170,N,0,Up,0",
		};
		 
		for(String e : originalTestEntries) {
			originalDataset.add(new UserData(e));
		}
		 
		
		
		// Create the expectation
		UserData testUser = new UserData("80,M,ASY,138,214,0,Normal,108,Y,1.5,Flat,1");
		Dataset expectedFilteredDataset = new Dataset();
		
		String[] expectedTestEntries = new String[]{
				"44,M,ATA,140,289,0,Normal,172,N,0,Up,0",
				"46,M,ATA,130,283,0,ST,98,N,0,Up,0",
				"48,M,NAP,150,195,0,Normal,122,N,0,Up,0",
				"50,M,ASY,140,207,0,Normal,130,Y,1.5,Flat,1"
		};
		 
		for(String e : expectedTestEntries) {
			expectedFilteredDataset.add(new UserData(e));
		}
		 
		
		
		// Create the actual
		FilteredDataset actualFilteredDataset = new FilteredDataset(originalDataset, testUser);
		System.out.print(expectedFilteredDataset);
   		// Assert that expected equals actual 	
		assertEquals(expectedFilteredDataset, actualFilteredDataset);
	}
	
	
	@Test
	void _3GetComparisonValuesMatchTest() {
		
		FilteredDataset filteredDataset = new FilteredDataset();

		String[] originalTestEntries = new String[]{
				"44,M,ATA,140,289,0,Normal,172,N,0,Up,0",
				"46,M,ATA,130,283,0,ST,98,N,0,Up,0",
				"48,M,NAP,150,195,0,Normal,122,N,0,Up,0",
				"50,M,ASY,140,207,0,Normal,130,Y,1.5,Flat,1",
		};
		 
		for(String e : originalTestEntries) {
			filteredDataset.add(new UserData(e));
		}
				 		 
		
		
		// Create the expectation	 
		ArrayList<Number> expected = new ArrayList<Number>() {{
			add(47);
			add(0);
			add(1);
			add(0);
			add(0);
			add(0);
			add(140);
			add(243);
			add(0);
			add(1);
			add(0);
			add(0);
			add(130);
			add(0);
			add(0.375f);
			add(0);
			add(0);
			add(1);
			add(0);
		}};
		
		
		// Create the actual
		UserData actual = filteredDataset.getComparisonValues();
		 
		System.out.print(actual);
	   	// Assert that expected equals actual 	
		assertEquals(expected, actual);
	}
	
	
	
	@Test
	void _4GetComparisonValuesNoMatchTest() {
		FilteredDataset filteredDataset = new FilteredDataset();

		// Create the expectation	 
		ArrayList<Number> expected = new ArrayList<Number>() {{
			add(-1);
			add(-1);
			add(-1);
			add(-1);
			add(-1);
			add(-1);
			add(-1);
			add(-1);
			add(-1);
			add(-1);
			add(-1);
			add(-1);
			add(-1);
			add(-1);
			add(-1);
			add(-1);
			add(-1);
			add(-1);
			add(-1);
		}};
				
		// Create the actual
		UserData actual = filteredDataset.getComparisonValues();

		System.out.println(actual);
		// Assert that expected equals actual 	
		assertEquals(expected, actual);
	}
	
	

}
