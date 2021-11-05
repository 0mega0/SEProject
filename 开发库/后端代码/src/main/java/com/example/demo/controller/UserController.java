package com.example.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;

/**
 * @author Alex,Scarlet_sky
 * last change 2021/11/5
 */

@CrossOrigin
@RestController
public class UserController { // 用户控制器
	@Autowired // 自动连接到UserService Bean
	private UserService userService;

	/**
	 * 输出Hello World!
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/show")
	public String show() {
		return userService.show();
	}

	/**
	 * 登录验证
	 * 
	 * @param id   用户ID
	 * @param word 用户密码
	 * @return parameter登录角色
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String id, String word) {
		// System.out.println(id);
		// System.out.println(word);
		if (userService.getUser(id, word).size() < 1 || userService.getUser(id, word) == null) {
			return "账号或密码错误！";
		} else if (userService.getUser(id, word).get(0).getStatus().equals("0")) {
			return "admin";
		} else if (userService.getUser(id, word).get(0).getStatus().equals("1")
				|| userService.getUser(id, word).get(0).getStatus().equals("2")
				|| userService.getUser(id, word).get(0).getStatus().equals("3")) {
			return "user";
		}
		return "账号或密码错误！";
	}

	/**
	 * 验证用户状态
	 * 
	 * @param id   用户ID
	 * @param word 用户密码
	 * @return 登录是否成功
	 */
	@RequestMapping(value = "/login1", method = RequestMethod.POST)
	public List<User> login1(String id, String word) {
		if (userService.getUser(id, word).size() > 0) {
			return userService.getUser(id, word);
		}
		return null;
	}

	/**
	 * 注册用户信息插入待验证用户表
	 * 
	 * @param id      用户ID
	 * @param word    用户密码
	 * @param name    姓名
	 * @param sex     性别
	 * @param phone   电话
	 * @param email   电子邮箱
	 * @param city    城市
	 * @param account 银行账户
	 */
	@RequestMapping(value = "/insertZhuce", method = RequestMethod.POST)
	public void insertRegisteredUser(String id, String word, String name, String sex, String phone, String email, String city,
			String account) {
		userService.insertRegisteredUser(id, word, name, sex, phone, email, city, account);
	}

	/**
	 * 验证注册用户是否重复提交
	 * 
	 * @param id 用户ID
	 * @return 已注册未审核的id查询
	 */
	@CrossOrigin
	@RequestMapping(value = "/getZhuceUser", method = RequestMethod.POST)
	public List<User> getRegisteredUser(String id) {
		return userService.getRegisteredUser(id);
	}

	/**
	 * 获取待验证的注册用户列表
	 * 
	 * @param id   用户ID
	 * @param word 用户密码
	 * @return 待验证列表
	 */
	@CrossOrigin
	@RequestMapping(value = "/getUser", method = RequestMethod.POST)
	public List<User> getUnauditedUser(String id, String word) {
		if (userService.getUser(id, word).get(0).getStatus().equals("0")) {
			return userService.getUnauditedUser();
		}
		return null;
	}

	/**
	 * 删除待验证表的允许登录的注册用户
	 * 
	 * @param id 用户ID
	 */
	@CrossOrigin
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public void deleteUser(String id) {
		userService.deleteUser(id);
		System.out.println(id);
	}

	/**
	 * 审核通过的用户插入允许登录表
	 * 
	 * @param id      用户ID
	 * @param word    用户密码
	 * @param name    姓名
	 * @param sex     性别
	 * @param phone   电话号码
	 * @param email   电子邮箱
	 * @param city    城市
	 * @param account 银行账户
	 */
	@CrossOrigin
	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
	public void insertUser(String id, String word, String name, String sex, String phone, String email, String city,
			String account) {
		userService.insertUser(id, word, name, sex, phone, email, city, account, "1");
	}

	/**
	 * 获取用户个人信息
	 * 
	 * @param id   用户ID
	 * @param word 用户密码
	 * @return List 用户信息
	 */
	@CrossOrigin
	@RequestMapping(value = "/personal", method = RequestMethod.POST)
	public List<User> getUserInfo(String id, String word) {
		//System.out.println(id);
		//System.out.println(word);
		return userService.getUserInfo(id, word);
	}

