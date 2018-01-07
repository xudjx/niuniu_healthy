package com.niuniu.motion.core.manager;

import com.niuniu.motion.common.exception.NiuSvrException;
import com.niuniu.motion.dto.AccountDTO;
import com.niuniu.motion.dto.ProfileDTO;

public interface AccountManager {

    AccountDTO registerAccount(AccountDTO accountDTO) throws NiuSvrException;

    AccountDTO logon(AccountDTO accountDTO) throws NiuSvrException;

    ProfileDTO getProfileByAccount(long accountId) throws NiuSvrException;

    ProfileDTO submitInfo(long accountId, ProfileDTO profileDTO)throws NiuSvrException;
}
