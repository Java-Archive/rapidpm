package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.uicomponents;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.system.security.*;
import org.rapidpm.persistence.system.security.berechtigungen.Rolle;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.BenutzerScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.AlreadyExistsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.EmailAlreadyExistsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.UsernameAlreadyExistsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.WrongLoginNameException;

import javax.transaction.UserTransaction;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 12:01
 */
public class BenutzerEditor extends FormLayout implements Internationalizationable {

//    private BeanItem<Benutzer> benutzerBean;

//    @Inject
//    @LoggerQualifier
//    private Logger logger;

//    @Inject
    private UserTransaction userTransaction;


    //REFAC per CDI ?
//    @Inject
//    private StammdatenScreensBean stammdatenScreenBean;
//    private final BenutzerEditorBean bean = EJBFactory.getEjbInstance(BenutzerEditorBean.class);

    private Collection<Mandantengruppe> mandantengruppen;
    private Collection<BenutzerGruppe> benutzerGruppen;
    private Collection<BenutzerWebapplikation> benutzerWebapplikationen;
    private Collection<Rolle> rollen;

    private TextField idTextField;
    private TextField loginTextField;
    private PasswordField passwdTextField;
    private TextField emailTextField;
    private DatePicker validFromDatePicker;
    private DatePicker validUntilDateFiled;
    private DatePicker lastLoginDatePicker;
    private ComboBox mandantengruppenSelect;
    private ComboBox benutzerGruppenSelect;
    private ComboBox benutzerWebapplikationenSelect;
    private ListBox rollenSelect;
    private Checkbox isActiveCheckbox;
    private Checkbox isHiddenCheckBox;
    private Button saveButton;

    private BenutzerScreen screen;
    private ResourceBundle messages;

