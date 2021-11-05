package com.example.demo.Service;

import java.util.List;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Dao.GoodsDao;
import com.example.demo.Entity.Goods;

@Service
public class GoodsService {
	@Autowired //连接到UserDao Bean
	private GoodsDao g;
	
	public void addgood(String id,String goodid,String name,String price,String zhekou,String intro,String newo,String fenlei,
			String size,String yijia,String amount,String imgUrl) {   //商家上架新商品  
		g.addgood(id, goodid, name, price, zhekou, intro, newo, fenlei, size, yijia, amount, imgUrl, "1", "0");
	}
	
	public List<Goods> getgood(){  //管理员获取待审核的商品
		return g.getgood();
	}
	
	public void allowgood(String goodid,String status) {   //管理员允许上架的商品
		g.allowgood(goodid,status);
	}
	
	public List<Goods> getAllgood(){     //获取所有允许上架的商品展示在主页面
		return g.getAllgood(); 
	}
	
	public List<Goods> goodinfo(String goodid){   //商品详情页
		return g.goodinfo(goodid);
	}
	
	public List<Goods> shopgood(String id){   //商家的店铺
		return g.shopgood(id);
	}
	
	public void changeamount(String goodid,String amount) {	 //商家更改数量
		g.changeamount(goodid, amount);
	}
	 
	public void changeprice(String goodid,String price) {
		g.changeprice(goodid, price);
	}
	
	public void changezhekou(String goodid,String zhekou) {
		g.changezhekou(goodid, zhekou);
	}
	
	public void tui(String id,String goodid,String amount) { //退款  商品库存加回来
		g.tui(id, goodid, amount);
	}
	
	public void shouchu(String goodid,String shouchu) { //历史销量增加
		g.shouchu(goodid, shouchu);
	}
	
	public List<Goods> getxiajia(String id,String status){  //下架的商品
		return g.getxiajia(id, status);
	}
}
