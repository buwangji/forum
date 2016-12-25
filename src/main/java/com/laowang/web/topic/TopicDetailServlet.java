package com.laowang.web.topic;

import com.laowang.entity.Fav;
import com.laowang.entity.Reply;
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
import java.util.List;


@WebServlet("/topicDetail")
public class TopicDetailServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicid = req.getParameter("topicid");
        TopicService topicService = new TopicService();
        try{
            Topic topic = topicService.findTopicById(topicid);
            //更新topic表中的clicknum
            topic.setClicknum(topic.getClicknum()+1);
            topicService.updateTopic(topic);
            //获取topicid对应的回复列表replyList
            List<Reply> replyList = topicService.findReplyListByTopicid(topicid);
            req.setAttribute("replyList",replyList);
            req.setAttribute("topic",topic);

            //判断用户是否收藏该主题
            User user = (User) req.getSession().getAttribute("curr_user");
            if(user != null && StringUtils.isNumeric(topicid)){
                Fav fav = topicService.findFavById(topicid,user);
                req.setAttribute("fav",fav);
            }
            forward("topic/topicdetail.jsp",req,resp);
        }catch (ServletException e){
            resp.sendError(404);
        }

    }
}
