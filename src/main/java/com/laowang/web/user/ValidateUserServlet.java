package com.laowang.web.user;

import com.laowang.service.UserService;
import com.laowang.util.StringUtils;
import com.laowang.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/16.
 */
@WebServlet("/validate/user")
public class ValidateUserServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        username = StringUtils.IsoToUTF8(username);
        UserService userService = new UserService();
        Boolean result = userService.validateusername(username);
        if(result) {
            renderText("true",resp);
        } else {
            renderText("false",resp);
        }

    }


}
