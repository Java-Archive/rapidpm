package org.rapidpm.webapp.vaadin;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 21:37
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.vaadin.annotations.Theme;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.WrappedRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;
import org.rapidpm.webapp.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.demo.DemoWorkingArea;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.StammdatenWorkingArea;

import static org.rapidpm.webapp.vaadin.Constants.*;

@Theme("rapidpm")
public abstract class BaseRoot extends Root {
//    private static final Logger logger = Logger.getLogger(MainApplicationBean.class);

    private static final boolean DEBUG_MODE = false;
    private static final int DEFAULT_WINDOW_WIDTH = 1024;
    private static final String LEFT_STRIPE_WITH = "20px";
    private static final String RIGHT_STRIPE_WITH = "20px";

    private int windowWidth = DEFAULT_WINDOW_WIDTH;
//    private final List<WindowSizeChangeListener> windowSizeChangeListeners = new ArrayList<>();

    //globale referenzen -> in eine reg oder so..
    private final HorizontalLayout hlHeaderLineContainer = new HorizontalLayout(); //obere buttonLeiste
    private final MenuBar menubar = new MenuBar();
    //InfoPanel
    //MessagePanel
    private final VerticalLayout hlWorkingAreaContainer = new VerticalLayout();

    private final String applicationName;
    private String currentUser = "";


    protected BaseRoot(final String applicationName) {
        this.applicationName = applicationName;
        setSizeFull();
    }

//    public int getWindowWidth() {
//        return windowWidth;
//    }
//
    public void setWindowWidth(final int windowWidth) {
        if (this.windowWidth != windowWidth) {
            this.windowWidth = windowWidth;
//            fireWindowSizeChanged();
        }
    }

    public int getMiddleStripeWidth() {
        return windowWidth - 40; // REFAC Konstante
//        return Page.getCurrent().getBrowserWindowWidth()-40;
    }

//    public void addWindowSizeChangeListener(final WindowSizeChangeListener listener) {
//        windowSizeChangeListeners.add(listener);
//    }
//
//    private void fireWindowSizeChanged() {
//        final int newMiddleStripeWidth = getMiddleStripeWidth();
//        for (final WindowSizeChangeListener listener : windowSizeChangeListeners) {
//            listener.onWindowSizeChanged(windowWidth, newMiddleStripeWidth);
//        }
//    }


    @Override
    public void init(WrappedRequest request) {
//        getApplication().setRootPreserved(true);
//        setTheme("chameleon-blue");
//        setWindowWidth(request.getBrowserDetails().getWebBrowser().getClientWidth());
        if (DEBUG_MODE) {
            buildMainLayout();
        } else {
//            removeAllComponents();
            VerticalLayout layout = new VerticalLayout();
            final LoginWindow window = new LoginWindow(this);
            addWindow(window);
        }
    }

    public void authentication(final String login, final String passwd) throws Exception {
        if (login.equals("RapidPM") && passwd.equals("geheim")) {

            currentUser = login; //UserObject spaeter
            //setUser(login); //TODO das UserObject itself.
            loadProtectedRessources();
        } else {
            throw new Exception("Login failed..");
        }
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
        workingArea.setWidth(getMiddleStripeWidth(), Sizeable.Unit.PIXELS);
//        addWindowSizeChangeListener(new WindowSizeChangeListener() {
//            @Override
//            public void onWindowSizeChanged(final int newWindowWidth, final int newMiddleStripeWidth) {
//                workingArea.setWidth(newMiddleStripeWidth, Sizeable.Unit.PIXELS);
//            }
//        });
        hlWorkingAreaContainer.removeAllComponents();
        hlWorkingAreaContainer.addComponent(workingArea);
    }


