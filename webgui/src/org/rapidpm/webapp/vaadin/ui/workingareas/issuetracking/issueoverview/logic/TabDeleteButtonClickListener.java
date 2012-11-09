package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.data.Container;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.CommentsDataContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.RelationsDataContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.TestCasesDataContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows.AddRelationWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows.DeleteCommentWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows.DeleteRelationWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows.DeleteTestcaseWindow;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 07.11.12
 * Time: 19:35
 * To change this template use File | Settings | File Templates.
 */
public class TabDeleteButtonClickListener implements Button.ClickListener {
    private static Logger logger = Logger.getLogger(TabDeleteButtonClickListener.class);

    private final IssueOverviewScreen screen;
    private final Table table;

    public TabDeleteButtonClickListener(final IssueOverviewScreen screen, final Table table){
        this.screen = screen;
        this.table = table;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        final Container container = table.getContainerDataSource();
        if (container instanceof CommentsDataContainer) {
            if (logger.isDebugEnabled())
                logger.debug("DeleteButton Comment");
            UI.getCurrent().addWindow(new DeleteCommentWindow(screen, table));
        } else if (container instanceof TestCasesDataContainer) {
            if (logger.isDebugEnabled())
                logger.debug("DeleteButton IssueTestCase");
            UI.getCurrent().addWindow(new DeleteTestcaseWindow(screen, table));
        } else if (container instanceof RelationsDataContainer) {
            if (logger.isDebugEnabled())
                logger.debug("DeleteButton Relation");
            UI.getCurrent().addWindow(new DeleteRelationWindow(screen, table));
        }

    }
}
