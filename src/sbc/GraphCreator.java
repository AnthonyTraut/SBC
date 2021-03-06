package sbc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.HashMap;

import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.Query;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class GraphCreator {

	private static String sparqlService  = "http://dbpedia.org/sparql";
	private static Graph<Vertex,Edge> graph = new DirectedSparseMultigraph<Vertex,Edge>();//DelegateTree<Vertex, Edge>();
	private static HashMap<String, Vertex> nodeMap = new HashMap<String, Vertex>();
		
	/**
	 * Permet de reset le graphe maintenu en memoire
	 */
	public static void resetGraph() {
		GraphCreator.graph = new DirectedSparseMultigraph<Vertex,Edge>();//DelegateTree<Vertex, Edge>();
		GraphCreator.nodeMap = new HashMap<String, Vertex>();
	}
	
	/**
	 * Getter du graphe
	 * @return
	 */
	public static Graph<Vertex,Edge> getGraph() {
		return GraphCreator.graph;
	}
	
	/**
	 * Execute une query
	 * @param query
	 * @return
	 */
	public static QueryExecution execQuery(Query query) {
		System.out.println(query);
		QueryExecution queryexec = QueryExecutionFactory.sparqlService(sparqlService, query);
		return queryexec;
	}
	
	/**
	 * 
	 * @param classe
	 * @param limit
	 * @param A1 : Recherche relation T-Box avec autres classes
	 * @param A2 : Recherche ancetres et descendants
	 * @param A3 : Recherche les predicats les plus instanciees
	 * @return
	 */
	public static JPanel createGraph_A(String classe, int limitA1, int limitA2, int limitA3, boolean A1, boolean A2, boolean A3) {
		GraphCreator.resetGraph();
		
		if (A2) {
			GraphCreator.createGraph_A2(classe, limitA2);
		}
		if (A1) {
			GraphCreator.createGraph_A1(classe, limitA1);
		}
		if (A3) {
			GraphCreator.createGraph_A3(classe, limitA3);
		}
		return GraphCreator.visualization();
	}
	
	/**
	 * Afficher les predicats theoriques d'une classe
	 * @param classe
	 * @param limit
	 */
	public static void createGraph_A1(String CLASS, int LIMIT) {
		String s_classe = CLASS.toString().split("/")[CLASS.toString().split("/").length-1];
		Vertex n_classeSource;
		if (GraphCreator.nodeMap.containsKey(CLASS)) {
			GraphCreator.nodeMap.get(CLASS).setColor(Color.BLUE);
			n_classeSource = GraphCreator.nodeMap.get(CLASS);
		}
		else {
			n_classeSource = new Vertex(CLASS, s_classe, Color.BLUE);
			GraphCreator.nodeMap.put(CLASS, n_classeSource);
		}
		
		// Afficher CLASS ?predicat ?class
		QueryExecution queryexec = GraphCreator.execQuery(RequestBuilder.A1_R1(CLASS, LIMIT));
		ResultSet res = queryexec.execSelect();
		while (res.hasNext()) {
			QuerySolution sol = res.next();
			
			// Un predicat en relation avec la classe source
			RDFNode pre = sol.get("predicat");
			String url_pre = pre.toString();
			String s_pre = pre.toString().split("/")[pre.toString().split("/").length-1];
			Edge predicat = new Edge(url_pre, s_pre, "T-Box");
			
			// La classe en relation via le predicat a la classe source
			RDFNode cla = sol.get("class");
			String url_cla = cla.toString();
			String s_cla = cla.toString().split("/")[cla.toString().split("/").length-1];
			Vertex n_cla;
			if (GraphCreator.nodeMap.containsKey(url_cla)) {
				n_cla = GraphCreator.nodeMap.get(url_cla);
			}
			else {
				n_cla = new Vertex(url_cla, s_cla, Color.ORANGE);
				GraphCreator.nodeMap.put(url_cla, n_cla);
				
			}
			
			// Verification que l'arc n'existe pas deja avant l'ajout
			boolean alreadyExist = false;
			if (GraphCreator.graph.containsVertex(n_classeSource) && GraphCreator.graph.containsVertex(n_cla)) {
				for(Edge e : GraphCreator.graph.findEdgeSet(n_classeSource, n_cla)) {
					if (e.getURL().equals(url_pre)) {
						alreadyExist=true;
						break;
					}
				}
			}
			if (!alreadyExist) {
				GraphCreator.graph.addEdge(predicat, n_classeSource, n_cla);
			}
		}
		
		// Afficher ?class ?predicat CLASS
		queryexec = GraphCreator.execQuery(RequestBuilder.A1_R2(CLASS, LIMIT));
		res = queryexec.execSelect();
		while (res.hasNext()) {
			QuerySolution sol = res.next();
			
			// Un predicat en relation avec la classe source
			RDFNode pre = sol.get("predicat");
			String url_pre = pre.toString();
			String s_pre = pre.toString().split("/")[pre.toString().split("/").length-1];
			Edge predicat = new Edge(url_pre, s_pre, "T-Box");
			
			// La classe en relation via le predicat a la classe source
			RDFNode cla = sol.get("class");
			String url_cla = cla.toString();
			String s_cla = cla.toString().split("/")[cla.toString().split("/").length-1];
			Vertex n_cla;
			if (GraphCreator.nodeMap.containsKey(url_cla)) {
				n_cla = GraphCreator.nodeMap.get(url_cla);
			}
			else {
				n_cla = new Vertex(url_cla, s_cla, Color.ORANGE);
				GraphCreator.nodeMap.put(url_cla, n_cla);
				
			}
			
			// Verification que l'arc n'existe pas deja avant l'ajout
			boolean alreadyExist = false;
			if (GraphCreator.graph.containsVertex(n_cla) && GraphCreator.graph.containsVertex(n_classeSource)) {
				for(Edge e : GraphCreator.graph.findEdgeSet(n_cla, n_classeSource)) {
					if (e.getURL().equals(url_pre)) {
						alreadyExist=true;
						break;
					}
				}
			}
			if (!alreadyExist) {
				GraphCreator.graph.addEdge(predicat, n_cla, n_classeSource);
			}
		}
		
		// Afficher ?predicat rdfs:domain CLASS .
		queryexec = GraphCreator.execQuery(RequestBuilder.A1_R3(CLASS, LIMIT));
		res = queryexec.execSelect();
		while (res.hasNext()) {
			QuerySolution sol = res.next();
			
			// rdfs:domain
			String url_domain = "http://www.w3.org/2000/01/rdf-schema#domain";
			Edge domain = new Edge(url_domain, "rdfs:domain", "T-Box");
			
			// Un predicat dont le domaine est CLASS
			RDFNode pre = sol.get("predicat");
			String url_pre = pre.toString();
			String s_pre = pre.toString().split("/")[pre.toString().split("/").length-1];
			Vertex n_pre;
			if (GraphCreator.nodeMap.containsKey(url_pre)) {
				n_pre = GraphCreator.nodeMap.get(url_pre);
			}
			else {
				n_pre = new Vertex(url_pre, s_pre, Color.GRAY);
				GraphCreator.nodeMap.put(url_pre, n_pre);
			}
			
			// Verification que l'arc n'existe pas deja avant l'ajout
			boolean alreadyExist = false;
			if (GraphCreator.graph.containsVertex(n_pre) && GraphCreator.graph.containsVertex(n_classeSource)) {
				for(Edge e : GraphCreator.graph.findEdgeSet(n_pre, n_classeSource)) {
					if (e.getURL().equals(url_domain)) {
						alreadyExist=true;
						break;
					}
				}
			}
			if (!alreadyExist) {
				GraphCreator.graph.addEdge(domain, n_pre, n_classeSource);
			}
		}
		
		// Afficher ?predicat rdfs:range CLASS .
		queryexec = GraphCreator.execQuery(RequestBuilder.A1_R4(CLASS, LIMIT));
		res = queryexec.execSelect();
		while (res.hasNext()) {
			QuerySolution sol = res.next();
			
			// rdfs:domain
			String url_range = "http://www.w3.org/2000/01/rdf-schema#range";
			Edge range = new Edge(url_range, "rdfs:range", "T-Box");
			
			// Un predicat dont le range est CLASS
			RDFNode pre = sol.get("predicat");
			String url_pre = pre.toString();
			String s_pre = pre.toString().split("/")[pre.toString().split("/").length-1];
			Vertex n_pre;
			if (GraphCreator.nodeMap.containsKey(url_pre)) {
				n_pre = GraphCreator.nodeMap.get(url_pre);
			}
			else {
				n_pre = new Vertex(url_pre, s_pre, Color.GRAY);
				GraphCreator.nodeMap.put(url_pre, n_pre);
			}
			
			// Verification que l'arc n'existe pas deja avant l'ajout
			boolean alreadyExist = false;
			if (GraphCreator.graph.containsVertex(n_pre) && GraphCreator.graph.containsVertex(n_classeSource)) {
				for(Edge e : GraphCreator.graph.findEdgeSet(n_pre, n_classeSource)) {
					if (e.getURL().equals(url_range)) {
						alreadyExist=true;
						break;
					}
				}
			}
			if (!alreadyExist) {
				GraphCreator.graph.addEdge(range, n_pre, n_classeSource);
			}
		}
	}

	/**
	 * Afficher la hierarchie d'une classe
	 * @param classe
	 * @param limit
	 */
	public static void createGraph_A2(String classe, int limit) {
		String url_subClassOf = "http://www.w3.org/2000/01/rdf-schema#subClassOf";
		String s_classe = classe.toString().split("/")[classe.toString().split("/").length-1];
		if (GraphCreator.nodeMap.containsKey(classe)) {
			GraphCreator.nodeMap.get(classe).setColor(Color.BLUE);
		}
		else {
			GraphCreator.nodeMap.put(classe, new Vertex(classe, s_classe, Color.BLUE));
		}
		
		// Afficher les classes parentes
		QueryExecution queryexec = GraphCreator.execQuery(RequestBuilder.A2_R1(classe, limit));
		ResultSet res = queryexec.execSelect();
		while (res.hasNext()) {
			QuerySolution sol = res.next();
			
			// Un ancetre de la classe source
			RDFNode parent = sol.get("parent");
			String url_parent = parent.toString();
			String s_parent = parent.toString().split("/")[parent.toString().split("/").length-1];
			Vertex n_parent;
			if (GraphCreator.nodeMap.containsKey(url_parent)) {
				n_parent = GraphCreator.nodeMap.get(url_parent);
			}
			else {
				n_parent = new Vertex(url_parent, s_parent, Color.RED);
				GraphCreator.nodeMap.put(url_parent, n_parent);
			}
			
			// Le pere de cet ancetre
			RDFNode superc = sol.get("super");
			String url_super = superc.toString();
			String s_super = superc.toString().split("/")[superc.toString().split("/").length-1];
			Vertex n_super;
			if (GraphCreator.nodeMap.containsKey(url_super)) {
				n_super = GraphCreator.nodeMap.get(url_super);
			}
			else {
				n_super = new Vertex(url_super, s_super, Color.RED);
				GraphCreator.nodeMap.put(url_super, n_super);
				
			}
			
			// Verification que l'arc n'existe pas deja avant l'ajout
			boolean alreadyExist = false;
			if (GraphCreator.graph.containsVertex(n_parent) && GraphCreator.graph.containsVertex(n_super)) {
				for(Edge e : GraphCreator.graph.findEdgeSet(n_parent, n_super)) {
					if (e.getURL().equals(url_subClassOf)) {
						alreadyExist=true;
						break;
					}
				}
			}
			if (!alreadyExist) {			
				GraphCreator.graph.addEdge(new Edge(url_subClassOf, "rdf-schema#subClassOf", "subClassOf"), n_parent, n_super);
			}
		}
		
		// Afficher les classes filles
		queryexec = GraphCreator.execQuery(RequestBuilder.A2_R2(classe, limit));
		res = queryexec.execSelect();
		while (res.hasNext()) {
			QuerySolution sol = res.next();
			
			// Un descendant de la classe source
			RDFNode sub = sol.get("sub");
			String url_sub = sub.toString();
			String s_sub = sub.toString().split("/")[sub.toString().split("/").length-1];
			Vertex n_sub;			
			if (GraphCreator.nodeMap.containsKey(url_sub)) {
				n_sub = GraphCreator.nodeMap.get(url_sub);
			}
			else {
				n_sub = new Vertex(url_sub, s_sub, Color.GREEN);
				GraphCreator.nodeMap.put(url_sub, n_sub);
			}
			
			// Le pere de cet ancetre
			RDFNode superc = sol.get("super");
			String url_super = superc.toString();
			String s_super = superc.toString().split("/")[superc.toString().split("/").length-1];
			Vertex n_super;
			if (GraphCreator.nodeMap.containsKey(url_super)) {
				n_super = GraphCreator.nodeMap.get(url_super);
			}
			else {
				n_super = new Vertex(url_super, s_super, Color.RED);
				GraphCreator.nodeMap.put(url_super, n_super);
				
			}
			
			// Verification que l'arc n'existe pas deja avant l'ajout
			boolean alreadyExist = false;
			if (GraphCreator.graph.containsVertex(n_sub) && GraphCreator.graph.containsVertex(n_super)) {
				for(Edge e : GraphCreator.graph.findEdgeSet(n_sub, n_super)) {
					if (e.getURL().equals(url_subClassOf)) {
						alreadyExist=true;
						break;
					}
				}
			}
			if (!alreadyExist) {			
				GraphCreator.graph.addEdge(new Edge(url_subClassOf, "rdf-schema#subClassOf", "subClassOf"), n_sub, n_super);
			}
		}
	}
	
	/**
	 * Afficher les predicats instancies d'une classe
	 * @param classe
	 * @param limit
	 */
	public static void createGraph_A3(String classe, int limit) {
		String s_classe = classe.toString().split("/")[classe.toString().split("/").length-1];
		Vertex n_classe;
		if (GraphCreator.nodeMap.containsKey(classe)) {
			GraphCreator.nodeMap.get(classe).setColor(Color.BLUE);
			n_classe = GraphCreator.nodeMap.get(classe);
		}
		else {
			n_classe = new Vertex(classe, s_classe, Color.BLUE);
			GraphCreator.nodeMap.put(classe, n_classe);
		}
		
		// Afficher les predicats de la A-Box les plus instancies par des instances de la classe source
		QueryExecution queryexec = GraphCreator.execQuery(RequestBuilder.A3(classe, limit));
		ResultSet res = queryexec.execSelect();
		while (res.hasNext()) {
			QuerySolution sol = res.next();
			
			// Le nombre d'instances qui instancie le predicat
			RDFNode count = sol.get("count");
			String temp = count.toString();
			String s_count = "";
			int i = 0 ;
			while(i<temp.length()-1 && Character.isDigit(temp.charAt(i))) {
				s_count = s_count + temp.charAt(i);
				i++;
			}
			Edge n_count = new Edge("", s_count,"A-Box");
			
			// Le predicat instancie
			RDFNode pre = sol.get("predicat");
			String url_pre = pre.toString();
			String s_pre = pre.toString().split("/")[pre.toString().split("/").length-1];
			Vertex predicat = new Vertex(url_pre, s_pre, Color.CYAN);
			
			GraphCreator.graph.addEdge(n_count, n_classe, predicat);
		}
	}
	
	/**
	 * Transformer le graphe en JPanel
	 * @return
	 */
	public static JPanel visualization() {
		Layout<Vertex,Edge> layout = new /*CircleLayout*//*SpringLayout*/FRLayout<Vertex, Edge>(GraphCreator.graph);
		//Layout<Vertex,Edge> layout = new TreeLayout<Vertex, Edge>((DelegateTree) GraphCreator.graph);
		BasicVisualizationServer<Vertex,Edge> vv = new BasicVisualizationServer<Vertex,Edge>(layout);

		Transformer<Vertex,Paint> nodePaint = new Transformer<Vertex,Paint>() {
			public Paint transform(Vertex vertex) {
				return vertex.getColor();
			}
		};
		
		Transformer<Edge, Stroke> vertexPaint = new Transformer<Edge, Stroke>() {
			public Stroke transform(Edge edge) {
				float dash[] = {10.0f};
				if (edge.getBoxType().equals("T-Box")) {
				}
				if (edge.getBoxType().equals("subClassOf")) {
					return new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				}
				return new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
			}
		};
		
		vv.getRenderContext().setVertexFillPaintTransformer(nodePaint);
		vv.getRenderContext().setEdgeStrokeTransformer(vertexPaint);
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<Edge>());
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Vertex>());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

		JPanel panel = new JPanel();
		panel.add(vv);
		return panel;
	}
}
