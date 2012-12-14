package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.descriptionandtestcases;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.textelement.TextElement;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DescriptionAndTestCasesFieldGroup extends FieldGroup {
    private final List<DescriptionEditableLayout> descriptionEditableRapidPanels = new ArrayList<>();
    private final List<RichTextArea> testcaseTextAreas = new ArrayList<>();
    private BeanItem<PlanningUnit> beanItemPlanningUnit;
    private ResourceBundle messages;

    public DescriptionAndTestCasesFieldGroup(final ResourceBundle messages, final PlanningUnit unmanagedPlanningUnit) {
        this.messages = messages;
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final PlanningUnit planningUnit = daoFactory.getPlanningUnitDAO().findByID(unmanagedPlanningUnit.getId());
        if(planningUnit == null){
            beanItemPlanningUnit = new BeanItem<>(unmanagedPlanningUnit);
        } else {
            beanItemPlanningUnit = new BeanItem<>(planningUnit);
        }
        setItemDataSource(beanItemPlanningUnit);

        buildForm();
    }

    private void buildForm() {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final List<Benutzer> users = daoFactory.getBenutzerDAO().loadAllEntities();
        for (final Object propertyId : getUnboundPropertyIds()) {
            final String spaltenName = propertyId.toString();
            switch(spaltenName){
                case(PlanningUnit.DESCRIPTIONS):
                    final PlanningUnit dataSource = beanItemPlanningUnit.getBean();
                    for(final TextElement description : dataSource.getDescriptions()){
                        final DescriptionEditableLayout panel = new DescriptionEditableLayout(screen, descriptionsPanel, messages,
                                description);
                        descriptionEditableRapidPanels.add(panel);
                    }
                    break;
                case(PlanningUnit.TESTCASES):
//                    final TextField orderNumberField = new TextField(messages.getString("planning_ordernumber"));
//                    bind(orderNumberField, propertyId);
//                    fieldList.add(orderNumberField);
                    break;
                default:
                    break;
            }
        }
    }

    public List<RichTextArea> getTestcaseTextAreas() {
        return testcaseTextAreas;
    }

    public List<DescriptionEditableLayout> getDescriptionEditableRapidPanels() {
        return descriptionEditableRapidPanels;
    }
}
