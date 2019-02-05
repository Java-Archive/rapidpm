package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import org.apache.log4j.Logger;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.exceptions.NameExistsException;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class AddRowWindow extends Dialog {
    public static final String HEIGHT = "400px";
    public static final String WIDTH = "400px";
    public static final int POSITION_X = 50;
    public static final int POSITION_Y = 100;

    private static final Logger logger = Logger.getLogger(AddRowWindow.class);

    private FormLayout formLayout = new FormLayout();
    private HorizontalLayout horizontalButtonLayout = new HorizontalLayout();
    private Button saveButton = new Button();
    private Button cancelButton = new Button();
    private RowFieldGroup fieldGroup;
    private ResourceBundle messages;
//    private AddRowWindowBean addRowWindowBean;

    public AddRowWindow() {
        messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
        setHeight(HEIGHT);
        setWidth(WIDTH);
//        setPositionX(POSITION_X);
//        setPositionY(POSITION_Y);

        fieldGroup = new RowFieldGroup();

        fillFormLayout();
        add(formLayout);

        horizontalButtonLayout.add(saveButton);
        horizontalButtonLayout.add(cancelButton);

        add(horizontalButtonLayout);

        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        addListeners(daoFactory);
        doInternationalization();

    }

    private void fillFormLayout() {
        for (TextField textField : fieldGroup.getTextFields()) {
            textField.setValue(fieldGroup.getDefaultValueByField(textField));
            formLayout.addFormItem(textField, fieldGroup.getLabelByField(textField));
        }
    }


    private void doInternationalization() {
//        this.setText(messages.getString("stdsatz_addRessource"));
        saveButton.setText(messages.getString("save"));
        cancelButton.setText(messages.getString("cancel"));
    }

    private void addListeners(final DaoFactory baseDaoFactoryBean) {
        saveButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            boolean allFilled = true;
            final Stream<Component> stream = AddRowWindow.this.formLayout.getChildren();
            boolean anyFieldEmpty = stream.anyMatch(component -> (component instanceof HasValue) && (((HasValue) component).getValue().equals("")));
            allFilled = !anyFieldEmpty;
            if (allFilled) {
                try {
                    final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
                    final List<String> ressourceGroupNames = new ArrayList<>();
                    final List<RessourceGroup> ressourceGroups = daoFactory.getRessourceGroupDAO()
                            .loadAllEntities();
                    for(final RessourceGroup ressourceGroup : ressourceGroups){
                        daoFactory.getEntityManager().refresh(ressourceGroup);
                        ressourceGroupNames.add(ressourceGroup.getName());
                    }
                    fieldGroup.commit();
                    final RessourceGroup ressourceGroup = fieldGroup.getBean();

                    final String ressourceGroupName = ressourceGroup.getName();
                    if(ressourceGroupNames.contains(ressourceGroupName)){
                        throw new NameExistsException();
                    }
                    if(ressourceGroupName.matches(Constants.EMPTY_OR_SPACES_ONLY_PATTERN)){
                        throw new InvalidNameException();
                    }

                    final RessourceGroup persistedRessourceGroup = baseDaoFactoryBean.saveOrUpdateTX
                            (ressourceGroup);
                    final List<PlanningUnit> planningUnits = baseDaoFactoryBean.getPlanningUnitDAO()
                            .loadAllEntities();

                    for(final PlanningUnit planningUnit : planningUnits){
                        final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                        planningUnitElement.setPlannedMinutes(0);
                        planningUnitElement.setRessourceGroup(persistedRessourceGroup);
                        final PlanningUnitElement persistedPlanningUnitElement = baseDaoFactoryBean.saveOrUpdateTX
                                (planningUnitElement);
                        if(planningUnit.getPlanningUnitElementList() == null){
                            planningUnit.setPlanningUnitElementList(new ArrayList<PlanningUnitElement>());
                        }
                        planningUnit.getPlanningUnitElementList().add(persistedPlanningUnitElement);
                        baseDaoFactoryBean.saveOrUpdateTX(planningUnit);
                    }
//                        screen.refreshGridAndRelatedContent();

                    AddRowWindow.this.close();
                } catch (final NameExistsException e) {
                    Notification.show(messages.getString("stdsatz_nameexists"));
                } catch (InvalidNameException e) {
                    Notification.show(messages.getString("stdsatz_invalidname"));
                }
            } else {
                final Label lbl = new Label();
                lbl.setText(messages.getString("stdsatz_fillInAllFields"));
                AddRowWindow.this.add(lbl);
            }
        });

        cancelButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> AddRowWindow.this.close());

    }

    public void show() {
        open();
    }
}
