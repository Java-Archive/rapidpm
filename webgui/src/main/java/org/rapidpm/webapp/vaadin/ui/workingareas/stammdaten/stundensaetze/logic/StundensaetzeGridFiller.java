package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit.EditModes;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import static org.rapidpm.Constants.DECIMAL_FORMAT;
import static org.rapidpm.Constants.EUR;
import static org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup.*;

public class StundensaetzeGridFiller {

    public static final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
    private static final String GESAMTSUMMEN = "Gesamtsummen: ";

    public static Grid<RessourceGroup> recreateGrid(final List<RessourceGroup> resourceGroups, EditModes editMode) {
        final Grid<RessourceGroup> grid = new Grid<>();
        final ResourceBundle messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
        final Locale locale = messages.getLocale();
        final NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(locale);
        final Map<String, Double> totalsByColumName = calculateTotals(resourceGroups);
        for (Grid.Column<RessourceGroup> column : grid.getColumns()) {
            grid.removeColumn(column);
        }
        grid.setWidth("100%");
        grid.setHeightByRows(true);
        grid.setColumnReorderingAllowed(true);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setEnabled(true);
        grid.setItems(resourceGroups);
        Grid.Column<RessourceGroup> nameColumn = grid.addColumn(RessourceGroup::getName).setFlexGrow(5).setHeader(messages.getString(NAME))
                .setFooter(GESAMTSUMMEN);
        Grid.Column<RessourceGroup> bruttoGehaltColumn = grid.addColumn(new NumberRenderer<>(RessourceGroup::getBruttoGehalt, currencyInstance)).setHeader(messages.getString(BRUTTOGEHALT))
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(RessourceGroup::getHoursPerWeek).setHeader(messages.getString(HOURS_PER_WEEK));
        grid.addColumn(RessourceGroup::getWeeksPerYear).setHeader(messages.getString(WEEKS_PER_YEAR));
        grid.addColumn(RessourceGroup::getTransientHoursPerYear).setHeader(messages.getString(HOURS_PER_YEAR));
        grid.addColumn(new NumberRenderer<>(RessourceGroup::getFacturizable, NumberFormat.getPercentInstance(locale))).setFlexGrow(1).setHeader(messages.getString(FACTURIZABLE));
        grid.addColumn(new NumberRenderer<>(RessourceGroup::getTransientEurosPerHour, currencyInstance)).setHeader(messages.getString(EUROS_PER_HOUR))
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new NumberRenderer<>(RessourceGroup::getExternalEurosPerHour, currencyInstance)).setHeader(messages.getString(EXTERNAL_EUROS_PER_HOUR))
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new NumberRenderer<>(RessourceGroup::getTransientOperativeEurosPerHour, currencyInstance)).setHeader(messages.getString(OPERATIVE_EUROS_PER_HOUR))
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new NumberRenderer<>(RessourceGroup::getTransientBruttoPerMonth, currencyInstance)).setHeader(messages.getString(BRUTTO_PER_MONTH))
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(RessourceGroup::getPlanAnzahl).setHeader(messages.getString(PLAN_ANZAHL));
        grid.addColumn(new NumberRenderer<>(RessourceGroup::getTransientSumPerMonth, currencyInstance)).setHeader(messages.getString(SUM_PER_MONTH))
                .setTextAlign(ColumnTextAlign.END)
                .setFooter(format.format(totalsByColumName.get(SUM_PER_MONTH)) + EUR);
        grid.addColumn(new NumberRenderer<>(RessourceGroup::getTransientSumPerDay, currencyInstance)).setHeader(messages.getString(SUM_PER_DAY))
                .setTextAlign(ColumnTextAlign.END)
                .setFooter(format.format(totalsByColumName.get(SUM_PER_DAY)) + EUR);
        switch (editMode) {
            case NOEDIT:
                break;
            case ROWEDIT:
                Binder<RessourceGroup> binder = new Binder<>(RessourceGroup.class);
                Editor<RessourceGroup> editor = grid.getEditor();
                editor.setBinder(binder);
                editor.setBuffered(true);

                Div validationStatus = new Div();
                validationStatus.setId("validation");

                TextField field = new TextField();
                binder.forField(field)
                        .withValidator(name -> !name.isBlank(),
                                "Name required.")
                        .withStatusLabel(validationStatus).bind("name");
                nameColumn.setEditorComponent(field);
                Grid.Column<RessourceGroup> editorColumn = grid.addComponentColumn(person -> {
                    Button edit = new Button("Edit");
                    edit.addClassName("edit");
                    edit.addClickListener(e -> editor.editItem(person));
                    return edit;
                });
                Button save = new Button("Save", e -> editor.save());
                save.addClassName("save");

                Button cancel = new Button("Cancel", e -> editor.cancel());
                cancel.addClassName("cancel");
                grid.getElement().addEventListener("keyup", event -> editor.cancel()).setFilter("event.key === 'Escape' || even.key === 'Esc'");
                Div buttons = new Div(save, cancel);
                editorColumn.setEditorComponent(buttons);
                editor.addSaveListener(saveListener -> Notification.show("SaveListener Executed for " + saveListener.getItem()));
                break;
            case TABLEEDIT:
                break;
        }
        return grid;
    }

    public static Map<String, Double> calculateTotals(final List<RessourceGroup> resourceGroups) {
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
}

