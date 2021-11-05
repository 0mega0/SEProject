package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.car;
import com.example.demo.Service.GoodsService;
import com.example.demo.Service.carService;
import com.example.demo.Service.userService;

@CrossOrigin
@RestController
public class carController {
	@Autowired //自动连接到UserService Bean
    private carService c;
	@Autowired
	private userService u;
	@Autowired
	private GoodsService g;
	
	@RequestMapping(value = "/addcar",method = RequestMethod.POST)
	public void addgood(String id,String word,String shopid,String goodid,String name,String price,String intro,String newo,String fenlei,
			String size,String yijia,String count,String amount,String imgUrl) {
		if(u.get(id, word).size() > 0) {
			if(c.getcar(id, goodid).size() > 0) {
				int s = Integer.parseInt(count) + Integer.parseInt(c.getcar(id, goodid).get(0).getCount());
				c.updatecount(id, goodid, String.valueOf(s));
			}else {
				c.addcar(id,shopid, goodid, name, price, intro, newo, fenlei, size, yijia, count, amount,imgUrl);
			}
		}
	}
	
	@RequestMapping(value = "/car",method = RequestMethod.POST)
	public List<car> car(String id,String word){  //用户购物车的所有商品
		if(u.get(id, word).size() > 0) {
			return c.car(id,"1");
		}
		return null;
	}
	
	@RequestMapping(value = "/dgoodcar",method = RequestMethod.POST)
	public void dgoodcar(String id,String word,String goodid){  //从购物车删除商品
		if(u.get(id, word).size() > 0) {
			c.dgoodcar(id, goodid);
		}
	}
	
	@RequestMapping(value = "/changecount",method = RequestMethod.POST)
	public void changecount(String id,String word,String goodid,String count){  //从购物车删除商品
		if(u.get(id, word).size() > 0) {
			c.updatecount(id, goodid, count);
		}
	}
	
	@RequestMapping(value = "/pay",method = RequestMethod.POST)
	public void pay (String id,String word,String[] goodid,String phone,String uname,String address,String money) {      //用户付款的商品
		System.out.println(goodid[0]);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		String time = df.format(new Date());
		//System.out.println(time);
		for(int i = 0; i < goodid.length; i++) {
			String a = c.getcar(id, goodid[i]).get(0).getAmount();
			String b = c.getcar(id, goodid[i]).get(0).getCount();
			String amount = String.valueOf( Integer.parseInt(a) - Integer.parseInt(b));
			c.pay(id, goodid[i], amount,time, phone, uname, address);
			g.changeamount(goodid[i], amount);
		}
		String m = u.get(id, word).get(0).getMoney();
		double rmb = Double.parseDouble(m) - Double.parseDouble(money);
		u.pay(id, String.valueOf(rmb));
	}
	
	@RequestMapping(value = "/payed",method = RequestMethod.POST)
	public List<car> payed(String id,String word){  //用户已经下单的商品
		if(u.get(id, word).size() > 0) {
			return c.car1(id);
		}
		return null;
	}
	
	@RequestMapping(value = "/tuikuan",method = RequestMethod.POST)
	public String tuikuan(String id,String word,String goodid,String time) throws ParseException{  //退款
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		String t = df.format(new Date());
		long from = df.parse(time).getTime();
		long to = df.parse(t).getTime();
		int minutes = (int) ((to - from)/(1000 * 60));
		if(minutes / 60 > 24) {
			return "下单时间超过24小时，无法申请退款！";
		}
		if(u.get(id, word).size() > 0) {
			c.tuikuan(id, goodid);
		}
		return "申请退款成功，请等待商家进行审核！";
	}
	
	@RequestMapping(value = "/sendgood",method = RequestMethod.POST)
	public List<car> sendgood(String id,String word){  //商家待发货
		if(u.get(id, word).get(0).getStatus().equals("3")) {
			return c.sendgood(id, "2");
		}
		return null;
	}
	
	@RequestMapping(value = "/fahuo",method = RequestMethod.POST)
	public String fahuo(String id,String shopid,String word,String goodid){  //商家确认发货
		if(u.get(shopid, word).size() > 0) {
			 c.fahuo(id, shopid, goodid, "4");
		}
		return null;
	}
	
