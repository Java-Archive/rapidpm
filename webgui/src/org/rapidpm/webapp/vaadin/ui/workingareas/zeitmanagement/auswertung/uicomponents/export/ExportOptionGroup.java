package org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.uicomponents.export;

import com.vaadin.ui.Button;
import com.vaadin.ui.OptionGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.model.ExportTypes;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 07.11.12
 * Time: 12:17
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ExportOptionGroup extends OptionGroup {

    public ExportOptionGroup(final Button exportButton){
        super("", Arrays.asList(ExportTypes.values()));
        setImmediate(true);
        setValue(ExportTypes.XLSX);
    }
}
