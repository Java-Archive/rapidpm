package org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.uicomponents.filter;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.ZeitauswertungScreen;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 29.10.12
 * Time: 10:29
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class BedingungsZeile extends HorizontalLayout {

    public static final String[] OPERATORS = new String[]{"<",">","<=",">=","=","<>"};

    private ComboBox spalte;
    private ComboBox vergleichsoperator;
    private TextField vergleichswert;

    public BedingungsZeile() {

        spalte = new ComboBox("", Arrays.asList(ZeitauswertungScreen.VISIBLE_COLUMNS));
        vergleichsoperator = new ComboBox("",Arrays.asList(OPERATORS));
        vergleichswert = new TextField();

        addComponent(spalte);
        addComponent(vergleichsoperator);
        addComponent(vergleichswert);

    }

    public ComboBox getSpalte() {
        return spalte;
    }

    public ComboBox getVergleichsoperator() {
        return vergleichsoperator;
    }

    public TextField getVergleichswert() {
        return vergleichswert;
    }
}
