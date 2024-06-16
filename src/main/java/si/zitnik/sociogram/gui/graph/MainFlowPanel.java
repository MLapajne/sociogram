package si.zitnik.sociogram.gui.graph;

import si.zitnik.sociogram.gui.SociogramRibbon;
import si.zitnik.sociogram.util.RunningUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Created by slavkoz on 14/09/14.
 */
public class MainFlowPanel extends JPanel {
    private final RunningUtil runningUtil;
    private final GraphUtil graphUtil;
    private final SociogramRibbon mainFrame;
    private final CircleGraphPanel circleGraphPanel;
    private final FreeGraphPanel freeGraphPanel;

    public MainFlowPanel(RunningUtil runningUtil, GraphUtil graphUtil, SociogramRibbon mainFrame) {
        this.runningUtil = runningUtil;
        this.graphUtil = graphUtil;
        this.mainFrame = mainFrame;

        this.circleGraphPanel = new CircleGraphPanel(runningUtil, this.graphUtil, this.mainFrame);
        this.freeGraphPanel = new FreeGraphPanel(runningUtil, this.graphUtil, this.mainFrame);

        this.setLayout(new FlowLayout());
        this.add(circleGraphPanel);
        this.add(freeGraphPanel);
    }

    public void init() {
        circleGraphPanel.init();
        freeGraphPanel.init();
        this.remove(circleGraphPanel);
        this.remove(freeGraphPanel);
        this.add(circleGraphPanel);
        this.add(freeGraphPanel);
    }

    public void togglePrikazImen() {
        circleGraphPanel.togglePrikazImen();
        freeGraphPanel.togglePrikazImen();
    }

    @Override
    public Color getBackground() {
        return super.getBackground();
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        if (circleGraphPanel != null)
            circleGraphPanel.setBackground(bg);
        if (freeGraphPanel != null)
            freeGraphPanel.setBackground(bg);
    }

    public CircleGraphPanel getCircleGraphPanel() {
        return circleGraphPanel;
    }

    public FreeGraphPanel getFreeGraphPanel() {
        return freeGraphPanel;
    }
}
