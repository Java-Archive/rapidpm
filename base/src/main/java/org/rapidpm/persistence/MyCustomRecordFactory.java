package org.rapidpm.persistence;

import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.db.ODatabaseThreadLocalFactory;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;

public class MyCustomRecordFactory implements ODatabaseThreadLocalFactory {


  @Override
  public ODatabaseDocumentInternal getThreadDatabase() {
    return new OPartitionedDatabasePool("plocal:orient/RapidPM", "admin", "admin").acquire();
  }
}
