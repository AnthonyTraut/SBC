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
		JCheckBox cb = (JCheckBox)e.getSource();
		switch(request){
		case "A1":
			if(cb.isSelected()){
				String classe = classeComp.getText();
				int limit = Integer.parseInt(limitComp.getText());
				JPanel newPanel = GraphCreator.createGraph_A1(classe, limit);
				panel.add(newPanel);
			}
			else{
				panel.removeAll();
			}
			panel.repaint();
			panel.revalidate();
			break;
		case "A2":
			if(cb.isSelected()){
				String classe = classeComp.getText();
				int limit = Integer.parseInt(limitComp.getText());
				JPanel newPanel = GraphCreator.createGraph_A2(classe, limit);
				panel.add(newPanel);
			}
			else{
				panel.removeAll();
			}
			panel.repaint();
			panel.revalidate();
			break;
		case "A3":
			if(cb.isSelected()){
				String classe = classeComp.getText();
				int limit = Integer.parseInt(limitComp.getText());
				JPanel newPanel = GraphCreator.createGraph_A3(classe, limit);
				panel.add(newPanel);
			}
			else{
				panel.removeAll();
			}
			panel.repaint();
			panel.revalidate();
			break;
		}
	}

}
