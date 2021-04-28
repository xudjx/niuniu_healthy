package com.niuniu.motion.dto;

import java.sql.Timestamp;

public class RecordWeightDTO extends BaseDTO{

    private long accountId ;
    private double weight ;
    private Timestamp inputTime ;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Timestamp getInputTime() {
        return inputTime;
    }

    public void setInputTime(Timestamp inputTime) {
        this.inputTime = inputTime;
    }
}
