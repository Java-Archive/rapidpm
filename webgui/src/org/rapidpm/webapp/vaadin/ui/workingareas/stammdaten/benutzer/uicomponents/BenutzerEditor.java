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
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.BenutzerScreen;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 12:01
 */
public class BenutzerEditor extends FormLayout implements Internationalizationable{

    private BeanItem<Benutzer> benutzerBean;

    @Inject
    @LoggerQualifier
    private Logger logger;

    @Inject
    private UserTransaction userTransaction;


    //REFAC per CDI ?
//    @Inject
//    private StammdatenScreensBean stammdatenScreenBean;
    private final BenutzerEditorBean bean = EJBFactory.getEjbInstance(BenutzerEditorBean.class);

    private Collection<Mandantengruppe> mandantengruppen;
    private Collection<BenutzerGruppe> benutzerGruppen;
    private Collection<BenutzerWebapplikation> benutzerWebapplikationen;
    private Collection<Berechtigung> berechtigungen;
//
    private TextField idTextField;
    private TextField loginTextField;
    private PasswordField passwdTextField;
    private TextField emailTextField;
    private DateField validFromDateField;
    private DateField validUntilDateFiled;
    private DateField lastLoginDateField;
    private ComboBox mandantengruppenSelect;
    private ComboBox benutzerGruppenSelect;
    private ComboBox benutzerWebapplikationenSelect;
    private ListSelect berechtigungenSelect;
    private CheckBox isActiveCheckbox;
    private CheckBox isHiddenCheckBox;
    private Button saveButton;

    private BenutzerScreen screen;
    private ResourceBundle messages;

