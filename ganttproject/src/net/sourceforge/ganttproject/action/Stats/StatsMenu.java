package net.sourceforge.ganttproject.action.Stats;


import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import net.sourceforge.ganttproject.GPLogger;
import net.sourceforge.ganttproject.IGanttProject;
import net.sourceforge.ganttproject.action.CancelAction;
import net.sourceforge.ganttproject.action.GPAction;
import net.sourceforge.ganttproject.action.OkAction;
import net.sourceforge.ganttproject.document.Document;
import net.sourceforge.ganttproject.document.DocumentManager;
import net.sourceforge.ganttproject.document.ReadOnlyProxyDocument;
import net.sourceforge.ganttproject.gui.ProjectUIFacade;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.gui.UIUtil;
import net.sourceforge.ganttproject.gui.ViewLogDialog;
import net.sourceforge.ganttproject.language.GanttLanguage;

import net.sourceforge.ganttproject.action.Stats.Stats;


public class StatsMenu {
        private Stats stats;

        /*private final ViewLogAction myViewLogAction;*/

        public StatsMenu(IGanttProject project, UIFacade uiFacade, ProjectUIFacade projectUiFacade) {

                stats = new Stats(project);

                //myViewLogAction = new ViewLogAction(uiFacade);

        }

        public JMenu createMenu() {
                /*JMenu result = UIUtil.createTooltiplessJMenu(GPAction.createVoidAction("Estatisticas"));
                result.add(myStats);
                result.add(myViewLogAction);
                return result;*/
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

       /*private void StatsAction() {


                        stats.makeTable();



        }*/

       /* private static class ViewLogAction extends GPAction {
                private final UIFacade myUiFacade;

                ViewLogAction(UIFacade uiFacade) {
                        super("Estatisticas");
                        myUiFacade = uiFacade;
                }


                public void actionPerformed(ActionEvent e) {
                        ViewLogDialog.show(myUiFacade);
                }
        }*/
}

