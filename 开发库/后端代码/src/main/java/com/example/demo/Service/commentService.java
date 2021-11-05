package com.example.demo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Dao.CommentDao;
import com.example.demo.Entity.Comment;

/**
 * @author Alex,0mega_0
 * last change 2021/11/5
 */

@Service
public class CommentService {
	@Autowired
	private CommentDao c;
	
	public void addComment(String id,String goodId,String text,String rate) { //用户评价
		c.addComment(id, goodId, text, rate);
	}
	
    public List<Comment> getComment(String goodId){  //获取商品评价
    	return c.getComment(goodId);
    }
    
    public List<Comment> userComment(String id, String goodId){
    	return c.userComment(id, goodId);
    }
}
