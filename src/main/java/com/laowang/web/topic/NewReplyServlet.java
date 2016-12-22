package com.laowang.web.topic;

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
 * Created by Administrator on 2016/12/22.
 */
@WebServlet("/newreply")
public class NewReplyServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        String content = req.getParameter("content");
        User user = (User) req.getSession().getAttribute("curr_user");
        TopicService service = new TopicService();
        if(StringUtils.isNumeric(topicid)){
            service.addNewTopicReply(topicid,content,user);
        }else{
            resp.sendError(404);
        }
        resp.sendRedirect("/topicDetail?topicid="+topicid);
    }
}