    public BenutzerEditor(final BenutzerScreen theScreen) {
        this.screen = theScreen;
        this.messages = screen.getMessagesBundle();
        setVisible(false);
        idTextField = new TextField();
        idTextField.setRequired(false);
//        idTextField.setReadOnly(true);
        idTextField.setEnabled(false);
        idTextField.setConverter(Long.class); // vaadin 7
//REFAC        idTextField.addValidator(new IntegerRangeValidator("ungültige ID", 0, Integer.MAX_VALUE));
        addComponent(idTextField);

        loginTextField = new TextField();
        loginTextField.setRequired(true);
        loginTextField.setNullRepresentation("");
        addComponent(loginTextField);

        passwdTextField = new PasswordField();
        passwdTextField.setRequired(true);
        passwdTextField.setNullRepresentation("");
        addComponent(passwdTextField);

        emailTextField = new TextField();
        emailTextField.addValidator(new EmailValidator("Ungültige E-Mail-Adresse"));
        emailTextField.setNullRepresentation("@rapidpm.org");
        addComponent(emailTextField);

        validFromDateField = new DateField();
        validFromDateField.setConverter(Date.class);
        addComponent(validFromDateField);

        validUntilDateFiled = new DateField();
        validUntilDateFiled.setDateFormat(BenutzerScreen.DATE_FORMAT.toPattern());
        addComponent(validUntilDateFiled);

        lastLoginDateField = new DateField();
        lastLoginDateField.setDateFormat(BenutzerScreen.DATE_FORMAT.toPattern());
        addComponent(lastLoginDateField);

        mandantengruppenSelect = new ComboBox();
//        mandantengruppenSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
        mandantengruppenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
        mandantengruppenSelect.setItemCaptionPropertyId("mandantengruppe");
        mandantengruppenSelect.setNullSelectionAllowed(false);
        mandantengruppenSelect.setRequired(true);
        mandantengruppenSelect.setFilteringMode(FilteringMode.CONTAINS);
        addComponent(mandantengruppenSelect);

        benutzerGruppenSelect = new ComboBox();
        benutzerGruppenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
        benutzerGruppenSelect.setItemCaptionPropertyId("gruppenname");
        benutzerGruppenSelect.setNullSelectionAllowed(false);
        benutzerGruppenSelect.setRequired(true);
        benutzerGruppenSelect.setFilteringMode(FilteringMode.CONTAINS);
        addComponent(benutzerGruppenSelect);

        benutzerWebapplikationenSelect = new ComboBox();
        benutzerWebapplikationenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
        benutzerWebapplikationenSelect.setItemCaptionPropertyId("webappName");
        benutzerWebapplikationenSelect.setNullSelectionAllowed(false);
        benutzerWebapplikationenSelect.setRequired(true);
        benutzerWebapplikationenSelect.setFilteringMode(FilteringMode.CONTAINS);
        addComponent(benutzerWebapplikationenSelect);

        berechtigungenSelect = new ListSelect();
        berechtigungenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
        berechtigungenSelect.setItemCaptionPropertyId("name");
        berechtigungenSelect.setMultiSelect(true);
        addComponent(berechtigungenSelect);

        isActiveCheckbox = new CheckBox();
        addComponent(isActiveCheckbox);

        isHiddenCheckBox = new CheckBox();
        addComponent(isHiddenCheckBox);

        saveButton = new Button();
        saveButton.addClickListener(new Button.ClickListener() {
            private final DaoFactoryBean daoFactoryBean = bean.getDaoFactoryBean();

            @Override
            public void buttonClick(final Button.ClickEvent clickEvent) {
                if (benutzerBean == null) {
                    benutzerBean = new BeanItem<>(new Benutzer());
                } else {
                    final BenutzerDAO benutzerDAO = daoFactoryBean.getBenutzerDAO();
                    final Benutzer benutzer = benutzerBean.getBean();
                }
                boolean valid = true;
                for (final Component component : BenutzerEditor.this.components) {
                    if (component instanceof Validatable) {
                        final Validatable validatable = (Validatable) component;
                        try {
                            validatable.validate();
                        } catch (Validator.InvalidValueException e) {
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
                    benutzerBean.getItemProperty("validFrom").setValue(validFromDateField.getValue());
                    benutzerBean.getItemProperty("validUntil").setValue(validUntilDateFiled.getValue());
                    benutzerBean.getItemProperty("login").setValue(loginTextField.getValue());
                    benutzerBean.getItemProperty("passwd").setValue(passwdTextField.getValue());
                    benutzerBean.getItemProperty("email").setValue(emailTextField.getValue());
                    benutzerBean.getItemProperty("lastLogin").setValue(lastLoginDateField.getValue());
                    //REFAC beheben von detached persistent Beans
                    benutzerBean.getItemProperty("mandantengruppe").setValue(mandantengruppenSelect.getValue());
                    benutzerBean.getItemProperty("benutzerGruppe").setValue(benutzerGruppenSelect.getValue());
                    benutzerBean.getItemProperty("benutzerWebapplikation").setValue(benutzerWebapplikationenSelect.getValue());
                    benutzerBean.getItemProperty("berechtigungen").setValue(berechtigungenList);
                    benutzerBean.getItemProperty("active").setValue(isActiveCheckbox.getValue());
                    benutzerBean.getItemProperty("hidden").setValue(isHiddenCheckBox.getValue());

                    // in die DB speichern
                    final Benutzer benutzer = benutzerBean.getBean();
                    daoFactoryBean.saveOrUpdate(benutzer);

                    final MainUI ui = screen.getUi();
                    ui.setWorkingArea(new BenutzerScreen(ui));
                    setVisible(false);
                } else {
                    Notification.show(messages.getString("incompletedata"));
                }
            }
        });
        addComponent(saveButton);

        doInternationalization();

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
        validFromDateField.setValue(benutzer.getValidFrom());
        validUntilDateFiled.setValue((benutzer.getValidUntil()));
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
        isActiveCheckbox.setValue(benutzer.getActive());
        isHiddenCheckBox.setValue(benutzer.getHidden());
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

    @Override
    public void doInternationalization() {
        isActiveCheckbox.setCaption(messages.getString("users_active"));
        isHiddenCheckBox.setCaption(messages.getString("users_hidden"));
        saveButton.setCaption(messages.getString("save"));
        berechtigungenSelect.setCaption(messages.getString("users_permissions"));
        benutzerWebapplikationenSelect.setCaption(messages.getString("users_webapp"));
        benutzerGruppenSelect.setCaption(messages.getString("users_usergroups"));
        mandantengruppenSelect.setCaption(messages.getString("users_mandantgroups"));
        lastLoginDateField.setCaption(messages.getString("users_lastlogin"));
        validFromDateField.setCaption(messages.getString("users_validfrom"));
        validUntilDateFiled.setCaption(messages.getString("users_validuntil"));
        emailTextField.setCaption(messages.getString("users_email"));
        passwdTextField.setCaption(messages.getString("users_password"));
        loginTextField.setCaption(messages.getString("users_login"));
        idTextField.setCaption(messages.getString("users_id"));
        setCaption(messages.getString("users_usereditor"));
    }
}
