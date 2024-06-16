package si.zitnik.sociogram.gui.poll;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.pushingpixels.radiance.component.api.common.CommandButtonPresentationState;
import org.pushingpixels.radiance.component.api.common.RichTooltip;
import org.pushingpixels.radiance.component.api.common.model.Command;
import org.pushingpixels.radiance.component.api.common.model.CommandButtonPresentationModel;
import org.pushingpixels.radiance.component.api.common.model.CommandToggleGroupModel;
import org.pushingpixels.radiance.component.api.common.projection.CommandButtonProjection;
import org.pushingpixels.radiance.component.api.ribbon.JFlowRibbonBand;
import org.pushingpixels.radiance.component.api.ribbon.JRibbonBand;
import org.pushingpixels.radiance.component.api.ribbon.RibbonTask;
import org.pushingpixels.radiance.component.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.radiance.component.api.ribbon.resize.RibbonBandResizePolicy;
import org.pushingpixels.radiance.component.api.ribbon.synapse.model.ComponentPresentationModel;
import org.pushingpixels.radiance.component.api.ribbon.synapse.model.RibbonDefaultComboBoxContentModel;
import org.pushingpixels.radiance.component.api.ribbon.synapse.projection.RibbonComboBoxProjection;
import si.zitnik.sociogram.config.PropertiesQuestions;
import si.zitnik.sociogram.enums.LikingType;
import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.gui.SociogramRibbon;
import si.zitnik.sociogram.interfaces.SociogramTask;
import si.zitnik.sociogram.io.printing.PollPrinter;
import si.zitnik.sociogram.io.printing.PollTablePrinter;
import si.zitnik.sociogram.io.printing.PrintingType;
import si.zitnik.sociogram.util.*;


public class PollTask implements SociogramTask {
	private PollPanel pollPanel;
	private RunningUtil runningUtil;
	private SociogramRibbon mainFrame;
	private RibbonTask ribbonTask;

	private RibbonDefaultComboBoxContentModel<String> posQComboBoxModel;
	private RibbonDefaultComboBoxContentModel<String> negQComboBoxModel;

	public PollTask(RunningUtil runningUtil, SociogramRibbon sociogramRibbon) {
		this.runningUtil = runningUtil;
		this.pollPanel = new PollPanel(runningUtil);
		this.mainFrame = sociogramRibbon;
		createBands();
	}

	private String generateTooltip() {
		var str = new Random().nextDouble()+"";
		System.out.println("str = " + str);

		return str;
	}

