package com.laowang.dao;

import com.laowang.entity.Reply;
import com.laowang.util.DbHelp;

/**
 * Created by Administrator on 2016/12/22.
 */
public class ReplyDao {


    public void addReply(Reply reply) {
        String sql = "insert into t_reply (content,userid,topicid) values (?,?,?)";
        DbHelp.update(sql,reply.getContent(),reply.getUserid(),reply.getTopicid());

    }
}
