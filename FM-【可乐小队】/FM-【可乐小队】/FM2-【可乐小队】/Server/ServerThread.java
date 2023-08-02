import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ServerThread extends Thread {

    Socket socket = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        Database_Connector dc = new Database_Connector("jdbc:mysql://114.116.239.147:3306/fm_bank?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai", "root", "Aacola2003");
        String msql = "";
        ResultSet rs = null;

        String userid = "";
        String username = "";
        String userid_sec = "";
        String userpasswd = "";
        String indentify = "";
        String birthday = null;
        String phone = "";
        String sex = "";

        double usermoney = 0;
        double usermoney_sec = 0;
        double chmoney = 0;

        DataOutputStream out = null;
        DataInputStream in = null;

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            A:
            while (true) {
                int judge = in.readInt();
                switch (judge) {
                    case 1:                              ///查询数据库中有无对应账号及密码是否正确，有且正确返回true，否则false,UI第444行
                        userid = in.readUTF();
                        userpasswd = in.readUTF();
                        msql = "SELECT * from bank where FIND_IN_SET(" + userid + ",用户ID)";
                        rs = null;
                        try {
                            rs = dc.statm.executeQuery(msql);
                            if (rs.next() == true) {
                                if (rs.getString("密码").equals(userpasswd)) {
                                    out.writeBoolean(true);
                                }
                            } else {
                                out.writeBoolean(false);
                            }
                            rs.close();
                        } catch (SQLException e) {
                            out.writeBoolean(false);
                        }
                        break;
                    case 2:                             ///查询数据库中有无对应账号，有返回true，否则false,UI第513行
                        userid = in.readUTF();
                        msql = "SELECT * from bank where FIND_IN_SET(" + userid + ",用户ID)";
                        rs = null;
                        try {
                            rs = dc.statm.executeQuery(msql);
                            if (rs.next() == true) {
                                double temp_2 =  rs.getDouble("账户余额");
                                msql = "DELETE FROM bank WHERE 用户ID = '" + userid + "'";
                                rs.close();
                                if (dc.statm.executeUpdate(msql) >= 1) {
                                    msql = "SELECT * from bank where FIND_IN_SET('root',用户ID)";
                                    rs = dc.statm.executeQuery(msql);
                                    rs.next();
                                    int temp = Integer.parseInt(rs.getString("身份ID")) - 1;
                                    double temp_1 = rs.getDouble("账户余额") - temp_2;
                                    rs.close();
                                    dc.statm.executeUpdate("UPDATE `fm_bank`.`bank` SET `身份ID` = '" + temp + "' WHERE `用户ID` = 'root'");
                                    dc.statm.executeUpdate("UPDATE `fm_bank`.`bank` SET `账户余额` = '" + temp_1 + "' WHERE `用户ID` = 'root'");
                                    // System.out.println("Operate Delete success");
                                    out.writeBoolean(true);
                                }
                            } else {
                                rs.close();
                                out.writeBoolean(false);
                            }
                        } catch (SQLException e) {
                            out.writeBoolean(false);
                        }
                        break;
                    case 3:                             ///检查账户是否存在，是否钱足够，符合要求的话扣钱返回true，否则返回false，UI第537行
                        synchronized (this) {
                            userid = in.readUTF();
                            chmoney = in.readDouble();

                            msql = "SELECT * from bank where FIND_IN_SET(" + userid + ",用户ID)";
                            rs = null;
                            try {
                                rs = dc.statm.executeQuery(msql);
                                if (rs.next() == true) {
                                    usermoney = rs.getDouble("账户余额");
                                    rs.close();
                                    if (usermoney >= chmoney) {
                                        msql = "UPDATE `fm_bank`.`bank` SET `账户余额` = " + (usermoney - (chmoney)) + " WHERE `用户ID` = '" + userid + "'";
                                        dc.statm.executeUpdate(msql);

                                        msql = "SELECT * from bank where FIND_IN_SET('root',用户ID)";
                                        rs = dc.statm.executeQuery(msql);
                                        rs.next();
                                        double temp = rs.getDouble("账户余额") - (chmoney);
                                        rs.close();
                                        dc.statm.executeUpdate("UPDATE `fm_bank`.`bank` SET `账户余额` = '" + temp + "' WHERE `用户ID` = 'root'");

                                        out.writeBoolean(true);
                                    } else {
                                        out.writeBoolean(false);
                                    }
                                } else {
                                    rs.close();
                                    out.writeBoolean(false);
                                }
                            } catch (SQLException e) {
                                out.writeBoolean(true);
                            }
                        }
                        break;
                    case 4:                             ///检查账户是否存在，不存在返回false，否则存进去钱返回true，UI第554行
                        synchronized (this) {
                            userid = in.readUTF();
                            chmoney = in.readDouble();

                            msql = "SELECT * from bank where FIND_IN_SET(" + userid + ",用户ID)";
                            rs = null;
                            try {
                                rs = dc.statm.executeQuery(msql);
                                if (rs.next() == true) {
                                    usermoney = rs.getDouble("账户余额");
                                    rs.close();
                                    msql = "UPDATE `fm_bank`.`bank` SET `账户余额` = " + (usermoney + chmoney) + " WHERE `用户ID` = '" + userid + "'";
                                    dc.statm.executeUpdate(msql);

                                    msql = "SELECT * from bank where FIND_IN_SET('root',用户ID)";
                                    rs = dc.statm.executeQuery(msql);
                                    rs.next();
                                    double temp = rs.getDouble("账户余额");
                                    rs.close();
                                    dc.statm.executeUpdate("UPDATE `fm_bank`.`bank` SET `账户余额` = '" + (temp + (chmoney)) + "' WHERE `用户ID` = 'root'");

                                    out.writeBoolean(true);
                                } else {
                                    rs.close();
                                    out.writeBoolean(false);
                                }
                            } catch (SQLException e) {
                                out.writeBoolean(false);
                            }
                        }
                        break;
                    case 5:                             ///查看是否存在两个账户，第一个账户钱够不够，UI第578行
                        synchronized (this) {
                            userid = in.readUTF();
                            userid_sec = in.readUTF();
                            chmoney = in.readDouble();
                            boolean flag = true;

                            msql = "SELECT * from bank where FIND_IN_SET(" + userid + ",用户ID)";
                            rs = null;
                            try {
                                rs = dc.statm.executeQuery(msql);
                                if (rs.next()) {
                                    usermoney = rs.getDouble("账户余额");
                                    if (usermoney < chmoney) {
                                        flag = false;
                                    }
                                } else {
                                    flag = false;
                                }
                            } catch (SQLException e) {
                                out.writeBoolean(false);
                            }

                            msql = "SELECT * from bank where FIND_IN_SET(" + userid_sec + ",用户ID)";
                            rs = null;
                            try {
                                rs = dc.statm.executeQuery(msql);
                                if (rs.next() != true) {
                                    flag = false;
                                } else {
                                    usermoney_sec = rs.getDouble("账户余额");
                                }
                                rs.close();
                            } catch (SQLException e) {
                                out.writeBoolean(false);
                            }

                            if (flag) {
                                msql = "UPDATE `fm_bank`.`bank` SET `账户余额` = " + (usermoney - (chmoney)) + " WHERE `用户ID` = '" + userid + "'";
                                try {
                                    dc.statm.executeUpdate(msql);
                                } catch (SQLException e) {
                                    out.writeBoolean(false);
                                }
                                msql = "UPDATE `fm_bank`.`bank` SET `账户余额` = " + (usermoney_sec + chmoney) + " WHERE `用户ID` = '" + userid_sec + "'";
                                try {
                                    dc.statm.executeUpdate(msql);
                                } catch (SQLException e) {
                                    out.writeBoolean(false);
                                }
                                out.writeBoolean(true);
                            } else {
                                out.writeBoolean(false);
                            }
                        }
                        break;
                    case 6:                             ///更新信息，我们还在改，待定，功能就是更新信息UI第596行
                        userid = in.readUTF();
                        userpasswd = in.readUTF();
                        birthday = in.readUTF();
                        phone = in.readUTF();
                        sex = in.readUTF();
                        username = in.readUTF();

                        msql = "UPDATE `fm_bank`.`bank` SET `用户名` = '" + username + "',`密码` = '" + userpasswd + "',`手机号` = '" + phone + "',`性别` = '" + sex + "',`出生日期` = '" + birthday + "' WHERE `用户ID` = '" + userid + "'";
                        try {
                            dc.statm.executeUpdate(msql);
                            out.writeBoolean(true);
                        } catch (SQLException e) {
                            out.writeBoolean(false);
                        }

                        break;
                    case 7:                             //////新建用户，UI第611行
                        username = in.readUTF();
                        birthday = in.readUTF();
                        phone = in.readUTF();
                        userpasswd = in.readUTF();
                        sex = in.readUTF();
                        indentify = in.readUTF();

                        userid = "";
                        int int_userid = 0;

                        msql = "SELECT * from bank where FIND_IN_SET('root',用户ID)";
                        try {
                            rs = dc.statm.executeQuery(msql);
                            rs.next();
                            userid = rs.getString("用户名");
                            int_userid = Integer.parseInt(userid) + 1;
                            userid = Integer.toString(int_userid);
                            rs.close();
                            rs = null;

                            msql = "INSERT INTO `fm_bank`.`bank` (`用户ID`,`用户名`,`密码`,`身份ID`,`手机号`,`性别`,`出生日期`) VALUES('" + userid + "','" + username + "','" + userpasswd + "','" + indentify + "','" + phone + "','" + sex + "','" + birthday + "' )";
                            dc.statm.executeUpdate(msql);

                            msql = "UPDATE `fm_bank`.`bank` SET `用户名` = '" + userid + "' WHERE `用户ID` = 'root'";
                            dc.statm.executeUpdate(msql);

                            msql = "SELECT * from bank where FIND_IN_SET('root',用户ID)";
                            rs = dc.statm.executeQuery(msql);
                            rs.next();
                            int temp = Integer.parseInt(rs.getString("身份ID")) + 1;
                            int temp_1 = Integer.parseInt(rs.getString("手机号")) + 1;
                            double temp_2 = rs.getDouble("账户余额") + 1000.0;
                            rs.close();
                            dc.statm.executeUpdate("UPDATE `fm_bank`.`bank` SET `身份ID` = '" + temp + "' WHERE `用户ID` = 'root'");
                            dc.statm.executeUpdate("UPDATE `fm_bank`.`bank` SET `手机号` = '" + temp_1 + "' WHERE `用户ID` = 'root'");
                            dc.statm.executeUpdate("UPDATE `fm_bank`.`bank` SET `账户余额` = '" + temp_2 + "' WHERE `用户ID` = 'root'");

                            out.writeUTF(userid);

                        } catch (SQLException e) {
                            out.writeUTF("wrong");
                        }

                        break;
                    case 8:
                        userid = in.readUTF();
                        msql = "SELECT * from bank where FIND_IN_SET(" + userid + ",用户ID)";
                        rs = null;
                        try {
                            rs = dc.statm.executeQuery(msql);
                            if (rs.next() == true) {
                                out.writeDouble(rs.getDouble("账户余额"));
                            } else {
                                out.writeDouble(-100);
                            }
                            rs.close();
                        } catch (SQLException e) {
                            out.writeDouble(-1000);
                        }
                        break;
                    case 9:
                        msql = "SELECT * from bank where FIND_IN_SET('root',用户ID)";
                        rs = null;

                        try {
                            rs = dc.statm.executeQuery(msql);

                            rs.next();

                            out.writeUTF(rs.getString("身份ID"));
                            out.writeUTF(rs.getString("手机号"));
                            out.writeDouble(rs.getDouble("账户余额"));

                            msql = "UPDATE `fm_bank`.`bank` SET `手机号` = '0' WHERE `用户ID` = 'root'";
                            rs.close();
                            dc.statm.executeUpdate(msql);
                        } catch (SQLException e) {

                            out.writeUTF("-1");
                            out.writeUTF("-1");
                            out.writeDouble(-1);
                        }
                        break;
                    case 10:
                        userid = in.readUTF();
                        msql = "SELECT * from bank where FIND_IN_SET(" + userid + ",用户ID)";
                        rs = null;
                        try {
                            rs = dc.statm.executeQuery(msql);
                            if (rs.next() == true) {
                                out.writeUTF(rs.getString("用户名"));
                                out.writeUTF(rs.getString("密码"));
                                out.writeUTF(rs.getString("身份ID"));
                                out.writeUTF(rs.getString("手机号"));
                                out.writeUTF(rs.getString("性别"));
                                out.writeUTF(rs.getString("出生日期"));
                                out.writeDouble(rs.getDouble("账户余额"));
                            } else {
                                out.writeUTF("username");
                                out.writeUTF("userpasswd");
                                out.writeUTF("indentify");
                                out.writeUTF("phone");
                                out.writeUTF("sex");
                                out.writeUTF("birthday");
                                out.writeDouble(-1);
                            }
                            rs.close();
                        } catch (SQLException e) {
                            out.writeUTF("username");
                            out.writeUTF("userpasswd");
                            out.writeUTF("indentify");
                            out.writeUTF("phone");
                            out.writeUTF("sex");
                            out.writeUTF("birthday");
                            out.writeDouble(-1);
                        }
                        break;
                    default:
                        break A;
                }
            }
            System.out.println("本线程结束");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
   
   