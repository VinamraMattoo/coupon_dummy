package com.portea.cpnen.domain;

import javax.persistence.*;

/**
 * A read-only implementation for an existing table, as coupon engine system is not expected to write to the
 * underlying table.
 */
@Entity
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "sub_service")
    private Boolean subService;

    @Column(name = "name", columnDefinition = "varchar(64)")
    private String name;

    @Column(name = "display_name", columnDefinition = "varchar(256)")
    private String displayName;

    @Column(name = "parentId")
    private Integer parentId;

    @Column(name = "deleted")
    private Boolean deleted;

    public Service() {}

    public Integer getId() {
        return id;
    }

    public Boolean getSubService() {
        return subService;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        return !(id != null ? !id.equals(service.id) : service.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
