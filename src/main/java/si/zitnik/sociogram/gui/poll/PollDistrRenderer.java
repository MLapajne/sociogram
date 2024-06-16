package si.zitnik.sociogram.gui.poll;

import org.jfree.chart.renderer.category.BarRenderer;
import si.zitnik.sociogram.enums.LikingType;

import java.awt.*;

public class PollDistrRenderer extends BarRenderer {

    private final LikingType likingType;

    public PollDistrRenderer(LikingType likingType) {
        this.likingType = likingType;
    }

    @Override
    public Paint getItemPaint(int row, int column) {
        if (likingType.equals(LikingType.POSITIVE)) {
            return Color.green;
        } else {
            return Color.red;
        }
    }
}
