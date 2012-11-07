package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 07.11.12
 * Time: 09:03
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractIssueDataContainer extends IndexedContainer {
    protected static Logger logger = Logger.getLogger(AbstractIssueDataContainer.class);

    private final IssueOverviewScreen screen;
    private List<Object> visibleColumns;
    protected boolean finishedLoading = false;

    public AbstractIssueDataContainer(final IssueOverviewScreen screen) {
        this.screen = screen;
        visibleColumns = setVisibleColumns();
//        this.addItemSetChangeListener(new DataContainerItemSetChangeListener());
    }

    protected abstract List<Object> setVisibleColumns();
    public abstract void fillContainer(final IssueBase issue);
    public abstract IssueBase getConnIssueFromItemId(Object itemId);
    public abstract boolean refresh();

    public List<Object> getVisibleColumns() {
        return visibleColumns;
    }


//    protected class DataContainerItemSetChangeListener implements ItemSetChangeListener {
//
//        @Override
//        public void containerItemSetChange(Container.ItemSetChangeEvent event) {
//            if (finishedLoading) {
//                refresh();
//                logger.info("REFRESH!");
//            }
//        }
//    }
}
