package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.parents;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Marco Ebbinghaus
 * Date: 16.10.12
 * Time: 08:37
 * To change this template use File | Settings | File Templates.
 */
public class PlanningUnitSelect
    extends ListBox<PlanningUnit>
    implements HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ListBox<PlanningUnit>, PlanningUnit>> {

  private List<Component> listenerComponents = new ArrayList<>();
  private DaoFactory      baseDaoFactoryBean;
  private PlannedProject  projectFromDB;

  public PlanningUnitSelect() {
    baseDaoFactoryBean = DaoFactorySingelton.getInstance();
    final PlannedProject projectFromSession = VaadinSession.getCurrent()
                                                           .getAttribute(PlannedProject.class);
    projectFromDB = baseDaoFactoryBean.getPlannedProjectDAO()
                                      .findByID(projectFromSession.getId());
    baseDaoFactoryBean.getEntityManager()
                      .refresh(projectFromDB);
    final Set<PlanningUnit> allPlanningUnitsOfProject  = projectFromDB.getPlanningUnits();
    final Set<PlanningUnit> planningUnitsWithoutParent = new HashSet<>();
    for (final PlanningUnit planningUnit : allPlanningUnitsOfProject) {
      if (planningUnit.getParent() == null) {
        planningUnitsWithoutParent.add(planningUnit);
      }
    }
    setItems(planningUnitsWithoutParent);
    setRenderer(new ComponentRenderer<>(planningUnit -> new Label(planningUnit.getPlanningUnitName())));
//        setNullSelectionAllowed(false);
//        setImmediate(true);
//        setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
//        setItemCaptionPropertyId(PlanningUnit.NAME);
    addValueChangeListener(this);
  }

  public PlannedProject getProjectFromDB() {
    return projectFromDB;
  }

  public void addListenerComponent(Component component) {
    listenerComponents.add(component);
  }

  public boolean isContentOnlyPlaceholder() {
    final List<PlanningUnit> planningUnits = getDataProvider().fetch(new Query<>())
                                                              .collect(Collectors.toList());
    if (planningUnits.isEmpty()) {
      return false;
    } else if (planningUnits.size() == 1 && planningUnits.get(0)
                                                         .getId()
                                                         .equals(ProjektplanungScreen.PLATZHALTER_ID)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void valueChanged(ComponentValueChangeEvent listBoxComponentValueChangeEvent) {
    final boolean placeholderOnly = isContentOnlyPlaceholder();
    for (Component listenerComponent : listenerComponents) {
      if (placeholderOnly) {
        if (listenerComponent instanceof AbstractField) {
          ((AbstractField) listenerComponent).setEnabled(false);
        }
      }
    }
  }
}
