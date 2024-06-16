package si.zitnik.sociogram.gui.poll;

import javax.swing.*;
import java.awt.*;

/**
 * Created by slavkoz on 18/10/14.
 */
public class SquarePanel extends JPanel {
    private int size;
    private Color fillColor;

    public SquarePanel(int size, Color fillColor) {
        super();
        this.size = size;
        this.fillColor = fillColor;
        this.setPreferredSize(new Dimension(size, size));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(fillColor);
        g.fillRect(0, 0, size, size);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, size-1, size-1);
    }
}
