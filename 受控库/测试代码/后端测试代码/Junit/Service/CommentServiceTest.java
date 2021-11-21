package com.example.demo.service;

import com.example.demo.Entity.comment;
import com.example.demo.Entity.User;
import com.example.demo.Service.commentService;
import com.example.demo.Service.userService;
import com.example.demo.controller.CarControllerTest;
import com.example.demo.main.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 主要测试了Service层评论的相关功能，包括：
 * 1.//获取用户评论
 * @author zzw
 *
 */

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
@Transactional
public class CommentServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(CarControllerTest.class);


    @Resource
    commentService commentService;

    @Test
    public void getComment(){//获取用户评论
        try{
            List<comment> comment = commentService.getcomment("adde99dd-2ab5-4b4e-9e98-ca3e6ee15bdc");
            System.out.println(Arrays.toString(comment.toArray(new comment[]{})));
            logger.debug(Arrays.toString(comment.toArray(new comment[]{})));
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("getComment is error , errorMessage is : " + e.getMessage());
        }
    }
}
