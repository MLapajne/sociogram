package si.zitnik.sociogram.gui.poll;

import java.awt.*;

import javax.swing.*;

import si.zitnik.sociogram.interfaces.TaskPanel;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

public class PollPanel extends JPanel implements TaskPanel {
	private static final long serialVersionUID = 2592514260180345160L;
	private RunningUtil runningUtil;
	private PollTable polltable;
	private JScrollPane scPane;
    private JPanel legend;

    public PollPanel(RunningUtil runningUtil) {
		this.runningUtil = runningUtil;
		this.setLayout(new BorderLayout());
		init();
	}

	@Override
	public void init() {
		
		
		//remove old
		if (this.scPane != null){
			this.remove(this.scPane);
		}
        if (this.legend != null) {
            this.remove(this.legend);
        }
		
		//instantiate new
		this.polltable = new PollTable(runningUtil);
		this.polltable.setFillsViewportHeight(true);
		
		//add new
		scPane = new JScrollPane(this.polltable);		
		this.add(scPane, BorderLayout.CENTER);

        legend = new JPanel(new FlowLayout(FlowLayout.LEADING));
        int squareSize = 10;
        legend.add(new JLabel(I18n.get("legend") + ": "));
        legend.add(new SquarePanel(squareSize, Colors.DEFAULT));
        legend.add(new JLabel(" - " + I18n.get("allInc") + ", "));
        legend.add(new SquarePanel(squareSize, Colors.ALL_POS));
        legend.add(new JLabel(" - " + I18n.get("allPoz") + ", "));
        legend.add(new SquarePanel(squareSize, Colors.ALL_NEG));
        legend.add(new JLabel(" - " + I18n.get("allNeg") + ", "));
        legend.add(new SquarePanel(squareSize, Colors.ALL));
        legend.add(new JLabel(" - " + I18n.get("allPozNeg")));

        this.add(legend, BorderLayout.SOUTH);
	}
	
	public PollTable getPollTable(){
		return this.polltable;
	}
}


