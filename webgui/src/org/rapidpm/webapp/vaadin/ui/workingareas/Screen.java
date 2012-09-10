package org.rapidpm.webapp.vaadin.ui.workingareas;

import com.vaadin.ui.VerticalLayout;
import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;

import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Marco
 * Date: 03.08.12
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */
public abstract class Screen extends VerticalLayout {

    protected ProjektBean projektBean;
    protected RessourceGroupsBean ressourceGroupsBean;
    protected ResourceBundle messagesBundle;

    public Screen(MainRoot root){
        this.projektBean = root.getPlanningUnitsBean();
        this.ressourceGroupsBean = root.getRessourceGroupsBean();
        this.messagesBundle = root.getResourceBundle();
    }

    protected abstract void doInternationalization();

    public Screen getScreen() {
        return this;
    }

    public ProjektBean getProjektBean() {
        return projektBean;
    }

    public void setProjektBean(ProjektBean projektBean) {
        this.projektBean = projektBean;
    }

    public RessourceGroupsBean getRessourceGroupsBean() {
        return ressourceGroupsBean;
    }

    public void setRessourceGroupsBean(RessourceGroupsBean ressourceGroupsBean) {
        this.ressourceGroupsBean = ressourceGroupsBean;
    }

    public ResourceBundle getMessagesBundle() {
        return messagesBundle;
    }
}
