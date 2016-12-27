package com.laowang.web.user;

import com.laowang.service.UserService;
import com.laowang.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/27.
 */
@WebServlet("/notifyRead")
public class NotifyReadServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids = req.getParameter("ids");
        UserService service = new UserService();
        service.updateNotifyByids(ids);
        renderText("success",resp);
    }
}
