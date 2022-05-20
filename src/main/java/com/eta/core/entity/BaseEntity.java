

package com.eta.core.entity;

import javax.persistence.Transient;

/**
 * 基础信息
 *
 */
public class BaseEntity {
    /*@Id
    @Column(name = "Id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;*/

    @Transient
    private Integer page = 1;

    @Transient
    private Integer limit = 10;

    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
