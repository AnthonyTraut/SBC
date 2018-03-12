package sbc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
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
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class GraphCreator {

	private static String sparqlService  = "http://dbpedia.org/sparql";
	public GraphCreator() {
		
	}
	
	public static QueryExecution execQuery(Query query) {
		QueryExecution queryexec = QueryExecutionFactory.sparqlService(sparqlService, query);
		return queryexec;
	}
	
	public static JPanel createGraph_A1_R1(String classe, int limit) {
		QueryExecution queryexec = GraphCreator.execQuery(RequestBuilder.A1_R1(classe, limit));
		ResultSet rs = queryexec.execSelect();
		
		Graph<Node,Vertex> graph = new DirectedSparseMultigraph<Node,Vertex>();
		HashMap<String, Node> nodeMap = new HashMap<String, Node>();
		nodeMap.put(classe, new Node(classe));
		
		while (rs.hasNext()) {
			QuerySolution sol = rs.next();
			RDFNode rel = sol.get("relation");
			RDFNode c = sol.get("otherclass");
			
			if (!nodeMap.containsKey(c.toString())) {
				nodeMap.put(c.toString(), new Node(c.toString()));
			}
			
			graph.addEdge(new Vertex(rel.toString()), nodeMap.get(classe), nodeMap.get(c.toString()));
		}
		
		return GraphCreator.visualization(graph);
	}
	/*
	public static JPanel createGraph_A1_R2(String classe, int limit) {
		ResultSet rs = query.execSelect();
	}
	
	public static JPanel createGraph_A2(String classe, int limit) {
		ResultSet rs = query.execSelect();
	}
	
	public static JPanel createGraph_A2bis(String classe, int limit) {
		ResultSet rs = query.execSelect();
	}
	
	public static JPanel createGraph_A3(String classe, int limit) {
		ResultSet rs = query.execSelect();
	}*/
	
	public static Graph<Node,Vertex> create(ResultSet res) {
		Graph<Node,Vertex> graph = new DirectedSparseMultigraph<Node,Vertex>();
				
		graph.addEdge(new Vertex("link"), new Node("n1"), new Node("n2"));
		return graph;
	}
	
	public static JPanel visualization(Graph<Node,Vertex> g) {
		Layout<Integer, String> layout = new CircleLayout(g);
		layout.setSize(new Dimension(300,300));
		BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<Integer,String>(layout);
		vv.setPreferredSize(new Dimension(350,350));
		/*
		Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
			public Paint transform(Integer i) {
				return Color.GREEN;
			}
		};
		
		float dash[] = {10.0f};
		final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
		Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
			public Stroke transform(String s) {
				return edgeStroke;
			}
		};
		
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
		vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		*/
		JPanel panel = new JPanel();
		panel.add(vv);
		return panel;
	}
}
