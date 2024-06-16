package si.zitnik.sociogram.gui.socioclass;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import org.pushingpixels.radiance.component.api.common.CommandActionEvent;
import org.pushingpixels.radiance.component.api.common.model.Command;
import org.pushingpixels.radiance.component.api.common.model.CommandToggleGroupModel;
import org.pushingpixels.radiance.component.api.ribbon.JRibbonBand;
import org.pushingpixels.radiance.component.api.ribbon.RibbonTask;
import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.enums.SocioClassification;
import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.gui.SociogramRibbon;
import si.zitnik.sociogram.interfaces.SociogramTask;
import si.zitnik.sociogram.util.*;

public class SocioClassTask implements SociogramTask {
	private RunningUtil runningUtil;
	@SuppressWarnings("unused")
	private SociogramRibbon mainFrame;
	private RibbonTask ribbonTask;
	private JPanel mainPanel;
	private SocioCTable table;

    public SocioClassTask(RunningUtil runningUtil, SociogramRibbon sociogramRibbon) {
		this.runningUtil = runningUtil;
		this.mainFrame = sociogramRibbon;
		createBands();
		createMainPanel();
	}

	private void runAction(CommandActionEvent e, SocioClassification socioClassification) {
		var buttonCommand = e.getCommand();

		buttonCommand.setToggleSelected(true);
		runningUtil.setSelectedClassification(socioClassification);

		((SocioCTableModel)table.getModel()).init();
		((SocioCTableModel)table.getModel()).fireTableDataChanged();
	}

	private void createBands() {
		JRibbonBand selections = new JRibbonBand(I18n.get("viewSelection"), runningUtil.icons.dummyImage);
		{
			RadianceUtil.setResizePolicy(selections);

			var group = new CommandToggleGroupModel();

			var all = Command.builder()
					.setText(I18n.get("all"))
					.setIconFactory(runningUtil.icons.all)
					.inToggleGroupAsSelected(group)
					.setAction(commandActionEvent -> {
                        runAction(commandActionEvent, SocioClassification.ALL);
                    })
					.build();
			var liking = Command.builder()
					.setText(I18n.get("liked"))
					.setIconFactory(runningUtil.icons.liking)
					.inToggleGroup(group)
					.setAction(commandActionEvent -> {
                        runAction(commandActionEvent, SocioClassification.LIKING);
                    })
					.build();
			var controverse = Command.builder()
					.setText(I18n.get("controversial"))
					.setIconFactory(runningUtil.icons.controverse)
					.inToggleGroup(group)
					.setAction(commandActionEvent -> {
                        runAction(commandActionEvent, SocioClassification.CONTROVERSE);
                    })
					.build();
			var unseen = Command.builder()
					.setText(I18n.get("unseen"))
					.setIconFactory(runningUtil.icons.unseen)
					.inToggleGroup(group)
					.setAction(commandActionEvent -> {
                        runAction(commandActionEvent, SocioClassification.UNSEEN);
                    })
					.build();
			var unliking = Command.builder()
					.setText(I18n.get("rejected"))
					.setIconFactory(runningUtil.icons.unliking)
					.inToggleGroup(group)
					.setAction(commandActionEvent -> {
                        runAction(commandActionEvent, SocioClassification.UNLIKING);
                    })
					.build();
			var averages = Command.builder()
					.setText(I18n.get("average"))
					.setIconFactory(runningUtil.icons.average)
					.inToggleGroup(group)
					.setAction(commandActionEvent -> {
                        runAction(commandActionEvent, SocioClassification.AVERAGES);
                    })
					.build();

			selections.addRibbonCommand(all.project(), JRibbonBand.PresentationPriority.TOP);
			selections.addRibbonCommand(averages.project(), JRibbonBand.PresentationPriority.TOP);
			selections.addRibbonCommand(liking.project(), JRibbonBand.PresentationPriority.TOP);
			selections.addRibbonCommand(controverse.project(), JRibbonBand.PresentationPriority.TOP);
			selections.addRibbonCommand(unseen.project(), JRibbonBand.PresentationPriority.TOP);
			selections.addRibbonCommand(unliking.project(), JRibbonBand.PresentationPriority.TOP);
		}
		
		JRibbonBand legend = new JRibbonBand(I18n.get("legend"), runningUtil.icons.pdf);
		{
			RadianceUtil.setResizePolicy(legend);

			var legendButton = Command.builder()
					.setText(I18n.get("legend"))
					.setIconFactory(runningUtil.icons.pdf)
					.setAction(commandActionEvent -> {
                        try {
                            Desktop.getDesktop().open(new File(RunningUtil.normalizedFilepath+"data/"+I18n.getLocale()+"/"+I18n.get("legendFilename")));
                        } catch (Exception e1) {
                            @SuppressWarnings("unused")
                            JErrorDialog errorDialog = new JErrorDialog(I18n.get("helpShowErrorMsg"), e1);
                        }
                    }).build();

			legend.addRibbonCommand(legendButton.project(), JRibbonBand.PresentationPriority.TOP);
		}

        JRibbonBand export = new JRibbonBand(I18n.get("export"), runningUtil.icons.dummyImage);
        {
			RadianceUtil.setResizePolicy(export);

			var exportButton = Command.builder()
					.setText(I18n.get("socioClassExportPNG"))
					.setIconFactory(runningUtil.icons.image)
					.setAction(commandActionEvent -> {
                        ImageSaver imageSaver = new ImageSaver(mainFrame, runningUtil, new SocioImagePanel(runningUtil));
                        imageSaver.saveDialog();
                    }).build();
			export.addRibbonCommand(exportButton.project(), JRibbonBand.PresentationPriority.TOP);

			var exportButtonWord = Command.builder()
					.setText(I18n.get("socioClassExportWord"))
					.setIconFactory(runningUtil.icons.word)
					.setAction(commandActionEvent -> {
                        WordSaver wordSaver = new WordSaver(mainFrame, runningUtil, table);
                        wordSaver.saveDialog();
                    }).build();
            export.addRibbonCommand(exportButtonWord.project(), JRibbonBand.PresentationPriority.TOP);

			var exportButtonExcel = Command.builder()
					.setText(I18n.get("socioClassExportExcel"))
					.setIconFactory(runningUtil.icons.excel)
					.setAction(commandActionEvent -> {
                        ExcelSaver excelSaver = new ExcelSaver(mainFrame, runningUtil, table);
                        excelSaver.saveDialog();
                    }).build();
            export.addRibbonCommand(exportButtonExcel.project(), JRibbonBand.PresentationPriority.TOP);
        }

		this.ribbonTask = new RibbonTask(I18n.get("sociometricClassification"), selections, legend, export);
	}
	
