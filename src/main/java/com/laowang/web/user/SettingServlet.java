package com.laowang.web.user;

import com.laowang.dto.JsonResult;
import com.laowang.entity.User;
import com.laowang.exception.ServiceException;
import com.laowang.service.UserService;
import com.laowang.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/19.
 */
@WebServlet("/setting")
public class SettingServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("user/setting.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if("profile".equals(action)){
            updateProfile(req,resp);
        }else if ("password".equals(action)){
            updatePassword(req,resp);
        }
    }

    private void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        User user = getCurrentUser(req);
        UserService userService = new UserService();
        JsonResult jsonResult = new JsonResult();

        try {
            userService.updateEmail(user,email);

            jsonResult.setState("success");
            renderJSON(jsonResult,resp);
        } catch (ServiceException e) {
            jsonResult.setMessage(e.getMessage());
            renderJSON(jsonResult,resp);
        }

    }

    private void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String oldPassword = req.getParameter("oldpassword");
        String newPassword = req.getParameter("newpassword");

        User user = getCurrentUser(req);
        UserService userService = new UserService();
        JsonResult jsonResult = new JsonResult();

        userService.updatePaasword(user,oldPassword,newPassword);
        jsonResult.setState("success");
        try {
            renderJSON(jsonResult,resp);
        } catch (ServiceException e) {
            jsonResult.setMessage(e.getMessage());
            renderJSON(jsonResult,resp);
        }


    }


}
