package si.zitnik.sociogram.gui.socioclass;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.enums.SocioClassification;
import si.zitnik.sociogram.interfaces.TaskPanel;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

public class SocioImagePanel extends JPanel implements TaskPanel {
	private static final long serialVersionUID = -2848876310341312984L;
	private RunningUtil runningUtil;
	private SpringLayout layout;
	
	private JLabel likingNumber;
	private JLabel unlikingNumber;
	private JLabel averageNumber;
	private JLabel unseenNumber;
	private JLabel controverseNumber;

	public SocioImagePanel(RunningUtil runningUtil) {
		this.runningUtil = runningUtil;
		this.layout = new SpringLayout();
		drawAll();
		init();
		
	}

	private void drawAll() {
		this.setLayout(this.layout);

		ImageIcon bgImage = runningUtil.icons.socioClassSI;

		if (I18n.getLocale().equals("hr")) {
			bgImage = runningUtil.icons.socioClassHR;
		} else if (I18n.getLocale().equals("en")) {
			bgImage = runningUtil.icons.socioClassEN;
		} else if (I18n.getLocale().equals("de")) {
			bgImage = runningUtil.icons.socioClassDE;
		}

		JLabel image = new JLabel(bgImage);
		layout.putConstraint(SpringLayout.WEST, image, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, image, 0, SpringLayout.NORTH, this);
		
		
		Font numberFont = new Font("Arial", Font.BOLD, 30);
		
		int initx = 250;
		int inity = 155;
		int nextx = 240;
		int nexty = 185;
		int avgx = 410;
		int avgy = 230;
		
		likingNumber = new JLabel("");
		likingNumber.setForeground(Color.BLACK);
		likingNumber.setFont(numberFont);
		layout.putConstraint(SpringLayout.WEST, likingNumber, initx, SpringLayout.WEST, image);
		layout.putConstraint(SpringLayout.NORTH, likingNumber, inity, SpringLayout.NORTH, image);
		
		unlikingNumber = new JLabel("");
		unlikingNumber.setForeground(Color.BLACK);
		unlikingNumber.setFont(numberFont);
		layout.putConstraint(SpringLayout.WEST, unlikingNumber, initx+nextx, SpringLayout.WEST, image);
		layout.putConstraint(SpringLayout.NORTH, unlikingNumber, inity+nexty, SpringLayout.NORTH, image);
		
		averageNumber = new JLabel("");
		averageNumber.setForeground(Color.BLACK);
		averageNumber.setFont(numberFont);
		layout.putConstraint(SpringLayout.WEST, averageNumber, avgx, SpringLayout.WEST, image);
		layout.putConstraint(SpringLayout.NORTH, averageNumber, avgy, SpringLayout.NORTH, image);
		
		unseenNumber = new JLabel("");
		unseenNumber.setForeground(Color.BLACK);
		unseenNumber.setFont(numberFont);
		layout.putConstraint(SpringLayout.WEST, unseenNumber, initx, SpringLayout.WEST, image);
		layout.putConstraint(SpringLayout.NORTH, unseenNumber, inity+nexty, SpringLayout.NORTH, image);
		
		controverseNumber = new JLabel("");
		controverseNumber.setForeground(Color.BLACK);
		controverseNumber.setFont(numberFont);
		layout.putConstraint(SpringLayout.WEST, controverseNumber, initx+nextx, SpringLayout.WEST, image);
		layout.putConstraint(SpringLayout.NORTH, controverseNumber, inity, SpringLayout.NORTH, image);
		
		this.add(likingNumber);
		this.add(unlikingNumber);
		this.add(averageNumber);
		this.add(unseenNumber);
		this.add(controverseNumber);
		this.add(image);
		Dimension size = new Dimension((int)image.getPreferredSize().getWidth(), (int)image.getPreferredSize().getHeight());
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		this.setMinimumSize(size);
	}

	@Override
	public void init() {
		HashMap<SocioClassification, ArrayList<Person>> socioResult = this.runningUtil.calculte2DimSocioClassification();
		
		this.likingNumber.setText(socioResult.get(SocioClassification.LIKING).size()+"");
		this.unlikingNumber.setText(socioResult.get(SocioClassification.UNLIKING).size()+"");
		this.averageNumber.setText(socioResult.get(SocioClassification.AVERAGES).size()+"");
		this.controverseNumber.setText(socioResult.get(SocioClassification.CONTROVERSE).size()+"");
		this.unseenNumber.setText(socioResult.get(SocioClassification.UNSEEN).size()+"");
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
