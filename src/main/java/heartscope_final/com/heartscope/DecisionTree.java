package heartscope_final.com.heartscope;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Nicholas Farkash
 */

public class DecisionTree {
	
	protected Node root;	// root node of the tree
	private ArrayList<Node> nodeList;	// List of nodes in the tree
	private Dataset bootstrappedData;	// Bootstrapped data
	
	
	// Constructor for DecisionTree
	public DecisionTree(Dataset trainingData) {
		this.setNodeList(new ArrayList<Node>());
		
		this.setBootstrappedData(bootstrapData(trainingData));
		this.growTree(getBootstrappedData());
	}
	
	
	private Dataset bootstrapData(Dataset trainingData) {
		/**
		 * Method to create a new dataset to grow the DecisionTree with
		 */
		
		Random random = new Random();
		Dataset bootstrappedData = new Dataset();
		
		
		// Pick 50 random points to bootstrap the dataset with
		for(int i=0; i<50; i++) {
			int index = random.nextInt(trainingData.size());
			bootstrappedData.add(trainingData.get(index));
		}
		
		return bootstrappedData;	
	}
	
	
	
	public void growTree(Dataset bootstrappedData) {
		/**
		 * Method to grow a Tree - utilized helper function buildTree
		 */
		
		this.root = buildTree(bootstrappedData);
	}
	
	
	private Node buildTree(Dataset data){
		/**
		 * Helper Method for growTree
		 */
	
		// If the dataset is empty
	    if (data.size() == 0) {
	        return new Node(-1, true); // Create a default leaf node with an invalid class
	    }
		
		// Check to see if the data in the node is pure, meaning all one classification
		boolean isPure = true;
		int classificationOfFirstNode = data.get(0).getLast();
		for(UserData entry : data) {
			if(entry.getLast() != classificationOfFirstNode) {
				isPure = false;
				break;
			}
		}
		
		// If the data in this node is pure, it is a leaf node
		if(isPure) {
			Node leafNode = new Node(data.get(0).getLast(), true);
			return leafNode;
		}
		
		// Otherwise, split the node
		
		// Pick a random feature to split on
		Random random = new Random();
		int splitFeature = random.nextInt(data.get(0).size()-1);
		
		
		// Find the best value to split that feature on
		float sum = 0f;
		for(UserData entry : data) {
			sum += entry.get(splitFeature).doubleValue();
		}
		float splitValue = sum / data.size();
		
		// Create the node to split on
		Node curNode = new Node(splitFeature, splitValue);

		// Split the data
		Dataset leftData = new Dataset();
		Dataset rightData = new Dataset();
		
		for(UserData entry : data) {
			float value = entry.get(splitFeature).floatValue();
			
			if(value < splitValue) {
				leftData.add(entry);
			}
			else {
				rightData.add(entry);
			}
			
		}
		
		// Grow the tree recursively
		curNode.left = buildTree(leftData);
		curNode.right = buildTree(rightData);
		getNodeList().add(curNode);
		return curNode;
	
	}


	public int getClassification(UserData u) {
		/**
		 * Method to send a UserData through the DecisionTree and return the classification
		 * Achieved by traversing nodes until a leaf is reached
		 */
		
		Node curNode = this.root;
		
		while(!curNode.isLeaf) {
			
			// Get the value of the node for the index being split upon
			float value = u.get(curNode.featureIndex).floatValue();
			
			// Traverse to the correct child node
			if(value < curNode.splitValue) {
				curNode = curNode.left;
			}
			else {
				curNode = curNode.right;
			}
		}
		
		// Return that node's classification value
		return curNode.classification;
		
		
	}


	public Dataset getBootstrappedData() {
		return bootstrappedData;
	}


	public void setBootstrappedData(Dataset bootstrappedData) {
		this.bootstrappedData = bootstrappedData;
	}


	public ArrayList<Node> getNodeList() {
		return nodeList;
	}


	public void setNodeList(ArrayList<Node> nodeList) {
		this.nodeList = nodeList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
