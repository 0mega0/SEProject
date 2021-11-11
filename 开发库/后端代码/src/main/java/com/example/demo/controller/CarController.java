package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Car;
import com.example.demo.Service.GoodsService;
import com.example.demo.Service.CarService;
import com.example.demo.Service.UserService;

/**
 * @author Alex,0mega_0
 * last change 2021/11/5
 */

@CrossOrigin
@RestController
public class CarController {
	@Autowired //自动连接到UserService Bean
    private CarService c;
	@Autowired
	private UserService u;
	@Autowired
	private GoodsService g;

	/**
	 * 向购物车添加商品
	 *
	 * @param id   用户ID
	 * @param word 用户密码
	 * @return parameter登录角色
	 */
	@RequestMapping(value = "/addCar",method = RequestMethod.POST)
	public void addGood(String id,String word,String shopId,String goodId,String name,String price,String intro,String newOrOld,String catalogue,
			String size,String bargain,String count,String amount,String imgUrl) {
		if(u.getUser(id, word).size() > 0) {
			if(c.getCar(id, goodId).size() > 0) {
				int s = Integer.parseInt(count) + Integer.parseInt(c.getCar(id, goodId).get(0).getCount());
				c.updateCount(id, goodId, String.valueOf(s));
			}else {
				c.addCar(id,shopId, goodId, name, price, intro, newOrOld, catalogue, size, bargain, count, amount,imgUrl);
			}
		}
	}
	
	@RequestMapping(value = "/Car",method = RequestMethod.POST)
	public List<Car> Car(String id,String word){  //用户购物车的所有商品
		if(u.getUser(id, word).size() > 0) {
			return c.Car(id,"1");
		}
		return null;
	}
	
	@RequestMapping(value = "/dgoodcar",method = RequestMethod.POST)
	public void deleteGoodFromCar(String id, String word, String goodid){  //从购物车删除商品
		if(u.getUser(id, word).size() > 0) {
			c.deleteGoodFromCar(id, goodid);
		}
	}
	
	@RequestMapping(value = "/changecount",method = RequestMethod.POST)
	public void changeCount(String id, String word, String goodid, String count){  //从购物车删除商品
		if(u.getUser(id, word).size() > 0) {
			c.updateCount(id, goodid, count);
		}
	}
	
	@RequestMapping(value = "/pay",method = RequestMethod.POST)
	public void pay (String id,String word,String[] goodid,String phone,String uname,String address,String money) {      //用户付款的商品
		System.out.println(goodid[0]);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//设置日期格式
		String time = df.format(new Date());
		for(int i = 0; i < goodid.length; i++) {
			String a = c.getCar(id, goodid[i]).get(0).getAmount();
			String b = c.getCar(id, goodid[i]).get(0).getCount();
			String amount = String.valueOf( Integer.parseInt(a) - Integer.parseInt(b));
			c.pay(id, goodid[i], amount,time, phone, uname, address);
			g.changeamount(goodid[i], amount);
		}
		String m = u.getUser(id, word).get(0).getMoney();
		double rmb = Double.parseDouble(m) - Double.parseDouble(money);
		u.pay(id, String.valueOf(rmb));
	}
	
	@RequestMapping(value = "/payed",method = RequestMethod.POST)
	public List<Car> payed(String id,String word){  //用户已经下单的商品
		if(u.getUser(id, word).size() > 0) {
			return c.Car1(id);
		}
		return null;
	}
	
	@RequestMapping(value = "/tuikuan",method = RequestMethod.POST)
	public String drawback(String id, String word, String goodid, String time) throws ParseException{  //退款
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String t = df.format(new Date());
		long from = df.parse(time).getTime();
		long to = df.parse(t).getTime();
		int minutes = (int) ((to - from)/(1000 * 60));
		if(minutes / 60 > 24) {
			return "下单时间超过24小时，无法申请退款！";
		}
		if(u.getUser(id, word).size() > 0) {
			c.drawback(id, goodid);
		}
		return "申请退款成功，请等待商家进行审核！";
	}
	
	@RequestMapping(value = "/sendgood",method = RequestMethod.POST)
	public List<Car> getGoodToBeSend(String id,String word){  //商家待发货
		if(u.getUser(id, word).get(0).getStatus().equals("3")) {
			return c.getGoodToBeSend(id, "2");
		}
		return null;
	}

	@RequestMapping(value = "/fahuo",method = RequestMethod.POST)
	public String sendGood(String id,String shopid,String word,String goodid){  //商家确认发货
		if(u.getUser(shopid, word).size() > 0) {
			 c.sendGood(id, shopid, goodid, "4");
		}
		return null;
	}
	
