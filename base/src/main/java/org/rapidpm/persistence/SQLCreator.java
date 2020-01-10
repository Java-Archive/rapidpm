package org.rapidpm.persistence;

import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.Mandantengruppe;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 05.11.2010
 *        Time: 08:42:17
 */

public class SQLCreator {

    public static String andValue(final String variable, final String value) {
        return " and " + variable + " = '" + value + "' \n";
    }

    public static String andNotValue(final String variable, final String value) {
        return " and " + variable + " !=  '" + value + "'  \n";
    }

    public static String andLikeValue(final String variable, final String value) {
        return " and " + variable + " like '" + value + "'  \n";
    }

    public static String andGreaterOrEQValue(final String variable, final String value) {
        return " and " + variable + " >= '" + value + "'  \n";
    }

    public static String andSmallerOrEQValue(final String variable, final String value) {
        return " and " + variable + " <= '" + value + "'  \n";
    }


    public static String andValue(final String variable, final Number value) {
        return " and " + variable + " = " + value + " \n";
    }

    public static String andNotValue(final String variable, final Number value) {
        return " and " + variable + " !=  " + value + "  \n";
    }

    public static String andLikeValue(final String variable, final Number value) {
        return " and " + variable + " like " + value + "  \n";
    }

    public static String andGreaterOrEQValue(final String variable, final Number value) {
        return " and " + variable + " >= " + value + "  \n";
    }

    public static String andSmallerOrEQValue(final String variable, final Number value) {
        return " and " + variable + " <= " + value + "  \n";
    }


    public static String and(final String variable, final String sql) {
        return " and " + variable + " = " + withBrackets(sql);
    }

    public static String andNot(final String variable, final String sql) {
        return " and " + variable + " != " + withBrackets(sql);
    }

    public static String andLike(final String variable, final String sql) {
        return " and " + variable + " like " + withBrackets(sql);
    }

    public static String andGreaterOrEQ(final String variable, final String sql) {
        return " and " + variable + " >= " + withBrackets(sql);
    }

    public static String andSmallerOrEQ(final String variable, final String sql) {
        return " and " + variable + " <= " + withBrackets(sql);
    }


    private static String withBrackets(final String sql) {
        return " ( " + sql + " ) ";
    }

    public static String benutzerGruppeID(final String grp) {
        return withBrackets("select id from benutzer_gruppe b where b.gruppenname='" + grp + "'");
    }

    public static String benutzerID(final String login, final String mandantengruppe) {
        return withBrackets("select id from benutzer b where b.login='" + login + "' and b.mandantengruppe_id =" + mandantengruppeID(mandantengruppe));
    }

    public static String benutzerID(final Benutzer benutzer) {
        final Mandantengruppe mandantengruppe = benutzer.getMandantengruppe();
        return withBrackets("select id from benutzer b where b.login='" + benutzer.getLogin() + "' and b.mandantengruppe_id =" + mandantengruppeID(mandantengruppe));
    }


    public static String mandantengruppeID(final String mandantengruppe) {
        return withBrackets("select id from mandantengruppe m where m.mandantengruppe='" + mandantengruppe + "'");
    }

    public static String mandantengruppeID(final Mandantengruppe mandantengruppe) {
        return withBrackets("select id from mandantengruppe m where m.mandantengruppe='" + mandantengruppe.getMandantengruppe() + "'");
    }

    public static String webapplikationID(final String webapp) {
        return withBrackets("select id from benutzer_webapplikation where webapp_name='" + webapp + "'");
    }

    public static String messageklassifizierungID(final String klassifizierung) {
        return withBrackets("select id from message_klassifizierung where name='" + klassifizierung + "'");
    }


    public static String statusID(final String status) {
        return withBrackets("select id from status s where s.name='" + status + "'");
    }

    public static String projectID(final String mandantengruppe, final String project) {
        return withBrackets("select id from project p where p.project_name='" + project + "' and p.mandantengruppe_id =" + mandantengruppeID(mandantengruppe));
    }

    public static String priorityID(final String priority) {
        return withBrackets("select id from priority p where p.name = '" + priority + "'");
    }

    public static String taetigkeitsfeldID(final String taetigkeitsfeld) {
        return withBrackets("select id from taetigkeitsfeld where namen='" + taetigkeitsfeld + "'");
    }

    public static String registrationStatusID(final String registrationstatus) {
        return withBrackets("select id from registration_status where status='" + registrationstatus + "'");
    }


}