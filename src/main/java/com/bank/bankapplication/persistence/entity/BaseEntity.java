package com.bank.bankapplication.persistence.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "INSERTED_AT")
    private LocalDateTime insertedAt;

    @Column(name = "INSERTED_BY")
    private String insertedBy;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Should be implemented by subclass.");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Should be implemented by subclass.");
    }


    @PrePersist
    private void onPrePersist() {
        insertedAt = now();
        insertedBy = "System";
        updatedAt = insertedAt;
        updatedBy = insertedBy;
    }

    @PreUpdate
    private void onPreUpdate() {
        updatedAt = now();
        updatedBy = "System";
    }
}