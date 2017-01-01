package com.laowang.web.ad;

import com.laowang.entity.Topic;
import com.laowang.exception.ServiceException;
import com.laowang.service.AdminService;
import com.laowang.service.TopicService;
import com.laowang.util.Page;
import com.laowang.util.StringUtils;
import com.laowang.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/29.
 */
@WebServlet("/ad/topic")
public class AdminTopicServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String  p = req.getParameter("p");
        Integer pageNo = StringUtils.isNumeric(p)?Integer.valueOf(p):1;
        TopicService topicService = new TopicService();
        Page<Topic> page = topicService.findAllTopic("",pageNo);
        req.setAttribute("page",page);
        forward("ad/topic.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        AdminService service = new AdminService();
        try {
            service.delTopicById(id);
            renderText("success",resp);
        }catch (ServiceException e){
            renderText(e.getMessage(),resp);
        }

    }
}
