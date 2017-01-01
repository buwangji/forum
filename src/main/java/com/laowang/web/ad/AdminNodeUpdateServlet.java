package com.laowang.web.ad;

import com.laowang.dto.JsonResult;
import com.laowang.entity.Node;
import com.laowang.exception.ServiceException;
import com.laowang.service.NodeService;
import com.laowang.service.TopicService;
import com.laowang.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/29.
 */
@WebServlet("/ad/nodeUpdate")
public class AdminNodeUpdateServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("nodeid");
        TopicService topicService = new TopicService();
        try{
            Node node = topicService.findNodeById(nodeid);
            req.setAttribute("node",node);
            forward("ad/nodeupdate.jsp",req,resp);
        }catch (ServiceException e){
            resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("nodeid");
        String nodename = req.getParameter("nodename");
        NodeService nodeService = new NodeService();
        JsonResult result = new JsonResult();
        try {
            nodeService.updateNode(nodeid,nodename);
            result.setState("success");
        }catch (ServiceException e){
            result.setState("error");
            result.setMessage(e.getMessage());
        }
        renderJSON(result,resp);
    }
}
