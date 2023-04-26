package tmall.dao;

import tmall.bean.Category;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yc
 * @date 2023/4/24 9:51
 */
public class CategoryDAO {

    /**
     * 得到总数
     * @return
     */
    public int getTotal(){
        int total=0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()){
            String sql = "select count(*) from Category";
            ResultSet rs=s.executeQuery(sql);

//            判断 rs 是否指向下一个结果集
            while (rs.next()){
//                使用 getInt() 方法将 rs 指向的第一个字段 (第 2 个参数) 的值赋给变量 total。
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 添加
     * @param bean
     */
    public void add(Category bean){
        String sql = "insert into category value";
        try (Connection c= DBUtil.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            //设置查询条件
            ps.setString(1,bean.getName());
            //执行查询语句
            ps.execute();
            //获取主键，封装到结果集中，这里的主键就是id(重要)
            ResultSet rs= ps.getGeneratedKeys();
            //循环取id
            while (rs.next()){
                int id = rs.getInt(1);
                //把id赋值给对象
                bean.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新
     * @param bean
     */
    public void update(Category bean){
        String sql = "update category set name = ? where id = ?";
        //这里用的是preparedStatement所以用的是？占位符
        try (Connection c= DBUtil.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            //这两个方法就是为了把sql语句中的？占位符给替换
            //第一个是替换成bean.getName
            ps.setString(1,bean.getName());
            ps.setInt(2,bean.getId());
            //换完了就开始执行
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(int id) {

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "delete from Category where id = " + id;

            s.execute(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    /**
     * 查询
     * @param id
     * @return
     */
    public Category get(int id) {
        Category bean = null;

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "select * from Category where id = " + id;

            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {
                bean = new Category();
                String name = rs.getString(2);
                bean.setName(name);
                bean.setId(id);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 拿到所有分类，简易信息
     * @return
     */
    public List<Category> list() {
        return list(0, Short.MAX_VALUE);
    }

    /**
     * 分类从start到count详细信息
     * @param start
     * @param count
     * @return
     */
    public List<Category> list(int start, int count) {
        List<Category> beans = new ArrayList<Category>();

        String sql = "select * from Category order by id desc limit ?,? ";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, start);
            ps.setInt(2, count);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category bean = new Category();
                int id = rs.getInt(1);
                String name = rs.getString(2);
                bean.setId(id);
                bean.setName(name);
                beans.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }
}
