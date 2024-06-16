package si.zitnik.sociogram.gui.graph;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
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

import javax.swing.*;
import java.awt.*;

public class FreeGraphPanel extends VisualizationViewer<Vertex, Edge> implements TaskPanel {
	private static final long serialVersionUID = -8273374447382756104L;
	private RunningUtil runningUtil;
	private GraphUtil graphUtil;
	//private static Layout<Vertex, Edge> layout;
	private JRibbonFrame mainFrame;
	private boolean prikazImen;

	public FreeGraphPanel(RunningUtil runningUtil, GraphUtil graphUtil, JRibbonFrame mainFrame) {
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

        Layout<Vertex, Edge> layout = new KKLayout<Vertex, Edge>(socGraph.getGraph());
        layout.setSize(new Dimension(size-SocLayout.margin,size-SocLayout.margin));
        this.setGraphLayout(layout);
		this.setPreferredSize(new Dimension(size-SocLayout.margin,size-SocLayout.margin));
	
		this.revalidate();
	}


	public void togglePrikazImen() {
		this.prikazImen = (this.prikazImen) ? false : true ;
	}	

	
}
