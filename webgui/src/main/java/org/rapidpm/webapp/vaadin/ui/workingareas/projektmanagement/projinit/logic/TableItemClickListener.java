package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.datenmodell.KnotenBlattEnum;

import java.util.ResourceBundle;

//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;

public class TableItemClickListener {


  private AufwandProjInitScreen screen;
  private KnotenBlattEnum       knotenBlattEnum;

  private ResourceBundle messages;


  public TableItemClickListener(final ResourceBundle bundle, final AufwandProjInitScreen screen) {
    this.messages = bundle;
    this.screen   = screen;
  }

//    @Override
//    public void itemClick(final ItemClickEvent event) {
//        final GridLayout formUnterlayout = screen.getUpperFormLayout();
//        final FieldGroup fieldGroup = new FieldGroup(event.getItem());
//        for (final Object listener : screen.getSaveButton().getListeners(Event.class)) {
//            if (listener instanceof ClickListener) {
//                screen.getSaveButton().removeClickListener((ClickListener) listener);
//            }
//
//        }
//        formUnterlayout.removeAllComponents();
//        final Object itemId = event.getItemId();
//        final HierarchicalContainer dataSource = screen.getDataSource();
//        final String aufgabeFromBundle = messages.getString("aufgabe");
//        final Item item = dataSource.getItem(itemId);
//        final String planningUnitName = item.getItemProperty(aufgabeFromBundle).getValue().toString();
//        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
//        final PlanningUnit planningUnit = daoFactory.getPlanningUnitDAO().loadPlanningUnitByName(planningUnitName);
//        if (planningUnit != null) {
//            final Set<PlanningUnit> kindPlanningUnits = planningUnit.getKindPlanningUnits();
//            if (kindPlanningUnits != null && (!kindPlanningUnits.isEmpty()) ) {
//                knotenBlattEnum = KnotenBlattEnum.KNOTEN;
//                buildRequiredFields(formUnterlayout, fieldGroup);
//            } else {
//                knotenBlattEnum = KnotenBlattEnum.BLATT;
//                for (final Object prop : fieldGroup.getUnboundPropertyIds()) {
//                    formUnterlayout.add(fieldGroup.buildAndBind(prop));
//                }
//                for (final Object propertyId : fieldGroup.getBoundPropertyIds()) {
//                    final Field<?> field = fieldGroup.getField(propertyId);
//                    if (!propertyId.equals(aufgabeFromBundle)) {
//                        field.addValidator(new DaysHoursMinutesFieldValidator(screen));
//                    }
//                    field.setRequired(true);
//                }
//            }
//        } else {
//            logger.warn("PlanningUnit nicht gefunden");
//        }
//
//        screen.getSaveButton().addClickListener(new SaveButtonClickListener(messages, fieldGroup, screen,
//                knotenBlattEnum, itemId));
//        screen.getFormLayout().setVisible(true);
//    }

//    private void buildRequiredFields(final GridLayout formUnterlayout, final FieldGroup fieldGroup) {
//        for (final Object prop : fieldGroup.getUnboundPropertyIds()) {
//            final String aufgabe = messages.getString("aufgabe");
//            if (prop.equals(aufgabe))
//                formUnterlayout.add(fieldGroup.buildAndBind(prop));
//        }
//        for (final Object propertyId : fieldGroup.getBoundPropertyIds()) {
//            fieldGroup.getField(propertyId).setRequired(true);
//        }
//    }


}
