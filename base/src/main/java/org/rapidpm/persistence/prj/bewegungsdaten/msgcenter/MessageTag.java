package org.rapidpm.persistence.prj.bewegungsdaten.msgcenter;


import javax.persistence.*;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 09.05.2010
 *        Time: 20:45:26
 */

@Entity
public class MessageTag {

    @Id
    @TableGenerator(name = "PKGenMessageTag", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "MessageTag_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenMessageTag")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Basic
    private String tagValue;

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(final String tagValue) {
        this.tagValue = tagValue;
    }
}
