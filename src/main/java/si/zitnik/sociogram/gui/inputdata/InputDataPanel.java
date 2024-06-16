package si.zitnik.sociogram.gui.inputdata;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.table.TableColumn;

import si.zitnik.sociogram.entities.Gender;
import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.enums.ProgramType;
import si.zitnik.sociogram.interfaces.TaskPanel;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;
import si.zitnik.sociogram.util.SpringUtilities;

public class InputDataPanel extends JPanel implements TaskPanel {
	private static final long serialVersionUID = 5168986299436234802L;
	private RunningUtil runningUtil;
	private JTable table; 
	private JButton addPerson;
	private JButton removePerson;
	private JTextField department;
	private JTextField schoolYear;
    private JTextArea comment;
    private JTextField grader;
    private JTextField date;
    private JTextField teacher;
    private JTextField name;
    private JTextField surname;
    private JComboBox gender;

    public InputDataPanel(RunningUtil runningUtil) {
		this.runningUtil = runningUtil;
		this.createTabPanel();
	}
	
	public JTable getTable(){
		return this.table;
	}
	
	public JButton getAddPerson(){
		return this.addPerson;
	}
	
	public JButton getRemovePerson(){
		return this.removePerson;
	}

	public void createTabPanel(){
		//center panel
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new InputTableModel(runningUtil.getPersons()));
		
