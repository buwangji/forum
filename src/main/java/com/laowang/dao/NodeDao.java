package com.laowang.dao;

import com.laowang.entity.Node;
import com.laowang.util.DbHelp;
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
}