	/**
	 * 修改个人信息
	 * 
	 * @param name    姓名
	 * @param phone   电话号码
	 * @param email   电子邮箱
	 * @param city    城市
	 * @param account 银行账户
	 * @param id      用户ID
	 * @param word    用户密码
	 */
	@CrossOrigin
	@RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
	public void updateUserInfo(String name, String phone, String email, String city, String account, String id,
			String word) { // 用户更改个人信息
		userService.updateUserInfo(name, phone, email, city, account, id, word);
	}

	/**
	 * 用户申请注册成为商家
	 * 
	 * @param licence
	 * @param card
	 * @param id      用户ID
	 * @param word    用户密码
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@CrossOrigin
	@RequestMapping(value = "/shopzhuce", method = RequestMethod.POST)
	public String shopRegister(@RequestParam(value = "licence", required = false) MultipartFile licence,
			@RequestParam(value = "card", required = false) MultipartFile card, String id, String word)
			throws IllegalStateException, IOException { // 用户注册商家
		System.out.println(id);
		if (licence == null || card == null) {
			return "上传失败，请您重新上传！";
		}
		String allname = licence.getOriginalFilename(); // 营业执照保存至文件夹
		String licenceName = id + "licence" + allname.substring(allname.lastIndexOf("."));
		licence.transferTo(new File("C:\\Users\\13049\\Pictures\\shop\\" + licenceName));

		String allname2 = card.getOriginalFilename(); // 身份证照片保存至文件夹
		String cardName = id + "card" + allname2.substring(allname2.lastIndexOf("."));
		card.transferTo(new File("C:\\Users\\13049\\Pictures\\shop\\" + cardName));

		userService.shopRegister(id, word, licenceName, cardName, "2");
		return "您已申请注册，请等待管理员确认！";
	}

	/**
	 * 获取申请注册商家的用户信息
	 * 
	 * @param id   用户ID
	 * @param word 用户密码
	 * @return List<User> 申请的用户表
	 */
	@RequestMapping(value = "/getshopzhuce", method = RequestMethod.POST)
	public List<User> getUnauditedShop(String id, String word) {
		if (this.login(id, word) == "admin") {
			return userService.getUnauditedShop();
		}
		return null;
	}

	/**
	 * 审核商家列表
	 * 
	 * @param id 用户ID
	 */
	@RequestMapping(value = "/allowshopzhuce", method = RequestMethod.POST)
	public void allowShopApply(String id) {
		userService.allowShopApply(id);
	}

	/**
	 * 获取商家等级
	 * 
	 * @param id   用户ID
	 * @param word 用户密码
	 * @return List 商家等级列表
	 */
	@RequestMapping(value = "/getshoplevel", method = RequestMethod.POST)
	public List<User> getShopLevel(String id, String word) {
		if (userService.getUser(id, word).size() > 0) {
			return userService.getShopLevel();
		}
		return null;
	}

	/**
	 * 管理员管理商家等级
	 * 
	 * @param id    用户ID
	 * @param word  用户密码
	 * @param uid
	 * @param level 商家等级
	 */
	@RequestMapping(value = "/changelevel", method = RequestMethod.POST)
	public void changeLevel(String id, String word, String uid, String level) {
		if (userService.getUser(id, word).size() > 0) {
			userService.changeLevel(uid, level);
		}
	}

	/**
	 * 充值钱包
	 * 
	 * @param id     用户ID
	 * @param word   用户密码
	 * @param money  账户余额
	 * @param charge 充值金额
	 */
	@RequestMapping(value = "/chongzhi", method = RequestMethod.POST)
	public void rechargeWallet(String id, String word, String money, String charge) {
		if (userService.getUser(id, word).size() > 0) {
			double a = Double.parseDouble(money) + Double.parseDouble(charge);
			userService.rechargeWallet(id, String.valueOf(a));
		}
	}
}
