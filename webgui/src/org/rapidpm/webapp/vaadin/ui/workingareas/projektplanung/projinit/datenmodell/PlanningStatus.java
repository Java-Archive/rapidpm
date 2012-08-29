package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell;

import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Marco
 * Date: 20.08.12
 * Time: 12:22
 * To change this template use File | Settings | File Templates.
 */
    public class PlanningStatus {
        private static final Logger logger = Logger.getLogger(PlanningStatus.class);

        private Long id;
        private int orderNumber;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(int orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
