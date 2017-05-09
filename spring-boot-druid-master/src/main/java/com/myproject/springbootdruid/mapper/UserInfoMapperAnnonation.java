package com.myproject.springbootdruid.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.pagehelper.Page;
import com.myproject.springbootdruid.model.UserInfo;

@Mapper
public interface UserInfoMapperAnnonation {
	@Select("select * from user_info")
	Page<UserInfo> selectAll();
	  
	@Insert("insert into user_info(loginname, loginpwd,nickname) values(#{loginname},#{loginpwd},#{nickname})")
	public void insertT(UserInfo user);
	
	@Delete("delete from user_info where userid=#{id}")
	public void deleteById(int id);
	
	@Update("update user_info set name=#{loginname},loginpwd=#{loginpwd} where userid=#{userid}")
	public void updateT(UserInfo user);
	
	@Select("select * from user_info where userid=#{id}")
	public UserInfo getUser(Long id);
}