    public BenutzerEditor(final BenutzerScreen theScreen) {
        this.screen = theScreen;
        this.messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
        setVisible(false);
        idTextField = new TextField();
        idTextField.setRequired(false);
//        idTextField.setReadOnly(true);
        idTextField.setEnabled(false);
//        idTextField.setConverter(Long.class); // vaadin 7
//REFAC        idTextField.addValidator(new IntegerRangeValidator("ungültige ID", 0, Integer.MAX_VALUE));
        add(idTextField);

        loginTextField = new TextField();
        loginTextField.setRequired(true);
//        loginTextField.setNullRepresentation("");
        add(loginTextField);

        passwdTextField = new PasswordField();
        passwdTextField.setRequired(true);
//        passwdTextField.setNullRepresentation("");
        add(passwdTextField);

        emailTextField = new TextField();
//        emailTextField.addValidator(new EmailValidator("Ungültige E-Mail-Adresse"));
//        emailTextField.setNullRepresentation("@rapidpm.org");
        add(emailTextField);

        validFromDatePicker = new DatePicker();
//        validFromDatePicker.setConverter(Date.class);
        add(validFromDatePicker);

        validUntilDateFiled = new DatePicker();
//        validUntilDateFiled.setDateFormat(BenutzerScreen.DATE_FORMAT.toPattern());
        add(validUntilDateFiled);

        lastLoginDatePicker = new DatePicker();
//        lastLoginDatePicker.setDateFormat(BenutzerScreen.DATE_FORMAT.toPattern());
        add(lastLoginDatePicker);

        mandantengruppenSelect = new ComboBox();
//        mandantengruppenSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
//        mandantengruppenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
//        mandantengruppenSelect.setItemCaptionPropertyId("mandantengruppe");
//        mandantengruppenSelect.setNullSelectionAllowed(false);
        mandantengruppenSelect.setRequired(true);
//        mandantengruppenSelect.setFilteringMode(FilteringMode.CONTAINS);
        add(mandantengruppenSelect);

        benutzerGruppenSelect = new ComboBox();
//        benutzerGruppenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
//        benutzerGruppenSelect.setItemCaptionPropertyId("gruppenname");
//        benutzerGruppenSelect.setNullSelectionAllowed(false);
        benutzerGruppenSelect.setRequired(true);
//        benutzerGruppenSelect.setFilteringMode(FilteringMode.CONTAINS);
        add(benutzerGruppenSelect);

        benutzerWebapplikationenSelect = new ComboBox();
//        benutzerWebapplikationenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
//        benutzerWebapplikationenSelect.setItemCaptionPropertyId("webappName");
//        benutzerWebapplikationenSelect.setNullSelectionAllowed(false);
        benutzerWebapplikationenSelect.setRequired(true);
//        benutzerWebapplikationenSelect.setFilteringMode(FilteringMode.CONTAINS);
        add(benutzerWebapplikationenSelect);

        rollenSelect = new ListBox();
//        rollenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
//        rollenSelect.setItemCaptionPropertyId("name");
//        rollenSelect.setMultiSelect(true);
        add(rollenSelect);

        isActiveCheckbox = new Checkbox();
        add(isActiveCheckbox);

        isHiddenCheckBox = new Checkbox();
        add(isHiddenCheckBox);

        saveButton = new Button();
//        saveButton.addClickListener(new Button.ClickListener() {
//            private final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
//
//            @Override
//            public void buttonClick(final Button.ClickEvent clickEvent) {
//                if (benutzerBean == null) {
//                    benutzerBean = new BeanItem<>(new Benutzer());
//                } else {
//                    final BenutzerDAO benutzerDAO = daoFactory.getBenutzerDAO();
//                    final Benutzer benutzer = benutzerBean.getBean();
//                }
//                boolean valid = true;
//                for (final Component component : BenutzerEditor.this.components) {
//                    if (component instanceof Validatable) {
//                        final Validatable validatable = (Validatable) component;
//                        try {
//                            validatable.validate();
//                        } catch (Validator.InvalidValueException e) {
//                            valid = false;
//                        }
//                    }
//                }
//                try {
//                    if (valid) {
//                        final List<String> userNames = new ArrayList<>();
//                        final List<String> userEmails = new ArrayList<>();
//                        final List<Benutzer> users = daoFactory.getBenutzerDAO().loadAllEntities();
//                        for (final Benutzer user : users) {
//                            daoFactory.getEntityManager().refresh(user);
//                            userNames.add(user.getLogin());
//                            userEmails.add(user.getEmail());
//                        }
//                        final String enteredLoginName = loginTextField.getValue().toString();
//                        if (userNames.contains(enteredLoginName) && idTextField.getValue().isEmpty()) {
//                            throw new UsernameAlreadyExistsException();
//                        }
//                        if (userEmails.contains(emailTextField.getValue().toString()) && idTextField.getValue().isEmpty()) {
//                            throw new EmailAlreadyExistsException();
//                        }
//                        if (enteredLoginName.matches(Constants.EMPTY_OR_SPACES_ONLY_PATTERN) || enteredLoginName
//                                .toCharArray().length <= 2) {
//                            throw new WrongLoginNameException();
//                        }
//                        final Set<Rolle> rolleSet = new HashSet<>();
//                        final Object rollenSelectValue = rollenSelect.getValue();
//                        if (rollenSelectValue instanceof Rolle) {
//                            rolleSet.add((Rolle) rollenSelectValue);
//                        } else if (rollenSelectValue instanceof Collection) {
//                            final Collection<Rolle> rollenCollection = (Collection<Rolle>) rollenSelectValue;
//                            rolleSet.addAll(rollenCollection);
//                        }
//
//                        // Tabelle aktualisieren
//                        //                    benutzerBean.getItemProperty("id").setValue(Long.parseLong(idTextField.getValue().toString())); // ID wird von der DB verwaltet
//                        benutzerBean.getItemProperty("validFrom").setValue(validFromDatePicker.getValue());
//                        benutzerBean.getItemProperty("validUntil").setValue(validUntilDateFiled.getValue());
//                        benutzerBean.getItemProperty("login").setValue(loginTextField.getValue());
//                        benutzerBean.getItemProperty("passwd").setValue(passwdTextField.getValue());
//                        benutzerBean.getItemProperty("email").setValue(emailTextField.getValue());
//                        benutzerBean.getItemProperty("lastLogin").setValue(lastLoginDatePicker.getValue());
//                        //REFAC beheben von detached persistent Beans
//                        benutzerBean.getItemProperty("mandantengruppe").setValue(mandantengruppenSelect.getValue());
//                        benutzerBean.getItemProperty("benutzerGruppe").setValue(benutzerGruppenSelect.getValue());
//                        benutzerBean.getItemProperty("benutzerWebapplikation").setValue(benutzerWebapplikationenSelect.getValue());
//                        benutzerBean.getItemProperty("rollen").setValue(rolleSet);
//                        benutzerBean.getItemProperty("active").setValue(isActiveCheckbox.getValue());
//                        benutzerBean.getItemProperty("hidden").setValue(isHiddenCheckBox.getValue());
//
//                        // in die DB speichern
//                        final Benutzer benutzer = benutzerBean.getBean();
//                        daoFactory.saveOrUpdateTX(benutzer);
//
//                        final MainUI ui = screen.getUi();
//                        ui.setWorkingArea(new BenutzerScreen(ui));
//                        setVisible(false);
//                    } else {
//                        Notification.show(messages.getString("incompletedata"));
//                    }
//                } catch (final AlreadyExistsException e) {
//                    if (e instanceof EmailAlreadyExistsException) {
//                        Notification.show(messages.getString("users_emailexists"));
//                    }
//                    if (e instanceof UsernameAlreadyExistsException) {
//                        Notification.show(messages.getString("users_nameexists"));
//                    }
//                } catch (final WrongLoginNameException e) {
//                    Notification.show(messages.getString("users_namenotaccepted"));
//                }
//            }
//        });
        add(saveButton);

        doInternationalization();

    }

