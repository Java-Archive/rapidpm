package org.rapidpm.webapp.vaadin.ui.workingareas.suche;

import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 02.04.13
 * Time: 09:20
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class SearchScreenNoCRUD extends Screen {

    private HorizontalLayout outterLayout;
    private VerticalLayout searchBarLayout;
    private HorizontalLayout filterLayout;
    private TextField filterField;
    private Button filterButton;
    private TabSheet categoryTabsheet;
    private HorizontalLayout searchLayout;
    private TextField searchField;
    private Button searchButton;
    private RapidPanel searchBarPanel;
    private RapidPanel searchPanel;
    private VerticalLayout frameLayout;
    private FormLayout filterFieldLayout;


    public SearchScreenNoCRUD(final MainUI ui) {
        super(ui);
        super.getContentLayout().setMargin(false);
        activeVerticalFullScreenSize(true);
        filterFieldLayout = new FormLayout();
        outterLayout = new HorizontalLayout();
        filterField = new TextField("Filter");
        filterFieldLayout.addComponent(filterField);
        filterButton = new Button("go");
        filterLayout = new HorizontalLayout(filterFieldLayout);
        categoryTabsheet = new TabSheet();
        categoryTabsheet.addTab(new Label("<Baum>"), "Standard (Baum)");
        categoryTabsheet.addTab(new Label("<Accordion>"), "Alphabet");
        categoryTabsheet.addTab(new Label("<Liste>"), "nach Beliebtheit");
        categoryTabsheet.addTab(new Label("<?!>"), "...");
        searchBarLayout = new VerticalLayout(filterLayout, categoryTabsheet);
        searchField = new TextField();
        searchField.setSizeFull();
        searchButton = new Button("Go!");
        searchButton.setSizeUndefined();
        searchBarPanel = new RapidPanel();
        searchPanel = new RapidPanel();
        searchBarPanel.setCaption("Suchmuster");
        searchPanel.setCaption("Suche");


        searchLayout = new HorizontalLayout(searchField, searchButton);
        searchLayout.setSizeFull();
        searchLayout.setExpandRatio(searchField, 0.9f);
        searchLayout.setSpacing(true);

        searchBarPanel.addComponent(searchBarLayout);
        searchPanel.addComponent(searchLayout);
        outterLayout = new HorizontalLayout(searchBarPanel, searchPanel);
        outterLayout.setSizeFull();
        searchBarPanel.setSizeFull();
        searchPanel.setSizeFull();
        outterLayout.setExpandRatio(searchBarPanel, 0.2f);
        outterLayout.setExpandRatio(searchPanel, 0.8f);
        Label lbl = new Label("<Suchmaschinen-Logo>");
        lbl.setSizeUndefined();
        frameLayout = new VerticalLayout();
        frameLayout.addComponents(lbl, outterLayout);
        frameLayout.setComponentAlignment(lbl, Alignment.MIDDLE_CENTER);
        frameLayout.setSizeFull();
        frameLayout.setExpandRatio(lbl, 0.05f);
        frameLayout.setExpandRatio(outterLayout, 0.95f);
        setComponents();
        doInternationalization();
    }

    @Override
    public void setComponents() {
        addComponent(frameLayout);
    }

    @Override
    public void doInternationalization() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
