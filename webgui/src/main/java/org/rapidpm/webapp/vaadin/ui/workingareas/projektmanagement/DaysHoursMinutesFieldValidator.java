package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;

import java.util.regex.Pattern;

import static org.rapidpm.Constants.DAYSHOURSMINUTES_REGEX;
import static org.rapidpm.Constants.DAYSHOURSMINUTES_VALIDATOR_REGEX_EXCEPTION_MESSAGE;

/**
 * Created with IntelliJ IDEA.
 * User: Marco Ebbinghaus
 * Date: 20.08.12
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
public class DaysHoursMinutesFieldValidator implements Validator {

    private static final Pattern COMPILE = Pattern.compile(DAYSHOURSMINUTES_REGEX);
    private final Screen screen;

    public DaysHoursMinutesFieldValidator(final Screen screen) {
        this.screen = screen;
    }

    public boolean isValid(final Object value) {
        if (value == null || !(value instanceof String)) {
            return false;
        }
        return COMPILE.matcher(((String) value)).matches();
    }

//    @Override
//    public void validate(final Object value) throws InvalidValueException {
//        if (!isValid(value)) {
//            throw new InvalidValueException(
//                    DAYSHOURSMINUTES_VALIDATOR_REGEX_EXCEPTION_MESSAGE);
//        }
//    }

    @Override
    public ValidationResult apply(Object o, ValueContext valueContext) {
        return null;
    }

    @Override
    public Object apply(Object o, Object o2) {
        return null;
    }
}
