package com.laowang.web.user;

import com.google.common.collect.Maps;
import com.laowang.exception.ServiceException;
import com.laowang.service.UserService;
import com.laowang.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/17.
 */
@WebServlet("/foundPassword")
public class FoundPasswordServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("/user/foundpassword.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        String value = req.getParameter("value");
        //获取当前用户的SESSIONID
        String sessionid = req.getSession().getId();
        Map<String , Object> result = Maps.newHashMap();
        UserService userService = new UserService();
        try {
            userService.foundpassword(sessionid, type, value);
            result.put("state","success");
        }catch(ServiceException e){
            result.put("state","error");
            result.put("message",e.getMessage());
        }
        renderJSON(result,resp);
    }
}
