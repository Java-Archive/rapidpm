package org.rapidpm.webapp.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.MenuBar;
import org.rapidpm.webapp.vaadin.ui.workingareas.anfragenmanagement.AnfragenmanagementWorkingArea;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.CostsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.distribution.VertriebScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.StammdatenWorkingArea;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;

import java.util.Locale;
import java.util.ResourceBundle;

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
        messages = ResourceBundle.getBundle("MessagesBundle", locale);
        final MenuBar.MenuItem stammdatenMenu = menuBar.addItem(messages.getString("masterdata"), null);
        stammdatenMenu.addItem(messages.getString("users"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new StammdatenWorkingArea());
            }
        });

        stammdatenMenu.addItem(messages.getString("hourlyrates"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new StundensaetzeScreen(MainRoot.this));
            }
        });

        menuBar.addItem(messages.getString("requestmanagement"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new AnfragenmanagementWorkingArea());
            }
        });
        final MenuBar.MenuItem projektmanagement = menuBar.addItem(messages.getString("projectmanagement"), null,
                null);


        projektmanagement.addItem(messages.getString("projectplanning"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new ProjektplanungScreen(messages, planningUnitsBean,ressourceGroupsBean));
            }
        });


        projektmanagement.addItem(messages.getString("projectinit"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new AufwandProjInitScreen(MainRoot.this));
            }
        });

        projektmanagement.addItem(messages.getString("costs"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new CostsScreen(MainRoot.this));
            }
        });


        projektmanagement.addItem(messages.getString("distribution"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new VertriebScreen(MainRoot.this));
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

    public Locale getLocale(){
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public ResourceBundle getResourceBundle(){
        return messages;
    }

    public void setResourceBundle(ResourceBundle bundle) {
        this.messages = bundle;
    }
}
