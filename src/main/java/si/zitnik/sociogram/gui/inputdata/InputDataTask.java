package si.zitnik.sociogram.gui.inputdata;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.pushingpixels.radiance.component.api.common.model.Command;
import org.pushingpixels.radiance.component.api.ribbon.JRibbonBand;
import org.pushingpixels.radiance.component.api.ribbon.RibbonTask;
import si.zitnik.sociogram.entities.Sociogram;
import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.gui.SociogramRibbon;
import si.zitnik.sociogram.interfaces.SociogramTask;
import si.zitnik.sociogram.io.ExcelImporter;
import si.zitnik.sociogram.io.csv.CSVManager;
import si.zitnik.sociogram.io.xml.XMLManager;
import si.zitnik.sociogram.util.RadianceUtil;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

public class InputDataTask implements SociogramTask {
	private RunningUtil runningUtil;
	private InputDataPanel inputDataPanel;
	private SociogramRibbon mainFrame;
	private RibbonTask ribbonTask;

	public InputDataTask(RunningUtil runningUtil, SociogramRibbon sociogramRibbon) {
		this.runningUtil = runningUtil;
		this.inputDataPanel = new InputDataPanel(runningUtil);
		this.mainFrame = sociogramRibbon;
		createBands();
	}

	private void createBands() {
        //create upravljanje
        JRibbonBand socioDataBand = new JRibbonBand(I18n.get("sociogram"), runningUtil.icons.dummyImage);
        {
            RadianceUtil.setResizePolicy(socioDataBand);

            var newButton = Command.builder()
                    .setText(I18n.get("new"))
                    .setIconFactory(runningUtil.icons.socioNov)
                    .setAction(this.mainFrame.getSocioNew().getAction())
                    .build();
            socioDataBand.addRibbonCommand(newButton.project(), JRibbonBand.PresentationPriority.TOP);

            var openButton = Command.builder()
                    .setText(I18n.get("open"))
                    .setIconFactory(runningUtil.icons.socioOdpri)
                    .setAction(this.mainFrame.getSocioOdpri().getAction())
                    .build();
            socioDataBand.addRibbonCommand(openButton.project(), JRibbonBand.PresentationPriority.TOP);

            var saveButton = Command.builder()
                    .setText(I18n.get("save"))
                    .setIconFactory(runningUtil.icons.socioShrani)
                    .setAction(this.mainFrame.getSocioShrani().getAction())
                    .build();
            socioDataBand.addRibbonCommand(saveButton.project(), JRibbonBand.PresentationPriority.TOP);
        }

		//create upravljanje
		JRibbonBand upravljanjeDataBand = new JRibbonBand(I18n.get("management"), runningUtil.icons.dummyImage);
		{
            RadianceUtil.setResizePolicy(upravljanjeDataBand);

			var addButton = Command.builder()
                    .setText(I18n.get("add"))
                    .setIconFactory(runningUtil.icons.dodajOsebo)
                    .setAction(commandActionEvent -> this.inputDataPanel.addPersonLogic())
                    .build();

            upravljanjeDataBand.addRibbonCommand(addButton.project(), JRibbonBand.PresentationPriority.TOP);
	
			var removeButton = Command.builder()
                    .setText(I18n.get("remove"))
                    .setIconFactory(runningUtil.icons.brisiOsebo)
                    .setAction(commandActionEvent -> this.inputDataPanel.removePersonLogic())
                    .build();
            upravljanjeDataBand.addRibbonCommand(removeButton.project(), JRibbonBand.PresentationPriority.TOP);
		}
		
		JRibbonBand uvozDataBand = new JRibbonBand(I18n.get("import"), runningUtil.icons.uvoziOsebe);
		{
            RadianceUtil.setResizePolicy(uvozDataBand);

            var importButton = Command.builder()
                    .setText(I18n.get("fileImport"))
                    .setIconFactory(runningUtil.icons.uvoziOsebe)
                    .setAction(commandActionEvent -> {
                        if (runningUtil.isActivated()) {
                            fileImport();
                        } else {
                            runningUtil.showNotActivatedDialog();
                        }
                    })
                    .build();

            uvozDataBand.addRibbonCommand(importButton.project(), JRibbonBand.PresentationPriority.TOP);

            var importSocButton = Command.builder()
                    .setText(I18n.get("socioPersonImport"))
                    .setIconFactory(runningUtil.icons.uvoziOsebeIzSociograma)
                    .setAction(commandActionEvent -> {
                        if (runningUtil.isActivated()) {
                            personImport();
                        } else {
                            runningUtil.showNotActivatedDialog();
                        }
                    })
                    .build();

            uvozDataBand.addRibbonCommand(importSocButton.project(), JRibbonBand.PresentationPriority.TOP);
		}
		
		JRibbonBand izvozDataBand = new JRibbonBand(I18n.get("export"), runningUtil.icons.izvozOseb);
		{
            RadianceUtil.setResizePolicy(izvozDataBand);

            var exportButton = Command.builder()
                    .setText(I18n.get("exportIntoFile"))
                    .setIconFactory(runningUtil.icons.izvozOseb).
                    setAction(commandActionEvent -> {
                        if (runningUtil.isActivated()) {
                            fileExport();
                        } else {
                            runningUtil.showNotActivatedDialog();
                        }
                    })
                    .build();

            izvozDataBand.addRibbonCommand(exportButton.project(), JRibbonBand.PresentationPriority.TOP);
		}

		//add to tab
        this.ribbonTask = new RibbonTask(I18n.get("pollMemberInput"), socioDataBand, upravljanjeDataBand, uvozDataBand, izvozDataBand);
	}

