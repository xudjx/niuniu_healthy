package com.niuniu.motion.spring.security;

import com.google.gson.Gson;
import com.niuniu.motion.common.constant.Role;
import com.niuniu.motion.common.exception.NiuSvrException;
import com.niuniu.motion.common.exception.ServerCommonErrorCode;
import com.niuniu.motion.common.pojo.NiuniuRedisTemplate;
import com.niuniu.motion.common.util.AuthCodeUtil;
import com.niuniu.motion.common.util.RedisUtil;
import com.niuniu.motion.config.AuthConfig;
import com.niuniu.motion.model.AccessTokenInfo;
import com.niuniu.motion.model.dao.AccessTokenDAO;
import com.niuniu.motion.model.query.AccessTokenDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    private static final String AUTH_CODE_NAME = "auth_token";

    @Autowired
    AuthConfig authConfig;
    @Autowired
    AccessTokenDAO accessTokenDAO;
    @Autowired
    NiuniuRedisTemplate redisTemplate;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getRequestURI().equals("/account/register")
                || req.getRequestURI().equals("/account/logon")
                || req.getRequestURI().startsWith("/rest/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = getAuthCodeFromCookie(req);
        try {
            AccessTokenInfo accessTokenInfo = decode(accessToken);
            request.setAttribute("token", accessTokenInfo);
            filterChain.doFilter(request, response);
            return;
        } catch (NiuSvrException e) {
            logger.warn("invalid token:{}",accessToken, e);
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String getAuthCodeFromCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(AUTH_CODE_NAME)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private AccessTokenInfo decode(String authCode) throws NiuSvrException {
        if (authCode == null) {
            throw new NiuSvrException(ServerCommonErrorCode.MUST_AUTH);
        }
        try {
            logger.info(authCode);
            String accessTokenDecode = AuthCodeUtil.authcodeDecode(authCode, authConfig.getAuthCodeSecret());
            AccessTokenInfo tokenInfo = new Gson().fromJson(accessTokenDecode, AccessTokenInfo.class);
            if (tokenInfo == null || tokenInfo.getTokenId() <= 0) {
                logger.warn("AccessToken decrypt: {}", tokenInfo);
                throw new NiuSvrException(ServerCommonErrorCode.INCORRECT_AUTH_TOKEN);
            }
            Long accountId = tokenInfo.getTokenId();
            String serverAccessToken = "";
            if (tokenInfo.getRole() == Role.USER.getRole()) {
                serverAccessToken = RedisUtil.getAccessToken(redisTemplate, accountId);
            }
            if (serverAccessToken == null || !authCode.equals(serverAccessToken)) {
                logger.warn("serverAccessToken:{}, userAccessToken:{}", serverAccessToken, authCode);
                throw new NiuSvrException(ServerCommonErrorCode.INCORRECT_AUTH_TOKEN);
            }
            if (tokenInfo.getExpireTime() < System.currentTimeMillis()) {
                logger.warn("userAccessToken expire:", tokenInfo);
                throw new NiuSvrException(ServerCommonErrorCode.EXPIRE_AUTH_TOKEN);
            }
            return tokenInfo;
        } catch (Exception e) {
            logger.warn("token decode exception:{}", e);
            throw new NiuSvrException(ServerCommonErrorCode.INVALID_AUTH_TOKEN);
        }
    }
}
