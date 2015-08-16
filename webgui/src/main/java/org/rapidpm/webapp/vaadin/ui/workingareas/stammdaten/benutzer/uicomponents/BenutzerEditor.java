package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.uicomponents;

import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerGruppe;
import org.rapidpm.persistence.system.security.BenutzerWebapplikation;
import org.rapidpm.persistence.system.security.Mandantengruppe;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.BenutzerScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.AlreadyExistsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.EmailAlreadyExistsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.UsernameAlreadyExistsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.WrongLoginNameException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 12:01
 */
public class BenutzerEditor extends FormLayout implements Internationalizationable {

    private BeanItem<Benutzer> benutzerBean;

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
    //private ListSelect rollenSelect;
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
        idTextField.setEnabled(false);
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
        validFromDateField.setDateFormat(BenutzerScreen.DATE_FORMAT.toPattern());
        addComponent(validFromDateField);

        validUntilDateFiled = new DateField();
        validUntilDateFiled.setDateFormat(BenutzerScreen.DATE_FORMAT.toPattern());
        addComponent(validUntilDateFiled);

        lastLoginDateField = new DateField();
        lastLoginDateField.setEnabled(false);
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

//        rollenSelect = new ListSelect();
//        rollenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
//        rollenSelect.setItemCaptionPropertyId("name");
//        rollenSelect.setMultiSelect(true);
//        addComponent(rollenSelect);

        isActiveCheckbox = new CheckBox();
        addComponent(isActiveCheckbox);

        isHiddenCheckBox = new CheckBox();
        addComponent(isHiddenCheckBox);

