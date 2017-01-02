package com.laowang.service;

import com.laowang.dao.*;
import com.laowang.entity.*;
import com.laowang.exception.ServiceException;
import com.laowang.util.Page;
import com.laowang.util.StringUtils;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2016/12/20.
 */
public class TopicService {
    TopicDao topicDao = new TopicDao();
    NodeDao nodeDao = new NodeDao();
    UserDao userDao = new UserDao();
    ReplyDao replyDao = new ReplyDao();
    FavDao favDao = new FavDao();
    NotifyDao notifyDao = new NotifyDao();

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
        //设置最后回复时间为当前时间
        topic.setLastreplytime(new Timestamp(new DateTime().getMillis()));
        Integer topicId = topicDao.save(topic);
        topic.setId(topicId);
        System.out.println(nodeid);
        //更新node表的topicnum
        Node node = nodeDao.findById(nodeid);
        if(node != null){
            node.setTopicnum(node.getTopicnum()+1);
            nodeDao.update(node);
        }else{
            throw new ServiceException("节点不存在");
        }
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
                //通过topic对象的userid、nodeid 获取user和node对象,并set到tipic对象中;
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

    /**
     * 添加新的回复
     * @param topicid
     * @param content
     * @param user
     */
    public void addNewTopicReply(String topicid, String content, User user) {
        //新增回复到t_reply表
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setUserid(user.getId());
        reply.setTopicid(Integer.valueOf(topicid));
        replyDao.addReply(reply);

        //更新t_topic表中的replynum和lastreplytime
        Topic topic = topicDao.findTopicById(topicid);
        if(topic != null){
            topic.setReplynum(topic.getReplynum()+1);
            topic.setLastreplytime(new Timestamp(DateTime.now().getMillis()));
            topicDao.update(topic);
        }else{
            throw new ServiceException("回复的主题不存在或被删除");
        }

        //新增回复通知
        if(!user.getId().equals(topic.getUserid())){
            Notify notify = new Notify();
            notify.setState(Notify.NOTIFY_STATE_UNREAD);
            notify.setUserid(topic.getUserid());
            notify.setContent("\"您的主题 <a href=\"/topicDetail?topicid="+topic.getId()+"\">["+ topic.getTitle()+"] </a> 有了新的回复,请查看.");
            notifyDao.save(notify);
        }
    }

    public List<Reply> findReplyListByTopicid(String topicid) {
        return replyDao.findListByTopicId(topicid);
    }

    public Fav findFavById(String topicid, User user) {
        return favDao.findFavById(Integer.valueOf(topicid),user.getId());
    }

    public void addFavTopic(String topicid, User user) {
        Fav fav = new Fav();
        fav.setTopicid(Integer.valueOf(topicid));
        fav.setUserid(user.getId());
        favDao.addFav(fav);
        //topic表中favnum+1
        Topic topic = topicDao.findTopicById(topicid);
        topic.setFavnum(topic.getFavnum() + 1);
        topicDao.update(topic);

    }

    public void unFavTopic(String topicid, User user) {
        favDao.delFav(topicid,user.getId());
        //topic表中favnum-1
        Topic topic = topicDao.findTopicById(topicid);
        topic.setFavnum(topic.getFavnum() - 1);
        topicDao.update(topic);
    }
    public void updateTopic(Topic topic) {
        topicDao.update(topic);
    }

    public Page<Topic> findAllTopic(String nodeid, Integer pageNo) {
        int count = 0;
        List<Topic> topicList = null;
        if(StringUtils.isEmpty(nodeid)){
            count = topicDao.count();
            count = count ==0?1:count;
            Page<Topic> topicPage = new Page<>(count,pageNo);
            topicList = topicDao.findAllByPage(topicPage.getStart(),topicPage.getPageSize());
            topicPage.setItems(topicList);
            return  topicPage;
        }else{
            count = topicDao.count(nodeid);
            count = count ==0?1:count;
            Page<Topic> topicPage = new Page<>(count,pageNo);
            topicList = topicDao.findAllByPage(nodeid,topicPage.getStart(),topicPage.getPageSize());
            topicPage.setItems(topicList);
            return  topicPage;
        }
    }

    public void updateTopicById(String title, String content, String nodeid, String topicid) {
        Topic topic = topicDao.findTopicById(topicid);
        Integer oldNodeid = topic.getNodeid();
        if(topic.isEdit()){
            //更新topic
            topic.setTitle(title);
            topic.setContent(content);
            topic.setNodeid(Integer.valueOf(nodeid));
            topicDao.update(topic);
            if(oldNodeid != Integer.valueOf(nodeid)){
                //更新node表
                Node oldNode = nodeDao.findById(oldNodeid);
                oldNode.setTopicnum(oldNode.getTopicnum() - 1);
                nodeDao.update(oldNode);
                Node newNode = nodeDao.findById(Integer.valueOf(nodeid));
                newNode.setTopicnum(newNode.getTopicnum()+1);
                nodeDao.update(newNode);
            }
        }else{
            throw new ServiceException("该帖已经不可编辑");
        }

    }

    public Node findNodeById(String nodeid) {
        if(StringUtils.isNumeric(nodeid)){
            Node node = nodeDao.findById(Integer.valueOf(nodeid));
            if(node != null){
                return node;
            }else{
                throw new ServiceException("该节点不存在");
            }
        }else{
            throw new ServiceException("参数异常");
        }

    }

    public Page<TopicReplyCount> getTopicAndReplyNumByDayList(Integer pageNo) {
        int count = topicDao.countTopicByDay();
        Page<TopicReplyCount> page = new Page<>(count,pageNo);

        List<TopicReplyCount> countLit =  topicDao.getTopicAndReplyNumList(page.getStart(),page.getPageSize());
        page.setItems(countLit);
        return page;
    }
}
