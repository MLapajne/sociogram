package si.zitnik.sociogram.gui.graph;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.pushingpixels.radiance.component.api.common.model.Command;
import org.pushingpixels.radiance.component.api.common.model.CommandToggleGroupModel;
import org.pushingpixels.radiance.component.api.ribbon.JRibbonBand;
import org.pushingpixels.radiance.component.api.ribbon.RibbonTask;
import org.pushingpixels.radiance.component.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.radiance.component.api.ribbon.resize.RibbonBandResizePolicy;
import si.zitnik.sociogram.enums.LikingType;
import si.zitnik.sociogram.enums.PersonDrawType;
import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.gui.SociogramRibbon;
import si.zitnik.sociogram.interfaces.SociogramTask;
import si.zitnik.sociogram.io.printing.GraphPrinter;
import si.zitnik.sociogram.util.RadianceUtil;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.ImageSaver;
import si.zitnik.sociogram.util.RunningUtil;

public class GraphTask implements SociogramTask {
	private MainFlowPanel graphPanel;
	private RunningUtil runningUtil;
	private SociogramRibbon mainFrame;
	private RibbonTask ribbonTask;
	private GraphUtil graphUtil;
    private Command oneP;
    private Command twoP;
    private Command threeP;
    private Command oneN;
    private Command twoN;
    private Command threeN;
    private Command vzajemnoAll;
	private Command woman;
	private Command man;

	public GraphTask(RunningUtil runningUtil, SociogramRibbon sociogramRibbon) {
		this.runningUtil = runningUtil;
		this.graphUtil = new GraphUtil();
		this.mainFrame = sociogramRibbon;
		this.graphPanel = new MainFlowPanel(runningUtil, this.graphUtil, this.mainFrame);
		createBands();
	}

