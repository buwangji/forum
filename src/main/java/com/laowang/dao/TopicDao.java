package com.laowang.dao;

import com.laowang.entity.Topic;
import com.laowang.entity.User;
import com.laowang.util.Config;
import com.laowang.util.DbHelp;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/20.
 */
//保存新发的帖子
public class TopicDao {
    public Integer save(Topic topic) {
        String sql = "insert into t_topic (title,content,nodeid,userid,lastreplytime) values(?,?,?,?,?)";
        return DbHelp.insert(sql,topic.getTitle(),topic.getContent(),topic.getNodeid(),topic.getUserid(),topic.getLastreplytime());
    }

    public Topic findTopicById(String topicid) {
        String sql = "select * from t_topic where id = ?";
        return DbHelp.query(sql,new BeanHandler<>(Topic.class),topicid);
    }

    public void update(Topic topic) {
        String sql ="update t_topic set title = ? ,content = ? ,clicknum = ?,favnum = ?,thankyounum = ?,replynum = ?,lastreplytime = ?, nodeid = ?,userid = ? where id = ?";
        DbHelp.update(sql,topic.getTitle(),topic.getContent(),topic.getClicknum(),topic.getFavnum(),topic.getThankyounum(),topic.getReplynum(),topic.getLastreplytime(),topic.getNodeid(),topic.getUserid(),topic.getId());
    }

    public int count() {
        String sql = "select count(*) from t_topic ";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }
    public int count(String nodeid) {
        String sql = "select count(*) from t_topic where nodeid = ?";
        return  DbHelp.query(sql,new ScalarHandler<Long>(),nodeid).intValue();

    }
    public List<Topic> findAllByPage(int start, int pageSize) {
        String sql = "select tu.username,tu.avatar,tt.* from t_topic tt,t_user tu where tt.userid = tu.id order by tt.lastreplytime desc limit ?,? ";
        return DbHelp.query(sql,abs,start,pageSize);
    }
    public List<Topic> findAllByPage(String nodeid, int start, int pageSize) {
        String sql = "select tu.username,tu.avatar,tt.* from t_topic tt,t_user tu where tt.userid = tu.id and nodeid = ? order by tt.lastreplytime desc limit ?,? ";
        return DbHelp.query(sql,abs,nodeid,start,pageSize);
    }
    private AbstractListHandler<Topic> abs = new AbstractListHandler<Topic>() {
        @Override
        protected Topic handleRow(ResultSet resultSet) throws SQLException {
            Topic topic = new BasicRowProcessor().toBean(resultSet,Topic.class);
            User user = new User();
            user.setUsername(resultSet.getString("username"));
            user.setAvatar(Config.get("qiniu.domain") + resultSet.getString("avatar"));
            topic.setUser(user);
            return topic;
        }
    };

}
