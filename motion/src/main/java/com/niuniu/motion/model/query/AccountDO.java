package com.niuniu.motion.model.query;

import javax.persistence.*;

@Entity
@Table(name = "t_account")
public class AccountDO {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "account_id")
    private String account;
    @Column(name = "password")
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
