package com.bjpowernode.money.service;

import com.bjpowernode.money.model.User;

/**
 * 用户业务接口
 */
public interface UserService {

    /**
     *  //平台用户数：总人数
     * @return
     */
    Long queryUserCount();

    /**
     * 注册：根据手机号码查询用户数量
     * @param phone
     * @return
     */
    int checkPhone(String phone);

    /**
     * 注册：注册
     * @param phone
     * @param loginPassword
     * @return
     */
    User register(String phone, String loginPassword);

    /**
     * 登录：登录
     * @param phone
     * @param loginPassword
     * @return
     */
    User login(String phone, String loginPassword);

}
