package heartscope_final.com.heartscope;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;



/**
 * @author Nicholas Farkash
 */

class RandomForestTest {


	
	@Test
	void _1AddTreeTest() {

		Dataset mother = new Dataset();

		String[] originalTestEntries = new String[]{
				"40,M,ATA,140,289,0,Normal,172,N,0,Up,0",
				"50,F,NAP,160,180,0,Normal,156,N,1,Flat,1",
				"38,M,NAP,130,211,0,Normal,142,N,0,Up,0",
				"63,F,ATA,120,204,0,Normal,145,N,0,Up,0",
				"41,M,ATA,130,283,0,ST,98,N,0,Up,0",
		};
		 
		for(String e : originalTestEntries) {
			mother.add(new UserData(e));
		}
		
		DecisionTree treeToAdd = new DecisionTree(mother);
		
		// Create the expectation
		RandomForest rf = new RandomForest(new Validation(new Dataset()));
		ArrayList<DecisionTree> expected = new ArrayList<DecisionTree>(Arrays.asList(treeToAdd));
		
		// Create the actual
		rf.addTree(treeToAdd);
		ArrayList<DecisionTree> actual = rf.getDecisionTreeList();
		
		// Assert that expected equals actual 	
		assertEquals(expected, actual);
	}
	
	
	@Test
	void _2AggregatePredictionMajorityTest() {

		ArrayList<Integer> preds = new ArrayList<Integer>(Arrays.asList(0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0));
		
		RandomForest rf = new RandomForest(new Validation(new Dataset()));
		rf.setDecisionTreePreds(preds);
		
		// Create the expectation
		int expected = 0;
		
		// Create the actual
		int actual = rf.aggregatePredictions();
		
		// Assert that expected equals actual 	
		assertEquals(expected, actual);
	}
	
	
	
	
	@Test
	void _3AggregatePredictionBimodalTest() {

		ArrayList<Integer> preds = new ArrayList<Integer>(Arrays.asList(0, 0, 1, 0, 1, 0, 1, 1, 0, 1));
		
		RandomForest rf = new RandomForest(new Validation(new Dataset()));
		rf.setDecisionTreePreds(preds);
		
		// Create the expectation
		int expected = 1;
		int actual = rf.aggregatePredictions();
		
		// Assert that expected equals actual 	
		assertEquals(expected, actual);
	}
	
	
	@Test
	void _4MeasureValidationTimeAndAccuracy() {

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
		
		long before = System.currentTimeMillis();

		Validation v = new Validation(mother);
		RandomForest rf = new RandomForest(v);

		@SuppressWarnings("unused")
		float error = rf.calculateInSampleError();
		
		long after = System.currentTimeMillis();		
        long duration = after - before;
        
        System.out.print("Before: " + before);
        System.out.print("\nAfter: " + after);

        System.out.print("\nDuration: " + duration);
        
		// Calculate the average time to validate one datapoint
		double timePerSingle = duration / (double) v.getTestingData().size();
		System.out.print("ms\nTime to validate one point: " + timePerSingle/1000 + "s");
		
		assertTrue(timePerSingle < 10*1000);
		
		
		
		
	}
		

}
