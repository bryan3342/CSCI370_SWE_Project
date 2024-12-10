package code;

/**
 * @author Nicholas Farkash
 */

public class Node {

	protected int featureIndex;	// The index in UserData the node is splitting on
	protected float splitValue;	// The value the node is splitting on
	protected int classification;	// Boolean classification value (1 or 0) stored in leaf node
	protected boolean isLeaf;		// Boolean to determine whether the node is a leaf or not
	protected Node left;	// The node's left child
	protected Node right;	// The node's right child
	
	
	
	// Constructor for internal node
	public Node(int featureIndex, float splitValue) {
		this.featureIndex = featureIndex;
		this.splitValue = splitValue;
		this.isLeaf = false;
	}
	
	
	
	// Constructor for leaf node
	public Node(int classification, boolean isLeaf) {
		this.classification = classification;
		this.isLeaf = isLeaf;
	}
	
	
	@Override
	public String toString() {
		String s = "Feature Index: " + this.featureIndex + " : Split Value:" + this.splitValue + ",\n";
		return s;
	}
	
}
