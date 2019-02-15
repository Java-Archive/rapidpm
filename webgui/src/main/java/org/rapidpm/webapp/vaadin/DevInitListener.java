package org.rapidpm.webapp.vaadin;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class DevInitListener implements VaadinServiceInitListener {
   @Override
   public void serviceInit(ServiceInitEvent initEvent) {
       initEvent.getSource().addSessionInitListener(sessionInitEvent -> {
               Locale locale = new Locale("de","DE");
               ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", locale);
               loadFirstProject();
               final VaadinSession session = VaadinSession.getCurrent();
               session.setAttribute(ResourceBundle.class, messages);
               String username = "sven.ruppert";
               String password = "geheim";
               Benutzer user = session.getAttribute(Benutzer.class);
               if (user != null) {
                   username = "sven.ruppert";
                   password = "geheim";
               }
               try {
                   authentication(username, password);
               } catch (Exception e) {
                   //
               }
           });
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
    }

    private void authentication(final String enteredLogin, final String enteredPasswd) {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final BenutzerDAO benutzerDAO = daoFactory.getBenutzerDAO();
        final List<Benutzer> benutzer = benutzerDAO.loadBenutzerForLogin(enteredLogin);
        for (final Benutzer user : benutzer) {
            final String userLogin = user.getLogin();
            final String userPasswd = user.getPasswd();
            if (userLogin.equals(enteredLogin) && userPasswd.equals(enteredPasswd)) {
                VaadinSession.getCurrent().setAttribute(Benutzer.class, user);
                return;
            }
        }
        Notification.show("Login failed..");
    }

}