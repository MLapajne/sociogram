package si.zitnik.sociogram.gui.dialogs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import si.zitnik.sociogram.SociogramRunner;
import si.zitnik.sociogram.config.SociogramConstants;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

public class JInfoDialog extends JDialog {
	private static final long serialVersionUID = -8027572520747108951L;
	private RunningUtil runningUtil;
	
	public JInfoDialog(JFrame frame, RunningUtil runningUtil) throws Exception {
		super(frame);
		this.setTitle(I18n.get("aboutTheApp"));
		this.runningUtil = runningUtil;
		this.setModal(true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		this.getContentPane().setLayout(new BorderLayout());

        JLabel mgaLogo = new JLabel();
        mgaLogo.setIcon(runningUtil.icons.mgaLogo);
        mgaLogo.setPreferredSize(new Dimension(315, 67));
        JLabel inLogo = new JLabel(" " + I18n.get("and") + " ");
        JLabel krimarLogo = new JLabel();
        krimarLogo.setIcon(runningUtil.icons.krimarLogo);
        krimarLogo.setPreferredSize(new Dimension(185, 80));

        JPanel logoPanel = new JPanel(new FlowLayout());
        logoPanel.add(krimarLogo);
        logoPanel.add(inLogo);
        logoPanel.add(mgaLogo);

        this.getContentPane().add(logoPanel, BorderLayout.NORTH);
		this.getContentPane().add(createMainPanel(), BorderLayout.CENTER);
		
		JButton ok = new JButton(I18n.get("ok"));
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JInfoDialog.this.setVisible(false);
			}
		});
		this.getContentPane().add(ok, BorderLayout.SOUTH);
		
		Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		this.pack();
		this.setLocation(r.width/2-this.getWidth()/2, r.height/2-this.getHeight()/2);
		this.setVisible(true);
	}

	private JPanel createMainPanel() throws Exception {
		JPanel retVal = new JPanel();
		retVal.setLayout(new BoxLayout(retVal, BoxLayout.Y_AXIS));
		retVal.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), I18n.get("aboutTheApp")));
		retVal.add(new JLabel("  " + I18n.get("version") + ": " + SociogramConstants.PROGRAM_VER));
		retVal.add(new JLabel("  " + I18n.get("idea") + " "));
        retVal.add(new JLabel("       Slavc Žust"));
        retVal.add(new JLabel("       Mikro Graf Art d.o.o."));
		retVal.add(new JLabel("  " + I18n.get("implementation")));
        retVal.add(new JLabel("       Slavko Žitnik"));
        retVal.add(new JLabel("       Krimar, informacijske storitve, Slavko Žitnik s.p."));
		retVal.add(new JLabel("   "));
        retVal.add(new JLabel("  " + I18n.get("kontakt")));
        retVal.add(new JLabel("       +386 1 540 65 19, +386 41 782 099"));
        retVal.add(new JLabel("       slavc.zust@mga.si"));
        retVal.add(new JLabel("       http://krimar.si/sociogram"));
        retVal.add(new JLabel("   "));
		retVal.add(new JLabel("  " + I18n.get("registeredTo") + ": "));
        JLabel instLabel = new JLabel("       " + runningUtil.getInstitution());
        instLabel.setFont(instLabel.getFont().deriveFont(instLabel.getFont().getStyle() | Font.BOLD));
        retVal.add(instLabel);
		retVal.add(new JLabel("   "));
		retVal.add(new JLabel("  " + I18n.get("rightsReserved") + " ©" + SociogramConstants.PROGRAM_YEAR));
		return retVal;
	}


}
