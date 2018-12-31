package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.descriptionandtestcases;

import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.textelement.TextElement;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.*;

public class DescriptionAndTestCasesFieldGroup {
    private final List<RapidPanel> descriptionRapidPanels = new LinkedList<>();
    private final List<RapidPanel> testcaseRapidPanels = new LinkedList<>();
//    private BeanItem<PlanningUnit> beanItemPlanningUnit;
    private ProjektplanungScreen screen;
    private ResourceBundle messages;
    private final PlanningUnit selectedPlanningUnit;

    public DescriptionAndTestCasesFieldGroup(final ProjektplanungScreen screen, final ResourceBundle messages,
                                             final PlanningUnit selectedPlanningUnit) {
        this.screen = screen;
        this.messages = messages;
        this.selectedPlanningUnit = selectedPlanningUnit;
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final PlanningUnit planningUnit = daoFactory.getPlanningUnitDAO().findByID(selectedPlanningUnit.getId());
//        if(planningUnit == null){
//            beanItemPlanningUnit = new BeanItem<>(selectedPlanningUnit);
//        } else {
//            beanItemPlanningUnit = new BeanItem<>(planningUnit);
//        }
//        setItemDataSource(beanItemPlanningUnit);

        buildForm();
    }

    private void buildForm() {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final List<Benutzer> users = daoFactory.getBenutzerDAO().loadAllEntities();
//        for (final Object propertyId : getUnboundPropertyIds()) {
//            final String spaltenName = propertyId.toString();
//            final PlanningUnit dataSource = beanItemPlanningUnit.getBean();
//            final LinkedList<TextElement> textElements = new LinkedList<>();
//            switch(spaltenName){
//                case(PlanningUnit.DESCRIPTIONS):
//                    fillList(dataSource.getDescriptions(), textElements, descriptionRapidPanels);
//                    break;
//                case(PlanningUnit.TESTCASES):
//                    fillList(dataSource.getTestcases(), textElements, testcaseRapidPanels);
//                    break;
//                default:
//                    break;
//            }
//        }
    }

    private void fillList(final List<TextElement> textElementList, final LinkedList<TextElement>
                          sortedTextElementList, final List<RapidPanel> fieldGroupList) {
        for(final TextElement textElement : textElementList){
            sortedTextElementList.add(textElement);
        }
        Collections.sort(sortedTextElementList);
        for (final TextElement textElement : sortedTextElementList) {
            final RapidPanel framePanel = new RapidPanel();
            final TextElementEditableLayout panel = new TextElementEditableLayout(selectedPlanningUnit,
                    screen, framePanel, textElement);
            framePanel.add(panel);
            fieldGroupList.add(framePanel);
        }
    }

    public List<RapidPanel> getTestcaseRapidPanels() {
        return testcaseRapidPanels;
    }

    public List<RapidPanel> getDescriptionRapidPanels() {
        return descriptionRapidPanels;
    }
}
