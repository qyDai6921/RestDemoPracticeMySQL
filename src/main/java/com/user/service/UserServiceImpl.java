package com.user.service;

import com.user.dao.UserRepository;
import com.user.entity.UserEntity;
import com.user.util.UserEntityConverter;
import com.user.vo.PagedResponse;
import com.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public List<User> findAllUsers() {
        List<UserEntity> users = userRepo.findAll();

//        List<User> al = new ArrayList<>();
//        for (int i = 0; i < users.size(); i++) {
//            User user = new User();
//            UserEntity userEntity = users.get(i);
//
//            user.setId(userEntity.getId());
//            user.setName(userEntity.getName());
//            user.setAge(userEntity.getAge());
//            user.setSalary(userEntity.getSalary());
//
//            al.add(user);
//        }
//        return al;

        // Java8 new feature: Stream API
        return users.stream()
                .map(e -> new User(e.getId(), e.getName(), e.getAge(), e.getSalary()))
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        UserEntity userEntity = userRepo.findById(id).orElse(null); // Optional <com.user.entity.UserEntity>
        return UserEntityConverter.convertEntityToUser(userEntity);
    }

    @Transactional // javax
    @Override
    public User saveUser(User user) {
        UserEntity userEntity = userRepo.save(new UserEntity(user.getId(), user.getName(), user.getAge(), user.getSalary()));
        return UserEntityConverter.convertEntityToUser(userEntity);
    }

    @Override
    public User updateUser(User user) {
        // saveAndFlush: 保存实体并立即刷新更改
        UserEntity userEntity = userRepo.saveAndFlush(new UserEntity(user.getId(), user.getName(), user.getAge(), user.getSalary()));
        return UserEntityConverter.convertEntityToUser(userEntity);
    }

    @Override
    public void deleteUserById(Long id) {
//        userRepo.findAll(PageRequest.of(1, 2));
        userRepo.deleteById(id);
    }


    @Override
    public PagedResponse<User> findPaginated(int page, int size, String orderBy) {
        // 定义排列顺序
        Sort sort = null; // Spring
        if (orderBy != null) {
            sort = Sort.by(Sort.Direction.ASC, orderBy);
        }

        Page<UserEntity> page1 = userRepo.findAll(PageRequest.of(page, size, sort));
        List<UserEntity> list = page1.getContent();

        PagedResponse<User> result = new PagedResponse<>();
        result.setPage(page1.getNumber());
        result.setRows(page1.getSize());
        result.setTotalPage(page1.getTotalPages());
        result.setTotalElement(page1.getTotalElements());
        result.setOrder(page1.getSort().toString());
        result.setBody(list.stream()
                .map(e -> new User(e.getId(), e.getName(), e.getAge(), e.getSalary()))
                .collect(Collectors.toList()));

        return result;
    }


}
