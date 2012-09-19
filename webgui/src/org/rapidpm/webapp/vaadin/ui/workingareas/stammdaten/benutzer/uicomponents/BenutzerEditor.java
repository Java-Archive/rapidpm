package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.uicomponents;

import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.logging.LoggerQualifier;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.system.security.*;
import org.rapidpm.persistence.system.security.berechtigungen.Berechtigung;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.StammdatenScreensBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.BenutzerScreen;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 12:01
 */
public class BenutzerEditor extends FormLayout {

    private BeanItem<Benutzer> benutzerBean;

    @Inject
    @LoggerQualifier
    private Logger logger;

    @Inject
    private UserTransaction userTransaction;


    //REFAC per CDI ?
//    @Inject
//    private StammdatenScreensBean stammdatenScreenBean;
    private final StammdatenScreensBean stammdatenScreenBean = EJBFactory.getEjbInstance(StammdatenScreensBean
 .class);

    private Collection<Mandantengruppe> mandantengruppen;
    private Collection<BenutzerGruppe> benutzerGruppen;
    private Collection<BenutzerWebapplikation> benutzerWebapplikationen;
    private Collection<Berechtigung> berechtigungen;
//
    private final TextField idTextField;
    private final TextField loginTextField;
    private final PasswordField passwdTextField;
    private final TextField emailTextField;
    private final DateField lastLoginDateField;
    private final ComboBox mandantengruppenSelect;
    private final ComboBox benutzerGruppenSelect;
    private final ComboBox benutzerWebapplikationenSelect;
    private final ListSelect berechtigungenSelect;

    public BenutzerEditor() {
        setCaption("Benutzer-Editor");

        idTextField = new TextField("ID");
        idTextField.setRequired(false);
//        idTextField.setReadOnly(true);
        idTextField.setEnabled(false);
        idTextField.setConverter(Long.class); // vaadin 7
//REFAC        idTextField.addValidator(new IntegerRangeValidator("ungültige ID", 0, Integer.MAX_VALUE));
        addComponent(idTextField);

        loginTextField = new TextField("Login");
        loginTextField.setRequired(true);
        loginTextField.setNullRepresentation("");
        addComponent(loginTextField);

        passwdTextField = new PasswordField("Passwort");
        passwdTextField.setRequired(true);
        passwdTextField.setNullRepresentation("");
        addComponent(passwdTextField);

        emailTextField = new TextField("E-Mail");
        emailTextField.addValidator(new EmailValidator("Ungültige E-Mail-Adresse"));
        emailTextField.setNullRepresentation("@rapidpm.org");
        addComponent(emailTextField);

        lastLoginDateField = new DateField("Letzter Login");
        lastLoginDateField.setDateFormat(BenutzerScreen.DATE_FORMAT.toPattern());
        addComponent(lastLoginDateField);

        mandantengruppenSelect = new ComboBox("Mandantengruppe");
//        mandantengruppenSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
        mandantengruppenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
        mandantengruppenSelect.setItemCaptionPropertyId("mandantengruppe");
        mandantengruppenSelect.setNullSelectionAllowed(false);
        mandantengruppenSelect.setRequired(true);
        mandantengruppenSelect.setFilteringMode(FilteringMode.CONTAINS);
        addComponent(mandantengruppenSelect);

        benutzerGruppenSelect = new ComboBox("Benutzergruppe");
        benutzerGruppenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
        benutzerGruppenSelect.setItemCaptionPropertyId("gruppenname");
        benutzerGruppenSelect.setNullSelectionAllowed(false);
        benutzerGruppenSelect.setRequired(true);
        benutzerGruppenSelect.setFilteringMode(FilteringMode.CONTAINS);
        addComponent(benutzerGruppenSelect);

        benutzerWebapplikationenSelect = new ComboBox("Webapplikation");
        benutzerWebapplikationenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
        benutzerWebapplikationenSelect.setItemCaptionPropertyId("webappName");
        benutzerWebapplikationenSelect.setNullSelectionAllowed(false);
        benutzerWebapplikationenSelect.setRequired(true);
        benutzerWebapplikationenSelect.setFilteringMode(FilteringMode.CONTAINS);
        addComponent(benutzerWebapplikationenSelect);

        berechtigungenSelect = new ListSelect("Berechtigungen");
        berechtigungenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
        berechtigungenSelect.setItemCaptionPropertyId("name");
        berechtigungenSelect.setMultiSelect(true);
        addComponent(berechtigungenSelect);

        addComponent(new Button("Speichern", new Button.ClickListener() {
            private final DaoFactoryBean daoFactoryBean = stammdatenScreenBean.getDaoFactoryBean();

            @Override
            public void buttonClick(final Button.ClickEvent clickEvent) {
                if (benutzerBean == null) {
                    benutzerBean = new BeanItem<>(new Benutzer());
                } else {
                    final BenutzerDAO benutzerDAO = daoFactoryBean.getBenutzerDAO();
                    final EntityManager entityManager = benutzerDAO.getEntityManager();
                    final Benutzer benutzer = benutzerBean.getBean();
                    entityManager.refresh(benutzer);
                }
                boolean valid = true;
                for (final Component component : BenutzerEditor.this.components) {
                    if (component instanceof Validatable) {
                        final Validatable validatable = (Validatable) component;
                        try {
                            validatable.validate();
                        } catch (Validator.InvalidValueException e) {
                            logger.log(Priority.WARN, e.getMessage(), e);
                            valid = false;
                        }
                    }
                }
                if (valid) {
                    final List<Berechtigung> berechtigungenList = new ArrayList<>();
                    final Object berechtigungenSelectValue = berechtigungenSelect.getValue();
                    if (berechtigungenSelectValue instanceof Berechtigung) {
                        berechtigungenList.add((Berechtigung) berechtigungenSelectValue);
                    } else if (berechtigungenSelectValue instanceof Collection) {
                        final Collection<Berechtigung> berechtigungsCollection = (Collection<Berechtigung>) berechtigungenSelectValue;
                        berechtigungenList.addAll(berechtigungsCollection);
                    }

                    // Tabelle aktualisieren
//                    benutzerBean.getItemProperty("id").setValue(Long.parseLong(idTextField.getValue().toString())); // ID wird von der DB verwaltet
                    benutzerBean.getItemProperty("login").setValue(loginTextField.getValue());
                    benutzerBean.getItemProperty("passwd").setValue(passwdTextField.getValue());
                    benutzerBean.getItemProperty("email").setValue(emailTextField.getValue());
                    benutzerBean.getItemProperty("lastLogin").setValue(lastLoginDateField.getValue());
                    //REFAC beheben von detached persistent Beans
                    benutzerBean.getItemProperty("mandantengruppe").setValue(mandantengruppenSelect.getValue());
                    benutzerBean.getItemProperty("benutzerGruppe").setValue(benutzerGruppenSelect.getValue());
                    benutzerBean.getItemProperty("benutzerWebapplikation").setValue(benutzerWebapplikationenSelect.getValue());
                    benutzerBean.getItemProperty("berechtigungen").setValue(berechtigungenList);

                    // in die DB speichern
                    final Benutzer benutzer = benutzerBean.getBean();
                    daoFactoryBean.saveOrUpdate(benutzer);
                } else {
                    Notification.show("Eingaben unvollständig oder ungültig");
//                    getRoot().showNotification();
                    // ein oder mehrere Eingaben sind ungÃ¼ltig
//                    getApplication().getMainWindow().showNotification("Eingaben unvollstÃ¤ndig oder ungÃ¼ltig", Window.Notification.TYPE_TRAY_NOTIFICATION);
                }
            }
        }));
    }

