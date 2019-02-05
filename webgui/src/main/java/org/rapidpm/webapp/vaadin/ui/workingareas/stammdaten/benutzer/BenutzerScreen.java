package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.system.security.*;
import org.rapidpm.persistence.system.security.berechtigungen.Rolle;
import org.rapidpm.persistence.system.security.berechtigungen.RolleDAO;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.FormattedDateStringToDateConverter;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.uicomponents.BenutzerEditor;

import javax.persistence.PersistenceException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class BenutzerScreen extends Screen {

    private Logger logger = Logger.getLogger(BenutzerScreen.class);

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private HorizontalLayout contentLayout = new HorizontalLayout();
    private BenutzerEditor benutzerEditor;
    private ComboBox<Mandantengruppe> mandantenBox;
    private Button removeButton;
    private Button addButton;
    private Button resetButton;
    private Grid<Benutzer> benutzerTable;

    public BenutzerScreen() {
//        setSizeFull();
        contentLayout.setSizeFull();
        contentLayout.setSpacing(true);

//        final BenutzerScreenBean bean = EJBFactory.getEjbInstance(BenutzerScreenBean.class);
//        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final MandantengruppeDAO mandantengruppeDAO = daoFactory.getMandantengruppeDAO();
        final RolleDAO rolleDAO = daoFactory.getRolleDAO();
        final BenutzerGruppeDAO benutzerGruppeDAO = daoFactory.getBenutzerGruppeDAO();
        final BenutzerWebapplikationDAO benutzerWebapplikationDAO = daoFactory.getBenutzerWebapplikationDAO();
        final BenutzerDAO benutzerDAO = daoFactory.getBenutzerDAO();
        final List<Mandantengruppe> mandantengruppen = mandantengruppeDAO.loadAllEntities();
        final List<BenutzerGruppe> benutzerGruppen = benutzerGruppeDAO.loadAllEntities();
        final List<BenutzerWebapplikation> benutzerWebapplikationen = benutzerWebapplikationDAO.loadAllEntities();
        final List<Rolle> rollen = rolleDAO.loadAllEntities();
        final List<Benutzer> benutzer = benutzerDAO.loadAllEntities();
        final VerticalLayout benutzerTableLayout = new VerticalLayout();
        benutzerTableLayout.setSpacing(true);
        contentLayout.add(benutzerTableLayout);

        mandantenBox = new ComboBox<>();
        mandantenBox.setItems(mandantengruppen);
        mandantenBox.setItemLabelGenerator(Mandantengruppe::getMandantengruppe);
//        mandantenBox.setFilteringMode(FilteringMode.CONTAINS);
//        mandantenBox.setImmediate(true);
        mandantenBox.setAllowCustomValue(false);
        mandantenBox.setValue(mandantengruppen.get(0));

        benutzerEditor = new BenutzerEditor(this);
        benutzerEditor.setMandantengruppen(mandantengruppen);
        benutzerEditor.setBenutzerGruppen(benutzerGruppen);
        benutzerEditor.setBenutzerWebapplikationen(benutzerWebapplikationen);
        benutzerEditor.setRollen(rollen);
        contentLayout.add(benutzerEditor);
//        contentLayout.setExpandRatio(benutzerTableLayout, 3);
//        contentLayout.setExpandRatio(benutzerEditor, 1);

//        final BeanItemContainer<Benutzer> benutzerDS = new BeanItemContainer<>(Benutzer.class, benutzer);
        benutzerTable = new Grid();
//        benutzerTable.setImmediate(true);
        benutzerTable.setItems(benutzer);
        benutzerTable.setWidth("100%");
        benutzerTable.setColumns("id", "login", "email", "validFrom", "validUntil", "failedLogins");
//        benutzerTable.setColumnHeaders(new String[]{"ID", "Loginname", "E-Mail", "Gültig von", "Gültig bis",
//                "Fehlgeschlagene Logins"});
        benutzerTable.setSelectionMode(Grid.SelectionMode.SINGLE);
//        benutzerTable.setConverter("validFrom", new FormattedDateStringToDateConverter(DATE_FORMAT));
//        benutzerTable.setConverter("validUntil", new FormattedDateStringToDateConverter(DATE_FORMAT));
        benutzerTableLayout.add(mandantenBox);
        benutzerTableLayout.add(benutzerTable);

        removeButton = new Button();
        removeButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            final Benutzer currentUser = VaadinSession.getCurrent().getAttribute(Benutzer.class);
            final Benutzer selectedBenutzer = benutzerTable.getSelectedItems().iterator().next();

            final boolean isUserDeletingHimself = currentUser.getLogin().equals(selectedBenutzer.getLogin());

            if(!isUserDeletingHimself){
                final DaoFactory daoFactory12 = DaoFactorySingelton.getInstance();
                final List<Benutzer> benutzerFromDB = daoFactory12.getBenutzerDAO().loadBenutzerForLogin
                        (selectedBenutzer.getLogin());
                if(benutzerFromDB != null && !benutzerFromDB.isEmpty()){
                    try{
                        daoFactory12.removeTX(selectedBenutzer);
                        benutzerTable.setItems(benutzer.stream().filter(einBenutzer -> einBenutzer.getId().equals(selectedBenutzer.getId())));
                        benutzerEditor.setVisible(false);
                    } catch (final PersistenceException e){
                        Notification.show(messagesBundle.getString("users_userinuse"));
                    }

                } else {
//                        benutzerTable.removeItem(tableItemId);
                    benutzerEditor.setVisible(false);
                    logger.warn(selectedBenutzer.toString() + "war nur transient vorhanden");
                }

            }
            else{
                Notification.show(messagesBundle.getString("users_selfdelete"));
            }
        });
        removeButton.setEnabled(false);

        resetButton = new Button();
        resetButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            final Benutzer selectedBenutzer = benutzerTable.getSelectedItems().iterator().next();
            final DaoFactory daoFactory1 = DaoFactorySingelton.getInstance();
            daoFactory1.saveOrUpdate(selectedBenutzer);
        });
        resetButton.setEnabled(false);

        benutzerTable.addItemClickListener(new ComponentEventListener<ItemClickEvent<Benutzer>>() {
            @Override
            public void onComponentEvent(ItemClickEvent<Benutzer> benutzerItemClickEvent) {
                final Benutzer selectedBenutzer = benutzerTable.getSelectedItems().iterator().next();
                if (!benutzerTable.getSelectedItems().contains(selectedBenutzer)) {
                    Integer failedLogins = selectedBenutzer.getFailedLogins();
                    boolean isResetable =  failedLogins >= 3;
//                    benutzerEditor.setBenutzerBean(selectedBenutzer);
                    benutzerEditor.setVisible(true);
                    removeButton.setEnabled(true);
                    resetButton.setEnabled(isResetable);
                } else {
                    removeButton.setEnabled(false);
                    benutzerEditor.setVisible(false);
                }
            }
        });

        final HorizontalLayout benutzerButtonsLayout = new HorizontalLayout();
        benutzerButtonsLayout.setSpacing(true);
        benutzerTableLayout.add(benutzerButtonsLayout);

        addButton = new Button();
        addButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            final Benutzer neuerBenutzer = new Benutzer();
            neuerBenutzer.setLogin(messagesBundle.getString("new"));
            neuerBenutzer.setActive(true);
            neuerBenutzer.setBenutzerGruppe(benutzerGruppen.get(0));
            neuerBenutzer.setBenutzerWebapplikation(benutzerWebapplikationen.get(0));
            neuerBenutzer.setEmail(messagesBundle.getString("new")+"@rapidpm.org");
            neuerBenutzer.setFailedLogins(0);
            neuerBenutzer.setMandantengruppe(mandantengruppen.get(0));
            neuerBenutzer.setValidFrom(new Date());
            benutzerTable.select(null);
            benutzerEditor.setVisible(false);
        });

        benutzerButtonsLayout.add(addButton);
        benutzerButtonsLayout.add(removeButton);
        benutzerButtonsLayout.add(resetButton);

        mandantenBox.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<Mandantengruppe>, Mandantengruppe>>) comboBoxMandantengruppeComponentValueChangeEvent -> {
            final Mandantengruppe mandantengruppeFilter = comboBoxMandantengruppeComponentValueChangeEvent.getValue();
//            benutzerDS.removeAllContainerFilters();
//            if (mandantengruppeFilter != null) {
//                benutzerDS.addContainerFilter(new Container.Filter() {
//                    @Override
//                    public boolean passesFilter(final Object o, final Item item) throws UnsupportedOperationException {
//                        final Benutzer b = (Benutzer) o;
//                        return mandantengruppeFilter.equals(b.getMandantengruppe());
//                    }
//
//                    @Override
//                    public boolean appliesToProperty(final Object o) {
//                        return "mandantengruppe".equals(o);
//                    }
//                });
//            }
        });
        setComponents();
        doInternationalization();
    }

    public void setComponents(){
        add(contentLayout);
    }

    @Override
    public void doInternationalization() {
        resetButton.setText(messagesBundle.getString("users_reset"));
        removeButton.setText(messagesBundle.getString("users_remove"));
        addButton.setText(messagesBundle.getString("users_add"));
//        benutzerTable.setText(messagesBundle.getString("users_users"));
        benutzerEditor.doInternationalization();
    }
}
