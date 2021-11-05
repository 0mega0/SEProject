package com.example.demo.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.Entity.Car;

/**
 * @author Alex,0mega_0
 * last change 2021/11/5
 */
@Mapper
public interface CarDao {

	@Results({
        @Result(property = "id", column = "id"),
        @Result(property = "shopId", column = "shopid"),
        @Result(property = "goodId", column = "goodid"),
        @Result(property = "name", column = "name"),
        @Result(property = "price", column = "price"),     
        @Result(property = "discount", column = "zhekou"),
        @Result(property = "intro", column = "intro"),
        @Result(property = "newOrOld", column = "new"),
        @Result(property = "catalogue", column = "fenlei"),
        @Result(property = "size", column = "size"),
        @Result(property = "bargain", column = "yijia"),
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
     * 用户添加商品到购物车
     * @param userId    用戶id
     * @param shopId    商家id
     * @param goodId    商品id
     * @param name      商品名
     * @param price     商品价格
     * @param intro     商品简介
     * @param newOrOld  新旧程度
     * @param catalogue 分类
     * @param size      尺寸
     * @param bargain   是否可以议价
     * @param count     添加数量
     * @param amount    商品数量
     * @param imgUrl    商品缩略图URL
     * @param status    订单状态: 1购物车中的收藏商品 2已付款 3已申请退款 4已发货 5已完成
     */
	@Insert("INSERT into Car(id,shopid,goodid,name,price,intro,newo,fenlei,size,yijia,count,amount,imgUrl,status) "
			+ "values(#{0},#{1},#{2},#{3},#{4},#{5},#{6},#{7},#{8},#{9},#{10},#{11},#{12},#{13}) ")
	void addCar(String userId,String shopId,String goodId,String name,String price,String intro,String newOrOld,String catalogue,
			String size,String bargain,String count,String amount,String imgUrl,String status);

    /**
     * 添加的商品是否已经在购物车
     * @param id        用户id
     * @param goodId    商品id
     * @return          返回符合条件的记录条目列表
     */
	@Select("SELECT * FROM Car WHERE id = #{0} and goodid = #{1} and status = '1'")
    List<Car> getCar(String id,String goodId);

    /**
     * 已经在购物车则更新数量
     * @param id        用户id
     * @param goodId    商品id
     * @param count     添加数量
     */
    @Update("UPDATE Car SET count = #{2} WHERE id = #{0} and goodid = #{1}")
	void updateCount(String id, String goodId, String count);

    /**
     * 显示用户的所有某个状态的历史记录
     * @param id        用户id
     * @param status    订单状态: 1购物车中的收藏商品 2已付款 3已申请退款 4已发货 5已完成
     * @return          符合条件的记录条目列表
     */
    @Select("SELECT * FROM Car WHERE id = #{0} and status = #{1}")
    List<Car> Car(String id,String status);

    /**
     * 显示该用户已付款或者已申请退款的记录
     * @param id        用户id
     * @return          符合条件的记录条目列表
     */
    @Select("SELECT * FROM Car WHERE id = #{0} and status = '2' or id = #{0} and status = '3'")
    List<Car> Car1(String id);

    /**
     * 显示某位商家的已完成历史记录
     * @param shopId    商家ID
     * @return          符合条件的记录条目列表
     */
    @Select("SELECT * FROM Car WHERE shopid = #{0} and status = '5' ")
    List<Car> Car2(String shopId);

    /**
     * 从购物车中删除某条商品记录
     * @param id        用户id
     * @param goodId    商品id
     */
    @Delete("Delete  FROM Car WHERE id = #{0} and goodid = #{1} and status = '1'")
    void deleteGoodFromCar(String id, String goodId);

    /**
     * 付款后改变购物车中某条记录的状态为已付款(status=2)
     * @param id        用户id
     * @param goodId    商品id
     * @param amount    商品数量
     * @param time      支付时间
     * @param phone     支付用户电话号码
     * @param uname     支付用户的用户名
     * @param address   支付用户的收货地址
     */
    @Update("UPDATE car SET status = '2',amount = #{2}, time = #{3},phone = #{4},uname = #{5},address = #{6} WHERE id = #{0} and goodid = #{1}")
	void pay(String id,String goodId,String amount,String time,String phone,String uname,String address);

    /**
     * 对某件商品退款
     * @param id        用户id
     * @param goodId    商品id
     */
    @Update("UPDATE car SET status = '3' WHERE id = #{0} and goodid = #{1}")
   	void drawback(String id, String goodId);

    /**
     * 查询待发货的记录
     * @param id        用户id
     * @param status    订单状态: 1购物车中的收藏商品 2已付款 3已申请退款 4已发货 5已完成
     * @return          符合条件的记录条目列表
     */
    @Select("SELECT * FROM car WHERE shopid = #{0} and status = #{1}")
    List<Car> getGoodToBeSend(String id, String status);  //商家待发货

    /**
     * 商家对某物品进行发货处理
     * @param id        用户id
     * @param shopId    商家id
     * @param goodId    商品id
     * @param status    订单状态: 1购物车中的收藏商品 2已付款 3已申请退款 4已发货 5已完成
     */
    @Update("UPDATE car SET status = #{3} WHERE id = #{0} and shopid = #{1} and goodid = #{2}")
	void sendGood(String id, String shopId, String goodId, String status);

    /**
     * 某用户对某商品退款
     * @param id        用户id
     * @param goodId    商品id
     */
    @Delete("Delete  FROM car WHERE id = #{0} and goodid = #{1}")
    void drawbackGood(String id, String goodId);

    /**
     * 商家对某商品拒绝退款
     * @param id        用户id
     * @param goodId    商品id
     */
    @Update("UPDATE car SET status = '2' WHERE id = #{0} and goodid = #{1}")
   	void drawbackRefuse(String id, String goodId);

    /**
     * 用户确认收货，并改变记录状态为已收货(status=5)
     * @param id        用户id
     * @param goodId    商品id
     */
    @Update("UPDATE car SET status = '5' WHERE id = #{0} and goodid = #{1}")
   	void receiptConfirm(String id, String goodId);

    /**
     * 商家更新某商品的库存数量
     * @param shopId    商家id
     * @param goodId    商品id
     * @param amount    商品数量
     */
    @Update("UPDATE car SET amount = #{2} WHERE shopid = #{0} and goodid = #{1}")
   	void amountChange(String shopId, String goodId, String amount);

    /**
     * 商家更新某商品的价格
     * @param shopId    商家id
     * @param goodId    商品id
     * @param price     商品价格
     */
    @Update("UPDATE car SET price = #{2} WHERE shopid = #{0} and goodid = #{1}")
   	void priceChange(String shopId, String goodId, String price);
    
 
}
