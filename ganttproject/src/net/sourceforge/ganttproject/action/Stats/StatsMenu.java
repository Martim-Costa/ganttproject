package net.sourceforge.ganttproject.action.Stats;


import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
import net.sourceforge.ganttproject.gui.about.AboutDialog2;
import net.sourceforge.ganttproject.language.GanttLanguage;


public class StatsMenu {

        private final AboutAction myAboutAction;
        private final ViewLogAction myViewLogAction;
        private final RecoverLastProjectAction myRecoverAction;

        public StatsMenu(IGanttProject project, UIFacade uiFacade, ProjectUIFacade projectUiFacade) {
                myAboutAction = new AboutAction(uiFacade);
                myViewLogAction = new ViewLogAction(uiFacade);
                myRecoverAction = new RecoverLastProjectAction(project, uiFacade, projectUiFacade);
        }

        public JMenu createMenu() {
                JMenu result = UIUtil.createTooltiplessJMenu(GPAction.createVoidAction("Estatisticas"));
                result.add(myAboutAction);
                result.add(myViewLogAction);
                result.add(myRecoverAction);
                return result;
        }

        private static class AboutAction extends GPAction {
                private final UIFacade myUiFacade;

                AboutAction(UIFacade uifacade) {
                        super("Estatisticas");
                        myUiFacade = uifacade;
                }

                @Override
                public void actionPerformed(ActionEvent e) {
                        AboutDialog2 agp = new AboutDialog2(myUiFacade);
                        agp.show();
                }
        }

        private static class ViewLogAction extends GPAction {
                private final UIFacade myUiFacade;

                ViewLogAction(UIFacade uiFacade) {
                        super("Estatisticas");
                        myUiFacade = uiFacade;
                }

                @Override
                public void actionPerformed(ActionEvent e) {
                        ViewLogDialog.show(myUiFacade);
                }
        }

        private static class RecoverLastProjectAction extends GPAction {
                private final UIFacade myUiFacade;
                private final DocumentManager myDocumentManager;
                private final IGanttProject myProject;
                private final ProjectUIFacade myProjectUiFacade;

                RecoverLastProjectAction(IGanttProject project, UIFacade uiFacade, ProjectUIFacade projectUiFacade) {
                        super("Estatisticas");
                        myProject = project;
                        myUiFacade = uiFacade;
                        myDocumentManager = project.getDocumentManager();
                        myProjectUiFacade = projectUiFacade;
                }

                @Override
                public void actionPerformed(ActionEvent arg0) {
                        try {
                                final Document lastAutosaveDocument = myDocumentManager.getLastAutosaveDocument(null);
                                if (lastAutosaveDocument != null) {
                                        runAction(lastAutosaveDocument);
                                }
                        } catch (IOException e) {
                                GPLogger.log(new RuntimeException("Failed to read autosave documents", e));
                        }
                }

                private void runAction(final Document autosaveDocument) {
                        OkAction ok = new OkAction() {
                                @Override
                                public void actionPerformed(ActionEvent arg0) {
                                        recover(autosaveDocument);
                                }
                        };
                        CancelAction skip = new CancelAction("help.recover.skip") {
                                @Override
                                public void actionPerformed(ActionEvent arg0) {
                                        SwingUtilities.invokeLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                        Document prevAutosaveDocument = null;
                                                        try {
                                                                prevAutosaveDocument = myDocumentManager.getLastAutosaveDocument(autosaveDocument);
                                                        } catch (IOException e) {
                                                                GPLogger.log(new RuntimeException("Failed to read autosave documents", e));
                                                        }
                                                        if (prevAutosaveDocument != null) {
                                                                runAction(prevAutosaveDocument);
                                                        }
                                                }
                                        });
                                }
                        };
                        File f = new File(autosaveDocument.getFilePath());
                        myUiFacade.showOptionDialog(
                                JOptionPane.INFORMATION_MESSAGE,
                                GanttLanguage.getInstance().formatText("help.recover.autosaveInfo", f.getName(), new Date(f.lastModified()),
                                        f.length()), new Action[] { ok, skip, CancelAction.CLOSE });
                }

                protected void recover(Document recoverDocument) {
                        try {
                                myProjectUiFacade.openProject(new ReadOnlyProxyDocument(recoverDocument), myProject);
                        } catch (Throwable e) {
                                GPLogger.log(new RuntimeException("Failed to recover file " + recoverDocument.getFileName(), e));
                        }
                }
        }

}
