package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;

import org.rapidpm.persistence.prj.projectmanagement.planning.management.travel.PlannedAccomodation;

import javax.persistence.*;

/**
 *
 * User: sven.ruppert
 * Date: 02.12.11
 * Time: 10:35
 *
 *
 */

public class IssueAccomodation extends IssueBase {

    @Id
    @TableGenerator(name = "PKGenIssueAccomodation", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "IssueAccomodation_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenIssueAccomodation")
    private Long id;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private PlannedAccomodation accommodation;

    public IssueAccomodation(final Long projectId) {
        super(projectId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlannedAccomodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(PlannedAccomodation accommodation) {
        this.accommodation = accommodation;
    }
}
