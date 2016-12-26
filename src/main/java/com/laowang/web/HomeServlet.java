package com.laowang.web;

import com.laowang.entity.Node;
import com.laowang.entity.Topic;
import com.laowang.service.TopicService;
import com.laowang.util.Page;
import com.laowang.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/15.
 */
@WebServlet("/home")
public class HomeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("nodeid");
        String p = req.getParameter("p");
        Integer pageNo = StringUtils.isNumeric(p)?Integer.valueOf(p):1;
        if(!StringUtils.isEmpty(nodeid)&&!StringUtils.isNumeric(nodeid)){
            forward("index.jsp",req,resp);
            return;
        }

        TopicService topicService = new TopicService();
        List<Node> nodeList = topicService.findAllNode();
        Page<Topic> page = topicService.findAllTopic(nodeid,pageNo);
        req.setAttribute("nodeList",nodeList);
        req.setAttribute("page",page);
        forward("index.jsp",req,resp);
    }
}
