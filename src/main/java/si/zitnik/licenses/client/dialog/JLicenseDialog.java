package si.zitnik.licenses.client.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import si.zitnik.licenses.client.Activator;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

public class JLicenseDialog extends JDialog
{
    private RunningUtil runningUtil;
    private boolean activated;
    private JTextArea serialCodeArea;
    private Activator activator;

    public JLicenseDialog(Frame frame, String title, RunningUtil runningUtil)
    {
        super(frame, title);
        setModal(true);
        getContentPane().add(makePanel());
        this.activated = false;

        this.runningUtil = runningUtil;

        Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        setPreferredSize(new Dimension(400, 150));
        setLocation(r.width / 2 - (int)getPreferredSize().getWidth() / 2,
                r.height / 2 - (int)getPreferredSize().getHeight() / 2);
        setDefaultCloseOperation(0);
    }

    private JPanel makePanel() {
        JPanel retVal = new JPanel(new BorderLayout());

        JLabel text = new JLabel(I18n.get("inputSerial") + ": ", 2);
        JLabel textMsg = new JLabel(I18n.get("activationMessage"));
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(textMsg);
        northPanel.add(text);

        this.serialCodeArea = new JTextArea(1, 50);
        this.serialCodeArea.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent arg0) {
            }

            public void keyReleased(KeyEvent arg0) {
                if (arg0.getKeyCode() == 10)
                    try {
                        JLicenseDialog.this.activateAction();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
            }

            public void keyPressed(KeyEvent arg0)
            {
            }
        });
        JPanel bottomPanel = new JPanel();
        JButton ok = new JButton(I18n.get("ok"));
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                try {
                    JLicenseDialog.this.activateAction();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        JButton cancel = new JButton(I18n.get("cancel"));
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0) {
                JLicenseDialog.this.activated = false;
                JLicenseDialog.this.setVisible(false);
            }
        });
        bottomPanel.add(ok);
        bottomPanel.add(cancel);

        retVal.add(northPanel, "North");
        retVal.add(this.serialCodeArea, "Center");
        retVal.add(bottomPanel, "South");

        return retVal;
    }

    public void activateAction() throws Exception {
        String serialCode = this.serialCodeArea.getText();

        this.activator = new Activator(serialCode, this.runningUtil);
        if (this.activator.activate()) {
            this.activated = true;
            setVisible(false);
        } else {
            JOptionPane.showOptionDialog(this,
                    I18n.get("wrongSerialNumber"),
                    I18n.get("wrongSerialNumberTitle"),
                    0,
                    2,
                    null,
                    new String[] { I18n.get("ok") }, I18n.get("ok"));
        }
    }

    public boolean showLicenseDialog() {
        pack();
        setVisible(true);

        while (isVisible()) {
            try {
                Thread.currentThread(); Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return this.activated;
    }

    public String getOwnerOfficialName() throws Exception {
        return this.activator.getOwnerOfficialName();
    }

    public static void main(String[] args)
    {
    }
}