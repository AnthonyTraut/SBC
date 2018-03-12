package sbc;

public class Vertex {

	private String url;
	private String label;
	
	public Vertex(String url, String label) {
		this.url=url;
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