package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.apache.log4j.Logger;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class AddRowWindow extends Window {
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
    private AddRowWindowBean addRowWindowBean;

    public AddRowWindow(final MainUI ui, final StundensaetzeScreen screen) {
        this.ui = ui;
        messages = screen.getMessagesBundle();
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setPositionX(POSITION_X);
        setPositionY(POSITION_Y);

        addRowWindowBean = EJBFactory.getEjbInstance(AddRowWindowBean.class);
        final DaoFactoryBean baseDaoFactoryBean = addRowWindowBean.getDaoFactoryBean();

        fieldGroup = new RowFieldGroup();

        fillFormLayout();
        addComponent(formLayout);

        horizontalButtonLayout.addComponent(saveButton);
        horizontalButtonLayout.addComponent(cancelButton);

        addComponent(horizontalButtonLayout);

        addListeners(baseDaoFactoryBean, ui, screen);
        doInternationalization();

    }

    private void fillFormLayout() {
        final AbstractTextField nameField = fieldGroup.getNameField();
        formLayout.addComponent(buildFieldWithValue(nameField));
        formLayout.addComponent(new Label("-----------"));
        for (AbstractTextField abstractTextField : fieldGroup.getFieldList()) {
            formLayout.addComponent(buildFieldWithValue(abstractTextField));
        }
    }

    private TextField buildFieldWithValue(final AbstractTextField abstractTextField) {
        final TextField field = (TextField) abstractTextField;
        final String typeNameOfFieldProperty = field.getPropertyDataSource().getType().getSimpleName();
        field.setValue(DefaultValues.valueOf(typeNameOfFieldProperty).getDefaultValue());
        return field;
    }

    private void doInternationalization() {
        this.setCaption(messages.getString("stdsatz_addRessource"));
        saveButton.setCaption(messages.getString("save"));
        cancelButton.setCaption(messages.getString("cancel"));
    }

    private void addListeners(final DaoFactoryBean baseDaoFactoryBean, final MainUI ui,
                              final StundensaetzeScreen screen) {
        saveButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                boolean allFilled = true;
                Iterator<Component> it = AddRowWindow.this.formLayout
                        .getComponentIterator();
                while (it.hasNext()) {
                    final Component component = it.next();
                    if (component instanceof AbstractField) {
                        if (((TextField) component).getValue() == null
                                || ((TextField) component).getValue().equals(""))
                            allFilled = false;
                    }
                }
                if (allFilled) {
                    try {
                        final Table tabelle = screen.getTabelle();
                        fieldGroup.commit();
                        //RessourceGroupBeanItem mit der neuen (transienten) RessourceGroup
                        final BeanItem<RessourceGroup> beanItem = (BeanItem)fieldGroup.getItemDataSource();
                        //Bean aus dem BeanItem
                        final RessourceGroup ressourceGroup = beanItem.getBean();

                        baseDaoFactoryBean.saveOrUpdate(ressourceGroup);
//                        baseDaoFactoryBean.getEntityManager().refresh(ressourceGroup);
//                        final RessourceGroup group = baseDaoFactoryBean.getRessourceGroupDAO().loadRessourceGroupByName
//                                (ressourceGroup
//                                .getName
//                                ());
                        final RessourceGroup group = baseDaoFactoryBean.getRessourceGroupDAO().loadRessourceGroupByName
                                (ressourceGroup.getName());


                        final List<PlanningUnit> planningUnits = baseDaoFactoryBean.getPlanningUnitDAO()
                                .loadAllEntities();

                        for(final PlanningUnit planningUnit : planningUnits){
                            final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                            planningUnitElement.setPlannedDays(0);
                            planningUnitElement.setPlannedHours(0);
                            planningUnitElement.setPlannedMinutes(0);
                            planningUnitElement.setRessourceGroup(group);
                            planningUnit.getPlanningUnitElementList().add(planningUnitElement);
                            baseDaoFactoryBean.saveOrUpdate(planningUnit);
                        }


//                        final int planningUnitCount = baseDaoFactoryBean.getPlanningUnitDAO().loadAllEntities().size();
//                        for(int i = 0; i < planningUnitCount; i++){
//                            PlanningUnitElement planningUnitElement = new PlanningUnitElement();
//                            planningUnitElement.setId(null);
//                            planningUnitElement.setPlannedDays(0);
//                            planningUnitElement.setPlannedHours(0);
//                            planningUnitElement.setPlannedMinutes(0);
//                            planningUnitElement.setRessourceGroup(ressourceGroup);
//                            baseDaoFactoryBean.saveOrUpdate(planningUnitElement);
//                        }
                        screen.generateTableAndCalculate();

                        AddRowWindow.this.close();
                    } catch (CommitException e) {
                        logger.warn(e);
                    }

                } else {
                    final Label lbl = new Label();
                    lbl.setValue(messages.getString("stdsatz_fillInAllFields"));
                    AddRowWindow.this.addComponent(lbl);
                }

            }

        });

        cancelButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                AddRowWindow.this.close();
            }
        });

    }

    public void show() {
        ui.addWindow(this);
    }
}