	private void createBands() {
		JRibbonBand posSelections = new JRibbonBand(I18n.get("positiveChoices"), runningUtil.icons.dummyImage);
		{
			RadianceUtil.setResizePolicy(posSelections);

			oneP = Command.builder()
					.setText(I18n.get("first"))
					.setIconFactory(runningUtil.icons.ok1)
					.setToggle()
					.setToggleSelected(true)
					.setAction(commandActionEvent -> {
						var command = commandActionEvent.getCommand();
						if (vzajemnoAll.isToggleSelected()) {
							//disable deselect
							command.setToggleSelected(true);
						}
						graphUtil.setSelection(LikingType.POSITIVE, 1, command.isToggleSelected());
						graphPanel.init();
					}).build();
			posSelections.addRibbonCommand(oneP.project(), JRibbonBand.PresentationPriority.TOP);

			twoP = Command.builder()
					.setText(I18n.get("second"))
					.setIconFactory(runningUtil.icons.ok2)
					.setToggle()
					.setToggleSelected(true)
					.setAction(commandActionEvent -> {
						var command = commandActionEvent.getCommand();
						if (vzajemnoAll.isToggleSelected()) {
							//disable deselect
							command.setToggleSelected(true);
						}
						graphUtil.setSelection(LikingType.POSITIVE, 2, command.isToggleSelected());
						graphPanel.init();
					}).build();
			posSelections.addRibbonCommand(twoP.project(), JRibbonBand.PresentationPriority.TOP);

			threeP = Command.builder()
					.setText(I18n.get("third"))
					.setIconFactory(runningUtil.icons.ok3)
					.setToggle()
					.setToggleSelected(true)
					.setAction(commandActionEvent -> {
						var command = commandActionEvent.getCommand();
						if (vzajemnoAll.isToggleSelected()) {
							//disable deselect
							command.setToggleSelected(true);
						}
						graphUtil.setSelection(LikingType.POSITIVE, 3, command.isToggleSelected());
						graphPanel.init();
					}).build();
			posSelections.addRibbonCommand(threeP.project(), JRibbonBand.PresentationPriority.TOP);

			this.graphUtil.setSelection(LikingType.POSITIVE, 1, true);
			this.graphUtil.setSelection(LikingType.POSITIVE, 2, true);
			this.graphUtil.setSelection(LikingType.POSITIVE, 3, true);
			
		}
		
		JRibbonBand negSelections = new JRibbonBand(I18n.get("negativeChoices"), runningUtil.icons.dummyImage);
		{
			RadianceUtil.setResizePolicy(negSelections);

			oneN = Command.builder()
					.setText(I18n.get("first"))
					.setIconFactory(runningUtil.icons.no1)
					.setToggle()
					.setToggleSelected(true)
					.setAction(commandActionEvent -> {
						var command = commandActionEvent.getCommand();
						if (vzajemnoAll.isToggleSelected()) {
							//disable deselect
							command.setToggleSelected(true);
						}
						graphUtil.setSelection(LikingType.NEGATIVE, 1, command.isToggleSelected());
						graphPanel.init();
					})
					.build();
			negSelections.addRibbonCommand(oneN.project(), JRibbonBand.PresentationPriority.TOP);

			twoN = Command.builder()
					.setText(I18n.get("second"))
					.setIconFactory(runningUtil.icons.no2)
					.setToggle()
					.setToggleSelected(true)
					.setAction(commandActionEvent -> {
						var command = commandActionEvent.getCommand();
						if (vzajemnoAll.isToggleSelected()) {
							//disable deselect
							command.setToggleSelected(true);
						}
						graphUtil.setSelection(LikingType.NEGATIVE, 2, command.isToggleSelected());
						graphPanel.init();
					})
					.build();
			negSelections.addRibbonCommand(twoN.project(), JRibbonBand.PresentationPriority.TOP);

			threeN = Command.builder()
					.setText(I18n.get("third"))
					.setIconFactory(runningUtil.icons.no3)
					.setToggle()
					.setToggleSelected(true)
					.setAction(commandActionEvent -> {
						var command = commandActionEvent.getCommand();
						if (vzajemnoAll.isToggleSelected()) {
							//disable deselect
							command.setToggleSelected(true);
						}
						graphUtil.setSelection(LikingType.NEGATIVE, 3, command.isToggleSelected());
						graphPanel.init();
					})
					.build();
			negSelections.addRibbonCommand(threeN.project(), JRibbonBand.PresentationPriority.TOP);

			this.graphUtil.setSelection(LikingType.NEGATIVE, 1, true);
			this.graphUtil.setSelection(LikingType.NEGATIVE, 2, true);
			this.graphUtil.setSelection(LikingType.NEGATIVE, 3, true);
		}
		
		JRibbonBand drawType = new JRibbonBand(I18n.get("drawType"), runningUtil.icons.dummyImage);
		{
			RadianceUtil.setResizePolicy(drawType);
			
			var alignGroup = new CommandToggleGroupModel();

			var all = Command.builder()
					.setText(I18n.get("all"))
					.setIconFactory(runningUtil.icons.vsi)
					.inToggleGroupAsSelected(alignGroup)
					.setAction(commandActionEvent -> {
						var command = commandActionEvent.getCommand();
						if (!command.isToggleSelected()){
							command.setToggleSelected(true);
						}
						graphUtil.setPersonToDrawType(PersonDrawType.ALL, null);
						graphPanel.init();
					}).build();

			drawType.addRibbonCommand(all.project(), JRibbonBand.PresentationPriority.TOP);

			var vzajemno = Command.builder()
					.setText(I18n.get("mutuallySelected"))
					.setIconFactory(runningUtil.icons.vzajemnoIzbrani)
					.inToggleGroup(alignGroup)
					.setAction(commandActionEvent -> {
						var command = commandActionEvent.getCommand();
						if (!command.isToggleSelected()){
							command.setToggleSelected(true);
						}
						graphUtil.setPersonToDrawType(PersonDrawType.VZAJEMNO_SELECTED, null);
						graphPanel.init();
					}).build();
			drawType.addRibbonCommand(vzajemno.project(), JRibbonBand.PresentationPriority.TOP);

			vzajemnoAll = Command.builder()
					.setText(I18n.get("mutuallySelectedAll"))
					.setIconFactory(runningUtil.icons.vzajemnoIzbraniAll)
					.inToggleGroup(alignGroup)
					.setAction(commandActionEvent -> {
						var command = commandActionEvent.getCommand();
						if (!command.isToggleSelected()){
							command.setToggleSelected(true);
						}
						graphUtil.setPersonToDrawType(PersonDrawType.VZAJEMNO_SELECTED_ALL, null);

						//all types must be selected
						graphUtil.setSelection(LikingType.POSITIVE, 1, true);
						graphUtil.setSelection(LikingType.POSITIVE, 2, true);
						graphUtil.setSelection(LikingType.POSITIVE, 3, true);
						graphUtil.setSelection(LikingType.NEGATIVE, 1, true);
						graphUtil.setSelection(LikingType.NEGATIVE, 2, true);
						graphUtil.setSelection(LikingType.NEGATIVE, 3, true);

						oneP.setToggleSelected(true);
						twoP.setToggleSelected(true);
						threeP.setToggleSelected(true);
						oneN.setToggleSelected(true);
						twoN.setToggleSelected(true);
						threeN.setToggleSelected(true);
						threeN.setToggleSelected(true);

						graphPanel.init();
					}).build();
			drawType.addRibbonCommand(vzajemnoAll.project(), JRibbonBand.PresentationPriority.TOP);


			var specificPerson = Command.builder()
					.setText(I18n.get("individual"))
					.setIconFactory(runningUtil.icons.posameznik)
					.setAction(commandActionEvent -> {
						var command = commandActionEvent.getCommand();
						if (!command.isToggleSelected()){
							command.setToggleSelected(true);
						}
						int selectedPersonRealId = 0;
						final JDialog whichPerson = new JDialog(mainFrame, I18n.get("personChoice"));
						whichPerson.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						whichPerson.setModal(true);
						whichPerson.getContentPane().setLayout(new BorderLayout());
						whichPerson.getContentPane().add(new JLabel(I18n.get("selectPerson") + ":"), BorderLayout.NORTH);
						String[] array = new String[runningUtil.getPersons().size()];
						for (int i = 0; i<runningUtil.getPersons().size(); i++) {
							array[i] = runningUtil.getPersonFullName(runningUtil.getPersons().get(i).getId());
						}

						JComboBox comboBox = new JComboBox(array);
						whichPerson.getContentPane().add(comboBox, BorderLayout.CENTER);
						JButton ok = new JButton(I18n.get("ok"));
						ok.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								whichPerson.setVisible(false);
							}
						});
						whichPerson.setSize(250, 100);
						Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
						whichPerson.setLocation(r.width/2-250/2, r.height/2-100/2);
						whichPerson.getContentPane().add(ok, BorderLayout.SOUTH);
						whichPerson.setVisible(true);
						selectedPersonRealId = comboBox.getSelectedIndex()+1;

						graphUtil.setPersonToDrawType(PersonDrawType.SPECIFIC_PERSON, runningUtil.getPerson(selectedPersonRealId));
						graphPanel.init();
					})
					.inToggleGroup(alignGroup)
					.build();
			drawType.addRibbonCommand(specificPerson.project(), JRibbonBand.PresentationPriority.TOP);

			var unselected = Command.builder()
					.setText(I18n.get("nonselected"))
					.setIconFactory(runningUtil.icons.neizbran)
					.setAction(commandActionEvent -> {
						var command = commandActionEvent.getCommand();
						if (!command.isToggleSelected()){
							command.setToggleSelected(true);
						}
						graphUtil.setPersonToDrawType(PersonDrawType.UNSELECTED, null);
						graphPanel.init();
					})
					.inToggleGroup(alignGroup)
					.build();
			drawType.addRibbonCommand(unselected.project(), JRibbonBand.PresentationPriority.TOP);
			
			drawType.startGroup();

			woman = Command.builder()
					.setText(I18n.get("woman"))
					.setIconFactory(runningUtil.icons.woman)
					.setToggleSelected(true)
					.setAction(commandActionEvent -> {
						if (!woman.isToggleSelected() && !man.isToggleSelected()){
							woman.setToggleSelected(true);
							return;
						}
						graphUtil.setDrawWoman(woman.isToggleSelected());
						graphPanel.init();
					})
					.build();
			drawType.addRibbonCommand(woman.project(), JRibbonBand.PresentationPriority.TOP);

			man = Command.builder()
					.setText(I18n.get("man"))
					.setIconFactory(runningUtil.icons.man)
					.setToggleSelected(true)
					.setAction(commandActionEvent -> {
						if (!woman.isToggleSelected() && !man.isToggleSelected()){
							man.setToggleSelected(true);
							return;
						}
						graphUtil.setDrawMan(man.isToggleSelected());
						graphPanel.init();
					})
					.build();
			drawType.addRibbonCommand(man.project(), JRibbonBand.PresentationPriority.TOP);

			drawType.startGroup();

			var prikazImen = Command.builder()
					.setText(I18n.get("showPollResultsOnly"))
					.setIconFactory(runningUtil.icons.anonymous)
					.setToggleSelected(false)
					.setAction(commandActionEvent -> {
						graphPanel.togglePrikazImen();
						graphPanel.init();
					}).build();
			drawType.addRibbonCommand(prikazImen.project(), JRibbonBand.PresentationPriority.TOP);
		}
		
		JRibbonBand printing = new JRibbonBand(I18n.get("printing"), runningUtil.icons.dummyImage);
		{
			RadianceUtil.setResizePolicy(printing);

			var print = Command.builder()
					.setText(I18n.get("printCircleGraph"))
					.setIconFactory(runningUtil.icons.print)
					.setAction(commandActionEvent -> {
						try {
							new GraphPrinter(graphPanel.getCircleGraphPanel(), runningUtil, graphUtil).print();
						} catch (Exception e) {
							e.printStackTrace();
							@SuppressWarnings("unused")
							JErrorDialog errorDialog = new JErrorDialog(I18n.get("printingError"), e);
						}
					}).build();
			printing.addRibbonCommand(print.project(), JRibbonBand.PresentationPriority.TOP);
		}

        JRibbonBand export = new JRibbonBand(I18n.get("export"), runningUtil.icons.imageBig);
        {
			List<RibbonBandResizePolicy> policies = new ArrayList<RibbonBandResizePolicy>();
			policies.add(new CoreRibbonResizePolicies.Mirror(export));
			policies.add(new CoreRibbonResizePolicies.Mid2Low(export));
			//policies.add(new CoreRibbonResizePolicies.High2Mid(export));
			policies.add(new CoreRibbonResizePolicies.IconRibbonBandResizePolicy(export));
			export.setResizePolicies(policies);

			var exportButton = Command.builder()
					.setText(I18n.get("socioCircleGraphExportPNG"))
					.setIconFactory(runningUtil.icons.image)
					.setAction(commandActionEvent -> {
						Color oldColor = graphPanel.getBackground();
						graphPanel.setBackground(Color.WHITE);
						ImageSaver imageSaver = new ImageSaver(mainFrame, runningUtil, graphPanel.getCircleGraphPanel());
						imageSaver.saveDialog();
						graphPanel.setBackground(oldColor);

						mainFrame.getContentPane().remove(getMainTaskPanel());
						mainFrame.getContentPane().add(getMainTaskPanel(), BorderLayout.SOUTH);
						initMainTaskPanel();
						mainFrame.revalidate();
						mainFrame.repaint();
					}).build();
			export.addRibbonCommand(exportButton.project(), JRibbonBand.PresentationPriority.TOP);

			var exportButtonFree = Command.builder()
					.setText(I18n.get("socioFreeGraphExportPNG"))
					.setIconFactory(runningUtil.icons.image)
					.setAction(commandActionEvent -> {
						Color oldColor = graphPanel.getBackground();
						graphPanel.setBackground(Color.WHITE);
						ImageSaver imageSaver = new ImageSaver(mainFrame, runningUtil, graphPanel.getFreeGraphPanel());
						imageSaver.saveDialog();
						graphPanel.setBackground(oldColor);

						mainFrame.getContentPane().remove(getMainTaskPanel());
						mainFrame.getContentPane().add(getMainTaskPanel(), BorderLayout.SOUTH);
						initMainTaskPanel();
						mainFrame.revalidate();
						mainFrame.repaint();
					}).build();
			export.addRibbonCommand(exportButtonFree.project(), JRibbonBand.PresentationPriority.TOP);

            export.startGroup();

			var exportButtonBoth = Command.builder()
					.setText(I18n.get("socioBothGraphExportPNG"))
					.setIconFactory(runningUtil.icons.image)
					.setAction(commandActionEvent -> {
						Color oldColor = graphPanel.getBackground();
						graphPanel.setBackground(Color.WHITE);
						ImageSaver imageSaver = new ImageSaver(mainFrame, runningUtil, graphPanel);
						imageSaver.saveDialog();
						graphPanel.setBackground(oldColor);

						mainFrame.getContentPane().remove(getMainTaskPanel());
						mainFrame.getContentPane().add(getMainTaskPanel(), BorderLayout.SOUTH);
						initMainTaskPanel();
						mainFrame.revalidate();
						mainFrame.repaint();
					}).build();
			export.addRibbonCommand(exportButtonBoth.project(), JRibbonBand.PresentationPriority.TOP);
        }

		this.ribbonTask = new RibbonTask(I18n.get("graph"), posSelections, negSelections, drawType, printing, export);
	}

	@Override
	public Component getMainTaskPanel() {
		return this.graphPanel;
	}

	@Override
	public void initMainTaskPanel() {
		this.graphPanel.init();
	}
	
	@Override
	public RibbonTask getRibbonTask() {
		return this.ribbonTask;
	}

}