package org.rapidpm.webapp.vaadin.ui.workingareas.zeiterfassung.uicomponents.filter;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeiterfassung.ZeiterfassungScreen;

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

    private HorizontalLayout buttonLayout;

    private ListSelect angezeigteSpalten;
    private List<BedingungsZeile> bedingungsZeilen;
    private SortierZeile sortierZeile;

    private Button addBedingungsZeileButton;
    private Button goButton;

    private Container container;
    private Table tabelle;

    public FilterPanel(final Table tabelle) {
        this.tabelle = tabelle;
        this.setCaption("Filter");
        setSizeUndefined();
        this.setHeight("200px");
        this.setWidth("200px");
        container = tabelle.getContainerDataSource();

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
                Set<String> chosenColumns = (Set<String>)angezeigteSpalten.getValue();
                final List<String> chosenColumnsList = new ArrayList<>(chosenColumns);
                for(final String spalte : ZeiterfassungScreen.VISIBLE_COLUMNS){
                    if(!chosenColumnsList.contains(spalte)){
                        container.removeContainerProperty(spalte);
                    }
                }

//                for(final BedingungsZeile bedingungsZeile : bedingungsZeilen){
//                    switch((String)bedingungsZeile.getSpalte().getValue()){
//                        case UserWorkLog.ASSIGNEE:
//                            for(final UserWorkLog userWorkLog : container.getItemIds()){
//                                if (userWorkLog.getAssignee())
//                            }
//
//                    }
//                    for(final UserWorkLog userWorkLog : container.getItemIds()){
//                        if (userWorkLog.get)
//                    }
//                }

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
                for(final BedingungsZeile bedingungsZeile1 : bedingungsZeilen){
                    bedingungsZeilenLayout.addComponent(bedingungsZeile1);
                }


            }
        });
    }

    private void buildAngezeigteSpaltenSelect() {
        angezeigteSpalten = new ListSelect("Angezeigte Spalten", Arrays.asList(ZeiterfassungScreen.VISIBLE_COLUMNS));
        angezeigteSpalten.setMultiSelect(true);
        angezeigteSpalten.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {

            }
        });
        angezeigteSpalten.select(angezeigteSpalten.getVisibleItemIds());
        angezeigteSpaltenLayout.addComponent(angezeigteSpalten);
    }

    private void buildLayout(){
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

    public void activate(boolean b){
        this.setVisible(b);
    }

    public Table getTabelle() {
        return tabelle;
    }

    public void setTabelle(Table tabelle) {
        this.tabelle = tabelle;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
