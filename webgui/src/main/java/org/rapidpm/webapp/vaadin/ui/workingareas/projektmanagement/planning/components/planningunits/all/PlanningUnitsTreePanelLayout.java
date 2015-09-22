package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.ConfirmDialog;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 25.08.12
 * Time: 18:50
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */

public class PlanningUnitsTreePanelLayout extends HorizontalLayout implements Internationalizationable {

  private static final Logger logger = Logger.getLogger(PlanningUnitsTreePanelLayout.class);
  private VerticalLayout leftLayout = new VerticalLayout();

  private HorizontalLayout buttonLayout = new HorizontalLayout();
  private PlannedProject projekt;

  private Button addButton;
  private Button deleteButton;
  private Button renameButton = new Button();
  private ResourceBundle messages;
  private DaoFactory daoFactory;

  private ProjektplanungScreen screen;

  public PlanningUnitsTreePanelLayout(final PlannedProject projekt, final ProjektplanungScreen screen) {
    this.screen = screen;
    this.projekt = projekt;
    daoFactory = DaoFactorySingleton.getInstance();

    messages = screen.getMessagesBundle();
    createDeleteButton();
    createAddButton();
    createRenameButton();
    buildForm();
    doInternationalization();
  }

  private void createDeleteButton() {
    deleteButton = screen.getDeleteButton();
    deleteButton.addClickListener(new Button.ClickListener() {
      @Override
      public void buttonClick(Button.ClickEvent event) {
        try {
          final PlanningUnit planningUnit = (PlanningUnit) screen.getPlanningUnitsTree().getValue();
          final PlanningUnit persistedFullPlanningUnit = daoFactory.getPlanningUnitDAO().findByID(planningUnit.getId(), true);
          if (persistedFullPlanningUnit == null) {
            throw new PlatzhalterException();
          }
          String message;
          if (persistedFullPlanningUnit.getKindPlanningUnits() != null && !persistedFullPlanningUnit.getKindPlanningUnits().isEmpty()) {
            message = messages.getString("planning_confirmdelete");
          } else {
            message = messages.getString("issuetracking_issue_deletequestion");
          }
          ConfirmDialog confirmDialog = new ConfirmDialog(message, screen) {
            @Override
            public void doThisOnOK() {
              daoFactory.getPlanningUnitDAO().deleteByEntity(persistedFullPlanningUnit, true);
              projekt = daoFactory.getPlannedProjectDAO().findByID(projekt.getId(), true);
              final MainUI ui = screen.getUi();
              ui.setWorkingArea(new ProjektplanungScreen(ui));
            }

            @Override
            public void doThisOnCancel() {
              // do nothing else than closing the window. Screen will be reloaded anyways.
            }
          };
          confirmDialog.show();
        } catch (final PlatzhalterException e) {
          Notification.show(messages.getString("planning_placeholder_delete"));
        } catch (final Exception e) {
          e.printStackTrace();
          Notification.show(messages.getString("planning_nodelete"));
        }
      }
    });
  }

  private void createAddButton() {
    addButton = screen.getAddButton();
    if (addButton.getListeners(Button.ClickEvent.class).size() <= 0) {
      addButton.addClickListener(new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent event) {
          final AddWindow window = new AddWindow(screen.getUi(), screen);
          window.show();
        }
      });
    }
  }

  private void createRenameButton() {
    renameButton.addClickListener(new Button.ClickListener() {
      @Override
      public void buttonClick(Button.ClickEvent event) {
        screen.getUi().addWindow(new RenamePlanningUnitWindow(screen));
      }
    });
  }

  protected void buildForm() {
    buttonLayout.addComponents(addButton, deleteButton, renameButton);
    leftLayout.addComponent(buttonLayout);
    leftLayout.addComponent(screen.getPlanningUnitsTree());
    addComponent(leftLayout);
  }

  @Override
  public void doInternationalization() {
    renameButton.setCaption(messages.getString("rename"));
  }

  public Button getDeleteButton() {
    return deleteButton;
  }

  public Button getRenameButton() {
    return renameButton;
  }

  public Button getAddButton() {
    return addButton;
  }
}
