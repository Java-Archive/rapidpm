package org.rapidpm.webapp.vaadin.ui;

import com.github.appreciated.app.layout.behaviour.AppLayout;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.appmenu.MenuHeaderComponent;
import com.github.appreciated.app.layout.component.appmenu.left.LeftClickableComponent;
import com.github.appreciated.app.layout.component.appmenu.left.LeftNavigationComponent;
import com.github.appreciated.app.layout.component.appmenu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.appmenu.left.builder.LeftSubMenuBuilder;
import com.github.appreciated.app.layout.notification.DefaultNotificationHolder;
import com.github.appreciated.app.layout.notification.component.AppBarNotificationButton;
import com.github.appreciated.app.layout.router.AppLayoutRouterLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.VaadinSession;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.webapp.vaadin.ui.windows.ImpressumWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.legal.ImprintScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.CostsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.distribution.VertriebScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.offer.OfferScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.BenutzerScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.github.appreciated.app.layout.entity.Section.FOOTER;
import static com.github.appreciated.app.layout.entity.Section.HEADER;
import static org.rapidpm.Constants.MESSAGESBUNDLE;

/**
 * The main view contains a button and a template element.
 */

@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainAppLayout extends AppLayoutRouterLayout {
    private DefaultNotificationHolder notifications;

    private static final Logger logger = Logger.getLogger(MainAppLayout.class);

    private static final boolean DEBUG_MODE = false;
    private static final String VERSION = "alpha - for testing only";

    protected Benutzer currentUser;
    protected PlannedProject currentProject;
    protected Locale locale = new Locale("de","DE");
    public ResourceBundle messages;


    @Override
    public AppLayout createAppLayoutInstance() {
        prepare();
        notifications = new DefaultNotificationHolder(newStatus -> {
        });
        MenuHeaderComponent iconElement = new MenuHeaderComponent(null, null, "/frontend/images/rpm.jpg");
        iconElement.getElement().addEventListener("click", event -> UI.getCurrent().navigate(""));
        return AppLayoutBuilder
                .get(Behaviour.LEFT_RESPONSIVE)
                .withTitle("RapidPM 2019 - Vaadin 13 (" + VERSION + ")")
                .withAppBar(AppBarBuilder
                        .get()
                        .add(new AppBarNotificationButton(VaadinIcon.BELL, notifications))
                        .build())
                .withAppMenu(LeftAppMenuBuilder
                        .get()
                        .addToSection(iconElement, HEADER)
                        .add(LeftSubMenuBuilder.get(messages.getString("masterdata"), VaadinIcon.PLUS.create())
//                                .add(LeftSubMenuBuilder
//                                        .get("My Submenu", VaadinIcon.PLUS.create())
//                                        .add(new LeftNavigationComponent(View2.class))
//                                        .add(new LeftNavigationComponent(View3.class))
//                                        .add(new LeftNavigationComponent(View4.class))
//                                        .build())
                                .add(new LeftNavigationComponent(StundensaetzeScreen.class))
                                .add(new LeftNavigationComponent(BenutzerScreen.class))
                                .build())
                        .add(LeftSubMenuBuilder.get(messages.getString("projectmanagement"), VaadinIcon.PLUS.create())
                                .add(new LeftNavigationComponent(ProjektplanungScreen.class))
                                .add(new LeftNavigationComponent(AufwandProjInitScreen.class))
                                .add(new LeftNavigationComponent(CostsScreen.class))
                                .add(new LeftNavigationComponent(VertriebScreen.class))
                                .add(new LeftNavigationComponent(OfferScreen.class))
                                .add(new LeftNavigationComponent(ProjectAdministrationScreen.class))
                                .build())
                        .addToSection(
                                new LeftNavigationComponent(ImprintScreen.class), FOOTER)
                        .build())
                .build();
    }

    private void prepare() {
        locale = VaadinSession.getCurrent().getAttribute(Locale.class);
        messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
        currentUser = VaadinSession.getCurrent().getAttribute(Benutzer.class);
        currentProject = VaadinSession.getCurrent().getAttribute(PlannedProject.class);
    }

    public Locale getLocale(){
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public void setCurrentProject(final PlannedProject currentProject) {
        this.currentProject = currentProject;
    }

//    private void buildLoginScreen() {
//        final LoginMask mask = new LoginMask(VaadinSession.getCurrent().getUI);
//        removeAll();
//        add(mask);
//    }

    public ResourceBundle getResourceBundle() {
        return messages;
    }

    public PlannedProject getCurrentProject() {
        currentProject = VaadinSession.getCurrent().getAttribute(PlannedProject.class);
        return currentProject;
    }

    public Benutzer getCurrentUser(){
        return currentUser;
    }
}
