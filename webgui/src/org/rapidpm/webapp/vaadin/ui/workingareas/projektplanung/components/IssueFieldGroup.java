package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.components;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.IssueBase;

/**
 * @deprecated vermutlich nicht umsetzbar, daher
 * Eigenkonstruktion: {@link org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.logic.PlanningDetailsMyFormLayout}
 */
@Deprecated
public class IssueFieldGroup extends FieldGroup {

    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");

    private VerticalLayout mainLayout = new VerticalLayout();
    private FormLayout componentsLayout = new FormLayout();
    private HorizontalLayout buttonLayout = new HorizontalLayout();

    public IssueFieldGroup(IssueBase issue) {
        setItemDataSource(new BeanItem<IssueBase>(issue));

        cancelButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                buttonLayout.setVisible(false);
            }
        });

        buildForm();
    }

    public IssueFieldGroup(Item item) {
        setItemDataSource(item);
        buildForm();
    }

    private void buildForm() {

        for (final Object propertyId : getUnboundPropertyIds()) {
            final String propertyIdString = propertyId.toString();
            final String propertyType = getPropertyType(propertyIdString).getSimpleName().toString();
            System.out.println("propertyId: " + propertyIdString);
            System.out.println("klasse: " + propertyType);
            if (propertyIdString.equals("summary")) {
//                    && !propertyIdString.equals("sumPerMonth")
//                    && !propertyIdString.equals("bruttoPerMonth")
//                    && !propertyIdString.equals("operativeEurosPerHour")
//                    && !propertyIdString.equals("eurosPerHour")
//                    && !propertyIdString.equals("hoursPerYear"))
                componentsLayout.addComponent(buildAndBind("summary", propertyIdString, Field.class));
            }
            if (propertyIdString.equals("knotenblatt")) {
                componentsLayout.addComponent(buildAndBind("summary", propertyIdString, ComboBox.class));
            }
            //System.out.println("PropertyId: " + propertyIdString);
        }

        buttonLayout.setSpacing(true);
        buttonLayout.addComponent(saveButton);
        buttonLayout.addComponent(cancelButton);

        mainLayout.addComponent(componentsLayout);
        mainLayout.addComponent(buttonLayout);
    }

    public VerticalLayout getForm() {
        return mainLayout;
    }


//    public <T extends Field> T buildAndBind(String caption, Object propertyId,
//                                            Class<ComboBox> fieldType) throws BindException {
//        Class<?> type = getPropertyType(propertyId);
//
//        final ComboBox field = new ComboBox("Status", new BeanItemContainer<>(IssueStatus.Status.class, Arrays.asList(IssueStatus.Status.values())));
//        field.setItemIconPropertyId("icon");
//        field.setNullSelectionAllowed(false);
//        //field.select(teil.getStatus());
//
//        //T field = build(caption, type, fieldType);
//        bind(field, propertyId);
//
//
//        return field;
//    }

}
