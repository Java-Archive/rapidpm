package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.distribution;

import com.vaadin.data.util.converter.StringToNumberConverter;
import com.vaadin.ui.*;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.projectmanagement.planning.finance.PlannedOffer;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.distribution.datenmodell.PlannedOfferContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.distribution.logic.PlannedOfferCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.distribution.logic.PlannedOfferConverterAdder;

import javax.persistence.EntityManager;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.rapidpm.Constants.DECIMAL_FORMAT;
import static org.rapidpm.Constants.EUR;

public class DistributionScreen extends Screen {
    private TextField vertrieblerField;
    private TextField datumField;
    private TextField summeMitAufschlagField;
    private TextField summeOhneAufschlagField;
    private TextField verhandelterPreisField;
    private TextArea bemerkungenArea;

    private Table vertriebsTable;
    private PlannedOfferContainer container;

    private FormLayout vertrieblerLayout = new FormLayout();
    private VerticalLayout tableLayout = new VerticalLayout();
    private FormLayout bottomLayout;
    
    private DistributionScreenBean bean;
    private DaoFactoryBean baseDaoFactoryBean;
    private PlannedOfferCalculator calculator;

    public DistributionScreen(MainUI ui) {
        super(ui);
        
        bean = EJBFactory.getEjbInstance(DistributionScreenBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();
        refreshEntities(baseDaoFactoryBean);

        
        erstelleVertrieblerLayout();
        erstelleStandardTableLayout(new Label("Uebersicht"), vertriebsTable, tableLayout);
        erstelleBottomLayout();
        setComponents();
        doInternationalization();
    }

    @Override
    public void doInternationalization() {
        vertrieblerField.setCaption(messagesBundle.getString("distri_responsible"));
        datumField.setCaption(messagesBundle.getString("distri_date"));
        summeMitAufschlagField.setCaption(messagesBundle.getString("distri_sumWithAddition"));
        summeOhneAufschlagField.setCaption(messagesBundle.getString("distri_sumWithoutAddition"));
        verhandelterPreisField.setCaption(messagesBundle.getString("distri_negotiatedPrice"));
        bemerkungenArea.setCaption(messagesBundle.getString("distri_comments"));
    }

    private void erstelleBottomLayout() {
        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        bottomLayout = new FormLayout();
        summeMitAufschlagField = new TextField();
        summeMitAufschlagField.setValue(format.format(calculator.getSumWithDistributionSpread()) + EUR);
        summeMitAufschlagField.setReadOnly(true);
        summeOhneAufschlagField = new TextField();
        summeOhneAufschlagField.setValue(format.format(calculator.getSumWithoutDistributionSpread()) + EUR);
        summeOhneAufschlagField.setReadOnly(true);
        verhandelterPreisField = new TextField();
        bemerkungenArea = new TextArea();
        bemerkungenArea.setWidth("500px");
        bemerkungenArea.setHeight("300px");

        bottomLayout.addComponent(summeMitAufschlagField);
        bottomLayout.addComponent(summeOhneAufschlagField);
        bottomLayout.addComponent(verhandelterPreisField);

        bottomLayout.addComponent(bemerkungenArea);
    }

    private void erstelleStandardTableLayout(Label ueberschrift, Table tabelle, Layout layout) {
        final PlannedProject project = ui.getCurrentProject();
        calculator = new PlannedOfferCalculator(project, messagesBundle);
        calculator.calculate();

        tabelle = new Table();
        container = new PlannedOfferContainer();
        container.fill(project.getPlannedOfferList());
        tabelle.setContainerDataSource(container);

        final List<String> visibleColumns = new ArrayList<>();
        visibleColumns.add(PlannedOffer.NAME);
        visibleColumns.add(PlannedOffer.PERCENT);
        visibleColumns.add(PlannedOffer.DAYS_HOURS_MINS);
        visibleColumns.add(PlannedOffer.EUROS_PER_HOUR);
        visibleColumns.add(PlannedOffer.COSTS);
        visibleColumns.add(PlannedOffer.PERCENT_WITH);
        visibleColumns.add(PlannedOffer.PERCENT_WITHOUT);

        final List<String> columnOrder = new ArrayList<>();
        columnOrder.add(messagesBundle.getString("distri_name"));
        columnOrder.add(messagesBundle.getString("distri_percent"));
        columnOrder.add(messagesBundle.getString("distri_dhhmm"));
        columnOrder.add(messagesBundle.getString("distri_eurosperhour"));
        columnOrder.add(messagesBundle.getString("distri_sum"));
        columnOrder.add(messagesBundle.getString("distri_percentwith"));
        columnOrder.add(messagesBundle.getString("distri_percentwithout"));

        tabelle.setVisibleColumns(visibleColumns.toArray());
        final String[] columnHeaders = new String[columnOrder.size()];
        columnOrder.toArray(columnHeaders);
        tabelle.setColumnHeaders(columnHeaders);

        final PlannedOfferConverterAdder converterAdder = new PlannedOfferConverterAdder();
        converterAdder.addConvertersTo(tabelle);

        layout.addComponent(ueberschrift);
        layout.addComponent(tabelle);
    }

    private void erstelleVertrieblerLayout() {
        vertrieblerLayout.setWidth("600px");
        vertrieblerField = new TextField();
        datumField = new TextField();
        vertrieblerLayout.addComponent(vertrieblerField);
        vertrieblerLayout.addComponent(datumField);
    }

    @Override
    public void setComponents() {
        addComponent(vertrieblerLayout);
        addComponent(tableLayout);
        addComponent(bottomLayout);
    }

    private void refreshEntities(DaoFactoryBean baseDaoFactoryBean) {
        final EntityManager entityManager = baseDaoFactoryBean.getEntityManager();
        for(final PlannedProject plannedProject : baseDaoFactoryBean.getPlannedProjectDAO().loadAllEntities()){
            entityManager.refresh(plannedProject);
        }
        for(final PlanningUnitElement planningUnitElement : baseDaoFactoryBean.getPlanningUnitElementDAO().loadAllEntities()){
            entityManager.refresh(planningUnitElement);
        }
        for(final PlanningUnit planningUnit : baseDaoFactoryBean.getPlanningUnitDAO().loadAllEntities()){
            entityManager.refresh(planningUnit);
        }
        for(final RessourceGroup ressourceGroup : baseDaoFactoryBean.getRessourceGroupDAO().loadAllEntities()){
            entityManager.refresh(ressourceGroup);
        }
        for(final Benutzer benutzer : baseDaoFactoryBean.getBenutzerDAO().loadAllEntities()){
            entityManager.refresh(benutzer);
        }
        for(final PlannedOffer plannedOffer : baseDaoFactoryBean.getPlannedOfferDAO().loadAllEntities()){
            entityManager.refresh(plannedOffer);
        }
    }


    public TextField getVertrieblerField() {
        return vertrieblerField;
    }

    public void setVertrieblerField(TextField vertrieblerField) {
        this.vertrieblerField = vertrieblerField;
    }

    public TextField getDatumField() {
        return datumField;
    }

    public void setDatumField(TextField datumField) {
        this.datumField = datumField;
    }

    public TextField getSummeMitAufschlagField() {
        return summeMitAufschlagField;
    }

    public void setSummeMitAufschlagField(TextField summeMitAufschlagField) {
        this.summeMitAufschlagField = summeMitAufschlagField;
    }

    public TextField getSummeOhneAufschlagField() {
        return summeOhneAufschlagField;
    }

    public void setSummeOhneAufschlagField(TextField summeOhneAufschlagField) {
        this.summeOhneAufschlagField = summeOhneAufschlagField;
    }

    public TextField getVerhandelterPreisField() {
        return verhandelterPreisField;
    }

    public void setVerhandelterPreisField(TextField verhandelterPreisField) {
        this.verhandelterPreisField = verhandelterPreisField;
    }

    public TextArea getBemerkungenArea() {
        return bemerkungenArea;
    }

    public void setBemerkungenArea(TextArea bemerkungenArea) {
        this.bemerkungenArea = bemerkungenArea;
    }

    public Table getVertriebsTable() {
        return vertriebsTable;
    }

    public void setVertriebsTable(Table vertriebsTable) {
        this.vertriebsTable = vertriebsTable;
    }

    public VerticalLayout getTableLayout() {
        return tableLayout;
    }

    public void setTableLayout(VerticalLayout tableLayout) {
        this.tableLayout = tableLayout;
    }
    
    

}