package com.niuniu.motion.core.managerImpl;

import com.google.gson.Gson;
import com.niuniu.motion.common.constant.Role;
import com.niuniu.motion.common.constant.TimePeriod;
import com.niuniu.motion.common.exception.NiuSvrException;
import com.niuniu.motion.common.exception.ServerBsErrorCode;
import com.niuniu.motion.common.exception.ServerCommonErrorCode;
import com.niuniu.motion.common.pojo.NiuniuRedisTemplate;
import com.niuniu.motion.common.util.AuthCodeUtil;
import com.niuniu.motion.common.util.IdConverterUtil;
import com.niuniu.motion.common.util.RedisUtil;
import com.niuniu.motion.config.AuthConfig;
import com.niuniu.motion.core.manager.AccountManager;
import com.niuniu.motion.dto.AccountDTO;
import com.niuniu.motion.dto.ProfileDTO;
import com.niuniu.motion.dto.RecordWeightDTO;
import com.niuniu.motion.model.AccessTokenInfo;
import com.niuniu.motion.model.dao.*;
import com.niuniu.motion.model.bean.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountManagerImpl implements AccountManager {

    private static final Logger logger = LoggerFactory.getLogger(AccountManagerImpl.class);

    @Autowired
    AccountDAO accountDAO;
    @Autowired
    ProfileDAO profileDAO;
    @Autowired
    AccessTokenDAO accessTokenDAO;
    @Autowired
    AuthConfig authConfig;
    @Autowired
    RecordWeightDAO recordWeightDAO;
    @Autowired
    NiuniuRedisTemplate redisTemplate;


    @Override
    public AccountDTO registerAccount(AccountDTO accountDTO) throws NiuSvrException  {
        AccountDO accountDO = accountDAO.findByAccountName(accountDTO.getAccountName());//去数据库里捞这个用户名
        if (accountDO != null) {   //如果已存在此用户则抛出异常
            logger.warn("Account has exist: {}", accountDO);
            throw new NiuSvrException(ServerCommonErrorCode.OBJECT_EXISTS);
        }
        AccountDO newAccount = new AccountDO();
        newAccount.setAccountName(accountDTO.getAccountName());
        newAccount.setPassword(accountDTO.getPassword());
        newAccount = accountDAO.save(newAccount);   //设置完成之后dao层save数据
        accountDTO.setAccountId(IdConverterUtil.commonEncrypt(String.valueOf(newAccount.getId())));
        return accountDTO;
    }

    @Override
    public AccountDTO logon(AccountDTO accountDTO) throws NiuSvrException {
        AccountDO accountDO = accountDAO.findByAccountName(accountDTO.getAccountName());
        if (accountDO == null) {
            logger.warn("Logon failed, account not exist: {}", accountDTO);
            throw new NiuSvrException(ServerCommonErrorCode.OBJECT_NOT_FOUND);
        }
        if (!accountDO.getPassword().equals(accountDTO.getPassword())) {
            logger.warn("Logon failed, password error: {}", accountDTO);
            throw new NiuSvrException(ServerCommonErrorCode.PARAM_ERROR);
        }

        // 登录成功生成加密id
        Long accountId = accountDO.getId();
        String accountCode = IdConverterUtil.commonEncrypt(String.valueOf(accountId));

        // 生成authToken
        AccessTokenInfo accessTokenInfo = new AccessTokenInfo();
        accessTokenInfo.setExpireTime(System.currentTimeMillis() + TimePeriod.ACCESS_EXPIRE_TIME);
        accessTokenInfo.setRole(Role.USER.getRole());
        accessTokenInfo.setTokenId(accountId);
        String authToken = AuthCodeUtil.authcodeEncode(new Gson().toJson(accessTokenInfo), authConfig.getAuthCodeSecret());

        // 将authToken存储到DB， 这个信息以后需要加到redis中
//        AccessTokenDO dbAccountTokenDO = accessTokenDAO.findByAccountId(accountId);
//        AccessTokenDO accessTokenDO = new AccessTokenDO();
//        if (dbAccountTokenDO != null) {
//            accessTokenDO.setId(dbAccountTokenDO.getId());
//        }
//        accessTokenDO.setAccessToken(authToken);
//        accessTokenDO.setAccountId(accountId);
//        accessTokenDO.setExpireTime(accessTokenInfo.getExpireTime());
//        accessTokenDAO.save(accessTokenDO);

        // 将token存储到redis中
        RedisUtil.setAccessToken(redisTemplate, accountId, authToken);

        // 将结果返回
        accountDTO.setAccountId(accountCode);
        accountDTO.setAuthToken(authToken);
        accountDTO.setAuthExpire(accessTokenInfo.getExpireTime());

        return accountDTO;
    }

    @Override
    public ProfileDTO getProfileByAccount(long accountId) throws NiuSvrException {
        ProfileDO dbProfile = profileDAO.findByAccountId(accountId);
        ProfileDTO resultProfile = new ProfileDTO();
        if (dbProfile == null) {
            logger.warn("Profile not exist: {}", accountId);
            throw new NiuSvrException(ServerBsErrorCode.PROFILE_NOT_EXIST);
        }
        AccountDO accountDO = accountDAO.findOne(accountId);
        BeanUtils.copyProperties(dbProfile, resultProfile);
        resultProfile.setAccountName(accountDO.getAccountName());
        resultProfile.setAccountId(IdConverterUtil.commonEncrypt(String.valueOf(accountDO.getId())));
        return resultProfile;
    }

    @Override
    public ProfileDTO submitInfo(long accountId, ProfileDTO profileDTO) throws NiuSvrException {
        ProfileDO dbProfile = profileDAO.findByAccountId(accountId);
        AccountDO accountDO = accountDAO.findOne(accountId);
        ProfileDTO resultProfile = new ProfileDTO();
        if (dbProfile == null) {
            ProfileDO profileDO = new ProfileDO();
            BeanUtils.copyProperties(profileDTO, profileDO);//
            profileDO.setAccountId(accountId);
            profileDAO.save(profileDO);
            BeanUtils.copyProperties(profileDO, resultProfile);
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
            BeanUtils.copyProperties(dbProfile, resultProfile);
        }
        resultProfile.setAccountName(accountDO.getAccountName());
        resultProfile.setAccountId(IdConverterUtil.commonEncrypt(String.valueOf(accountId)));
        return resultProfile;
    }

    /**
     *  recordweight
     *
     *  @param accountId id integer
     *  @param recordWeightDTO 请求传输过来的信息
     * */
    @Override
    public RecordWeightDTO setWeight(long accountId, RecordWeightDTO recordWeightDTO) throws NiuSvrException {
        RecordWeightDO recordWeightDO = new RecordWeightDO();
        BeanUtils.copyProperties(recordWeightDTO, recordWeightDO);
        recordWeightDO.setAccountId(accountId);
        recordWeightDAO.save(recordWeightDO);
        return recordWeightDTO;
    }
}
