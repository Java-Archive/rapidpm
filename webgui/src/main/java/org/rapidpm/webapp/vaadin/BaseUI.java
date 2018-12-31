package org.rapidpm.webapp.vaadin;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 21:37
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedHttpSession;
import com.vaadin.flow.server.WrappedSession;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.windows.*;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.IMAGE_LOGO;
import static org.rapidpm.Constants.MESSAGESBUNDLE;


public abstract class BaseUI extends VerticalLayout {

//    @Override
    public BaseUI() {

    }





//    public MenuBar getMenuBar() {
//        return menubar;
//    }


//    public RapidPanel getWorkingAreaContainer() {
//        return workingArea;
//    }



//    public void setWorkingArea(final Component workingArea) {
//        this.workingArea.removeAllComponents();
//        this.workingArea.getContentLayout().setMargin(false);
//        this.workingArea.add(workingArea);
////        this.workingArea.getContent().setSizeFull();
////        this.workingArea.setSizeFull();
//    }


//    private void buildMainLayout() {
//        mainlayout.setSizeFull();
////        workingArea.setSizeFull();
//
//        createLinkLeistenLayout();
//        createIconsLayout();
//
////        initMenuBar(menubar);
////        menubar.setSizeUndefined();
//
//
//        linkLeistenLayout.setSizeUndefined();
//        iconsLayout.setSizeUndefined();
//        iconsLayout.setWidth("100%");
////        menubar.setWidth("100%");
//        mainlayout.setSpacing(false);
//        mainlayout.add(linkLeistenLayout);
//        mainlayout.add(iconsLayout);
////        mainlayout.add(menubar);
//        mainlayout.add(workingArea);
////        mainlayout.setExpandRatio(workingArea, 1);
////        workingArea.setSizeFull();
//        mainlayout.setSpacing(false);
//        setContent(mainlayout);
//    }

//    private void createIconsLayout() {
////        final Image iconLeft = new Image("", new ThemeResource(IMAGE_LOGO));
////        final Image iconRight = new Image("", new ThemeResource(IMAGE_LOGO));
//        iconsLayout.setWidth("100%");
////        iconsLayout.add(iconLeft);
////        iconsLayout.setComponentAlignment(iconLeft, Alignment.TOP_LEFT);
//
//        final Label versionLabel = new Label(VERSION);
//        iconsLayout.add(versionLabel);
////        iconsLayout.setComponentAlignment(versionLabel, Alignment.TOP_CENTER);
//
////        iconsLayout.add(iconRight);
////        iconsLayout.setComponentAlignment(iconRight, Alignment.TOP_RIGHT);
//    }

//    protected abstract void initMenuBar(MenuBar menuBar);

//    private void createLinkLeistenLayout() {
//        linkLeistenLayout.setSizeFull();
//        linkLeistenLayout.setSpacing(true);
////        final Button buttonKontakt = new Button("Kontakt", new Button.ClickListener() {
//
////            @Override
////            public void buttonClick(Button.ClickEvent clickEvent) {
////                UI.getCurrent().addWindow(new KontaktWindow());
////            }
//////                getMainWindow().addWindow(new KontaktWindow());
////        });
////        final Button buttonSupport = new Button("Support", new Button.ClickListener() {
////
////            @Override
////            public void buttonClick(Button.ClickEvent clickEvent) {
////                UI.getCurrent().addWindow(new SupportWindow());
////            }
////        });
////        final Button buttonImpressum = new Button("Impressum", new Button.ClickListener() {
////
////            @Override
////            public void buttonClick(Button.ClickEvent clickEvent) {
////                UI.getCurrent().addWindow(new ImpressumWindow());
////            }
////        });
////        final Button buttonDisclaimer = new Button("Disclaimer", new Button.ClickListener() {
////
////            @Override
////            public void buttonClick(Button.ClickEvent clickEvent) {
////                UI.getCurrent().addWindow(new DisclaimerWindow());
////            }
////        });
////        final Button buttonSitemap = new Button("Sitemap", new Button.ClickListener() {
////
////            @Override
////            public void buttonClick(Button.ClickEvent clickEvent) {
////                UI.getCurrent().addWindow(new KontaktWindow());
////            }
////        });
////        final Button buttonAbmelden = new Button("Abmelden", new Button.ClickListener() {
////
////            @Override
////            public void buttonClick(Button.ClickEvent clickEvent) {
////                getSession().close();
////                getPage().setLocation("/rapidpm");
////            }
////        });
////        buttonKontakt.setStyleName(BaseTheme.BUTTON_LINK);
////        buttonSupport.setStyleName(BaseTheme.BUTTON_LINK);
////        buttonImpressum.setStyleName(BaseTheme.BUTTON_LINK);
////        buttonDisclaimer.setStyleName(BaseTheme.BUTTON_LINK);
////        buttonSitemap.setStyleName(BaseTheme.BUTTON_LINK);
////        buttonAbmelden.setStyleName(BaseTheme.BUTTON_LINK);
//
////        linkLeistenLayout.add(buttonAbmelden);
////        linkLeistenLayout.add(new Label("|"));
////        linkLeistenLayout.add(buttonKontakt);
////        linkLeistenLayout.add(new Label("|"));
////        linkLeistenLayout.add(buttonSupport);
////        linkLeistenLayout.add(new Label("|"));
////        linkLeistenLayout.add(buttonImpressum);
////        linkLeistenLayout.add(new Label("|"));
////        linkLeistenLayout.add(buttonDisclaimer);
////        linkLeistenLayout.add(new Label("|"));
////        linkLeistenLayout.add(buttonSitemap);
//    }


}
