package com.example.demo.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.demo.Entity.Comment;

/**
 * @author Alex,0mega_0
 * last change 2021/11/5
 */

@Mapper
public interface CommentDao {

	@Results({
        @Result(property = "id", column = "id"),
        @Result(property = "goodId", column = "goodid"),
        @Result(property = "text", column = "text"),
        @Result(property = "rate", column = "rate") 
	})

	/**
	 * 用户对某商品进行评价
	 * @param id		用户id
	 * @param goodId	商品id
	 * @param text		评论正文
	 * @param rate		评分分数
	 */
	@Insert("INSERT into comment(id,goodid,text,rate)"
			+ "values(#{0},#{1},#{2},#{3}) ")
	void addComment(String id, String goodId, String text, String rate);

	/**
	 * 返回某用户对于某商品的评论记录
	 * @param id 		用户id
	 * @param goodId	商品id
	 * @return			符合搜索用户和商品条件的条目列表
	 */
	@Select("SELECT * FROM comment WHERE id = #{0} and goodid = #{1}")
    List<Comment> userComment(String id, String goodId);

	/**
	 * 返回某商品的全部评论记录
	 * @param goodId 	商品id
	 * @return			该商品所有评论的条目列表
	 */
	@Select("SELECT * FROM comment WHERE goodid = #{0} ")
    List<Comment> getComment(String goodId);
}
