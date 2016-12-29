package com.laowang.web.ad;

import com.laowang.dto.JsonResult;
import com.laowang.entity.Admin;
import com.laowang.exception.ServiceException;
import com.laowang.service.AdminService;
import com.laowang.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/28.
 */
@WebServlet("/ad/login")
public class AdminLoginServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("curr_admin");
        forward("ad/login.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String adminName = req.getParameter("adminName");
        String password = req.getParameter("password");
        System.out.println(adminName);
        String ip = req.getRemoteAddr();
        AdminService adminService = new AdminService();
        JsonResult result = new JsonResult();
        try {
            Admin admin = adminService.login(adminName, password, ip);
            req.getSession().setAttribute("curr_admin",admin);
            result.setState("success");
        }catch (ServiceException e){
            result.setMessage(e.getMessage());
        }
        renderJSON(result,resp);
    }
}
