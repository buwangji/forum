package com.laowang.service;

import com.laowang.dao.NodeDao;
import com.laowang.dao.TopicDao;
import com.laowang.dao.UserDao;
import com.laowang.entity.Node;
import com.laowang.entity.Topic;
import com.laowang.entity.User;
import com.laowang.exception.ServiceException;
import com.laowang.util.StringUtils;

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
    //保存新上传的帖子
    public Topic addNewTopic(String title, String content, Integer nodeid, Integer userid) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setNodeid(nodeid);
        topic.setUserid(userid);
        Integer topicId = topicDao.save(topic);
        topic.setId(topicId);
        return topic;
    }

    /**
     * 根据id寻找对应的帖子
     * @param topicid
     * @return
     */
    public Topic findTopicById(String topicid) {
        if (StringUtils.isNumeric(topicid)){
            Topic topic = topicDao.findTopicById(topicid);
            if(topic != null){
                User user = userDao.findByid(topic.getUserid());
                Node node = nodeDao.findById(topic.getNodeid());
                topic.setUser(user);
                topic.setNode(node);
                return topic;
            }else{
                throw new ServiceException("该贴不存在或已被删除");
            }
        }else{
            throw new ServiceException("参数错误");
        }
    }
}
