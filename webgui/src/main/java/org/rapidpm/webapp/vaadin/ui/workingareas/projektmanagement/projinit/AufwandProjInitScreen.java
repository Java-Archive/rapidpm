package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit;

//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.AbstractView;
import org.rapidpm.webapp.vaadin.ui.MainAppLayout;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TreeTableHeaderClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic.OverviewTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic.TreeTableFiller;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.DATE_FORMAT;

@Route(value = "aufwand", layout = MainAppLayout.class)
@Caption("Projektinitialisierung")
@Icon(VaadinIcon.INFO)
public class AufwandProjInitScreen extends Screen {

    private Button saveButton = new Button();
    private Button undoButton;
    private Checkbox expandCheckBox;
    private TextField kundeField;
    private TextField projektField;
    private DatePicker datumField;
    private TextField projektLeiterField;
    private TextField unterschriftField;
    private TextField manntageField;
    private TextField summeField;
    private Component hoursPerWorkingDayPanel;
    private HoursPerWorkingDayEditableLayout editableLayout;

//    private HierarchicalContainer dataSource = new HierarchicalContainer();
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

    public AufwandProjInitScreen() {
//        super(ui);

//        bean = EJBFactory.getEjbInstance(AufwandProjInitScreenBean.class);
//        baseDaoFactoryBean = bean.getDaoFactoryBean();
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        try{
            hoursPerWorkingDayPanel = new RapidPanel();
//            editableLayout = new HoursPerWorkingDayEditableLayout(this, hoursPerWorkingDayPanel);
//            hoursPerWorkingDayPanel.setContent(editableLayout);
//            hoursPerWorkingDayPanel.setStyleName(Reindeer.PANEL_LIGHT);
//            hoursPerWorkingDayPanel.setSizeUndefined();
            final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO().loadAllEntities();
            if(plannedProjects == null || plannedProjects.isEmpty()){
                throw new NoProjectsException();
            }
            erstelleUnterschriftLayout();
            erstelleFelderLayout();

            expandCheckBox = new ExpandTableCheckBox(treeTable, null);
            undoButton = new UndoButton(this, treeTable, null);
//            undoButton.setVisible(false);

            final TreeTableFiller treeTableFiller = new TreeTableFiller(null, this, treeTable);
            treeTableFiller.fill();

            final OverviewTableFiller overviewTableFiller = new OverviewTableFiller(null, uebersichtTable);
            overviewTableFiller.fill();

            fillFields();

//            uebersichtTable.setPageLength(2);
            uebersichtTable.setConnectedTable(treeTable);
            uebersichtTable.setSizeFull();
            treeTable.setConnectedTable(uebersichtTable);
//            treeTable.addHeaderClickListener(new TreeTableHeaderClickListener(undoButton));
//            treeTable.setSizeFull();
//            treeTable.setPageLength(10);

            table1layout.add(uebersichtTable);
            table1layout.setSizeFull();
            table1layout.setMargin(true);

            table2layout.add(expandCheckBox);
            table2layout.add(undoButton);
//            table2layout.add(treeTable);
//            table2layout.setExpandRatio(expandCheckBox, 10);
//            table2layout.setExpandRatio(undoButton, 10);
//            table2layout.setExpandRatio(treeTable, 80);
            table2layout.setSizeFull();
            table2layout.setMargin(true);


            lowerFormLayout.add(saveButton);

//            formLayout.add(upperFormLayout);
            formLayout.add(lowerFormLayout);
            formLayout.setVisible(false);
            setComponents();

            doInternationalization();
        } catch (final NoProjectsException e){
        removeAll();
        final NoProjectsScreen noProjectsScreen = new NoProjectsScreen();
        add(noProjectsScreen);
    }

    }

    public void doInternationalization() {
        ResourceBundle messagesBundle = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
        expandCheckBox.setLabel(messagesBundle.getString("costsinit_expand"));
        saveButton.setText(messagesBundle.getString("save"));
        undoButton.setText(messagesBundle.getString("costsinit_removesortorder"));
        kundeField.setValue(messagesBundle.getString("initscreen_customer"));
        projektField.setValue(messagesBundle.getString("initscreen_project"));
        datumField.setLabel(messagesBundle.getString("costsinit_date"));
        manntageField.setValue(messagesBundle.getString("costsinit_manday"));
        summeField.setValue(messagesBundle.getString("costsinit_sumInDDHHMM"));
        projektLeiterField.setValue(messagesBundle.getString("initscreen_projectleader"));
        unterschriftField.setValue(messagesBundle.getString("initscreen_signature"));
    }

    public void fillFields() {
        final TimesCalculator timesCalculator = new TimesCalculator();
        timesCalculator.calculate();
        manntageField.setReadOnly(false);
        summeField.setReadOnly(false);
        manntageField.setValue(timesCalculator.getMannTageGerundet());
        summeField.setValue(timesCalculator.getGesamtSummeItem().toString());
        manntageField.setReadOnly(true);
        summeField.setReadOnly(true);
        final PlannedProject projectFromSession = VaadinSession.getCurrent().getAttribute(PlannedProject.class);
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final PlannedProjectDAO plannedProjectDAO = daoFactory.getPlannedProjectDAO();
        final PlannedProject projekt = plannedProjectDAO.findByID(projectFromSession.getId());
        projektField.setValue(projekt.getProjektName());
    }

    private void erstelleFelderLayout() {
        final FormLayout layoutLinks = new FormLayout();
        final FormLayout layoutRechts = new FormLayout();

        kundeField = new TextField();
        projektField = new TextField();
        datumField = new DatePicker("Datum: ");
//        datumField.setDateFormat(DATE_FORMAT.toPattern());
        manntageField = new TextField();
        summeField = new TextField();
        felderLayout.setSizeUndefined();
        felderLayout.setWidth(ABSOLUTE_WIDTH);

        layoutLinks.add(kundeField);
        layoutLinks.add(projektField);
        layoutLinks.add(datumField);

        layoutRechts.add(manntageField);
        layoutRechts.add(summeField);

        felderLayout.add(layoutLinks);
        felderLayout.add(layoutRechts);

    }

    private void erstelleUnterschriftLayout() {
        projektLeiterField = new TextField();
        unterschriftField = new TextField();
        unterschriftLayout.setSizeUndefined();
        unterschriftLayout.setWidth("350px");

        unterschriftLayout.add(projektLeiterField);
        unterschriftLayout.add(unterschriftField);
    }

    public void setComponents() {
        add(felderLayout);
        add(hoursPerWorkingDayPanel);
        add(unterschriftLayout);
        add(table1layout);
        add(table2layout);
        add(formLayout);
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

//    public HierarchicalContainer getDataSource() {
//        return dataSource;
//    }

}
