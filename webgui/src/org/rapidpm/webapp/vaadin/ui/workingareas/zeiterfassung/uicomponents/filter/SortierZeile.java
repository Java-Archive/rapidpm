package org.rapidpm.webapp.vaadin.ui.workingareas.zeiterfassung.uicomponents.filter;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;

import java.util.Arrays;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 29.10.12
 * Time: 10:55
 */
public class SortierZeile extends HorizontalLayout {

    private ComboBox sortierSpaltenBox;
    private ComboBox sortierReihenfolgeBox;

    public SortierZeile(final Set<String> selectedColumns){
        sortierSpaltenBox = new ComboBox("", selectedColumns);
        sortierReihenfolgeBox = new ComboBox("", Arrays.asList(new String[]{"asc","desc"}));
        addComponent(sortierSpaltenBox);
        addComponent(sortierReihenfolgeBox);
    }

}
