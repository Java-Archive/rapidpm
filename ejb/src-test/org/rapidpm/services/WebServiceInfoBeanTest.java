/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.services;

import org.junit.Test;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.DaoFactoryBeanTest;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 18.01.12
 * Time: 12:31
 */
public class WebServiceInfoBeanTest extends DaoFactoryBeanTest<WebServiceInfoBean> {
    public WebServiceInfoBeanTest() {
        super(WebServiceInfoBean.class, DaoFactoryBean.class);
    }

    @Test
    public void testGetWebServiceList() throws Exception {
        final List<String> webServiceList = daoBean.getWebServiceList("sessionID", -1L);
        for (final String ws : webServiceList) {
            System.out.println(ws);
        }
    }
}