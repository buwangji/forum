package com.laowang.dao;

import com.laowang.entity.Fav;
import com.laowang.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by Administrator on 2016/12/24.
 */
public class FavDao {


    public Fav findFavById(Integer topicid, Integer userid) {
        String sql = "select * from t_fav where userid = ? and topicid = ?";
        return DbHelp.query(sql,new BeanHandler<Fav>(Fav.class),userid,topicid);
    }

    public void addFav(Fav fav) {
        String sql = "insert into t_fav (topicid,userid) values (?,?)";
        DbHelp.update(sql,fav.getTopicid(),fav.getUserid());
    }

    public void delFav(String topicid, Integer userid) {
        String sql = "delete from t_fav where userid = ? and topicid =?";
        DbHelp.update(sql,userid,Integer.valueOf(topicid));
    }

}
