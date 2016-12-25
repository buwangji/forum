package com.laowang.web.topic;

import com.laowang.dto.JsonResult;
import com.laowang.entity.Topic;
import com.laowang.entity.User;
import com.laowang.service.TopicService;
import com.laowang.util.StringUtils;
import com.laowang.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/25.
 */
@WebServlet("/topicfav")
public class TopicFavServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        String action = req.getParameter("action");
        User user = (User) req.getSession().getAttribute("curr_user");
        TopicService service = new TopicService();
        JsonResult result = new JsonResult();
        if(StringUtils.isNotEmpty(action)&&StringUtils.isNumeric(topicid)){
            if(action.equals("fav")){
                service.addFavTopic(topicid , user);
                result.setState("success");
            }else if (action.equals("unfav")){
                service.unFavTopic(topicid,user);
                result.setState("success");
            }
            TopicService topicService = new TopicService();
            Topic topic = topicService.findTopicById(topicid);
            result.setData(topic.getFavnum());
        }else{
            result.setMessage("参数异常");
        }
        renderJSON(result,resp);
    }
}
