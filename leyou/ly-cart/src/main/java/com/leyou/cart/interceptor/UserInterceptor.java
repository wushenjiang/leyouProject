package com.leyou.cart.interceptor;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.cart.config.JwtProperties;
import com.leyou.common.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: UserInterceptor
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/16  18:56
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    private static final  ThreadLocal<UserInfo> tl = new ThreadLocal<>();
    private  JwtProperties prop;

    public UserInterceptor(JwtProperties prop) {
        this.prop  = prop;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取cookie中的token
        String token = CookieUtils.getCookieValue(request, prop.getCookieName());
        try {
            // 解析token
            UserInfo user = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            // 传递user
            tl.set(user);
            // 放行
            return true;
        } catch (Exception e) {
            log.error("[购物车服务] 解析用户身份失败",e);
           return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 最后用完数据, 清空数据
        tl.remove();
    }

    public static UserInfo getUser(){
        return tl.get();
    }
}
