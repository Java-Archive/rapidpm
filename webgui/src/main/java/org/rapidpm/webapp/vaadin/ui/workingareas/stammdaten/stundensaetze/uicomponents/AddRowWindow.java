package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.apache.log4j.Logger;
import org.rapidpm.Constants;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.exceptions.NameExistsException;

import javax.naming.InvalidNameException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class AddRowWindow extends RapidWindow {
    public static final String HEIGHT = "600px";
    public static final String WIDTH = "500px";
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
        messages = screen.getMessagesBundle();
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setPositionX(POSITION_X);
        setPositionY(POSITION_Y);

//        addRowWindowBean = EJBFactory.getEjbInstance(AddRowWindowBean.class);
//        final DaoFactoryBean baseDaoFactoryBean = addRowWindowBean.getDaoFactoryBean();

        fieldGroup = new RowFieldGroup();

        fillFormLayout();
        addComponent(formLayout);

        horizontalButtonLayout.addComponent(saveButton);
        horizontalButtonLayout.addComponent(cancelButton);

        addComponent(horizontalButtonLayout);

        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
        addListeners(daoFactory, ui, screen);
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

    private void addListeners(final DaoFactory baseDaoFactoryBean, final MainUI ui,
                              final StundensaetzeScreen screen) {
        saveButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                boolean allFilled = true;
                final Iterator<Component> it = AddRowWindow.this.formLayout
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
                        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
                        final List<String> ressourceGroupNames = new ArrayList<>();
                        final List<RessourceGroup> ressourceGroups = daoFactory.getRessourceGroupDAO()
                                .findAll();
                        for(final RessourceGroup ressourceGroup : ressourceGroups){
//                            daoFactory.getEntityManager().refresh(ressourceGroup);
                            ressourceGroupNames.add(ressourceGroup.getName());
                        }
                        //final Table tabelle = screen.getTabelle();
                        fieldGroup.commit();
                        //RessourceGroupBeanItem mit der neuen (transienten) RessourceGroup
                        final BeanItem<RessourceGroup> beanItem = (BeanItem)fieldGroup.getItemDataSource();
                        //Bean aus dem BeanItem
                        final RessourceGroup temporaryRessourceGroup = beanItem.getBean();

                        final String ressourceGroupName = temporaryRessourceGroup.getName();
                        if(ressourceGroupNames.contains(ressourceGroupName)){
                            throw new NameExistsException();
                        }
                        if(ressourceGroupName.matches(Constants.EMPTY_OR_SPACES_ONLY_PATTERN)){
                            throw new InvalidNameException();
                        }

                        final List<PlanningUnit> planningUnits = baseDaoFactoryBean.getPlanningUnitDAO()
                                .findAll();
                        final RessourceGroup persistedRessourceGroup = daoFactory.getRessourceGroupDAO().createEntityFull(temporaryRessourceGroup);
                        for(final PlanningUnit planningUnit : planningUnits){
                            final PlanningUnitElement temporaryPlanningUnitElement = new PlanningUnitElement();
                            temporaryPlanningUnitElement.setPlannedMinutes(0);
                            temporaryPlanningUnitElement.setRessourceGroup(persistedRessourceGroup);
                            if(planningUnit.getPlanningUnitElementList() == null){
                                planningUnit.setPlanningUnitElementList(new ArrayList<PlanningUnitElement>());
                            }
                            final PlanningUnitElement persistedPUE = daoFactory.getPlanningUnitElementDAO().createEntityFull(temporaryPlanningUnitElement);
                            daoFactory.getPlanningUnitDAO().addPlanningUnitElementToPlanningUnit(persistedPUE, planningUnit);

                        }
                        screen.generateTableAndCalculate();

                        AddRowWindow.this.close();
                    }catch(final FieldGroup.CommitException e){
                          Notification.show(messages.getString("stdsatz_addfail"));
                    } catch (final NameExistsException e) {
                        Notification.show(messages.getString("stdsatz_nameexists"));
                    } catch (InvalidNameException e) {
                        Notification.show(messages.getString("stdsatz_invalidname"));
                    } catch (MissingNonOptionalPropertyException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (NotYetImplementedException e) {
                        e.printStackTrace();
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
