package com.user.util;

import com.user.entity.UserEntity;
import com.user.vo.User;

public class UserEntityConverter {
    public static User convertEntityToUser(UserEntity userEntity) {
        if (userEntity != null) {
            User user = new User();
            user.setId(userEntity.getId());
            user.setName(userEntity.getName());
            user.setAge(userEntity.getAge());
            user.setSalary(userEntity.getSalary());
            return user;
        } else {
            return null;
        }
    }
}
