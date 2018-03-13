package sbc;

import java.awt.Color;

public class Vertex {
	
	private String URL;
	private String label;
	private Color color;
	
	public Vertex(String URL, String label, Color color) {
		this.URL=URL;
		this.label=label;
		this.color=color;
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
	public void setColor(Color color) {
		this.color=color;
	}
	public Color getColor() {
		return this.color;
	}
	
	public String toString() {
		return this.label;
	}

}