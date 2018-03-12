package sbc;

public class Vertex {

	private String label;
	
	public Vertex(String label) {
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