package si.zitnik.sociogram.error;

import si.zitnik.sociogram.util.I18n;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class JErrorDialog extends JDialog {
	private static final long serialVersionUID = -629509510338878206L;

	public JErrorDialog(String string, Exception e) {
		e.printStackTrace();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setModal(true);
		
		Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int width = 700;
		int height = 412;
		this.setPreferredSize(new Dimension(width,height));
		this.setLocation(r.width/2-width/2, r.height/2-height/2);
		
		this.setTitle(I18n.get("error"));
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		this.getContentPane().setLayout(layout);
		this.getContentPane().add(new JLabel(I18n.get("errorHappened")));
		JTextArea label = new JTextArea(I18n.get("errorDesc")+": "+string);
		label.setWrapStyleWord(true);
		label.setEditable(false);
		label.setLineWrap(true);
		JScrollPane paneL = new JScrollPane(label);
		paneL.setPreferredSize(new Dimension(680, 40));
		paneL.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.getContentPane().add(paneL);

        StringBuffer sb = new StringBuffer(I18n.get("errorMesageDetail") + ": " + System.lineSeparator() + System.lineSeparator());
        for (StackTraceElement st : e.getStackTrace()) {
            sb.append(st);
        }

		JTextArea textArea = new JTextArea(I18n.get("errorMesageDetailShort") + ": " + e.getMessage() + System.lineSeparator() + sb);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		JScrollPane pane = new JScrollPane(textArea);
		pane.setPreferredSize(new Dimension(680, 270));
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.getContentPane().add(pane);
		JButton ok = new JButton(I18n.get("ok"));
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JErrorDialog.this.setVisible(false);
			}
		});
		this.getContentPane().add(ok);
		
		
		
		this.pack();
		this.setVisible(true);
	}

}


