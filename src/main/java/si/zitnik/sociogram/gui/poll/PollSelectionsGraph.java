package si.zitnik.sociogram.gui.poll;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultKeyedValues2DDataset;
import org.jfree.data.general.KeyedValues2DDataset;
import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.enums.LikingType;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Created by slavkoz on 23/09/14.
 */
public class PollSelectionsGraph {

    private final RunningUtil runningUtil;

    public PollSelectionsGraph(RunningUtil runningUtil) {
        this.runningUtil = runningUtil;
    }

    public JPanel getGraphPanel() {
        JPanel jpanel = new JPanel();
        try {
            jpanel = new ChartPanel(createChart(createDataset()));
            jpanel.setPreferredSize(new Dimension(800, 700));
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,
                    I18n.get("pollDistrWarnTxt1"),
                    I18n.get("basicDistrWarn"),
                    JOptionPane.WARNING_MESSAGE);
        }
        return  jpanel;
    }

    private JFreeChart createChart(CategoryDataset keyedvalues2ddataset) throws Exception {
        org.jfree.chart.JFreeChart jfreechart = ChartFactory.createStackedBarChart(
                I18n.get("selectionsGraphName"),
                I18n.get("participants"),
                I18n.get("numSelections"), keyedvalues2ddataset, PlotOrientation.HORIZONTAL,
                true, true, false);

        /*
        CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
        categoryplot.setRenderer(new PollSelectionsRenderer());
        categoryplot.setBackgroundPaint(Color.lightGray);
        categoryplot.setRangeGridlinePaint(Color.white);
        */

        return jfreechart;
    }

    private KeyedValues2DDataset createDataset() {
        String poz = I18n.get("posSelectionGraphName");
        String neg = I18n.get("negSelectionGraphName");

        DefaultKeyedValues2DDataset defaultkeyedvalues2ddataset = new DefaultKeyedValues2DDataset();
        for (Person person : runningUtil.getPersons()) {
            defaultkeyedvalues2ddataset.addValue(-1.0 * runningUtil.getNumOfSelected(person.getId(), LikingType.NEGATIVE), neg, person.getFirstName() + " " + person.getLastName());
            defaultkeyedvalues2ddataset.addValue(runningUtil.getNumOfSelected(person.getId(), LikingType.POSITIVE), poz, person.getFirstName() + " " + person.getLastName());
        }

        return defaultkeyedvalues2ddataset;
    }
}