    private void buildMainLayout() {

        final VerticalLayout mainlayout = new VerticalLayout();
        mainlayout.setSizeFull();
        //final Window window = new Window(applicationName, mainlayout);
        //addWindow(window);

        final HorizontalLayout gl = new HorizontalLayout();
        //gl.setSizeFull();
        //gl.setWidth(windowWidth, Sizeable.Unit.PIXELS);
        gl.setSpacing(true);
        mainlayout.addComponent(gl);
        //mainlayout.setComponentAlignment(gl, Alignment.TOP_CENTER);


        //Hpt 3 Spalten
        final VerticalLayout vlLeft = new VerticalLayout();
        vlLeft.setWidth(LEFT_STRIPE_WITH);
        gl.addComponent(vlLeft);
        gl.setExpandRatio(vlLeft, 1.0f);
        gl.setComponentAlignment(vlLeft, Alignment.TOP_LEFT);

        final VerticalLayout vlMiddle = new VerticalLayout();
        vlMiddle.setWidth(getMiddleStripeWidth(), Sizeable.Unit.PIXELS);
        gl.addComponent(vlMiddle);
        gl.setExpandRatio(vlMiddle, 1.0f);
        gl.setComponentAlignment(vlMiddle, Alignment.TOP_CENTER);

        final VerticalLayout vlRight = new VerticalLayout();
        vlRight.setWidth(RIGHT_STRIPE_WITH);
        gl.addComponent(vlRight);
        gl.setExpandRatio(vlRight, 1.0f);
        gl.setComponentAlignment(vlRight, Alignment.TOP_RIGHT);


        //Innere Bereich komplett
        final VerticalLayout vlMiddleInner = new VerticalLayout(); //
        vlMiddleInner.setWidth(getMiddleStripeWidth(), Sizeable.Unit.PIXELS);
        vlMiddle.addComponent(vlMiddleInner);

        vlMiddleInner.addComponent(hlHeaderLineContainer);
        vlMiddleInner.setComponentAlignment(hlHeaderLineContainer, Alignment.TOP_RIGHT);

        final HorizontalLayout hlHeader = new HorizontalLayout();
        vlMiddleInner.addComponent(hlHeader);
        hlHeader.setWidth(getMiddleStripeWidth(), Sizeable.Unit.PIXELS);


        final HorizontalLayout hlHeaderBottomLine = new HorizontalLayout();
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
        Embedded emLeft = new Embedded("", new ThemeResource(IMAGE_LOGO));
        hlHeader.addComponent(emLeft);
        hlHeader.setComponentAlignment(emLeft, Alignment.TOP_LEFT);

        //InfoPanel - MessagePanel
        Embedded emRight = new Embedded("", new ThemeResource(IMAGE_LOGO));
        hlHeader.addComponent(emRight);
        hlHeader.setComponentAlignment(emRight, Alignment.TOP_RIGHT);

        //hlHeaderBottomLine
        initMenuBarIntern(menubar);
        hlHeaderBottomLine.addComponent(menubar);


        //hlWorkingAreaContainer
//        final Component demoWorkingArea = createDemoWorkingArea();
        setWorkingArea(new DemoWorkingArea());
        addComponent(mainlayout);

        // vaadin 7
//        setImmediate(true);
//        Root.getCurrent().getPage().addListener(new Page.BrowserWindowResizeListener() {
//            @Override
//            public void browserWindowResized(final Page.BrowserWindowResizeEvent event) {
//                final int newWindowWidth = Page.getCurrent().getBrowserWindowWidth();
//                setWindowWidth(newWindowWidth);
//                final int newWindowHight = Page.getCurrent().getBrowserWindowHeight();
//                gl.setWidth(newWindowWidth, Sizeable.Unit.PIXELS);
//                final int middleStripeWidth = getMiddleStripeWidth();
//                menubar.setWidth(middleStripeWidth, Sizeable.Unit.PIXELS);
//                vlMiddle.setWidth(middleStripeWidth, Sizeable.Unit.PIXELS);
//                vlMiddleInner.setWidth(middleStripeWidth, Sizeable.Unit.PIXELS);
//                hlHeader.setWidth(middleStripeWidth, Sizeable.Unit.PIXELS);
//
//
//            }
//        });

//        addWindowSizeChangeListener(new WindowSizeChangeListener() {
//            @Override
//            public void onWindowSizeChanged(final int newWindowWidth, final int newMiddleStripeWidth) {
//                gl.setWidth(newWindowWidth, Sizeable.Unit.PIXELS);
//                menubar.setWidth(newMiddleStripeWidth, Sizeable.Unit.PIXELS);
//                vlMiddle.setWidth(newMiddleStripeWidth, Sizeable.Unit.PIXELS);
//                vlMiddleInner.setWidth(newMiddleStripeWidth, Sizeable.Unit.PIXELS);
//                hlHeader.setWidth(newMiddleStripeWidth, Sizeable.Unit.PIXELS);
//            }
//        });


//        Page.getBrowserWindowHeight();
//        Page.getBrowserWindowWidth()
//        addWindowSizeChangeListener(new WindowSizeChangeListener() {
//            @Override
//            public void onResize(Window.ResizeEvent event) {
//                setWindowWidth(event.getWidth());
//            }
//
//            @Override
//            public void onWindowSizeChanged(int newWindowWidth, int newMiddleStripeWidth) {
//                setWindowWidth(newWindowWidth);
//            }
//        });
//

        //hlBottomLine
        //        hlBottomLine.setComponentAlignment();

        // Liferay Test
//        final LiferayIPC liferay = new LiferayIPC();
//        liferay.addListener("event1", new LiferayIPCEventListener() {
//            @Override
//            public void eventReceived(final LiferayIPCEvent event) {
//                showNotification(event.getSource().toString(), event.getData());
//            }
//        });
//        mainlayout.addComponent(liferay);

        ////final Root root = Root.getCurrent();
        ////root.removeAllComponents();
        //removeAllComponents();


    }

    protected abstract void initMenuBar(MenuBar menuBar);

    //TODO hier anhand des Users den Context entscheiden
    private void initMenuBarIntern(final MenuBar menuBar) {


        final MenuBar.MenuItem stammdatenMenu = menuBar.addItem("Stammdaten", null);
        stammdatenMenu.addItem("Benutzer", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new StammdatenWorkingArea());
            }
        });

        initMenuBar(menuBar);
        menuBar.setWidth(getMiddleStripeWidth(), Sizeable.Unit.PIXELS);
    }

    private HorizontalLayout createStdHeaderLine() {
        final HorizontalLayout hlHeaderLine = new HorizontalLayout();
        hlHeaderLine.setSizeFull();
        hlHeaderLine.setSpacing(true);
        final Button buttonKontakt = new Button("Kontakt", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Root.getCurrent().addWindow(new KontaktWindow());
            }
//                getMainWindow().addWindow(new KontaktWindow());
        });
        final Button buttonSupport = new Button("Support", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Root.getCurrent().addWindow(new SupportWindow());
            }
        });
        final Button buttonImpressum = new Button("Impressum", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Root.getCurrent().addWindow(new ImpressumWindow());
            }
        });
        final Button buttonDisclaimer = new Button("Disclaimer", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Root.getCurrent().addWindow(new DisclaimerWindow());
            }
        });
        final Button buttonSitemap = new Button("Sitemap", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Root.getCurrent().addWindow(new KontaktWindow());
            }
        });
        final Button buttonAbmelden = new Button("Abmelden", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
//                getApplication().close();
                BaseRoot.this.setEnabled(false); // O.o
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


//    public static interface WindowSizeChangeListener {
//        void onWindowSizeChanged(int newWindowWidth, int newMiddleStripeWidth);
//    }
}
