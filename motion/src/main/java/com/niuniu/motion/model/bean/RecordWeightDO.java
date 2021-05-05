package com.niuniu.motion.model.bean;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_record_weight")
public class RecordWeightDO {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "account_id")
    private Long accountId;
    @Column(name = "weight")
    private double weight;
    @Column(name = "inputTime")
    private Timestamp inputTime;

    public Timestamp getInputTime() {
        return inputTime;
    }

    public void setInputTime(Timestamp inputTime) {
        this.inputTime = inputTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
