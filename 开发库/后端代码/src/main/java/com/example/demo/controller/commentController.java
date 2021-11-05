package com.example.demo.controller;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.comment;
import com.example.demo.Service.commentService;
import com.example.demo.Service.userService;

@CrossOrigin
@RestController
public class commentController {
	@Autowired
	private commentService c;
	@Autowired
	private userService u;
	
	@RequestMapping(value = "/addcomment",method = RequestMethod.POST)
	public String addcomment(String id,String word,String goodid,String text,String rate) { //用户评价
		if(u.get(id, word).size() > 0) {
			if(c.ucomment(id, goodid).size() > 0) {
				return "您已评论,请勿重复评论！";
			}else {
				c.addcomment(id, goodid, text, rate);
				return "评论成功！";
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/comment",method = RequestMethod.POST)
    public List<comment> getcomment(String goodid){  //获取商品评价
    	return c.getcomment(goodid);
    }
    
    @RequestMapping(value = "/uc",method = RequestMethod.POST)
    public String ucomment(String id,String goodid){
    	return c.ucomment(id, goodid).get(0).getText();
    }
    
}	
