package sbc;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.jena.query.ResultSet;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

public class GraphCreator {

	
	public GraphCreator() {
		
	}
	
	public Graph<Node,Vertex> create(ResultSet res) {
		Graph<Node,Vertex> graph = new DirectedSparseMultigraph<Node,Vertex>();
				
		return graph;
	}
	
	public Graph<Node,Vertex> test(ResultSet res) {
		Graph<Node,Vertex> graph = new DirectedSparseMultigraph<Node,Vertex>();
		
		graph.addEdge(new Vertex("link"), new Node("n1"), new Node("n2"));
		
		return graph;
	}
	
	public JPanel visualization(Graph<Node,Vertex> g) {
		Layout<Integer, String> layout = new CircleLayout(g);
		layout.setSize(new Dimension(300,300));
		BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<Integer,String>(layout);
		vv.setPreferredSize(new Dimension(350,350));
		JPanel panel = new JPanel();	
		panel.add(vv);
		return panel;
		
	}
}
