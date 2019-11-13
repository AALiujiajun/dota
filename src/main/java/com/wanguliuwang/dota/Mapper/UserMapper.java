package com.wanguliuwang.dota.Mapper;

import com.wanguliuwang.dota.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into User(name,account_id,token,gmt_create,gmt_modified,avatar_url) values(#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified},#{avatar_url})")
     void insert(User user);

    @Select("select * from user where token =#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id =#{id}")
    User findById(@Param("id")Integer creator);

    @Select("select * from user where id =#{account_id}")
    User findByAccountID(@Param("account_id") String account_id);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmt_modified},gmt_create=#{gmt_create},avatar_url=#{avatar_url} where account_id =#{account_id}")
    void update(User user);
}
