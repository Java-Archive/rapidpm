package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.system.security.*;
import org.rapidpm.persistence.system.security.berechtigungen.Berechtigung;
import org.rapidpm.persistence.system.security.berechtigungen.BerechtigungDAO;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.StammdatenScreensBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.uicomponents.BenutzerEditor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 09:43
 */
public class BenutzerScreen extends Screen {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private HorizontalLayout contentLayout = new HorizontalLayout();

    public BenutzerScreen(MainUI ui) {
        super(ui);
        setSizeFull();
        contentLayout.setSizeFull();
        contentLayout.setSpacing(true);

        final StammdatenScreensBean bean = EJBFactory.getEjbInstance(StammdatenScreensBean.class);
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
        contentLayout.addComponent(benutzerTableLayout);

        final ComboBox mandantenBox = new ComboBox("Mandanten", new BeanItemContainer<>(Mandantengruppe.class,
                mandantengruppen));
        mandantenBox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        mandantenBox.setItemCaptionPropertyId("mandantengruppe");
        mandantenBox.setFilteringMode(FilteringMode.CONTAINS);
        mandantenBox.setImmediate(true);

        final BenutzerEditor benutzerEditor = new BenutzerEditor();
        benutzerEditor.setMandantengruppen(mandantengruppen);
        benutzerEditor.setBenutzerGruppen(benutzerGruppen);
        benutzerEditor.setBenutzerWebapplikationen(benutzerWebapplikationen);
        benutzerEditor.setBerechtigungen(berechtigungen);
        contentLayout.addComponent(benutzerEditor);

        contentLayout.setExpandRatio(benutzerTableLayout, 3);
        contentLayout.setExpandRatio(benutzerEditor, 1);

        final BeanItemContainer<Benutzer> benutzerDS = new BeanItemContainer<>(Benutzer.class, benutzer);
        final Table benutzerTable = new Table("Benutzer", benutzerDS);
        benutzerTable.setWidth(100, Unit.PERCENTAGE);
        benutzerTable.setVisibleColumns(new String[]{"id", "login", "email", "validUntil"});
        benutzerTable.setColumnHeaders(new String[]{"ID", "Loginname", "E-Mail", "GÃ¼ltig Bis"});
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
        benutzerTableLayout.addComponent(mandantenBox);
        benutzerTableLayout.addComponent(benutzerTable);

        final Button removeBenutzerButton = new Button("Benutzer lÃ¶schen", new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent clickEvent) {
                // TODO LÃ¶schbestÃ¤tigung

                benutzerTable.removeItem(benutzerTable.getValue());
            }
        });
        removeBenutzerButton.setEnabled(false);

        benutzerTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
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

        benutzerButtonsLayout.addComponent(new Button("Benutzer hinzufÃ¼gen", new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent clickEvent) {
                final BeanItem<Benutzer> benutzerBean = benutzerDS.addBean(new Benutzer());
                benutzerEditor.setBenutzerBean(benutzerBean);
            }
        }));

        benutzerButtonsLayout.addComponent(removeBenutzerButton);

        mandantenBox.addValueChangeListener(new Property.ValueChangeListener() {
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
        setComponents();
    }

    public void setComponents(){
        addComponent(contentLayout);
    }

/*    public Collection<Benutzer> createDemoData() {
        final ArrayList<Benutzer> benutzer = new ArrayList<>();

        final Mandantengruppe mandantengruppeA = new Mandantengruppe(1L, "Mandant A");
        final Mandantengruppe mandantengruppeB = new Mandantengruppe(2L, "Mandant B");
        final Mandantengruppe mandantengruppeC = new Mandantengruppe(3L, "Mandant C");

        final Berechtigung berechtigungLesen = new Berechtigung(1L, "Lesen");
        final Berechtigung berechtigungSchreiben = new Berechtigung(2L, "Schreiben");

        final Benutzer ruppert = new Benutzer(1L, "sven.ruppert", "ruppert", "sven.ruppert@neoscio.de");
        ruppert.setMandantengruppe(mandantengruppeA);
        ruppert.addBerechtigungen(berechtigungLesen, berechtigungSchreiben);
        benutzer.add(ruppert);

        final Benutzer vos = new Benutzer(2L, "alexander.vos", "vos", "alexander.vos@neoscio.de");
        vos.setMandantengruppe(mandantengruppeA);
        vos.addBerechtigungen(berechtigungLesen);
        benutzer.add(vos);

        final Benutzer gast = new Benutzer(3L, "gast", "gast", "gast@neoscio.de");
        gast.setMandantengruppe(mandantengruppeB);
        benutzer.add(gast);

        final Benutzer benutzerc = new Benutzer(3L, "Benutzer C", "benutzerc", "benutzerc@neoscio.de");
        benutzerc.setMandantengruppe(mandantengruppeC);
        benutzerc.addBerechtigungen(berechtigungLesen);
        benutzer.add(benutzerc);

        return benutzer;
    }*/

    @Override
    protected void doInternationalization() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
