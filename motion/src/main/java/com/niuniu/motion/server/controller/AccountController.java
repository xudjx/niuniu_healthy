package com.niuniu.motion.server.controller;

import com.niuniu.motion.common.constant.Role;
import com.niuniu.motion.common.exception.NiuSvrException;
import com.niuniu.motion.common.exception.ServerCommonErrorCode;
import com.niuniu.motion.core.manager.AccountManager;
import com.niuniu.motion.dto.ProfileDTO;
import com.niuniu.motion.model.AccessTokenInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/account")
public class AccountController {

    @Autowired
    AccountManager accountManager;

    @ModelAttribute("token")
    public AccessTokenInfo getUser(HttpServletRequest request) throws NiuSvrException {
        AccessTokenInfo token = (AccessTokenInfo) request.getAttribute("token");

        if (token.getRole() != Role.USER.getRole()) {
            throw new NiuSvrException(ServerCommonErrorCode.INCORRECT_AUTH_TOKEN);
        }
        return token;
    }

    @RequestMapping(value = "/profile/{accountId}", method = RequestMethod.POST)
    public ProfileDTO submitInfo(@PathVariable String accountId,@ModelAttribute
            ("token") AccessTokenInfo token, @RequestBody ProfileDTO profileDTO) throws NiuSvrException {
        if (profileDTO == null || StringUtils.isEmpty(accountId)) {
            throw new NiuSvrException(ServerCommonErrorCode.PARAM_ERROR);
        }
        Long uID = token.getTokenId();
        ProfileDTO resultProfile = accountManager.submitInfo(uID, profileDTO);
        return resultProfile;
    }

    @RequestMapping(value = "/profile/{accountId}", method = RequestMethod.GET)
    public ProfileDTO getUserProfile(@PathVariable String accountId,
                                     @ModelAttribute("token") AccessTokenInfo token) throws NiuSvrException {
        if (StringUtils.isEmpty(accountId)) {
            throw new NiuSvrException(ServerCommonErrorCode.PARAM_ERROR);
        }

        Long uID = token.getTokenId();
        ProfileDTO profileDTO = accountManager.getProfileByAccount(uID);
        return profileDTO;
    }
}
