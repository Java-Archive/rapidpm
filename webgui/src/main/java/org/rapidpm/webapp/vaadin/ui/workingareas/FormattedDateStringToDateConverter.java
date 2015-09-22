package org.rapidpm.webapp.vaadin.ui.workingareas;

import com.vaadin.data.util.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 24.09.12
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public final class FormattedDateStringToDateConverter implements Converter<String, Date> {
  private final SimpleDateFormat dateFormat;

  public FormattedDateStringToDateConverter(SimpleDateFormat dateFormat) {
    this.dateFormat = dateFormat;
  }


  @Override
  public Date convertToModel(final String s, final Class<? extends Date> aClass, final Locale locale) throws ConversionException {
    Date parsedDate = new Date();

    try {
      parsedDate = dateFormat.parse(s);
    } catch (ParseException e) {
      e.printStackTrace();  //TODO Logger verwenden
    }
    return parsedDate;
  }

  @Override
  public String convertToPresentation(final Date date, final Class<? extends String> aClass, final Locale locale) throws ConversionException {
    String formattedDate = dateFormat.toPattern();
    if (date != null)
      formattedDate = dateFormat.format(date);
    return formattedDate;
  }

  @Override
  public Class<Date> getModelType() {
    return Date.class;
  }

  @Override
  public Class<String> getPresentationType() {
    return String.class;
  }
}
