package com.laowang.web.ad;

import com.laowang.service.NodeService;
import com.laowang.util.StringUtils;
import com.laowang.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/29.
 */
@WebServlet("/ad/nodeValidate")
public class NodeValidateServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeid = req.getParameter("nodeid");
        String nodename = req.getParameter("nodename");
        nodename = StringUtils.IsoToUTF8(nodename);
        NodeService nodeService = new NodeService();
         String res = nodeService.validateNode(nodeid,nodename);
         renderText(res,resp);
    }
}
