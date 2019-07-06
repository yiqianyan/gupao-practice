package v1;

import v1.mapper.Blog;

import java.sql.*;

public class GpExecutor {
    //GpExecutor用来具体jdbc操作
    //传入sql，和where条件。此处只处理id筛选，所以只一个参数，并且Object
    public <T> T query(String sql, Object paramater) {
        Connection conn = null;
        Statement stmt = null;
        Blog blog = new Blog();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(sql, paramater));
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                Integer authorId = rs.getInt("author_id");
                blog.setAuthorId(authorId);
                blog.setId(id);
                blog.setName(name);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return (T)blog;
    }
}
