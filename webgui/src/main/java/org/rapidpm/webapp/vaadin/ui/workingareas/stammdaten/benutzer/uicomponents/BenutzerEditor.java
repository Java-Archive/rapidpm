package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.uicomponents;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.server.VaadinSession;
import org.hibernate.validator.internal.metadata.facets.Validatable;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerGruppe;
import org.rapidpm.persistence.system.security.BenutzerWebapplikation;
import org.rapidpm.persistence.system.security.Mandantengruppe;
import org.rapidpm.persistence.system.security.berechtigungen.Rolle;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.BenutzerScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.AlreadyExistsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.EmailAlreadyExistsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.UsernameAlreadyExistsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.WrongLoginNameException;

import javax.transaction.UserTransaction;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 12:01
 */
public class BenutzerEditor extends FormLayout implements Internationalizationable {

    private Benutzer benutzer;

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
    private ComboBox<Mandantengruppe> mandantengruppenSelect;
    private ComboBox<BenutzerGruppe> benutzerGruppenSelect;
    private ComboBox<BenutzerWebapplikation> benutzerWebapplikationenSelect;
    private ListBox<Rolle> rollenSelect;
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
        idTextField.setReadOnly(true);
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

        mandantengruppenSelect = new ComboBox<>();
        mandantengruppenSelect.setItemLabelGenerator(Mandantengruppe::getMandantengruppe);
//        mandantengruppenSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
//        mandantengruppenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
//        mandantengruppenSelect.setItemCaptionPropertyId("mandantengruppe");
//        mandantengruppenSelect.setNullSelectionAllowed(false);
        mandantengruppenSelect.setRequired(true);
//        mandantengruppenSelect.setFilteringMode(FilteringMode.CONTAINS);
        add(mandantengruppenSelect);

        benutzerGruppenSelect = new ComboBox<>();
        benutzerGruppenSelect.setItemLabelGenerator(BenutzerGruppe::getGruppenname);
//        benutzerGruppenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
//        benutzerGruppenSelect.setItemCaptionPropertyId("gruppenname");
//        benutzerGruppenSelect.setNullSelectionAllowed(false);
        benutzerGruppenSelect.setRequired(true);
//        benutzerGruppenSelect.setFilteringMode(FilteringMode.CONTAINS);
        add(benutzerGruppenSelect);

        benutzerWebapplikationenSelect = new ComboBox<>();
        benutzerWebapplikationenSelect.setItemLabelGenerator(BenutzerWebapplikation::getWebappName);
//        benutzerWebapplikationenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
//        benutzerWebapplikationenSelect.setItemCaptionPropertyId("webappName");
//        benutzerWebapplikationenSelect.setNullSelectionAllowed(false);
        benutzerWebapplikationenSelect.setRequired(true);
//        benutzerWebapplikationenSelect.setFilteringMode(FilteringMode.CONTAINS);
        add(benutzerWebapplikationenSelect);

        rollenSelect = new ListBox<>();
        rollenSelect.setRenderer(new ComponentRenderer<>(rolle -> new Label(rolle.getName())));
//        rollenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
//        rollenSelect.setItemCaptionPropertyId("name");
//        rollenSelect.setMultiSelect(true);
        add(rollenSelect);

        isActiveCheckbox = new Checkbox();
        add(isActiveCheckbox);

        isHiddenCheckBox = new Checkbox();
        add(isHiddenCheckBox);

