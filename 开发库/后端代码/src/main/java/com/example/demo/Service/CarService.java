package com.example.demo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Dao.CarDao;
import com.example.demo.Entity.Car;

/**
 * @author Alex,0mega_0
 * last change 2021/11/5
 */

@Service
public class CarService {
	@Autowired
	private CarDao c;
	
	public void addCar(String id,String shopid,String goodid,String name,String price,String intro,String newo,String fenlei,
			String size,String yijia,String count,String amount,String imgUrl) {
		c.addCar(id,shopid, goodid, name, price, intro, newo, fenlei, size, yijia, count,amount, imgUrl, "1");
	}
	
	public List<Car> getCar(String id,String goodid){      //添加的商品是否已经在购物车
		return c.getCar(id, goodid);
	}
	
	public void updateCount(String id, String goodid, String count) {  //已经在购物车则更新数量
		c.updateCount(id, goodid, count);
	}
	
	public List<Car> Car(String id,String status){  //用户购物车的所有商品
		return c.Car(id,status);
	}
	
	public List<Car> Car1(String id){
		return c.Car1(id);
	}
	
	public List<Car> Car2(String shopid){
		return c.Car2(shopid);
	}
	
	public void deleteGoodFromCar(String id,String goodid) {     //从购物车删除商品
		c.deleteGoodFromCar(id, goodid);
	}
	
	public void pay (String id,String goodid,String amount,String time,String phone,String uname,String address) {      //用户付款的商品
		c.pay(id, goodid,amount, time, phone, uname, address);
	}
	
   	public void drawback(String id,String goodid) {//退款
   		c.drawback(id, goodid);
   	}
   	
    public List<Car> getGoodToBeSend(String id,String status){ //商家待发货
    	return c.getGoodToBeSend(id, status);
    }
     
	public void sendGood(String id,String shopid,String goodid,String status) {  //商家发货
		c.sendGood(id, shopid, goodid, status);
	}
	
	public void drawbackRefuse(String id,String goodid) { //拒绝退款
		c.drawbackRefuse(id, goodid);
	}
	
	public void drawbackGood(String id,String goodid) {  //退款
	    	c.drawbackGood(id, goodid);
	}
	
	public void receiptConfirm(String id,String goodid) { //用户确认收货改变商品状态为5
		c.receiptConfirm(id, goodid);
	}
	
	public void amountChange(String shopid,String goodid,String amount) { //商家更新库存
		c.amountChange(shopid, goodid, amount);
	}
	
	public void priceChange(String shopid,String goodid,String p) { //商家更新库存
		c.priceChange(shopid, goodid, p);
	}

}