		TableColumn genderColumn = table.getColumnModel().getColumn(3);
		JComboBox comboBox = new JComboBox();
        comboBox.addItem(I18n.get("man"));
        comboBox.addItem(I18n.get("woman"));
		genderColumn.setCellEditor(new DefaultCellEditor(comboBox));
		JScrollPane centerPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		
		//right panel
        JPanel rightPane = new JPanel(new SpringLayout());
        rightPane.setMaximumSize(new Dimension(300, this.getHeight()));
        rightPane.add(new JLabel());
        rightPane.add(new JLabel(I18n.get("surveyData"), JLabel.CENTER));
        rightPane.add(new JLabel(I18n.get("date") + ":", JLabel.TRAILING));
        date = new JTextField(runningUtil.getDate(), 9);
        date.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        date.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                runningUtil.setDate(date.getText());
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }
        });
        rightPane.add(date);
        rightPane.add(new JLabel(I18n.get("grader") + ":", JLabel.TRAILING));
        grader = new JTextField(runningUtil.getGrader(), 9);
        grader.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        grader.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                runningUtil.setGrader(grader.getText());
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }
        });
        rightPane.add(grader);

        if (runningUtil.getProgramType().equals(ProgramType.SOLSTVO)) {
            rightPane.add(new JLabel(I18n.get("class") + ": ", JLabel.TRAILING));
            department = new JTextField(runningUtil.getDepartment(), 5);
            department.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    runningUtil.setDepartment(department.getText());
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }
            });
            rightPane.add(department);
            rightPane.add(new JLabel(I18n.get("teacher") + ":", JLabel.TRAILING));
            teacher = new JTextField(runningUtil.getTeacher(), 9);
            teacher.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            teacher.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent arg0) {
                }

                @Override
                public void keyReleased(KeyEvent arg0) {
                    runningUtil.setTeacher(teacher.getText());
                }

                @Override
                public void keyPressed(KeyEvent arg0) {
                }
            });
            rightPane.add(teacher);
            rightPane.add(new JLabel(I18n.get("schoolYear") + ": ", JLabel.TRAILING));
            schoolYear = new JTextField(runningUtil.getSchoolYear(), 9);
            schoolYear.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent arg0) {
                }

                @Override
                public void keyReleased(KeyEvent arg0) {
                    runningUtil.setSchoolYear(schoolYear.getText());
                }

                @Override
                public void keyPressed(KeyEvent arg0) {
                }
            });
            rightPane.add(schoolYear);
        } else {
            department = new JTextField(runningUtil.getDepartment(), 5);
            teacher = new JTextField(runningUtil.getTeacher(), 9);
            schoolYear = new JTextField(runningUtil.getSchoolYear(), 9);
        }
        rightPane.add(new JLabel(I18n.get("comment") + ":", JLabel.TRAILING));
        comment = new JTextArea(runningUtil.getComment(), 3, 9);
        comment.setLineWrap(true);
        comment.setWrapStyleWord(true);
        comment.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        comment.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                runningUtil.setComment(comment.getText());
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }
        });
        rightPane.add(comment);

        rightPane.add(new JLabel(" "));
        rightPane.add(new JLabel(" "));

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        rightPane.add(separator);
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        rightPane.add(separator1);

        rightPane.add(new JLabel(" "));
        rightPane.add(new JLabel(" "));

        rightPane.add(new JLabel());
        rightPane.add(new JLabel(I18n.get("participantData"), JLabel.CENTER));
        rightPane.add(new JLabel(I18n.get("name") + ": ", JLabel.TRAILING));
		name = new JTextField(20);
        rightPane.add(name);
        rightPane.add(new JLabel(I18n.get("surname") + ": ", JLabel.TRAILING));
		surname = new JTextField(20);
        rightPane.add(surname);
		gender = new JComboBox(new String[]{I18n.get("man"), I18n.get("woman")});
        rightPane.add(new JLabel(I18n.get("gender") + ": ", JLabel.TRAILING));
        rightPane.add(gender);
		this.addPerson = new JButton(I18n.get("addPersonTxt"));
		this.addPerson.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
                addPersonLogic();
            }
		});
        rightPane.add(new JLabel());
        rightPane.add(new JLabel());
        rightPane.add(new JLabel());
        rightPane.add(new JLabel());
        rightPane.add(new JLabel());
        rightPane.add(this.addPerson);
		this.removePerson = new JButton(I18n.get("removePerson"));
		this.removePerson.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                removePersonLogic();
			}
		});
        rightPane.add(new JLabel());
        rightPane.add(removePerson);
        JPanel rightPane1 = new JPanel();
        rightPane1.add(rightPane);

        if (runningUtil.getProgramType().equals(ProgramType.SOLSTVO)) {
            SpringUtilities.makeCompactGrid(rightPane,
                    18, 2, //rows, cols
                    6, 6,        //initX, initY
                    6, 6);       //xPad, yPad
        } else {
            SpringUtilities.makeCompactGrid(rightPane,
                    15, 2, //rows, cols
                    6, 6,        //initX, initY
                    6, 6);       //xPad, yPad
        }



        //add components to layout
        this.setLayout(new BorderLayout());
        this.add(centerPane, BorderLayout.CENTER);
        this.add(rightPane1, BorderLayout.EAST);
	}

    public void addPersonLogic() {
        if (runningUtil.isActivated()) {
            addPerson(name, surname, gender);
        } else {
            runningUtil.showNotActivatedDialog();
        }
    }

    private void addPerson(JTextField name, JTextField surname, JComboBox gender) {
        if (name.getText().length() > 0 && surname.getText().length() > 0){
            Person person = new Person(3, name.getText(), surname.getText(), (gender.getSelectedItem().toString().toLowerCase().startsWith("m")) ? Gender.MALE : Gender.FEMALE);
            runningUtil.addPerson(person);
            ((InputTableModel)table.getModel()).fireTableDataChanged();
            name.setText(""); surname.setText(""); gender.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(null,
                    I18n.get("basicDataWarn"),
                    I18n.get("addPersonWarnTxt"),
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void removePersonLogic() {
        if (runningUtil.isActivated()) {
            int[] selRows = table.getSelectedRows();
            if (selRows.length > 0){
                int id = Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
                runningUtil.removePerson(id);
                ((InputTableModel)table.getModel()).fireTableDataChanged();
            } else {
                JOptionPane.showMessageDialog(null,
                        I18n.get("removePersonWarn"),
                        I18n.get("removePersonWarnTxt"),
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            runningUtil.showNotActivatedDialog();
        }
    }

    @Override
	public void init() {
        department.setText(this.runningUtil.getDepartment());
        schoolYear.setText(this.runningUtil.getSchoolYear());
        comment.setText(this.runningUtil.getComment());
        grader.setText(this.runningUtil.getGrader());
        date.setText(this.runningUtil.getDate());
        teacher.setText(this.runningUtil.getTeacher());
		((InputTableModel)this.table.getModel()).fireTableDataChanged();
	}
}
