package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview;


import com.vaadin.ui.*;
//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueTabSheet;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueTreeLayout;
//import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details.PlanningDetailsFieldGroupBean;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 20.09.12
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class IssueOverviewScreen extends Screen{

    private HorizontalSplitPanel hSplitPanel;
    private IssueTreeLayout treeLayout;
    private IssueTabSheet issueTabSheet;


    public IssueOverviewScreen(MainUI ui) {
        super(ui);
        this.setSizeFull();
        //this.setMargin(true);
        hSplitPanel = new HorizontalSplitPanel();
        hSplitPanel.setSplitPosition(30, Unit.PERCENTAGE);
        issueTabSheet = new IssueTabSheet(this);
        treeLayout = new IssueTreeLayout(this, issueTabSheet);
        doInternationalization();
        setComponents();
    }

    public PlannedProject getCurrentProject() {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final PlannedProjectDAO plannedProjectDAO = daoFactory.getPlannedProjectDAO();
        return plannedProjectDAO.findByID(ui.getCurrentProject().getId());
    }

    public IssueTabSheet getIssueTabSheet() {
        return issueTabSheet;
    }

    public IssueTreeLayout getIssueTreeLayout() {
        return treeLayout;
    }

    @Override
    public void setComponents() {
        hSplitPanel.setFirstComponent(treeLayout);
        hSplitPanel.setSecondComponent(issueTabSheet);
        addComponent(hSplitPanel);
    }

    @Override
    public void doInternationalization() {
    }
}
