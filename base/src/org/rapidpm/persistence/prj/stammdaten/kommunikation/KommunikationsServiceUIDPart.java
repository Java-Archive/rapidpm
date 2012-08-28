package org.rapidpm.persistence.prj.stammdaten.kommunikation;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 12.01.11
 * Time: 08:31
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.apache.log4j.Logger;

import javax.persistence.*;

@Entity
public class KommunikationsServiceUIDPart {
    private static final Logger logger = Logger.getLogger(KommunikationsServiceUIDPart.class);


    @Id
    @TableGenerator(name = "PKGenKommunikationsServiceUIDPart", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "KommunikationsServiceUIDPart_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenKommunikationsServiceUIDPart")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Basic
    private int orderNr;

    @Basic
    private String uidPart;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private KommunikationsServiceUIDPartKlassifikation klassifizierung;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("KommunikationsServiceUIDPart");
        sb.append("{id=").append(id);
        sb.append(", orderNr=").append(orderNr);
        sb.append(", uidPart='").append(uidPart).append('\'');
        sb.append(", klassifizierung=").append(klassifizierung);
        sb.append('}');
        return sb.toString();
    }

    public int getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(int orderNr) {
        this.orderNr = orderNr;
    }

    public String getUidPart() {
        return uidPart;
    }

    public void setUidPart(final String uidPart) {
        this.uidPart = uidPart;
    }


    public KommunikationsServiceUIDPartKlassifikation getKlassifizierung() {
        return klassifizierung;
    }

    public void setKlassifizierung(final KommunikationsServiceUIDPartKlassifikation klassifizierung) {
        this.klassifizierung = klassifizierung;
    }
}
