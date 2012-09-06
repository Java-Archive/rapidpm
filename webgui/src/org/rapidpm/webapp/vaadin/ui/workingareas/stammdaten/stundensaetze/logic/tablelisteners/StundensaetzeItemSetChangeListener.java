package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tablelisteners;

import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeFieldsCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeTableCalculator;

public class StundensaetzeItemSetChangeListener implements ItemSetChangeListener {
    private Logger logger = Logger.getLogger(StundensaetzeItemSetChangeListener.class);

    private Table tabelle;
    private TextField betriebsstdField;
    private TextField betriebsWertField;

    public StundensaetzeItemSetChangeListener(final Table tabelle, final TextField std, final TextField betriebswert) {
        this.tabelle = tabelle;
        this.betriebsWertField = betriebswert;
        this.betriebsstdField = std;
    }

    @Override
    public void containerItemSetChange(final ItemSetChangeEvent event) {
        logger.debug("jetzt");
        final StundensaetzeTableCalculator tableComp = new StundensaetzeTableCalculator(tabelle);
        tableComp.computeColumns();
        final StundensaetzeFieldsCalculator fieldsComp = new StundensaetzeFieldsCalculator(tabelle);
        fieldsComp.compute();
        betriebsWertField.setValue(fieldsComp.getBetriebsFraAsString());
        betriebsstdField.setValue(fieldsComp.getBetriebsStundeAsString());
    }

}