	public void createMainPanel() {
		this.mainPanel = new JPanel(new BorderLayout());
		this.runningUtil.calculateSociometricClassification();
		
		//west
        JPanel socioDataPanel = createDataPanel();
		this.mainPanel.add(socioDataPanel, BorderLayout.WEST);
		
		//east
		table = new SocioCTable(this.runningUtil);
		JScrollPane pane = new JScrollPane(table);
		this.mainPanel.add(pane, BorderLayout.CENTER);
	}

    private JPanel createDataPanel() {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");

        //left panel
        JPanel rightPane = new JPanel(new SpringLayout());
        rightPane.setMaximumSize(new Dimension(400, mainFrame.getHeight()));

        rightPane.add(new JLabel(" "));
        rightPane.add(new JLabel(" "));

        rightPane.add(new JLabel(" "));
        rightPane.add(new JLabel(" "));

        rightPane.add(new JLabel(" "));
        rightPane.add(new JLabel(" "));

        rightPane.add(new JLabel(I18n.get("indexes"), JLabel.LEADING));
        rightPane.add(new JLabel(""));

        rightPane.add(new JLabel(" "));
        rightPane.add(new JLabel(" "));

        rightPane.add(new JLabel("\t" + I18n.get("cohesiveIndexMsg") + ":", JLabel.TRAILING));
        rightPane.add(new JLabel(df.format(this.runningUtil.getIndexKohez())));

        rightPane.add(new JLabel("\t" + I18n.get("groupIntegrationIndex") + ":", JLabel.TRAILING));
        rightPane.add(new JLabel(df.format(this.runningUtil.getIndexGroupIntegration())));

        rightPane.add(new JLabel(" "));
        rightPane.add(new JLabel(" "));

        rightPane.add(new JLabel(" "));
        rightPane.add(new JLabel(" "));

        rightPane.add(new JLabel(I18n.get("socioClassification"), JLabel.LEADING));
        rightPane.add(new JLabel(""));

        rightPane.add(new JLabel(" "));
        rightPane.add(new JLabel(" "));

        HashMap<SocioClassification, ArrayList<Person>> socioResult = this.runningUtil.calculte2DimSocioClassification();
        rightPane.add(new JLabel("\t" + I18n.get("liked") + ":", JLabel.TRAILING));
        rightPane.add(new JLabel(socioResult.get(SocioClassification.LIKING).size() + ""));

        rightPane.add(new JLabel("\t" + I18n.get("rejected") + ":", JLabel.TRAILING));
        rightPane.add(new JLabel(socioResult.get(SocioClassification.UNLIKING).size() + ""));

        rightPane.add(new JLabel("\t" + I18n.get("average") + ":", JLabel.TRAILING));
        rightPane.add(new JLabel(socioResult.get(SocioClassification.AVERAGES).size() + ""));

        rightPane.add(new JLabel("\t" + I18n.get("controversial") + ":", JLabel.TRAILING));
        rightPane.add(new JLabel(socioResult.get(SocioClassification.CONTROVERSE).size() + ""));

        rightPane.add(new JLabel("\t" + I18n.get("unseen") + ":", JLabel.TRAILING));
        rightPane.add(new JLabel(socioResult.get(SocioClassification.UNSEEN).size() + ""));



        JPanel rightPane1 = new JPanel();
        rightPane1.add(rightPane);

        SpringUtilities.makeCompactGrid(rightPane,
                16, 2,       //rows, cols
                6, 6,       //initX, initY
                6, 6);      //xPad, yPad
        return rightPane1;
    }

    public RibbonTask getRibbonTask() {
		return this.ribbonTask;
	}

	public Component getMainTaskPanel() {
		return this.mainPanel;
	}

	public void initMainTaskPanel() {
		createMainPanel();
	}

}
