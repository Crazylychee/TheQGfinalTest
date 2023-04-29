package tmall.dao;

import tmall.bean.Star;
import tmall.bean.Store;
import tmall.util.DBUtil;

import java.beans.Beans;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yc
 * @date 2023/4/27 23:54
 */
public class StarDAO {
    public void add(int id1,int id2){
        String sql = "insert into Star values(? ,? )";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id1);
            ps.setInt(2,id2);
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void delete(int id1,int id2){
        String sql = "delete from Star where id = ? and sid =?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id1);
            ps.setInt(2,id2);
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public Star get(int id1,int id2){
        Star bean = null;
        String sql = "select * from Star where id = ? and sid = ?";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,id1);
            ps.setInt(2,id2);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bean = new Star();
                int id = rs.getInt("id");
                bean.setId(id);
                int sid = rs.getInt("id");
                bean.setId(sid);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return bean;
    }

    public boolean isExist(int id1,int id2){
        Star star=get(id1,id2);
        return star!=null;
    }




}
