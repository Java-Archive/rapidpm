package org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.model;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import org.rapidpm.data.table.Table;
import org.rapidpm.data.table.export.Table2CSV;
import org.rapidpm.data.table.export.Table2XLSX;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.uicomponents.export.ExportButton;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 03.12.12
 * Time: 10:29
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class FileSaver {

    private final String dateiname;
    private final ExportTypes exportType;
    private Table table;
    private ExportButton button;

    public FileSaver(final String dateiname, final ExportTypes exportType, final Table table,
                     final ExportButton button) {
        this.dateiname = dateiname;
        this.exportType = exportType;
        this.table = table;
        this.button = button;
    }

    public FileResource getFileResource() throws IOException {
        File file = null;
        FileOutputStream fos = null;
        if (exportType.equals(ExportTypes.CSV)) {
            file = new File(dateiname + ".csv");
            fos = new FileOutputStream(file);
            final Table2CSV table2CSV = new Table2CSV();
            final String string = table2CSV.export(table);
            fos.write(string.getBytes());
        } else {
            file = new File(dateiname + ".xlsx");
            final Table2XLSX table2XLSX = new Table2XLSX();
            final ByteArrayOutputStream byteArrayOutputStream2 = table2XLSX.export(table);
            fos = new FileOutputStream(file);
            fos.write(byteArrayOutputStream2.toByteArray());
        }
        fos.flush();
        fos.close();
        final FileResource fileResource = new FileResource(file);
        return fileResource;
    }


}
