package it.polito.tdp.genes.model;

public class Event implements Comparable<Event>{
	
	private int mese;
	private int numIng;
	
	public Event(int mese, int numIng) {
		super();
		this.mese = mese;
		this.numIng = numIng;
	}
	
	public int getMese() {
		return mese;
	}
	
	public int getNumIng() {
		return numIng;
	}

	@Override
	public int compareTo(Event o) {
		
		return this.mese - o.mese;
	}
	
	

}
