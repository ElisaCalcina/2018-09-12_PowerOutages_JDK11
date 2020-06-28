package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {
	
	 PowerOutagesDAO dao;
	 Graph<Nerc, DefaultWeightedEdge> grafo;
	 Map<Integer, Nerc> idMap;
	 
	
	public Model() {
		dao= new PowerOutagesDAO();
		idMap= new HashMap<>();
	}

	public void creaGrafo() {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.loadAllNercs(idMap);
		
		Graphs.addAllVertices(grafo, idMap.values());
		for(Archi a: dao.getArchi(idMap)) {
			if(a.getN1()!=a.getN2() && this.grafo.containsVertex(a.getN1()) && this.grafo.containsVertex(a.getN2())) {
				Graphs.addEdgeWithVertices(grafo, a.getN1(), a.getN2(), a.getPeso());
			}
		}
		
		
		System.out.println("Grafo creato con "+ this.grafo.vertexSet().size()+"vertici e con "+ this.grafo.edgeSet().size()+"archi\n");
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public Graph<Nerc,DefaultWeightedEdge> grafo(){
		return this.grafo;
	}
	
	public List<Archi> elencoArchi(Nerc n){
		List<Archi> result = new ArrayList<>();
		
		List<Nerc> vicini= Graphs.neighborListOf(grafo, n);
		
		for(Nerc ne: vicini) {
			Double peso= this.grafo.getEdgeWeight(this.grafo.getEdge(n, ne));
			result.add(new Archi(n, ne, peso));
		}
		Collections.sort(result);
		return result;
	}
}
