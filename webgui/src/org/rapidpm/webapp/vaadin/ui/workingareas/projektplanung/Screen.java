package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung;

import com.vaadin.ui.VerticalLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.ProjektBean;

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
}
