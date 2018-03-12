package sbc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.HashMap;

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
	
	public static JPanel createGraph_A1(String classe, int limit) {
		QueryExecution queryexec = GraphCreator.execQuery(RequestBuilder.A1_R1(classe, limit));
		ResultSet res = queryexec.execSelect();
		
		Graph<Node,Vertex> graph = new DirectedSparseMultigraph<Node,Vertex>();
		HashMap<String, Node> nodeMap = new HashMap<String, Node>();
		String string_classe = classe.toString().split("/")[classe.toString().split("/").length-1];
		System.out.println(string_classe);
		nodeMap.put(classe, new Node(classe, string_classe));
		
		while (res.hasNext()) {
			QuerySolution sol = res.next();
			RDFNode rel = sol.get("relation");
			String url_rel = rel.toString();
			String string_rel = rel.toString().split("/")[rel.toString().split("/").length-1];
			RDFNode c = sol.get("otherclass");
			String url_c = c.toString();
			String string_c = c.toString().split("/")[c.toString().split("/").length-1];
			
			if (!nodeMap.containsKey(url_c)) {
				nodeMap.put(url_c, new Node(url_c, string_c));
			}
			
			graph.addEdge(new Vertex(url_rel, string_rel), nodeMap.get(classe), nodeMap.get(url_c));
		}
		
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
				nodeMap.put(url_c, new Node(url_c, string_c));
			}
			
			graph.addEdge(new Vertex(url_rel, string_rel), nodeMap.get(url_c), nodeMap.get(classe));
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
	
	public static JPanel visualization(Graph<Node,Vertex> g) {
		Layout<Node,Vertex> layout = new CircleLayout(g);
		BasicVisualizationServer<Node,Vertex> vv = new BasicVisualizationServer<Node,Vertex>(layout);

		Transformer<Node,Paint> vertexPaint = new Transformer<Node,Paint>() {
			public Paint transform(Node i) {
				return Color.GREEN;
			}
		};
		
		float dash[] = {10.0f};
		final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
		Transformer<Vertex, Stroke> edgeStrokeTransformer = new Transformer<Vertex, Stroke>() {
			public Stroke transform(Vertex s) {
				return edgeStroke;
			}
		};
		
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
		vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

		JPanel panel = new JPanel();
		panel.add(vv);
		return panel;
	}
}
