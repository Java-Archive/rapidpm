/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.bewegungsdaten.msgcenter;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:35
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MessageKlassifizierung {

    @Id
    @TableGenerator(name = "PKGenMessageKlassifizierung", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "MessageKlassifizierung_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenMessageKlassifizierung")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    //    GESUCHE,
    //    ANGEBOT,
    //    SPEZIALANGEBOT,
    //    TERMIN,
    //    VERANSTALTUNG;

    public static final MessageKlassifizierung NOT_DEFINED = new MessageKlassifizierung(0L, "NOT_DEFINED");
    public static final MessageKlassifizierung GESUCH = new MessageKlassifizierung(1L, "GESUCH");
    public static final MessageKlassifizierung ANGEBOT = new MessageKlassifizierung(2L, "ANGEBOT");
    public static final MessageKlassifizierung SPEZIALANGEBOT = new MessageKlassifizierung(3L, "SPEZIALANGEBOT");
    public static final MessageKlassifizierung TERMIN = new MessageKlassifizierung(4L, "TERMIN");
    public static final MessageKlassifizierung VERANSTALTUNG = new MessageKlassifizierung(5L, "VERANSTALTUNG");
    public static final MessageKlassifizierung AKTUELLES = new MessageKlassifizierung(6L, "AKTUELLES");
    public static final MessageKlassifizierung PERSONAL_MESSAGE = new MessageKlassifizierung(7L, "PERSONAL_MESSAGE");

    private static final List<MessageKlassifizierung> values = new ArrayList<>();

    static {
        values.add(GESUCH);
        values.add(ANGEBOT);
        values.add(SPEZIALANGEBOT);
        values.add(TERMIN);
        values.add(VERANSTALTUNG);
        values.add(AKTUELLES);
        values.add(PERSONAL_MESSAGE);
    }

    public static List<MessageKlassifizierung> getValues() {
        return values;
    }

    public static MessageKlassifizierung valueOf(final String name) {
        for (final MessageKlassifizierung messageStatus : values) {
            if (messageStatus.name.equals(name)) {
                return messageStatus;
            } else {
                //
            }
        }
        return NOT_DEFINED;
    }

    public static MessageKlassifizierung valueOf(final Long oid) {
        for (final MessageKlassifizierung messageStatus : values) {
            if (messageStatus.getId().equals(oid)) {
                return messageStatus;
            } else {
                //
            }
        }
        return NOT_DEFINED;
    }

    public MessageKlassifizierung() {
    }

    public MessageKlassifizierung(final Long id, final String name) {
        setId(id);
        this.name = name;
    }

    //    private Long id;
    private String name;
    //
    //
    //    @Id

    //    @Column(name = "id")
    //    @SequenceGenerator(name = "message_klassifizierung_id_seq", sequenceName = "message_klassifizierung_id_seq")
    //    @GeneratedValue(generator = "message_klassifizierung_id_seq", strategy = GenerationType.SEQUENCE)
    //    @Override
    //    public Long getId() {
    //        return id;
    //    }
    //
    //    @Override
    //    public void setId(final Long id) {
    //        this.id = id;
    //    }
    //

    //    @Basic
    //    @Column(name = "name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    //
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final MessageKlassifizierung that = (MessageKlassifizierung) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
