package com.huaixuan.network.biz.service.user;

import java.util.List;

import com.huaixuan.network.biz.domain.user.User;

public interface UserService {

    User getUser(String userId);

    User getUserByUsername(String username);

    List<User> getUsers(User user);

    Long saveUser(User user);

    void removeUser(String userId);

    void updateUserScrap(User user);
}
