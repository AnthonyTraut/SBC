package sbc;

import java.awt.Color;

import javax.swing.*;

public class Frame extends JFrame{
	
	public Frame(){
		this.setTitle("MyVowl");
		this.setSize(700,600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	    this.setVisible(true);
	    
	    JPanel topPanel = new JPanel();
	    
	    JPanel midPanel = new JPanel();
	    midPanel.setBackground(Color.WHITE);
	}

}
