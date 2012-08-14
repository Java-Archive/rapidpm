/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.orm.prj.projectmanagement.execution;

import org.rapidpm.orm.BaseDAOBeanTest;
import org.rapidpm.orm.prj.stammdaten.DaoFactoryBean;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.01.12
 * Time: 10:19
 */
public class IssueBaseDAOBeanTest extends BaseDAOBeanTest<IssueBaseDAOBean> {
    public IssueBaseDAOBeanTest() {
        super(IssueBaseDAOBean.class, DaoFactoryBean.class);
    }

    @Test
    public void testConnectIssueComment() throws Exception {
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final IssueBaseDAOBean.IssueBaseResult result = daoBean.connectIssueComment("sessionID", -1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }

    @Test
    public void testConnectIssueTimeUnitUsed() throws Exception {
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final IssueBaseDAOBean.IssueBaseResult result = daoBean.connectIssueTimeUnitUsed("sessionID", -1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }
}