	private void createBands() {
		JFlowRibbonBand posQuestionsBand = new JFlowRibbonBand(I18n.get("positiveSelection"), runningUtil.icons.addQBig);
		{
			List<RibbonBandResizePolicy> policies = new ArrayList<RibbonBandResizePolicy>();
			policies.add(new CoreRibbonResizePolicies.FlowThreeRows(posQuestionsBand));
			policies.add(new CoreRibbonResizePolicies.IconRibbonBandResizePolicy(posQuestionsBand));
			posQuestionsBand.setResizePolicies(policies);

			//TODO: solve tooltip and width of element if question loooong,
			// for positive and negative questions
			posQComboBoxModel = RibbonDefaultComboBoxContentModel.<String>builder()
					.setItems(new String[] {})
					.build();
			fillPosQComboBox(posQComboBoxModel);

			posQComboBoxModel.addListDataListener(new ListDataListener() {
				Object selected = posQComboBoxModel.getSelectedItem();

				@Override
				public void intervalAdded(ListDataEvent e) {
				}

				@Override
				public void intervalRemoved(ListDataEvent e) {
				}

				@Override
				public void contentsChanged(ListDataEvent e) {
					Object newSelection = posQComboBoxModel.getSelectedItem();
					if (this.selected != newSelection) {
						this.selected = newSelection;
					}
					runningUtil.setSelectedPosQuestion(newSelection.toString());
				}
			});
			posQuestionsBand.addFlowComponent(new RibbonComboBoxProjection(posQComboBoxModel,
					ComponentPresentationModel.builder().build()));

			//set questions right
			if (this.runningUtil.getSelectedPosQuestion().equals("")){
				runningUtil.setSelectedPosQuestion(posQComboBoxModel.getSelectedItem().toString());
			} else {
				if (containsQuestion(posQComboBoxModel, this.runningUtil.getSelectedPosQuestion())){
					posQComboBoxModel.setSelectedItem(this.runningUtil.getSelectedPosQuestion());
				} else {
					addNewPositiveQuestion(this.runningUtil.getSelectedPosQuestion());
				}
			}

			// add posQ
			posQuestionsBand.addFlowComponent(
				new CommandButtonProjection<Command>(
						Command.builder()
								.setText(I18n.get("addPositiveQuestion"))
								.setIconFactory(runningUtil.icons.addQ)
								.setAction(commandActionEvent -> {
									String newQuestion = getNewQuestion();
									addNewPositiveQuestion(newQuestion);
								}).build(),
						CommandButtonPresentationModel.builder()
								.setPresentationState(CommandButtonPresentationState.MEDIUM)
								.build()
				)
			);

			// remove posQ
			posQuestionsBand.addFlowComponent(
					new CommandButtonProjection<Command>(
							Command.builder()
									.setText(I18n.get("removePositiveQuestion"))
									.setIconFactory(runningUtil.icons.removeQ)
									.setAction(commandActionEvent -> {
										try {
											if (posQComboBoxModel.getSize() > 1){
												PropertiesQuestions questions = runningUtil.getPosQuestions();
												questions.remove(posQComboBoxModel.getSelectedItem().toString());
												runningUtil.setPosQuestions(questions);
												fillPosQComboBox(posQComboBoxModel);
												runningUtil.setSelectedPosQuestion(posQComboBoxModel.getSelectedItem().toString());
												mainFrame.repaint();
											}
										} catch (Exception e){
											@SuppressWarnings("unused")
											JErrorDialog errorDialog = new JErrorDialog(I18n.get("positiveQuestionRemovalErrorMsg"), e);
										}
									}).build(),
							CommandButtonPresentationModel.builder()
									.setPresentationState(CommandButtonPresentationState.MEDIUM)
									.build()
					)
			);
		}

		JFlowRibbonBand negQuestionsBand = new JFlowRibbonBand(I18n.get("negativeChoice"), runningUtil.icons.removeQBig);
		{
			List<RibbonBandResizePolicy> policies = new ArrayList<RibbonBandResizePolicy>();
			policies.add(new CoreRibbonResizePolicies.FlowThreeRows(negQuestionsBand));
			policies.add(new CoreRibbonResizePolicies.IconRibbonBandResizePolicy(posQuestionsBand));
			negQuestionsBand.setResizePolicies(policies);

			//TODO: solve tooltip and width of element if question loooong,
			// for positive and negative questions
			negQComboBoxModel = RibbonDefaultComboBoxContentModel.<String>builder()
					.setItems(new String[] {})
					.build();
			fillNegQComboBox(negQComboBoxModel);

			negQComboBoxModel.addListDataListener(new ListDataListener() {
				Object selected = negQComboBoxModel.getSelectedItem();

				@Override
				public void intervalAdded(ListDataEvent e) {
				}

				@Override
				public void intervalRemoved(ListDataEvent e) {
				}

				@Override
				public void contentsChanged(ListDataEvent e) {
					Object newSelection = negQComboBoxModel.getSelectedItem();
					if (this.selected != newSelection) {
						this.selected = newSelection;
					}
					runningUtil.setSelectedNegQuestion(newSelection.toString());
				}
			});
			negQuestionsBand.addFlowComponent(new RibbonComboBoxProjection(negQComboBoxModel,
					ComponentPresentationModel.builder().build()));

			if (this.runningUtil.getSelectedNegQuestion().equals("")){
				runningUtil.setSelectedNegQuestion(negQComboBoxModel.getSelectedItem().toString());
			} else {
				if (containsQuestion(negQComboBoxModel, this.runningUtil.getSelectedNegQuestion())){
					negQComboBoxModel.setSelectedItem(this.runningUtil.getSelectedNegQuestion());
				} else {
					addNewNegativeQuestion(this.runningUtil.getSelectedNegQuestion());
				}
			}

			// add negQ
			negQuestionsBand.addFlowComponent(
					new CommandButtonProjection<Command>(
							Command.builder()
									.setText(I18n.get("addNegativeQuestion"))
									.setIconFactory(runningUtil.icons.addQ)
									.setAction(commandActionEvent -> {
										String newQuestion = getNewQuestion();
										addNewNegativeQuestion(newQuestion);
									}).build(),
							CommandButtonPresentationModel.builder()
									.setPresentationState(CommandButtonPresentationState.MEDIUM)
									.build()
					)
			);

			// remove negQ
			negQuestionsBand.addFlowComponent(
					new CommandButtonProjection<Command>(
							Command.builder()
									.setText(I18n.get("removeNegativeQuestion"))
									.setIconFactory(runningUtil.icons.removeQ)
									.setAction(commandActionEvent -> {
										try {
											if (negQComboBoxModel.getSize() > 1){
												PropertiesQuestions questions = runningUtil.getNegQuestions();
												questions.remove(negQComboBoxModel.getSelectedItem().toString());
												runningUtil.setNegQuestions(questions);
												fillNegQComboBox(negQComboBoxModel);
												runningUtil.setSelectedNegQuestion(negQComboBoxModel.getSelectedItem().toString());
												mainFrame.repaint();
											}
										} catch (Exception e){
											@SuppressWarnings("unused")
											JErrorDialog errorDialog = new JErrorDialog(I18n.get("negativeQuestionRemovalErrorMsg"), e);
										}
									}).build(),
							CommandButtonPresentationModel.builder()
									.setPresentationState(CommandButtonPresentationState.MEDIUM)
									.build()
					)
			);
		}

		JRibbonBand printPollBand = new JRibbonBand(I18n.get("printing"), runningUtil.icons.dummyImage);
		{
			RadianceUtil.setResizePolicy(printPollBand);

			var buttonGroup = new CommandToggleGroupModel();


			var twoPerPage = Command.builder()
					.setText(I18n.get("twoPollsPerPage"))
					.setIconFactory(runningUtil.icons.twoPerPage)
					.inToggleGroupAsSelected(buttonGroup)
					.setActionRichTooltip(RichTooltip.builder()
							.setTitle(I18n.get("twoPollsPerPage"))
							.addDescriptionSection(I18n.get("twoPollsPerPageSelection"))
							.setMainIconFactory(runningUtil.icons.twoPerPageTooltip).build()
					)
					.setAction(commandActionEvent -> {})
					.build();

			var fourPerPage = Command.builder()
					.setText(I18n.get("fourPollsPerPage"))
					.setIconFactory(runningUtil.icons.fourPerPage)
					.inToggleGroup(buttonGroup)
					.setActionRichTooltip(RichTooltip.builder()
							.setTitle(I18n.get("fourPollsPerPage"))
							.addDescriptionSection(I18n.get("fourPollsPerPageSelection"))
							.setMainIconFactory(runningUtil.icons.fourPerPageTooltip).build()
					)
					.setAction(commandActionEvent -> {})
					.build();

			var printName = Command.builder()
					.setText(I18n.get("turnNamesOn"))
					.setIconFactory(runningUtil.icons.printNames)
					.setToggle()
					.setToggleSelected(true)
					.build();

			var print = Command.builder()
					.setText(I18n.get("printPolls"))
					.setIconFactory(runningUtil.icons.print)
					.setAction(commandActionEvent -> {
						try {
							PollPrinter pm = null;
							if (twoPerPage.isToggleSelected()) {
								pm = new PollPrinter(runningUtil, printName.isToggleSelected(), PrintingType.TWO_PER_PAGE);
							} else {
								pm = new PollPrinter(runningUtil, printName.isToggleSelected(),PrintingType.FOUR_PER_PAGE);
							}
							pm.print();
						} catch (Exception e) {
							@SuppressWarnings("unused")
							JErrorDialog errorDialog = new JErrorDialog(I18n.get("printingErrorMsg"), e);
						}
					})
					.build();

			var printTable = Command.builder()
					.setText(I18n.get("printTable"))
					.setIconFactory(runningUtil.icons.print)
					.setAction(commandActionEvent -> {
						pollPanel.getPollTable().setIsPrinting(true);
						PollTablePrinter printer = new PollTablePrinter(pollPanel.getPollTable(), runningUtil);
						pollPanel.getPollTable().setIsPrinting(false);
						pollPanel.init();
						pollPanel.revalidate();
						try {
							printer.print();
						} catch (Exception e) {
							@SuppressWarnings("unused")
							JErrorDialog errorDialog = new JErrorDialog(I18n.get("printingErrorMsg"), e);
						}
					})
					.build();

			printPollBand.addRibbonCommand(twoPerPage.project(), JRibbonBand.PresentationPriority.TOP);
			printPollBand.addRibbonCommand(fourPerPage.project(), JRibbonBand.PresentationPriority.TOP);
			printPollBand.addRibbonCommand(printName.project(), JRibbonBand.PresentationPriority.TOP);
			printPollBand.addRibbonCommand(print.project(), JRibbonBand.PresentationPriority.TOP);
			printPollBand.addRibbonCommand(printTable.project(), JRibbonBand.PresentationPriority.TOP);
		}

        JRibbonBand export = new JRibbonBand(I18n.get("export"), runningUtil.icons.imageBig);
        {
			RadianceUtil.setResizePolicy(export);
			List<RibbonBandResizePolicy> policies = new ArrayList<RibbonBandResizePolicy>();
			policies.add(new CoreRibbonResizePolicies.Mirror(export));
			policies.add(new CoreRibbonResizePolicies.Mid2Low(export));
			//policies.add(new CoreRibbonResizePolicies.High2Mid(export));
			policies.add(new CoreRibbonResizePolicies.IconRibbonBandResizePolicy(export));
			export.setResizePolicies(policies);

			var exportButton = Command.builder()
					.setText(I18n.get("pollPosExportPNG"))
					.setIconFactory(runningUtil.icons.image)
					.setActionRichTooltip(RichTooltip.builder()
							.setTitle(I18n.get("export"))
							.addDescriptionSection(I18n.get("pollPosExportPNG"))
							.build())
					.setAction(commandActionEvent -> {
						if (runningUtil.getPersons().size() > 0) {
							PollDistr pollDistr = new PollDistr(runningUtil, LikingType.POSITIVE);
							ImageSaver imageSaver = new ImageSaver(mainFrame, runningUtil, pollDistr.getGraphPanel());
							imageSaver.saveDialog();
						} else {
							JOptionPane.showMessageDialog(null,
									I18n.get("pollDistrWarnTxt"),
									I18n.get("basicDistrWarn"),
									JOptionPane.WARNING_MESSAGE);
						}
					}).build();
			export.addRibbonCommand(exportButton.project(), JRibbonBand.PresentationPriority.TOP);

			var exportButtonFree = Command.builder()
					.setText(I18n.get("pollNegExportPNG"))
					.setIconFactory(runningUtil.icons.image)
					.setActionRichTooltip(RichTooltip.builder()
							.setTitle(I18n.get("export"))
							.addDescriptionSection(I18n.get("pollNegExportPNG")).build())
					.setAction(commandActionEvent -> {
						if (runningUtil.getPersons().size() > 0) {
							PollDistr pollDistr = new PollDistr(runningUtil, LikingType.NEGATIVE);
							ImageSaver imageSaver = new ImageSaver(mainFrame, runningUtil, pollDistr.getGraphPanel());
							imageSaver.saveDialog();
						} else {
							JOptionPane.showMessageDialog(null,
									I18n.get("pollDistrWarnTxt"),
									I18n.get("basicDistrWarn"),
									JOptionPane.WARNING_MESSAGE);
						}
					}).build();
			export.addRibbonCommand(exportButtonFree.project(), JRibbonBand.PresentationPriority.TOP);

			var exportSelButton = Command.builder()
					.setText(I18n.get("selectionsGraphButton"))
					.setIconFactory(runningUtil.icons.image)
					.setActionRichTooltip(RichTooltip.builder()
							.setTitle(I18n.get("export"))
							.addDescriptionSection(I18n.get("selectionsGraphButton")).build())
					.setAction(commandActionEvent -> {
						if (runningUtil.getPersons().size() > 0) {
							PollSelectionsGraph graph = new PollSelectionsGraph(runningUtil);
							ImageSaver imageSaver = new ImageSaver(mainFrame, runningUtil, graph.getGraphPanel());
							imageSaver.saveDialog();
						} else {
							JOptionPane.showMessageDialog(null,
									I18n.get("pollDistrWarnTxt"),
									I18n.get("basicDistrWarn"),
									JOptionPane.WARNING_MESSAGE);
						}
					}).build();
			export.addRibbonCommand(exportSelButton.project(), JRibbonBand.PresentationPriority.TOP);

			var exportButtonXlsx = Command.builder()
					.setText(I18n.get("pollXLSX"))
					.setIconFactory(runningUtil.icons.excel)
					.setActionRichTooltip(RichTooltip.builder()
							.setTitle(I18n.get("export"))
							.addDescriptionSection(I18n.get("pollXLSX")).build())
					.setAction(commandActionEvent -> {
						ExcelPollSaver excelSaver = new ExcelPollSaver(mainFrame, runningUtil, pollPanel.getPollTable());
						excelSaver.saveDialog();
					}).build();
			export.addRibbonCommand(exportButtonXlsx.project(), JRibbonBand.PresentationPriority.TOP);
        }

		this.ribbonTask = new RibbonTask(I18n.get("poll"), posQuestionsBand, negQuestionsBand, printPollBand, export);
	}

