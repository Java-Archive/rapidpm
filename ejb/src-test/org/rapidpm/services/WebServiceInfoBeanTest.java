/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.services;

import org.rapidpm.persistence.BaseDAOBeanTest;
import org.rapidpm.persistence.BaseDaoFactoryBean;
import org.junit.Test;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 18.01.12
 * Time: 12:31
 */
public class WebServiceInfoBeanTest extends BaseDAOBeanTest<WebServiceInfoBean> {
    public WebServiceInfoBeanTest() {
        super(WebServiceInfoBean.class, BaseDaoFactoryBean.class);
    }

    @Test
    public void testGetWebServiceList() throws Exception {
        final List<String> webServiceList = daoBean.getWebServiceList("sessionID", -1L);
        for (final String ws : webServiceList) {
            System.out.println(ws);
        }
    }
}