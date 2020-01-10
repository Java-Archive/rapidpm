package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.datenmodell.KnotenBlattEnum;

import java.util.ResourceBundle;
import java.util.regex.Pattern;

//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;

public class SaveButtonClickListener {
  private static final Pattern               SPLITT_PATTERN    = Pattern.compile(":");
  //    private FieldGroup fieldGroup;
  private              AufwandProjInitScreen screen;
  private              KnotenBlattEnum       knotenBlattEnum;
  private              Object                itemId;
  private              PlanningUnit          foundPlanningUnit = null;
  private              ResourceBundle        messages;
//    private SaveButtonClickListenerBean bean;
//    private DaoFactoryBean baseDaoFactoryBean;

  public SaveButtonClickListener(final ResourceBundle bundle,
                                 final AufwandProjInitScreen screen,
                                 final KnotenBlattEnum knotenBlattEnum,
                                 final Object itemId) {
    this.messages = bundle;
//        this.fieldGroup = fieldGroup;
    this.screen          = screen;
    this.knotenBlattEnum = knotenBlattEnum;
    this.itemId          = itemId;

//        bean = EJBFactory.getEjbInstance(SaveButtonClickListenerBean.class);
//        baseDaoFactoryBean = bean.getDaoFactoryBean();
    final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
  }

//    @Override
//    public void buttonClick(ClickEvent event) {
//        try {
//            final Item item = screen.getDataSource().getItem(itemId);
//            final String planningUnitNameBeforeCommit = item.getItemProperty(messages.getString("aufgabe")).getValue().toString();
//            final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
//            final PlanningUnitDAO planningUnitDAO = daoFactory.getPlanningUnitDAO();
//            foundPlanningUnit = planningUnitDAO.loadPlanningUnitByName(planningUnitNameBeforeCommit);
//            daoFactory.getEntityManager().refresh(foundPlanningUnit);
//
//            fieldGroup.commit();
//            final String planningUnitNameAfterCommit = item.getItemProperty(messages.getString("aufgabe")).getValue().toString();
//            if (knotenBlattEnum.equals(KnotenBlattEnum.KNOTEN)) {
//                foundPlanningUnit.setPlanningUnitName(planningUnitNameAfterCommit);
//            } else {
//                foundPlanningUnit.setPlanningUnitName(planningUnitNameAfterCommit);
//                for (final PlanningUnitElement planningUnitElement : foundPlanningUnit.getPlanningUnitElementList()) {
//                    final String planningUnitElementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
//                    final Property<?> planningUnitElementCellContent = item.getItemProperty(planningUnitElementRessourceGroupName);
//                    final String daysHoursMinutesString = planningUnitElementCellContent.getValue().toString();
//                    final String[] daysHoursMinutes = SPLITT_PATTERN.split(daysHoursMinutesString);
//                    final int plannedDays = Integer.parseInt(daysHoursMinutes[0]);
//                    final int plannedHours = Integer.parseInt(daysHoursMinutes[1]);
//                    final int plannedMinutes = Integer.parseInt(daysHoursMinutes[2]);
//                    final VaadinSession session = screen.getUi().getSession();
//                    final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
//                    planningUnitElement.setPlannedMinutes((plannedDays * currentProject.getHoursPerWorkingDay() *
//                            Constants.MINS_HOUR) + (plannedHours * Constants.MINS_HOUR) + (plannedMinutes));
//                    daoFactory.saveOrUpdateTX(planningUnitElement);
//                }
//            }
//            daoFactory.saveOrUpdateTX(foundPlanningUnit);
//            final MainUI ui = screen.getUi();
//            ui.setWorkingArea(new AufwandProjInitScreen(ui));
//        }catch (CommitException e){
//            logger.info(COMMIT_EXCEPTION_MESSAGE);
//        }catch(Exception e){
//            logger.warn("Exception", e);
//        }
//    }

}
