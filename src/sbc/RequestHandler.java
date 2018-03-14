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
	JTextField limitComp1;
	JTextField limitComp2;
	JTextField limitComp3;
	JPanel panel;
	
	public RequestHandler(JCheckBox request1, JCheckBox request2, JCheckBox request3, JTextField limit1, JTextField limit2, JTextField limit3, JTextField classe, JPanel panel){
		this.request1 = request1;
		this.request2 = request2;
		this.request3 = request3;
		this.classeComp = classe;
		this.limitComp1 = limit1;
		this.limitComp2 = limit2;
		this.limitComp3 = limit3;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean r1, r2, r3;
		r1 = request1.isSelected();
		r2 = request2.isSelected();
		r3 = request3.isSelected();
		
		int l1, l2, l3;
		l1 = Integer.parseInt(limitComp1.getText());
		l2 = Integer.parseInt(limitComp2.getText());
		l3 = Integer.parseInt(limitComp3.getText());
		
		panel.removeAll();
		
		JPanel graph = GraphCreator.createGraph_A(classeComp.getText(), l1, l2, l3, r1, r2, r3);
		panel.add(graph);
		
		panel.repaint();
		panel.revalidate();
	}

}
