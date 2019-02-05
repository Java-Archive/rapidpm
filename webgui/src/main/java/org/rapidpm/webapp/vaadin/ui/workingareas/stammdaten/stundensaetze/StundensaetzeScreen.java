package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
import org.rapidpm.webapp.vaadin.ui.MainAppLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjektFieldGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeGridFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.EditModes;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks.AddRowClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks.DelRowClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ButtonComponent;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.EditOptionButtonGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ExternalDailyRateEditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ItemClickDependentComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.rapidpm.Constants.EUR;
import static org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup.MIN_WORKDAYS;
import static org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup.OPERATIVE_HOURS;
import static org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeGridFiller.format;

@Route(value = "hourlyrates", layout = MainAppLayout.class)
@Caption("Stundensätze")
@Icon(VaadinIcon.MONEY)
public class StundensaetzeScreen extends Screen {

    private VerticalLayout betriebsFieldsLayout = new VerticalLayout();
    private ExternalDailyRateEditableLayout externerTagessatzLayout;
    private Div externerTagessatzPanel = new Div();
    private TextField mindestManntageField;
    private TextField betriebsStundeField;
    private Button addRowButton = new Button("+");
    private ButtonComponent delRowButton = new ButtonComponent("-");
    private VerticalLayout tabellenTasksLayout = new VerticalLayout();
    private Grid<RessourceGroup> grid;
    private Label addDeleteRessourceLabel = new Label();
    private VerticalLayout saveButtonLayout = new VerticalLayout();
    private Button saveButton = new Button();
    private EditOptionButtonGroup optionGroup;
    private EditModes editMode = EditModes.NOEDIT;
//    private StundensaetzeScreenBean screenBean;


    private List<ItemClickDependentComponent> dependentComponents = new ArrayList<>();

    public StundensaetzeScreen() {
        try{
           final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
            final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO().loadAllEntities();
            if(plannedProjects == null || plannedProjects.isEmpty()){
                throw new NoProjectsException();
            }
            betriebsStundeField = new TextField();
            mindestManntageField = new TextField();

            betriebsFieldsLayout.add(betriebsStundeField);
            betriebsFieldsLayout.add(mindestManntageField);

            externerTagessatzLayout = new ExternalDailyRateEditableLayout();
            externerTagessatzPanel.addClickListener((ComponentEventListener<ClickEvent<Div>>) divClickEvent -> {
                externerTagessatzLayout.setFieldGroup(new ProjektFieldGroup(externerTagessatzLayout.getCurrentProject(), messagesBundle));
                externerTagessatzLayout.setExternalDailyRateField((TextField) externerTagessatzLayout.getFieldGroup().getFieldForProperty(PlannedProject.EXTERNALDAILYRATE).orElse(new TextField()));
                externerTagessatzLayout.removeAll();
                externerTagessatzLayout.add(externerTagessatzLayout.getExternalDailyRateField());
                externerTagessatzLayout.getExternalDailyRateField().focus();
            });
            externerTagessatzPanel.add(externerTagessatzLayout);
            externerTagessatzPanel.setSizeUndefined();
            dependentComponents.add(delRowButton);

            grid = new Grid<>();

            optionGroup = new EditOptionButtonGroup(messagesBundle);
            optionGroup.setValue(EditModes.NOEDIT);
            optionGroup.addValueChangeListener(event ->  {
                editMode = event.getValue();
                refreshGridAndRelatedContent();
            });
            saveButtonLayout.setSpacing(true);
            saveButtonLayout.add(saveButton);

            delRowButton.setEnabled(false);
            delRowButton.addClickListener(new DelRowClickListener(this, delRowButton, messagesBundle));
            tabellenTasksLayout.setWidth("500px");
            tabellenTasksLayout.add(addDeleteRessourceLabel);
            final HorizontalLayout addDeleteLayout = new HorizontalLayout();

            addDeleteLayout.add(addRowButton);
            addDeleteLayout.add(delRowButton);
            tabellenTasksLayout.add(addDeleteLayout);
            tabellenTasksLayout.add(optionGroup);
            addRowButton.addClickListener(new AddRowClickListener());

            refreshGridAndRelatedContent();

            doInternationalization();
            setComponents();
        } catch (final NoProjectsException e){
            removeAllComponents();
            final NoProjectsScreen noProjectsScreen = new NoProjectsScreen();
            add(noProjectsScreen);
        }
    }

    public void refreshGridAndRelatedContent() {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final RessourceGroupDAO ressourceGroupDAO = daoFactory.getRessourceGroupDAO();

        final List<RessourceGroup> ressourceGroups = ressourceGroupDAO.loadAllEntities();
        final Map<String, Double> totals = StundensaetzeGridFiller.calculateTotals(ressourceGroups);
        grid = StundensaetzeGridFiller.recreateGrid(ressourceGroups, editMode);

        mindestManntageField.setReadOnly(false);
        betriebsStundeField.setReadOnly(false);

        betriebsStundeField.setValue(format.format(totals.get(OPERATIVE_HOURS)) + EUR);
        mindestManntageField.setValue(format.format(totals.get(MIN_WORKDAYS)));

        mindestManntageField.setReadOnly(true);
        betriebsStundeField.setReadOnly(true);

    }

    @Override
    public void doInternationalization() {
        betriebsStundeField.setLabel(messagesBundle.getString("stdsatz_businesshour"));
        mindestManntageField.setLabel(messagesBundle.getString("stdsatz_businessMinManDays"));
        saveButton.setText(messagesBundle.getString("save"));
//        editModeLabel.setText(messagesBundle.getString("stdsatz_editMode"));
        addDeleteRessourceLabel.setText(messagesBundle.getString("stdsatz_addDelete"));

    }

    @Override
    public void setComponents() {
        add(betriebsFieldsLayout);
//        add(externerTagessatzPanel);
        add(tabellenTasksLayout);
        add(saveButtonLayout);
        add(grid);
    }

    /* getters and setters */

    public Grid getGrid() {
        return grid;
    }

    public VerticalLayout getSaveButtonLayout() {
        return saveButtonLayout;
    }
}
