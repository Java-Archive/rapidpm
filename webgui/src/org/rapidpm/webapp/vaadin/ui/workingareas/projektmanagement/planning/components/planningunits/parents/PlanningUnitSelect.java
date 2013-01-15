package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.parents;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ListSelect;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.MainUI;

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

    private DaoFactory baseDaoFactoryBean;
    private PlannedProject projectFromDB;

    public PlanningUnitSelect(final MainUI ui){
        baseDaoFactoryBean = DaoFactorySingelton.getInstance();
        final PlannedProject projectFromSession = ui.getSession().getAttribute(PlannedProject.class);
        projectFromDB = baseDaoFactoryBean.getPlannedProjectDAO().findByID
                (projectFromSession.getId());
        baseDaoFactoryBean.getEntityManager().refresh(projectFromDB);
        final Set<PlanningUnit> allPlanningUnitsOfProject = projectFromDB.getPlanningUnits();
        final Set<PlanningUnit> planningUnitsWithoutParent = new HashSet<>();
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

    public PlannedProject getProjectFromDB() {
        return projectFromDB;
    }
}
