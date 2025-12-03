package com.flashmeal.service;

import com.flashmeal.dto.UserLoginDTO;
import com.flashmeal.entity.User;

public interface UserService {
    User wxLogin(UserLoginDTO userLoginDTO);
}
