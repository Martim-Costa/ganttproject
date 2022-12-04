package net.sourceforge.ganttproject.action.Stats;


import javax.swing.BorderFactory;
import javax.swing.Box;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JFrame;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.sourceforge.ganttproject.task.TaskManager;
import net.sourceforge.ganttproject.IGanttProject;

import java.text.DecimalFormat;

public class Stats {
// used to round doubles
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private final TaskManager myTaskManager;

    public Stats(IGanttProject project) {
        myTaskManager = project.getTaskManager();

    }
//This method calculates the percentage of each type of Tasks by completion(completed, started and not started
    public  void makeTable(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();


        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Tasks Statistics", TitledBorder.CENTER, TitledBorder.TOP));
//create table
        String[][] rec = getPercentage();
        String[] header = { "Completed", "Started", "Not started", "Total Tasks" };
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

        String[][] result = {{"0", "0", "0","0"}};

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

            String str = df.format(calculatedCompleted) + "";
            String str1 = df.format(calculatedOnDoing) + "";
            String str2 = df.format(calculatedNotStarted) + "";
            String str3 = numberOfTasks + "";

            result[0][0] = str;
            result[0][1] = str1;
            result[0][2] = str2;
            result[0][3] = str3;
        }


        return result;

    }

}








