package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.descriptionandtestcases;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.textelement.TextElement;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.*;

public class DescriptionAndTestCasesFieldGroup extends FieldGroup {
    private final List<RapidPanel> descriptionEditableRapidPanels = new LinkedList<>();
    private final List<RichTextArea> testcaseTextAreas = new ArrayList<>();
    private BeanItem<PlanningUnit> beanItemPlanningUnit;
    private ProjektplanungScreen screen;
    private ResourceBundle messages;

    public DescriptionAndTestCasesFieldGroup(final ProjektplanungScreen screen, final ResourceBundle messages,
                                             final PlanningUnit unmanagedPlanningUnit) {
        this.screen = screen;
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
                    final LinkedList<TextElement> textElements = new LinkedList<>();
                    for(final TextElement description : dataSource.getDescriptions()){
                        textElements.add(description);
                    }
                    Collections.sort(textElements);
                    for (TextElement description : textElements) {
                        final RapidPanel framePanel = new RapidPanel();
                        final DescriptionEditableLayout panel = new DescriptionEditableLayout(screen, framePanel,
                                messages, description);
                        framePanel.addComponent(panel);
                        descriptionEditableRapidPanels.add(framePanel);
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

    public List<RapidPanel> getDescriptionEditableRapidPanels() {
        return descriptionEditableRapidPanels;
    }
}
