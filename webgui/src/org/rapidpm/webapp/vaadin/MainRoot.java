package org.rapidpm.webapp.vaadin;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Theme;
import com.vaadin.ui.MenuBar;
import org.rapidpm.webapp.vaadin.ui.workingareas.anfragenmanagement.AnfragenmanagementWorkingArea;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ProjektplanungWorkingArea;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.calculator.CalculatorScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.costs.CostsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.distribution.VertriebScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ebbinghaus.projinit.AufwandProjInitScreen;

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
                setWorkingArea(new ProjektplanungWorkingArea());
            }
        });


        final MenuBar.MenuItem view = menuBar.addItem("View", null, null);

        view.addItem("Aufwand - Projektinitialisierung", null, new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new AufwandProjInitScreen());
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

}
