package com.reachauto.hkr.cr.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by haojr on 17/06/05.
 */
@Data
public class Entity implements Serializable {

    private static final long serialVersionUID = -9073095998693952756L;

    private Long id;
    private String remarks;
    private Integer deleted;
    private String createdBy;
    private Date createdAt;
    private String updatedBy;
    private Date updatedAt;
}
