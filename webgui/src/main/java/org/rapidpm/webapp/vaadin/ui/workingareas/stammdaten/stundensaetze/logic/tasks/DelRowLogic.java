package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;


import com.vaadin.flow.component.notification.Notification;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ButtonComponent;

import javax.persistence.PersistenceException;
import java.util.ResourceBundle;

public class DelRowLogic {
    private ButtonComponent button;
    private StundensaetzeScreen screen;
    //    private DelRowLogicBean bean;
    private ResourceBundle messages;

    public DelRowLogic(final StundensaetzeScreen screen, final ButtonComponent button, final ResourceBundle messages) {
        this.screen = screen;
        this.button = button;
        this.messages = messages;
//        bean = EJBFactory.getEjbInstance(DelRowLogicBean.class);

    }

    public void execute() {
        //Bean aus dem BeanItem
//        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final RessourceGroup ressourceGroup = (RessourceGroup) button.getItemId();

        try{
            final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
            daoFactory.removeTX(ressourceGroup);

            screen.refreshGridAndRelatedContent();
//            screen.getSaveButtonLayout().setVisible(false);
        } catch (final PersistenceException e){
            Notification.show(messages.getString("stdsatz_nodelete"));
        }

    }
}
