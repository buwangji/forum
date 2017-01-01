package com.laowang.web.ad;

import com.laowang.dto.JsonResult;
import com.laowang.exception.ServiceException;
import com.laowang.service.NodeService;
import com.laowang.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/1/1.
 */
@WebServlet("/ad/newnode")
public class NewServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("ad/newnode.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodename = req.getParameter("nodename");
        NodeService nodeService = new NodeService();
        JsonResult result = new JsonResult();
        try {
            nodeService.save(nodename);
            result.setState("success");
        }catch (ServiceException e){
            result.setState("error");
            result.setMessage(e.getMessage());
        }
        renderJSON(result,resp);
    }
}
