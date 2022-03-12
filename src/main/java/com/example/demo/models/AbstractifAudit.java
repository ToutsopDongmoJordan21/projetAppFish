package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Calendar;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractifAudit {

    /** The version. */
    @Version
    protected Long version = 0L;

    /** The created date. */
    @CreatedDate
    @DateTimeFormat(style = "M-")
    protected Calendar createdDate = Calendar.getInstance();

    /** The created by. */
    @CreatedBy
    protected String createdBy = "SYSTEM";

    /** The modified date. */
    @LastModifiedDate
    @DateTimeFormat(style = "M-")
    protected Calendar modifiedDate = Calendar.getInstance();

    /** The modified by. */
    @LastModifiedBy
    protected String modifiedBy = "SYSTEM";
}
