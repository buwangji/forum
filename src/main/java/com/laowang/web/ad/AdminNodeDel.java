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
 * Created by Administrator on 2016/12/29.
 */
@WebServlet("/ad/nodeDel")
public class AdminNodeDel extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        NodeService nodeService = new NodeService();
        JsonResult result = new JsonResult();
        try {
            nodeService.delNodeById(id);
            result.setState("success");
        }catch (ServiceException e){
            result.setState("error");
            result.setMessage(e.getMessage());
        }
        renderJSON(result,resp);
    }
}
