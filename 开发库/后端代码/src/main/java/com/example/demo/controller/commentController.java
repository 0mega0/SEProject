package com.example.demo.controller;

import java.util.List;

import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Comment;
import com.example.demo.Service.CommentService;

/**
 * @author Alex,0mega_0
 * last change 2021/11/5
 */

@CrossOrigin
@RestController
public class CommentController {
	@Autowired
	private CommentService c;
	@Autowired
	private UserService u;
	
	@RequestMapping(value = "/addcomment",method = RequestMethod.POST)
	public String addComment(String id,String word,String goodId,String text,String rate) { //用户评价
		if(u.getUser(id, word).size() > 0) {
			if(c.userComment(id, goodId).size() > 0) {
				return "您已评论,请勿重复评论！";
			}else {
				c.addComment(id, goodId, text, rate);
				return "评论成功！";
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/comment",method = RequestMethod.POST)
    public List<Comment> getComment(String goodId){  //获取商品评价
    	return c.getComment(goodId);
    }
    
    @RequestMapping(value = "/uc",method = RequestMethod.POST)
    public String userComment(String id,String goodId){
    	return c.userComment(id, goodId).get(0).getText();
    }
    
}	
