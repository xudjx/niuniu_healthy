package com.niuniu.motion.core.manager;

import com.niuniu.motion.common.exception.NiuSvrException;
import com.niuniu.motion.dto.AccountDTO;

public interface AccountManager {

    AccountDTO registerAccount(AccountDTO accountDTO) throws NiuSvrException;

    AccountDTO logon(AccountDTO accountDTO) throws NiuSvrException;
}
