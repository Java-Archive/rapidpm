package org.rapidpm.orm.prj.stammdaten.organisationseinheit.intern.personal;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: svenruppert
 * Date: 30.07.12
 * Time: 07:52
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class MitarbeiterComment {

    @Id
    @TableGenerator(name = "PKGenMitarbeiterComment", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "MitarbeiterComment_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenMitarbeiterComment")
    private Long id;



    private Date datum;
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    //private SecurityLevel securityLevel
}
