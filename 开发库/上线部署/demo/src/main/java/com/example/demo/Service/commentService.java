package com.example.demo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Dao.commentDao;
import com.example.demo.Entity.comment;

/**
 * @author Alex,0mega_0
 * last change 2021/11/5
 */

@Service
public class commentService {
	@Autowired
	private commentDao c;


	/**
	 * 用户对某商品进行评价
	 * @param id		用户id
	 * @param goodid	商品id
	 * @param text		评价正文
	 * @param rate		评分
	 */
	public void addcomment(String id,String goodid,String text,String rate) { //用户评价
		c.addcomment(id, goodid, text, rate);
	}

	/**
	 * 返回某商品的全部评价记录
	 * @param goodid	商品id
	 * @return			返回某商品的评价列表
	 */
    public List<comment> getcomment(String goodid){  //获取商品评价
    	return c.getcomment(goodid);
    }

	/**
	 * 返回某用户对于某商品的评论记录，用于防止用户重复评价
	 * @param id		用户id
	 * @param goodid	商品id
	 * @return			返回某用户在某商品的评价列表
	 */
    public List<comment> ucomment(String id,String goodid){ //防止重复评价
    	return c.ucomment(id, goodid);
    }
}
