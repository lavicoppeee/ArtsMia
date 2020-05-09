package it.polito.tdp.artsmia.model;

import java.util.*;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	private Graph<ArtObject,DefaultWeightedEdge> graph;
	private Map<Integer, ArtObject> idMap;
	
	public Model() {
	idMap=new HashMap<Integer,ArtObject>();
		
	}
	
	/**
	 * Cancello ed ogni volta ricreo il grafo
	 * risolve molti scazzi. 
	 * 
	 * Passo l'identity map al dao, così la riempie direttamente lui. 
	 * Per non creare doppioni
	 * 
	 */
	public void creaGrafo(){
		this.graph=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Lo faccio fare direttamente al dao mettendoli in un idMap
		ArtsmiaDAO dao=new ArtsmiaDAO();
		dao.listObjects(idMap);
		
		//aggiungo veritici
		Graphs.addAllVertices(this.graph, idMap.values());
		
		//aggiungo archi
		
		for(Adiacenza a: dao.getAdiacenza()) {
			if(a.getPeso()>0) {
				Graphs.addEdge(this.graph, idMap.get(a.getObj1()), idMap.get(a.getObj2()), a.getPeso());
			}
		}
	}
	
	public int nVertici() {
		return this.graph.vertexSet().size();
	}
	
	public int nArchi() {
		return this.graph.edgeSet().size();
	}
	
	
}
