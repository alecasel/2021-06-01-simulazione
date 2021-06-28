package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulator {
	
	// -- coda degli eventi --
	private PriorityQueue<Event> queue;
	
	
	// -- modello del mondo: parte variabile, ciò che la simulazione fa evolvere --
	// Due possibilità: 
	
	// 1) dato un ing (0 ... n-1), dimmi su quale gene lavora. DA ING A GENE
	private List<Genes> geniStudiati;	// geneStudiato.get(nIng)
	
	// 2) dato un gene, dimmi quanti ing ci lavorano
//	Map<Genes, Integer> numIngPerGene;
	
	
	// -- parametri di input --
	private Genes startGene;
	private int numIng;
	private Graph<Genes, DefaultWeightedEdge> graph;
	
	private static int MESEMAX = 36;
	private static double MANTIENIGENE = 0.3; 
	
	
	// -- valori calcolati -- 
	// Li dedurremo da geniStudiati perché 
	/* Alla fine della simulazione identificare quali geni sono in corso di studio 
	 * ed il rispettivo numero di ingegneri ad essi associati. */
	
	
	public Simulator(Genes startGene, int numIng, Graph<Genes, DefaultWeightedEdge> graph) {
		
		this.startGene = startGene;
		this.numIng = numIng;
		this.graph = graph;
		
		if (graph.degreeOf(startGene)==0) {
			throw new IllegalArgumentException("Vertice di partenza isolato");
		}
		
		// -- inizializzo coda eventi --
		queue = new PriorityQueue<Event>();
		for (int i = 0; i < this.numIng; i++) {
			Event e = new Event(0, i);
			queue.add(e);
		}
		
		// -- inizializzo mondo --
		// creo array con numIng valori pari a startGene
		geniStudiati = new ArrayList<Genes>();
		for (int i = 0; i < this.numIng; i++) {
			geniStudiati.add(startGene);
		}
	}
	
	public void run() {
		
		while (! queue.isEmpty()) {
			Event e = queue.poll();
			
			int mese = e.getMese();
			int numIng = e.getNumIng();
			Genes genes = geniStudiati.get(numIng);
			
			// Devo controllare di non essere arrivato alla fine: mese ?= 36
			if (mese < MESEMAX) {
				// cosa studierà numIng al mese = mese +1 ?
				if (Math.random() < MANTIENIGENE) {
					// mantieni
					Event e1 = new Event(mese+1, numIng);
					queue.add(e1);
				} else {
					
					// CAMBIO GENE
					
					/*
					 * la probabilità di scegliere un certo gene dipende dal peso dell’arco 
					 * che lo connette con il gene precedente.
					 * se il gene precedente g1 è connesso a g2, g3 e g4, 
					 * la probabilità di spostarsi da g1 a g2 sarà data dal peso dell’arco g1-g2 
					 * diviso per la somma dei pesi degli archi g1-g2, g1-g3, g1-g4
					 */
					
					// -- sommatoria S dei pesi degli archi --
					double S = 0;
					for (DefaultWeightedEdge dwe : graph.edgesOf(genes)) { // tutti gli archi incidenti in genes
						S += graph.getEdgeWeight(dwe); // somma di tutti gli archi con vertice genes
					}
					
					// -- estrazione casuale numero tra 0 e S --
					double random = Math.random() * S;
					
					// -- confronto tra R e le somme parziali dei pesi --
					Genes geneAggiornato = null;
					double sum = 0.0;
					for (DefaultWeightedEdge dwe : graph.edgesOf(genes)) {
						
						sum += graph.getEdgeWeight(dwe); 
						
						/* la probabilità di scegliere un certo gene dipende dal peso dell’arco 
						 * che lo connette con il gene precedente */
						// Affinché ogni gene dipenda dal peso dell'arco che lo connette al gene precedente,
						// faccio una somma cumulativa di pesi di archi che connettono geni adiacenti tra loro.
						// Man mano che passo gli archi, aumento la probabilità di cambiare gene. 
						if (sum > random) {
							geneAggiornato = Graphs.getOppositeVertex(graph, dwe, genes);
							break;
						}
					}
					
					geniStudiati.set(numIng, geneAggiornato);
				}
			}
			
		}
	}
	
	public Map<Genes, Integer> getGeniStudiati() {
		
		Map<Genes, Integer> rslt = new HashMap<Genes, Integer>();
		 
		 for (int i = 0; i < this.numIng; i++) {
			Genes genes = geniStudiati.get(i);
			if (rslt.containsKey(genes)) {
				rslt.put(genes, rslt.get(genes)+1);
			} else {
				rslt.put(genes, 1);
			}
		}
		 
		 return rslt;
	}

}
