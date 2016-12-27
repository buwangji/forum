package com.laowang.web.user;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.laowang.dto.JsonResult;
import com.laowang.entity.Notify;
import com.laowang.entity.User;
import com.laowang.service.UserService;
import com.laowang.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */
@WebServlet("/notify")
public class NotifyServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user =getCurrentUser(req);
        List<Notify> notifyList = new UserService().findNotifyListBy(user);
        req.setAttribute("notifyList",notifyList);
        forward("user/notify.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getCurrentUser(req);
        List<Notify> notifyList = new UserService().findNotifyListBy(user);
        //根据guava 的Collections2.filter 过滤未读消息数据
        List<Notify> unreadList = Lists.newArrayList(Collections2.filter(notifyList, new Predicate<Notify>() {
            @Override
            public boolean apply(Notify notify) {
                return notify.getState() == 0;
            }
        }));
        JsonResult result = new JsonResult();
        result.setData(unreadList.size());
        result.setState("success");
        renderJSON(result,resp);


    }
}
