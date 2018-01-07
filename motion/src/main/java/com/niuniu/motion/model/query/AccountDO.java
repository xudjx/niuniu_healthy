package com.niuniu.motion.model.query;

import javax.persistence.*;

@Entity
@Table(name = "t_account")
public class AccountDO {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "account_name")
    private String accountName;
    @Column(name = "password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
