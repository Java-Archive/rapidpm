package org.rapidpm.webapp.vaadin;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 21:37
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.vaadin.annotations.Theme;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.windows.*;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.IMAGE_LOGO;
import static org.rapidpm.Constants.MESSAGESBUNDLE;

@Theme("rapidpm")
public abstract class BaseUI extends UI {
    private static final Logger logger = Logger.getLogger(BaseUI.class);

    private static final boolean DEBUG_MODE = false;
    private final VerticalLayout upperScreenLayout = new VerticalLayout();
    private final HorizontalLayout linkLeistenLayout = new HorizontalLayout(); //obere buttonLeiste
    private final HorizontalLayout iconsLayout = new HorizontalLayout();
    private final MenuBar menubar = new MenuBar();
    private final RapidPanel workingArea = new RapidPanel();
    private final VerticalLayout mainlayout = new VerticalLayout();

    protected Benutzer currentUser;
    protected PlannedProject currentProject;
    protected Locale locale = new Locale("de","DE");
    public static ResourceBundle messages;

    @Override
    public void init(final VaadinRequest request) {
        loadFirstProject();
        this.setSizeFull();
        final VaadinSession session = getSession();
        if (session.getAttribute(Benutzer.class) == null) {
            if (DEBUG_MODE) {
                buildMainLayout();
            } else {
                buildLoginScreen();
            }
        } else {
            currentUser = session.getAttribute(Benutzer.class);
            try {
                authentication(currentUser.getLogin(), currentUser.getPasswd());
            } catch (Exception e) {
                logger.error("Erneute Authentifizierung fehlgeschlagen", e.fillInStackTrace());
            }
        }
    }

    private void loadFirstProject() {
//        final LoginBean bean = EJBFactory.getEjbInstance(LoginBean.class);
        final VaadinSession session = this.getSession();
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final List<PlannedProject> projects = daoFactory.getPlannedProjectDAO().loadAllEntities();
        if(projects == null || projects.isEmpty()){
            session.setAttribute(PlannedProject.class, null);
        } else {
            session.setAttribute(PlannedProject.class, projects.get(0));
        }
        currentProject = session.getAttribute(PlannedProject.class);
    }

