package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.comment;
import com.example.demo.Service.commentService;
import com.example.demo.Service.userService;

/**
 * @author Alex,0mega_0
 * last change 2021/11/5
 */

@CrossOrigin
@RestController
public class commentController {
	@Autowired
	private commentService c;
	@Autowired
	private userService u;

	/**
	 * 用户对某商品进行评价
	 * @param id        评价用户的id
	 * @param word	    评价词
	 * @param goodid    商品id
	 * @param text	    评论正文
	 * @param rate      评分分数
	 * @return			返回评论状态提示语
	 */
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

	/**
	 *返回某商品的全部评价记录
	 * @param goodid	商品id
	 * @return			返回某商品的评价列表
	 */
	@RequestMapping(value = "/comment",method = RequestMethod.POST)
    public List<comment> getcomment(String goodid){  //获取商品评价
    	return c.getcomment(goodid);
    }

	/**
	 * 返回某用户对于某商品的评论记录，用于防止用户重复评价
	 * @param id        用户id
	 * @param goodid	商品id
	 * @return			返回某用户对某商品的评论
	 */
    @RequestMapping(value = "/uc",method = RequestMethod.POST)
    public String ucomment(String id,String goodid){
    	return c.ucomment(id, goodid).get(0).getText();
    }
    
}	
