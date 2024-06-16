package si.zitnik.sociogram.util;


import org.pushingpixels.radiance.component.api.ribbon.JFlowRibbonBand;
import org.pushingpixels.radiance.component.api.ribbon.JRibbonBand;
import org.pushingpixels.radiance.component.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.radiance.component.api.ribbon.resize.RibbonBandResizePolicy;

import java.util.ArrayList;
import java.util.List;

public class RadianceUtil {

    /**
     * This is actually "CoreRibbonResizePolicies.getCorePoliciesPermissive(BAND))" without high2mid.
     * High2Mid threw errors when upgrading to 1.0.0 version.
     * @param ribbonBand
     */
    public static void setResizePolicy(JRibbonBand ribbonBand) {

        List<RibbonBandResizePolicy> policies = new ArrayList<RibbonBandResizePolicy>();
        policies.add(new CoreRibbonResizePolicies.None(ribbonBand));
        policies.add(new CoreRibbonResizePolicies.Low2Mid(ribbonBand));
        policies.add(new CoreRibbonResizePolicies.Mid2Mid(ribbonBand));
        policies.add(new CoreRibbonResizePolicies.Mirror(ribbonBand));
        //policies.add(new CoreRibbonResizePolicies.Mid2Low(ribbonBand));
        //policies.add(new CoreRibbonResizePolicies.High2Mid(ribbonBand));
        //policies.add(new CoreRibbonResizePolicies.High2Low(ribbonBand));
        //policies.add(new CoreRibbonResizePolicies.IconRibbonBandResizePolicy(ribbonBand));
        ribbonBand.setResizePolicies(policies);
    }
}