    public void authentication(final String enteredLogin, final String enteredPasswd) throws Exception {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final BenutzerDAO benutzerDAO = daoFactory.getBenutzerDAO();
        final List<Benutzer> benutzer = benutzerDAO.loadBenutzerForLogin(enteredLogin);
        final String enteredPasswdHashed = hash(enteredPasswd);
        for (final Benutzer user : benutzer) {
            final String userLogin = user.getLogin();
            final String userPasswd = user.getPasswd();
            if (userLogin.equals(enteredLogin) && userPasswd.equals(enteredPasswdHashed)) {
                currentUser = user;
                getSession().setAttribute(Benutzer.class, currentUser);
                setContent(null);
                loadProtectedRessources();
                return;
            }
        }
        throw new Exception("Login failed..");
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

    private void loadProtectedRessources() {
        buildMainLayout();
    }

    public MenuBar getMenuBar() {
        return menubar;
    }


    public RapidPanel getWorkingAreaContainer() {
        return workingArea;
    }

    public void setCurrentProject(final PlannedProject currentProject) {
        this.currentProject = currentProject;
    }

    public void setWorkingArea(final Component workingArea) {
        workingArea.setSizeFull();
        this.workingArea.removeAllComponents();
        this.workingArea.addComponent(workingArea);
    }


    private void buildMainLayout() {


        mainlayout.setSizeFull();
        workingArea.setSizeFull();

        createLinkLeistenLayout();
        createIconsLayout();

        initMenuBar(menubar);
        menubar.setWidth("100%");

        linkLeistenLayout.setSizeUndefined();
        iconsLayout.setSizeUndefined();
        iconsLayout.setWidth("100%");
        upperScreenLayout.addComponent(linkLeistenLayout);
        upperScreenLayout.setComponentAlignment(linkLeistenLayout, Alignment.BOTTOM_LEFT);
        upperScreenLayout.addComponent(iconsLayout);
        upperScreenLayout.setComponentAlignment(iconsLayout, Alignment.TOP_LEFT);
        upperScreenLayout.setSpacing(false);
        upperScreenLayout.addComponent(menubar);
        mainlayout.addComponent(upperScreenLayout);
        //mainlayout.addComponent(menubar);
        mainlayout.addComponent(workingArea);
        mainlayout.setExpandRatio(upperScreenLayout,17);
        //mainlayout.setExpandRatio(menubar, 2);
        mainlayout.setExpandRatio(workingArea,85);
        mainlayout.setSpacing(false);
//        RapidPanel panel = new RapidPanel();
//        panel.setSizeFull();
//        Table table = new Table();
//        table.addContainerProperty("blub", String.class, null);
//        table.addContainerProperty("bl32ub", String.class, null);
//        table.addContainerProperty("blsdfub", String.class, null);
//        table.addContainerProperty("bfsdlub", String.class, null);
//        table.addContainerProperty("bdsub", String.class, null);
//        table.addContainerProperty("blfdfvub", String.class, null);
//        table.addContainerProperty("bcxvlub", String.class, null);
//        table.addContainerProperty("blcv vub", String.class, null);
//        table.addContainerProperty("blxcyub", String.class, null);
//        table.addContainerProperty("b xcv lub", String.class, null);
//        table.addContainerProperty("b lub", String.class, null);
//        table.addContainerProperty("bl xc ub", String.class, null);
//        table.addContainerProperty("blsfsaasub", String.class, null);
//        table.addContainerProperty("sfserwerdfsfdf", String.class, null);
//        table.addContainerProperty("sfsdfserwrfdf", String.class, null);
//        table.addContainerProperty("serwrfsdfsfdf", String.class, null);
//        table.addContainerProperty("sfsdfwerwerwsfdf", String.class, null);
//        table.addContainerProperty("ewrw", String.class, null);
//        table.addContainerProperty("ewrwerewr", String.class, null);
//        table.addContainerProperty("sfsdwerwfsfdf", String.class, null);
//        table.addContainerProperty("sfdsfssdfsfdf", String.class, null);
//        table.addContainerProperty("sfssdfsddfsfdf", String.class, null);
//        table.addContainerProperty("sfssfsdfsfdf", String.class, null);
//        table.addContainerProperty("sfssdfsfsdfdfsfdf", String.class, null);
//        table.addContainerProperty("qqqeqwe", String.class, null);table.addContainerProperty("aefdsgdg",
//                String.class, null);
//        table.addContainerProperty("weqeweqeqw", String.class, null);
//        table.addContainerProperty("qewqewqew", String.class, null);
//        table.addContainerProperty("fgdgerg", String.class, null);
//        table.addContainerProperty("dfgdsg", String.class, null);
//
//        //table.setSizeFull();
//        panel.addComponent(table);
//        for (int i = 0; i < 20; i++) {
//             Button button = new Button("test");
//
//            if(i%2 == 0){
//                VerticalLayout layout = new VerticalLayout();
//                layout.setSizeUndefined();
//                layout.addComponent(button);
//                layout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
//                panel.addComponent(layout);
//            } else {
//                panel.addComponent(button);
//            }
//
//        }
        setContent(mainlayout);
    }

    private void createIconsLayout() {
        final Embedded emLeft = new Embedded("", new ThemeResource(IMAGE_LOGO));
        final Embedded emRight = new Embedded("", new ThemeResource(IMAGE_LOGO));

        iconsLayout.setWidth("100%");
        iconsLayout.addComponent(emLeft);
        iconsLayout.setComponentAlignment(emLeft, Alignment.TOP_LEFT);
        iconsLayout.addComponent(emRight);
        iconsLayout.setComponentAlignment(emRight, Alignment.TOP_RIGHT);
    }

    protected abstract void initMenuBar(MenuBar menuBar);

    private void createLinkLeistenLayout() {
        linkLeistenLayout.setSizeFull();
        linkLeistenLayout.setSpacing(true);
        final Button buttonKontakt = new Button("Kontakt", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().addWindow(new KontaktWindow());
            }
//                getMainWindow().addWindow(new KontaktWindow());
        });
        final Button buttonSupport = new Button("Support", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().addWindow(new SupportWindow());
            }
        });
        final Button buttonImpressum = new Button("Impressum", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().addWindow(new ImpressumWindow());
            }
        });
        final Button buttonDisclaimer = new Button("Disclaimer", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().addWindow(new DisclaimerWindow());
            }
        });
        final Button buttonSitemap = new Button("Sitemap", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().addWindow(new KontaktWindow());
            }
        });
        final Button buttonAbmelden = new Button("Abmelden", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getSession().close();
                getPage().setLocation("/rapidpm");
            }
        });
        buttonKontakt.setStyleName(BaseTheme.BUTTON_LINK);
        buttonSupport.setStyleName(BaseTheme.BUTTON_LINK);
        buttonImpressum.setStyleName(BaseTheme.BUTTON_LINK);
        buttonDisclaimer.setStyleName(BaseTheme.BUTTON_LINK);
        buttonSitemap.setStyleName(BaseTheme.BUTTON_LINK);
        buttonAbmelden.setStyleName(BaseTheme.BUTTON_LINK);

        linkLeistenLayout.addComponent(buttonAbmelden);
        linkLeistenLayout.addComponent(new Label("|"));
//        buttonAbmelden.addListener(this);
//        linkLeistenLayout.setComponentAlignment(buttonAbmelden, Alignment.MIDDLE_RIGHT);

        linkLeistenLayout.addComponent(buttonKontakt);
        linkLeistenLayout.addComponent(new Label("|"));
//        buttonKontakt.addListener(this);
//        linkLeistenLayout.setComponentAlignment(buttonKontakt, Alignment.MIDDLE_RIGHT);

        linkLeistenLayout.addComponent(buttonSupport);
        linkLeistenLayout.addComponent(new Label("|"));
//        buttonSupport.addListener(this);
//        linkLeistenLayout.setComponentAlignment(buttonSupport, Alignment.MIDDLE_RIGHT);

        linkLeistenLayout.addComponent(buttonImpressum);
        linkLeistenLayout.addComponent(new Label("|"));
//        buttonImpressum.addListener(this);
//        linkLeistenLayout.setComponentAlignment(buttonImpressum, Alignment.MIDDLE_RIGHT);

        linkLeistenLayout.addComponent(buttonDisclaimer);
        linkLeistenLayout.addComponent(new Label("|"));
//        buttonDisclaimer.addListener(this);
//        linkLeistenLayout.setComponentAlignment(buttonDisclaimer, Alignment.MIDDLE_RIGHT);

        linkLeistenLayout.addComponent(buttonSitemap);
//        buttonSitemap.addListener(this);
//        linkLeistenLayout.setComponentAlignment(buttonSitemap, Alignment.MIDDLE_RIGHT);
    }

    private void buildLoginScreen() {
        final LoginMask mask = new LoginMask(this);
        setContent(null);
        setContent(mask);
        System.out.println("hallo");
    }


    public ResourceBundle getResourceBundle() {
        return messages;
    }

    public PlannedProject getCurrentProject() {
        currentProject = getSession().getAttribute(PlannedProject.class);
        return currentProject;
    }

    public Benutzer getCurrentUser(){
        return currentUser;
    }
}
