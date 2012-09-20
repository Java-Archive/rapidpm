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
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;
import org.apache.log4j.Logger;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.webapp.vaadin.ui.windows.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.IMAGE_LOGO;
import static org.rapidpm.Constants.MESSAGESBUNDLE;

@Theme("rapidpm")
public abstract class BaseUI extends UI {
    private static final Logger logger = Logger.getLogger(BaseUI.class);

    private static final boolean DEBUG_MODE = false;
//    private static final int DEFAULT_WINDOW_WIDTH = 1024;
//    private static final String LEFT_STRIPE_WITH = "20px";
//    private static final String RIGHT_STRIPE_WITH = "20px";

//    private int windowWidth = DEFAULT_WINDOW_WIDTH;
//    private final List<WindowSizeChangeListener> windowSizeChangeListeners = new ArrayList<>();

    //globale referenzen -> in eine reg oder so..
    private final HorizontalLayout hlHeaderLineContainer = new HorizontalLayout(); //obere buttonLeiste
    private final MenuBar menubar = new MenuBar();
    //InfoPanel
    //MessagePanel
    private final VerticalLayout hlWorkingAreaContainer = new VerticalLayout();

    protected Benutzer currentUser;
    protected Locale locale = new Locale("de","DE");
    protected ResourceBundle messages;



    protected ProjektBean projektBean = new ProjektBean(5);


