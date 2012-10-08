package org.rapidpm.webapp.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.MenuBar;
import org.rapidpm.webapp.vaadin.ui.workingareas.anfragenmanagement.AnfragenmanagementWorkingArea;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.CostsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.distribution.DistributionScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.offer.OfferScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.BenutzerScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;

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
public class MainUI extends BaseUI {

    @Override
    protected void initMenuBar(final MenuBar menuBar) {

        messages = ResourceBundle.getBundle("MessagesBundle", locale);
        final MenuBar.MenuItem stammdatenMenu = menuBar.addItem(messages.getString("masterdata"), null);
        stammdatenMenu.addItem(messages.getString("users"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new BenutzerScreen(MainUI.this));
            }
        });

        stammdatenMenu.addItem(messages.getString("hourlyrates"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new StundensaetzeScreen(MainUI.this));
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
                setWorkingArea(new ProjektplanungScreen(MainUI.this));
            }
        });


        projektmanagement.addItem(messages.getString("projectinit"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new AufwandProjInitScreen(MainUI.this));
            }
        });

        projektmanagement.addItem(messages.getString("costs"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new CostsScreen(MainUI.this));
            }
        });


        projektmanagement.addItem(messages.getString("distribution"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new DistributionScreen(MainUI.this));
            }
        });

        projektmanagement.addItem(messages.getString("offer"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new OfferScreen(MainUI.this));
            }
        }).setEnabled(false);

        projektmanagement.addSeparator();

        projektmanagement.addItem(messages.getString("administrateprojects"), new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                setWorkingArea(new ProjectAdministrationScreen(MainUI.this));
            }
        });

        setWorkingArea(new ProjektplanungScreen(this));

    }

    public Locale getLocale(){
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
