package org.rapidpm.webservice.mapping;

import org.rapidpm.persistence.DAO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Alexander Vos
 * Date: 03.12.12
 * Time: 13:53
 */
@WebService
public abstract class FlatBaseWS<T, DT extends DAO<Long, T>, FT extends FlatEntity> {
    protected final DT dao;

    public FlatBaseWS(final DT dao) {
        this.dao = dao;
    }

    protected abstract FT toFlatObject(T object);

//    protected abstract T toObject(FT flatObject);

    protected List<FT> toFlatObjectList(final Iterable<? extends T> objects) {
        final ArrayList<FT> flatObjectList = new ArrayList<>();
        for (final T object : objects) {
            flatObjectList.add(toFlatObject(object));
        }
        return flatObjectList;
    }

    @WebMethod
    public FT findById(@WebParam(name = "id") final Long id) {
        return toFlatObject(dao.findByID(id));
    }

    @WebMethod
    public List<FT> findByIdList(@WebParam(name = "idList") final List<Long> idList) {
        final List<T> objectList = dao.loadWithOIDList(idList);
        return toFlatObjectList(objectList);
    }

    @WebMethod
    public List<FT> getAll() {
        final List<T> objectList = dao.loadAllEntities();
        return toFlatObjectList(objectList);
    }
}
