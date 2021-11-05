package com.example.demo.Service;

import java.util.List;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Dao.GoodsDao;
import com.example.demo.Entity.Goods;

/**
 * @author Alex,0mega_0,Scarlet_sky
 * last change 2021/11/5
 */

@Service
public class GoodsService {
	@Autowired //连接到UserDao Bean
	private GoodsDao goodsDao;
	
	public void addgood(String id,String goodid,String name,String price,String zhekou,String intro,String newo,String fenlei,
			String size,String yijia,String amount,String imgUrl) {   //商家上架新商品  
		goodsDao.addGood(id, goodid, name, price, zhekou, intro, newo, fenlei, size, yijia, amount, imgUrl, "1", "0");
	}
	
	public List<Goods> getUnauditedGood(){  //管理员获取待审核的商品
		return goodsDao.getUnauditedGood();
	}
	
	public void allowgood(String goodid,String status) {   //管理员允许上架的商品
		goodsDao.allowGood(goodid,status);
	}
	
	public List<Goods> getAllgood(){     //获取所有允许上架的商品展示在主页面
		return goodsDao.getAllGood(); 
	}
	
	public List<Goods> goodinfo(String goodid){   //商品详情页
		return goodsDao.goodInfomation(goodid);
	}
	
	public List<Goods> shopgood(String id){   //商家的店铺
		return goodsDao.shopGood(id);
	}
	
	public void changeamount(String goodid,String amount) {	 //商家更改数量
		goodsDao.changeAmount(goodid, amount);
	}
	
	public void changeprice(String goodid,String price) {
		goodsDao.changePrice(goodid, price);
	}
	
	public void changeDisacount(String goodid,String zhekou) {
		goodsDao.changeDisacount(goodid, zhekou);
	}
	
	public void changeAmount(String id,String goodid,String amount) { //退款  商品库存加回来
		goodsDao.changeAmount(id, goodid, amount);
	}
	
	public void hasBeenSold(String goodid,String shouchu) { //历史销量增加
		goodsDao.hasBeenSold(goodid, shouchu);
	}
	
	public List<Goods> getRemovedGoods(String id,String status){  //下架的商品
		return goodsDao.getRemovedGoods(id, status);
	}
}
