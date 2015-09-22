package org.rapidpm.webapp.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import org.rapidpm.webapp.vaadin.ui.windows.DisclaimerWindow;
import org.rapidpm.webapp.vaadin.ui.windows.ImpressumWindow;
import org.rapidpm.webapp.vaadin.ui.windows.KontaktWindow;
import org.rapidpm.webapp.vaadin.ui.windows.SupportWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.CostsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.distribution.VertriebScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.offer.OfferScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.BenutzerScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;

import javax.servlet.annotation.WebServlet;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 26.04.12
 * Time: 12:41
 */
@SuppressWarnings("serial")
@Theme("valo")
public class MainUI extends BaseUI {

  @Override
  protected void initMenuBar(final MenuBar menuBar) {

    messages = ResourceBundle.getBundle("MessagesBundle", locale);
    final MenuBar.MenuItem stammdatenMenu = menuBar.addItem(messages.getString("masterdata"), null);
    stammdatenMenu.addItem(messages.getString("users"), menuItem -> setWorkingArea(new BenutzerScreen(MainUI.this)));
    stammdatenMenu.addItem(messages.getString("hourlyrates"), menuItem -> setWorkingArea(new StundensaetzeScreen(MainUI.this)));


    final MenuBar.MenuItem projektmanagement = menuBar.addItem(messages.getString("projectmanagement"), null,
        null);

    projektmanagement.addItem(messages.getString("projectplanning"), menuItem -> setWorkingArea(new ProjektplanungScreen(MainUI.this)));
    projektmanagement.addItem(messages.getString("projectinit"), menuItem -> setWorkingArea(new AufwandProjInitScreen(MainUI.this)));
    projektmanagement.addItem(messages.getString("costs"), menuItem -> setWorkingArea(new CostsScreen(MainUI.this)));
    projektmanagement.addItem(messages.getString("distribution"), menuItem -> setWorkingArea(new VertriebScreen(MainUI.this))).setEnabled(false);
    projektmanagement.addItem(messages.getString("offer"), menuItem -> setWorkingArea(new OfferScreen(MainUI.this))).setEnabled(false);
    projektmanagement.addSeparator();
    projektmanagement.addItem(messages.getString("administrateprojects"), selectedItem -> setWorkingArea(new ProjectAdministrationScreen(MainUI.this)));

    final MenuBar.MenuItem help = menuBar.addItem(messages.getString("?"), null,
        null);

    help.addItem(messages.getString("contact"), menuItem -> UI.getCurrent().addWindow(new KontaktWindow()));
    help.addItem(messages.getString("support"), menuItem -> UI.getCurrent().addWindow(new SupportWindow()));
    help.addItem(messages.getString("impressum"), menuItem -> UI.getCurrent().addWindow(new ImpressumWindow()));
    help.addItem(messages.getString("disclaimer"), menuItem -> UI.getCurrent().addWindow(new DisclaimerWindow()));
    help.addItem(messages.getString("sitemap"), menuItem -> UI.getCurrent().addWindow(new KontaktWindow()));
    help.addItem(messages.getString("logout"), menuItem -> {
      getSession().close();
      getPage().setLocation("/");
    });

    setWorkingArea(new ProjektplanungScreen(this));

  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(final Locale locale) {
    this.locale = locale;
  }

}
