package com.niuniu.motion.server.controller;

import com.niuniu.motion.common.exception.NiuSvrException;
import com.niuniu.motion.common.exception.ServerCommonErrorCode;
import com.niuniu.motion.core.manager.AccountManager;
import com.niuniu.motion.dto.AccountDTO;
import com.niuniu.motion.dto.ProfileDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/account")
public class AccountController {

    @Autowired
    AccountManager accountManager;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public AccountDTO registerAccount(@RequestParam String account,
                                      @RequestParam String password) throws NiuSvrException {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccount(account);
        accountDTO.setPassword(password);
        accountManager.registerAccount(accountDTO);
        return accountDTO;
    }


    @RequestMapping(value = "/logon", method = RequestMethod.GET)
    public ProfileDTO logonAccount(@RequestParam String account,
                                   @RequestParam String password) throws NiuSvrException {
        if (StringUtils.isEmpty(password)) {
            throw new NiuSvrException(ServerCommonErrorCode.PARAM_ERROR.getCode(), ServerCommonErrorCode.PARAM_ERROR.getMsg(), ServerCommonErrorCode.PARAM_ERROR.getChineseMsg());
        }
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccount(account);
        accountDTO.setPassword(password);
        accountManager.logon(accountDTO);
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setAccount(account);
        return profileDTO;
    }

}
