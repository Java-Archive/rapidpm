package org.rapidpm.webapp.vaadin.ui.workingareas;

import com.vaadin.ui.VerticalLayout;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.webapp.vaadin.BaseUI;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.ProjektmanagementScreensBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.StammdatenScreensBean;

import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Marco
 * Date: 03.08.12
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */
public abstract class Screen extends VerticalLayout implements Internationalizationable,Componentssetable {

    protected ProjektBean projektBean;
    protected ProjektmanagementScreensBean projektmanagementScreensBean;
    protected StammdatenScreensBean stammdatenScreensBean;
    protected ResourceBundle messagesBundle;

    public Screen(MainUI ui){
        projektmanagementScreensBean = EJBFactory.getEjbInstance(ProjektmanagementScreensBean.class);
        stammdatenScreensBean = EJBFactory.getEjbInstance(StammdatenScreensBean.class);
        this.messagesBundle = ui.getResourceBundle();
    }

    public Screen(BaseUI ui){
        projektmanagementScreensBean = EJBFactory.getEjbInstance(ProjektmanagementScreensBean.class);
        stammdatenScreensBean = EJBFactory.getEjbInstance(StammdatenScreensBean.class);
        this.messagesBundle = ui.getResourceBundle();
        this.projektBean = ui.getProjektBean();
    }

    public Screen getScreen() {
        return this;
    }

    public ProjektBean getProjektBean() {
        return projektBean;
    }

    public void setProjektBean(ProjektBean projektBean) {
        this.projektBean = projektBean;
    }

    public ResourceBundle getMessagesBundle() {
        return messagesBundle;
    }

    public ProjektmanagementScreensBean getProjektmanagementScreensBean() {
        return projektmanagementScreensBean;
    }

    public StammdatenScreensBean getStammdatenScreensBean() {
        return stammdatenScreensBean;
    }
}
