package si.zitnik.sociogram.gui.poll;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.enums.LikingType;
import si.zitnik.sociogram.util.CounterMap;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by slavkoz on 23/09/14.
 */
public class PollDistr {

    private final RunningUtil runningUtil;
    private final LikingType likingType;

    public PollDistr(RunningUtil runningUtil, LikingType likingType) {
        this.runningUtil = runningUtil;
        this.likingType = likingType;
    }

    public JPanel getGraphPanel() {
        JPanel jpanel = new JPanel();
        try {
            jpanel = new ChartPanel(createChart(createDataset()));
            jpanel.setPreferredSize(new Dimension(800, 400));
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,
                    I18n.get("pollDistrWarnTxt1"),
                    I18n.get("basicDistrWarn"),
                    JOptionPane.WARNING_MESSAGE);
        }
        return  jpanel;
    }

    private JFreeChart createChart(CategoryDataset intervalxydataset) throws Exception {
        String title = (likingType.equals(LikingType.POSITIVE)) ? I18n.get("pollDistrTitlePos") : I18n.get("pollDistrTitleNeg");
        String xAxis = (likingType.equals(LikingType.POSITIVE)) ? I18n.get("pollDistrXAxisNamePos") : I18n.get("pollDistrXAxisNameNeg");
        String yAxis = (likingType.equals(LikingType.POSITIVE)) ? I18n.get("pollDistrYAxisNamePos") : I18n.get("pollDistrYAxisNameNeg");

        JFreeChart jfreechart = ChartFactory.createBarChart(title, xAxis, yAxis, intervalxydataset, PlotOrientation.VERTICAL, false, false, false);
        jfreechart.addSubtitle(new TextTitle(runningUtil.getInstitution() + " - " + runningUtil.getDepartment(), new Font("Dialog", 2, 10)));
        jfreechart.setBackgroundPaint(Color.white);
        CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
        categoryplot.setRenderer(new PollDistrRenderer(likingType));
        categoryplot.setBackgroundPaint(Color.lightGray);
        categoryplot.setRangeGridlinePaint(Color.white);
        return jfreechart;
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
        HashMap<Integer, Integer> dataset = getDataset();

        int min = Collections.min(dataset.keySet());
        int max = Collections.max(dataset.keySet());

        for (
                int selections = min;
                selections <= max;
                selections ++) {
            categoryDataset.addValue(dataset.getOrDefault(selections, 0), "D1", selections+"");
        }


        return categoryDataset;
    }

    private HashMap<Integer, Integer> getDataset() {
        CounterMap<Integer> counterMap = new CounterMap<Integer>();
        for (Person person : runningUtil.getPersons()) {
            counterMap.put(runningUtil.getNumOfSelected(person.getId(), likingType));
        }
        return counterMap.map;
    }
}

