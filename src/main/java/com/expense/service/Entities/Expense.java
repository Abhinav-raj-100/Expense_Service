package com.expense.service.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.common.utils.Time;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "external_id")
    private String externalId;

    @Column(name="user_id")
    private String userId;

    @Column(name = "amount")
    private String amount;

    @Column(name="currency")
    private String currency;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "merchant")
    private String merchant;

    @PrePersist
    @PreUpdate
    private void generateExternalId()
    {
        if(this.externalId ==null)
        {
            this.externalId = UUID.randomUUID().toString();
        }
    }

    @PrePersist
    @PreUpdate
    private void generateCreatedAt()
    {
        if(this.createdAt==null)
        {
            this.createdAt= Timestamp.valueOf(LocalDateTime.now());
        }
    }
}

