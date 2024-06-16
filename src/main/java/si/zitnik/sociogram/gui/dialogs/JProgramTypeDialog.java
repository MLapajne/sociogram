package si.zitnik.sociogram.gui.dialogs;

import si.zitnik.sociogram.enums.ProgramType;
import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JProgramTypeDialog extends JDialog {
	private static final long serialVersionUID = -1L;
    private final boolean calledFromSettings;
    private RunningUtil runningUtil;

	public JProgramTypeDialog(JFrame frame, RunningUtil runningUtil, boolean calledFromSettings) throws Exception {
		super(frame);
		this.setTitle(I18n.get("programTypeSelection"));
		this.runningUtil = runningUtil;
		this.setModal(true);
        this.calledFromSettings = calledFromSettings;
        if (calledFromSettings) {
            this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        } else {
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }

        this.setContentPane(createMainPanel());
		
		Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		this.pack();
		this.setLocation(r.width/2-this.getWidth()/2, r.height/2-this.getHeight()/2);
		this.setVisible(true);
	}

	private JPanel createMainPanel() throws Exception {
		JPanel retVal = new JPanel();
		retVal.setLayout(new GridLayout(3,1));
		retVal.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), I18n.get("programTypeSelection")));

        JButton slo = new JButton(I18n.get("solstvo"));
        slo.setIconTextGap(20);
        slo.setHorizontalAlignment(SwingConstants.LEFT);
        slo.setHorizontalTextPosition(SwingConstants.RIGHT);
        slo.setIcon(runningUtil.icons.solstvo);
        slo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    runningUtil.changeProgramType(ProgramType.SOLSTVO);
                } catch (Exception e) {
                    e.printStackTrace();
                    new JErrorDialog(I18n.get("programTypeSelError"), e);
                }
                if (calledFromSettings) {
                    JOptionPane.showMessageDialog(null,
                            I18n.get("programTypeWarnMsg"),
                            I18n.get("programTypeWarnTxt"),
                            JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
                JProgramTypeDialog.this.setVisible(false);
            }
        });
        retVal.add(slo);

        JButton hr = new JButton(I18n.get("drustva"));
        hr.setIconTextGap(20);
        hr.setHorizontalAlignment(SwingConstants.LEFT);
        hr.setHorizontalTextPosition(SwingConstants.RIGHT);
        hr.setIcon(runningUtil.icons.drustva);
        hr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    runningUtil.changeProgramType(ProgramType.DRUSTVA);
                } catch (Exception e) {
                    e.printStackTrace();
                    new JErrorDialog(I18n.get("programTypeSelError"), e);
                }
                if (calledFromSettings) {
                    JOptionPane.showMessageDialog(null,
                            I18n.get("programTypeWarnMsg"),
                            I18n.get("programTypeWarnTxt"),
                            JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
                JProgramTypeDialog.this.setVisible(false);
            }
        });
        retVal.add(hr);

        JButton en = new JButton(I18n.get("organizacije"));
        en.setIconTextGap(20);
        en.setHorizontalAlignment(SwingConstants.LEFT);
        en.setHorizontalTextPosition(SwingConstants.RIGHT);
        en.setIcon(runningUtil.icons.delovneSkupine);
        en.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    runningUtil.changeProgramType(ProgramType.ORGANIZACIJE);
                } catch (Exception e) {
                    e.printStackTrace();
                    new JErrorDialog(I18n.get("programTypeSelError"), e);
                }
                if (calledFromSettings) {
                    JOptionPane.showMessageDialog(null,
                            I18n.get("programTypeWarnMsg"),
                            I18n.get("programTypeWarnTxt"),
                            JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
                JProgramTypeDialog.this.setVisible(false);
            }
        });
        retVal.add(en);

		return retVal;
	}


}
