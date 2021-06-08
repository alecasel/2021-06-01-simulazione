package it.polito.tdp.genes.model;

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
		
		verticesMap = new HashMap<String, Genes>();
		for (Genes genes : vertices) {
			verticesMap.put(genes.getGeneId(), genes);
		}
		
		Graphs.addAllVertices(graph, vertices);
		
		List<Arco> edges = dao.getEdges();
		for (Arco arco : edges) {
			Graphs.addEdge(graph, verticesMap.get(arco.getVertex1()), verticesMap.get(arco.getVertex2()), arco.getWeight());
		}
		
		return String.format("Grafo creato con %d vertici e %d archi\n", vertices.size(), graph.edgeSet().size());
		
	}
	
	public List<Genes> getVertices() {
		return vertices;
	}
}
