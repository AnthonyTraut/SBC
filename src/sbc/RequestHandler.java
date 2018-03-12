package sbc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RequestHandler implements ActionListener {
	
	String request;
	JTextField classeComp;
	JTextField limitComp;
	JPanel panel;
	
	public RequestHandler(String request, JTextField classe, JTextField limit, JPanel panel){
		this.request = request;
		this.classeComp = classe;
		this.limitComp = limit;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(request){
		case "A1":
			JCheckBox cb = (JCheckBox)e.getSource();
			if(cb.isSelected()){
				String classe1 = classeComp.getText();
				int limit1 = Integer.parseInt(limitComp.getText());
				JPanel panel1 = GraphCreator.createGraph_A1(classe1, limit1);
				panel.add(panel1);
				panel.repaint();
				panel.revalidate();
			}
		case "A2":
			String classe2 = classeComp.getText();
			int limit2 = Integer.parseInt(limitComp.getText());
			//GraphCreator.createGraph_A1(classe1, limit1);
			//panel.add(panel2);
			break;
		case "A3":
			String classe3 = classeComp.getText();
			int limit3 = Integer.parseInt(limitComp.getText());
			//GraphCreator.createGraph_A1(classe1, limit1);
			//panel.add(panel3);
			break;
		}
	}

}
