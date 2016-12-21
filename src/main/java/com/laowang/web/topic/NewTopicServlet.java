package com.laowang.web.topic;

import com.laowang.dto.JsonResult;
import com.laowang.entity.Node;
import com.laowang.entity.Topic;
import com.laowang.entity.User;
import com.laowang.service.TopicService;
import com.laowang.web.BaseServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("tatle");
        String content = req.getParameter("content");
        String nodeid = req.getParameter("nodeid");
        User user = (User) req.getSession().getAttribute("curr_user");
        Topic topic = topicService.addNewTopic(title,content,Integer.valueOf(nodeid),user.getId());
        JsonResult result = new JsonResult(topic);
        renderJSON(result,resp);
    }
}
