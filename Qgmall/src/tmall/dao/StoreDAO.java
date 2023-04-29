package tmall.dao;

import tmall.bean.Store;
import tmall.bean.User;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yc
 * @date 2023/4/26 1:29
 */
public class StoreDAO {
    //DAO
    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "select count(*) from Store";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public void add(User bean,String name,String description) {
        //这里的空是不插入数据
        String sql = "insert into Store values(? ,? ,? ,?)";
        //perpareStatement预编译防sql注入
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, bean.getId());
            //初始粉丝数设置为零
            ps.setInt(2, 0);
            ps.setString(3, description);
            ps.setString(4, name);
            ps.execute();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void update(User bean) {

        String sql = "update user set name= ? , password = ? , destination = ? , number = ? , email = ? where id = ? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setString(1, bean.getName());
            ps.setString(2, bean.getPassword());
            ps.setString(3,bean.getDestination());
            ps.setString(4,bean.getNumber());
            ps.setString(5,bean.getEmail());
            ps.setInt(6, bean.getId());
            ps.execute();
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void delete(int id) {

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "delete from Store where uid = " + id;

            s.execute(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public Store get(int id) {
        Store bean = null;

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "select * from Store where uid = " + id;

            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {
                bean = new Store();
                String name = rs.getString("name");
                bean.setName(name);
                String description = rs.getString("description");
                bean.setDescription(description);
                int fans = rs.getInt("fans");
                bean.setFans(fans);
                bean.setUid(id);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return bean;
    }

    public List<User> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<User> list(int start, int count) {
        List<User> beans = new ArrayList<User>();

        String sql = "select * from Store order by uid desc limit ?,? ";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, start);
            ps.setInt(2, count);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User bean = new User();
                int id = rs.getInt(1);

                String name = rs.getString("name");
                bean.setName(name);
                String password = rs.getString("password");
                bean.setPassword(password);
                String destination = rs.getString("destination");
                bean.setDestination(destination);
                String number = rs.getString("number");
                bean.setNumber(number);
                String email = rs.getString("email");
                bean.setEmail(email);
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }

    public boolean isExist(int id) {
        Store store = get(id);
        return store!=null;

    }
}
