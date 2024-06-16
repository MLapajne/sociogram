package si.zitnik.sociogram.io.printing;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class TextPrinter {
	/**
	 * Splits string by space in writes in one or more lines. 
	 * Return next line y to draw.
	 */
	public static int printText(Graphics2D g2d, int width, int lineHeight, int startx, int starty, String text){
		String[] words = text.split(" ");
		FontMetrics fm = g2d.getFontMetrics();
		
		int curx = startx;
		int cury = starty;
		for (int i = 0 ; i<words.length; i++){
			if (fm.stringWidth(words[i]+" ")+curx > width){
				curx = startx;
				cury += lineHeight;
			}
			g2d.drawString(words[i]+" ", curx, cury);
			curx += fm.stringWidth(words[i]+" ");
		}
		
		return cury+lineHeight;
	}
}
