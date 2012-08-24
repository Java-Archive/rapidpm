package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Marco
 * Date: 14.08.12
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class ProjektBean {

    private Projekt projekt;

    public ProjektBean() {
        addData();
    }

    private void addData()
    {
        projekt = new Projekt();
        projekt.setProjektName("Projekt 1");

        final ArrayList<PlanningUnitGroup> planningUnitGroups = new ArrayList<>();
        final ArrayList<RessourceGroup> ressourceGroups = new ArrayList<>();
        final RessourceGroup aushilfe = new RessourceGroup();
        final RessourceGroup multiProjektManager = new RessourceGroup();
        final RessourceGroup projektMitarbeiter = new RessourceGroup();
        final RessourceGroup projektLeiter = new RessourceGroup();
        aushilfe.setName("stud. Aushilfskraft");
        multiProjektManager.setName("Multi-Projektmanager");
        projektMitarbeiter.setName("Projektmitarbeiter");
        projektLeiter.setName("Projektleiter");
        ressourceGroups.add(aushilfe);
        ressourceGroups.add(multiProjektManager);
        ressourceGroups.add(projektMitarbeiter);
        ressourceGroups.add(projektLeiter);

        final PlanningUnitGroup vorbereitungen = new PlanningUnitGroup();
        final PlanningUnitGroup projektworkshop = new PlanningUnitGroup();
        final PlanningUnitGroup angebotserstellung = new PlanningUnitGroup();
        final PlanningUnitGroup realisierungMandantengruppe = new PlanningUnitGroup();
        final PlanningUnitGroup realisierungDatenKollektieren = new PlanningUnitGroup();
        final PlanningUnitGroup vorbereitenDesReporting = new PlanningUnitGroup();
        final PlanningUnitGroup projektmanagement = new PlanningUnitGroup();
        final PlanningUnitGroup kommunikation = new PlanningUnitGroup();
        final PlanningUnitGroup abschlussarbeiten = new PlanningUnitGroup();
        final PlanningUnitGroup schulungen = new PlanningUnitGroup();
        vorbereitungen.setPlanningUnitName("Vorbereitungen");
        projektworkshop.setPlanningUnitName("projektworkshop");
        angebotserstellung.setPlanningUnitName("angebotserstellung");
        realisierungMandantengruppe.setPlanningUnitName("Realisierung Mandantengruppe");
        realisierungDatenKollektieren.setPlanningUnitName("Realisierung / Daten kollektieren");
        vorbereitenDesReporting.setPlanningUnitName("Vorbereiten des Reporting");
        projektmanagement.setPlanningUnitName("Projektmanagement");
        kommunikation.setPlanningUnitName("Kommunikation");
        abschlussarbeiten.setPlanningUnitName("abschlussarbeiten");
        schulungen.setPlanningUnitName("schulungen");

        planningUnitGroups.add(vorbereitungen);
        planningUnitGroups.add(projektworkshop);
        planningUnitGroups.add(angebotserstellung);
        planningUnitGroups.add(realisierungMandantengruppe);
        planningUnitGroups.add(realisierungDatenKollektieren);
        planningUnitGroups.add(vorbereitenDesReporting);
        planningUnitGroups.add(projektmanagement);
        planningUnitGroups.add(kommunikation);
        planningUnitGroups.add(abschlussarbeiten);
        planningUnitGroups.add(schulungen);

        for(PlanningUnitGroup planningUnitGroup : planningUnitGroups){
            planningUnitGroup.setPlanningUnitList(new ArrayList<PlanningUnit>());
        }
        //--PlanningUnitGroup: Vorbereitungen mit PlanningUnits füllen (welche mit PlanningUnitElements gefüllt werden)
        final ArrayList<PlanningUnit> planningUnitsVorbereitungen = new ArrayList<>();

        String[] planningUnitsArray = {"Erstkontakt vor Ort","Gesprächsvorbereitung","Präsentation","Gesprächsbestätigung"};
        for(final String planningUnitName : Arrays.asList(planningUnitsArray)){  //für jede unterzeile
            final PlanningUnit planningUnit = new PlanningUnit();
            planningUnit.setPlanningUnitElementName(planningUnitName);
            final ArrayList<PlanningUnitElement> planningUnitElements = new ArrayList<>();
            for(final RessourceGroup ressourceGroup : ressourceGroups)          //für jede zelle
            {
                PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                planningUnitElement.setPlannedDays((int) (Math.random()*10));
                planningUnitElement.setPlannedHours((int) (Math.random()*24));
                planningUnitElement.setPlannedMinutes((int) (Math.random()*60));
                planningUnitElement.setRessourceGroup(ressourceGroup);
                planningUnitElements.add(planningUnitElement);
            }
            planningUnit.setPlanningUnitElementList(planningUnitElements);
            planningUnitsVorbereitungen.add(planningUnit);
        }
        //Erstkontakt vor Ort kinder übergeben
        for(PlanningUnit planningUnit : planningUnitsVorbereitungen){
            if(planningUnit.getPlanningUnitElementName().equals("Erstkontakt vor Ort")){
                ArrayList<PlanningUnit> childPlanningUnits = new ArrayList<>();
                PlanningUnit childPlanningUnit1 = new PlanningUnit();
                childPlanningUnit1.setPlanningUnitElementName("Person A kontaktieren");
                final ArrayList<PlanningUnitElement> planningUnitElements1 = new ArrayList<>();
                for(final RessourceGroup ressourceGroup : ressourceGroups)          //für jede zelle
                {
                    PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                    planningUnitElement.setPlannedDays((int) (Math.random()*10));
                    planningUnitElement.setPlannedHours((int) (Math.random()*24));
                    planningUnitElement.setPlannedMinutes((int) (Math.random()*60));
                    planningUnitElement.setRessourceGroup(ressourceGroup);
                    planningUnitElements1.add(planningUnitElement);
                }
                childPlanningUnit1.setPlanningUnitElementList(planningUnitElements1);

                PlanningUnit childPlanningUnit2 = new PlanningUnit();
                childPlanningUnit2.setPlanningUnitElementName("Person B kontaktieren");
                final ArrayList<PlanningUnitElement> planningUnitElements2 = new ArrayList<>();
                for(final RessourceGroup ressourceGroup : ressourceGroups)          //für jede zelle
                {
                    PlanningUnitElement planningUnitElement = new PlanningUnitElement();
                    planningUnitElement.setPlannedDays((int) (Math.random()*10));
                    planningUnitElement.setPlannedHours((int) (Math.random()*24));
                    planningUnitElement.setPlannedMinutes((int) (Math.random()*60));
                    planningUnitElement.setRessourceGroup(ressourceGroup);
                    planningUnitElements2.add(planningUnitElement);
                }
                childPlanningUnit2.setPlanningUnitElementList(planningUnitElements2);
                childPlanningUnits.add(childPlanningUnit1);
                childPlanningUnits.add(childPlanningUnit2);
                planningUnit.setKindPlanningUnits(childPlanningUnits);

            }
        }
        vorbereitungen.setPlanningUnitList(planningUnitsVorbereitungen);
        //--------

        projekt.setPlanningUnitGroups(planningUnitGroups);

//        addContainerProperty("Aufgabe", String.class, null);
//        addContainerProperty("Aushilfe (min)", Integer.class, null);
//        addContainerProperty("Multiprojektmanager (min)",
//                Integer.class, null);
//        addContainerProperty("Projektmitarbeiter (min)",
//                Integer.class, null);
//        addContainerProperty("Projektleiter (min)", Integer.class,
//                null);
//
//        // oberste ebene
//        Object itemId = addItem();
//        ersteEbeneIds.add((Integer) itemId);
//        getItem(itemId).getItemProperty("Aufgabe")
//                .setValue("Vorbereitungen");
//        getItem(itemId).getItemProperty("Aushilfe (min)")
//                .setValue(null);
//        System.out.println(getItem(itemId));
//        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
//                .setValue(195);
//        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Projektleiter (min)")
//                .setValue(null);
//
//        itemId = addItem();
//        ersteEbeneIds.add((Integer) itemId);
//        getItem(itemId).getItemProperty("Aufgabe")
//                .setValue("Projekt-Workshop");
//        getItem(itemId).getItemProperty("Aushilfe (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
//                .setValue(270);
//        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Projektleiter (min)")
//                .setValue(null);
//
//        itemId = addItem();
//        ersteEbeneIds.add((Integer) itemId);
//        getItem(itemId).getItemProperty("Aufgabe")
//                .setValue("Angebotserstellung");
//        getItem(itemId).getItemProperty("Aushilfe (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
//                .setValue(310);
//        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Projektleiter (min)")
//                .setValue(null);
//
//        itemId = addItem();
//        ersteEbeneIds.add((Integer) itemId);
//        getItem(itemId).getItemProperty("Aufgabe")
//                .setValue("Realisierung Mandantengruppe");
//        getItem(itemId).getItemProperty("Aushilfe (min)")
//                .setValue(300);
//        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
//                .setValue(130);
//        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
//                .setValue(300);
//        getItem(itemId).getItemProperty("Projektleiter (min)")
//                .setValue(null);
//
//        itemId = addItem();
//        ersteEbeneIds.add((Integer) itemId);
//        getItem(itemId).getItemProperty("Aufgabe")
//                .setValue("Realisierung / Daten kollektieren");
//        getItem(itemId).getItemProperty("Aushilfe (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
//                .setValue(60);
//        getItem(itemId).getItemProperty("Projektleiter (min)")
//                .setValue(null);
//
//        itemId = addItem();
//        ersteEbeneIds.add((Integer) itemId);
//        getItem(itemId).getItemProperty("Aufgabe")
//                .setValue("Vorbereiten des Reporting");
//        getItem(itemId).getItemProperty("Aushilfe (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Projektleiter (min)")
//                .setValue(null);
//
//        itemId = addItem();
//        ersteEbeneIds.add((Integer) itemId);
//        getItem(itemId).getItemProperty("Aufgabe")
//                .setValue("Projektmanagement");
//        getItem(itemId).getItemProperty("Aushilfe (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
//                .setValue(120);
//        getItem(itemId).getItemProperty("Projektleiter (min)")
//                .setValue(null);
//
//        itemId = addItem();
//        ersteEbeneIds.add((Integer) itemId);
//        getItem(itemId).getItemProperty("Aufgabe")
//                .setValue("Kommunikation");
//        getItem(itemId).getItemProperty("Aushilfe (min)")
//                .setValue(60);
//        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
//                .setValue(60);
//        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Projektleiter (min)")
//                .setValue(null);
//
//        itemId = addItem();
//        ersteEbeneIds.add((Integer) itemId);
//        getItem(itemId).getItemProperty("Aufgabe")
//                .setValue("Abschlussarbeiten");
//        getItem(itemId).getItemProperty("Aushilfe (min)")
//                .setValue(60);
//        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
//                .setValue(240);
//        getItem(itemId).getItemProperty("Projektleiter (min)")
//                .setValue(null);
//
//        itemId = addItem();
//        ersteEbeneIds.add((Integer) itemId);
//        getItem(itemId).getItemProperty("Aufgabe")
//                .setValue("Schulung");
//        getItem(itemId).getItemProperty("Aushilfe (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Multiprojektmanager (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
//                .setValue(null);
//        getItem(itemId).getItemProperty("Projektleiter (min)")
//                .setValue(null);
//
//        // --------------------
//
//        final Object vorbereitungenId1 = addItem();
//        getItem(vorbereitungenId1).getItemProperty("Aufgabe")
//                .setValue("Erstkontakt vor Ort");
//        getItem(vorbereitungenId1).getItemProperty("Aushilfe (min)")
//                .setValue(null);
//        getItem(vorbereitungenId1)
//                .getItemProperty("Multiprojektmanager (min)").setValue(120);
//        getItem(vorbereitungenId1)
//                .getItemProperty("Projektmitarbeiter (min)").setValue(null);
//        getItem(vorbereitungenId1)
//                .getItemProperty("Projektleiter (min)").setValue(null);
//
//        final Object vorbereitungenId2 = addItem();
//        getItem(vorbereitungenId2).getItemProperty("Aufgabe")
//                .setValue("Gespr\u00E4chsvorbereitung");
//        getItem(vorbereitungenId2).getItemProperty("Aushilfe (min)")
//                .setValue(null);
//        getItem(vorbereitungenId2)
//                .getItemProperty("Multiprojektmanager (min)").setValue(60);
//        getItem(vorbereitungenId2)
//                .getItemProperty("Projektmitarbeiter (min)").setValue(null);
//        getItem(vorbereitungenId2)
//                .getItemProperty("Projektleiter (min)").setValue(null);
//
//        final Object vorbereitungenId3 = addItem();
//        getItem(vorbereitungenId3).getItemProperty("Aufgabe")
//                .setValue("Pr\u00E4sentation");
//        getItem(vorbereitungenId3).getItemProperty("Aushilfe (min)")
//                .setValue(null);
//        getItem(vorbereitungenId3)
//                .getItemProperty("Multiprojektmanager (min)").setValue(null);
//        getItem(vorbereitungenId3)
//                .getItemProperty("Projektmitarbeiter (min)").setValue(null);
//        getItem(vorbereitungenId3)
//                .getItemProperty("Projektleiter (min)").setValue(null);
//
//        final Object vorbereitungenId4 = addItem();
//        getItem(vorbereitungenId4).getItemProperty("Aufgabe")
//                .setValue("Gespr\u00E4chsbest\u00E4tigung");
//        getItem(vorbereitungenId4).getItemProperty("Aushilfe (min)")
//                .setValue(null);
//        getItem(vorbereitungenId4)
//                .getItemProperty("Multiprojektmanager (min)").setValue(15);
//        getItem(vorbereitungenId4)
//                .getItemProperty("Projektmitarbeiter (min)").setValue(null);
//        getItem(vorbereitungenId4)
//                .getItemProperty("Projektleiter (min)").setValue(null);
//        setParent(vorbereitungenId1, ersteEbeneIds.get(0));
//        setParent(vorbereitungenId2, ersteEbeneIds.get(0));
//        setParent(vorbereitungenId3, ersteEbeneIds.get(0));
//        setParent(vorbereitungenId4, ersteEbeneIds.get(0));
//    }
//
//    public ArrayList<Integer> getErsteEbeneIds() {
//        return ersteEbeneIds;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }
}
