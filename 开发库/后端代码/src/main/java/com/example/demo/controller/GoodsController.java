package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.Goods;
import com.example.demo.Service.GoodsService;
import com.example.demo.Service.carService;
import com.example.demo.Service.userService;

@CrossOrigin
@RestController
public class GoodsController {
	@Autowired //自动连接到UserService Bean
    private GoodsService g;
	@Autowired
	private userService u;
	@Autowired
	private carService c;
	
	@CrossOrigin
	@RequestMapping(value = "/addgood",method = RequestMethod.POST)
	public String addgood(@RequestParam(value="file",required=false) MultipartFile[] file,String id,String word,
			String name,String price,String zhekou,String intro,String newo,String fenlei,String size,
			String yijia,String amount) throws IllegalStateException, IOException {   //商家上架新商品  
		System.out.println(id);
		System.out.println(amount);
		if(u.get(id, word).size() < 1) {
			return "fail";
		}else if(!u.get(id, word).get(0).getStatus().equals("3"))   {
			return "fail";
		}
		if(file == null  ) {
			return "请上传商品图片";
		}
		String goodid = UUID.randomUUID().toString();
		String imgUrl = "";
		String allname = "";
		String imgName = ""; 
		for(int i = 0; i < file.length; i++) {
			allname = file[i].getOriginalFilename();   
			imgName = goodid+String.valueOf(i)+allname.substring(allname.lastIndexOf("."));
			file[i].transferTo(new File("C:\\Users\\13049\\Pictures\\good\\"+imgName));
			imgUrl += imgName + ",";
		}
		g.addgood(id, goodid, name, price, zhekou, intro, newo, fenlei, size, yijia, amount, imgUrl);
		return "请等待管理员审核后，方可正式上架！";
	}
	
	@CrossOrigin
	@RequestMapping(value = "/getgood",method = RequestMethod.POST)
	public List<Goods> getgood(String id,String word) {    //管理员获取待审核的商品
		if(u.get(id, word).size()  > 0 && u.get(id, word).get(0).getStatus().equals("0")) {
			return g.getgood();
		}
		return null;   	
	}
	
	@CrossOrigin
	@RequestMapping(value = "/allowgood",method = RequestMethod.POST)
	public void allowgood(String id,String word,String goodid) {      //允许商品上架
		System.out.println(goodid);
		if(u.get(id, word).size() == 1 && u.get(id, word).get(0).getStatus().equals("0")) {
			g.allowgood(goodid,"2");
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = "/getAllgood",method = RequestMethod.POST)
	public List<Goods> getAllgood() {     //获取所有允许上架的商品展示在主页面
		//System.out.println(g.getAllgood().toString());
		return g.getAllgood();   	
	} 
	
	@CrossOrigin
	@RequestMapping(value = "/goodinfo",method = RequestMethod.POST)
	public List<Goods> goodinfo(String goodid) {     //商品详情页
		System.out.println(goodid);
		return g.goodinfo(goodid);   	
	} 
	
	@CrossOrigin
	@RequestMapping(value = "/shopgood",method = RequestMethod.POST)
	public List<Goods> shopgood(String id){   //商家的店铺
		System.out.println(id);
		return g.shopgood(id);
	}
	
	@RequestMapping(value = "/allgood",method = RequestMethod.POST)
	public List<Goods>	allgood(String id,String word){   //商家的店铺
		if(u.get(id, word).size() > 0) {
			return g.shopgood(id);
		}
		return null;
	}
	
	@RequestMapping(value = "/xiajia",method = RequestMethod.POST)
	public void xiajia(String id,String word,String goodid) {      //允许商品上架
		if(u.get(id, word).size() > 0) {
			g.allowgood(goodid,"3");
		}
	}
	
	@RequestMapping(value = "/shangjia",method = RequestMethod.POST)
	public void shangjia(String id,String word,String goodid) {      //允许商品上架
		if(u.get(id, word).size() > 0) {
			g.allowgood(goodid,"2");
		}
	}
	
	@RequestMapping(value = "/xiajiagood",method = RequestMethod.POST)
	public List<Goods> xiajiagood(String id,String word) {      
		if(u.get(id, word).size() > 0) {
			return g.getxiajia(id, "3");
		}
		return null;
	}
	
	@RequestMapping(value = "/changeamount",method = RequestMethod.POST)
	public void changeamount(String id,String word,String goodid,String amount){
		if(u.get(id, word).size() > 0) {
			g.tui(id, goodid, amount);
			c.changeamount(id, goodid, amount);
		}
	}
	
	@RequestMapping(value = "/changeprice",method = RequestMethod.POST)
	public void changeprice(String id,String word,String goodid,String price,String p){
		System.out.println(p);
		if(u.get(id, word).size() > 0) {
			g.changeprice(goodid, price);
			c.changeprice(id, goodid, p);
		}
	}
	
	@RequestMapping(value = "/changezhekou",method = RequestMethod.POST)
	public void changezhekou(String id,String word,String goodid,String zhekou,String p){
		if(u.get(id, word).size() > 0) {
			g.changezhekou(goodid, zhekou);
			c.changeprice(id, goodid, p);
		}
	}
	
}
