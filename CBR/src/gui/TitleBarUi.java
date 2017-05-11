package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

public class TitleBarUi extends JButton{
	
	final static protected Dimension uiSize = new Dimension(22, 22); //�ݱ� �ִ�ȭ �ּ�ȭ ��ưũ��
	
	public Dimension getUiSize() {
		return uiSize;
	}

	@Override
    protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       
       int width = getWidth();
       int height = getHeight();
       Graphics2D graphics = (Graphics2D) g;
       graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

       graphics.setColor(getBackground());
       graphics.fillRoundRect(0, 0, width-1, height-1, uiSize.width, uiSize.height);
       
       //graphics.setColor(getForeground());
       graphics.setColor(new Color(0, 0, 50, 80));
       
       //�̰͵� ��¦ ����غ��ߵ�
       graphics.drawRoundRect(0, 0, width-1, height-1, uiSize.width, uiSize.height);
    }
	
	public TitleBarUi() {
		setOpaque(false);
		setBorderPainted(false);
		setFocusPainted(false);
		setSize(uiSize);
		// �̰Ŵ� ����غ��ߵ�
		setContentAreaFilled(false);
	}
}