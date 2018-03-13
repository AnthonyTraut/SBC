package sbc;

import java.awt.*;

import javax.swing.*;

public class Frame extends JFrame{
	
	public Frame(){
		this.setTitle("MyVowl");
		this.setSize(1400,900);
	    
	    JPanel menuPanel = new JPanel();
	    menuPanel.setLayout(new BorderLayout());
	    menuPanel.setPreferredSize(new Dimension(300, 800));
	    menuPanel.setBackground(Color.LIGHT_GRAY);
	    
	    JPanel midPanel = new JPanel();
	    midPanel.setLayout(new BorderLayout());
	    midPanel.setBackground(Color.WHITE);
	    
	    JPanel params = new JPanel();
	    params.setLayout(new BoxLayout(params, BoxLayout.Y_AXIS));
	    params.setPreferredSize(new Dimension(300, 250));
	    params.setBackground(Color.LIGHT_GRAY);
	    params.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
	    
	    JPanel classeLabelPanel = new JPanel();
	    classeLabelPanel.setPreferredSize(new Dimension(300, 35));
	    classeLabelPanel.setLayout(new BoxLayout(classeLabelPanel, BoxLayout.X_AXIS));
	    classeLabelPanel.setBackground(Color.LIGHT_GRAY);
	    JLabel classeLabel = new JLabel("Classe :");
	    classeLabel.setPreferredSize(new Dimension(100, 30));
	    
	    classeLabelPanel.add(Box.createRigidArea(new Dimension(20,0)));
	    classeLabelPanel.add(classeLabel);
	    classeLabelPanel.add(Box.createHorizontalGlue());
	    
	    JPanel classePanel = new JPanel();
	    classePanel.setPreferredSize(new Dimension(300, 35));
	    classePanel.setLayout(new BoxLayout(classePanel, BoxLayout.X_AXIS));
	    classePanel.setBackground(Color.LIGHT_GRAY);
	    JTextField classe = new JTextField();
	    classe.setPreferredSize(new Dimension(200, 30));
	    classe.setMaximumSize(new Dimension(200, 30));
	    
	    classePanel.add(Box.createRigidArea(new Dimension(20,0)));
	    classePanel.add(classe);
	    classePanel.add(Box.createHorizontalGlue());
	    
	    JPanel limitLabelPanel = new JPanel();
	    limitLabelPanel.setPreferredSize(new Dimension(300, 35));
	    limitLabelPanel.setLayout(new BoxLayout(limitLabelPanel, BoxLayout.X_AXIS));
	    limitLabelPanel.setBackground(Color.LIGHT_GRAY);
	    JLabel limitLabel = new JLabel("Limite :");
	    limitLabel.setPreferredSize(new Dimension(100, 30));
	    
	    limitLabelPanel.add(Box.createRigidArea(new Dimension(20,0)));
	    limitLabelPanel.add(limitLabel);
	    limitLabelPanel.add(Box.createHorizontalGlue());
	    
	    JPanel limitPanel = new JPanel();
	    limitPanel.setPreferredSize(new Dimension(300, 35));
	    limitPanel.setLayout(new BoxLayout(limitPanel, BoxLayout.X_AXIS));
	    limitPanel.setBackground(Color.LIGHT_GRAY);
	    JTextField limit = new JTextField();
	    limit.setPreferredSize(new Dimension(200, 30));
	    limit.setMaximumSize(new Dimension(200, 30));
	    
	    limitPanel.add(Box.createRigidArea(new Dimension(20,0)));
	    limitPanel.add(limit);
	    limitPanel.add(Box.createHorizontalGlue());	    
	    
	    params.add(Box.createRigidArea(new Dimension(0, 40)));
	    params.add(classeLabelPanel);
	    params.add(Box.createRigidArea(new Dimension(0, 5)));
	    params.add(classePanel);
	    params.add(Box.createRigidArea(new Dimension(0, 30)));
	    params.add(limitLabelPanel);
	    params.add(Box.createRigidArea(new Dimension(0, 5)));
	    params.add(limitPanel);
	    params.add(Box.createRigidArea(new Dimension(0, 30)));
	    
	    JPanel requestsPanel = new JPanel();
	    requestsPanel.setLayout(new BoxLayout(requestsPanel, BoxLayout.Y_AXIS));
	    requestsPanel.setBackground(Color.LIGHT_GRAY);
	    
	    JPanel request1Panel = new JPanel();
	    request1Panel.setLayout(new BoxLayout(request1Panel, BoxLayout.X_AXIS));
	    request1Panel.setBackground(Color.LIGHT_GRAY);
	    JCheckBox request1 = new JCheckBox("Relations theoriques");
	    request1.setBackground(Color.LIGHT_GRAY);
	    request1.addActionListener(new RequestHandler("A1", classe, limit, midPanel));
	    request1Panel.add(Box.createRigidArea(new Dimension(20,0)));
	    request1Panel.add(request1);
	    request1Panel.add(Box.createHorizontalGlue());
	    
	    JPanel request2Panel = new JPanel();
	    request2Panel.setLayout(new BoxLayout(request2Panel, BoxLayout.X_AXIS));
	    request2Panel.setBackground(Color.LIGHT_GRAY);
	    JCheckBox request2 = new JCheckBox("Hierarchie");
	    request2.setBackground(Color.LIGHT_GRAY);
	    request2.addActionListener(new RequestHandler("A2", classe, limit, midPanel));
	    request2Panel.add(Box.createRigidArea(new Dimension(20,0)));
	    request2Panel.add(request2);
	    request2Panel.add(Box.createHorizontalGlue());
	    
	    JPanel request3Panel = new JPanel();
	    request3Panel.setLayout(new BoxLayout(request3Panel, BoxLayout.X_AXIS));
	    request3Panel.setBackground(Color.LIGHT_GRAY);
	    JCheckBox request3 = new JCheckBox("Relations instanciees");
	    request3.setBackground(Color.LIGHT_GRAY);
	    request3.addActionListener(new RequestHandler("A3", classe, limit, midPanel));
	    request3Panel.add(Box.createRigidArea(new Dimension(20,0)));
	    request3Panel.add(request3);
	    request3Panel.add(Box.createHorizontalGlue());
	    
	    requestsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
	    requestsPanel.add(request1Panel);
	    requestsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
	    requestsPanel.add(request2Panel);
	    requestsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
	    requestsPanel.add(request3Panel);
	    requestsPanel.add(Box.createRigidArea(new Dimension(0, 400)));
	    
	    menuPanel.add(params, BorderLayout.NORTH);	  
	    menuPanel.add(requestsPanel, BorderLayout.CENTER);	 
	    
	    this.getContentPane().setLayout(new BorderLayout());
	    this.getContentPane().add(menuPanel, BorderLayout.WEST);
	    this.getContentPane().add(midPanel, BorderLayout.CENTER);
	    
	    this.setResizable(false);
	    this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	    this.setVisible(true);
	}
}
