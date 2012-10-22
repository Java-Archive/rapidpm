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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Marco Ebbinghaus
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
        final PlannedProject projectFromSession = ui.getCurrentProject();
        projectFromDB = baseDaoFactoryBean.getPlannedProjectDAO().findByID
                (projectFromSession.getId());
        baseDaoFactoryBean.getEntityManager().refresh(projectFromDB);
        final Set<PlanningUnit> allPlanningUnitsOfProject = projectFromDB.getPlanningUnits();
        final Set<PlanningUnit> planningUnitsWithoutParent = new HashSet<>();
        for (final PlanningUnit planningUnit : allPlanningUnitsOfProject) {
            baseDaoFactoryBean.getEntityManager().refresh(planningUnit);
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

    public PlannedProject getProjectFromDB() {
        return projectFromDB;
    }
}
