package si.zitnik.sociogram.util;

import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.gui.SociogramRibbon;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by slavkoz on 13/09/14.
 */
public class ImageSaver {

    private SociogramRibbon mainFrame;
    private RunningUtil runningUtil;
    private JPanel imagePanel;

    public ImageSaver(SociogramRibbon mainFrame, RunningUtil runningUtil, JPanel imagePanel) {
        this.mainFrame = mainFrame;
        this.runningUtil = runningUtil;
        this.imagePanel = imagePanel;
    }

    public void saveDialog() {
        final JDialog dialog = new JDialog(mainFrame);
        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        dialog.setModal(true);

        JPanel dialogPanel = new JPanel(new BorderLayout());

        JButton ok = new JButton(I18n.get("saveImage"));
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    JFileChooser chooser = new JFileChooser(runningUtil.getWorkingDirectory());
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(I18n.get("imagesPNG"), "png");
                    chooser.setFileFilter(filter);
                    int returnVal = chooser.showSaveDialog(mainFrame);
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        String saveFile = chooser.getSelectedFile().getAbsolutePath();
                        runningUtil.setWorkingDirectory(chooser.getCurrentDirectory().getAbsolutePath());
                        if (!saveFile.endsWith(".png")){
                            saveFile += ".png";
                        }

                        //save
                        export(imagePanel, saveFile);
                    }
                } catch (Exception e1) {
                    @SuppressWarnings("unused")
                    JErrorDialog errorDialog = new JErrorDialog(I18n.get("helpShowErrorMsg"), e1);
                }

                dialog.setVisible(false);
            }
        });
        JButton cancel = new JButton(I18n.get("cancel"));
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                dialog.setVisible(false);
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(ok);
        topPanel.add(cancel);
        dialogPanel.add(topPanel, BorderLayout.NORTH);

        dialogPanel.add(imagePanel, BorderLayout.CENTER);

        dialog.getContentPane().add(dialogPanel);
        dialog.pack();
        Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        dialog.setLocation(r.width/2-dialog.getWidth()/2, r.height/2-dialog.getHeight()/2);
        dialog.setVisible(true);
    }

    private void export(JPanel imagePanel, String saveFile) throws IOException {
        BufferedImage bi = new BufferedImage(imagePanel.getSize().width, imagePanel.getSize().height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        imagePanel.paint(g);
        g.dispose();
        ImageIO.write(bi, "png", new File(saveFile));
    }
}
