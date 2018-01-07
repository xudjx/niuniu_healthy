package com.niuniu.motion.server.controller;

import com.niuniu.motion.common.exception.NiuSvrException;
import com.niuniu.motion.common.exception.ServerCommonErrorCode;
import com.niuniu.motion.core.manager.AccountManager;
import com.niuniu.motion.dto.AccountDTO;
import com.niuniu.motion.spring.security.TokenFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/account")
public class LogonController {

    private static final Logger logger = LoggerFactory.getLogger(LogonController.class);

    @Autowired
    AccountManager accountManager;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public AccountDTO registerAccount(@RequestParam String account,
                                      @RequestParam String password) throws NiuSvrException {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountName(account);
        accountDTO.setPassword(password);
        accountManager.registerAccount(accountDTO);
        return accountDTO;
    }


    @RequestMapping(value = "/logon", method = RequestMethod.GET)
    public AccountDTO logonAccount(@RequestParam String account,
                                   @RequestParam String password) throws NiuSvrException {
        if (StringUtils.isEmpty(password)) {
            throw new NiuSvrException(ServerCommonErrorCode.PARAM_ERROR);
        }
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountName(account);
        accountDTO.setPassword(password);
        AccountDTO resultAccount = accountManager.logon(accountDTO);
        return resultAccount;
    }
}
