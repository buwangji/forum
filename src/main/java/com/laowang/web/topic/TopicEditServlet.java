package com.laowang.web.topic;

import com.laowang.dto.JsonResult;
import com.laowang.entity.Node;
import com.laowang.entity.Topic;
import com.laowang.exception.ServiceException;
import com.laowang.service.TopicService;
import com.laowang.util.StringUtils;
import com.laowang.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */
@WebServlet("/topicedit")
public class TopicEditServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        TopicService service = new TopicService();
        Topic topic = service.findTopicById(topicid);
        List<Node> nodeList = service.findAllNode();
        req.setAttribute("topic",topic);
        req.setAttribute("nodeList",nodeList);
        forward("topic/topicedit.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String nodeid = req.getParameter("nodeid");
        String topicid = req.getParameter("topicid");
        JsonResult result = new JsonResult();
        if(StringUtils.isNumeric(topicid)){
            TopicService service = new TopicService();
            try{
                service.updateTopicById(title,content,nodeid,topicid);
                result.setState("success");
                result.setData(topicid);
            }catch (ServiceException e){
                result.setMessage(e.getMessage());
            }
        }else{
            result.setMessage("参数异常");
        }
        renderJSON(result,resp);
    }
}