    public Benutzer getBenutzer() {
        return benutzerBean.getBean();
    }

    public void setBenutzerBean(final BeanItem<Benutzer> benutzerBean) {
        if (benutzerBean == null) {
            return;
        }
        this.benutzerBean = benutzerBean;
        final Benutzer benutzer = benutzerBean.getBean();
        if (benutzer.getId() != null) {
            idTextField.setValue(benutzer.getId().toString());
        }
        loginTextField.setValue(benutzer.getLogin());
        passwdTextField.setValue(benutzer.getPasswd());
        emailTextField.setValue(benutzer.getEmail());
        lastLoginDateField.setValue(benutzer.getLastLogin());
        mandantengruppenSelect.select(benutzer.getMandantengruppe());
        benutzerGruppenSelect.select(benutzer.getBenutzerGruppe());
        benutzerWebapplikationenSelect.select(benutzer.getBenutzerWebapplikation());
        berechtigungenSelect.setValue(null); // Selektion aufheben
        if (benutzer.getBerechtigungen() != null) {
            for (final Berechtigung berechtigung : benutzer.getBerechtigungen()) {
                berechtigungenSelect.select(berechtigung);
            }
        }
    }


    public void setMandantengruppen(final Collection<Mandantengruppe> mandantengruppen) {
        this.mandantengruppen = mandantengruppen;
        this.mandantengruppenSelect.setContainerDataSource(new BeanItemContainer<>(Mandantengruppe.class, mandantengruppen));
    }

    public void setBenutzerGruppen(final Collection<BenutzerGruppe> benutzerGruppen) {
        this.benutzerGruppen = benutzerGruppen;
        this.benutzerGruppenSelect.setContainerDataSource(new BeanItemContainer<>(BenutzerGruppe.class, benutzerGruppen));
    }

    public void setBenutzerWebapplikationen(final Collection<BenutzerWebapplikation> benutzerWebapplikationen) {
        this.benutzerWebapplikationen = benutzerWebapplikationen;
        this.benutzerWebapplikationenSelect.setContainerDataSource(new BeanItemContainer<>(BenutzerWebapplikation.class, benutzerWebapplikationen));
    }

    public void setBerechtigungen(final Collection<Berechtigung> berechtigungen) {
        this.berechtigungen = berechtigungen;
        this.berechtigungenSelect.setContainerDataSource(new BeanItemContainer<>(Berechtigung.class, berechtigungen));
    }

}
