package com.laowang.web.topic;

import com.laowang.entity.Node;
import com.laowang.service.TopicService;
import com.laowang.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/20.
 */
@WebServlet("/newtopic")
public class NewTopicServlet extends BaseServlet {
    TopicService topicService = new TopicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取nodelist到jsp
        List<Node> nodeList = topicService.findAllNode();
        req.setAttribute("nodeList",nodeList);
        forward("topic/newtopic.jsp",req,resp);
    }
}
