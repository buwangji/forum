package com.laowang.web.topic;

import com.laowang.entity.Topic;
import com.laowang.service.TopicService;
import com.laowang.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/21.
 */
@WebServlet("/topicDetail")
public class TopicDetailServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        TopicService topicService = new TopicService();
        try{
            Topic topic = topicService.findTopicById(topicid);
            req.setAttribute("topic",topic);
            forward("topic/topicdetail.jsp",req,resp);
        }catch (ServletException e){
            resp.sendError(404);
        }

    }
}
