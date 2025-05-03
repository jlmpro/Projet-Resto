package com.moris.resto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @CreatedDate
    @JsonIgnore
    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate ;
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    @JsonIgnore
    private Instant lastModifiedDate ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
