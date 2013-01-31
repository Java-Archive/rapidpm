package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 11.01.13
 * Time: 16:01
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlannedProjectFieldGroup extends FieldGroup {

    private final ResourceBundle messages;
    private TextField externalDailyRateField;

    public PlannedProjectFieldGroup(final ResourceBundle messages, final PlannedProject unmanagedPlannedProject) {
        this.messages = messages;
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final PlannedProject plannedProject = daoFactory.getPlannedProjectDAO().findByID(unmanagedPlannedProject.getId());
        if(plannedProject == null){
            setItemDataSource(new BeanItem<>(unmanagedPlannedProject));
        } else {
            setItemDataSource(new BeanItem<>(plannedProject));
        }

        buildForm();
    }

    private void buildForm() {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final List<Benutzer> users = daoFactory.getBenutzerDAO().loadAllEntities();
        for (final Object propertyId : getUnboundPropertyIds()) {
            final String spaltenName = propertyId.toString();
            switch(spaltenName){
                case(PlannedProject.EXTERNALDAILYRATE):
                    final TextField field = new TextField();
                    bind(field, propertyId);
                    break;
                default:
                    break;
            }
        }
    }

    public TextField getExternalDailyRateField() {
        return externalDailyRateField;
    }
}