    public Benutzer getBenutzer() {
//        return benutzerBean.getBean();
        return null;
    }

//    public void setBenutzerBean(final BeanItem<Benutzer> benutzerBean) {
//        if (benutzerBean == null) {
//            return;
//        }
//        this.benutzerBean = benutzerBean;
//        final Benutzer benutzer = benutzerBean.getBean();
//        if (benutzer.getId() != null) {
//            idTextField.setValue(benutzer.getId().toString());
//        }
//        loginTextField.setValue(benutzer.getLogin());
//        passwdTextField.setValue(benutzer.getPasswd());
//        emailTextField.setValue(benutzer.getEmail());
//        validFromDatePicker.setValue(benutzer.getValidFrom());
//        validUntilDateFiled.setValue((benutzer.getValidUntil()));
//        lastLoginDatePicker.setValue(benutzer.getLastLogin());
//        mandantengruppenSelect.select(benutzer.getMandantengruppe());
//        benutzerGruppenSelect.select(benutzer.getBenutzerGruppe());
//        benutzerWebapplikationenSelect.select(benutzer.getBenutzerWebapplikation());
//        rollenSelect.setValue(null); // Selektion aufheben
//        if (benutzer.getRollen() != null) {
//            for (final Rolle rolle : benutzer.getRollen()) {
//                rollenSelect.select(rolle);
//            }
//        }
//        isActiveCheckbox.setValue(benutzer.getActive());
//        isHiddenCheckBox.setValue(benutzer.getHidden());
//    }


    public void setMandantengruppen(final Collection<Mandantengruppe> mandantengruppen) {
        this.mandantengruppen = mandantengruppen;
//        this.mandantengruppenSelect.setContainerDataSource(new BeanItemContainer<>(Mandantengruppe.class, mandantengruppen));
    }

    public void setBenutzerGruppen(final Collection<BenutzerGruppe> benutzerGruppen) {
        this.benutzerGruppen = benutzerGruppen;
//        this.benutzerGruppenSelect.setContainerDataSource(new BeanItemContainer<>(BenutzerGruppe.class, benutzerGruppen));
    }

    public void setBenutzerWebapplikationen(final Collection<BenutzerWebapplikation> benutzerWebapplikationen) {
        this.benutzerWebapplikationen = benutzerWebapplikationen;
//        this.benutzerWebapplikationenSelect.setContainerDataSource(new BeanItemContainer<>(BenutzerWebapplikation.class, benutzerWebapplikationen));
    }

    public void setRollen(final Collection<Rolle> rollen) {
        this.rollen = rollen;
//        this.rollenSelect.setContainerDataSource(new BeanItemContainer<>(Rolle.class, rollen));
    }

    @Override
    public void doInternationalization() {
//        isActiveCheckbox.setText(messages.getString("users_active"));
//        isHiddenCheckBox.setText(messages.getString("users_hidden"));
        saveButton.setText(messages.getString("save"));
//        rollenSelect.setText(messages.getString("users_roles"));
//        benutzerWebapplikationenSelect.setText(messages.getString("users_webapp"));
//        benutzerGruppenSelect.setText(messages.getString("users_usergroups"));
//        mandantengruppenSelect.setText(messages.getString("users_mandantgroups"));
//        lastLoginDatePicker.setText(messages.getString("users_lastlogin"));
//        validFromDatePicker.setText(messages.getString("users_validfrom"));
//        validUntilDateFiled.setText(messages.getString("users_validuntil"));
//        emailTextField.setText(messages.getString("users_email"));
//        passwdTextField.setText(messages.getString("users_password"));
//        loginTextField.setText(messages.getString("users_login"));
//        idTextField.setText(messages.getString("users_id"));
//        setText(messages.getString("users_usereditor"));
    }
}
