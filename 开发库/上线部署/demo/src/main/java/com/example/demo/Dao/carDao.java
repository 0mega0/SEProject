package com.example.demo.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.Entity.Goods;
import com.example.demo.Entity.car;

@Mapper
public interface carDao {
	@Results({
        @Result(property = "id", column = "id"),
        @Result(property = "shopid", column = "shopid"),
        @Result(property = "goodid", column = "goodid"),
        @Result(property = "name", column = "name"),
        @Result(property = "price", column = "price"),     
        @Result(property = "zhekou", column = "zhekou"),   
        @Result(property = "intro", column = "intro"),
        @Result(property = "newo", column = "new"),         
        @Result(property = "fenlei", column = "fenlei"),
        @Result(property = "size", column = "size"),
        @Result(property = "yijia", column = "yijia"),
        @Result(property = "count", column = "count"),  
        @Result(property = "amount", column = "amount"), 
        @Result(property = "imgUrl", column = "imgUrl"),
        @Result(property = "status", column = "status"),
        @Result(property = "time", column = "time"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "uname", column = "uname"),
        @Result(property = "address", column = "address")  
	})
	
	@Insert("INSERT into car(id,shopid,goodid,name,price,intro,newo,fenlei,size,yijia,count,amount,imgUrl,status) " //用户添加商品到购物车
			+ "values(#{0},#{1},#{2},#{3},#{4},#{5},#{6},#{7},#{8},#{9},#{10},#{11},#{12},#{13}) ")
	void addcar(String id,String shopid,String goodid,String name,String price,String intro,String newo,String fenlei,
			String size,String yijia,String count,String amount,String imgUrl,String status);
	
	@Select("SELECT * FROM car WHERE id = #{0} and goodid = #{1} and status = '1'") 
    List<car> getcar(String id,String goodid);  //添加的商品是否已经在购物车
    
    @Update("UPDATE car SET count = #{2} WHERE id = #{0} and goodid = #{1}")  //已经在购物车则更新数量
	void updatecount(String id,String goodid,String count);
	
    @Select("SELECT * FROM car WHERE id = #{0} and status = #{1}") 
    List<car> car(String id,String status);  //用户购物车的所有商品
    
    @Select("SELECT * FROM car WHERE id = #{0} and status = '2' or id = #{0} and status = '3'") 
    List<car> car1(String id); 
    
    @Select("SELECT * FROM car WHERE shopid = #{0} and status = '5' ") 
    List<car> car2(String shopid); 
    
    @Delete("Delete  FROM car WHERE id = #{0} and goodid = #{1} and status = '1'") 
    void dgoodcar(String id,String goodid);      //从购物车删除商品
    
    @Update("UPDATE car SET status = '2',amount = #{2}, time = #{3},phone = #{4},uname = #{5},address = #{6} WHERE id = #{0} and goodid = #{1}")  
	void pay(String id,String goodid,String amount,String time,String phone,String uname,String address); //付款后改变购物车的商品状态为已付款   2
    
    @Update("UPDATE car SET status = '3' WHERE id = #{0} and goodid = #{1}")  
   	void tuikuan(String id,String goodid); //退款
    
    @Select("SELECT * FROM car WHERE shopid = #{0} and status = #{1}") 
    List<car> sendgood(String id,String status);  //商家待发货
    
    @Update("UPDATE car SET status = #{3} WHERE id = #{0} and shopid = #{1} and goodid = #{2}")  //商家发货
	void fahuo(String id,String shopid,String goodid,String status);
    
    @Delete("Delete  FROM car WHERE id = #{0} and goodid = #{1}")   //退款
    void tuigood(String id,String goodid);
    
    @Update("UPDATE car SET status = '2' WHERE id = #{0} and goodid = #{1}")  
   	void jutui(String id,String goodid); //拒绝退款
    
    @Update("UPDATE car SET status = '5' WHERE id = #{0} and goodid = #{1}")  
   	void shouhuo(String id,String goodid); //用户确认收货改变商品状态为5
    
    @Update("UPDATE car SET amount = #{2} WHERE shopid = #{0} and goodid = #{1}")  
   	void changeamount(String shopid,String goodid,String amount); //商家更新库存
    
    @Update("UPDATE car SET price = #{2} WHERE shopid = #{0} and goodid = #{1}")  
   	void changeprice(String shopid,String goodid,String price); 
    
 
}
