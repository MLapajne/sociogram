package si.zitnik.sociogram.gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.pushingpixels.radiance.common.api.RadianceCommonCortex;
import org.pushingpixels.radiance.component.api.common.CommandButtonPresentationState;
import org.pushingpixels.radiance.component.api.common.RichTooltip;
import org.pushingpixels.radiance.component.api.common.model.Command;
import org.pushingpixels.radiance.component.api.common.model.CommandButtonPresentationModel;
import org.pushingpixels.radiance.component.api.common.model.CommandGroup;
import org.pushingpixels.radiance.component.api.common.model.CommandMenuContentModel;
import org.pushingpixels.radiance.component.api.ribbon.JRibbonFrame;
import org.pushingpixels.radiance.component.api.ribbon.RibbonApplicationMenu;
import org.pushingpixels.radiance.component.api.ribbon.model.RibbonTaskbarCommandButtonPresentationModel;
import org.pushingpixels.radiance.component.api.ribbon.projection.RibbonApplicationMenuCommandButtonProjection;
import org.pushingpixels.radiance.component.api.ribbon.projection.RibbonTaskbarCommandButtonProjection;
import si.zitnik.sociogram.SociogramRunner;
import si.zitnik.sociogram.config.SociogramConstants;
import si.zitnik.sociogram.entities.Sociogram;
import si.zitnik.sociogram.enums.LikingType;
import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.gui.dialogs.JInfoDialog;
import si.zitnik.sociogram.gui.dialogs.JLanguageDialog;
import si.zitnik.sociogram.gui.dialogs.JProgramTypeDialog;
import si.zitnik.sociogram.gui.dialogs.JRegistrationDialog;
import si.zitnik.sociogram.gui.graph.GraphTask;
import si.zitnik.sociogram.gui.inputdata.InputDataTask;
import si.zitnik.sociogram.gui.poll.PollTask;
import si.zitnik.sociogram.gui.socioclass.SocioClassTask;
import si.zitnik.sociogram.icons.IconCache;
import si.zitnik.sociogram.interfaces.SociogramTask;
import si.zitnik.sociogram.io.xml.XMLManager;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;


public class SociogramRibbon extends JRibbonFrame implements WindowStateListener, ComponentListener {
	private static final long serialVersionUID = -2393198387152700514L;
	private RunningUtil runningUtil;
	private InputDataTask inputDataTask;
	private PollTask pollTask;
	private GraphTask graphTask;
	private SocioClassTask socioClass;

    //buttons
    private Command socioNew;
    private Command socioOdpri;
    private Command socioShrani;

