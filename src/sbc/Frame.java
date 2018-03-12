package sbc;

import java.awt.*;

import javax.swing.*;

public class Frame extends JFrame{
	
	public Frame(){
		this.setTitle("MyVowl");
		this.setSize(900,800);
	    
	    JPanel topPanel = new JPanel();
	    topPanel.setLayout(new BorderLayout());
	    topPanel.setPreferredSize(new Dimension(900, 50));
	    topPanel.setBackground(Color.LIGHT_GRAY);
	    
	    JPanel endpointPanel = new JPanel();
	    endpointPanel.setLayout(new GridBagLayout());
	    endpointPanel.setBackground(Color.LIGHT_GRAY);
	    endpointPanel.setPreferredSize(new Dimension(topPanel.getPreferredSize().width/3,50));
	    JTextField endpoint = new JTextField();
	    endpoint.setPreferredSize(new Dimension(200, 20));
	    endpoint.setMaximumSize(new Dimension(150, 20));
	    endpointPanel.add(endpoint);
	    
	    JPanel classPanel = new JPanel();
	    classPanel.setLayout(new GridBagLayout());
	    classPanel.setBackground(Color.LIGHT_GRAY);
	    classPanel.setPreferredSize(new Dimension(topPanel.getPreferredSize().width/3, 50));
	    JTextField classe = new JTextField();
	    classe.setPreferredSize(new Dimension(200, 20));
	    classe.setMaximumSize(new Dimension(150, 20));
	    classPanel.add(classe);
	    
	    JPanel propertyPanel = new JPanel();
	    propertyPanel.setLayout(new GridBagLayout());
	    propertyPanel.setBackground(Color.LIGHT_GRAY);
	    propertyPanel.setPreferredSize(new Dimension(topPanel.getPreferredSize().width/3,50));
	    JTextField property = new JTextField();
	    property.setPreferredSize(new Dimension(200, 20));
	    property.setMaximumSize(new Dimension(150, 20));
	    //propertyPanel.add(property);
	    
	    topPanel.add(endpointPanel, BorderLayout.WEST);
	    topPanel.add(classPanel);
	    topPanel.add(propertyPanel, BorderLayout.EAST);
	    
	    JPanel midPanel = new JPanel();
	    midPanel.setBackground(Color.WHITE);
	    midPanel.add(GraphCreator.createGraph_A3("http://dbpedia.org/ontology/CelestialBody", 10));
	    
	    this.getContentPane().setLayout(new BorderLayout());
	    this.getContentPane().add(topPanel, BorderLayout.NORTH);
	    this.getContentPane().add(midPanel, BorderLayout.CENTER);
	    
	    this.setResizable(false);
	    this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	    this.setVisible(true);
	    }
}
