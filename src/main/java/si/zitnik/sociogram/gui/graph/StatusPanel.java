package si.zitnik.sociogram.gui.graph;

import si.zitnik.sociogram.util.I18n;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class StatusPanel extends JPanel {
	private GraphUtil graphUtil;

	public StatusPanel(GraphUtil graphUtil) {
		this.graphUtil = graphUtil;
		this.setPreferredSize(new Dimension(150, 300));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawString(I18n.get("graphInfoTxt"), 100, 100);
	}

}
