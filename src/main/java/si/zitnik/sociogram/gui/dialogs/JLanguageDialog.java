package si.zitnik.sociogram.gui.dialogs;

import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JLanguageDialog extends JDialog {
	private static final long serialVersionUID = -1L;
    private final boolean calledFromSettings;
    private RunningUtil runningUtil;

	public JLanguageDialog(JFrame frame, RunningUtil runningUtil, boolean calledFromSettings) throws Exception {
		super(frame);
		this.setTitle(I18n.get("languageSelection"));
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
		retVal.setLayout(new GridLayout(4, 1));
		retVal.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), I18n.get("languageSelection")));

        JButton slo = new JButton(I18n.get("sloLang"));
        slo.setIconTextGap(20);
        slo.setHorizontalAlignment(SwingConstants.LEFT);
        slo.setHorizontalTextPosition(SwingConstants.RIGHT);
        slo.setIcon(runningUtil.icons.sl);
        slo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    runningUtil.changeLanguage("sl");
                } catch (Exception e) {
                    e.printStackTrace();
                    new JErrorDialog(I18n.get("lanSelError"), e);
                }
                if (calledFromSettings) {
                    JOptionPane.showMessageDialog(null,
                            I18n.get("languageChangeWarnMsg"),
                            I18n.get("languageChangeWarnTxt"),
                            JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
                JLanguageDialog.this.setVisible(false);
            }
        });
        retVal.add(slo);

        JButton hr = new JButton(I18n.get("hrLang"));
        hr.setIconTextGap(20);
        hr.setHorizontalAlignment(SwingConstants.LEFT);
        hr.setHorizontalTextPosition(SwingConstants.RIGHT);
        hr.setIcon(runningUtil.icons.hr);
        hr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    runningUtil.changeLanguage("hr");
                } catch (Exception e) {
                    e.printStackTrace();
                    new JErrorDialog(I18n.get("lanSelError"), e);
                }
                if (calledFromSettings) {
                    JOptionPane.showMessageDialog(null,
                            I18n.get("languageChangeWarnMsg"),
                            I18n.get("languageChangeWarnTxt"),
                            JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
                JLanguageDialog.this.setVisible(false);
            }
        });
        retVal.add(hr);

        JButton en = new JButton(I18n.get("enLang"));
        en.setIconTextGap(20);
        en.setHorizontalAlignment(SwingConstants.LEFT);
        en.setHorizontalTextPosition(SwingConstants.RIGHT);
        en.setIcon(runningUtil.icons.en);
        en.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    runningUtil.changeLanguage("en");
                } catch (Exception e) {
                    e.printStackTrace();
                    new JErrorDialog(I18n.get("lanSelError"), e);
                }
                if (calledFromSettings) {
                    JOptionPane.showMessageDialog(null,
                            I18n.get("languageChangeWarnMsg"),
                            I18n.get("languageChangeWarnTxt"),
                            JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
                JLanguageDialog.this.setVisible(false);
            }
        });
        retVal.add(en);

        JButton de = new JButton(I18n.get("deLang"));
        de.setIconTextGap(20);
        de.setHorizontalAlignment(SwingConstants.LEFT);
        de.setHorizontalTextPosition(SwingConstants.RIGHT);
        de.setIcon(runningUtil.icons.de);
        de.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    runningUtil.changeLanguage("de");
                } catch (Exception e) {
                    e.printStackTrace();
                    new JErrorDialog(I18n.get("lanSelError"), e);
                }
                if (calledFromSettings) {
                    JOptionPane.showMessageDialog(null,
                            I18n.get("languageChangeWarnMsg"),
                            I18n.get("languageChangeWarnTxt"),
                            JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
                JLanguageDialog.this.setVisible(false);
            }
        });
        retVal.add(de);

		return retVal;
	}


}
