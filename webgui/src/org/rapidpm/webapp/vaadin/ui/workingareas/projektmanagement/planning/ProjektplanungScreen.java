package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning;

import com.vaadin.data.Property;
import com.vaadin.ui.*;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.PlanningUnitsTree;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.PlanningUnitsTreePanelLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.parents.PlanningUnitSelect;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.PlanningCalculator;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 09:43
 */
public class ProjektplanungScreen extends Screen {

    private HorizontalSplitPanel splitPanel;
    private final VerticalLayout menuLayout;
    private Panel mainPanel;
    private Panel ressourcesPanel;
    private final Panel planningUnitPanel;
    private final Panel treePanel;
    private final Panel detailPanel;
    private PlanningUnitSelect planningUnitSelect;
    private final VerticalLayout mainLayout;
    private ProjektPlanungScreenBean projektplanungScreenBean;
    private DaoFactoryBean baseDaoFactoryBean;
    private PlanningUnitsTreePanelLayout planningUnitsTreePanelLayout;
    private PlanningUnitsTree planningUnitsTree;


    public ProjektplanungScreen(final MainUI ui) {
        super(ui);

        projektplanungScreenBean = EJBFactory.getEjbInstance(ProjektPlanungScreenBean.class);
        baseDaoFactoryBean = projektplanungScreenBean.getDaoFactoryBean();
        refreshEntities(baseDaoFactoryBean);


        final PlanningCalculator calculator = new PlanningCalculator(messagesBundle);
        calculator.calculate();
        splitPanel = new HorizontalSplitPanel();
        splitPanel.setSizeFull();
        splitPanel.setSplitPosition(40, Unit.PERCENTAGE);

        planningUnitPanel = new Panel();
        treePanel = new Panel();
        detailPanel = new Panel();

        menuLayout = new VerticalLayout();
        menuLayout.setSpacing(true);
        menuLayout.addComponent(planningUnitPanel);
        menuLayout.addComponent(treePanel);
        menuLayout.addComponent(detailPanel);

        mainPanel = new Panel();
        ressourcesPanel = new Panel();
        ressourcesPanel.setSizeFull();

        mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        mainLayout.addComponent(ressourcesPanel);
        mainLayout.addComponent(mainPanel);

        splitPanel.addComponent(menuLayout);
        splitPanel.addComponent(mainLayout);

        buildPlanningUnitPanel();

        doInternationalization();
        setComponents();
    }

    private void buildPlanningUnitPanel() {
        planningUnitSelect = new PlanningUnitSelect(ui);
        final PlannedProject projectFromDB = planningUnitSelect.getProjectFromDB();
        final List<?> ids = (List<?>) planningUnitSelect.getItemIds();
        planningUnitSelect.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(final Property.ValueChangeEvent valueChangeEvent) {
                final PlanningUnit value = (PlanningUnit) valueChangeEvent.getProperty().getValue();
                treePanel.getContent().removeAllComponents();
                detailPanel.getContent().removeAllComponents();
                treePanel.setCaption(value.getPlanningUnitName());
                fillTreePanel(value, projectFromDB);
            }
        });
        if (ids != null && !ids.isEmpty()) {
            planningUnitSelect.setValue(ids.get(0));
        }
        planningUnitPanel.setCaption(projectFromDB.getProjektName());
        planningUnitPanel.addComponent(planningUnitSelect);
    }

    public void fillTreePanel(final PlanningUnit selectedPlanningUnit,
                              final PlannedProject projekt) {
        planningUnitsTree = new PlanningUnitsTree(this, selectedPlanningUnit, projekt);
        planningUnitsTree.select(selectedPlanningUnit);
        planningUnitsTreePanelLayout = new PlanningUnitsTreePanelLayout(ProjektplanungScreen.this);
        treePanel.removeAllComponents();
        treePanel.addComponent(planningUnitsTreePanelLayout);
        treePanel.addComponent(planningUnitsTree);
    }

    @Override
    public void doInternationalization() {
        detailPanel.setCaption(messagesBundle.getString("details"));
    }

    @Override
    public void setComponents() {
        addComponent(splitPanel);
    }

    private void refreshEntities(final DaoFactoryBean baseDaoFactoryBean) {
        final EntityManager entityManager = baseDaoFactoryBean.getEntityManager();
        for (final PlannedProject plannedProject : baseDaoFactoryBean.getPlannedProjectDAO().loadAllEntities()) {
            entityManager.refresh(plannedProject);
        }
        for (final PlanningUnitElement planningUnitElement : baseDaoFactoryBean.getPlanningUnitElementDAO().loadAllEntities()) {
            entityManager.refresh(planningUnitElement);
        }
        for (final PlanningUnit planningUnit : baseDaoFactoryBean.getPlanningUnitDAO().loadAllEntities()) {
            entityManager.refresh(planningUnit);
        }
        for (final RessourceGroup ressourceGroup : baseDaoFactoryBean.getRessourceGroupDAO().loadAllEntities()) {
            entityManager.refresh(ressourceGroup);
        }
        for (final Benutzer benutzer : baseDaoFactoryBean.getBenutzerDAO().loadAllEntities()) {
            entityManager.refresh(benutzer);
        }
    }

    public PlanningUnitSelect getPlanningUnitSelect() {
        return planningUnitSelect;
    }

    public PlanningUnitsTree getPlanningUnitsTree() {
        return planningUnitsTree;
    }

    public Panel getDetailPanel() {
        return detailPanel;
    }

    public Panel getPlanningUnitPanel() {
        return planningUnitPanel;
    }

    public Panel getTreePanel() {
        return treePanel;
    }

    public VerticalLayout getMainLayout() {
        return mainLayout;
    }

    public Panel getMainPanel() {
        return mainPanel;
    }

    public Panel getRessourcesPanel() {
        return ressourcesPanel;
    }


}
