package si.zitnik.sociogram.gui.dialogs;

import si.zitnik.licenses.client.dialog.JLicenseDialog;
import si.zitnik.sociogram.SociogramRunner;
import si.zitnik.sociogram.config.SociogramConstants;
import si.zitnik.sociogram.entities.Gender;
import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.entities.Sociogram;
import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JRegistrationDialog extends JDialog {
	private static final long serialVersionUID = -1L;
    private final boolean calledFromSettings;
    private RunningUtil runningUtil;

	public JRegistrationDialog(JFrame frame, RunningUtil runningUtil, boolean calledFromSettings) throws Exception {
		super(frame);
		this.setTitle(I18n.get("registrationSelection"));
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
		retVal.setLayout(new GridLayout(2, 1));
		retVal.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), I18n.get("registrationSelection")));

        JButton demo = new JButton(I18n.get("demo"));
        demo.setIconTextGap(20);
        demo.setLayout(new BorderLayout());
        demo.setIcon(runningUtil.icons.demo);
        demo.setHorizontalAlignment(SwingConstants.LEFT);
        demo.setHorizontalTextPosition(SwingConstants.RIGHT);
        demo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                fillDummyData();
                JRegistrationDialog.this.setVisible(false);
            }
        });
        retVal.add(demo);

        JButton activation = new JButton(I18n.get("buyOrRegister"));
        activation.setIcon(runningUtil.icons.buy);
        activation.setIconTextGap(20);
        activation.setHorizontalAlignment(SwingConstants.LEFT);
        activation.setHorizontalTextPosition(SwingConstants.RIGHT);
        activation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    JLicenseDialog dialog = new JLicenseDialog(null,
                            I18n.get("activationTitle") + " " + SociogramConstants.PROGRAM_NAME,
                            runningUtil);
                    if (dialog.showLicenseDialog()){
                        runningUtil.setActivated(dialog.getOwnerOfficialName());
                        JRegistrationDialog.this.setVisible(false);
                    }
                } catch (Exception e) {
                    JDialog errorDialog = new JErrorDialog(I18n.get("registrationError"), e);
                    e.printStackTrace();
                }
            }
        });
        retVal.add(activation);




		return retVal;
	}

    private void fillDummyData() {
        Sociogram sociogram = runningUtil.getSociogram();

        sociogram.setComment(I18n.get("demo_comment"));
        sociogram.setDate(I18n.get("demo_date"));
        sociogram.setGrader(I18n.get("demo_grader"));

        for (String name : I18n.get("demo_maleParticipant").split(";")) {
            String[] splitName = name.split(" ");
            runningUtil.addPerson(new Person(-1, splitName[0], splitName[1], Gender.MALE));
        }
        for (String name : I18n.get("demo_femaleParticipant").split(";")) {
            String[] splitName = name.split(" ");
            runningUtil.addPerson(new Person(-1, splitName[0], splitName[1], Gender.FEMALE));
        }


    }


}
