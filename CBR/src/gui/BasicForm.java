package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/** 
 * �ۼ��� : 2016 - 12 - 31
 * �ۼ��� : ����ä
 * ����   : ���� ���� ������ִ� ���̺귯�� ����
 * 
 * ��� �޼��� - �ٸ� �����ε� �޼��带 ������� �����ּ���.
 * setTitle(String title)  //�������̵�
 * setFormSize(Dimension d)
 * getMainPanel() �� ����Ͽ� ���°��Դϴ�.
 * setVisable(true) ��������߉�
 *  //���� ����
 *  public class Gui extends BasicFrame{
 * 		JPanel mainCon = getMainPanel();
 * 		Gui(){
 * 			mainCon.setLayout(null);
 * 			mainCon.add(new JLable("dd"));
 * 		}
 * 
 * 
 * ���߿� getter setter ������
 */
public class BasicForm extends JFrame {
	
	private JPanel titleBar_pan = new JPanel();
	private JLabel title_lab = new JLabel("����");
	private Font title_font = new Font("���� ���", Font.PLAIN, 14);
	
	private TitleBarUi exit_btn = new TitleBarUi(); //JButton
	private TitleBarUi max_btn = new TitleBarUi(); //JButton
	private TitleBarUi min_btn = new TitleBarUi(); //JButton
	
	private JPanel contents_pan = new JPanel();
	
	private Point absPoint = null;
	
	/**
	 * ȭ��ũ�� *���Ƿ� �������� �ʾ�����
	 */
	private Dimension windowSize = new Dimension(800, 500);
	
	final private Color titleColor = new Color(33, 174, 255);
	final private Color exitColor = new Color(232, 91, 34);
	final private Color maxColor = new Color(255, 146, 68);
	final private Color minColor = new Color(255, 188, 44);
	
	int titleBar_height = 30;
	
	ActionListener exitAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	};
	
	@Override
	public void setTitle(String title) {
		title_lab.setText(title);
	}
	
	/**
	 * container�� ������ ũ�⸦ ���ϴ°�
	 * @param d
	 */
	public void setFormSize(Dimension d) {
		windowSize = d;
		setSize(windowSize.width,windowSize.height+titleBar_height);
		formResize();
	}
	
	public void formResize(){
		titleBar_pan.setSize(windowSize.width,titleBar_height);
		contents_pan.setSize(windowSize.width,windowSize.height);
		exit_btn.setLocation(windowSize.width-TitleBarUi.uiSize.width/4*5,titleBar_height/2-TitleBarUi.uiSize.height/2);
		max_btn.setLocation(windowSize.width-TitleBarUi.uiSize.width/4*10,titleBar_height/2-TitleBarUi.uiSize.height/2);
		min_btn.setLocation(windowSize.width-TitleBarUi.uiSize.width/4*15,titleBar_height/2-TitleBarUi.uiSize.height/2);
	}
	
	
	/**
	 * container panel �� �ҷ��ɴϴ�.
	 */
	public JPanel getMainPanel(){
		return contents_pan;
	}
	
	public void addExitMsg(String title,String msg){
		exit_btn.removeActionListener(exitAction);
		exit_btn.addActionListener(e->{
			int ok = JOptionPane.showOptionDialog(null, 
					msg,
					title,
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.CANCEL_OPTION,
					null, null, null);
			if(ok == 0)
				System.exit(0);
		});
	}
	
	public BasicForm() {
		//�ٸ������� �̸������ϰ��ϱ�
		setTitle("LiveTV"); //�Ƹ� �ε��� ���ǰų� ���ɷ����� ���ϼ��ֽ�
		setSize(windowSize.width, windowSize.height+titleBar_height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setUndecorated(true); //�׵θ� ����
		
		//Ÿ��Ʋ
		titleBar_pan.setSize(windowSize.width,titleBar_height);
		titleBar_pan.setLocation(0,0);
		titleBar_pan.setLayout(null);
		titleBar_pan.setBackground(titleColor);
		MouseAdapter windowMove = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				absPoint = e.getPoint();
			}
			@Override
            public void mouseDragged(MouseEvent e) {
            	if(absPoint != null){
            		setLocation(new Point(
            			getLocation().x - (absPoint.x-e.getX()),
            			getLocation().y - (absPoint.y-e.getY())
            		));
            	}
            }
			@Override
            public void mouseReleased(MouseEvent e) {
				absPoint = null;
            }
		};
		
		titleBar_pan.addMouseListener(windowMove);
		titleBar_pan.addMouseMotionListener(windowMove);
		
		title_lab.setSize(windowSize.width-200,titleBar_height);
		title_lab.setLocation(10,0);
		title_lab.setFont(title_font);
		
		//���� ������ location ���� 5 10 15
		
		exit_btn.setBackground(exitColor);
		exit_btn.addActionListener(exitAction);
		
		max_btn.setBackground(maxColor);
		max_btn.addActionListener(e->{
			setState(Frame.MAXIMIZED_BOTH);
		});
		
		
		min_btn.setBackground(minColor);
		min_btn.addActionListener(e->{
			setState(Frame.ICONIFIED);
		});
		
		formResize();
		
		titleBar_pan.add(title_lab);
		titleBar_pan.add(exit_btn);
		titleBar_pan.add(max_btn);
		titleBar_pan.add(min_btn);
		
		//������
		contents_pan.setSize(windowSize.width,windowSize.height);
		contents_pan.setLocation(0,titleBar_height);
		//contents_pan.setLayout(null);
		
		getContentPane().add(titleBar_pan);
		getContentPane().add(contents_pan);	
	}
}