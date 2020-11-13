package com.lyl.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @author 【 木子雷 】 公众号
 * @Title: User
 * @Description: 用户
 * @date: 2019年8月23日 下午4:21:11
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private int age;


    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