    public SociogramRibbon(final RunningUtil runningUtil) throws Exception {
		super(SociogramConstants.PROGRAM_NAME + " - " + runningUtil.getInstitution());
		this.runningUtil = runningUtil;

		createApplicationMenu();
		createTaskBar();
		inputDataTask = new InputDataTask(runningUtil, this);
		pollTask = new PollTask(runningUtil, this);
		graphTask = new GraphTask(runningUtil, this);
		socioClass = new SocioClassTask(runningUtil, this);

		this.getRibbon().addTask(inputDataTask.getRibbonTask());
		this.getRibbon().addTask(pollTask.getRibbonTask());
		this.getRibbon().addTask(graphTask.getRibbonTask());
		this.getRibbon().addTask(socioClass.getRibbonTask());


		this.getRibbon().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent propertyEvent) {
				if (propertyEvent.getPropertyName().equals("selectedTask")){
					if (propertyEvent.getOldValue() == inputDataTask.getRibbonTask()) {
						getContentPane().remove(inputDataTask.getMainTaskPanel());	
					} else if (propertyEvent.getOldValue() == pollTask.getRibbonTask()) {
						getContentPane().remove(pollTask.getMainTaskPanel());	
					} else if (propertyEvent.getOldValue() == graphTask.getRibbonTask()) {
						getContentPane().remove(graphTask.getMainTaskPanel());	
					} else if (propertyEvent.getOldValue() == socioClass.getRibbonTask()) {
						getContentPane().remove(socioClass.getMainTaskPanel());	
					}
					if (propertyEvent.getNewValue() == inputDataTask.getRibbonTask()) {
						inputDataTask.initMainTaskPanel();
						getContentPane().add(inputDataTask.getMainTaskPanel(), BorderLayout.CENTER);	
					} else if (propertyEvent.getNewValue() == pollTask.getRibbonTask()) {
						pollTask.initMainTaskPanel();
						getContentPane().add(pollTask.getMainTaskPanel(), BorderLayout.CENTER);	
					} else if (propertyEvent.getNewValue() == graphTask.getRibbonTask()) {
						graphTask.initMainTaskPanel();
						getContentPane().add(graphTask.getMainTaskPanel(), BorderLayout.CENTER);
					} else if (propertyEvent.getNewValue() == socioClass.getRibbonTask()) {
						socioClass.initMainTaskPanel();
						getContentPane().add(socioClass.getMainTaskPanel(), BorderLayout.CENTER);	
					}
					repaint();

					if (propertyEvent.getNewValue() == graphTask.getRibbonTask() ||
						propertyEvent.getNewValue() == socioClass.getRibbonTask()) {
						if (!isPollFinished()) {
							proceedTOShowEvaluationPart();
						}
					}
				}


				
			}
		});
		this.getContentPane().add(inputDataTask.getMainTaskPanel(), BorderLayout.CENTER);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void proceedTOShowEvaluationPart() {
		Object[] options = {I18n.get("ok")};
		int response = JOptionPane.showOptionDialog(SociogramRibbon.this,
				I18n.get("pollFinishedWarningMsg"),
				I18n.get("warning"),
				JOptionPane.YES_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				options,
				options[0]);
	}

	private void createTaskBar() {
		var taskbarButtonInfo = Command.builder()
				.setIconFactory(runningUtil.icons.info)
				.setAction(commandActionEvent -> {
					try {
						@SuppressWarnings("unused")
						JInfoDialog jinfoDialog = new JInfoDialog(SociogramRibbon.this, runningUtil);
					} catch (Exception e1) {
						@SuppressWarnings("unused")
						JErrorDialog errorDialog = new JErrorDialog(I18n.get("errorShowingWindowMsg"), e1);
					}
				}).setActionRichTooltip(RichTooltip.builder()
								.setTitle(I18n.get("info"))
								.addDescriptionSection(I18n.get("showProgramInfo"))
								.addDescriptionSection(I18n.get("showProgramInfoTooltip"))
								.build()
				)
				.build();
		this.getRibbon().addTaskbarCommand(new RibbonTaskbarCommandButtonProjection(
				taskbarButtonInfo,
				RibbonTaskbarCommandButtonPresentationModel.builder().build()));

		var taskbarButtonHelp = Command.builder()
				.setIconFactory(runningUtil.icons.help)
				.setAction(commandActionEvent -> {
					try {
						Desktop.getDesktop().open(new File(RunningUtil.normalizedFilepath+"data/"+I18n.getLocale()+"/"+I18n.get("helpFile")));
					} catch (Exception e1) {
						@SuppressWarnings("unused")
						JErrorDialog errorDialog = new JErrorDialog(I18n.get("helpShowErrorMsg"), e1);
					}
				}).setActionRichTooltip(RichTooltip.builder()
						.setTitle(I18n.get("help"))
						.addDescriptionSection(I18n.get("showHelp"))
						.addDescriptionSection(I18n.get("showHelpTooltip"))
						.build()
				).build();
		this.getRibbon().addTaskbarCommand(new RibbonTaskbarCommandButtonProjection(
				taskbarButtonHelp,
				RibbonTaskbarCommandButtonPresentationModel.builder().build()));
	}

	private void createApplicationMenu() {

		socioNew = Command.builder()
				.setIconFactory(runningUtil.icons.socioNov)
				.setText(I18n.get("new"))
				.setAction(commandActionEvent -> {
					if (runningUtil.isActivated()) {
						socioNov();
					} else {
						runningUtil.showNotActivatedDialog();
					}
				})
				.build();

		socioOdpri = Command.builder()
				.setIconFactory(runningUtil.icons.socioOdpri)
				.setText(I18n.get("open"))
				.setAction(commandActionEvent -> {
					if (runningUtil.isActivated()) {
						socioOdpri();
					} else {
						runningUtil.showNotActivatedDialog();
					}
				})
				.build();


		socioShrani = Command.builder()
				.setIconFactory(runningUtil.icons.socioShrani)
				.setText(I18n.get("save"))
				.setAction(commandActionEvent -> {
					if (!SociogramRibbon.this.runningUtil.hasBeenSaved()){
						saveAs();
					} else {
						String saveFile = SociogramRibbon.this.runningUtil.getSaveFile();
						if (!saveFile.endsWith(".socx")){
							saveFile += ".socx";
						}
						SociogramRibbon.this.save(saveFile);
					}
				})
				.build();

		var socioShraniKot = Command.builder()
				.setText(I18n.get("saveAs"))
				.setIconFactory(runningUtil.icons.socioShrani)
				.setAction(commandActionEvent -> saveAs())
				.build();

        var jezikSettings = Command.builder()
				.setText(I18n.get("languageSettings"))
				.setIconFactory(runningUtil.icons.jezikSettings)
				.setAction(commandActionEvent -> {
					try {
						JLanguageDialog dialog = new JLanguageDialog(null, runningUtil, true);
					} catch (Exception e1) {
						e1.printStackTrace();
						JErrorDialog errorDialog = new JErrorDialog(I18n.get("errorHappened"), e1);
					}
				}).build();

        var registracijaSettings = Command.builder()
				.setText(I18n.get("registrationSettings"))
				.setIconFactory(runningUtil.icons.registracijaSettings)
				.setAction(commandActionEvent -> {
					try {
						JRegistrationDialog dialog = new JRegistrationDialog(null, runningUtil, true);
					} catch (Exception e1) {
						e1.printStackTrace();
						JErrorDialog errorDialog = new JErrorDialog(I18n.get("errorHappened"), e1);
					}
				}).build();

        var tipProgramaSettings = Command.builder()
				.setText(I18n.get("programTypeSettings"))
				.setIconFactory(runningUtil.icons.tipProgramaSettings)
				.setAction(commandActionEvent -> {
					try {
						JProgramTypeDialog dialog = new JProgramTypeDialog(null, runningUtil, true);
					} catch (Exception e1) {
						e1.printStackTrace();
						JErrorDialog errorDialog = new JErrorDialog(I18n.get("errorHappened"), e1);
					}
				}).build();

		CommandMenuContentModel settingsMenu = null;
		if (runningUtil.isActivated()) {
			settingsMenu = new CommandMenuContentModel(new CommandGroup(
					I18n.get("settings"), jezikSettings, tipProgramaSettings));
		} else {
			settingsMenu = new CommandMenuContentModel(new CommandGroup(
					I18n.get("settings"), jezikSettings, registracijaSettings, tipProgramaSettings));
		}

		var socioSettings = Command.builder()
				.setText(I18n.get("settings"))
				.setIconFactory(runningUtil.icons.socioSettings)
				.setAction(commandActionEvent -> {}) //ker je glavni gumb in ima podgumbe za nastavitve
				.setSecondaryContentModel(settingsMenu)
				.build();
		
		var socioIzhod = Command.builder()
				.setText(I18n.get("exit"))
				.setIconFactory(runningUtil.icons.socioIzhod)
				.setAction(commandActionEvent -> {
					runningUtil.exitSociogram(SociogramRibbon.this);
				}).build();

		try {
			this.setIconImages(Arrays.asList(
					ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/world_write_16.png")),
					ImageIO.read(new FileInputStream(RunningUtil.normalizedFilepath+"icons/world_write_24.png"))));
			if (RadianceCommonCortex.getPlatform() != RadianceCommonCortex.Platform.WINDOWS) {
				this.setApplicationIcon(runningUtil.icons.socioLogo);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		RibbonApplicationMenu applicationMenu = new RibbonApplicationMenu(
				new CommandGroup(this.socioNew, this.socioOdpri, this.socioShrani, socioShraniKot),
				new CommandGroup(socioSettings),
				new CommandGroup(socioIzhod));

		RibbonApplicationMenuCommandButtonProjection ribbonMenuCommandProjection =
				new RibbonApplicationMenuCommandButtonProjection(
						Command.builder()
								.setText(I18n.get("file"))
								.setIconFactory(runningUtil.icons.unseen)
                        .setSecondaryContentModel(applicationMenu)
				.build(),
				CommandButtonPresentationModel.builder().setPopupKeyTip("F").build());

		Map<Command, CommandButtonPresentationState> applicationMenuSecondaryStates = new HashMap<>();
		applicationMenuSecondaryStates.put(socioSettings,
				RibbonApplicationMenuCommandButtonProjection.RIBBON_APP_MENU_SECONDARY_LEVEL);
		ribbonMenuCommandProjection.setSecondaryLevelCommandPresentationState(applicationMenuSecondaryStates);

		this.getRibbon().setApplicationMenuCommand(ribbonMenuCommandProjection);

		// keytips
		Set<RibbonKeyboardAction> keyboardActions = new HashSet<>();
		keyboardActions.add(new RibbonKeyboardAction("new",
				(RadianceCommonCortex.getPlatform() == RadianceCommonCortex.Platform.MACOS)
						? KeyStroke.getKeyStroke("meta N")
						: KeyStroke.getKeyStroke("ctrl N"),
				socioNew));
		keyboardActions.add(new RibbonKeyboardAction("open",
				(RadianceCommonCortex.getPlatform() == RadianceCommonCortex.Platform.MACOS)
						? KeyStroke.getKeyStroke("meta O")
						: KeyStroke.getKeyStroke("ctrl O"),
				socioOdpri));
		keyboardActions.add(new RibbonKeyboardAction("save",
				(RadianceCommonCortex.getPlatform() == RadianceCommonCortex.Platform.MACOS)
						? KeyStroke.getKeyStroke("meta S")
						: KeyStroke.getKeyStroke("ctrl S"),
				socioShrani));
		keyboardActions.add(new RibbonKeyboardAction("exit",
				(RadianceCommonCortex.getPlatform() == RadianceCommonCortex.Platform.MACOS)
						? KeyStroke.getKeyStroke("meta X")
						: KeyStroke.getKeyStroke("ctrl X"),
				socioIzhod));

		this.setKeyboardActions(keyboardActions);
	}

    private void socioOdpri() {
        int result = JOptionPane.showOptionDialog(this,
                I18n.get("openDocumentWarn"),
                I18n.get("openDocument"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                new String[]{I18n.get("yes"), I18n.get("no")}, I18n.get("no"));
        if (result == JOptionPane.YES_OPTION){
            this.open();
            this.inputDataTask.initMainTaskPanel();
            this.getRibbon().setSelectedTask(this.inputDataTask.getRibbonTask());
        }
    }

    private void socioNov() {
        int result = JOptionPane.showOptionDialog(this,
                I18n.get("newDocumentWarn"),
                I18n.get("newDoc"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                new String[]{I18n.get("yes"), I18n.get("no")}, I18n.get("no"));
        if (result == JOptionPane.YES_OPTION){
            try {
                this.runningUtil.init();
                this.inputDataTask.initMainTaskPanel();
                this.getRibbon().setSelectedTask(this.inputDataTask.getRibbonTask());
            } catch (Exception e1) {
                @SuppressWarnings("unused")
                JErrorDialog errorDialog = new JErrorDialog(I18n.get("errorCreatingNewSociogram"), e1);
            }
        }
    }

    protected void open() {
		JFileChooser chooser = new JFileChooser(runningUtil.getWorkingDirectory());
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(I18n.get("socioFiles"), "socx");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	XMLManager xm = new XMLManager(chooser.getSelectedFile().getAbsolutePath());
			runningUtil.setWorkingDirectory(chooser.getCurrentDirectory().getAbsolutePath());
	    	try {
				this.runningUtil.copyDataFrom((Sociogram)xm.decodeXML(Sociogram.class));
			} catch (Exception e) {
				@SuppressWarnings("unused")
				JErrorDialog errorDialog = new JErrorDialog(I18n.get("socioOpenErrorMsg"), e);
			}
	    }
	}

	protected void save(String saveFile) {
		XMLManager xm = new XMLManager(saveFile);
    	try {
			xm.encodeXML(this.runningUtil.getSociogram());
		} catch (Exception e1) {
			@SuppressWarnings("unused")
			JErrorDialog errorDialog = new JErrorDialog(I18n.get("socioSaveErrorMsg"), e1);
		}		
	}

	private void saveAs() {
		JFileChooser chooser = new JFileChooser(runningUtil.getWorkingDirectory());
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(I18n.get("socioFiles"), "socx");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showSaveDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	String saveFile = chooser.getSelectedFile().getAbsolutePath();
			runningUtil.setWorkingDirectory(chooser.getCurrentDirectory().getAbsolutePath());
			if (!saveFile.endsWith(".socx")){
				saveFile += ".socx";
			}
	    	save(saveFile);
	    	this.runningUtil.setSaveFile(saveFile);
	    }
		
	}

	private Boolean isPollFinished() {
		int positiveCounter = 0;
		int negativeCounter = 0;
		for (int i = 0; i < this.runningUtil.getPersons().size(); i++) {
			int realId = i+1;
			if (this.runningUtil.hasAllSelected(realId, LikingType.POSITIVE)){
				positiveCounter ++;
			}
			if (this.runningUtil.hasAllSelected(realId, LikingType.NEGATIVE)){
				negativeCounter ++;
			}
		}

		return 2 * this.runningUtil.getPersons().size() == (positiveCounter+negativeCounter);
	}

	public RunningUtil getRunningUtil() {
		return this.runningUtil;		
	}

	@Override
	public void windowStateChanged(WindowEvent e) {
		((SociogramTask)(this.getRibbon().getSelectedTask())).initMainTaskPanel();
	}

    @Override
    public void componentResized(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    public Command getSocioNew() {
        return socioNew;
    }

    public Command getSocioOdpri() {
        return socioOdpri;
    }

    public Command getSocioShrani() {
        return socioShrani;
    }
}
