package com.laowang.service;

import com.laowang.dao.NodeDao;
import com.laowang.dao.TopicDao;
import com.laowang.dao.UserDao;
import com.laowang.entity.Node;
import com.laowang.entity.Topic;

import java.util.List;

/**
 * Created by Administrator on 2016/12/20.
 */
public class TopicService {
    TopicDao topicDao = new TopicDao();
    NodeDao nodeDao = new NodeDao();
    UserDao userDao = new UserDao();

    //获取nodelist
    public List<Node> findAllNode() {
        List<Node> nodeList = nodeDao.findAllNode();
        return  nodeList;

    }
}
