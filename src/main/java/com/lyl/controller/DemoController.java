package com.lyl.controller;

import com.lyl.annotation.GetDistributedLock;
import com.lyl.annotation.PrintLog;
import com.lyl.annotation.ResponseResult;
import com.lyl.bean.User;
import com.lyl.utils.Response;
import com.lyl.utils.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 【 木子雷 】 公众号
 * @PACKAGE_NAME: com.lyl.controller
 * @ClassName: DemoController
 * @Description:
 * @Date: 2020-11-10 10:02
 **/
@RestController
@RequestMapping(value = "/v1/api")
public class DemoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    // ===================================自定义注解对返回响应的包装====================================== //

    /**
     * 这就是一个普通的API接口，使用最常见的方式对返回响应做的封装
     *
     * @return
     */
    @GetMapping("/user/findAllUser")
    public Response<List<User>> findAllUser() {
        logger.info("开始查询所有数据...");

        List<User> findAllUser = new ArrayList<>();
        findAllUser.add(new User("木子雷", 26));
        findAllUser.add(new User("公众号", 28));

        // 返回响应进行包装
        Response response = new Response(findAllUser, ResponseCode.SUCCESS.code(), ResponseCode.SUCCESS.message());

        logger.info("response: {} \n", response.toString());
        return response;
    }


    /**
     * 没有使用自定义注解对 返回响应 进行包装
     *
     * @return
     */
    @GetMapping("/user/findAllUserNo")
    public List<User> findAllUserNo() {

        List<User> findAllUser = new ArrayList<>();
        findAllUser.add(new User("木子雷", 26));
        findAllUser.add(new User("公众号", 28));

        return findAllUser;
    }


    /**
     * 这就是一个普通的API接口，使用最常见的方式对返回响应做的封装
     *
     * @return
     */
    @ResponseResult
    @GetMapping("/user/findAllUserByAnnotation")
    public List<User> findAllUserByAnnotation() {
        logger.info("开始查询所有数据...");

        List<User> findAllUser = new ArrayList<>();
        findAllUser.add(new User("木子雷", 26));
        findAllUser.add(new User("公众号", 28));

        logger.info("使用 @ResponseResult 自定义注解进行响应的包装，使controller代码更加简介");
        return findAllUser;
    }


    // =================================自定义注解优雅的处理分布式锁====================================== //

    /**
     * 使用 @GetDistributedLock 自定义注解优雅使用分布式锁，代码看起来更加简洁
     *
     * @return
     */
    @GetDistributedLock(lockKey = "userLock")
    @GetMapping("/user/getDistributedLock")
    public boolean getUserDistributedLock() {
        logger.info("获取分布式锁...");
        // 写具体的业务逻辑

        return true;
    }


    // ==================================自定义注解实现日志打印====================================== //

    /**
     * 使用 @PrintLog 自定义注解优雅实现日志打印
     *
     * @param id
     * @return
     */
    @PrintLog
    @GetMapping(value = "/user/findUserNameById/{id}", produces = "application/json;charset=utf-8")
    public String findUserNameById(@PathVariable("id") int id) {
        // 模拟根据id查询用户名
        String userName = "木子雷 公众号";
        return userName;
    }

}
