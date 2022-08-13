package com.user.service;

import com.user.vo.PagedResponse;
import com.user.vo.User;

import java.util.List;

public interface UserService {
    User findById(Long id);

    User saveUser(User user);

    User updateUser(User user);

    public void deleteUserById(Long id);

    List<User> findAllUsers();

    PagedResponse<User> findPaginated(int page, int size, String orderBy);
}
