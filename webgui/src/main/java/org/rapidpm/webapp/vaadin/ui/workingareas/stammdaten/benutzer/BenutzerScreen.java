package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.system.security.*;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.FormattedDateStringToDateConverter;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.uicomponents.BenutzerEditor;

import javax.persistence.PersistenceException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
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
  private Logger logger = Logger.getLogger(BenutzerScreen.class);
  private HorizontalLayout contentLayout = new HorizontalLayout();
  private BenutzerEditor benutzerEditor;
  private ComboBox mandantenBox;
  private Button removeButton;
  private Button addButton;
  private Button resetButton;
  private Table benutzerTable;

  public BenutzerScreen(final MainUI ui) {
    super(ui);
    setSizeFull();
    contentLayout.setSizeFull();
    contentLayout.setSpacing(true);

    final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
    final MandantengruppeDAO mandantengruppeDAO = daoFactory.getMandantengruppeDAO();
    final BenutzerGruppeDAO benutzerGruppeDAO = daoFactory.getBenutzerGruppeDAO();
    final BenutzerWebapplikationDAO benutzerWebapplikationDAO = daoFactory.getBenutzerWebapplikationDAO();
    final BenutzerDAO benutzerDAO = daoFactory.getBenutzerDAO();
    final List<Mandantengruppe> mandantengruppen = mandantengruppeDAO.findAll();
    final List<BenutzerGruppe> benutzerGruppen = benutzerGruppeDAO.findAll();
    final List<BenutzerWebapplikation> benutzerWebapplikationen = benutzerWebapplikationDAO.findAll();
    final List<Benutzer> allUsers = benutzerDAO.findAll();
    final List<Benutzer> allUsersFull = new ArrayList<>();
    for (final Benutzer user : allUsers) {
      allUsersFull.add(benutzerDAO.findByID(user.getId(), true));
    }
    final VerticalLayout benutzerTableLayout = new VerticalLayout();
    benutzerTableLayout.setSpacing(true);
    contentLayout.addComponent(benutzerTableLayout);

    mandantenBox = new ComboBox();
    mandantenBox.setContainerDataSource(new BeanItemContainer<>(Mandantengruppe.class,
        mandantengruppen));
    mandantenBox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
    mandantenBox.setItemCaptionPropertyId("mandantengruppe");
    mandantenBox.setFilteringMode(FilteringMode.CONTAINS);
    mandantenBox.setImmediate(true);
    mandantenBox.setTextInputAllowed(false);
    mandantenBox.setNullSelectionAllowed(false);
    mandantenBox.select(mandantengruppen.get(0));

    benutzerEditor = new BenutzerEditor(this);
    benutzerEditor.setMandantengruppen(mandantengruppen);
    benutzerEditor.setBenutzerGruppen(benutzerGruppen);
    benutzerEditor.setBenutzerWebapplikationen(benutzerWebapplikationen);
    contentLayout.addComponent(benutzerEditor);
    contentLayout.setExpandRatio(benutzerTableLayout, 3);
    contentLayout.setExpandRatio(benutzerEditor, 1);

    final BeanItemContainer<Benutzer> benutzerDS = new BeanItemContainer<>(Benutzer.class, allUsersFull);
    benutzerTable = new Table();
    benutzerTable.setImmediate(true);
    benutzerTable.setContainerDataSource(benutzerDS);
    benutzerTable.setWidth(100, Unit.PERCENTAGE);
    benutzerTable.setVisibleColumns(new String[]{"id", "login", "email", "validFrom", "validUntil",
        "failedLogins"});
    benutzerTable.setColumnHeaders(new String[]{"ID", "Loginname", "E-Mail", "Gültig von", "Gültig bis",
        "Fehlgeschlagene Logins"});
    benutzerTable.setSelectable(true);
    benutzerTable.setConverter("validFrom", new FormattedDateStringToDateConverter(DATE_FORMAT));
    benutzerTable.setConverter("validUntil", new FormattedDateStringToDateConverter(DATE_FORMAT));
    benutzerTableLayout.addComponent(mandantenBox);
    benutzerTableLayout.addComponent(benutzerTable);

    removeButton = new Button();
    removeButton.addClickListener(clickEvent -> {
      final Benutzer currentUser = (Benutzer) getSession().getAttribute(Benutzer.class);
      final Object tableItemId = benutzerTable.getValue();
      final BeanItem<Benutzer> beanItem = (BeanItem<Benutzer>) benutzerTable.getItem(tableItemId);
      final Benutzer selectedBenutzer = beanItem.getBean();

      final boolean isUserDeletingHimself = currentUser.getLogin().equals(selectedBenutzer.getLogin());

      if (!isUserDeletingHimself) {
        final DaoFactory daoFactory1 = DaoFactorySingleton.getInstance();
        final Benutzer benutzerFromDB = daoFactory1.getBenutzerDAO().loadBenutzerForLogin(selectedBenutzer.getLogin());
        if (benutzerFromDB != null) {
          try {
            benutzerTable.removeItem(tableItemId);
            daoFactory1.getBenutzerDAO().deleteByEntity(benutzerFromDB, false);
            benutzerEditor.setVisible(false);
          } catch (final PersistenceException e) {
            Notification.show(messagesBundle.getString("users_userinuse"));
          }

        } else {
          benutzerTable.removeItem(tableItemId);
          benutzerEditor.setVisible(false);
          logger.warn(selectedBenutzer.toString() + "war nur transient vorhanden");
        }

      } else {
        Notification.show(messagesBundle.getString("users_selfdelete"));
      }
    });
    removeButton.setEnabled(false);

    resetButton = new Button();
    resetButton.addClickListener(new Button.ClickListener() {
      @Override
      public void buttonClick(Button.ClickEvent event) {
        final Object tableItemId = benutzerTable.getValue();
        final BeanItem<Benutzer> beanItem = (BeanItem<Benutzer>) benutzerTable.getItem(tableItemId);
        beanItem.getItemProperty("failedLogins").setValue(0);
        final Benutzer selectedBenutzer = beanItem.getBean();
//                final BenutzerScreenBean stammdatenScreenBean = EJBFactory.getEjbInstance(BenutzerScreenBean.class);
//                final DaoFactoryBean daoFactoryBean = stammdatenScreenBean.getDaoFactoryBean();
        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
//                daoFactory.saveOrUpdate(selectedBenutzer);
      }
    });
    resetButton.setEnabled(false);

    benutzerTable.addValueChangeListener(valueChangeEvent -> {
      final Benutzer user = (Benutzer) valueChangeEvent.getProperty().getValue();
      System.out.println("-------------------------");
      if (benutzerTable.isSelected(user)) {
        Integer failedLogins = user.getFailedLogins();
        boolean isResetable = failedLogins >= 3;
        benutzerEditor.reset();
        benutzerEditor.setBenutzerBean(new BeanItem<>(user));
        benutzerEditor.setVisible(true);
        removeButton.setEnabled(true);
        resetButton.setEnabled(isResetable);
      } else {
        removeButton.setEnabled(false);
        benutzerEditor.setVisible(false);
      }
    });

    final HorizontalLayout benutzerButtonsLayout = new HorizontalLayout();
    benutzerButtonsLayout.setSpacing(true);
    benutzerTableLayout.addComponent(benutzerButtonsLayout);

    addButton = new Button();
    addButton.addClickListener(clickEvent -> {
      final Benutzer neuerBenutzer = new Benutzer();
      neuerBenutzer.setLogin(messagesBundle.getString("new"));
      neuerBenutzer.setActive(true);
      neuerBenutzer.setBenutzerGruppe(benutzerGruppen.get(0));
      neuerBenutzer.setBenutzerWebapplikation(benutzerWebapplikationen.get(0));
      neuerBenutzer.setEmail(messagesBundle.getString("new") + "@rapidpm.org");
      neuerBenutzer.setFailedLogins(0);
      neuerBenutzer.setMandantengruppe(mandantengruppen.get(0));
      neuerBenutzer.setValidFrom(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
      neuerBenutzer.setValidUntil(Date.from(LocalDate.now().plus(Period.ofYears(1)).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
      benutzerDS.addBean(neuerBenutzer);
      benutzerTable.select(neuerBenutzer);
    });

    benutzerButtonsLayout.addComponent(addButton);
    benutzerButtonsLayout.addComponent(removeButton);
    benutzerButtonsLayout.addComponent(resetButton);

    mandantenBox.addValueChangeListener(valueChangeEvent -> {
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
    });
    setComponents();
    doInternationalization();
  }

  public void setComponents() {
    addComponent(contentLayout);
  }

  @Override
  public void doInternationalization() {
    resetButton.setCaption(messagesBundle.getString("users_reset"));
    removeButton.setCaption(messagesBundle.getString("users_remove"));
    addButton.setCaption(messagesBundle.getString("users_add"));
    benutzerTable.setCaption(messagesBundle.getString("users_users"));
    benutzerEditor.doInternationalization();
  }
}
