package org.rapidpm.webapp.vaadin.ui.workingareas.suche;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;

import static org.rapidpm.Constants.PATTY_LOGO;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 02.04.13
 * Time: 09:20
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ResultScreen extends Screen {

    private final Image pattyImage = new Image("", new ThemeResource(PATTY_LOGO));

    private HorizontalLayout outterLayout;
    private VerticalLayout searchBarLayout;
    private HorizontalLayout filterLayout;
    private TextField filterField;
    private Button filterButton;
    private TabSheet categoryTabsheet;
    //private HorizontalLayout searchLayout;
    //private TextField searchField;
    //private Button searchButton;
    private RapidPanel searchBarPanel;
    private RapidPanel searchPanel;
    private VerticalLayout frameLayout;
    private FormLayout filterFieldLayout;
    private VerticalLayout resultLayout;
    private HorizontalLayout crudLayout;
    private Button addButton;
    private Button deleteButton;
    private Button editButton;

    private Tree tree;


    public ResultScreen(final MainUI ui) {
        super(ui);

        fillTree();
        addButton = new Button("+");
        deleteButton = new Button("-");
        editButton = new Button("o");
        addButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent clickEvent) {
                AddSearchPatternWindow window = new AddSearchPatternWindow();
                ui.addWindow(window);
            }
        });
        crudLayout = new HorizontalLayout(addButton, editButton, deleteButton);

        super.getContentLayout().setMargin(false);
        activeVerticalFullScreenSize(true);
        filterFieldLayout = new FormLayout();
        outterLayout = new HorizontalLayout();
        filterField = new TextField("Filter");
        filterFieldLayout.addComponent(filterField);
        filterButton = new Button("go");
        filterLayout = new HorizontalLayout(filterFieldLayout);
        categoryTabsheet = new TabSheet();
        categoryTabsheet.addTab(tree, "Standard (Baum)");
        categoryTabsheet.addTab(new Label("<Accordion>"), "Alphabet");
        categoryTabsheet.addTab(new Label("<Liste>"), "nach Beliebtheit");
        categoryTabsheet.addTab(new Label("<?!>"), "...");
        searchBarLayout = new VerticalLayout(filterLayout, crudLayout, categoryTabsheet);
        searchBarPanel = new RapidPanel();
        searchPanel = new RapidPanel();
        searchBarPanel.setCaption("Suchmuster");
        searchPanel.setCaption("Ergebnis");

        resultLayout = new VerticalLayout();
        resultLayout.addComponent(new Label("Ihre Suche nach \"Äquator\" unter Verwendung des Suchmusters \"Saturn\" " +
                "ergab folgende 10 Treffer:"));
        for(int i=0; i<10; i++){
            Button button = new Button("Treffer "+(i+1));
            button.setStyleName("link");
            resultLayout.addComponent(button);
            resultLayout.addComponent(new Label(""));
        }
        resultLayout.setSpacing(true);

        searchBarPanel.addComponent(searchBarLayout);
        searchPanel.addComponent(resultLayout);
        outterLayout = new HorizontalLayout(searchBarPanel, searchPanel);
        outterLayout.setSizeFull();
        searchBarPanel.setSizeFull();
        searchPanel.setSizeFull();
        outterLayout.setExpandRatio(searchBarPanel, 0.2f);
        outterLayout.setExpandRatio(searchPanel, 0.8f);
        outterLayout.setSizeFull();
        frameLayout = new VerticalLayout();
        frameLayout.addComponents(pattyImage, outterLayout);
        frameLayout.setComponentAlignment(pattyImage, Alignment.TOP_CENTER);
        frameLayout.setSizeFull();
        frameLayout.setExpandRatio(pattyImage, 0.13f);
        frameLayout.setExpandRatio(outterLayout, 0.87f);
        setComponents();
        doInternationalization();
    }

    private void fillTree() {
        final Object[][] planets = new Object[][]{
                new Object[]{"Mercury"},
                new Object[]{"Venus"},
                new Object[]{"Earth", "The Moon"},
                new Object[]{"Mars", "Phobos", "Deimos"},
                new Object[]{"Jupiter", "Io", "Europa", "Ganymedes",
                        "Callisto"},
                new Object[]{"Saturn",  "Titan", "Tethys", "Dione",
                        "Rhea", "Iapetus"},
                new Object[]{"Uranus",  "Miranda", "Ariel", "Umbriel",
                        "Titania", "Oberon"},
                new Object[]{"Neptune", "Triton", "Proteus", "Nereid",
                        "Larissa"}};

/* Add planets as root items in the tree. */
        tree = new Tree();
        for (int i=0; i<planets.length; i++) {
            String planet = (String) (planets[i][0]);
            tree.addItem(planet);

            if (planets[i].length == 1) {
                // The planet has no moons so make it a leaf.
                tree.setChildrenAllowed(planet, false);
            } else {
                // Add children (moons) under the planets.
                for (int j=1; j<planets[i].length; j++) {
                    String moon = (String) planets[i][j];

                    // Add the item as a regular item.
                    tree.addItem(moon);

                    // Set it to be a child.
                    tree.setParent(moon, planet);

                    // Make the moons look like leaves.
                    tree.setChildrenAllowed(moon, false);
                }

                // Expand the subtree.
                tree.expandItemsRecursively(planet);
            }
        }
        tree.setValue("Saturn");
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
