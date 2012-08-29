package org.rapidpm.webapp.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.MenuBar;
import org.rapidpm.webapp.vaadin.ui.workingareas.anfragenmanagement.AnfragenmanagementWorkingArea;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektinitialisierung.ProjektinitialisierungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektplanung.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.transience.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.kosten.CostsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.vertrieb.VertriebScreen;
import org.rapidpm.transience.prj.projectmanagement.planning.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.BenutzerWorkingArea;

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

    private RessourceGroupsBean ressourceGroupsBean = new RessourceGroupsBean();
    private ProjektBean planningUnitsBean = new ProjektBean(ressourceGroupsBean);

    public MainRoot() {
        super("RapidPM application");
    }

    @Override
    protected void initMenuBar(final MenuBar menuBar) {

        final MenuBar.MenuItem stammdatenMenu = menuBar.addItem("Stammdaten", null);
        stammdatenMenu.addItem("Benutzer", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new BenutzerWorkingArea());
            }
        });

        stammdatenMenu.addItem("Stundensätze", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new StundensaetzeScreen(MainRoot.this));
            }
        });

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
        final MenuBar.MenuItem projektmanagement = menuBar.addItem("Projektmanagement", null, null);


        projektmanagement.addItem("Projektplanung", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new ProjektplanungScreen(planningUnitsBean));
            }
        });


        projektmanagement.addItem("Projektinitialisierung", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new ProjektinitialisierungScreen(MainRoot.this));
            }
        });

        projektmanagement.addItem("Kosten", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new CostsScreen());
            }
        });


        projektmanagement.addItem("Vertrieb", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new VertriebScreen());
            }
        });
    }


    public ProjektBean getPlanningUnitsBean() {
        return planningUnitsBean;
    }

    public RessourceGroupsBean getRessourceGroupsBean() {
        return ressourceGroupsBean;
    }

    public void setRessourceGroupsBean(RessourceGroupsBean ressourceGroupsBean) {
        this.ressourceGroupsBean = ressourceGroupsBean;
    }

    public void setPlanningUnitsBean(ProjektBean planningUnitsBean) {
        this.planningUnitsBean = planningUnitsBean;
    }
}
