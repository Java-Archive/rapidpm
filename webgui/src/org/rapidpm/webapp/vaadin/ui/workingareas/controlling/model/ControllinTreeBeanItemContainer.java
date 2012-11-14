package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.model;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 12.11.12
 * Time: 16:43
 * To change this template use File | Settings | File Templates.
 */
public class ControllinTreeBeanItemContainer extends BeanItemContainer<PlannedProject> implements Container.Hierarchical {

    public ControllinTreeBeanItemContainer(Class type) throws IllegalArgumentException {
        super(type);
        HierarchicalContainer c = new HierarchicalContainer();
    }

    @Override
    public Collection<?> getChildren(Object itemId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getParent(Object itemId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<?> rootItemIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean setParent(Object itemId, Object newParentId) throws UnsupportedOperationException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean areChildrenAllowed(Object itemId) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean setChildrenAllowed(Object itemId, boolean areChildrenAllowed) throws UnsupportedOperationException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isRoot(Object itemId) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasChildren(Object itemId) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