	@RequestMapping(value = "/tui",method = RequestMethod.POST)
	public List<car> tui(String id,String word){  //商家退款订单
		if(u.get(id, word).size() > 0) {
			return c.sendgood(id, "3");
		}
		return null;
	}
	
	@RequestMapping(value = "/jutui",method = RequestMethod.POST)
	public void jutui(String id,String shopid,String word,String goodid){  //商家拒绝退款
		if(u.get(shopid, word).size() > 0) {
			c.jutui(id, goodid);
		}
	}
	
	@RequestMapping(value = "/allowtui",method = RequestMethod.POST)
	public void allowtui(String id,String shopid,String word,String goodid,String price,String count,String amount) {    //同意退款
		if(u.get(shopid, word).size() > 0) {
			c.tuigood(id, goodid);
			int a = Integer.parseInt(count) + Integer.parseInt(amount);
			g.tui(shopid,goodid , String.valueOf(a));
			double b = Double.parseDouble(u.umoney(id).get(0).getMoney()) + Double.parseDouble(price);
			u.pay(id, String.valueOf(b));
		}
	}
	
	@RequestMapping(value = "/shouhuo",method = RequestMethod.POST)
	public List<car> shouhuo(String id,String word){  //用户确认收货表
		if(u.get(id, word).size() > 0) {
			return c.car(id, "4");
		}
		return null;
	}
	
	@RequestMapping(value = "/ushou",method = RequestMethod.POST)
	public void ushou(String id,String word,String shopid,String goodid,String price,String count) { //用户确认收货改变商品状态为5
		if(u.get(id, word).size() > 0) {
			c.shouhuo(id, goodid);
			int a = Integer.parseInt(g.goodinfo(goodid).get(0).getShouchu()) + Integer.parseInt(count);
			g.shouchu(goodid, String.valueOf(a));
			double b = 0;
			int fen = 0;
			double p = 0;
			fen =  Integer.parseInt(u.umoney(shopid).get(0).getGrade()) +  
					(int)(Double.parseDouble(price)/1);
			if(u.umoney(shopid).get(0).getLevel().equals("1") || u.umoney(shopid).get(0).getLevel().equals("2") ) {
				b =  Double.parseDouble(u.umoney(shopid).get(0).getMoney()) +  
						Double.parseDouble(price)*(1-(Double.parseDouble(u.umoney(shopid).get(0).getLevel())/1000));
				p =  Double.parseDouble(u.umoney("admin").get(0).getMoney()) +  
						Double.parseDouble(price)*Double.parseDouble(u.umoney(shopid).get(0).getLevel())/1000;
			}else if(u.umoney(shopid).get(0).getLevel().equals("3")) {
				b =  Double.parseDouble(u.umoney(shopid).get(0).getMoney()) +  
						Double.parseDouble(price)*(1-0.005);
				p =  Double.parseDouble(u.umoney("admin").get(0).getMoney()) +  
						Double.parseDouble(price)*0.005;
			}else if(u.umoney(shopid).get(0).getLevel().equals("4")) {
				b =  Double.parseDouble(u.umoney(shopid).get(0).getMoney()) +  
						Double.parseDouble(price)*(1-0.075);
				p =  Double.parseDouble(u.umoney("admin").get(0).getMoney()) +  
						Double.parseDouble(price)*0.075;
			}else if(u.umoney(shopid).get(0).getLevel().equals("5")) {
				b =  Double.parseDouble(u.umoney(shopid).get(0).getMoney()) +  
						Double.parseDouble(price)*(1-0.01);
				p =  Double.parseDouble(u.umoney("admin").get(0).getMoney()) +  
						Double.parseDouble(price)*0.01;
			}
			System.out.println(p);
			u.pay(shopid, String.valueOf(b));
			u.pay("admin", String.valueOf(p));
			u.grade(id, String.valueOf(fen));
		}
	}
	
	@RequestMapping(value = "/horder",method = RequestMethod.POST)
	public List<car> horder(String id,String word){  //用户历史订单
		if(u.get(id, word).size() > 0) {
			return c.car(id, "5");
		}
		return null;
	}
	
	@RequestMapping(value = "/horder1",method = RequestMethod.POST)
	public List<car> horder1(String id,String word){  //用户历史订单
		if(u.get(id, word).size() > 0) {
			if(u.get(id, word).size() > 0 && u.get(id, word).get(0).getStatus().equals("3")) {
				return c.car2(id);
			}
		}
		return null;
	}
}