    private void fileExport() {
        JFileChooser chooser = new JFileChooser(runningUtil.getWorkingDirectory());
        FileNameExtensionFilter filter = new FileNameExtensionFilter(I18n.get("csvOrTxt"), "csv", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(mainFrame);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            String filepath = chooser.getSelectedFile().getAbsolutePath();
            runningUtil.setWorkingDirectory(chooser.getCurrentDirectory().getAbsolutePath());
            if (!filepath.endsWith(".csv")){
                filepath += ".csv";
            }
            CSVManager cm = new CSVManager(filepath);
            try {
                cm.writeData(runningUtil.getPersons());
            } catch (Exception e1) {
                @SuppressWarnings("unused")
                JErrorDialog errorDialog = new JErrorDialog(I18n.get("csvTxtExportErrorMsg"), e1);
            }
        }
    }

    private void personImport() {
        JFileChooser chooser = new JFileChooser(runningUtil.getWorkingDirectory());
        FileNameExtensionFilter filter = new FileNameExtensionFilter(I18n.get("socioFiles"), "socx");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(mainFrame);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            XMLManager xm = new XMLManager(chooser.getSelectedFile().getAbsolutePath());
            runningUtil.setWorkingDirectory(chooser.getCurrentDirectory().getAbsolutePath());
            try {
                runningUtil.addAllPersons(((Sociogram)xm.decodeXML(Sociogram.class)).getPersons());
                ((InputTableModel)inputDataPanel.getTable().getModel()).fireTableDataChanged();
            } catch (Exception e1) {
                @SuppressWarnings("unused")
                JErrorDialog errorDialog = new JErrorDialog(I18n.get("socioImportErrorMsg"), e1);
            }
        }
    }

    private void fileImport() {
        JFileChooser chooser = new JFileChooser(runningUtil.getWorkingDirectory());
        FileNameExtensionFilter filter = new FileNameExtensionFilter(I18n.get("csvOrTxt"), "csv", "txt", "xlsx");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(mainFrame);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            if (chooser.getSelectedFile().getAbsolutePath().endsWith("xlsx")) {
                ExcelImporter cm = new ExcelImporter(chooser.getSelectedFile().getAbsolutePath());
                runningUtil.setWorkingDirectory(chooser.getCurrentDirectory().getAbsolutePath());
                try {
                    runningUtil.addAllPersons(cm.readData());
                    ((InputTableModel)inputDataPanel.getTable().getModel()).fireTableDataChanged();
                } catch (Exception e1) {
                    @SuppressWarnings("unused")
                    JErrorDialog errorDialog = new JErrorDialog(I18n.get("csvTxtErrorMsg"), e1);
                }
            } else {
                CSVManager cm = new CSVManager(chooser.getSelectedFile().getAbsolutePath());
                runningUtil.setWorkingDirectory(chooser.getCurrentDirectory().getAbsolutePath());
                try {
                    runningUtil.addAllPersons(cm.readData());
                    ((InputTableModel)inputDataPanel.getTable().getModel()).fireTableDataChanged();
                } catch (Exception e1) {
                    @SuppressWarnings("unused")
                    JErrorDialog errorDialog = new JErrorDialog(I18n.get("csvTxtErrorMsg"), e1);
                }
            }
        }
    }

    @Override
	public void initMainTaskPanel() {
		this.inputDataPanel.init();
	}

	@Override
	public Component getMainTaskPanel() {
		return this.inputDataPanel;
	}

	@Override
	public RibbonTask getRibbonTask() {
		return this.ribbonTask;
	}

}
