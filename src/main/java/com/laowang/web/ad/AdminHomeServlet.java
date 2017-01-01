package com.laowang.web.ad;
import com.laowang.entity.TopicReplyCount;
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
 * Created by Administrator on 2017/1/1.
 */
@WebServlet("/ad/home")
public class AdminHomeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p = req.getParameter("p");
        Integer pageNo = StringUtils.isNumeric(p)?Integer.valueOf(p):1;
        Page<TopicReplyCount> page = new TopicService().getTopicAndReplyNumByDayList(pageNo);
        req.setAttribute("page",page);
        forward("ad/home.jsp",req,resp);
    }
}
