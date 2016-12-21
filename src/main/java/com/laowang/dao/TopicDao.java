package com.laowang.dao;

import com.laowang.entity.Topic;
import com.laowang.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by Administrator on 2016/12/20.
 */
//保存新发的帖子
public class TopicDao {
    public Integer save(Topic topic) {
        String sql = "insert into t_topic (title,content,nodeid,userid) values(?,?,?,?)";
        return DbHelp.insert(sql,topic.getTitle(),topic.getContent(),topic.getNodeid(),topic.getUserid());
    }

    public Topic findTopicById(String topicid) {
        String sql = "select * from t_topic where id = ?";
        return DbHelp.query(sql,new BeanHandler<Topic>(Topic.class),topicid);
    }
}
