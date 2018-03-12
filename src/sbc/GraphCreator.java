package sbc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.Scanner;

import javax.sound.midi.Synthesizer;
import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.Query;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import javafx.scene.shape.Line;

public class GraphCreator {

	private static String sparqlService  = "http://dbpedia.org/sparql";
	public GraphCreator() {
		
	}
	
	public static QueryExecution execQuery(Query query) {
		QueryExecution queryexec = QueryExecutionFactory.sparqlService(sparqlService, query);
		return queryexec;
	}
	
	/**
	 * Afficher les relations théoriques d’une classe
	 * @param classe
	 * @param limit
	 * @return
	 */
	public static JPanel createGraph_A1(String classe, int limit) {
		Graph<Node,Vertex> graph = new DirectedSparseMultigraph<Node,Vertex>();
		HashMap<String, Node> nodeMap = new HashMap<String, Node>();
		String string_classe = classe.toString().split("/")[classe.toString().split("/").length-1];
		nodeMap.put(classe, new Node(classe, string_classe, Color.BLUE));
		
		// Afficher CLASS ?relation ?otherclass
		QueryExecution queryexec = GraphCreator.execQuery(RequestBuilder.A1_R1(classe, limit));
		ResultSet res = queryexec.execSelect();
		while (res.hasNext()) {
			QuerySolution sol = res.next();
			
			RDFNode rel = sol.get("relation");
			String url_rel = rel.toString();
			String string_rel = rel.toString().split("/")[rel.toString().split("/").length-1];
			
			RDFNode c = sol.get("otherclass");
			String url_c = c.toString();
			String string_c = c.toString().split("/")[c.toString().split("/").length-1];
			if (!nodeMap.containsKey(url_c)) {
				nodeMap.put(url_c, new Node(url_c, string_c, Color.RED));
			}
			
			graph.addEdge(new Vertex(url_rel, string_rel, "T-Box"), nodeMap.get(classe), nodeMap.get(url_c));
		}
		
		// Afficher ?otherclass ?relation CLASS
		queryexec = GraphCreator.execQuery(RequestBuilder.A1_R2(classe, limit));
		res = queryexec.execSelect();
		while (res.hasNext()) {
			QuerySolution sol = res.next();
			
			RDFNode rel = sol.get("relation");
			String url_rel = rel.toString();
			String string_rel = rel.toString().split("/")[rel.toString().split("/").length-1];
			
			RDFNode c = sol.get("otherclass");
			String url_c = c.toString();
			String string_c = c.toString().split("/")[c.toString().split("/").length-1];
			if (!nodeMap.containsKey(url_c)) {
				nodeMap.put(url_c, new Node(url_c, string_c, Color.GREEN));
			}
			
			graph.addEdge(new Vertex(url_rel, string_rel, "T-Box"), nodeMap.get(url_c), nodeMap.get(classe));
		}
		
		return GraphCreator.visualization(graph);
	}

