package com.bjpowernode.money.mapper;

import com.bjpowernode.money.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //平台用户数：总人数
    Long selectUserCount();

    //注册：根据手机号码查询用户数量
    int selectUserCountByPhone(String phone);

    //登录：登录
    User selectUserByPhoneAndPasswd(String phone, String loginPassword);

}