    @Override
    public void init(final WrappedRequest request) {
        //setWindowWidth(request.getBrowserDetails().getWebBrowser().getScreenWidth());
        this.setSizeFull();
        final VaadinSession session = getSession();
        if (session.getAttribute(Benutzer.class) == null) {
            if (DEBUG_MODE) {
                buildMainLayout();
            } else {
//            removeAllComponents();
//                final VerticalLayout layout = new VerticalLayout();
                final LoginWindow window = new LoginWindow(this);
                addWindow(window);
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

    public void authentication(final String enteredLogin, final String enteredPasswd) throws Exception {

        final LoginBean bean = EJBFactory.getEjbInstance(LoginBean.class);
        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final BenutzerDAO benutzerDAO = baseDaoFactoryBean.getBenutzerDAO();
        final List<Benutzer> benutzer = benutzerDAO.loadBenutzerForLogin(enteredLogin);
        final String enteredPasswdHashed = hash(enteredPasswd);
        for (final Benutzer user : benutzer) {
            final String userLogin = user.getLogin();
            final String userPasswd = user.getPasswd();
            if (userLogin.equals(enteredLogin) && userPasswd.equals(enteredPasswdHashed)) {
                currentUser = user;
                //getSession().setUser(currentUser);
                getSession().setAttribute(Benutzer.class, currentUser);
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
                messages = ResourceBundle.getBundle(MESSAGESBUNDLE, locale);
                break;
            case "ENGLISH":
                locale = new Locale("en", "US");
                messages = ResourceBundle.getBundle(MESSAGESBUNDLE, locale);
                break;
        }
        //this.getSession().setLocale(messagesBundle);
    }

    private void loadProtectedRessources() {
        buildMainLayout();
    }

    public MenuBar getMenuBar() {
        return menubar;
    }

    public HorizontalLayout getHeaderLineContainer() {
        return hlHeaderLineContainer;
    }

    public void setHeaderLine(final Component headerLine) {
        hlHeaderLineContainer.removeAllComponents();
        hlHeaderLineContainer.addComponent(headerLine);
    }

    public VerticalLayout getWorkingAreaContainer() {
        return hlWorkingAreaContainer;
    }

    public void setWorkingArea(final Component workingArea) {
        workingArea.setSizeFull();
        hlWorkingAreaContainer.setSizeFull();
        hlWorkingAreaContainer.removeAllComponents();
        hlWorkingAreaContainer.addComponent(workingArea);
    }


    private void buildMainLayout() {

        final VerticalLayout mainlayout = new VerticalLayout();
        mainlayout.setSizeFull();

        final VerticalLayout vlMiddle = new VerticalLayout();
        vlMiddle.setSizeFull();

        final HorizontalLayout gl = new HorizontalLayout();
        gl.setSizeFull();
        gl.setSpacing(true);
        gl.addComponent(vlMiddle);
        gl.setExpandRatio(vlMiddle, 1.0f);
        gl.setComponentAlignment(vlMiddle, Alignment.TOP_CENTER);
        mainlayout.addComponent(gl);


        //Innere Bereich komplett
        final VerticalLayout vlMiddleInner = new VerticalLayout(); //
        vlMiddleInner.setSizeFull();
        vlMiddle.addComponent(vlMiddleInner);

        vlMiddleInner.addComponent(hlHeaderLineContainer);
        vlMiddleInner.setComponentAlignment(hlHeaderLineContainer, Alignment.TOP_RIGHT);

        final HorizontalLayout hlHeader = new HorizontalLayout();
        vlMiddleInner.addComponent(hlHeader);
        hlHeader.setSizeFull();


        final HorizontalLayout hlHeaderBottomLine = new HorizontalLayout();
        hlHeaderBottomLine.setSizeFull();
        vlMiddleInner.addComponent(hlHeaderBottomLine);

//        hlWorkingAreaContainer.setSizeFull();
        vlMiddleInner.addComponent(hlWorkingAreaContainer);

        final HorizontalLayout hlBottomLine = new HorizontalLayout();
        vlMiddleInner.addComponent(hlBottomLine);
        vlMiddleInner.setComponentAlignment(hlBottomLine, Alignment.BOTTOM_CENTER);

        //HeaderLine
        final HorizontalLayout headerLine = createStdHeaderLine();
        setHeaderLine(headerLine);

        //Header
        final Embedded emLeft = new Embedded("", new ThemeResource(IMAGE_LOGO));
        hlHeader.addComponent(emLeft);
        hlHeader.setComponentAlignment(emLeft, Alignment.TOP_LEFT);

        //InfoPanel - MessagePanel
        final Embedded emRight = new Embedded("", new ThemeResource(IMAGE_LOGO));
        hlHeader.addComponent(emRight);
        hlHeader.setComponentAlignment(emRight, Alignment.TOP_RIGHT);

        //hlHeaderBottomLine
        initMenuBarIntern(menubar);
        hlHeaderBottomLine.addComponent(menubar);

        setWorkingArea(new ProjektplanungScreen(BaseUI.this));
        addComponent(mainlayout);
    }

    protected abstract void initMenuBar(MenuBar menuBar);

    //TODO hier anhand des Users den Context entscheiden
    private void initMenuBarIntern(final MenuBar menuBar) {
        initMenuBar(menuBar);
        menuBar.setSizeFull();
    }

    private HorizontalLayout createStdHeaderLine() {
        final HorizontalLayout hlHeaderLine = new HorizontalLayout();
        hlHeaderLine.setSizeFull();
        hlHeaderLine.setSpacing(true);
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
            }
        });
        buttonKontakt.setStyleName(BaseTheme.BUTTON_LINK);
        buttonSupport.setStyleName(BaseTheme.BUTTON_LINK);
        buttonImpressum.setStyleName(BaseTheme.BUTTON_LINK);
        buttonDisclaimer.setStyleName(BaseTheme.BUTTON_LINK);
        buttonSitemap.setStyleName(BaseTheme.BUTTON_LINK);
        buttonAbmelden.setStyleName(BaseTheme.BUTTON_LINK);

        hlHeaderLine.addComponent(buttonAbmelden);
        hlHeaderLine.addComponent(new Label("|"));
//        buttonAbmelden.addListener(this);
        hlHeaderLine.setComponentAlignment(buttonAbmelden, Alignment.MIDDLE_RIGHT);

        hlHeaderLine.addComponent(buttonKontakt);
        hlHeaderLine.addComponent(new Label("|"));
//        buttonKontakt.addListener(this);
        hlHeaderLine.setComponentAlignment(buttonKontakt, Alignment.MIDDLE_RIGHT);

        hlHeaderLine.addComponent(buttonSupport);
        hlHeaderLine.addComponent(new Label("|"));
//        buttonSupport.addListener(this);
        hlHeaderLine.setComponentAlignment(buttonSupport, Alignment.MIDDLE_RIGHT);

        hlHeaderLine.addComponent(buttonImpressum);
        hlHeaderLine.addComponent(new Label("|"));
//        buttonImpressum.addListener(this);
        hlHeaderLine.setComponentAlignment(buttonImpressum, Alignment.MIDDLE_RIGHT);

        hlHeaderLine.addComponent(buttonDisclaimer);
        hlHeaderLine.addComponent(new Label("|"));
//        buttonDisclaimer.addListener(this);
        hlHeaderLine.setComponentAlignment(buttonDisclaimer, Alignment.MIDDLE_RIGHT);

        hlHeaderLine.addComponent(buttonSitemap);
//        buttonSitemap.addListener(this);
        hlHeaderLine.setComponentAlignment(buttonSitemap, Alignment.MIDDLE_RIGHT);
        return hlHeaderLine;
    }


    public ResourceBundle getResourceBundle() {
        return messages;
    }

    public ProjektBean getProjektBean() {
        return projektBean;
    }

    public void setProjektBean(ProjektBean projektBean) {
        this.projektBean = projektBean;
    }
}
