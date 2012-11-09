package org.rapidpm.webapp.vaadin.ui.workingareas.zeiterfassung.uicomponents.filter;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeiterfassung.ZeiterfassungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeiterfassung.uicomponents.filter.listeners.ListSelectImmediateValueChangeListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 29.10.12
 * Time: 10:02
 */
public class FilterPanel extends Panel {

    private VerticalLayout angezeigteSpaltenLayout;
    private VerticalLayout bedingungsZeilenLayout;
    private VerticalLayout sortierLayout;

    private ListSelectImmediateValueChangeListener immediateListener;

    private HorizontalLayout buttonLayout;

    private ListSelect angezeigteSpalten;
    private List<BedingungsZeile> bedingungsZeilen;
    private SortierZeile sortierZeile;
    private CheckBox immediateBox;

    private Button addBedingungsZeileButton;
    private Button goButton;

    private Table tabelle;

    public FilterPanel(final Table tabelle) {
        this.tabelle = tabelle;
        this.setCaption("Filter");
        setSizeUndefined();
        this.setHeight("300px");
        this.setWidth("200px");
        immediateBox = new CheckBox("Immediate", false);
        immediateBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if ((Boolean) event.getProperty().getValue() == true) {
                    angezeigteSpalten.setImmediate(true);
                    goButton.setEnabled(false);
                } else {
                    angezeigteSpalten.setValue(angezeigteSpalten.getVisibleItemIds());
                    angezeigteSpalten.setImmediate(false);
                    goButton.setEnabled(true);
                    System.out.println(angezeigteSpalten.getVisibleItemIds());

                }
            }
        });

        angezeigteSpaltenLayout = new VerticalLayout();
        bedingungsZeilenLayout = new VerticalLayout();
        sortierLayout = new VerticalLayout();


        buildAngezeigteSpaltenSelect();
        buildBedingungsZeilen();
        buildSortierLayout();
        buildButtonLayout();



        buildLayout();
    }

    private void buildButtonLayout() {
        buttonLayout = new HorizontalLayout();
        goButton = new Button("Anwenden");
        goButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final Set<String> chosenColumns = (Set<String>) angezeigteSpalten.getValue();
                for (final String spalte : ZeiterfassungScreen.VISIBLE_COLUMNS) {
                    if (!chosenColumns.contains(spalte)) {
                        tabelle.setColumnCollapsed(spalte, true);
                    }
                }
            }
        });
        buttonLayout.addComponent(goButton);
    }

    private void buildSortierLayout() {
        sortierLayout = new VerticalLayout();
    }


    private void buildBedingungsZeilen() {
        bedingungsZeilen = new ArrayList<>();
        final BedingungsZeile bedingungsZeile = new BedingungsZeile();
        bedingungsZeilen.add(bedingungsZeile);
        bedingungsZeilenLayout.addComponent(bedingungsZeilen.get(0));
        addBedingungsZeileButton = new Button("+");
        addBedingungsZeileButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final BedingungsZeile newBedingungsZeie = new BedingungsZeile();
                bedingungsZeilen.add(newBedingungsZeie);
                bedingungsZeilenLayout.removeAllComponents();
                for (final BedingungsZeile bedingungsZeile1 : bedingungsZeilen) {
                    bedingungsZeilenLayout.addComponent(bedingungsZeile1);
                }
            }
        });
    }

    private void buildAngezeigteSpaltenSelect() {
        angezeigteSpalten = new ListSelect("Angezeigte Spalten", Arrays.asList(ZeiterfassungScreen.VISIBLE_COLUMNS));
        angezeigteSpalten.setMultiSelect(true);
        immediateListener = new ListSelectImmediateValueChangeListener(angezeigteSpalten, tabelle);
        angezeigteSpalten.addValueChangeListener(immediateListener);
        angezeigteSpalten.setValue(angezeigteSpalten.getVisibleItemIds());
        angezeigteSpaltenLayout.addComponent(angezeigteSpalten);
    }

    private void buildLayout() {
        addComponent(immediateBox);
        addComponent(angezeigteSpaltenLayout);
        //addComponent(new Label("-------------"));
        //addComponent(new Label("Bedingungen"));
        //addComponent(addBedingungsZeileButton);
        //addComponent(bedingungsZeilenLayout);
        //addComponent(new Label("-------------"));
        //addComponent(new Label("Sortierung"));
        //addComponent(sortierLayout);
        addComponent(buttonLayout);

    }

    public void activate(boolean b) {
        this.setVisible(b);
    }

}
