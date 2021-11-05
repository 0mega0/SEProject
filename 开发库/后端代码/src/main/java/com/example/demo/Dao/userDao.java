package com.example.demo.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;

import com.example.demo.Entity.User;

@Mapper
public interface userDao {
	@Results({
        @Result(property = "id", column = "id"),
        @Result(property = "word", column = "word"),
        @Result(property = "name", column = "name"),
        @Result(property = "sex", column = "sex"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "email", column = "email"),
        @Result(property = "city", column = "city"),
        @Result(property = "account", column = "account"),
        @Result(property = "status", column = "status"),
        @Result(property = "licence", column = "licence"),
        @Result(property = "card", column = "card"),
        @Result(property = "money", column = "money"),
        @Result(property = "level", column = "level"),
        @Result(property = "grade", column = "grade")
	})
	
	@Select("SELECT * FROM user WHERE id = #{0} and word = #{1}") 
    List<User> get(String id,String word);      //登录验证
	
	@Insert("INSERT into zhuce(id,word,name,sex,phone,email,city,account) "   //注册用户信息插入待验证用户表
			+ "values(#{0},#{1},#{2},#{3},#{4},#{5},#{6},#{7}) ")
	void insertZhuce(String id,String word,String name,String sex,String phone,String email,String city,String account);    
	
	@Select("SELECT * FROM zhuce where id = #{0}")
    List<User> getZhuceUser(String id);      //验证用户注册是否重复提交
	
	@Select("SELECT * FROM zhuce ")
    List<User> getUser();      //获取待验证的注册用户列表
	
	@Delete("Delete  FROM zhuce WHERE id = #{0}") 
    void deleteUser(String id);      //删除允许登录的注册用户
	
	@Insert("INSERT into user(id,word,name,sex,phone,email,city,account,status,money,grade) "   //允许登录的注册用户信息插入用户表
			+ "values(#{0},#{1},#{2},#{3},#{4},#{5},#{6},#{7},#{8},'0','0') ")
	void insertUser(String id,String word,String name,String sex,String phone,String email,String city,String account,String status);
	
	@Select("SELECT * from user where id = #{0} and word = #{1}" )  //获取用户个人信息
	List<User> getUserinfo(String id,String word);
	
	@Update("UPDATE user SET name = #{0},phone = #{1},email = #{2},city = #{3},account = #{4}  where id = #{5} and word = #{6}" )  //用户更改个人 信息
	void updateInfo(String name,String phone,String email,String city,String account,String id,String word);
	
	@Update("UPDATE user SET licence = #{2},card = #{3},status = #{4} where id = #{0} and word = #{1}" )  //用户注册商家
	void shopzhuce(String id,String word,String licence,String card,String status);
	
	@Select("SELECT id,word,name,sex,phone,email,city,account,licence,card from user where status = #{0} " ) //获取所有注册商家的用户
	List<User> getshopzhuce(String status);
	
	@Select("SELECT * from user where status = #{0} " ) 
	List<User> getshoplevel(String status);  //获取商家等级
	
	@Update("UPDATE user SET status = #{0},level = '5' where id = #{1}" )  //允许注册商家的用户status更改为3
	void allowshopzhuce(String status,String id);
	
	@Update("UPDATE user SET money = #{1} where id = #{0}" )  //用户下单后扣除相应的钱数
	void pay(String id,String money);
	
	@Select("SELECT * from user where id = #{0} " ) //获取用户的钱数
	List<User> umoney(String id);
	
	@Update("UPDATE user SET grade = #{1} where id = #{0}" )  //更新用户积分
	void grade(String id,String grade);
	
	@Update("UPDATE user SET level = #{1} where id = #{0}" )  
	void changelevel(String id,String level); //更新商家等级
	
	@Update("UPDATE user SET money = #{1} where id = #{0}" )  
	void chongzhi(String id,String money); //充值
}
