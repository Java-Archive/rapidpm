package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.exceptions.MissingAttributeException;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.exceptions.NameAlreadyInUseException;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.exceptions.NoNameException;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TreeIssueBaseContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueDetailsLayout;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 05.10.12
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
public class AddIssueWindow extends RapidWindow {
    private static Logger logger = Logger.getLogger(AddIssueWindow.class);

    private final IssueOverviewScreen screen;
    private final Tree issueTree;
    private AddIssueWindow self;

    private IssueDetailsLayout addDetailsLayout;

    public AddIssueWindow(final IssueOverviewScreen screen, final Tree issueTree) {
        if (screen == null)
            throw new NullPointerException("Screen must not be null");
        if (issueTree == null)
            throw new NullPointerException("Tree must not be null");

        self = this;
        this.screen = screen;
        this.issueTree = issueTree;
        setCaption(screen.getMessagesBundle().getString("issuetracking_issue_addwindow"));
        this.setModal(true);
        this.setResizable(false);
        this.setWidth("70%");
        addDetailsLayout = new IssueDetailsLayout(screen, false);
        addDetailsLayout.addSaveButtonClickListener(new AddIssueSaveClickListener());
        addDetailsLayout.addCancelButtonClickListener(new AddIssueCancelClickListener());
        this.addComponent(addDetailsLayout);
    }

    private class AddIssueSaveClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            try {
                IssueBaseDAO issueDao;
                issueDao = DaoFactorySingelton.getInstance().getIssueBaseDAO(screen.getUi().getCurrentProject().getId());

                IssueBase childIssue = addDetailsLayout.setIssueProperties(true);
                if (childIssue != null) {
                    try {
                        childIssue = issueDao.persist(childIssue);
                    } catch (IllegalArgumentException e) {
                        throw new NameAlreadyInUseException();
                    }
                    final Object itemId = issueTree.addItem();
                    final Object parentItemId = issueTree.getValue();

                    issueTree.getContainerDataSource().getContainerProperty(itemId,
                            TreeIssueBaseContainer.PROPERTY_CAPTION).setValue(childIssue.name());
                    issueTree.getContainerDataSource().getContainerProperty(itemId,
                            TreeIssueBaseContainer.PROPERTY_ISSUEBASE).setValue(childIssue);

                    if (parentItemId != null)  {
                        final IssueBase parentIssue = (IssueBase)issueTree.getContainerDataSource().getContainerProperty
                                (parentItemId, TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue();
                        parentIssue.addSubIssue(childIssue);
                        issueDao.persist(parentIssue);
                    }



                    issueTree.setChildrenAllowed(parentItemId, true);
                    issueTree.setParent(itemId, parentItemId);
                    issueTree.setChildrenAllowed(itemId, false);
                    issueTree.expandItem(parentItemId);
                    issueTree.select(itemId);
                    self.close();
                } else
                    if (logger.isDebugEnabled())
                        logger.debug("childIssue is null");

            } catch (MissingAttributeException e) {
                Notification.show(e.getLocalizedMessage(), Notification.Type.WARNING_MESSAGE);
            } catch (NoNameException e) {
                Notification.show(screen.getMessagesBundle().getString("issuetracking_exception_noname"),
                        Notification.Type.WARNING_MESSAGE);
            } catch (NameAlreadyInUseException e) {
                Notification.show(screen.getMessagesBundle().getString("issuetracking_exception_nameinuse"),
                        Notification.Type.WARNING_MESSAGE);
            }
        }
    }

    private class AddIssueCancelClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            self.close();
        }
    }
}
