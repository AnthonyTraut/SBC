package sbc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.ResultSet;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class GraphCreator {

	
	public GraphCreator() {
		
	}
	
	public static void createGraph_A1_R1(QueryExecution query) {
		ResultSet rs = query.execSelect();
	}
	
	public static void createGraph_A1_R2(QueryExecution query) {
		ResultSet rs = query.execSelect();
	}
	
	public static void createGraph_A2(QueryExecution query) {
		ResultSet rs = query.execSelect();
	}
	
	public static void createGraph_A3(QueryExecution query) {
		ResultSet rs = query.execSelect();
	}
	
	public static void createGraph_B1(QueryExecution query) {
		ResultSet rs = query.execSelect();
	}
	
	public static void createGraph_B3(QueryExecution query) {
		ResultSet rs = query.execSelect();
	}
	
	public static void createGraph_C1(QueryExecution query) {
		ResultSet rs = query.execSelect();
	}
	
	public static void createGraph_C2(QueryExecution query) {
		ResultSet rs = query.execSelect();
	}
	
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
		
		JPanel panel = new JPanel();
		panel.add(vv);
		return panel;
	}
}
