package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.parents;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Component;
import com.vaadin.ui.ListSelect;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Marco Ebbinghaus
 * Date: 16.10.12
 * Time: 08:37
 * To change this template use File | Settings | File Templates.
 */
public class PlanningUnitSelect extends ListSelect implements Property.ValueChangeListener {

    private List<Component> listenerComponents = new ArrayList<>();
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
        addValueChangeListener(this);
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event){
        final boolean placeholderOnly = isContentOnlyPlaceholder();
        for (Component listenerComponent : listenerComponents) {
            if(placeholderOnly){
                listenerComponent.setEnabled(false);
            }
        }
    }

    public PlannedProject getProjectFromDB() {
        return projectFromDB;
    }

    public void addListenerComponent(Component component) {
        listenerComponents.add(component);
    }

    public boolean isContentOnlyPlaceholder() {
        final Collection<?> itemIds = getItemIds();
        final ArrayList<PlanningUnit> planningUnits = new ArrayList(itemIds);
        if(itemIds == null || itemIds.isEmpty()){
            return false;
        } else if(itemIds.size() == 1 && planningUnits.get(0).getId().equals(ProjektplanungScreen.PLATZHALTER_ID)){
            return true;
        } else {
            return false;
        }
    }
}