        saveButton = new Button();
        saveButton.addClickListener(new Button.ClickListener() {
            private final DaoFactory daoFactory = DaoFactorySingleton.getInstance();

            @Override
            public void buttonClick(final Button.ClickEvent clickEvent) {
                if (benutzerBean == null) {
                    benutzerBean = new BeanItem<>(new Benutzer());
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
                try {
                    if (valid) {
                        final List<String> userNames = new ArrayList<>();
                        final List<String> userEmails = new ArrayList<>();
                        final List<Benutzer> users = daoFactory.getBenutzerDAO().findAll();
                        for (final Benutzer user : users) {
                            userNames.add(user.getLogin());
                            userEmails.add(user.getEmail());
                        }
                        final String enteredLoginName = loginTextField.getValue().toString();
                        if (userNames.contains(enteredLoginName) && idTextField.getValue().equals("autom.")) {
                            throw new UsernameAlreadyExistsException();
                        }
                        if (userEmails.contains(emailTextField.getValue().toString()) && idTextField.getValue().equals("autom.")) {
                            throw new EmailAlreadyExistsException();
                        }
                        if (enteredLoginName.matches(Constants.EMPTY_OR_SPACES_ONLY_PATTERN) || enteredLoginName
                                .toCharArray().length <= 2) {
                            throw new WrongLoginNameException();
                        }
//                        final Set<Rolle> rolleSet = new HashSet<>();
//                        final Object rollenSelectValue = rollenSelect.getValue();
//                        if (rollenSelectValue instanceof Rolle) {
//                            rolleSet.add((Rolle) rollenSelectValue);
//                        } else if (rollenSelectValue instanceof Collection) {
//                            final Collection<Rolle> rollenCollection = (Collection<Rolle>) rollenSelectValue;
//                            rolleSet.addAll(rollenCollection);
//                        }

                        // Tabelle aktualisieren
                        //                    benutzerBean.getItemProperty("id").setValue(Long.parseLong(idTextField.getValue().toString())); // ID wird von der DB verwaltet
                        benutzerBean.getItemProperty("validFrom").setValue(validFromDateField.getValue());
                        benutzerBean.getItemProperty("validUntil").setValue(validUntilDateFiled.getValue());
                        benutzerBean.getItemProperty("login").setValue(loginTextField.getValue());
                        benutzerBean.getItemProperty("passwd").setValue(passwdTextField.getValue());
                        benutzerBean.getItemProperty("email").setValue(emailTextField.getValue());
                        benutzerBean.getItemProperty("lastLogin").setValue(lastLoginDateField.getValue());
                        benutzerBean.getItemProperty("active").setValue(isActiveCheckbox.getValue());
                        benutzerBean.getItemProperty("hidden").setValue(isHiddenCheckBox.getValue());
                        Benutzer benutzer = benutzerBean.getBean();
                        benutzer.setBenutzerGruppe((BenutzerGruppe)benutzerGruppenSelect.getValue());
                        benutzer.setBenutzerWebapplikation((BenutzerWebapplikation) benutzerWebapplikationenSelect.getValue());
                        benutzer.setMandantengruppe((Mandantengruppe) mandantengruppenSelect.getValue());
                        final boolean edit = (benutzer.getId() != null);
                        if(edit){
                            daoFactory.getBenutzerDAO().updateByEntity(benutzer, true);
                        } else {
                            // create
                            benutzer = daoFactory.getBenutzerDAO().createEntityFlat(benutzer);
                            daoFactory.getBenutzerDAO().setUserGroupForUser((BenutzerGruppe)benutzerGruppenSelect.getValue(), benutzer);
                            daoFactory.getBenutzerDAO().setMandantenGruppeForUser((Mandantengruppe) mandantengruppenSelect.getValue(), benutzer);
                            daoFactory.getBenutzerDAO().setWebapplicationForUser((BenutzerWebapplikation) benutzerWebapplikationenSelect.getValue(), benutzer);
                        }
                        final MainUI ui = screen.getUi();
                        ui.setWorkingArea(new BenutzerScreen(ui));
                        setVisible(false);
                    } else {
                        Notification.show(messages.getString("incompletedata"));
                    }
                } catch (final AlreadyExistsException e) {
                    if (e instanceof EmailAlreadyExistsException) {
                        Notification.show(messages.getString("users_emailexists"));
                    }
                    if (e instanceof UsernameAlreadyExistsException) {
                        Notification.show(messages.getString("users_nameexists"));
                    }
                } catch (final WrongLoginNameException e) {
                    Notification.show(messages.getString("users_namenotaccepted"));
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
            idTextField.setValue(benutzer.getId());
        } else {
            idTextField.setValue("autom.");
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
//        rollenSelect.setValue(null); // Selektion aufheben
//        if (benutzer.getRollen() != null) {
//            for (final Rolle rolle : benutzer.getRollen()) {
//                rollenSelect.select(rolle);
//            }
//        }
        isActiveCheckbox.setValue(benutzer.getActive());
        isHiddenCheckBox.setValue(benutzer.getHidden());
    }


    public void setMandantengruppen(final Collection<Mandantengruppe> mandantengruppen) {
        this.mandantengruppenSelect.setContainerDataSource(new BeanItemContainer<>(Mandantengruppe.class, mandantengruppen));
    }

    public void setBenutzerGruppen(final Collection<BenutzerGruppe> benutzerGruppen) {
        this.benutzerGruppenSelect.setContainerDataSource(new BeanItemContainer<>(BenutzerGruppe.class, benutzerGruppen));
    }

    public void setBenutzerWebapplikationen(final Collection<BenutzerWebapplikation> benutzerWebapplikationen) {
        this.benutzerWebapplikationenSelect.setContainerDataSource(new BeanItemContainer<>(BenutzerWebapplikation.class, benutzerWebapplikationen));
    }

//    public void setRollen(final Collection<Rolle> rollen) {
//        this.rollen = rollen;
//        this.rollenSelect.setContainerDataSource(new BeanItemContainer<>(Rolle.class, rollen));
//    }

    @Override
    public void doInternationalization() {
        isActiveCheckbox.setCaption(messages.getString("users_active"));
        isHiddenCheckBox.setCaption(messages.getString("users_hidden"));
        saveButton.setCaption(messages.getString("save"));
//        rollenSelect.setCaption(messages.getString("users_roles"));
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

    public void reset() {
        isActiveCheckbox.setValue(null);
        isHiddenCheckBox.setValue(null);
//        rollenSelect.setValue(null);
        benutzerWebapplikationenSelect.setValue(null);
        benutzerGruppenSelect.setValue(null);
        mandantengruppenSelect.setValue(null);
        lastLoginDateField.setValue(null);
        validFromDateField.setValue(null);
        validUntilDateFiled.setValue(null);
        emailTextField.setValue(null);
        passwdTextField.setValue(null);
        loginTextField.setValue(null);
        idTextField.setValue(null);
    }
}
