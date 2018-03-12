package sbc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class RequestHandler implements ActionListener {
	
	String request;
	JTextField classeComp;
	JTextField limitComp;
	
	public RequestHandler(String request, JTextField classe, JTextField limit){
		this.request = request;
		this.classeComp = classe;
		this.limitComp = limit;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(request){
		case "A1":
			String classe1 = classeComp.getText();
			int limit1 = Integer.parseInt(limitComp.getText());
			GraphCreator.createGraph_A1_R1(classe, limit)
		case "A2":
			String classe2 = classeComp.getText();
			int limit2 = Integer.parseInt(limitComp.getText());
			break;
		case "A3":
			String classe3 = classeComp.getText();
			int limit3 = Integer.parseInt(limitComp.getText());
			break;
		}
	}

}
