package it.polito.tdp.genes.model;

public class GenePeso implements Comparable<GenePeso>{
	
	private Genes gene;
	private Double peso;
	
	public GenePeso(Genes gene, Double peso) {
		super();
		this.gene = gene;
		this.peso = peso;
	}

	public Genes getGene() {
		return gene;
	}

	public void setGene(Genes gene) {
		this.gene = gene;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return gene.getGeneId() + " " + peso;
	}

	@Override
	public int compareTo(GenePeso o) {
		return -(this.peso.compareTo(o.peso));
	}
	
	
}
