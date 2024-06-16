package si.zitnik.sociogram.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

class JTableTest extends JFrame
{
    private JPanel panel;
    private JTable table;
    private JScrollPane scrollPane;
    private String[] columns = new String[3];
    private String[][] data = new String[3][3];
    JTextField textBox = new JTextField();

    public JTableTest()
    {
        //set the title of JFrame
        setTitle("JTable Example");
        //set the size of JFrame
        setSize(250,250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Create a panel
        panel = new JPanel();
        //define layout manager
        panel.setLayout(new BorderLayout());
        //add panel to frame
        getContentPane().add(panel);

        //JTable Header
        columns = new String[] {"Column 1" ,"Column 2", "Column 3"};
        //data for JTable in a 2D table
        data = new String[][]
                {
                        {"A","D","G"},
                        {"B","E","H"},
                        {"C","F","I"}
                };
        //Create the model
        TableModel model = new myTableModel();
        //Create a table
        table = new JTable();
        //set row height
        table.setRowHeight(40);
        //set the model
        table.setModel(model);
        //get the second column
        TableColumn col = table.getColumnModel().getColumn(1);
        //set the editor
        col.setCellEditor(new DefaultCellEditor(textBox));
        //enable selection of JTable cell
        table.setCellSelectionEnabled(true);
        scrollPane = new JScrollPane(table);
        //add JTable to panel
        panel.add(scrollPane,BorderLayout.CENTER);
        //Events on JTable cell
        table.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent e)
            {
                textBox.setBackground(Color.BLUE);
            }
        });
    }

    public class myTableModel extends DefaultTableModel
    {
        myTableModel() {
            super(data,columns);
        }

        public boolean isCellEditable(int row,int cols) {
            return true;
        }
    }

    public static void main(String args[])
    {
        JTableTest frame = new JTableTest();
        frame.setVisible(true);
    }
}
