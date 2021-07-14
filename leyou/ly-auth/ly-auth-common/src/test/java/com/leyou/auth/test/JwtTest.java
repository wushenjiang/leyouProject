package com.leyou.auth.test;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @ClassName: JwtTest
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/15  17:03
 */

public class JwtTest {
    private static final String pubKeyPath = "F:\\test\\rsa.pub";

    private static final String priKeyPath = "F:\\test\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU5NTIzNjY2MH0.P_jmU7vqBjGmH_SNE7mHzG57Irdg1uGseIMyIFJ1FrFdFo2n2VbtmXSO5mzbqTYzyR4zbhL9Wi_ETaUJYcTdUzWW4q-yfnwrX9KC4GFnqwdgDA-eERbRFWfavJC_4p3Gmg4YGO5PluibHwV4wfVPFBa9jAgsjwCQqM_Jg39ReLQ";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}
