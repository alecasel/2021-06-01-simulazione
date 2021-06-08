package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private SimpleWeightedGraph<Genes, DefaultWeightedEdge> graph;
	private GenesDao dao;
	private List<Genes> vertices; 
	private Map<String, Genes> verticesMap;
	
	public Model() {
		dao = new GenesDao();
	}
	
	public String createGraph() {
		
		graph = new SimpleWeightedGraph<Genes, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		vertices = dao.getEssentialGenes();
		Collections.sort(vertices);
		
		verticesMap = new HashMap<String, Genes>();
		for (Genes genes : vertices) {
			verticesMap.put(genes.getGeneId(), genes);
		}
		
		Graphs.addAllVertices(graph, vertices);
		
		List<Arco> edges = dao.getEdges();
		for (Arco arco : edges) {
			Graphs.addEdge(graph, verticesMap.get(arco.getVertex1()), verticesMap.get(arco.getVertex2()), arco.getWeight());
		}
		
		return String.format("Grafo creato con %d vertici e %d archi\n\n", vertices.size(), graph.edgeSet().size());
		
	}
	
	public List<Genes> getVertices() {
		return vertices;
	}
	
	public List<GenePeso> getGenesAdiacenti(Genes genes) {
		
		List<GenePeso> rslt = new ArrayList<GenePeso>();
		List<Genes> neighbors = Graphs.neighborListOf(graph, genes);
		
		for (Genes g : neighbors) { // Per tutti i vertici vicini al vertice-parametro
			DefaultWeightedEdge dwe = graph.getEdge(genes, g); // Prende l'arco corrispondente ai due vertici
			GenePeso gp = new GenePeso(g, graph.getEdgeWeight(dwe)); // crea oggetto GenePeso
			rslt.add(gp);
		}
		
		Collections.sort(rslt);
		
		/* Per ogni gene di neighbors, dovrei andare a prendere gli archi a cui appartiene (vertex1 oppure vertex2)
		 * e per quegli archi estrapolare il peso. Devo restituire un elenco di Genes con relativo peso => Map
		*/
		
		return rslt;
	}
	
	
}
