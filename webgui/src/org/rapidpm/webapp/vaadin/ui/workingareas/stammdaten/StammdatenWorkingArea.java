package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.prj.stammdaten.DaoFactoryBean;
import org.rapidpm.persistence.system.security.*;
import org.rapidpm.persistence.system.security.berechtigungen.Berechtigung;
import org.rapidpm.persistence.system.security.berechtigungen.BerechtigungDAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 09:43
 */
public class StammdatenWorkingArea extends HorizontalLayout {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public StammdatenWorkingArea() {
        setSizeFull();
        setSpacing(true);

        final StammdatenWorkingAreaBean bean = EJBFactory.getEjbInstance(StammdatenWorkingAreaBean.class);
        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final MandantengruppeDAO mandantengruppeDAO = baseDaoFactoryBean.getMandantengruppeDAO();
        final BerechtigungDAO berechtigungDAO = baseDaoFactoryBean.getBerechtigungDAO();
        final BenutzerGruppeDAO benutzerGruppeDAO = baseDaoFactoryBean.getBenutzerGruppeDAO();
        final BenutzerWebapplikationDAO benutzerWebapplikationDAO = baseDaoFactoryBean.getBenutzerWebapplikationDAO();
        final BenutzerDAO benutzerDAO = baseDaoFactoryBean.getBenutzerDAO();
        final List<Mandantengruppe> mandantengruppen = mandantengruppeDAO.loadAllEntities();
        final List<BenutzerGruppe> benutzerGruppen = benutzerGruppeDAO.loadAllEntities();
        final List<BenutzerWebapplikation> benutzerWebapplikationen = benutzerWebapplikationDAO.loadAllEntities();
        final List<Berechtigung> berechtigungen = berechtigungDAO.loadAllEntities();
        final List<Benutzer> benutzer = benutzerDAO.loadAllEntities();

        final VerticalLayout benutzerTableLayout = new VerticalLayout();
        benutzerTableLayout.setSpacing(true);
        addComponent(benutzerTableLayout);

        final Select mandantenSelect = new Select("Mandanten", new BeanItemContainer<>(Mandantengruppe.class, mandantengruppen));
        mandantenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        mandantenSelect.setItemCaptionPropertyId("mandantengruppe");
        mandantenSelect.setFilteringMode(Select.FILTERINGMODE_CONTAINS);
        mandantenSelect.setImmediate(true);

        final BenutzerEditor benutzerEditor = new BenutzerEditor();
        benutzerEditor.setMandantengruppen(mandantengruppen);
        benutzerEditor.setBenutzerGruppen(benutzerGruppen);
        benutzerEditor.setBenutzerWebapplikationen(benutzerWebapplikationen);
        benutzerEditor.setBerechtigungen(berechtigungen);
        addComponent(benutzerEditor);

        final BeanItemContainer<Benutzer> benutzerDS = new BeanItemContainer<>(Benutzer.class, benutzer);
        final Table benutzerTable = new Table("Benutzer", benutzerDS);
        benutzerTable.setWidth(100, Unit.PERCENTAGE);
        benutzerTable.setVisibleColumns(new String[]{"id", "login", "email", "validUntil"});
        benutzerTable.setColumnHeaders(new String[]{"ID", "Loginname", "E-Mail", "Gültig Bis"});
        benutzerTable.setSelectable(true);
//        benutzerTable.setEditable(true);
        benutzerTable.addGeneratedColumn("validUntil", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table table, final Object o, final Object o1) {
                final Benutzer b = (Benutzer) o;
                final Date lastLogin = b.getLastLogin();
                return new Label(lastLogin != null ? DATE_FORMAT.format(lastLogin) : "-");
            }
        });
        benutzerTableLayout.addComponent(mandantenSelect);
        benutzerTableLayout.addComponent(benutzerTable);

        final Button removeBenutzerButton = new Button("Benutzer löschen", new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent clickEvent) {
                // TODO Löschbestätigung

                benutzerTable.removeItem(benutzerTable.getValue());
            }
        });
        removeBenutzerButton.setEnabled(false);

        benutzerTable.addListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(final ItemClickEvent itemClickEvent) {
                final BeanItem<Benutzer> item = (BeanItem<Benutzer>) itemClickEvent.getItem();
                if (!benutzerTable.isSelected(itemClickEvent.getItemId())) {
                    benutzerEditor.setBenutzerBean(item);
                    removeBenutzerButton.setEnabled(true);
                } else {
                    removeBenutzerButton.setEnabled(false);
                }
            }
        });

        final HorizontalLayout benutzerButtonsLayout = new HorizontalLayout();
        benutzerButtonsLayout.setSpacing(true);
        benutzerTableLayout.addComponent(benutzerButtonsLayout);

        benutzerButtonsLayout.addComponent(new Button("Benutzer hinzufügen", new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent clickEvent) {
                final BeanItem<Benutzer> benutzerBean = benutzerDS.addBean(new Benutzer());
                benutzerEditor.setBenutzerBean(benutzerBean);
            }
        }));

        benutzerButtonsLayout.addComponent(removeBenutzerButton);

        mandantenSelect.addListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(final Property.ValueChangeEvent valueChangeEvent) {
                final Mandantengruppe mandantengruppeFilter = (Mandantengruppe) valueChangeEvent.getProperty().getValue();
                benutzerDS.removeAllContainerFilters();
                if (mandantengruppeFilter != null) {
                    benutzerDS.addContainerFilter(new Container.Filter() {
                        @Override
                        public boolean passesFilter(final Object o, final Item item) throws UnsupportedOperationException {
                            final Benutzer b = (Benutzer) o;
                            return mandantengruppeFilter.equals(b.getMandantengruppe());
                        }

                        @Override
                        public boolean appliesToProperty(final Object o) {
                            return "mandantengruppe".equals(o);
                        }
                    });
                }
            }
        });
    }

/*    public Collection<Benutzer> createDemoData() {
        final ArrayList<Benutzer> benutzer = new ArrayList<>();

        final Mandantengruppe mandantengruppeA = new Mandantengruppe(1L, "Mandant A");
        final Mandantengruppe mandantengruppeB = new Mandantengruppe(2L, "Mandant B");
        final Mandantengruppe mandantengruppeC = new Mandantengruppe(3L, "Mandant C");

        final Berechtigung berechtigungLesen = new Berechtigung(1L, "Lesen");
        final Berechtigung berechtigungSchreiben = new Berechtigung(2L, "Schreiben");

        final Benutzer ruppert = new Benutzer(1L, "sven.ruppert", "ruppert", "sven.ruppert@rapidpm.org");
        ruppert.setMandantengruppe(mandantengruppeA);
        ruppert.addBerechtigungen(berechtigungLesen, berechtigungSchreiben);
        benutzer.add(ruppert);

        final Benutzer vos = new Benutzer(2L, "alexander.vos", "vos", "alexander.vos@RapidPM.de");
        vos.setMandantengruppe(mandantengruppeA);
        vos.addBerechtigungen(berechtigungLesen);
        benutzer.add(vos);

        final Benutzer gast = new Benutzer(3L, "gast", "gast", "gast@RapidPM.de");
        gast.setMandantengruppe(mandantengruppeB);
        benutzer.add(gast);

        final Benutzer benutzerc = new Benutzer(3L, "Benutzer C", "benutzerc", "benutzerc@RapidPM.de");
        benutzerc.setMandantengruppe(mandantengruppeC);
        benutzerc.addBerechtigungen(berechtigungLesen);
        benutzer.add(benutzerc);

        return benutzer;
    }*/
}
