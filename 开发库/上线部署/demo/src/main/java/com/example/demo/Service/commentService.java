package com.example.demo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Dao.commentDao;
import com.example.demo.Entity.comment;

@Service
public class commentService {
	@Autowired
	private commentDao c;
	
	public void addcomment(String id,String goodid,String text,String rate) { //用户评价
		c.addcomment(id, goodid, text, rate);
	}
	
    public List<comment> getcomment(String goodid){  //获取商品评价
    	return c.getcomment(goodid);
    }
    
    public List<comment> ucomment(String id,String goodid){ //防止重复评价
    	return c.ucomment(id, goodid);
    }
}
