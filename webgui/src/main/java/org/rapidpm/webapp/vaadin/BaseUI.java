package org.rapidpm.webapp.vaadin;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 21:37
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.vaadin.server.ClassResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.StartDataCreator;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.windows.LoginMask;

import java.security.InvalidKeyException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.IMAGE_LOGO;
import static org.rapidpm.Constants.MESSAGESBUNDLE;

public abstract class BaseUI extends UI {
  private static final Logger logger = Logger.getLogger(BaseUI.class);

  private static final boolean DEBUG_MODE = false;
  private static final String VERSION = "alpha - for testing only";
  public static ResourceBundle messages;
  private final HorizontalLayout iconsLayout = new HorizontalLayout();
  private final MenuBar menubar = new MenuBar();
  private final RapidPanel workingArea = new RapidPanel();
  private final VerticalLayout mainlayout = new VerticalLayout();
  protected Benutzer currentUser;
  protected PlannedProject currentProject;
  protected Locale locale = new Locale("de", "DE");

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
    if (DaoFactorySingleton.getInstance().countVertices() <= 0) {
      try {
        StartDataCreator.run();
      } catch (MissingNonOptionalPropertyException | InvalidKeyException | NotYetImplementedException e) {
        Notification.show("Error during creation of StartData: " + e.getMessage());
        e.printStackTrace();
      }
    }
    final VaadinSession session = this.getSession();
    final PlannedProjectDAO plannedProjectDAO = DaoFactorySingleton.getInstance().getPlannedProjectDAO();
    final List<PlannedProject> plannedProjects = plannedProjectDAO.findAll();
    for (final PlannedProject plannedProject : plannedProjects) {
      System.out.println("Project found: " + plannedProject.toString());
    }
    Collections.sort(plannedProjects);
    if (plannedProjects.isEmpty()) {
      session.setAttribute(PlannedProject.class, null);
    } else {
      session.setAttribute(PlannedProject.class, plannedProjects.get(0));
    }
    currentProject = session.getAttribute(PlannedProject.class);
  }

  private void buildMainLayout() {
    mainlayout.setSizeFull();
    workingArea.setSizeFull();

    createIconsLayout();

    initMenuBar(menubar);
    menubar.setSizeUndefined();
    iconsLayout.setSizeUndefined();
    iconsLayout.setWidth("100%");
    menubar.setWidth("100%");
    workingArea.setSizeFull();
    mainlayout.setSpacing(false);
    mainlayout.addComponent(iconsLayout);
    mainlayout.addComponent(menubar);
    mainlayout.addComponent(workingArea);
    mainlayout.setExpandRatio(workingArea, 1);
    mainlayout.setSpacing(false);
    setContent(mainlayout);
  }

  private void buildLoginScreen() {
    final LoginMask mask = new LoginMask(this);
    setContent(null);
    setContent(mask);
  }

  public void authentication(final String enteredLogin, final String enteredPasswd) throws Exception {
    final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
    final BenutzerDAO benutzerDAO = daoFactory.getBenutzerDAO();
    final Benutzer benutzer = benutzerDAO.loadBenutzerForLogin(enteredLogin);
    final String enteredPasswdHashed = hash(enteredPasswd);
    final String userLogin = benutzer.getLogin();
    final String userPasswd = benutzer.getPasswd();
    if (userLogin.equals(enteredLogin) && userPasswd.equals(enteredPasswdHashed)) {
      currentUser = benutzer;
      System.out.println(currentUser);
      getSession().setAttribute(Benutzer.class, currentUser);
      setContent(null);
      loadProtectedRessources();
    }
  }

  private void createIconsLayout() {
    final Image iconLeft = new Image(null, new ClassResource(IMAGE_LOGO));
    final Image iconRight = new Image(null, new ClassResource(IMAGE_LOGO));
    iconsLayout.setWidth("100%");
    iconsLayout.addComponent(iconLeft);
    iconsLayout.setComponentAlignment(iconLeft, Alignment.TOP_LEFT);

    final Label versionLabel = new Label(VERSION);
    versionLabel.setSizeUndefined();
    iconsLayout.addComponent(versionLabel);
    iconsLayout.setComponentAlignment(versionLabel, Alignment.MIDDLE_CENTER);

    iconsLayout.addComponent(iconRight);
    iconsLayout.setComponentAlignment(iconRight, Alignment.TOP_RIGHT);
    iconsLayout.setMargin(new MarginInfo(false, false, false, false));
    iconsLayout.setExpandRatio(versionLabel, 1.0f);
  }

  protected abstract void initMenuBar(MenuBar menuBar);

  private String hash(final String enteredPasswd) {
    return enteredPasswd;        //TODO spÃ¤ter gehashtes PW zurÃ¼ckgeben
  }

  private void loadProtectedRessources() {
    buildMainLayout();
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

  public MenuBar getMenuBar() {
    return menubar;
  }

  public RapidPanel getWorkingAreaContainer() {
    return workingArea;
  }

  public void setWorkingArea(final Component workingArea) {
    this.workingArea.removeAllComponents();
    this.workingArea.getContentLayout().setMargin(false);
    this.workingArea.addComponent(workingArea);
    this.workingArea.getContent().setSizeFull();
    this.workingArea.setSizeFull();
  }

  public ResourceBundle getResourceBundle() {
    return messages;
  }

  public PlannedProject getCurrentProject() {
    currentProject = getSession().getAttribute(PlannedProject.class);
    return currentProject;
  }

  public void setCurrentProject(final PlannedProject currentProject) {
    this.currentProject = currentProject;
  }

  public Benutzer getCurrentUser() {
    return currentUser;
  }
}
