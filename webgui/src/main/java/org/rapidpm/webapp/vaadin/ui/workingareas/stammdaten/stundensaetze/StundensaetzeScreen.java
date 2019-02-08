package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;
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
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.EditModes;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks.AddRowClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ButtonComponent;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.EditOptionButtonGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ExternalDailyRateEditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ItemClickDependentComponent;

import javax.persistence.PersistenceException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import static org.rapidpm.Constants.DECIMAL_FORMAT;
import static org.rapidpm.Constants.EUR;
import static org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup.*;

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
    private List<RessourceGroup> resourceGroups;
    private EditModes editMode = EditModes.NOEDIT;
    private List<Registration> gridListeners;
    private final Binder<RessourceGroup> binder = new Binder<>(RessourceGroup.class);
//    private StundensaetzeScreenBean screenBean;


    private List<ItemClickDependentComponent> dependentComponents = new ArrayList<>();

    private static final String GESAMTSUMMEN = "Gesamtsummen: ";
    public static final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);

    public StundensaetzeScreen() {
        try {
            final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
            final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO().loadAllEntities();
            resourceGroups = daoFactory.getRessourceGroupDAO().loadAllEntities();
            if (plannedProjects == null || plannedProjects.isEmpty()) {
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

            buildGrid();
            fillFooters();

            optionGroup = new EditOptionButtonGroup(messagesBundle);
            optionGroup.setValue(EditModes.NOEDIT);
            optionGroup.addValueChangeListener(event -> {
                editMode = event.getValue();
                handleEditMode();
            });
            saveButtonLayout.setSpacing(true);
            saveButtonLayout.add(saveButton);

            delRowButton.setEnabled(false);
            delRowButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
                final RessourceGroup ressourceGroup = grid.getSelectedItems().iterator().next();
                try {
                    final DaoFactory daoFactory1 = DaoFactorySingelton.getInstance();
                    daoFactory1.removeTX(ressourceGroup);
                    resourceGroups.remove(ressourceGroup);
                } catch (final PersistenceException e) {
                    Notification.show(messagesBundle.getString("stdsatz_nodelete"));
                }
            });
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
        } catch (final NoProjectsException e) {
            removeAllComponents();
            final NoProjectsScreen noProjectsScreen = new NoProjectsScreen();
            add(noProjectsScreen);
        }
    }

    private void handleEditMode() {
        if (gridListeners == null) {
            gridListeners = new ArrayList<>();
        }
        for (Registration gridListener : gridListeners) {
            gridListener.remove();
        }
        gridListeners.clear();
        switch (editMode) {
            case NOEDIT:
                gridListeners.add(grid.addItemClickListener(event -> delRowButton.setEnabled(!grid.getSelectedItems().isEmpty())));
                grid.getEditor().cancel();
                break;
            case ROWEDIT:
                gridListeners.add(grid.addItemClickListener(event -> grid.getEditor().editItem(event.getItem())));
                break;
            case TABLEEDIT:
                break;
        }
    }

    private void buildGrid() {
        grid = new Grid<>();
        grid.setItems(resourceGroups);
        final ResourceBundle messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
        final Locale locale = messages.getLocale();
        final NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(locale);
        grid.setWidth("100%");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setHeightByRows(true);
        grid.setColumnReorderingAllowed(true);
        Grid.Column<RessourceGroup> column = grid.addColumn(RessourceGroup::getName).setFlexGrow(5).setFooter(GESAMTSUMMEN);
        applyColumnHeaderAndKey(column, NAME);
        column = grid.addColumn(new NumberRenderer<>(RessourceGroup::getBruttoGehalt, currencyInstance)).setTextAlign(ColumnTextAlign.END);
        applyColumnHeaderAndKey(column, BRUTTOGEHALT);
        column = grid.addColumn(RessourceGroup::getHoursPerWeek);
        applyColumnHeaderAndKey(column, HOURS_PER_WEEK);
        column = grid.addColumn(RessourceGroup::getWeeksPerYear);
        applyColumnHeaderAndKey(column, WEEKS_PER_YEAR);
        column = grid.addColumn(RessourceGroup::getTransientHoursPerYear);
        applyColumnHeaderAndKey(column, HOURS_PER_YEAR);
        column = grid.addColumn(new NumberRenderer<>(RessourceGroup::getFacturizable, NumberFormat.getPercentInstance(locale))).setFlexGrow(1);
        applyColumnHeaderAndKey(column, FACTURIZABLE);
        column = grid.addColumn(new NumberRenderer<>(RessourceGroup::getTransientEurosPerHour, currencyInstance))
                .setTextAlign(ColumnTextAlign.END);
        applyColumnHeaderAndKey(column, EUROS_PER_HOUR);
        column = grid.addColumn(new NumberRenderer<>(RessourceGroup::getExternalEurosPerHour, currencyInstance))
                .setTextAlign(ColumnTextAlign.END);
        applyColumnHeaderAndKey(column, EXTERNAL_EUROS_PER_HOUR);
        column = grid.addColumn(new NumberRenderer<>(RessourceGroup::getTransientOperativeEurosPerHour, currencyInstance))
                .setTextAlign(ColumnTextAlign.END);
        applyColumnHeaderAndKey(column, OPERATIVE_EUROS_PER_HOUR);
        column = grid.addColumn(new NumberRenderer<>(RessourceGroup::getTransientBruttoPerMonth, currencyInstance))
                .setTextAlign(ColumnTextAlign.END);
        applyColumnHeaderAndKey(column, BRUTTO_PER_MONTH);
        column = grid.addColumn(RessourceGroup::getPlanAnzahl);
        applyColumnHeaderAndKey(column, PLAN_ANZAHL);
        column = grid.addColumn(new NumberRenderer<>(RessourceGroup::getTransientSumPerMonth, currencyInstance))
                .setTextAlign(ColumnTextAlign.END);
        applyColumnHeaderAndKey(column, SUM_PER_MONTH);
        column = grid.addColumn(new NumberRenderer<>(RessourceGroup::getTransientSumPerDay, currencyInstance))
                .setTextAlign(ColumnTextAlign.END);
        applyColumnHeaderAndKey(column, SUM_PER_DAY);
        final Editor<RessourceGroup> editor = grid.getEditor();
        editor.setBinder(binder);
        buildAndBindField(NAME);
        buildAndBindField(HOURS_PER_WEEK);
        buildAndBindField(WEEKS_PER_YEAR);
        buildAndBindField(PLAN_ANZAHL);
        buildAndBindField(FACTURIZABLE);
        buildAndBindField(EXTERNAL_EUROS_PER_HOUR);
        buildAndBindField(BRUTTOGEHALT);
        handleEditMode();
    }

    private void buildAndBindField(String resourceGroupProperty) {
        final Grid.Column column = grid.getColumnByKey(resourceGroupProperty);
        final TextField field = new TextField();
        switch (resourceGroupProperty) {
            case NAME:
                field.getElement()
                        .addEventListener("keydown", event -> grid.getEditor().cancel())
                        .setFilter("event.key === 'Tab' && event.shiftKey");
                binder.bind(field, resourceGroupProperty);
                break;
            case HOURS_PER_WEEK:
            case WEEKS_PER_YEAR:
            case PLAN_ANZAHL:
                binder.forField(field).withConverter(new StringToIntegerConverter("Integer only")).bind(resourceGroupProperty);
                break;
            case FACTURIZABLE:
            case EXTERNAL_EUROS_PER_HOUR:
            case BRUTTOGEHALT:
//                field.setPattern("[+-]?([0-9]*[.])?[0-9]+");
//                field.setPreventInvalidInput(true);
                binder.forField(field).withConverter(Double::parseDouble, Object::toString).bind(resourceGroupProperty);
                break;
            default:
                return;
        }
        field.addBlurListener(blurEvent -> saveBean(binder.getBean()));
        column.setEditorComponent(field);
    }

    private void saveBean(RessourceGroup bean) {
        DaoFactorySingelton.getInstance().saveOrUpdateTX(bean);
    }

    private void applyColumnHeaderAndKey(Grid.Column<RessourceGroup> column, String key) {
        column.setHeader(messagesBundle.getString(key)).setKey(key);
    }

    public Map<String, Double> calculateTotals(final List<RessourceGroup> resourceGroups) {
        Double sumPerMonthTotal = 0.0;
        Double sumPerDayTotal = 0.0;
        Double betriebsStunde = 0.0;
        Double mindestManntage = 0.0;
        final VaadinSession session = VaadinSession.getCurrent();
        final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
        for (final RessourceGroup ressourceGroup : resourceGroups) {
            sumPerMonthTotal += ressourceGroup.getTransientSumPerMonth();
            sumPerDayTotal += ressourceGroup.getTransientSumPerDay();
        }
        betriebsStunde = sumPerDayTotal / currentProject.getHoursPerWorkingDay();
        mindestManntage = sumPerDayTotal / currentProject.getExternalDailyRate();
        Map<String, Double> totals = new HashMap<>();
        totals.put(SUM_PER_MONTH, sumPerMonthTotal);
        totals.put(SUM_PER_DAY, sumPerDayTotal);
        totals.put(OPERATIVE_HOURS, betriebsStunde);
        totals.put(MIN_WORKDAYS, mindestManntage);
        return totals;
    }

    private void fillFooters() {
        final Map<String, Double> totalsByColumName = calculateTotals(resourceGroups);
        grid.getColumnByKey(SUM_PER_MONTH).setFooter(format.format(totalsByColumName.get(SUM_PER_MONTH)) + EUR);
        grid.getColumnByKey(SUM_PER_DAY).setFooter(format.format(totalsByColumName.get(SUM_PER_DAY)) + EUR);
    }

    public void refreshGridAndRelatedContent() {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final RessourceGroupDAO ressourceGroupDAO = daoFactory.getRessourceGroupDAO();

        final List<RessourceGroup> ressourceGroups = ressourceGroupDAO.loadAllEntities();
        final Map<String, Double> totals = calculateTotals(ressourceGroups);
//        StundensaetzeGridFiller.refreshGrid(ressourceGroups, editMode);

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
//        add(saveButtonLayout);
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
