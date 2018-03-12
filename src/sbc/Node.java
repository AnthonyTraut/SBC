package sbc;

public class Node {
	
	private String label;
	
	public Node(String label) {
		this.label=label;
	}
	
	public void setLabel(String label) {
		this.label=label;
	}
	public String getLabel(String label) {
		return this.label;
	}
	
	public String toString() {
		return this.label;
	}

}
