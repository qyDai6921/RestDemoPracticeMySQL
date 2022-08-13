package com.user.controller;


import com.user.exception.UserException;
import com.user.exception.UserNotFoundException;
import com.user.service.UserService;
import com.user.util.Constants;

import com.user.vo.PagedResponse;
import com.user.vo.ResponseMessage;
import com.user.vo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("/api")
@Api(value = "User", description = "REST API for Users", tags = {"User"})
public class UserRestController {
    private static Logger logger = LoggerFactory.getLogger(UserRestController.class);

    UserService userService;

    Constants messages;

    @Autowired
    public UserRestController(UserService userService, Constants messages) {
        this.userService = userService;
        this.messages = messages;
    }


    //http://localhost:8009/swagger-ui.html#/

    // database: http://localhost:8009/h2-console

    /**
     * 1. GET: Get user by using pagination, if no parameters are provided, the first page with 10 records will be returned
     **/
    @ApiOperation("get users by page:")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<PagedResponse<User>> getUserPagenation(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                                                 @RequestParam(required = false, defaultValue = "5") Integer rows,
                                                                 @RequestParam(required = false, defaultValue = "name") String orderBy) {
        PagedResponse<User> users = userService.findPaginated(pageNo, rows, orderBy);
        if (users.isEmpty()) {
//            return new ResponseEntity<PagedResponse<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND

            throw new UserNotFoundException(messages.getMessage("USER_NOT_FOUND!"));
        }

        return new ResponseEntity<PagedResponse<User>>(users, HttpStatus.OK);
    }


    ///////////////////////////////////////////////////////

    /**
     * 2. GET: retrieves a single user
     **/
    @ApiOperation("get a single user: ")
    @RequestMapping(value = "/user/{uid}", method = RequestMethod.GET)

    // @RequestMapping 定义了url路劲，和http方法
    // @PathVariable对应@RequestMapping的这个路径  的 变量
    public ResponseEntity<?> getUser(@PathVariable("uid") Long id) throws UserException {
        System.out.println("Fetching User with id " + id);

        User user = userService.findById(id);
        if (user == null) {

            System.out.println("User with id " + id + " not found");
//            return new ResponseEntity<User>(HttpStatus.NOT_FOUND); // 页面上显示404

            throw new UserNotFoundException(messages.getMessage("USER_NOT_FOUND!")); // 页面上显示500
        }

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    ///////////////////////////////////////////////////////

    /**
     * 3. POST: create a user
     **/
    @ApiOperation("create a user:")
    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = "application/json")
//    public ResponseEntity<ResponseMessage> createUser(@Validated User user, UriComponentsBuilder ucBuilder) { // User里有验证条件
    public ResponseEntity<ResponseMessage> createUser(@Validated @RequestBody User user, UriComponentsBuilder ucBuilder) { // 有一个RequestBody输入模板
        System.out.println("Creating User " + user.getName());

        User savedUser = userService.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        // @Nullable URI location
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(messages.getMessage("USER_CREATED!"), savedUser), headers, HttpStatus.CREATED);
    }

    ///////////////////////////////////////////////////////

    /**
     * 4. PUT: update a user
     **/
    @ApiOperation("update a user:")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @Validated @RequestBody User user) {
        User currentUser = userService.findById(id);
        if (currentUser == null) {
            throw new UserNotFoundException(messages.getMessage("USER_NOT_FOUND"));
        }

        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());
        userService.updateUser(currentUser);

        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }


    ///////////////////////////////////////////////////////

    /**
     * 5. DELETE: delete a user
     **/
    @ApiOperation("delete a user:")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new UserNotFoundException(messages.getMessage("USER_NOT_FOUND"));
        }

        userService.deleteUserById(id); // service端执行功能

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(messages.getMessage("USER_DELETED!"), user), HttpStatus.OK);
    }

    ///////////////////////////////////////////////////////
    /** 6. @throws UserException**/

}
