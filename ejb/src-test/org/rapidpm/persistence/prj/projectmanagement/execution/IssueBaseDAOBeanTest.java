/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.persistence.prj.projectmanagement.execution;

import org.junit.Test;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.DaoFactoryBeanTest;

import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.01.12
 * Time: 10:19
 */
public class IssueBaseDAOBeanTest extends DaoFactoryBeanTest<IssueBaseDAOBean> {
    public IssueBaseDAOBeanTest() {
        super(IssueBaseDAOBean.class, DaoFactoryBean.class);
    }

    @Test
    public void testConnectIssueComment() throws Exception {
        daoFactory.getIssueBaseDAO().new Transaction() {
            @Override
            public void doTask() {
                final IssueBaseDAOBean.IssueBaseResult result = daoBean.connectIssueComment("sessionID", -1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }

    @Test
    public void testConnectIssueTimeUnitUsed() throws Exception {
        daoFactory.getIssueBaseDAO().new Transaction() {
            @Override
            public void doTask() {
                final IssueBaseDAOBean.IssueBaseResult result = daoBean.connectIssueTimeUnitUsed("sessionID", -1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }
}
