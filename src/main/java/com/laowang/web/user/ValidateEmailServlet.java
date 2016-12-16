package com.laowang.web.user;

import com.laowang.entity.User;
import com.laowang.service.UserService;
import com.laowang.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/16.
 */
@WebServlet("/validate/email")
public class ValidateEmailServlet extends BaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");

        UserService userService = new UserService();
        User user = userService.findByEmail(email);

        if(user == null) {
            renderText("true",resp);
        } else {
            renderText("false",resp);
        }

    }


}
