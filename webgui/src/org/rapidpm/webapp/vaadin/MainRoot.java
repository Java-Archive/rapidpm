package org.rapidpm.webapp.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.MenuBar;
import org.rapidpm.webapp.vaadin.ui.workingareas.anfragenmanagement.AnfragenmanagementWorkingArea;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ProjektplanungWorkingArea2;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.CalculatorScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.CostsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.distribution.VertriebScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnitsContainer;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 26.04.12
 * Time: 12:41
 */
@Theme("rapidpm")
//@JavaScript({   "http://localhost:8080/rapidpm/javascript/highcharts/highcharts.js",
//                "http://localhost:8080/rapidpm/javascript/jquery/jquery-1.4.4.min.js"})
public class MainRoot extends BaseRoot {

    private PlanningUnitsContainer planningUnitsContainer = new PlanningUnitsContainer();

    public MainRoot() {
        super("RapidPM application");
    }

    @Override
    protected void initMenuBar(final MenuBar menuBar) {

        final MenuBar.MenuItem reportings = menuBar.addItem("Analysen", null, null);
        final MenuBar.MenuItem rp_difference = reportings.addItem("Wortraumanalyse", null, null);
        rp_difference.addItem("Difference-Report", null, null);
        rp_difference.addItem("Termmatrix", null, null);

        menuBar.addItem("Anfragenmanagement", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new AnfragenmanagementWorkingArea());
            }
        });

        menuBar.addItem("Projektplanung", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new ProjektplanungWorkingArea2(planningUnitsContainer));
            }
        });


        final MenuBar.MenuItem view = menuBar.addItem("View", null, null);

        view.addItem("Aufwand - Projektinitialisierung", null, new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new AufwandProjInitScreen(planningUnitsContainer));
            }
        });

        view.addItem("Kosten", null, new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new CostsScreen());
            }
        });


        view.addItem("Vertrieb", null, new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new VertriebScreen());
            }
        });


        view.addItem("Stundensatz-Kalkulator", null, new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new CalculatorScreen(MainRoot.this));
            }
        });
    }

    public PlanningUnitsContainer getPlanningUnitsContainer() {
        return planningUnitsContainer;
    }
}
