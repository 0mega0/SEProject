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
import com.example.demo.Service.CarService;
import com.example.demo.Service.UserService;

/**
 * @author Alex,Scarlet_sky
 * last change 2021/11/5
 */

@CrossOrigin
@RestController
public class GoodsController {
	@Autowired // 自动连接到UserService Bean
	private GoodsService goodsService;
	@Autowired
	private UserService userService;
	@Autowired
	private CarService carService;

	/**
	 * 商家上传并提供管理员审核的商品,未注册或非商家上传失败
	 * 
	 * @param file      图片
	 * @param userId	用户id
	 * @param password	密码
	 * @param name      姓名
	 * @param price     价格
	 * @param discount	折扣
	 * @param intro     商品简介
	 * @param newOrOld  新旧程度
	 * @param catalogue 分类
	 * @param size      尺寸
	 * @param bargain   是否可以议价
	 * @param amount    上架商品的数量
	 * @return 是否成功上传商品
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@CrossOrigin
	@RequestMapping(value = "/addgood", method = RequestMethod.POST)
	public String addGood(@RequestParam(value = "file", required = false) MultipartFile[] file, String userId, String password,
			String name, String price, String discount, String intro, String newOrOld, String catalogue, String size,
			String bargain, String amount) throws IllegalStateException, IOException { // 商家上架新商品
		if (userService.getUser(userId, password).size() < 1) {
			return "fail";
		} else if (!userService.getUser(userId, password).get(0).getStatus().equals("3")) {
			return "fail";
		}
		if (file == null) {
			return "请上传商品图片";
		}
		String goodId = UUID.randomUUID().toString();
		String imgUrl = "";
		String allName = "";
		String imgName = "";
		for (int i = 0; i < file.length; i++) {
			allName = file[i].getOriginalFilename();
			imgName = goodId + String.valueOf(i) + allName.substring(allName.lastIndexOf("."));
			file[i].transferTo(new File("C:\\Users\\13049\\Pictures\\good\\" + imgName));
			imgUrl += imgName + ",";
		}
		goodsService.addgood(userId, goodId, name, price, discount, intro, newOrOld, catalogue, size, bargain, amount, imgUrl);
		return "请等待管理员审核后，方可正式上架！";
	}

	/**
	 * 管理员获取未审核的商品
	 * 
	 * @param userId	用户ID
	 * @param password	用户密码
	 * @return 待审核列表
	 */
	@CrossOrigin
	@RequestMapping(value = "/getgood", method = RequestMethod.POST)
	public List<Goods> getUnauditedGood(String userId, String password) {
		if (userService.getUser(userId, password).size() > 0 && userService.getUser(userId, password).get(0).getStatus().equals("0")) {
			return goodsService.getUnauditedGood();
		}
		return null;
	}

	/**
	 * 管理员从已下架的商品中上架
	 * 
	 * @param userId     用户ID
	 * @param word   用户密码
	 * @param goodid 商品ID
	 */
	@CrossOrigin
	@RequestMapping(value = "/allowgood", method = RequestMethod.POST)
	public void adminUpGood(String userId, String word, String goodid) {
		// System.out.println(goodid);
		if (userService.getUser(userId, word).size() == 1 && userService.getUser(userId, word).get(0).getStatus().equals("0")) {
			goodsService.allowgood(goodid, "2");
		}
	}

	/**
	 *
	 * 管理员管理商品
	 * 
	 * @param id   用户ID
	 * @param word 用户密码
	 * @return 已下架的商品列表
	 */
	@RequestMapping(value = "/xiajiagood", method = RequestMethod.POST)
	public List<Goods> adminDownGood(String id, String word) {
		if (userService.getUser(id, word).size() > 0) {
			return goodsService.getRemovedGoods(id, "3");
		}
		return null;
	}

	/**
	 * 商家上架商品
	 * 
	 * @param id     用户ID
	 * @param word   用户密码
	 * @param goodId 商品ID
	 */

	@RequestMapping(value = "/shangjia", method = RequestMethod.POST)
	public void upGood(String id, String word, String goodId) {
		if (userService.getUser(id, word).size() > 0) {
			goodsService.allowgood(goodId, "2");
		}
	}

	/**
	 * 商家下架商品
	 * 
	 * @param id     用户ID
	 * @param word   用户密码
	 * @param goodId 商品ID
	 */
	@RequestMapping(value = "/xiajia", method = RequestMethod.POST)
	public void downGood(String id, String word, String goodId) {
		if (userService.getUser(id, word).size() > 0) {
			goodsService.allowgood(goodId, "3");
		}
	}

	/**
	 * 获取所有允许上架的商品展示在主页面
	 * 
	 * @return 上架的商品列表
	 */
	@CrossOrigin
	@RequestMapping(value = "/getAllgood", method = RequestMethod.POST)
	public List<Goods> getAllGood() {
		// System.out.println(g.getAllgood().toString());
		return goodsService.getAllgood();
	}

	/**
	 * 商品详情页信息
	 * 
	 * @param goodid 商品ID
	 * @return 商品详情信息
	 */
	@CrossOrigin
	@RequestMapping(value = "/goodinfo", method = RequestMethod.POST)
	public List<Goods> goodInfomation(String goodid) {
		// System.out.println(goodid);
		return goodsService.goodinfo(goodid);
	}

	/**
	 * 获取店铺商品
	 * 
	 * @param id 用户ID
	 * @return 店铺信息
	 */
	@CrossOrigin
	@RequestMapping(value = "/shopgood", method = RequestMethod.POST)
	public List<Goods> shopInformation(String id) {
		// System.out.println(id);
		return goodsService.shopgood(id);
	}

	/**
	 * 获取所有商品
	 * 
	 * @param id
	 * @param word
	 * @return
	 */
	@RequestMapping(value = "/allgood", method = RequestMethod.POST)
	public List<Goods> shopGood(String id, String word) {
		if (userService.getUser(id, word).size() > 0) {
			return goodsService.shopgood(id);
		}
		return null;
	}

	/**
	 * 修改商品数量
	 * 
	 * @param id     用户ID
	 * @param word   用户密码
	 * @param goodid 商品ID
	 * @param amount 商品数量
	 */
	@RequestMapping(value = "/changeamount", method = RequestMethod.POST)
	public void changeAmount(String id, String word, String goodid, String amount) {
		if (userService.getUser(id, word).size() > 0) {
			goodsService.changeAmount(id, goodid, amount);
			carService.amountChange(id, goodid, amount);
		}
	}

	/**
	 * 修改商品价格
	 * 
	 * @param id       用户ID
	 * @param word     用户密码
	 * @param goodid   商品ID
	 * @param price    商品旧价格
	 * @param newprice 商品新价格
	 */
	@RequestMapping(value = "/changeprice", method = RequestMethod.POST)
	public void changePrice(String id, String word, String goodid, String price, String newprice) {
		//System.out.println(newprice);
		if (userService.getUser(id, word).size() > 0) {
			goodsService.changeprice(goodid, price);
			carService.priceChange(id, goodid, newprice);
		}
	}

	/**
	 * 修改商品折扣
	 * 
	 * @param id       用户ID
	 * @param word     用户密码
	 * @param goodId   商品ID
	 * @param discount 商品折扣
	 * @param price    商品价格
	 */
	@RequestMapping(value = "/changezhekou", method = RequestMethod.POST)
	public void changeDiscount(String id, String word, String goodId, String discount, String price) {
		if (userService.getUser(id, word).size() > 0) {
			goodsService.changeDisacount(goodId, discount);
			carService.priceChange(id, goodId, price);
		}
	}

}
