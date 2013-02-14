/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.bewegungsdaten.msgcenter;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:34
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerGruppe;
import org.rapidpm.persistence.system.security.BenutzerWebapplikation;
import org.rapidpm.persistence.system.security.Mandantengruppe;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Message {
    private static final Logger logger = Logger.getLogger(Message.class);

    @Id
    @TableGenerator(name = "PKGenMessage", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "Message_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenMessage")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Basic
    private String header;
    @Basic
    private String teaser;
    @Basic
    private String message;
    @Basic
    private Date validFrom;
    @Basic
    private Date validUntil;

    @OneToOne(cascade = CascadeType.REFRESH)
    private MessageStatus messageStatus;

    @OneToOne(cascade = CascadeType.REFRESH)
    private MessageKlassifizierung messageKlassifizierung;

    @OneToOne(cascade = CascadeType.REFRESH)
    private Mandantengruppe mandantengruppe;
    @OneToOne(cascade = CascadeType.REFRESH)
    private BenutzerWebapplikation benutzerWebapplikation;
    @OneToOne(cascade = CascadeType.REFRESH)
    private BenutzerGruppe benutzerGruppe;

    @OneToOne(cascade = CascadeType.REFRESH)
    private Benutzer creator;
    @OneToOne(cascade = CascadeType.REFRESH)
    private Benutzer owner;


    public BenutzerGruppe getBenutzerGruppe() {
        return benutzerGruppe;
    }

    public void setBenutzerGruppe(final BenutzerGruppe benutzerGruppe) {
        this.benutzerGruppe = benutzerGruppe;
    }

    public BenutzerWebapplikation getBenutzerWebapplikation() {
        return benutzerWebapplikation;
    }

    public void setBenutzerWebapplikation(final BenutzerWebapplikation benutzerWebapplikation) {
        this.benutzerWebapplikation = benutzerWebapplikation;
    }

    public Benutzer getCreator() {
        return creator;
    }

    public void setCreator(final Benutzer creator) {
        this.creator = creator;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(final String header) {
        this.header = header;
    }

    public Mandantengruppe getMandantengruppe() {
        return mandantengruppe;
    }

    public void setMandantengruppe(final Mandantengruppe mandantengruppe) {
        this.mandantengruppe = mandantengruppe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public MessageKlassifizierung getMessageKlassifizierung() {
        return messageKlassifizierung;
    }

    public void setMessageKlassifizierung(final MessageKlassifizierung messageKlassifizierung) {
        this.messageKlassifizierung = messageKlassifizierung;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(final MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    public Benutzer getOwner() {
        return owner;
    }

    public void setOwner(final Benutzer owner) {
        this.owner = owner;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(final String teaser) {
        this.teaser = teaser;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(final Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(final Date validUntil) {
        this.validUntil = validUntil;
    }
}