	/**
	 * Afficher la hiérarchie d’une classe
	 * @param classe
	 * @param limit
	 * @return
	 */
	public static JPanel createGraph_A2(String classe, int limit) {
		Graph<Node,Vertex> graph = new DelegateForest<Node,Vertex>();
		HashMap<String, Node> nodeMap = new HashMap<String, Node>();
		String string_classe = classe.toString().split("/")[classe.toString().split("/").length-1];
		nodeMap.put(classe, new Node(classe, string_classe, Color.blue));
		
		// Afficher les classes parentes
		QueryExecution queryexec = GraphCreator.execQuery(RequestBuilder.A2_R1(classe, limit));
		ResultSet res = queryexec.execSelect();
		while (res.hasNext()) {
			QuerySolution sol = res.next();
			
			RDFNode parentclass = sol.get("parentclass");
			String url_parent = parentclass.toString();
			String string_parent = parentclass.toString().split("/")[parentclass.toString().split("/").length-1];
			if (!nodeMap.containsKey(url_parent)) {
				nodeMap.put(url_parent, new Node(url_parent, string_parent, Color.RED));
			}
			RDFNode superclass = sol.get("superclass");
			String url_super = superclass.toString();
			String string_super = superclass.toString().split("/")[superclass.toString().split("/").length-1];
			if (!nodeMap.containsKey(url_super)) {
				nodeMap.put(url_super, new Node(url_super, string_super, Color.RED));
			}
			
			graph.addEdge(new Vertex("http://www.w3.org/2000/01/rdf-schema#rdfs:subClassOf", "rdfs:subClassOf", "subClassOf"), nodeMap.get(url_parent), nodeMap.get(url_super));
		}
		
		// Afficher les classes filles
		queryexec = GraphCreator.execQuery(RequestBuilder.A2_R2(classe, limit));
		res = queryexec.execSelect();
		while (res.hasNext()) {
			QuerySolution sol = res.next();
			
			RDFNode subclass = sol.get("subclass");
			String url_sub = subclass.toString();
			String string_sub = subclass.toString().split("/")[subclass.toString().split("/").length-1];
			if (!nodeMap.containsKey(url_sub)) {
				nodeMap.put(url_sub, new Node(url_sub, string_sub, Color.GREEN));
			}
			RDFNode superclass = sol.get("superclass");
			String url_super = superclass.toString();
			String string_super = superclass.toString().split("/")[superclass.toString().split("/").length-1];
			if (!nodeMap.containsKey(url_super)) {
				nodeMap.put(url_super, new Node(url_super, string_super, Color.GREEN));
			}
			
			graph.addEdge(new Vertex("http://www.w3.org/2000/01/rdf-schema#rdfs:subClassOf", "rdfs:subClassOf", "subClassOf"), nodeMap.get(url_sub), nodeMap.get(url_super));
		}
		
		return GraphCreator.visualization(graph);
	}
	
	/**
	 * Afficher les relations instanciées d’une classe
	 * @param classe
	 * @param limit
	 * @return
	 */
	public static JPanel createGraph_A3(String classe, int limit) {
		Graph<Node,Vertex> graph = new DirectedSparseMultigraph<Node,Vertex>();
		HashMap<String, Node> nodeMap = new HashMap<String, Node>();
		String string_classe = classe.toString().split("/")[classe.toString().split("/").length-1];
		nodeMap.put(classe, new Node(classe, string_classe, Color.BLUE));
		
		// Afficher CLASS ?relation ?otherclass
		QueryExecution queryexec = GraphCreator.execQuery(RequestBuilder.A3(classe, limit));
		ResultSet res = queryexec.execSelect();
		while (res.hasNext()) {
			QuerySolution sol = res.next();
			
			RDFNode count = sol.get("count");
			String temp = count.toString();
			String string_count = "";
			int i = 0 ;
			while(i<temp.length()-1 && Character.isDigit(temp.charAt(i))) {
				string_count = string_count + temp.charAt(i);
				i++;
			}
			
			RDFNode rel = sol.get("relation");
			String url_rel = rel.toString();
			String string_rel = rel.toString().split("/")[rel.toString().split("/").length-1];
			
			graph.addEdge(new Vertex(url_rel, string_rel, "A-Box"), nodeMap.get(classe), new Node("", string_count,Color.YELLOW));
		}
		
		return GraphCreator.visualization(graph);
	}
	
	/**
	 * Transformer le graphe en JPanel
	 * @param g
	 * @return
	 */
	public static JPanel visualization(Graph<Node,Vertex> g) {
		Layout<Node,Vertex> layout = new CircleLayout(g);
		//Layout<Node,Vertex> layout = new TreeLayout((Forest) g);
		BasicVisualizationServer<Node,Vertex> vv = new BasicVisualizationServer<Node,Vertex>(layout);

		Transformer<Node,Paint> nodePaint = new Transformer<Node,Paint>() {
			public Paint transform(Node node) {
				return node.getColor();
			}
		};
		
		Transformer<Vertex, Stroke> vertexPaint = new Transformer<Vertex, Stroke>() {
			public Stroke transform(Vertex vertex) {
				float dash[] = {10.0f};
				if (vertex.getBoxType().equals("T-Box")) {
				}
				if (vertex.getBoxType().equals("subClassOf")) {
					return new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
				}
				return new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
			}
		};
		
		vv.getRenderContext().setVertexFillPaintTransformer(nodePaint);
		vv.getRenderContext().setEdgeStrokeTransformer(vertexPaint);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

		JPanel panel = new JPanel();
		panel.add(vv);
		return panel;
	}
}
