package com.laowang.dao;

import com.laowang.entity.Node;
import com.laowang.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by Administrator on 2016/12/20.
 */
public class NodeDao {
    public List<Node> findAllNode() {
        String sql = "select * from t_node";
        return DbHelp.query(sql,new BeanListHandler<>(Node.class));
    }


    public Node findById(Integer nodeid) {
        String sql = "select * from t_node where id = ?";
        return DbHelp.query(sql,new BeanHandler<>(Node.class),nodeid);

    }

    public void update(Node node) {
        String sql = "update t_node set topicnum = ? , nodename = ? where id = ?  ";
        DbHelp.update(sql,node.getTopicnum(),node.getNodename(),node.getId());
    }
}
