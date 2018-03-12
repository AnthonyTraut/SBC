package sbc;

public class Vertex {

	private String URL;
	private String label;
	private String boxType;
	
	public Vertex(String URL, String label, String boxType) {
		this.URL=URL;
		this.label=label;
		this.boxType=boxType;
	}
	
	public void setURL(String URL) {
		this.URL=URL;
	}
	public String getURL() {
		return this.URL;
	}
	public void setLabel(String label) {
		this.label=label;
	}
	public String getLabel() {
		return this.label;
	}
	public void setBoxType(String boxType) {
		this.boxType=boxType;
	}
	public String getBoxType() {
		return this.boxType;
	}
	
	public String toString() {
		return this.label;
	}
	
}