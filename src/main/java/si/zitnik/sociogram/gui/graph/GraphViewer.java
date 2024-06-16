package si.zitnik.sociogram.gui.graph;

import java.awt.Color;
import java.awt.Graphics;

import si.zitnik.sociogram.gui.graph.jung.SocLayout;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class GraphViewer<Vertex, Edge> extends VisualizationViewer<Vertex, Edge> {
	private static final long serialVersionUID = -5834203893192335403L;
	
	public GraphViewer(Layout<Vertex, Edge> layout) {
		super(layout);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		int size = 0;
		if (this.getHeight() > this.getWidth()){
			size = this.getWidth();
		} else {
			size = this.getHeight();
		}
		double radiusInc = (size-2*SocLayout.margin)*1.0/2/5;
		int radius = (int) radiusInc;
		g.drawArc(size/2-radius, size/2-radius, radius*2, radius*2, 0, 360);
		radius += (int) radiusInc;
		g.drawArc(size/2-radius, size/2-radius, radius*2, radius*2, 0, 360);
		radius += (int) radiusInc;
		g.drawArc(size/2-radius, size/2-radius, radius*2, radius*2, 0, 360);
		radius += (int) radiusInc;
		g.drawArc(size/2-radius, size/2-radius, radius*2, radius*2, 0, 360);
		radius += (int) radiusInc;
		g.drawArc(size/2-radius, size/2-radius, radius*2, radius*2, 0, 360);
	}


	

}
