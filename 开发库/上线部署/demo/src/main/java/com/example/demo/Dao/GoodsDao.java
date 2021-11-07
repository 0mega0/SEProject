package com.example.demo.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.Entity.Goods;
import com.example.demo.Entity.User;

@Mapper
public interface GoodsDao {
	@Results({
        @Result(property = "id", column = "id"),
        @Result(property = "goodid", column = "goodid"),
        @Result(property = "name", column = "name"),
        @Result(property = "price", column = "price"),     
        @Result(property = "zhekou", column = "zhekou"),   
        @Result(property = "intro", column = "intro"),
        @Result(property = "newo", column = "new"),         
        @Result(property = "fenlei", column = "fenlei"),
        @Result(property = "size", column = "size"),
        @Result(property = "yijia", column = "yijia"),
        @Result(property = "amount", column = "amount"),      
        @Result(property = "imgUrl", column = "imgUrl"),
        @Result(property = "status", column = "status"),
        @Result(property = "shouchu", column = "shouchu")     
	})
	
	@Insert("INSERT into goods(id,goodid,name,price,zhekou,intro,newo,fenlei,size,yijia,amount,imgUrl,status,shouchu) " // 商家上架新商品
			+ "values(#{0},#{1},#{2},#{3},#{4},#{5},#{6},#{7},#{8},#{9},#{10},#{11},#{12},#{13}) ")
	void addgood(String id,String goodid,String name,String price,String zhekou,String intro,String newo,String fenlei,
			String size,String yijia,String amount,String imgUrl,String status,String shouchu);
	
	@Select("SELECT id,goodid,name,price,zhekou,intro,newo,fenlei,size,yijia,amount,imgUrl FROM goods WHERE status = '1'") 
    List<Goods> getgood();      //管理员获取待审核上架的商品
	
	@Update("UPDATE goods SET status = #{1} WHERE goodid = #{0}")  //允许上架商品
	void allowgood(String goodid,String status);
	
	@Select("SELECT * FROM goods WHERE status = '2'") 
    List<Goods> getAllgood();   //获取所有允许上架的商品展示在主页面
	
	@Select("SELECT * FROM goods WHERE goodid = #{0}") 
    List<Goods> goodinfo(String goodid);   //商品详情页
	
	@Select("SELECT * FROM goods WHERE id = #{0} and status = '2'") 
    List<Goods> shopgood(String id);   //商家的店铺
	
	@Update("UPDATE goods SET amount = #{1} WHERE goodid = #{0}")  //用户下单商品库存减少
	void changeamount(String goodid,String amount);
	
	@Update("UPDATE goods SET price = #{1} WHERE goodid = #{0}")  
	void changeprice(String goodid,String price);
	
	@Update("UPDATE goods SET zhekou = #{1} WHERE goodid = #{0}")  
	void changezhekou(String goodid,String zhekou);
	
	@Update("UPDATE goods SET amount = #{2} WHERE id = #{0} and goodid = #{1}")  //退款  商品库存加回来
	void tui(String id,String goodid,String amount);
	
	@Update("UPDATE goods SET shouchu = #{1} WHERE goodid = #{0}")  //历史销量增加
	void shouchu(String goodid,String shouchu);
	
	@Select("SELECT * FROM goods WHERE id = #{0} and status = #{1}") 
    List<Goods> getxiajia(String id,String status);   //下架的商品
} 