	@RequestMapping(value = "/tui",method = RequestMethod.POST)
	public List<Car> drawbackGood(String id,String word){  //商家退款订单
		if(u.getUser(id, word).size() > 0) {
			return c.getGoodToBeSend(id, "3");
		}
		return null;
	}
	
	@RequestMapping(value = "/jutui",method = RequestMethod.POST)
	public void drawbackRefuse(String id,String shopid,String word,String goodid){  //商家拒绝退款
		if(u.getUser(shopid, word).size() > 0) {
			c.drawbackRefuse(id, goodid);
		}
	}
	
	@RequestMapping(value = "/allowtui",method = RequestMethod.POST)
	public void drawbackAccept(String id,String shopid,String word,String goodid,String price,String count,String amount) {    //同意退款
		if(u.getUser(shopid, word).size() > 0) {
			c.drawbackGood(id, goodid);
			int a = Integer.parseInt(count) + Integer.parseInt(amount);
			g.refund(shopid,goodid , String.valueOf(a));
			double b = Double.parseDouble(u.userMoney(id).get(0).getMoney()) + Double.parseDouble(price);
			u.pay(id, String.valueOf(b));
		}
	}
	
	@RequestMapping(value = "/shouhuo",method = RequestMethod.POST)
	public List<Car> receiptConfirm(String id,String word){  //用户确认收货表
		if(u.getUser(id, word).size() > 0) {
			return c.Car(id, "4");
		}
		return null;
	}
	
	@RequestMapping(value = "/ushou",method = RequestMethod.POST)
	public void userReceiptConfirm(String id,String word,String shopId,String goodId,String price,String count) { //用户确认收货改变商品状态为5
		if(u.getUser(id, word).size() > 0) {
			c.receiptConfirm(id, goodId);
			int a = Integer.parseInt(g.goodinfo(goodId).get(0).getSoldAmount()) + Integer.parseInt(count);
			g.hasBeenSold(goodId, String.valueOf(a));
			double b = 0;
			int fen = 0;
			double p = 0;
			fen =  Integer.parseInt(u.userMoney(shopId).get(0).getUserGrade()) +
					(int)(Double.parseDouble(price)/1);
			switch (u.userMoney(shopId).get(0).getShopLevel()) {
				case "1":
				case "2":
					b = Double.parseDouble(u.userMoney(shopId).get(0).getMoney()) +
							Double.parseDouble(price) * (1 - (Double.parseDouble(u.userMoney(shopId).get(0).getShopLevel()) / 1000));
					p = Double.parseDouble(u.userMoney("admin").get(0).getMoney()) +
							Double.parseDouble(price) * Double.parseDouble(u.userMoney(shopId).get(0).getShopLevel()) / 1000;
					break;
				case "3":
					b = Double.parseDouble(u.userMoney(shopId).get(0).getMoney()) +
							Double.parseDouble(price) * (1 - 0.005);
					p = Double.parseDouble(u.userMoney("admin").get(0).getMoney()) +
							Double.parseDouble(price) * 0.005;
					break;
				case "4":
					b = Double.parseDouble(u.userMoney(shopId).get(0).getMoney()) +
							Double.parseDouble(price) * (1 - 0.075);
					p = Double.parseDouble(u.userMoney("admin").get(0).getMoney()) +
							Double.parseDouble(price) * 0.075;
					break;
				case "5":
					b = Double.parseDouble(u.userMoney(shopId).get(0).getMoney()) +
							Double.parseDouble(price) * (1 - 0.01);
					p = Double.parseDouble(u.userMoney("admin").get(0).getMoney()) +
							Double.parseDouble(price) * 0.01;
					break;
				default:break;
			}
			System.out.println(p);
			u.pay(shopId, String.valueOf(b));
			u.pay("admin", String.valueOf(p));
			u.userGrade(id, String.valueOf(fen));
		}
	}
	
	@RequestMapping(value = "/horder",method = RequestMethod.POST)
	public List<Car> horder(String id,String word){  //用户历史订单
		if(u.getUser(id, word).size() > 0) {
			return c.Car(id, "5");
		}
		return null;
	}
	
	@RequestMapping(value = "/horder1",method = RequestMethod.POST)
	public List<Car> horder1(String id,String word){  //用户历史订单
		if(u.getUser(id, word).size() > 0) {
			if(u.getUser(id, word).size() > 0 && u.getUser(id, word).get(0).getStatus().equals("3")) {
				return c.Car2(id);
			}
		}
		return null;
	}
}
