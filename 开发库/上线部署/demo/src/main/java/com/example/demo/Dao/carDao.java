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

/**
 * @author Alex,0mega_0
 * last change 2021/11/5
 */
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

    /**
     * 向购物车中添加商品
     * @param id        用户id
     * @param shopid    商家id
     * @param goodid    商品id
     * @param name      商品名
     * @param price     商品价格
     * @param intro     商品简介
     * @param newo      新旧程度
     * @param fenlei    分类
     * @param size      尺寸
     * @param yijia     是否可以砍价
     * @param count     添加数量
     * @param amount    商品总量
     * @param imgUrl    商品缩略图URL
     * @param status    订单状态: 1购物车中的收藏商品 2已付款 3已申请退款 4已发货 5已完成
     */
    @Insert("INSERT into car(id,shopid,goodid,name,price,intro,newo,fenlei,size,yijia,count,amount,imgUrl,status) " //用户添加商品到购物车
			+ "values(#{0},#{1},#{2},#{3},#{4},#{5},#{6},#{7},#{8},#{9},#{10},#{11},#{12},#{13}) ")
	void addcar(String id,String shopid,String goodid,String name,String price,String intro,String newo,String fenlei,
			String size,String yijia,String count,String amount,String imgUrl,String status);

    /**
     * 添加的商品是否已经在购物车
     * @param id        用户id
     * @param goodid    商品id
     * @return          返回符合条件的记录条目列表
     */
	@Select("SELECT * FROM car WHERE id = #{0} and goodid = #{1} and status = '1'") 
    List<car> getcar(String id,String goodid);  //添加的商品是否已经在购物车

    /**
     * 商品已经在购物车则更新数量
     * @param id        用户id
     * @param goodid    商品id
     * @param count     添加数量
     */
    @Update("UPDATE car SET count = #{2} WHERE id = #{0} and goodid = #{1}")  //已经在购物车则更新数量
	void updatecount(String id,String goodid,String count);

    /**
     * 显示用户的所有某个状态的历史记录
     * @param id        用户id
     * @param status    订单状态: 1购物车中的收藏商品 2已付款 3已申请退款 4已发货 5已完成
     * @return          符合条件的记录条目列表
     */
    @Select("SELECT * FROM car WHERE id = #{0} and status = #{1}") 
    List<car> car(String id,String status);  //用户购物车的所有商品

    /**
     * 显示该用户已付款或者已申请退款的记录
     * @param id        用户id
     * @return          符合条件的记录条目列表
     */
    @Select("SELECT * FROM car WHERE id = #{0} and status = '2' or id = #{0} and status = '3'") 
    List<car> car1(String id);

    /**
     * 显示某位商家的已完成历史记录
     * @param shopid    商家ID
     * @return          符合条件的记录条目列表
     */
    @Select("SELECT * FROM car WHERE shopid = #{0} and status = '5' ") 
    List<car> car2(String shopid);

    /**
     * 从购物车中删除某条商品记录
     * @param id        用户id
     * @param goodid    商品id
     */
    @Delete("Delete  FROM car WHERE id = #{0} and goodid = #{1} and status = '1'") 
    void dgoodcar(String id,String goodid);      //从购物车删除商品

    /**
     * 付款后改变购物车中某条记录的状态为已付款(status=2)
     * @param id        用户id
     * @param goodid    商品id
     * @param amount    商品数量
     * @param time      支付时间
     * @param phone     支付用户电话号码
     * @param uname     支付用户的用户昵称
     * @param address   支付用户的收货地址
     */
    @Update("UPDATE car SET status = '2',amount = #{2}, time = #{3},phone = #{4},uname = #{5},address = #{6} WHERE id = #{0} and goodid = #{1}")  
	void pay(String id,String goodid,String amount,String time,String phone,String uname,String address); //付款后改变购物车的商品状态为已付款   2

    /**
     * 对某件商品退款
     * @param id        用户id
     * @param goodid    商品id
     */
    @Update("UPDATE car SET status = '3' WHERE id = #{0} and goodid = #{1}")  
   	void tuikuan(String id,String goodid); //退款

    /**
     * 查询某商家的所有订单中符合status的订单
     * @param id        商家id
     * @param status    订单状态: 1购物车中的收藏商品 2已付款 3已申请退款 4已发货 5已完成
     * @return          符合条件的记录条目列表
     */
    @Select("SELECT * FROM car WHERE shopid = #{0} and status = #{1}") 
    List<car> sendgood(String id,String status);  //商家待发货

    /**
     * 商家对某物品进行发货处理
     * @param id        用户id
     * @param shopid    商家id
     * @param goodid    商品id
     * @param status    订单状态: 1购物车中的收藏商品 2已付款 3已申请退款 4已发货 5已完成
     */
    @Update("UPDATE car SET status = #{3} WHERE id = #{0} and shopid = #{1} and goodid = #{2}")  //商家发货
	void fahuo(String id,String shopid,String goodid,String status);

    /**
     * 某用户对某商品退款
     * @param id        用户id
     * @param goodid    商品id
     */
    @Delete("Delete  FROM car WHERE id = #{0} and goodid = #{1}")   //退款
    void tuigood(String id,String goodid);

    /**
     * 商家对某商品拒绝退款
     * @param id        用户id
     * @param goodid    商品id
     */
    @Update("UPDATE car SET status = '2' WHERE id = #{0} and goodid = #{1}")  
   	void jutui(String id,String goodid); //拒绝退款

    /**
     * 用户确认收货，并改变记录状态为已收货(status=5)
     * @param id        用户id
     * @param goodid    商品id
     */
    @Update("UPDATE car SET status = '5' WHERE id = #{0} and goodid = #{1}")  
   	void shouhuo(String id,String goodid); //用户确认收货改变商品状态为5

    /**
     * 商家更新某商品的库存数量
     * @param shopid    商家id
     * @param goodid    商品id
     * @param amount    商品数量
     */
    @Update("UPDATE car SET amount = #{2} WHERE shopid = #{0} and goodid = #{1}")  
   	void changeamount(String shopid,String goodid,String amount); //商家更新库存

    /**
     * 商家更新某商品的价格
     * @param shopid    商家id
     * @param goodid    商品id
     * @param price     商品价格
     */
    @Update("UPDATE car SET price = #{2} WHERE shopid = #{0} and goodid = #{1}")  
   	void changeprice(String shopid,String goodid,String price); 
    
 
}
