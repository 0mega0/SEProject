package com.example.demo.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.demo.Entity.car;
import com.example.demo.Entity.comment;

@Mapper
public interface commentDao {
	@Results({
        @Result(property = "id", column = "id"),
        @Result(property = "goodid", column = "goodid"),
        @Result(property = "text", column = "text"),
        @Result(property = "rate", column = "rate") 
	})
	
	@Insert("INSERT into comment(id,goodid,text,rate)" //用户评价
			+ "values(#{0},#{1},#{2},#{3}) ")
	void addcomment(String id,String goodid,String text,String rate);
	
	@Select("SELECT * FROM comment WHERE id = #{0} and goodid = #{1}") 
    List<comment> ucomment(String id,String goodid);  //防止重复评价
	
	@Select("SELECT * FROM comment WHERE goodid = #{0} ") 
    List<comment> getcomment(String goodid);  //获取商品评价
}
