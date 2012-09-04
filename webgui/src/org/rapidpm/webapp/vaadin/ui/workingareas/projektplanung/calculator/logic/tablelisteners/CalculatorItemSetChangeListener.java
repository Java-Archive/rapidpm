package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.tablelisteners;

import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.CalculatorFieldsComputer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.CalculatorTableComputer;

public class CalculatorItemSetChangeListener implements ItemSetChangeListener {
    private Logger logger = Logger.getLogger(CalculatorItemSetChangeListener.class);

    private Table tabelle;
    private TextField betriebsstdField;
    private TextField betriebsWertField;

    public CalculatorItemSetChangeListener(final Table tabelle, final TextField std,final TextField betriebswert) {
        this.tabelle = tabelle;
        this.betriebsWertField = betriebswert;
        this.betriebsstdField = std;
    }

    @Override
    public void containerItemSetChange(final ItemSetChangeEvent event) {
        logger.debug("jetzt");
        final CalculatorTableComputer tableComp = new CalculatorTableComputer(tabelle);
        tableComp.computeColumns();
        final CalculatorFieldsComputer fieldsComp = new CalculatorFieldsComputer(tabelle);
        fieldsComp.compute();
        betriebsWertField.setValue(fieldsComp.getBetriebsFraAsString());
        betriebsstdField.setValue(fieldsComp.getBetriebsStundeAsString());
    }

}