	protected void addNewNegativeQuestion(String newQuestion) {
		try {
			PropertiesQuestions questions = runningUtil.getNegQuestions();
			questions.add(newQuestion);
			runningUtil.setNegQuestions(questions);
			fillNegQComboBox(negQComboBoxModel);
			runningUtil.setSelectedNegQuestion(negQComboBoxModel.getSelectedItem().toString());
			mainFrame.repaint();
		} catch (Exception e){
			@SuppressWarnings("unused")
			JErrorDialog errorDialog = new JErrorDialog(I18n.get("addingNegativeQuestionError"), e);
		}
	}

	protected void addNewPositiveQuestion(String newQuestion) {
		try {
			PropertiesQuestions questions = runningUtil.getPosQuestions();
			questions.add(newQuestion);
			runningUtil.setPosQuestions(questions);
			fillPosQComboBox(posQComboBoxModel);
			runningUtil.setSelectedPosQuestion(posQComboBoxModel.getSelectedItem().toString());
			mainFrame.repaint();
			
		} catch (Exception e){
			@SuppressWarnings("unused")
			JErrorDialog errorDialog = new JErrorDialog(I18n.get("addingPositiveQuestionError"), e);
		}
	}

	private boolean containsQuestion(RibbonDefaultComboBoxContentModel<String> comboBox, String question) {
		for (int i = 0; i < comboBox.getSize(); i++) {
			String item = comboBox.getElementAt(i);
			if (item.equals(question)){
				return true;
			}
		}
		return false;
	}

