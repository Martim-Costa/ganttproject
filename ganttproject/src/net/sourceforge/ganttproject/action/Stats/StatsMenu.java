package net.sourceforge.ganttproject.action.Stats;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import net.sourceforge.ganttproject.IGanttProject;
import net.sourceforge.ganttproject.action.Stats.Stats;

//Menus stats
public class StatsMenu {
        private Stats stats;



        public StatsMenu(IGanttProject project) {

                stats = new Stats(project);



        }
// this will create the menu to stats
        public JMenu createMenu() {
                JMenuBar menu = new JMenuBar();
                JMenu estati = new JMenu("Statistics");
                JMenuItem table = new JMenuItem("Table");
                table.setToolTipText("Statistics Table");
                menu.add(estati);
                estati.add(table);
                table.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ev) {
                                stats.makeTable();
                        }



        });
                return estati;}


}

