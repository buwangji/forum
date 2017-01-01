package com.laowang.web.ad;

import com.laowang.dto.JsonResult;
import com.laowang.entity.User;
import com.laowang.exception.ServiceException;
import com.laowang.service.UserService;
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
@WebServlet("/ad/user")
public class AdminUserServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p = req.getParameter("p");
        Integer pageNo = StringUtils.isNumeric(p)?Integer.valueOf(p):1;
        UserService userService = new UserService();
        Page<User> page = userService.findUserList(pageNo);
        req.setAttribute("page",page);
        forward("ad/user.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userState = Integer.valueOf(req.getParameter("userState"));
        String userid = req.getParameter("userid");
        userState = userState == 1?2:1;
        JsonResult result = new JsonResult();
        UserService userService = new UserService();
        try {
            userService.updateUserState(userid,userState);
            result.setState("success");
        }catch (ServiceException e){
            result.setState("error");
            result.setMessage(e.getMessage());
        }
        renderJSON(result,resp);
    }
}
