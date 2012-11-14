package org.rapidpm.webapp.vaadin.ui.workingareas.controlling;

import com.vaadin.ui.*;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.components.highchatrsjs.JsHighChart;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.componnents.ControllingProjectTab;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.componnents.ControllingRessourceGroupsTab;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 05.11.12
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
public class ControllingScreen extends Screen {

    private final ControllingScreenBean controllingScreenBean;
    private final HorizontalLayout mainLayout= new HorizontalLayout();
    private final HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();

    private final Tree projectStructureTree;

    public ControllingScreen(final MainUI ui) {
        super(ui);

        controllingScreenBean = EJBFactory.getEjbInstance(ControllingScreenBean.class);


        JsHighChart chart = new JsHighChart();
        chart.setId("myJSComponent");

        TabSheet tabsheet = new TabSheet();
        tabsheet.setSizeFull();
        tabsheet.addTab(new ControllingProjectTab(),
                "Dauer/Kosten");
        tabsheet.addTab(new ControllingRessourceGroupsTab(),
                "Ressourcengruppen");


        projectStructureTree = new Tree("Projectstrutcture");
        splitPanel.setSizeFull();
        splitPanel.setSplitPosition(40, Unit.PERCENTAGE);

        splitPanel.setFirstComponent(projectStructureTree);
        splitPanel.setSecondComponent(tabsheet);
        splitPanel.setHeight(String.valueOf(ui.getHeight()));

        setComponents();
    }

    @Override
    public void setComponents() {
        addComponent(splitPanel);
    }

    @Override
    public void doInternationalization() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
