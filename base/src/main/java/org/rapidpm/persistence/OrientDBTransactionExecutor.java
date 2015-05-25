package org.rapidpm.persistence;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;

/**
 * Created by Marco on 24.05.2015.
 */
public abstract class OrientDBTransactionExecutor {

    private OrientGraph graph;

    public OrientDBTransactionExecutor(final OrientGraph graph){
        this.graph = graph;
    }

    public void execute(){
        try{
            doSpecificDBWork();
            graph.commit();
        } catch( Exception e ) {
            graph.rollback();
        }
    }

    public abstract void doSpecificDBWork();

}
