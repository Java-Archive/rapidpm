package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
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
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.exceptions.NameExistsException;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class AddRowWindow extends RapidWindow {
    public static final String HEIGHT = "400px";
    public static final String WIDTH = "400px";
    public static final int POSITION_X = 50;
    public static final int POSITION_Y = 100;

    private static final Logger logger = Logger.getLogger(AddRowWindow.class);

    private MainUI ui;

    private FormLayout formLayout = new FormLayout();
    private HorizontalLayout horizontalButtonLayout = new HorizontalLayout();
    private Button saveButton = new Button();
    private Button cancelButton = new Button();
    private RowFieldGroup fieldGroup;
    private ResourceBundle messages;
//    private AddRowWindowBean addRowWindowBean;

    public AddRowWindow(final MainUI ui, final StundensaetzeScreen screen) {
        this.ui = ui;
        messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
//        setHeight(HEIGHT);
//        setWidth(WIDTH);
//        setPositionX(POSITION_X);
//        setPositionY(POSITION_Y);

//        addRowWindowBean = EJBFactory.getEjbInstance(AddRowWindowBean.class);
//        final DaoFactoryBean baseDaoFactoryBean = addRowWindowBean.getDaoFactoryBean();

        fieldGroup = new RowFieldGroup();

        fillFormLayout();
        add(formLayout);

        horizontalButtonLayout.add(saveButton);
        horizontalButtonLayout.add(cancelButton);

        add(horizontalButtonLayout);

        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
//        addListeners(daoFactory, ui, screen);
        doInternationalization();

    }

    private void fillFormLayout() {
//        final AbstractTextField nameField = fieldGroup.getNameField();
//        formLayout.add(buildFieldWithValue(nameField));
//        formLayout.add(new Label("-----------"));
//        for (AbstractTextField abstractTextField : fieldGroup.getFieldList()) {
//            formLayout.add(buildFieldWithValue(abstractTextField));
//        }
    }

//    private TextField buildFieldWithValue(final AbstractTextField abstractTextField) {
//        final TextField field = (TextField) abstractTextField;
//        final String typeNameOfFieldProperty = field.getPropertyDataSource().getType().getSimpleName();
//        field.setValue(DefaultValues.valueOf(typeNameOfFieldProperty).getDefaultValue());
//        return field;
//    }

    private void doInternationalization() {
//        this.setText(messages.getString("stdsatz_addRessource"));
        saveButton.setText(messages.getString("save"));
        cancelButton.setText(messages.getString("cancel"));
    }

//    private void addListeners(final DaoFactory baseDaoFactoryBean, final MainUI ui,
//                              final StundensaetzeScreen screen) {
//        saveButton.addClickListener(new ClickListener() {
//
//            @Override
//            public void buttonClick(ClickEvent event) {
//                boolean allFilled = true;
//                final Iterator<Component> it = AddRowWindow.this.formLayout
//                        .getComponentIterator();
//                while (it.hasNext()) {
//                    final Component component = it.next();
//                    if (component instanceof AbstractField) {
//                        if (((TextField) component).getValue() == null
//                                || ((TextField) component).getValue().equals(""))
//                            allFilled = false;
//                    }
//                }
//                if (allFilled) {
//                    try {
//                        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
//                        final List<String> ressourceGroupNames = new ArrayList<>();
//                        final List<RessourceGroup> ressourceGroups = daoFactory.getRessourceGroupDAO()
//                                .loadAllEntities();
//                        for(final RessourceGroup ressourceGroup : ressourceGroups){
//                            daoFactory.getEntityManager().refresh(ressourceGroup);
//                            ressourceGroupNames.add(ressourceGroup.getName());
//                        }
//                        //final Table tabelle = screen.getTabelle();
//                        fieldGroup.commit();
//                        //RessourceGroupBeanItem mit der neuen (transienten) RessourceGroup
//                        final BeanItem<RessourceGroup> beanItem = (BeanItem)fieldGroup.getItemDataSource();
//                        //Bean aus dem BeanItem
//                        final RessourceGroup ressourceGroup = beanItem.getBean();
//
//                        final String ressourceGroupName = ressourceGroup.getName();
//                        if(ressourceGroupNames.contains(ressourceGroupName)){
//                            throw new NameExistsException();
//                        }
//                        if(ressourceGroupName.matches(Constants.EMPTY_OR_SPACES_ONLY_PATTERN)){
//                            throw new InvalidNameException();
//                        }
//
//                        final RessourceGroup persistedRessourceGroup = baseDaoFactoryBean.saveOrUpdateTX
//                                (ressourceGroup);
//                        final List<PlanningUnit> planningUnits = baseDaoFactoryBean.getPlanningUnitDAO()
//                                .loadAllEntities();
//
//                        for(final PlanningUnit planningUnit : planningUnits){
//                            final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
//                            planningUnitElement.setPlannedMinutes(0);
//                            planningUnitElement.setRessourceGroup(persistedRessourceGroup);
//                            final PlanningUnitElement persistedPlanningUnitElement = baseDaoFactoryBean.saveOrUpdateTX
//                                    (planningUnitElement);
//                            if(planningUnit.getPlanningUnitElementList() == null){
//                                planningUnit.setPlanningUnitElementList(new ArrayList<PlanningUnitElement>());
//                            }
//                            planningUnit.getPlanningUnitElementList().add(persistedPlanningUnitElement);
//                            baseDaoFactoryBean.saveOrUpdateTX(planningUnit);
//                        }
//                        screen.generateTableAndCalculate();
//
//                        AddRowWindow.this.close();
//                    }catch(final FieldGroup.CommitException e){
//                          Notification.show(messages.getString("stdsatz_addfail"));
//                    } catch (final NameExistsException e) {
//                        Notification.show(messages.getString("stdsatz_nameexists"));
//                    } catch (InvalidNameException e) {
//                        Notification.show(messages.getString("stdsatz_invalidname"));
//                    }
//                } else {
//                    final Label lbl = new Label();
//                    lbl.setValue(messages.getString("stdsatz_fillInAllFields"));
//                    AddRowWindow.this.add(lbl);
//                }
//            }
//
//        });
//
//        cancelButton.addClickListener(new ClickListener() {
//
//            @Override
//            public void buttonClick(ClickEvent event) {
//                AddRowWindow.this.close();
//            }
//        });
//
//    }

    public void show() {
//        ui.addWindow(this);
    }
}
