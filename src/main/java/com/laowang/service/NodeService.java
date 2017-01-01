package com.laowang.service;

import com.laowang.dao.NodeDao;
import com.laowang.entity.Node;
import com.laowang.exception.ServiceException;
import com.laowang.util.StringUtils;

/**
 * Created by Administrator on 2016/12/29.
 */
public class NodeService {
    NodeDao nodeDao = new NodeDao();

    public void updateNode(String nodeid, String nodename) {
        if(StringUtils.isNumeric(nodeid)&&StringUtils.isNotEmpty(nodename)){
            Node node = nodeDao.findById(Integer.valueOf(nodeid));
            node.setNodename(nodename);
            nodeDao.update(node);
        }else {
            throw new ServiceException("传入参数异常");
        }

    }

    public void delNodeById(String id) {
        Node node = nodeDao.findById(Integer.valueOf(id));
        if(node.getTopicnum() > 0){
            throw new ServiceException("该节点下有主题,不能删除");
        }else{
            nodeDao.delNode(id);
        }
    }

    public String validateNode(String nodeid, String nodename) {
        Node node = nodeDao.findById(Integer.valueOf(nodeid));
        if(node.getNodename().equals(nodename)){
            return "true";
        }else{
            Node node1 = nodeDao.findNodeByName(nodename);
            if(node1 == null){
                return "true";
            }
        }
        return "false";
    }

    public void save(String nodename) {
        if(StringUtils.isNotEmpty(nodename)){
            Node node = nodeDao.findNodeByName(nodename);
            if(node == null){
                nodeDao.save(nodename);
            }else{
                throw new ServiceException("该节点已存在");
            }
        }else{
            throw new ServiceException("参数异常");
        }
    }
}
