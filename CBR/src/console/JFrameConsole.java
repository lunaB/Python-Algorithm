package console;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import gui.BasicForm;

/*
 * 
 */
public final class JFrameConsole {
	
	/*
	 * beauti JFrame 
	 */
	BasicForm form;
	JPanel main_panel;
	JScrollPane jScrollPane;
	JTextArea textArea;
	
	//##########################################################
	public JFrameConsole() {
		form = new BasicForm();
		form.setTitle("Console");
		init();
	}
	public JFrameConsole(String title) {
		form = new BasicForm();
		form.setTitle(title);
		init();
	}
	public JFrameConsole(int number) {
		form = new BasicForm();
		form.setTitle("Console "+number);
		init();
	}

	//##########################################################
	public void init(){
		main_panel = form.getMainPanel();
		main_panel.setLayout(new BorderLayout());
		textArea = new JTextArea();
		
		jScrollPane = new JScrollPane(textArea);
		jScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		main_panel.add(jScrollPane,BorderLayout.CENTER);
		
		form.setVisible(true);
	}
	
	//##########################################################
	public void print(String text){
		textArea.append(text);
		int pos = textArea.getText().length();
		textArea.setCaretPosition(pos);
		textArea.requestFocus();
	}
	
	public void println(String text){
		textArea.append(text+"\n");
		int pos = textArea.getText().length();
		textArea.setCaretPosition(pos);
		textArea.requestFocus();
	}
	
	public void clear(){
		textArea.setText("");
	}
	
	//##########################################################
	public void setConsoleSize(int width, int height) {
		form.setFormSize(new Dimension(width, height));
	}
	public void setConsoleSize(Dimension size) {
		form.setFormSize(size);
	}
	
	//##########################################################
	
}
