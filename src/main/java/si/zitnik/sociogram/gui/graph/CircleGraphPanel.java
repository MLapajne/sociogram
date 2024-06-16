package si.zitnik.sociogram.gui.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Paint;

import javax.swing.Icon;

import org.apache.commons.collections15.Transformer;


import org.pushingpixels.radiance.component.api.ribbon.JRibbonFrame;
import si.zitnik.sociogram.entities.Gender;
import si.zitnik.sociogram.gui.graph.jung.Edge;
import si.zitnik.sociogram.gui.graph.jung.SocGraph;
import si.zitnik.sociogram.gui.graph.jung.SocLayout;
import si.zitnik.sociogram.gui.graph.jung.Vertex;
import si.zitnik.sociogram.interfaces.TaskPanel;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

public class CircleGraphPanel extends VisualizationViewer<Vertex, Edge> implements TaskPanel {
	private static final long serialVersionUID = -8273374447382756104L;
	private RunningUtil runningUtil;
	private GraphUtil graphUtil;
	private JRibbonFrame mainFrame;
	private boolean prikazImen;

	public CircleGraphPanel(RunningUtil runningUtil, GraphUtil graphUtil, JRibbonFrame mainFrame) {
		super(new CircleLayout<Vertex, Edge>(new SparseGraph<Vertex, Edge>()));
		this.runningUtil = runningUtil;
		this.graphUtil = graphUtil;
		this.mainFrame = mainFrame;
		this.prikazImen = true;
		initialInit();
		init();
	}


	private void initialInit() {
		this.getRenderContext().setEdgeDrawPaintTransformer(new Transformer<Edge, Paint>() {
			@Override
			public Paint transform(Edge edge) {
				return edge.getColor();
			}
		});
		this.getRenderContext().setVertexIconTransformer(new Transformer<Vertex, Icon>() {
			@Override
			public Icon transform(Vertex vertex) {
				if (runningUtil.getPerson(vertex.getRealId()).getGender().equals(Gender.MALE)){
					return runningUtil.icons.maleSign;
				} else {
					return runningUtil.icons.femaleSign;
				}
			}
		});
		this.setEdgeToolTipTransformer(new Transformer<Edge, String>() {
			@Override
			public String transform(Edge edge) {
				StringBuffer sb = new StringBuffer();
				switch (edge.getRank()){
				case 1: sb.append(I18n.get("first") + " "); break;
				case 2: sb.append(I18n.get("second") + " "); break;
				case 3: sb.append(I18n.get("third") + " "); break;
				}
				
				switch (edge.getLikingType()) {
				case POSITIVE: sb.append(I18n.get("positive") + " "); break;
				case NEGATIVE: sb.append(I18n.get("negative") + " "); break;
				}
				
				sb.append(I18n.get("choice") + ": ");
				sb.append(runningUtil.getPersonFullName(edge.getFromRealId()));
				sb.append(" -> ");
				sb.append(runningUtil.getPersonFullName(edge.getToRealId()));
				return sb.toString();
			}
		});
		this.addPreRenderPaintable(new VisualizationServer.Paintable() {
			
			@Override
			public boolean useTransform() {
				return false;
			}
			
			@Override
			public void paint(Graphics g) {
				g.setColor(Color.LIGHT_GRAY);
				int size = 0;
				if (CircleGraphPanel.this.getHeight() > CircleGraphPanel.this.getWidth()){
					size = CircleGraphPanel.this.getWidth();
				} else {
					size = CircleGraphPanel.this.getHeight();
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
		});
		
		
		DefaultModalGraphMouse<Vertex, Edge> gm = new DefaultModalGraphMouse<Vertex, Edge>(1,1);
		gm.setMode(Mode.PICKING);
		this.setGraphMouse(gm);		
	}


	@Override
	public void init() {
		SocGraph socGraph = new SocGraph(runningUtil, graphUtil);
		
		int size = 0;
		if (mainFrame.getSize().getHeight()-mainFrame.getRibbon().getSize().getHeight() < mainFrame.getSize().getWidth() / 2 - 10){
			size = (int) (mainFrame.getSize().getHeight()-mainFrame.getRibbon().getSize().getHeight());
		} else {
			size = (int) (mainFrame.getSize().getWidth() / 2 - 10);
		}
		
		this.getRenderContext().setVertexLabelTransformer(new Transformer<Vertex, String>() {
			@Override
			public String transform(Vertex vertex) {
				if (prikazImen){
					return runningUtil.getPersonFullName(vertex.getRealId());
				} else {
					return vertex.getRealId()+"";
				}
			}
		});
		
		Layout<Vertex, Edge> layout = new SocLayout(socGraph.getGraph(), this.graphUtil, size-SocLayout.margin);
		this.setGraphLayout(layout);
		this.setPreferredSize(new Dimension(size-SocLayout.margin,size-SocLayout.margin));
	
		this.revalidate();
	}


	public void togglePrikazImen() {
		this.prikazImen = (this.prikazImen) ? false : true ;
	}	

	
}
