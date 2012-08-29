package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tablelisteners;

import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeFieldsComputer;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeTableComputer;

public class StundensaetzeTableItemSetChangeListener implements ItemSetChangeListener {
    private Logger logger = Logger.getLogger("org.apache");

    private Table tabelle;
    private TextField betriebsstdField;
    private TextField betriebsWertField;

    public StundensaetzeTableItemSetChangeListener(Table tabelle, TextField std,
                                                   TextField betriebswert) {
        this.tabelle = tabelle;
        this.betriebsWertField = betriebswert;
        this.betriebsstdField = std;
    }

    @Override
    public void containerItemSetChange(ItemSetChangeEvent event) {
        logger.debug("jetzt");
        final StundensaetzeTableComputer tableComp = new StundensaetzeTableComputer(
                tabelle);
        tableComp.computeColumns();
        final StundensaetzeFieldsComputer fieldsComp = new StundensaetzeFieldsComputer(
                tabelle);
        fieldsComp.compute();
        betriebsWertField.setValue(fieldsComp.getBetriebsFraAsString());
        betriebsstdField.setValue(fieldsComp.getBetriebsStundeAsString());
    }

}