        saveButton = new Button();
        saveButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
            if (benutzer == null) {
                benutzer = new Benutzer();
            }
            boolean valid = true;
           BenutzerEditor.this.getChildren().forEach(component -> {
               if (component instanceof Validatable) {
                   final Validatable validatable = (Validatable) component;
//                   try {
//                       validatable.validate();
//                   } catch (Validator.InvalidValueException e) {
//                       valid = false;
//                   }
               }
           });
            try {
                if (valid) {
                    final List<String> userNames = new ArrayList<>();
                    final List<String> userEmails = new ArrayList<>();
                    final List<Benutzer> users = daoFactory.getBenutzerDAO().loadAllEntities();
                    for (final Benutzer user : users) {
                        daoFactory.getEntityManager().refresh(user);
                        userNames.add(user.getLogin());
                        userEmails.add(user.getEmail());
                    }
                    final String enteredLoginName = loginTextField.getValue().toString();
                    if (userNames.contains(enteredLoginName) && idTextField.getValue().isEmpty()) {
                        throw new UsernameAlreadyExistsException();
                    }
                    if (userEmails.contains(emailTextField.getValue().toString()) && idTextField.getValue().isEmpty()) {
                        throw new EmailAlreadyExistsException();
                    }
                    if (enteredLoginName.matches(Constants.EMPTY_OR_SPACES_ONLY_PATTERN) || enteredLoginName
                            .toCharArray().length <= 2) {
                        throw new WrongLoginNameException();
                    }
                    final Set<Rolle> rolleSet = new HashSet<>();
                    final Object rollenSelectValue = rollenSelect.getValue();
                    if (rollenSelectValue instanceof Rolle) {
                        rolleSet.add((Rolle) rollenSelectValue);
                    } else if (rollenSelectValue instanceof Collection) {
                        final Collection<Rolle> rollenCollection = (Collection<Rolle>) rollenSelectValue;
                        rolleSet.addAll(rollenCollection);
                    }

                    // Tabelle aktualisieren
                    //                    benutzerBean.getItemProperty("id").setValue(Long.parseLong(idTextField.getValue().toString())); // ID wird von der DB verwaltet
                    benutzer.setValidFrom(Date.from(validFromDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    benutzer.setValidUntil(Date.from(validUntilDateFiled.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    benutzer.setLogin(loginTextField.getValue());
                    benutzer.setPasswd(passwdTextField.getValue());
                    benutzer.setEmail(emailTextField.getValue());
                    benutzer.setLastLogin(Date.from(lastLoginDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    //REFAC beheben von detached persistent Beans
                    benutzer.setMandantengruppe(mandantengruppenSelect.getValue());
                    benutzer.setBenutzerGruppe(benutzerGruppenSelect.getValue());
                    benutzer.setBenutzerWebapplikation(benutzerWebapplikationenSelect.getValue());
                    benutzer.setRollen(rolleSet);
                    benutzer.setActive(isActiveCheckbox.getValue());
                    benutzer.setHidden(isHiddenCheckBox.getValue());

                    // in die DB speichern
                    daoFactory.saveOrUpdateTX(benutzer);

                    screen.getBenutzerGrid().getDataProvider().refreshItem(benutzer);
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
        });
        add(saveButton);
        doInternationalization();
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(final Benutzer benutzer) {
        if (benutzer == null) {
            return;
        }
        this.benutzer = benutzer;
        if (this.benutzer.getId() != null) {
            idTextField.setValue(this.benutzer.getId().toString());
        }
        loginTextField.setValue(this.benutzer.getLogin());
        passwdTextField.setValue(this.benutzer.getPasswd());
        emailTextField.setValue(this.benutzer.getEmail());
        validFromDatePicker.setValue(this.benutzer.getValidFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        validUntilDateFiled.setValue((this.benutzer.getValidUntil()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        lastLoginDatePicker.setValue(this.benutzer.getLastLogin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        mandantengruppenSelect.setValue(this.benutzer.getMandantengruppe());
        benutzerGruppenSelect.setValue(this.benutzer.getBenutzerGruppe());
        benutzerWebapplikationenSelect.setValue(this.benutzer.getBenutzerWebapplikation());
        rollenSelect.setValue(null); // Selektion aufheben
        if (this.benutzer.getRollen() != null) {
            for (final Rolle rolle : this.benutzer.getRollen()) {
                rollenSelect.setValue(rolle);
            }
        }
        isActiveCheckbox.setValue(this.benutzer.getActive());
        isHiddenCheckBox.setValue(this.benutzer.getHidden());
    }


    public void setMandantengruppen(final Collection<Mandantengruppe> mandantengruppen) {
        this.mandantengruppen = mandantengruppen;
        this.mandantengruppenSelect.setItems(mandantengruppen);
    }

    public void setBenutzerGruppen(final Collection<BenutzerGruppe> benutzerGruppen) {
        this.benutzerGruppen = benutzerGruppen;
        this.benutzerGruppenSelect.setItems(benutzerGruppen);
    }

    public void setBenutzerWebapplikationen(final Collection<BenutzerWebapplikation> benutzerWebapplikationen) {
        this.benutzerWebapplikationen = benutzerWebapplikationen;
        this.benutzerWebapplikationenSelect.setItems(benutzerWebapplikationen);
    }

    public void setRollen(final Collection<Rolle> rollen) {
        this.rollen = rollen;
        this.rollenSelect.setItems(rollen);
    }

    @Override
    public void doInternationalization() {
        isActiveCheckbox.setLabel(messages.getString("users_active"));
        isHiddenCheckBox.setLabel(messages.getString("users_hidden"));
        saveButton.setText(messages.getString("save"));
//        rollenSelect.setText(messages.getString("users_roles"));
        benutzerWebapplikationenSelect.setLabel(messages.getString("users_webapp"));
        benutzerGruppenSelect.setLabel(messages.getString("users_usergroups"));
        mandantengruppenSelect.setLabel(messages.getString("users_mandantgroups"));
        lastLoginDatePicker.setLabel(messages.getString("users_lastlogin"));
        validFromDatePicker.setLabel(messages.getString("users_validfrom"));
        validUntilDateFiled.setLabel(messages.getString("users_validuntil"));
        emailTextField.setLabel(messages.getString("users_email"));
        passwdTextField.setLabel(messages.getString("users_password"));
        loginTextField.setLabel(messages.getString("users_login"));
        idTextField.setLabel(messages.getString("users_id"));
//        setText(messages.getString("users_usereditor"));
    }
}
