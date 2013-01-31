package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.StringToNumberConverter;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.EditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjektFieldGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;

import static org.rapidpm.Constants.COMMIT_EXCEPTION_MESSAGE;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 11.01.13
 * Time: 13:35
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ExternalDailyRateEditableLayout extends EditableLayout {

    public static final Logger logger = Logger.getLogger(ExternalDailyRateEditableLayout.class);
    private ProjektFieldGroup fieldGroup;
    private TextField externalDailyRateField;
    private PlannedProject currentProject;

    public ExternalDailyRateEditableLayout(final Screen screen, final Panel screenPanel) {
        super(screen, screenPanel);
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final PlannedProject plannedProjectFromSessionAttribute = screen.getUi()
                .getSession().getAttribute(PlannedProject.class);
        currentProject = daoFactory.getPlannedProjectDAO().findByID(plannedProjectFromSessionAttribute.getId());
        fieldGroup = new ProjektFieldGroup(currentProject);
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try{
                    fieldGroup.commit();
                    final BeanItem<PlannedProject> beanItem = (BeanItem)fieldGroup.getItemDataSource();
                    final PlannedProject editedPlannedProject = beanItem.getBean();
                    final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
                    daoFactory.saveOrUpdateTX(editedPlannedProject);
                    final MainUI ui = screen.getUi();
                    ui.setWorkingArea(new StundensaetzeScreen(ui));
                }catch (final NullPointerException e){
                    logger.info(COMMIT_EXCEPTION_MESSAGE);
                }catch(final Exception e){
                    logger.warn("Exception", e);
                }

            }
        });
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                fieldGroup.discard();
                externalDailyRateField = (TextField) fieldGroup.getField(PlannedProject.EXTERNALDAILYRATE);
                externalDailyRateField.setReadOnly(true);
                addConverterToField();
                componentsLayout.addComponent(externalDailyRateField);
                externalDailyRateField.setReadOnly(true);
                externalDailyRateField.setConversionError(messages.getString("stdsatz_currencyOnly"));
                buttonLayout.setVisible(false);
            }
        });
        buildForm();
    }


    @Override
    protected void buildForm() {
//        for(final Field field : fieldGroup.getFields()){
//            field.setReadOnly(false);
//            if(field instanceof AbstractSelect){
//                ((ComboBox)field).setNullSelectionAllowed(false);
//                ((ComboBox)field).setTextInputAllowed(false);
//            }
//        }
        externalDailyRateField = (TextField) fieldGroup.getField(PlannedProject.EXTERNALDAILYRATE);
        externalDailyRateField.setReadOnly(true);
        addConverterToField();
        componentsLayout.addComponent(externalDailyRateField);
    }

    public void addConverterToField() {
        externalDailyRateField.setConverter(new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(final Locale locale) {
                return NumberFormat.getCurrencyInstance(locale);
            }
        });
    }

    @Override
    protected void setLayout() {
        componentsLayout = new FormLayout();
    }

    public PlannedProject getCurrentProject() {
        return currentProject;
    }

    public TextField getExternalDailyRateField() {
        return externalDailyRateField;
    }

    public void setExternalDailyRateField(final TextField externalDailyRateField) {
        this.externalDailyRateField = externalDailyRateField;
    }

    public ProjektFieldGroup getFieldGroup() {
        return fieldGroup;
    }

    public void setFieldGroup(ProjektFieldGroup fieldGroup) {
        this.fieldGroup = fieldGroup;
    }
}
