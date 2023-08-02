import java.sql.*;

public class Database_Connector {
    Connection con = null;
    Statement statm = null;

    public Database_Connector(String murl, String muser, String mpasswd) {
        // 驱动管家
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // 获取链接对象
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(murl, muser, mpasswd);
            this.con = connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 获取执行者
        Statement statement = null;
        try {
            statement = this.con.createStatement();
            this.statm = statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 判断链接对象
        if (connection == null) {
            System.out.println("Database connect fail");
        } else {
            System.out.println("Database connect success");
        }
    }
    public void Op_disconn() {
        try {
            con.close();
            statm.close();
            System.out.println("FMBank disconnect success");
            this.con = null;
            this.statm = null;
        } catch (SQLException e) {
            System.out.println("FMBank disconnect fail");
            throw new RuntimeException(e);
        }
    }
}
