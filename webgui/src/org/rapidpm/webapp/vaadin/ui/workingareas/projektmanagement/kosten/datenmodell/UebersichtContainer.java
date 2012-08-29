package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.kosten.datenmodell;

import com.vaadin.data.util.BeanItemContainer;
import org.rapidpm.webapp.vaadin.Constants;

import java.io.Serializable;

public class UebersichtContainer extends BeanItemContainer<UebersichtBean> implements Serializable {

    public UebersichtContainer() throws IllegalArgumentException {
        super(UebersichtBean.class);
    }

    public static UebersichtContainer fill() {
        final UebersichtBean row = new UebersichtBean();
        row.setBezeichnung("extern" + Constants.EUR + " / h");
        row.setAushilfe(25.0);
        row.setJuniorProjektmitarbeiter(45.0);
        row.setSeniorProjektmitarbeiter(65.0);
        row.setInternExternProjektManagerScrumMaster(75.0);
        row.setGeschaeftsfuehrer(125.0);
        row.setExterneRessource(90.0);

        final UebersichtBean row2 = new UebersichtBean();
        row2.setBezeichnung("operative Verteilung");
        row2.setAushilfe(86.32);
        row2.setJuniorProjektmitarbeiter(0.0);
        row2.setSeniorProjektmitarbeiter(626.09);
        row2.setInternExternProjektManagerScrumMaster(286.96);
        row2.setGeschaeftsfuehrer(1452.0);
        row2.setExterneRessource(0.0);

        final UebersichtBean row3 = new UebersichtBean();
        row3.setBezeichnung("Summe in %");
        row3.setAushilfe(0.1725);
        row3.setJuniorProjektmitarbeiter(0.0);
        row3.setSeniorProjektmitarbeiter(0.2957);
        row3.setInternExternProjektManagerScrumMaster(0.1355);
        row3.setGeschaeftsfuehrer(0.3963);
        row3.setExterneRessource(0.0);

        final UebersichtBean row4 = new UebersichtBean();
        row4.setBezeichnung("Summe in h");
        row4.setAushilfe(7.0);
        row4.setJuniorProjektmitarbeiter(0.0);
        row4.setSeniorProjektmitarbeiter(12.0);
        row4.setInternExternProjektManagerScrumMaster(5.5);
        row4.setGeschaeftsfuehrer(16.0);
        row4.setExterneRessource(0.0);

        final UebersichtBean row5 = new UebersichtBean();
        row5.setBezeichnung("Summe in" + Constants.EUR);
        row5.setAushilfe(175.0);
        row5.setJuniorProjektmitarbeiter(780.0);
        row5.setSeniorProjektmitarbeiter(412.5);
        row5.setInternExternProjektManagerScrumMaster(2010.42);
        row5.setGeschaeftsfuehrer(0.0);
        row5.setExterneRessource(67.5);

        final UebersichtContainer container = new UebersichtContainer();
        container.addBean(row);
        container.addBean(row2);
        container.addBean(row3);
        container.addBean(row4);
        container.addBean(row5);

        return container;

    }


}
