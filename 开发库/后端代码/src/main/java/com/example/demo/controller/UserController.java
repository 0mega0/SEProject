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
import com.example.demo.Service.userService;

@CrossOrigin
@RestController
public class UserController {    //用户控制器
	@Autowired //自动连接到UserService Bean
    private userService userS;
	
	@RequestMapping(value = "/show")
	public String show() {
		return userS.show();
	}
	
	            //登录验证接口
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public  String login(String id, String word) {
		System.out.println(id);
		System.out.println(word);
		if(userS.get(id, word).size() < 1 || userS.get(id, word) == null) {
			return "账号或密码错误！";
		}else if(userS.get(id, word).get(0).getStatus().equals("0")) {
			return "admin";
		}else if(userS.get(id, word).get(0).getStatus().equals("1") || userS.get(id, word).get(0).getStatus().equals("2") ||
				userS.get(id, word).get(0).getStatus().equals("3")) {
			return "user";
		}
		return "账号或密码错误！";
	}
	
	@RequestMapping(value = "/login1",method = RequestMethod.POST)
	public  List<User> login1(String id, String word) {
		if(userS.get(id, word).size() > 0) {
			return userS.get(id, word);
		}
		return null;
	}
	
	             
	@RequestMapping(value = "/insertZhuce",method = RequestMethod.POST)
	public void insertZhuce(String id,String word,String name,String sex,
			String phone,String email,String city,String account) {    //注册用户信息插入待验证用户表
		userS.insertZhuce(id, word, name, sex, phone, email, city, account);
	}
	
	@CrossOrigin             //验证注册用户是否重复提交
	@RequestMapping(value = "/getZhuceUser",method = RequestMethod.POST)
	public  List<User> getZhuceUser(String id) {
		return userS.getZhuceUser(id);
	}
	
	
	@CrossOrigin             //获取待验证的注册用户列表
	@RequestMapping(value = "/getUser",method = RequestMethod.POST)
	public  List<User> getUser(String id,String word) {
		if(userS.get(id, word).get(0).getStatus().equals("0")) {
			return userS.getUser();
		}
		return null;
	}
	
	@CrossOrigin            
	@RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
	public void deleteUser(String id) {   //删除待验证表的允许登录的注册用户
   	 	userS.deleteUser(id);
   	 	System.out.println(id);
	}
	
	@CrossOrigin            
	@RequestMapping(value = "/insertUser",method = RequestMethod.POST)
	public void insertUser(String id,String word,String name,String sex,
			String phone,String email,String city,String account) {    //允许登录的注册用户信息插入用户表
		userS.insertUser(id, word, name, sex, phone, email, city, account,"1");
	}
	
	@CrossOrigin            
	@RequestMapping(value = "/personal",method = RequestMethod.POST)
	public List<User> getpersonnalinfo(String id,String word) {    //获取用户个人信息
		System.out.println(id);
		System.out.println(word);
		return userS.getuserinfo(id, word);
	}
	
	@CrossOrigin            
	@RequestMapping(value = "/updateInfo",method = RequestMethod.POST)
	public void updateInfo(String name,String phone,String email,String city,String account,String id,String word) {  //用户更改个人信息
		userS.updateInfo(name, phone, email, city, account, id, word);
	}
	
	@CrossOrigin            
	@RequestMapping(value = "/shopzhuce",method = RequestMethod.POST)
	public String shopzhuce(@RequestParam(value="licence",required=false) MultipartFile licence,
			@RequestParam(value="card",required=false) MultipartFile card,String id,String word) throws IllegalStateException, IOException {    //用户注册商家
		System.out.println(id);
		if(licence == null || card == null) {
			return "上传失败，请您重新上传！";
		}
		String allname = licence.getOriginalFilename();    //营业执照保存至文件夹
		String licenceName = id+"licence"+allname.substring(allname.lastIndexOf("."));
		licence.transferTo(new File("C:\\Users\\13049\\Pictures\\shop\\"+licenceName));
		
		String allname2 = card.getOriginalFilename();      //身份证照片保存至文件夹
		String cardName = id+"card"+allname2.substring(allname2.lastIndexOf("."));
		card.transferTo(new File("C:\\Users\\13049\\Pictures\\shop\\"+cardName));
		
		userS.shopzhuce(id, word, licenceName, cardName, "2");
		return "您已申请注册，请等待管理员确认！";
	}
	            
	@RequestMapping(value = "/getshopzhuce",method = RequestMethod.POST)
	public List<User> getshopzhuce(String id,String word) {    //获取注册商家的用户信息
		if(this.login(id, word) == "admin") {
			return userS.getshopzhuce();
		}
		return null;
	}
	            
	@RequestMapping(value = "/allowshopzhuce",method = RequestMethod.POST)
	public void allowshopzhuce(String id) {  //用户更改个人信息
		userS.allowshopzhuce(id);;
	}
	
	@RequestMapping(value = "/getshoplevel",method = RequestMethod.POST)
	public List<User> getshoplevel(String id,String word) {  
		if(userS.get(id, word).size() > 0) {
			return userS.getshoplevel();
		}
		return null;
	}
	
	@RequestMapping(value = "/changelevel",method = RequestMethod.POST)
	public void changelevel(String id,String word,String uid,String level) {  
		if(userS.get(id, word).size() > 0) {
			userS.changelevel(uid, level);
		}
	}
	
	@RequestMapping(value = "/chongzhi",method = RequestMethod.POST)
	public void chongzhi(String id,String word,String money,String v) {  
		if(userS.get(id, word).size() > 0) {
			double a = Double.parseDouble(money) + Double.parseDouble(v);
			userS.chongzhi(id, String.valueOf(a));
		}
	}
}
