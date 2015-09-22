package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.metadata;

import org.apache.log4j.Logger;

import javax.persistence.*;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 02.03.2010
 * Time: 18:14:16
 */

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
//@Table(name = "verwaltungseinheit")
public class Verwaltungseinheit {
  private static final Logger logger = Logger.getLogger(Verwaltungseinheit.class);


  @Id
  @TableGenerator(name = "PKGenVerwaltungseinheit", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "Verwaltungseinheit_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenVerwaltungseinheit")
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }


}
