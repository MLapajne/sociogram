package si.zitnik.sociogram;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javax.swing.*;

import org.pushingpixels.radiance.common.api.RadianceCommonCortex;
import org.pushingpixels.radiance.theming.api.RadianceLookAndFeel;
import org.pushingpixels.radiance.theming.api.RadianceThemingCortex;
import org.pushingpixels.radiance.theming.api.skin.*;
import si.zitnik.sociogram.entities.Sociogram;
import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.gui.dialogs.JLanguageDialog;
import si.zitnik.sociogram.gui.dialogs.JProgramTypeDialog;
import si.zitnik.sociogram.gui.dialogs.JRegistrationDialog;
import si.zitnik.sociogram.gui.SociogramRibbon;
import si.zitnik.sociogram.io.xml.XMLManager;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

public class SociogramRunner {
	/**
	 * @param args
	 * @throws InvocationTargetException 
	 * @throws InterruptedException 
	 */
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//TODO: check all exceptions and report them!!!
				JFrame.setDefaultLookAndFeelDecorated(true);
				RadianceThemingCortex.GlobalScope.setSkin(new BusinessBlueSteelSkin());

				SociogramRibbon c = null;
				RunningUtil runningUtil = null;
				try {
					runningUtil = new RunningUtil();
					System.out.println("Sociogram arguments: " + Arrays.asList(args));
					if (args.length == 1 && runningUtil.isActivated()){
						//RunningUtil.normalizedFilepath = args[1];
                        //if (!RunningUtil.normalizedFilepath.endsWith("/")) {
                            //RunningUtil.normalizedFilepath += "/";
                        //}
						XMLManager xm = new XMLManager(args[0]);
				    	try {
							runningUtil.copyDataFrom((Sociogram)xm.decodeXML(Sociogram.class));
						} catch (Exception e) {}
					}

                    if (!runningUtil.isLanguageSelected()) {
                        JLanguageDialog dialog = new JLanguageDialog(null, runningUtil, false);
                        runningUtil.setLanguageSelected();
                    }

                    if (!runningUtil.isProgramTypeSelected()) {
                        JProgramTypeDialog dialog = new JProgramTypeDialog(null, runningUtil, false);
                        runningUtil.setProgramTypeSelected();
                    }


					if (!runningUtil.isActivated()){
                        JRegistrationDialog dialog = new JRegistrationDialog(null, runningUtil, false);
					}

					c = new SociogramRibbon(runningUtil);
				} catch (Exception e) {
					@SuppressWarnings("unused")
					JDialog errorDialog = new JErrorDialog(I18n.get("severeError"), e);
					e.printStackTrace();
					System.exit(-1);
				}
				
				//position window
				Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
				int width = (int) Math.ceil(r.width/1.1);
				int height = (r.height >= 800) ? 800 : r.height;
				c.setPreferredSize(new Dimension(width, height));
				c.pack();
				c.setLocation(r.width/2-width/2, r.height/2-height/2);
				c.setVisible(true);
				c.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

				RunningUtil finalRunningUtil = runningUtil;
				SociogramRibbon finalC = c;
				c.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e)
					{
						finalRunningUtil.exitSociogram(finalC);
					}
				});


			}
		});
	}

}
