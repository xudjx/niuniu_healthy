package com.niuniu.motion.core.managerImpl;

import com.niuniu.motion.common.exception.NiuSvrException;
import com.niuniu.motion.common.exception.ServerCommonErrorCode;
import com.niuniu.motion.core.manager.AccountManager;
import com.niuniu.motion.dto.AccountDTO;
import com.niuniu.motion.model.dao.AccountDAO;
import com.niuniu.motion.model.query.AccountDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountManagerImpl implements AccountManager {

    @Autowired
    AccountDAO accountDAO;

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
}
