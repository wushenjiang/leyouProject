package com.leyou.auth.service;

import com.leyou.auth.client.UserClient;
import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: AuthService
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/15  17:23
 */
@Service
@Slf4j
public class AuthService {
    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtProperties properties;

    public String authentication(String username, String password) {
        try {
            // 调用微服务进行查询
            User user = userClient.queryUser(username, password);
            // 如果查询为null,直接返回null
            if (user == null) {
                return null;
            }
            // 如果有查询结果,则生成token
            String token = JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()),
                    properties.getPrivateKey(), properties.getExpire());
            return token;
        } catch (Exception e) {
            // 发生错误,记录日志
            log.error("[授权中心] 授权异常!错误为: " + e);
        }
        return null;
    }
}
