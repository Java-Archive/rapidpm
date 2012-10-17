package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.parents;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ListSelect;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.MainUI;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 16.10.12
 * Time: 08:37
 * To change this template use File | Settings | File Templates.
 */
public class PlanningUnitSelect extends ListSelect {

    private PlanningUnitSelectBean bean;
    private DaoFactoryBean baseDaoFactoryBean;
    private PlannedProject projectFromDB;

    public PlanningUnitSelect(final MainUI ui){
        bean = EJBFactory.getEjbInstance(PlanningUnitSelectBean.class);
        baseDaoFactoryBean = bean.getDaoFactoryBean();
        refreshEntities(baseDaoFactoryBean);
        final PlannedProject projectFromSession = ui.getCurrentProject();
        projectFromDB = baseDaoFactoryBean.getPlannedProjectDAO().findByID
                (projectFromSession.getId());
        final List<PlanningUnit> allPlanningUnitsOfProject = projectFromDB.getPlanningUnits();
        final List<PlanningUnit> planningUnitsWithoutParent = new ArrayList<>();
        for (final PlanningUnit planningUnit : allPlanningUnitsOfProject) {
            if(planningUnit.getParent() == null){
                planningUnitsWithoutParent.add(planningUnit);
            }
        }
        setContainerDataSource(new BeanItemContainer<>(PlanningUnit.class, planningUnitsWithoutParent));
        setNullSelectionAllowed(false);
        setImmediate(true);
        setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        setItemCaptionPropertyId(PlanningUnit.NAME);
    }

    private void refreshEntities(final DaoFactoryBean baseDaoFactoryBean) {
        final EntityManager entityManager = baseDaoFactoryBean.getEntityManager();
//        for(final PlannedProject plannedProject : baseDaoFactoryBean.getPlannedProjectDAO().loadAllEntities()){
//            entityManager.refresh(plannedProject);
//        }
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
    }

    public PlannedProject getProjectFromDB() {
        return projectFromDB;
    }
}
