package org.rapidpm.orm.prj.projectmanagement.execution.issuetracking.workflow;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 09.01.11
 * Time: 18:31
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

public class Workflow {
    private static final Logger logger = Logger.getLogger(Workflow.class);

//    @Id
//    @TableGenerator(name = "PKGenWorkflow", table = "pk_gen", pkColumnName = "gen_key",
//            pkColumnValue = "Workflow_id", valueColumnName = "gen_value", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenWorkflow")
//    private Long id;



}
