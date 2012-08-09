package org.rapidpm.orm.rohdaten;

import javax.persistence.*;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class OntologieConnection {

    @Id
    @TableGenerator(name = "PKGenOntologieConnection", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "OntologieConnection_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenOntologieConnection")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Basic
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OntologieConnection() {
    }

    public OntologieConnection(final String name) {
        this.name = name;
    }

    //    public static OntologieConnection IS_EIN = new OntologieConnection(1L, "IS_EIN");
    //    public static OntologieConnection BESTEHT_AUS = new OntologieConnection(2L, "BESTEHT_AUS");
    //    public static OntologieConnection ENTHAELT = new OntologieConnection(3L, "ENTHAELT");
    //    public static OntologieConnection WIRD_ENTHALTEN = new OntologieConnection(4L, "WIRD_ENTHALTEN");
    //
    //    public static OntologieConnection valueOf(final String name){
    //        if(name.equals(IS_EIN.name)){ return IS_EIN ;}
    //        if(name.equals(BESTEHT_AUS.name)){ return BESTEHT_AUS ;}
    //        if(name.equals(ENTHAELT.name)){ return ENTHAELT ;}
    //        if(name.equals(WIRD_ENTHALTEN.name)){ return WIRD_ENTHALTEN ;}
    //        return null;
    //    }


    //    public static List<String> values(){
    //
    //        final List<String> beziehungen = new ArrayList<String>();
    //        beziehungen.add(IS_EIN.name);
    //        beziehungen.add(BESTEHT_AUS.name);
    //        beziehungen.add(ENTHAELT.name);
    //        beziehungen.add(WIRD_ENTHALTEN.name);
    //        return beziehungen;
    //    }

}
