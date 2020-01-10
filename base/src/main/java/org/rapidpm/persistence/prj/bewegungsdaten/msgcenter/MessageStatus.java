/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.bewegungsdaten.msgcenter;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:36
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
//@Table(name = "message_status")
public class MessageStatus {

    @Id
    @TableGenerator(name = "PKGenMessageStatus", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "MessageStatus_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenMessageStatus")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Basic
    private String name;

    public MessageStatus() {
    }

    private MessageStatus(final Long id, final String name) {
        this.setId(id);
        this.name = name;
    }

    public static final MessageStatus NOT_DEFINED = new MessageStatus(0L, "NOT_DEFINED");
    public static final MessageStatus ZUR_PRUEFUNG = new MessageStatus(1L, "ZUR_PRUEFUNG");
    public static final MessageStatus ABGELEHNT = new MessageStatus(2L, "ABGELEHNT");
    public static final MessageStatus FREIGESCHALTET = new MessageStatus(3L, "FREIGESCHALTET");
    public static final MessageStatus UNGELESEN = new MessageStatus(4L, "UNGELESEN");
    public static final MessageStatus GELESEN = new MessageStatus(5L, "GELESEN");

    private static final List<MessageStatus> values = new ArrayList<>();

    static {
        values.add(ZUR_PRUEFUNG);
        values.add(ABGELEHNT);
        values.add(FREIGESCHALTET);
        values.add(UNGELESEN);
        values.add(GELESEN);
    }

    public static List<MessageStatus> getValues() {
        return values;
    }

    public static MessageStatus valueOf(final String name) {
        for (final MessageStatus messageStatus : values) {
            if (messageStatus.name.equals(name)) {
                return messageStatus;
            } else {
                //
            }
        }
        return NOT_DEFINED;
    }

    public static MessageStatus valueOf(final Long oid) {
        for (final MessageStatus messageStatus : values) {
            if (messageStatus.getId().equals(oid)) {
                return messageStatus;
            } else {
                //
            }
        }
        return NOT_DEFINED;
    }

    //    @Id
    //    @Column(name = "id")
    //    @SequenceGenerator(name = "message_status_id_seq", sequenceName = "message_status_id_seq")
    //    @GeneratedValue(generator = "message_status_id_seq", strategy = GenerationType.SEQUENCE)


    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    //    @Basic
    //    @Column(name = "IssueStatus", unique = true, nullable = false)
    //    public String getIssueStatus() {
    //        return status;
    //    }

    //
    //    public void setIssueStatus(String status) {
    //        this.status = status;
    //    }
    //
    //    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final MessageStatus that = (MessageStatus) o;

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
