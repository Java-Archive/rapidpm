package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TreeTableHeaderClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic.OverviewTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic.TreeTableFiller;

import java.util.Date;
import java.util.List;

import static org.rapidpm.Constants.DATE_FORMAT;

//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;

public class AufwandProjInitScreen extends Screen {

    private Button saveButton = new Button();
    private Button undoButton;
    private CheckBox expandCheckBox;
    private TextField kundeField;
    private TextField projektField;
    private DateField datumField;
    private TextField projektLeiterField;
    private TextField unterschriftField;
    private TextField manntageField;
    private TextField summeField;
    private Panel hoursPerWorkingDayPanel;
    private HoursPerWorkingDayEditableLayout editableLayout;

    private HierarchicalContainer dataSource = new HierarchicalContainer();
    private MyTreeTable treeTable = new MyTreeTable();
    private MyTable uebersichtTable = new MyTable();
//    private AufwandProjInitScreenBean bean;
//    private DaoFactoryBean baseDaoFactoryBean;

    private static final String ABSOLUTE_WIDTH = "700px";

    private HorizontalLayout felderLayout = new HorizontalLayout();
    private FormLayout unterschriftLayout = new FormLayout();
    private VerticalLayout table1layout = new VerticalLayout();
    private VerticalLayout table2layout = new VerticalLayout();
    private VerticalLayout formLayout = new VerticalLayout();
    private GridLayout upperFormLayout = new GridLayout(2, 10);
    private VerticalLayout lowerFormLayout = new VerticalLayout();

    public AufwandProjInitScreen(final MainUI ui) {
        super(ui);

//        bean = EJBFactory.getEjbInstance(AufwandProjInitScreenBean.class);
//        baseDaoFactoryBean = bean.getDaoFactoryBean();
        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
        try{
            hoursPerWorkingDayPanel = new Panel();
            editableLayout = new HoursPerWorkingDayEditableLayout(this, hoursPerWorkingDayPanel);
            hoursPerWorkingDayPanel.setContent(editableLayout);
            hoursPerWorkingDayPanel.setStyleName(Reindeer.PANEL_LIGHT);
            hoursPerWorkingDayPanel.setSizeUndefined();
            final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO().findAll();
            if(plannedProjects == null || plannedProjects.isEmpty()){
                throw new NoProjectsException();
            }
            erstelleUnterschriftLayout();
            erstelleFelderLayout();

            expandCheckBox = new ExpandTableCheckBox(treeTable, dataSource);
            undoButton = new UndoButton(this, treeTable, dataSource);
            undoButton.setVisible(false);

            final TreeTableFiller treeTableFiller = new TreeTableFiller(messagesBundle, this, treeTable, dataSource);
            treeTableFiller.fill();

            final OverviewTableFiller overviewTableFiller = new OverviewTableFiller(this, uebersichtTable);
            overviewTableFiller.fill();

            fillFields();

            uebersichtTable.setPageLength(2);
            uebersichtTable.setConnectedTable(treeTable);
            uebersichtTable.setSizeFull();
            treeTable.setConnectedTable(uebersichtTable);
            treeTable.addHeaderClickListener(new TreeTableHeaderClickListener(undoButton));
            treeTable.setSizeFull();
            treeTable.setPageLength(10);

            table1layout.addComponent(uebersichtTable);
            table1layout.setSizeFull();
            table1layout.setMargin(true);

            table2layout.addComponent(expandCheckBox);
            table2layout.addComponent(undoButton);
            table2layout.addComponent(treeTable);
            table2layout.setExpandRatio(expandCheckBox, 10);
            table2layout.setExpandRatio(undoButton, 10);
            table2layout.setExpandRatio(treeTable, 80);
            table2layout.setSizeFull();
            table2layout.setMargin(true);


            lowerFormLayout.addComponent(saveButton);

            formLayout.addComponent(upperFormLayout);
            formLayout.addComponent(lowerFormLayout);
            formLayout.setVisible(false);
            setComponents();

            doInternationalization();
        } catch (final NoProjectsException e){
        removeAllComponents();
        final NoProjectsScreen noProjectsScreen = new NoProjectsScreen(ui);
        addComponent(noProjectsScreen);
    }

    }

    @Override
    public void doInternationalization() {
        expandCheckBox.setCaption(messagesBundle.getString("costsinit_expand"));
        saveButton.setCaption(messagesBundle.getString("save"));
        undoButton.setCaption(messagesBundle.getString("costsinit_removesortorder"));
        kundeField.setCaption(messagesBundle.getString("initscreen_customer"));
        projektField.setCaption(messagesBundle.getString("initscreen_project"));
        datumField.setCaption(messagesBundle.getString("costsinit_date"));
        manntageField.setCaption(messagesBundle.getString("costsinit_manday"));
        summeField.setCaption(messagesBundle.getString("costsinit_sumInDDHHMM"));
        projektLeiterField.setCaption(messagesBundle.getString("initscreen_projectleader"));
        unterschriftField.setCaption(messagesBundle.getString("initscreen_signature"));
    }

    public void fillFields() {
        final TimesCalculator timesCalculator = new TimesCalculator(this);
        timesCalculator.calculate();
        manntageField.setReadOnly(false);
        summeField.setReadOnly(false);
        manntageField.setValue(timesCalculator.getMannTageGerundet().toString());
        summeField.setValue(timesCalculator.getGesamtSummeItem().toString());
        manntageField.setReadOnly(true);
        summeField.setReadOnly(true);
        final PlannedProject projectFromSession = ui.getCurrentProject();
        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
        final PlannedProjectDAO plannedProjectDAO = daoFactory.getPlannedProjectDAO();
        final PlannedProject projekt = plannedProjectDAO.findByID(projectFromSession.getId(), true);
        projektField.setValue(projekt.getProjektName());
    }

    private void erstelleFelderLayout() {
        final FormLayout layoutLinks = new FormLayout();
        final FormLayout layoutRechts = new FormLayout();

        kundeField = new TextField();
        projektField = new TextField();
        datumField = new DateField("Datum: ", new Date());
        datumField.setDateFormat(DATE_FORMAT.toPattern());
        manntageField = new TextField();
        summeField = new TextField();
        felderLayout.setSizeUndefined();
        felderLayout.setWidth(ABSOLUTE_WIDTH);

        layoutLinks.addComponent(kundeField);
        layoutLinks.addComponent(projektField);
        layoutLinks.addComponent(datumField);

        layoutRechts.addComponent(manntageField);
        layoutRechts.addComponent(summeField);

        felderLayout.addComponent(layoutLinks);
        felderLayout.addComponent(layoutRechts);

    }

    private void erstelleUnterschriftLayout() {
        projektLeiterField = new TextField();
        unterschriftField = new TextField();
        unterschriftLayout.setSizeUndefined();
        unterschriftLayout.setWidth("350px");

        unterschriftLayout.addComponent(projektLeiterField);
        unterschriftLayout.addComponent(unterschriftField);
    }

    @Override
    public void setComponents() {
        addComponent(felderLayout);
        addComponent(hoursPerWorkingDayPanel);
        addComponent(unterschriftLayout);
        addComponent(table1layout);
        addComponent(table2layout);
        addComponent(formLayout);
    }

    public VerticalLayout getFormLayout() {
        return formLayout;
    }

    public GridLayout getUpperFormLayout() {
        return upperFormLayout;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public HierarchicalContainer getDataSource() {
        return dataSource;
    }

}
