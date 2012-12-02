package org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.uicomponents.export;

import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.logic.jira.JiraSoapClientVaadin;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.model.ExportTypes;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 07.11.12
 * Time: 13:09
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ExportPanel extends RapidPanel {

   private VerticalLayout exportLayout;
   private ExportButton exportButton;
    private ExportOptionGroup exportOptionGroup;
    private TextField filenameField;

   public ExportPanel(final MainUI ui, final Table tabelle){
       this.setSizeUndefined();
       this.setCaption("Export");
       this.setHeight("300px");
       this.setWidth("200px");
       exportLayout = new VerticalLayout();
       exportButton = new ExportButton();
       exportButton.addClickListener(new Button.ClickListener() {
           @Override
           public void buttonClick(Button.ClickEvent event) {
               if(filenameField.getValue() != null){
                   JiraSoapClientVaadin clientVaadin = new JiraSoapClientVaadin(ui, tabelle,
                           filenameField.getValue(), (ExportTypes) exportOptionGroup.getValue());

//                   String path = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
//                   event.getButton().getUI().getWindows().toArray()[0].open(new FileDownloadResource(
//                           new File(path+File.separatorChar+"VAADIN"+File.separatorChar+"export"+
//                                   File.separatorChar+document.getName()+".xml")));
                   Notification.show("Vaadin export");
               } else {
                   Notification.show("Dateinamen eingeben");
               }
           }
       });
       exportOptionGroup = new ExportOptionGroup(exportButton);


       exportLayout.setSpacing(true);
       exportLayout.addComponent(exportOptionGroup);
       exportLayout.addComponent(exportButton);
       addComponent(exportLayout);


   }


    public void activate(boolean b) {
        this.setVisible(b);
    }


}
