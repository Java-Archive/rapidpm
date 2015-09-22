package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement;

import com.vaadin.data.Validator;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;

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

  @Override
  public void validate(final Object value) throws InvalidValueException {
    if (!isValid(value)) {
      throw new InvalidValueException(
          DAYSHOURSMINUTES_VALIDATOR_REGEX_EXCEPTION_MESSAGE);
    }
  }

  public boolean isValid(final Object value) {
    if (value == null || !(value instanceof String)) {
      return false;
    }
    return COMPILE.matcher(((String) value)).matches();
  }
}
