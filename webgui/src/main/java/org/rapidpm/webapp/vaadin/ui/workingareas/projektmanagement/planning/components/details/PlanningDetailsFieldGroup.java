package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//import com.vaadin.server.ThemeResource;
//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 26.09.12
 * Time: 12:51
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningDetailsFieldGroup extends FieldGroup {

  private List<AbstractField> fieldList = new ArrayList<>();
  private ResourceBundle messages;

  public PlanningDetailsFieldGroup(final ResourceBundle messages, final PlanningUnit unmanagedPlanningUnit) {
    this.messages = messages;
    final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
    final PlanningUnit planningUnit = daoFactory.getPlanningUnitDAO().findByID(unmanagedPlanningUnit.getId(), true);
    if (planningUnit == null) {
      setItemDataSource(new BeanItem<>(unmanagedPlanningUnit));
    } else {
      setItemDataSource(new BeanItem<>(planningUnit));
    }

    buildForm();
  }

  private void buildForm() {
    final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
    final List<Benutzer> users = daoFactory.getBenutzerDAO().findAll();
    for (final Object propertyId : getUnboundPropertyIds()) {
      final String spaltenName = propertyId.toString();
      switch (spaltenName) {
        case (PlanningUnit.RESPONSIBLE):
          final ComboBox responsiblePersonBox = generateBox(messages.getString("planning_responsible"),
              new BeanItemContainer<>(Benutzer.class, users), Benutzer.LOGIN);
          bind(responsiblePersonBox, propertyId);
          fieldList.add(responsiblePersonBox);
          break;
        case (PlanningUnit.ORDERNUMBER):
          final TextField orderNumberField = new TextField(messages.getString("planning_ordernumber"));
          bind(orderNumberField, propertyId);
          fieldList.add(orderNumberField);
          break;
        case (PlanningUnit.COMPLEXITY):
          final TextField complexityField = new TextField(messages.getString("planning_complexity"));
          bind(complexityField, propertyId);
          fieldList.add(complexityField);
          break;
        case (PlanningUnit.STORYPTS):
          final TextField storyPointsField = new TextField(messages.getString("planning_storypoints"));
          bind(storyPointsField, propertyId);
          fieldList.add(storyPointsField);
          break;
        default:
          break;
      }
    }
  }

  public ComboBox generateBox(final String caption, final BeanItemContainer container,
                              final String itemCaptionPropertyId) {
    final ComboBox box = new ComboBox(caption, container);
    box.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
    box.setItemCaptionPropertyId(itemCaptionPropertyId);
    return box;
  }

  public List<AbstractField> getFieldList() {
    return fieldList;
  }
}
