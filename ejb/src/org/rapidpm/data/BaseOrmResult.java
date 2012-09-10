package org.rapidpm.data;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 21.02.11
 * Time: 09:30
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseOrmResult<T> extends LoggingResult implements Serializable {
    private static final Logger logger = Logger.getLogger(BaseOrmResult.class);

//    @Inject
//        private Logger logger;

    private Boolean valid; //TODO überall setzen

    public Boolean getValid() {
        return valid;
    }

    public void setValid(final Boolean valid) {
        this.valid = valid;
    }

    protected List<T> objList = new ArrayList<>();

    public List<T> getGenericObjList() {
        return objList;
    }

    //    public abstract List<T> getObjList();
    //
    //    public abstract void setObjList(final List<T> objList);

}