package org.rapidpm.webapp.vaadin;

import java.text.SimpleDateFormat;

public class Constants
{

	public static final Integer HOURSPERDAY = 8;
	public static final Integer KONSTANTE = 750;
	public static final String EUR = " \u20AC";
	
	public static final String TABLEEDIT = "Tabelle";
	public static final String ROWEDIT = "Zeile";

    public static final String IMAGE_LOGO = "images/logo_sru.png";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public static final String DAYSHOURSMINUTES_REGEX = "[0-9]{1,}:[012]{1}[0-9]{1}:[012345]{1}[0-9]{1}";
    public static final String DAYSHOURSMINUTES_VALIDATOR_EXCEPTION_MESSAGE = "Format: [d*]d:hh:mm! (hh = 23 max., mm = 59 max.)";

}
