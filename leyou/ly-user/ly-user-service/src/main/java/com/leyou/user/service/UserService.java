package com.leyou.user.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.utils.CodecUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: UserService
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/15  14:37
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final  String KEY_PREFIX = "user:verify:phone:";
    public Boolean checkData(String data, Integer type) {
        // 判断数据类型
        User record = new User();
        switch (type){
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                throw new LyException(ExceptionEnum.INVALID_USER_DATA_TYPE);
        }
        int count = userMapper.selectCount(record);
        return count == 0;
    }

    public void sendCode(String phone) {
        // 生成key
        String key = KEY_PREFIX + phone;
        // 生成验证码
        String code = NumberUtils.generateCode(6);
        Map<String,String> msg=  new HashMap<>();
        msg.put("phone",phone);
        msg.put("code",code);
        // 发送验证码
        amqpTemplate.convertAndSend("ly.sms.exchange","sms.verify.code",msg);

        // 保存验证码
        redisTemplate.opsForValue().set(key,code,5, TimeUnit.MINUTES);

        log.info("存入redis的验证码为:" + code);


    }

    public void register(User user, String code) {
        String key = KEY_PREFIX + user.getPhone();
        // 从redis中取出验证码
        String codeCache = redisTemplate.opsForValue().get(key);
        log.info("redis中的验证码为：" + codeCache + "发送的验证码为:" + code);
        // 检验验证码是否正确
        if (!code.equals(codeCache)) {
            // 不正确，抛出异常
            throw new LyException(ExceptionEnum.USER_CODE_ERROR);
        }
        user.setId(null);
        user.setCreated(new Date());
        // 生成salt
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        // 对密码进行加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        // 写入数据库
        int count = userMapper.insertSelective(user);
        if(count == 1){
            // 注册成功,删除redis中的code
            redisTemplate.delete(key);
        }else{
            // 注册失败,抛出异常
            throw new LyException(ExceptionEnum.USER_REGISTER_ERROR);
        }

    }

    public User queryUser(String username, String password) {
        // 查询用户
        User record = new User();
        record.setUsername(username);
        User user = userMapper.selectOne(record);
        // 校验用户名,为空抛出异常
        if (user == null) {
            throw new LyException(ExceptionEnum.USER_NAME_ERROR);
        }
        // 校验密码,注意加上salt来校验
        if (!user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))) {
            throw new LyException(ExceptionEnum.USER_PASSWORD_ERROR);
        }
        return user;
    }
}
