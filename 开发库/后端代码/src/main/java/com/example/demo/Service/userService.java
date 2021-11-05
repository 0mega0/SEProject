package com.example.demo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Dao.userDao;
import com.example.demo.Entity.User;

@Service
public class userService {
	@Autowired //连接到UserDao Bean
	private userDao userD;
	
	public List<User> get(String id,String word) {		//登录验证
		return userD.get(id, word);
	}
	
	public void insertZhuce(String id,String word,String name,String sex,
			String phone,String email,String city,String account) {    //注册用户信息插入待验证用户表
		userD.insertZhuce(id, word, name, sex, phone, email, city, account);
	}
	
	public List<User> getZhuceUser(String id){    //验证注册用户是否重复提交信息
		return userD.getZhuceUser(id);
	}
	
	public List<User> getUser(){    //获取待验证的注册用户列表
		return userD.getUser();
	}
	
    public void deleteUser(String id) {   //删除允许登录的注册用户
    	 userD.deleteUser(id);
    };      
		
	public void insertUser(String id,String word,String name,String sex,
			String phone,String email,String city,String account,String status) {    //允许登录的注册用户信息插入用户表
		userD.insertUser(id, word, name, sex, phone, email, city, account,status);
	}
	
	public List<User> getuserinfo(String id,String word){    //获取用户个人信息
		return userD.getUserinfo(id, word);
	}
	
	public void updateInfo(String name,String phone,String email,String city,String account,String id,String word) {  //用户更改个人信息
		userD.updateInfo(name, phone, email, city, account, id, word);
	}
	
	public void shopzhuce(String id,String word,String licence,String card,String status) {  //用户注册商家
		userD.shopzhuce(id, word, licence, card, status);
	}
	
	public List<User> getshopzhuce(){    //获取注册商家的用户信息
		return userD.getshopzhuce("2");
	}
	
	public List<User> getshoplevel(){    //获取商家的等级
		return userD.getshoplevel("3");
	}
	
	public void allowshopzhuce(String id){   //允许注册商家的用户status更改为3
		userD.allowshopzhuce("3", id);
	}
	 
	public void pay(String id,String money) { //用户下单后扣除相应的钱数
		userD.pay(id, money);
	}
	
	public List<User> umoney(String id){ //获取用户的钱数
		return userD.umoney(id);
	}
	
	public void grade(String id,String grade) { //更新用户积分
		userD.grade(id, grade);
	}
	
	public void changelevel(String id,String level) { //更新商家等级
		userD.changelevel(id, level);
	}
	
	public void chongzhi(String id,String money) { //充值
		userD.chongzhi(id, money);
	}
	
	public String show() {
        return "Hello World!";
    }
}
