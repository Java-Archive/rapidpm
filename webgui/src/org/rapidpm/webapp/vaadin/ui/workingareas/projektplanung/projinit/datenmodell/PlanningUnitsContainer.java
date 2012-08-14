package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell;

import com.vaadin.data.util.HierarchicalContainer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Marco
 * Date: 14.08.12
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class PlanningUnitsContainer extends HierarchicalContainer implements Serializable {

    private ArrayList<Integer> ersteEbeneIds = new ArrayList<>();

    public PlanningUnitsContainer() {
        addData();
    }

    private void addData()
    {
        addContainerProperty("Aufgabe", String.class, null);
        addContainerProperty("Aushilfe (min)", Integer.class, null);
        addContainerProperty("Multiprojektmanager (min)",
                Integer.class, null);
        addContainerProperty("Projektmitarbeiter (min)",
                Integer.class, null);
        addContainerProperty("Projektleiter (min)", Integer.class,
                null);

        // oberste ebene
        Object itemId = addItem();
        ersteEbeneIds.add((Integer) itemId);
        getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Vorbereitungen");
        getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(null);
        System.out.println(getItem(itemId));
        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(195);
        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = addItem();
        ersteEbeneIds.add((Integer) itemId);
        getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Projekt-Workshop");
        getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(270);
        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = addItem();
        ersteEbeneIds.add((Integer) itemId);
        getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Angebotserstellung");
        getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(310);
        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = addItem();
        ersteEbeneIds.add((Integer) itemId);
        getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Realisierung Mandantengruppe");
        getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(300);
        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(130);
        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(300);
        getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = addItem();
        ersteEbeneIds.add((Integer) itemId);
        getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Realisierung / Daten kollektieren");
        getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(60);
        getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = addItem();
        ersteEbeneIds.add((Integer) itemId);
        getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Vorbereiten des Reporting");
        getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = addItem();
        ersteEbeneIds.add((Integer) itemId);
        getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Projektmanagement");
        getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(120);
        getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = addItem();
        ersteEbeneIds.add((Integer) itemId);
        getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Kommunikation");
        getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(60);
        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(60);
        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = addItem();
        ersteEbeneIds.add((Integer) itemId);
        getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Abschlussarbeiten");
        getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(60);
        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(240);
        getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = addItem();
        ersteEbeneIds.add((Integer) itemId);
        getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Schulung");
        getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(null);
        getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        // --------------------

        final Object vorbereitungenId1 = addItem();
        getItem(vorbereitungenId1).getItemProperty("Aufgabe")
                .setValue("Erstkontakt vor Ort");
        getItem(vorbereitungenId1).getItemProperty("Aushilfe (min)")
                .setValue(null);
        getItem(vorbereitungenId1)
                .getItemProperty("Multiprojektmanager (min)").setValue(120);
        getItem(vorbereitungenId1)
                .getItemProperty("Projektmitarbeiter (min)").setValue(null);
        getItem(vorbereitungenId1)
                .getItemProperty("Projektleiter (min)").setValue(null);

        final Object vorbereitungenId2 = addItem();
        getItem(vorbereitungenId2).getItemProperty("Aufgabe")
                .setValue("Gespr\u00E4chsvorbereitung");
        getItem(vorbereitungenId2).getItemProperty("Aushilfe (min)")
                .setValue(null);
        getItem(vorbereitungenId2)
                .getItemProperty("Multiprojektmanager (min)").setValue(60);
        getItem(vorbereitungenId2)
                .getItemProperty("Projektmitarbeiter (min)").setValue(null);
        getItem(vorbereitungenId2)
                .getItemProperty("Projektleiter (min)").setValue(null);

        final Object vorbereitungenId3 = addItem();
        getItem(vorbereitungenId3).getItemProperty("Aufgabe")
                .setValue("Pr\u00E4sentation");
        getItem(vorbereitungenId3).getItemProperty("Aushilfe (min)")
                .setValue(null);
        getItem(vorbereitungenId3)
                .getItemProperty("Multiprojektmanager (min)").setValue(null);
        getItem(vorbereitungenId3)
                .getItemProperty("Projektmitarbeiter (min)").setValue(null);
        getItem(vorbereitungenId3)
                .getItemProperty("Projektleiter (min)").setValue(null);

        final Object vorbereitungenId4 = addItem();
        getItem(vorbereitungenId4).getItemProperty("Aufgabe")
                .setValue("Gespr\u00E4chsbest\u00E4tigung");
        getItem(vorbereitungenId4).getItemProperty("Aushilfe (min)")
                .setValue(null);
        getItem(vorbereitungenId4)
                .getItemProperty("Multiprojektmanager (min)").setValue(15);
        getItem(vorbereitungenId4)
                .getItemProperty("Projektmitarbeiter (min)").setValue(null);
        getItem(vorbereitungenId4)
                .getItemProperty("Projektleiter (min)").setValue(null);
        setParent(vorbereitungenId1, ersteEbeneIds.get(0));
        setParent(vorbereitungenId2, ersteEbeneIds.get(0));
        setParent(vorbereitungenId3, ersteEbeneIds.get(0));
        setParent(vorbereitungenId4, ersteEbeneIds.get(0));
    }

    public ArrayList<Integer> getErsteEbeneIds() {
        return ersteEbeneIds;
    }
}
