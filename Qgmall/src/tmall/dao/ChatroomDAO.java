package tmall.dao;

import tmall.bean.Chatroom;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yc
 * @date 2023/4/26 1:28
 */
public class ChatroomDAO {

    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "select count(*) from Chatroom";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }


    public int getTotal(int uid) {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "select count(*) from Chatroom where uid = " + uid;

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public void add(Chatroom bean) {

        String sql = "insert into Chatroom values(null,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, bean.getUser1().getId());
            ps.setInt(2, bean.getUser2().getId());
            ps.setString(3, bean.getMessage());
            ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    public List<Chatroom> get(int id1,int id2) {
        List<Chatroom> beans = new ArrayList<Chatroom>();

        String sql = "select * from Chatroom where rid = ? and uid = ?";

        try (Connection c = DBUtil.getConnection(); PreparedStatement s = c.prepareStatement(sql);) {
            s.setInt(1,id1);
            s.setInt(2,id2);
            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                Chatroom bean = new Chatroom();
                int id = rs.getInt(1);
                int rid = rs.getInt("rid");
                int uid = rs.getInt("uid");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

                String message = rs.getString("message");

                User user1 = new UserDAO().get(rid);
                User user2 = new UserDAO().get(uid);

                bean.setMessage(message);
                bean.setCreateDate(createDate);
                bean.setUser1(user1);
                bean.setUser2(user2);
                bean.setId(id);
                beans.add(bean);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }

    public List<Chatroom> list(int rid) {
        return list(rid, 0, Short.MAX_VALUE);
    }

    public int getCount(int pid) {
        String sql = "select count(*) from Chatroom where pid = ? ";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return 0;
    }
    public List<Chatroom> list(int rid, int start, int count) {
        List<Chatroom> beans = new ArrayList<Chatroom>();

        String sql = "select * from Chatroom where rid = ? order by id desc limit ?,? ";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, rid);
            ps.setInt(2, start);
            ps.setInt(3, count);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Chatroom bean = new Chatroom();
                int id = rs.getInt(1);

                int uid = rs.getInt("uid");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

                String message = rs.getString("message");

                User user1 = new UserDAO().get(rid);
                User user2 = new UserDAO().get(uid);

                bean.setMessage(message);
                bean.setCreateDate(createDate);
                bean.setId(id);
                bean.setUser1(user1);
                bean.setUser2(user2);
                beans.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }
    public boolean isExist(String message, int rid) {

        String sql = "select * from Chatroom where message = ? and pid = ?";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, message);
            ps.setInt(2, rid);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }


}
