package com.myproject.springbootdruid.mapper;

import com.github.pagehelper.Page;
import com.myproject.springbootdruid.model.UserInfo;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    @Select("select * from user_info")
    Page<UserInfo> selectAll();
}