	private void fillPosQComboBox(RibbonDefaultComboBoxContentModel<String> comboBox) {
		try {
			comboBox.removeAllElements();
			for (String question : this.runningUtil.getPosQuestions().toArray(new String[]{})) {
				comboBox.addElement(question);
			}
		} catch (Exception e) {
			comboBox.addElement(I18n.get("posQuestionExample"));
			@SuppressWarnings("unused")
			JErrorDialog errorDialog = new JErrorDialog(I18n.get("positiveQuestionsRetrievalErrorMsg"),e);
		}		
	}
	
	private void fillNegQComboBox(RibbonDefaultComboBoxContentModel<String> comboBox) {
		try {
			comboBox.removeAllElements();
			for (String question : this.runningUtil.getNegQuestions().toArray(new String[]{})) {
				comboBox.addElement(question);
			}
		} catch (Exception e) {
			comboBox.addElement(I18n.get("negQuestionExample"));
			@SuppressWarnings("unused")
			JErrorDialog errorDialog = new JErrorDialog(I18n.get("negativeQuestionRetrievalErrorMsg"),e);
		}		
	}
	
	private String getNewQuestion() {
		String retVal = "";
		final JDialog newQuestionDialog = new JDialog(mainFrame, I18n.get("newQuestionInput"));
		newQuestionDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		newQuestionDialog.setModal(true);
		newQuestionDialog.getContentPane().setLayout(new BorderLayout());
		newQuestionDialog.getContentPane().add(new JLabel(I18n.get("enterNewQuestion")+":"), BorderLayout.NORTH);
		JTextArea textArea = new JTextArea(3, 50);
		textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		newQuestionDialog.getContentPane().add(textArea, BorderLayout.CENTER);
		JButton ok = new JButton(I18n.get("save"));
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newQuestionDialog.setVisible(false);
			}
		});
		JButton cancel = new JButton(I18n.get("cancel"));
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newQuestionDialog.setVisible(false);
			}
		});
		//newQuestionDialog.setSize(250, 100);
		JPanel southPanel = new JPanel(new FlowLayout());
			southPanel.add(ok);
			southPanel.add(cancel);
		newQuestionDialog.getContentPane().add(southPanel, BorderLayout.SOUTH);
		newQuestionDialog.pack();
		Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		newQuestionDialog.setLocation(r.width/2-newQuestionDialog.getWidth()/2, r.height/2-newQuestionDialog.getHeight()/2);
		newQuestionDialog.setVisible(true);
		
		retVal = textArea.getText();
		return retVal;
	}

	@Override
	public Component getMainTaskPanel() {
		return this.pollPanel;
	}

	@Override
	public void initMainTaskPanel() {
		this.pollPanel.init();		
	}
	
	@Override
	public RibbonTask getRibbonTask() {
		return this.ribbonTask;
	}

    class PosComboBoxRenderer extends BasicComboBoxRenderer {
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
                if (-1 < index) {
                    try {
                        list.setToolTipText(runningUtil.getPosQuestions().get(index));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setFont(list.getFont());
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class NegComboBoxRenderer extends BasicComboBoxRenderer {
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
                if (-1 < index) {
                    try {
                        list.setToolTipText(runningUtil.getNegQuestions().get(index));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setFont(list.getFont());
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

}
