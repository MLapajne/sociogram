package si.zitnik.sociogram.interfaces;



import org.pushingpixels.radiance.component.api.ribbon.RibbonTask;

import java.awt.Component;


public interface SociogramTask {
	public void initMainTaskPanel();
	public Component getMainTaskPanel();
	public RibbonTask getRibbonTask();
}
