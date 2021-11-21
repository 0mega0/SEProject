package com.example.demo.dao;

import com.example.demo.Dao.commentDao;
import com.example.demo.Entity.comment;
import com.example.demo.Service.commentService;
import com.example.demo.controller.CarControllerTest;
import com.example.demo.main.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 主要测试了Dao层评论的相关功能，包括：
 * 1.获取商品评价
 * @author 张子文
 *
 */

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
@Transactional
public class CommentDaoTest {

    private static final Logger logger = LoggerFactory.getLogger(CommentDaoTest.class);


    @Resource
    commentDao commentDao;

    @Test
    public void getComment(){
        try{
            List<comment> comment = commentDao.getcomment("adde99dd-2ab5-4b4e-9e98-ca3e6ee15bdc");
            logger.debug(Arrays.toString(comment.toArray(new comment[]{})));
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("getComment is error , errorMessage is : " + e.getMessage());
        }
    }
}
