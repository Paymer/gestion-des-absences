package dev.entite.histogramme;

public class Set {

	private String label;
	private String backgroundColor;
	private String yAxisID;
	private int[] data;
	
	
	
	
	public Set() {
		super();

		this.yAxisID = "bar-y-axis";

	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getyAxisID() {
		return yAxisID;
	}
	public void setyAxisID(String yAxisID) {
		this.yAxisID = yAxisID;
	}
	public int[] getData() {
		return data;
	}
	public void setData(int[] data) {
		this.data = data;
	}
	
	
}
