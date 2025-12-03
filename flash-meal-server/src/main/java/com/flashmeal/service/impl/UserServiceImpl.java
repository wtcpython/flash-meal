package com.flashmeal.service.impl;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.flashmeal.constant.MessageConstant;
import com.flashmeal.dto.UserLoginDTO;
import com.flashmeal.entity.User;
import com.flashmeal.exception.LoginFailedException;
import com.flashmeal.mapper.UserMapper;
import com.flashmeal.properties.WeChatProperties;
import com.flashmeal.service.UserService;
import com.flashmeal.utils.HttpClientUtil;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    private final WeChatProperties weChatProperties;
    private final UserMapper userMapper;

    public UserServiceImpl(WeChatProperties weChatProperties, UserMapper userMapper) {
        this.weChatProperties = weChatProperties;
        this.userMapper = userMapper;
    }

    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        // 获取微信 openid
        Map<String, String> params = Map.of(
                "appid", weChatProperties.getAppid(),
                "secret", weChatProperties.getSecret(),
                "js_code", userLoginDTO.getCode(),
                "grant_type", "authorization_code");

        String result = HttpClientUtil.doGet(WX_LOGIN_URL, params);
        // 判断是否为空
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.createObjectNode();
        try {
            node = objectMapper.readTree(result);
        } catch (JacksonException e) {
            e.printStackTrace();
        }
        String openid = node.get("openid").asString();
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        User user = userMapper.getByOpenid(openid);
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        return user;
    }

}
