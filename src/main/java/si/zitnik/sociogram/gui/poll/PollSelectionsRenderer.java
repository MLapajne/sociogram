package si.zitnik.sociogram.gui.poll;

import org.jfree.chart.renderer.category.BarRenderer;
import si.zitnik.sociogram.enums.LikingType;

import java.awt.*;

public class PollSelectionsRenderer extends BarRenderer {

    @Override
    public Paint getItemPaint(int row, int column) {
        if (row == 1) {
            return Color.green;
        } else {
            return Color.red;
        }
    }
}
