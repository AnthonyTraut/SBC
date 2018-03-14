package sbc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RequestHandler implements ActionListener {
	
	JCheckBox request1;
	JCheckBox request2;
	JCheckBox request3;
	JTextField classeComp;
	JTextField limitComp;
	JPanel panel;
	
	public RequestHandler(JCheckBox request1, JCheckBox request2, JCheckBox request3, JTextField classe, JTextField limit, JPanel panel){
		this.request1 = request1;
		this.request2 = request2;
		this.request3 = request3;
		this.classeComp = classe;
		this.limitComp = limit;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean r1, r2, r3;
		r1 = request1.isSelected();
		r2 = request2.isSelected();
		r3 = request3.isSelected();
		
		panel.removeAll();
		
		JPanel graph = GraphCreator.createGraph_A(classeComp.getText(), Integer.parseInt(limitComp.getText()), r1, r2, r3);
		panel.add(graph);
		
		panel.repaint();
		panel.revalidate();
	}

}
