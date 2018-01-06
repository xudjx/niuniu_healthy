package com.niuniu.motion.core.managerImpl;

import com.niuniu.motion.common.exception.NiuSvrException;
import com.niuniu.motion.common.exception.ServerCommonErrorCode;
import com.niuniu.motion.core.manager.AccountManager;
import com.niuniu.motion.dto.AccountDTO;
import com.niuniu.motion.dto.ProfileDTO;
import com.niuniu.motion.model.dao.AccountDAO;
import com.niuniu.motion.model.dao.ProfileDAO;
import com.niuniu.motion.model.query.AccountDO;
import com.niuniu.motion.model.query.ProfileDO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountManagerImpl implements AccountManager {

    @Autowired
    AccountDAO accountDAO;
    @Autowired
    ProfileDAO profileDAO;

    @Override
    public AccountDTO registerAccount(AccountDTO accountDTO) throws NiuSvrException  {
        AccountDO accountDO = accountDAO.findByAccount(accountDTO.getAccount());
        if (accountDO != null) {
            throw new NiuSvrException(ServerCommonErrorCode.OBJECT_EXISTS.getCode(), ServerCommonErrorCode.OBJECT_EXISTS.getMsg(), ServerCommonErrorCode.OBJECT_EXISTS.getChineseMsg());
        }
        AccountDO newAccount = new AccountDO();
        newAccount.setAccount(accountDTO.getAccount());
        newAccount.setPassword(accountDTO.getPassword());
        accountDAO.save(newAccount);
        return accountDTO;
    }

    @Override
    public AccountDTO logon(AccountDTO accountDTO) throws NiuSvrException {
        AccountDO accountDO = accountDAO.findByAccount(accountDTO.getAccount());
        if (accountDO == null) {
            throw new NiuSvrException(ServerCommonErrorCode.OBJECT_NOT_FOUND.getCode(), ServerCommonErrorCode.OBJECT_NOT_FOUND.getMsg(), ServerCommonErrorCode.OBJECT_NOT_FOUND.getChineseMsg());
        }
        if (!accountDO.getPassword().equals(accountDTO.getPassword())) {
            throw new NiuSvrException(ServerCommonErrorCode.PARAM_ERROR.getCode(), ServerCommonErrorCode.PARAM_ERROR.getMsg(), ServerCommonErrorCode.PARAM_ERROR.getChineseMsg());
        }
        return accountDTO;
    }

    @Override
    public ProfileDTO getProfileByAccount(String account) throws NiuSvrException {
        ProfileDO dbProfile = profileDAO.findByAccount(account);
        ProfileDTO resultProfile = new ProfileDTO();
        resultProfile.setAccount(account);
        if (dbProfile != null) {
            resultProfile.setAge(dbProfile.getAge());
            resultProfile.setAvatar(dbProfile.getAvatar());
            resultProfile.setUserName(dbProfile.getUserName());
            resultProfile.setGender(dbProfile.getGender());
        }
        return resultProfile;
    }

    @Override
    public ProfileDTO submitInfo(ProfileDTO profileDTO) throws NiuSvrException {
        ProfileDO dbProfile = profileDAO.findByAccount(profileDTO.getAccount());
        ProfileDTO resultProfile = new ProfileDTO();
        if (dbProfile == null) {
            ProfileDO profileDO = new ProfileDO();
            profileDO.setAccount(profileDTO.getAccount());
            profileDO.setAge(profileDTO.getAge());
            profileDO.setAvatar(profileDTO.getAvatar());
            profileDO.setGender(profileDTO.getGender());
            profileDO.setUserName(profileDTO.getUserName());
            profileDAO.save(profileDO);
        } else {
            if (profileDTO.getAge() != null) {
                dbProfile.setAge(profileDTO.getAge());
            }
            if (profileDTO.getGender() != null) {
                dbProfile.setGender(profileDTO.getGender());
            }
            if (!StringUtils.isEmpty(profileDTO.getAvatar())) {
                dbProfile.setAvatar(profileDTO.getAvatar());
            }
            if (!StringUtils.isEmpty(profileDTO.getUserName())) {
                dbProfile.setUserName(profileDTO.getUserName());
            }
            profileDAO.save(dbProfile);
        }
        return resultProfile;
    }
}
