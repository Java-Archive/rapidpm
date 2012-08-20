package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.components;

import com.vaadin.data.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Marco
 * Date: 20.08.12
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
public class DaysHoursMinutesFieldValidator implements Validator {

    public boolean isValid(Object value) {
        if (value == null || !(value instanceof String)) {
            return false;
        }

        return ((String) value).matches("[0-9]{1,}:[012]{1}[0-9]{1}:[012345]{1}[0-9]{1}");
    }

    @Override
    public void validate(Object value) throws InvalidValueException {
        if (!isValid(value)) {
                throw new InvalidValueException(
                        "Format: [d*]d:hh:mm! (hh = 23 max., mm = 59 max.)");
        }
    }
}
