package org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.uicomponents.export;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.logic.jira.JiraSoapClientVaadin;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.model.ExportTypes;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.model.FileSaver;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 07.11.12
 * Time: 13:09
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ExportPanel extends RapidPanel {

    private ExportButton exportButton;
    private ExportOptionGroup exportOptionGroup;
    private TextField filenameField;
    private FileResource fileResource;
    private FileDownloader fileDownloader;

    public ExportPanel(final MainUI ui, final Table tabelle) {
        this.setSizeUndefined();
        this.setCaption("Export");
        this.setHeight("300px");
        this.setWidth("200px");
        exportButton = new ExportButton();
        fileDownloader = new FileDownloader(new FileResource(new File("dummy")));
        fileDownloader.extend(exportButton);
        exportButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (filenameField.getValue() != null && !filenameField.getValue().isEmpty()) {
                    final JiraSoapClientVaadin clientVaadin = new JiraSoapClientVaadin(ui, tabelle,
                            filenameField.getValue());
                    final FileSaver fileSaver = new FileSaver(filenameField.getValue(),
                            (ExportTypes) exportOptionGroup.getValue(), clientVaadin.getTable(), exportButton);
                    try {
                        fileResource = fileSaver.getFileResource();
                        fileDownloader.setFileDownloadResource(fileResource);
                    } catch (final IOException e) {
                        Notification.show("Datei konnte nicht heruntergeladen werden");
                    }
//                   String path = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
//                   event.getButton().getUI().getWindows().toArray()[0].open(new FileDownloadResource(
//                           new File(path+File.separatorChar+"VAADIN"+File.separatorChar+"export"+
//                                   File.separatorChar+document.getName()+".xml")));
                } else {
                    Notification.show("Dateinamen eingeben");
                }
            }
        });
        exportOptionGroup = new ExportOptionGroup(exportButton);
        filenameField = new TextField();

        addComponent(filenameField);
        addComponent(exportOptionGroup);
        addComponent(exportButton);


    }


    public void activate(boolean b) {
        this.setVisible(b);
    }

    public FileResource getFileResource() {
        return fileResource;
    }

    public void setFileResource(FileResource fileResource) {
        this.fileResource = fileResource;
    }

    public FileDownloader getFileDownloader() {
        return fileDownloader;
    }

    public void setFileDownloader(FileDownloader fileDownloader) {
        this.fileDownloader = fileDownloader;
    }
}
