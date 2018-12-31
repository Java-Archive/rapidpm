package org.rapidpm.webapp.vaadin.ui.workingareas;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

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
    public Result<Date> convertToModel(String s, ValueContext valueContext) {
        Date parsedDate = new Date();

        try {
            parsedDate = dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();  //TODO Logger verwenden
        }
        return Result.ok(parsedDate);
    }

    @Override
    public String convertToPresentation(Date date, ValueContext valueContext) {
        String formattedDate = dateFormat.toPattern();
        if(date != null)
            formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}
