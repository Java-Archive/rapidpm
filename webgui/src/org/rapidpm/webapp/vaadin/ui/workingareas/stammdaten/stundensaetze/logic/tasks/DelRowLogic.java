package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;


import com.vaadin.ui.Notification;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ButtonComponent;

import java.util.ResourceBundle;

public class DelRowLogic {
    private ButtonComponent button;
    private StundensaetzeScreen screen;
    private DelRowLogicBean bean;
    private ResourceBundle messages;

    public DelRowLogic(final StundensaetzeScreen screen, final ButtonComponent button, final ResourceBundle messages) {
        this.screen = screen;
        this.button = button;
        this.messages = messages;
        bean = EJBFactory.getEjbInstance(DelRowLogicBean.class);

    }

    public void execute() {

        try{
        //Bean aus dem BeanItem
        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final RessourceGroup ressourceGroup = (RessourceGroup) button.getItemId();

        //transiente RessourceGroup in DB löschen
        baseDaoFactoryBean.remove(ressourceGroup);

        screen.generateTableAndCalculate();
        screen.getSaveButtonLayout().setVisible(false);
        }catch(Exception e){
            Notification.show(messages.getString("stdsatz_nodelete"));
        }
    }
}
