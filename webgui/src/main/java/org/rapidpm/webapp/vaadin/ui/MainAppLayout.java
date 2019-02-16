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
        return AppLayoutBuilder
                .get(Behaviour.LEFT_RESPONSIVE)
                .withTitle("RapidPM 2019 - Vaadin 13")
                .withAppBar(AppBarBuilder
                        .get()
                        .add(new AppBarNotificationButton(VaadinIcon.BELL, notifications))
                        .build())
                .withAppMenu(LeftAppMenuBuilder
                        .get()
                        .addToSection(new MenuHeaderComponent(null, null, "/frontend/images/rpm.jpg"), HEADER)
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
                        .addToSection(new LeftClickableComponent("Impressum",
                                VaadinIcon.COG.create(),
                                clickEvent -> Notification.show("onClick ...")
                        ), FOOTER)
                        .build())
                .build();
    }

    private void prepare() {
        locale = new Locale("de","DE");
        messages = ResourceBundle.getBundle("MessagesBundle", locale);
        loadFirstProject();
        final VaadinSession session = VaadinSession.getCurrent();
        session.setAttribute(ResourceBundle.class, getResourceBundle());
        String username = "sven.ruppert";
        String password = "geheim";
        Benutzer user = session.getAttribute(Benutzer.class);
//        if (user != null) {
//            if (DEBUG_MODE) {
//                buildMainLayout();
//            } else {
//                buildLoginScreen();
//            }
//        } else {
        if (user != null) {
            username = "sven.ruppert";
            password = "geheim";
        }
        try {
            authentication(username, password);
        } catch (Exception e) {
            logger.error("Erneute Authentifizierung fehlgeschlagen", e.fillInStackTrace());
        }
    }
    private void loadFirstProject() {
        final VaadinSession session = VaadinSession.getCurrent();
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        List<PlannedProject> projects = null;
        if (daoFactory != null) {
            projects = daoFactory.getPlannedProjectDAO().loadAllEntities();
        }

        if(projects == null || projects.isEmpty()){
            session.setAttribute(PlannedProject.class, null);
        } else {
            session.setAttribute(PlannedProject.class, projects.get(0));
            Collections.sort(projects);
        }
        currentProject = session.getAttribute(PlannedProject.class);
    }

    public void authentication(final String enteredLogin, final String enteredPasswd) {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final BenutzerDAO benutzerDAO = daoFactory.getBenutzerDAO();
        final List<Benutzer> benutzer = benutzerDAO.loadBenutzerForLogin(enteredLogin);
        final String enteredPasswdHashed = hash(enteredPasswd);
        for (final Benutzer user : benutzer) {
            final String userLogin = user.getLogin();
            final String userPasswd = user.getPasswd();
            if (userLogin.equals(enteredLogin) && userPasswd.equals(enteredPasswdHashed)) {
                currentUser = user;
                VaadinSession.getCurrent().setAttribute(Benutzer.class, currentUser);
                return;
            }
        }
        Notification.show("Login failed..");
    }

    private String hash(final String enteredPasswd) {
        return enteredPasswd;        //TODO spÃ¤ter gehashtes PW zurÃ¼ckgeben
    }

    public void localization(final Object value) {
        switch (value.toString()) {
            case "GERMAN":
                locale = new Locale("de", "DE");
                break;
            case "ENGLISH":
                locale = new Locale("en", "US");
                break;
        }
        messages = ResourceBundle.getBundle(MESSAGESBUNDLE, locale);
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
