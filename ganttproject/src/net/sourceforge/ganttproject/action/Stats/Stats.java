package net.sourceforge.ganttproject.action.Stats;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JFrame;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.sourceforge.ganttproject.task.TaskManager;
import net.sourceforge.ganttproject.task.TaskManagerImpl;
import net.sourceforge.ganttproject.IGanttProject;

public class Stats {

    private final TaskManager myTaskManager;

    public Stats(IGanttProject project) {
        myTaskManager = project.getTaskManager();

    }

    public  void makeTable(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();


        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Tasks Statistics", TitledBorder.CENTER, TitledBorder.TOP));

        String[][] rec = getPercentage();
        String[] header = { "Completed", "Started", "Not started" };
        JTable table = new JTable(rec, header);
        panel.add(new JScrollPane(table));
        frame.add(panel);
        frame.setSize(550, 400);
        frame.setVisible(true);
    }


    private String[][] getPercentage() {

        double completed = 0.0;
        double onDoing = 0.0;
        double notStarted = 0.0;

        String[][] result = {{"0", "0", "0"}};

        double calculatedCompleted;
        double calculatedOnDoing;
        double calculatedNotStarted;

        int numberOfTasks = myTaskManager.getTaskCount();
        if (numberOfTasks != 0) {

            for (int i = 0; i < numberOfTasks; i++) {

                if (myTaskManager.getTasks()[i].getCompletionPercentage() == 100) {
                    completed++;
                } else if (myTaskManager.getTasks()[i].getCompletionPercentage() > 0) {
                    onDoing++;
                } else {
                    notStarted++;
                }
            }


            calculatedCompleted = (completed / numberOfTasks) * 100;
            calculatedOnDoing = (onDoing / numberOfTasks) * 100;
            calculatedNotStarted = (notStarted / numberOfTasks) * 100;

            String str = calculatedCompleted + "%";
            String str1 = calculatedOnDoing + "%";
            String str2 = calculatedNotStarted + "%";

            result[0][0] = str;
            result[0][1] = str1;
            result[0][2] = str2;
        }


        return result;

    }

}








