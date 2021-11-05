package com.example.demo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.User;

@Service
public class UserService {
	@Autowired //连接到UserDao Bean
	private UserDao userDao;
	
	public List<User> getUser(String id,String word) {		//登录验证
		return userDao.getUser(id, word);
	}
	
	public void insertRegisteredUser(String id,String word,String name,String sex,
			String phone,String email,String city,String account) {    //注册用户信息插入待验证用户表
		userDao.insertRegisteredUser(id, word, name, sex, phone, email, city, account);
	}
	
	public List<User> getRegisteredUser(String id){    //验证注册用户是否重复提交信息
		return userDao.getRegisteredUser(id);
	}
	
	public List<User> getUnauditedUser(){    //获取待验证的注册用户列表
		return userDao.getUnauditedUser();
	}
	
    public void deleteUser(String id) {   //删除允许登录的注册用户
    	 userDao.deleteUser(id);
    };      
		
	public void insertUser(String id,String word,String name,String sex,
			String phone,String email,String city,String account,String status) {    //允许登录的注册用户信息插入用户表
		userDao.insertUser(id, word, name, sex, phone, email, city, account,status);
	}
	
	public List<User> getUserInfo(String id,String word){    //获取用户个人信息
		return userDao.getUserInfo(id, word);
	}
	
	public void updateUserInfo(String name,String phone,String email,String city,String account,String id,String word) {  //用户更改个人信息
		userDao.updateUserInfo(name, phone, email, city, account, id, word);
	}
	
	public void shopRegister(String id,String word,String licence,String card,String status) {  //用户注册商家
		userDao.shopRegister(id, word, licence, card, status);
	}
	
	public List<User> getUnauditedShop(){    //获取注册商家的用户信息
		return userDao.getUnauditedShop("2");
	}
	
	public List<User> getShopLevel(){    //获取商家的等级
		return userDao.getShopLevel("3");
	}
	
	public void allowShopApply(String id){   //允许注册商家的用户status更改为3
		userDao.allowShopApply("3", id);
	}
	 
	public void pay(String id,String money) { //用户下单后扣除相应的钱数
		userDao.pay(id, money);
	}
	
	public List<User> userMoney(String id){ //获取用户的钱数
		return userDao.userMoney(id);
	}
	
	public void userGrade(String id,String grade) { //更新用户积分
		userDao.userGrade(id, grade);
	}
	
	public void changeLevel(String id,String level) { //更新商家等级
		userDao.changeLevel(id, level);
	}
	
	public void rechargeWallet(String id,String money) { //充值
		userDao.rechargeWallet(id, money);
	}
	
	public String show() {
        return "Hello World!";
    }
}
