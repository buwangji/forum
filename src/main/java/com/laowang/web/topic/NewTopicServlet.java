package com.laowang.web.topic;

import com.laowang.dto.JsonResult;
import com.laowang.entity.Node;
import com.laowang.entity.Topic;
import com.laowang.entity.User;
import com.laowang.exception.ServiceException;
import com.laowang.service.TopicService;
import com.laowang.util.Config;
import com.laowang.web.BaseServlet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
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
        Auth auth = Auth.create(Config.get("qiniu.ak"),Config.get("qiniu.sk"));
        StringMap stringMap = new StringMap();
        stringMap.put("returnBody","{ \"success\": true,\"file_path\": \""+Config.get("qiniu.domain")+"${key}\"}");
        String token = auth.uploadToken(Config.get("qiniu.bucket"),null,3600,stringMap);
        //获取nodelist到jsp
        List<Node> nodeList = topicService.findAllNode();
        req.setAttribute("nodeList",nodeList);
        forward("topic/newtopic.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String nodeid = req.getParameter("nodeid");
        User user = (User) req.getSession().getAttribute("curr_user");
        JsonResult result = new JsonResult();
        try{
            Topic topic = topicService.addNewTopic(title,content,Integer.valueOf(nodeid),user.getId());
            result = new JsonResult(topic);
        }catch (ServiceException e){
            result = new JsonResult(e.getMessage());
        }
        renderJSON(result,resp);
    }
}
