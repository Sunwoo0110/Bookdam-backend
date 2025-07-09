package com.sunwoo.bookdam.common.model;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseDateEntity {

    @CreationTimestamp
    @Column(name = "create_date", nullable = false, columnDefinition = "datetime(3) comment '생성일'")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date", nullable = false, columnDefinition = "datetime(3) comment '수정일'")
    private LocalDateTime updateDate;
}
