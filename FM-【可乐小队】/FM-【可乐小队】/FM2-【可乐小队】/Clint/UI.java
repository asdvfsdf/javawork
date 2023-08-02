import java.awt.*;
import javax.swing.*;
import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class UI implements ActionListener, WindowListener {
    private int size1, size2;
    private String Title;

    private String Username, Password, Bank_ID;// 用户参数
    private double count;

    private JFrame current;
    private Worker worker = new Worker("localhost", 4331);

    UI(String title, int size1, int size2) { // 初始化
        worker.run();
        this.Title = title;
        this.size1 = size1;
        this.size2 = size2;
        Frame1();
    }

    private final int x = 400, y = 300;
    private JButton button1, button2;
    private JFrame frame1;

    private void Frame1() { // 初始登录界面
        frame1 = new JFrame("飞马银行");
        frame1.setBounds(x, y, size1, size2);
        frame1.setResizable(false);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setLayout(null);
        frame1.setBackground(Color.gray);
        frame1.addWindowListener(this);

        JLabel label = new JLabel("欢迎来到飞马银行！");
        button1 = new JButton("登录");
        button2 = new JButton("注册");

        label.setBounds(100, 2, 200, 30);
        button1.setBounds(100, 50, 100, 30);
        button2.setBounds(100, 100, 100, 30);

        button1.addActionListener(this);
        button2.addActionListener(this);

        frame1.add(label);
        frame1.add(button1);
        frame1.add(button2);

        current = frame1;
        current.setVisible(true);
    }

    private JPanel cards1;
    private CardLayout cards2;
    private JFrame frame2;
    private JButton button3, button4;
    private JTextArea text1; // 用户名框
    private JPasswordField password1; // 密码框

    private void Frame2() { // 登陆的输入帐密界面
        frame2 = new JFrame("飞马银行");
        frame2.setBounds(x, y, size1, size2);
        frame2.setResizable(false);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setBackground(Color.gray);
        frame2.addWindowListener(this);

        JPanel panel1 = new JPanel();
        cards1 = new JPanel(new CardLayout(50, 10));
        cards2 = (CardLayout) (cards1.getLayout());

        JLabel label = new JLabel("请输入银行ID和密码");
        text1 = new JTextArea(1, 10);
        password1 = new JPasswordField(10);
        button3 = new JButton("  登录  ");
        button4 = new JButton("忘记密码");

        panel1.add(label);
        panel1.add(text1);
        panel1.add(password1);
        panel1.add(button3);
        panel1.add(button4);

        cards1.add(panel1, "ok");
        frame2.add(cards1);

        button3.addActionListener(this);
        button4.addActionListener(this);
    }

    private JFrame frame3;
    private JButton button5, button6, button7, button8, button9, button10;

    private void Frame3() {// 操作界面
        frame3 = new JFrame(Title + Username);
        frame3.setBounds(x, y, size1, size2);
        frame3.setResizable(false);
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.setBackground(Color.gray);
        frame3.addWindowListener(this);

        button5 = new JButton("查询余额");
        button6 = new JButton("取款");
        button7 = new JButton("存款");
        button8 = new JButton("转账");
        button9 = new JButton("修改信息");
        button10 = new JButton("销户");

        frame3.add(button5);
        frame3.add(button6);
        frame3.add(button7);
        frame3.add(button8);
        frame3.add(button9);
        frame3.add(button10);

        frame3.setLayout(new GridLayout(3, 2, 5, 5));

        button5.addActionListener(this);
        button6.addActionListener(this);
        button7.addActionListener(this);
        button8.addActionListener(this);
        button9.addActionListener(this);
        button10.addActionListener(this);
    }

    private JFrame frame4;
    private JButton button11, button12;

    private void Frame4() {// 查询余额
        frame4 = new JFrame(Title + Username);
        frame4.setBounds(x, y, size1, size2);
        frame4.setResizable(false);
        frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame4.setBackground(Color.gray);
        frame4.setLayout(null);
        frame4.addWindowListener(this);

        JLabel label = new JLabel("当前余额为：" + String.format("%.2f", count) + "");
        button11 = new JButton("刷新");
        button12 = new JButton("返回");

        label.setBounds(100, 20, 150, 20);
        button11.setBounds(20, 100, 100, 20);
        button12.setBounds(150, 100, 100, 20);

        frame4.add(label);
        frame4.add(button11);
        frame4.add(button12);

        button11.addActionListener(this);
        button12.addActionListener(this);
    }

    private JFrame frame5;
    private JTextArea text2;

    private JButton button13, button14;

    private void Frame5() {// 取款
        frame5 = new JFrame(Title + Username);
        frame5.setBounds(x, y, size1, size2);
        frame5.setResizable(false);
        frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame5.setBackground(Color.gray);
        frame5.setLayout(null);
        frame5.addWindowListener(this);

        JLabel label = new JLabel("输入取款数目");
        text2 = new JTextArea(1, 10);
        button13 = new JButton("取款");
        button14 = new JButton("返回");

        label.setBounds(100, 10, 100, 50);
        text2.setBounds(100, 60, 100, 20);
        button13.setBounds(20, 110, 100, 30);
        button14.setBounds(150, 110, 100, 30);

        frame5.add(label);
        frame5.add(text2);
        frame5.add(button13);
        frame5.add(button14);

        button13.addActionListener(this);
        button14.addActionListener(this);
    }

    private JFrame frame6;
    private JButton button15, button16;
    private JTextArea text3;

    private void Frame6() {// 存款
        frame6 = new JFrame(Title + Username);
        frame6.setBounds(x, y, size1, size2);
        frame6.setResizable(false);
        frame6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame6.setBackground(Color.gray);
        frame6.setLayout(null);
        frame6.addWindowListener(this);

        JLabel label = new JLabel("输入存款数目");
        text3 = new JTextArea(1, 10);
        button15 = new JButton("存款");
        button16 = new JButton("返回");

        button15.addActionListener(this);
        button16.addActionListener(this);

        label.setBounds(100, 10, 100, 50);
        text3.setBounds(100, 60, 100, 20);
        button15.setBounds(20, 110, 100, 30);
        button16.setBounds(150, 110, 100, 30);

        frame6.add(label);
        frame6.add(text3);
        frame6.add(button15);
        frame6.add(button16);
    }

    private JFrame frame7;
    private JTextArea text4, text5;
    private JButton button17, button18;

    private void Frame7() {// 转账
        frame7 = new JFrame(Title + Username);
        frame7.setBounds(x, y, size1, size2);
        frame7.setResizable(false);
        frame7.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame7.setBackground(Color.gray);
        frame7.addWindowListener(this);

        frame7.setLayout(null);

        JLabel label1 = new JLabel("输入转入银行ID和金额");
        JLabel label2 = new JLabel("银行ID");
        JLabel label3 = new JLabel("金额");
        text4 = new JTextArea(1, 10);
        text5 = new JTextArea(1, 10);
        button17 = new JButton("转账");
        button18 = new JButton("返回");

        button17.addActionListener(this);
        button18.addActionListener(this);

        label1.setBounds(80, 5, 150, 20);
        label2.setBounds(100, 25, 100, 20);
        text4.setBounds(100, 45, 100, 20);
        label3.setBounds(100, 65, 100, 20);
        text5.setBounds(100, 85, 100, 20);
        button17.setBounds(20, 120, 100, 30);
        button18.setBounds(150, 120, 100, 30);

        frame7.add(label1);
        frame7.add(label2);
        frame7.add(text4);
        frame7.add(label3);
        frame7.add(text5);
        frame7.add(button17);
        frame7.add(button18);
    }

    private JFrame frame8;
    private JPasswordField password2;
    private JTextArea text6, text7, text_add2, text_add4;
    private JButton button19, button20;

    private void Frame8() {// 修改信息
        frame8 = new JFrame(Title + Username);
        frame8.setBounds(x, y, size1, size2 + 150);
        frame8.setResizable(false);
        frame8.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame8.setBackground(Color.gray);
        frame8.addWindowListener(this);

        frame8.setLayout(null);

        JLabel label1 = new JLabel("新密码：（不少于4位）");
        JLabel label2 = new JLabel("新生日：（xxxx-xx-xx）");
        JLabel label3 = new JLabel("新手机号：（11位数字）");
        JLabel label4 = new JLabel("新性别：（1位汉字）");
        JLabel label5 = new JLabel("新用户名：（10个汉字长度）");

        password2 = new JPasswordField();
        text6 = new JTextArea(1, 15);
        text7 = new JTextArea(1, 15);
        text_add2 = new JTextArea(1, 1);
        text_add4 = new JTextArea(1, 15);
        button19 = new JButton("修改");
        button20 = new JButton("返回");

        button19.addActionListener(this);
        button20.addActionListener(this);

        label1.setBounds(80, 5, 150, 20);
        password2.setBounds(80, 25, 100, 20);
        label2.setBounds(80, 45, 150, 20);
        text6.setBounds(80, 65, 100, 20);
        label3.setBounds(80, 85, 150, 20);
        text7.setBounds(80, 105, 100, 20);
        label4.setBounds(80, 125, 150, 20);
        text_add2.setBounds(80, 145, 100, 20);
        label5.setBounds(80, 165, 180, 20);
        text_add4.setBounds(80, 185, 100, 20);
        button19.setBounds(20, 210, 100, 20);
        button20.setBounds(150, 210, 100, 20);

        frame8.add(label1);
        frame8.add(label2);
        frame8.add(label3);
        frame8.add(password2);
        frame8.add(text6);
        frame8.add(text7);
        frame8.add(label4);
        frame8.add(text_add2);
        frame8.add(text_add4);
        frame8.add(label5);
        frame8.add(button19);
        frame8.add(button20);
    }

    private JFrame frame9;
    private JButton button21, button22;
    private JTextArea text8, text9, text10, text_add1, text_add3;
    private JPasswordField password3;

    private void Frame9() {// 注册
        frame9 = new JFrame(Title);
        frame9.setBounds(x, y, size1, size2 + 150);
        frame9.setResizable(false);
        frame9.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame9.setBackground(Color.gray);
        frame9.addWindowListener(this);

        frame9.setLayout(null);

        JLabel label1 = new JLabel("输入用户名（长度为10位字符）");
        JLabel label2 = new JLabel("输入生日（XXXX-XX-XX）");
        JLabel label3 = new JLabel("输入手机号（11位数字）");
        JLabel label4 = new JLabel("输入性别（1位汉字）");
        JLabel label5 = new JLabel("输入密码（不少于4位）");
        JLabel label6 = new JLabel("输入身份ID（12位数字）");
        text8 = new JTextArea(1, 15);
        text9 = new JTextArea(1, 15);
        text10 = new JTextArea(1, 15);
        password3 = new JPasswordField();
        text_add1 = new JTextArea(1, 1);
        text_add3 = new JTextArea(1, 15);
        button21 = new JButton("注册");
        button22 = new JButton("返回");

        label1.setBounds(80, 5, 200, 20);
        text8.setBounds(80, 25, 100, 20);
        label2.setBounds(80, 45, 200, 20);
        text9.setBounds(80, 65, 100, 20);
        label3.setBounds(80, 85, 200, 20);
        text10.setBounds(80, 105, 100, 20);
        label4.setBounds(80, 125, 200, 20);
        text_add1.setBounds(80, 145, 100, 20);
        label5.setBounds(80, 165, 200, 20);
        password3.setBounds(80, 185, 100, 20);
        label6.setBounds(80, 205, 150, 20);
        text_add3.setBounds(80, 225, 100, 20);
        button21.setBounds(20, 255, 100, 20);
        button22.setBounds(150, 255, 100, 20);

        button21.addActionListener(this);
        button22.addActionListener(this);

        frame9.add(label1);
        frame9.add(label2);
        frame9.add(label3);
        frame9.add(label4);
        frame9.add(text8);
        frame9.add(text9);
        frame9.add(text10);
        frame9.add(password3);
        frame9.add(button21);
        frame9.add(button22);
        frame9.add(label5);
        frame9.add(text_add1);
        frame9.add(label6);
        frame9.add(text_add3);
    }

    private JFrame frame10;
    private JButton button23, button24, button25;

    private void Frame10() {// 管理员界面
        frame10 = new JFrame(Title + "管理员" + Username);
        frame10.setBounds(x, y, size1, size2 + 100);
        frame10.setResizable(false);
        frame10.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame10.setLayout(null);
        frame10.setBackground(Color.gray);
        frame10.addWindowListener(this);

        JLabel label = new JLabel("管理员操作界面");

        button23 = new JButton("批量开户");
        button24 = new JButton("年终报告");
        button25 = new JButton("查询信息并导出");

        label.setBounds(100, 2, 200, 30);
        button23.setBounds(100, 50, 100, 30);
        button24.setBounds(100, 100, 100, 30);
        button25.setBounds(100, 150, 100, 30);

        button23.addActionListener(this);
        button24.addActionListener(this);
        button25.addActionListener(this);

        frame10.add(label);
        frame10.add(button23);
        frame10.add(button24);
        frame10.add(button25);
    }

    private JButton button26, button27;
    private JFrame frame11;
    private JTextArea text11;

    private void Frame11() {// 导出信息界面
        frame11 = new JFrame(Title + "管理员" + Username);
        frame11.setBounds(x, y, size1, size2);
        frame11.setResizable(false);
        frame11.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame11.setBackground(Color.gray);
        frame11.setLayout(null);
        frame11.addWindowListener(this);

        JLabel label1 = new JLabel("请输入你想查找的银行ID：");
        text11 = new JTextArea(1, 10);
        button26 = new JButton("导出");
        button27 = new JButton("返回");

        label1.setBounds(80, 5, 200, 20);
        text11.setBounds(100, 25, 100, 20);
        button26.setBounds(20, 55, 100, 30);
        button27.setBounds(150, 55, 100, 30);

        button26.addActionListener(this);
        button27.addActionListener(this);

        frame11.add(label1);
        frame11.add(text11);
        frame11.add(button26);
        frame11.add(button27);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1)
            button_1();
        else if (e.getSource() == button2)
            button_2();
        else if (e.getSource() == button3)
            button_3();
        else if (e.getSource() == button4)
            button_4();
        else if (e.getSource() == button5)
            button_5();
        else if (e.getSource() == button6)
            button_6();
        else if (e.getSource() == button7)
            button_7();
        else if (e.getSource() == button8)
            button_8();
        else if (e.getSource() == button9)
            button_9();
        else if (e.getSource() == button10)
            button_10();
        else if (e.getSource() == button11)
            button_11();
        else if (e.getSource() == button12 || e.getSource() == button14 || e.getSource() == button16
                || e.getSource() == button18 || e.getSource() == button20)
            button_back1();
        else if (e.getSource() == button22)
            button_back2();
        else if (e.getSource() == button13)
            button_12();
        else if (e.getSource() == button15)
            button_13();
        else if (e.getSource() == button17)
            button_14();
        else if (e.getSource() == button19)
            button_15();
        else if (e.getSource() == button21)
            button_16();
        else if (e.getSource() == button23)
            button_17();
        else if (e.getSource() == button24)
            button_18();
        else if (e.getSource() == button25)
            button_19();
        else if (e.getSource() == button26)
            button_20();
        else if (e.getSource() == button27)
            button_21();
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        try{
            worker.thread_end();
        }catch(Exception e2){
            e2.printStackTrace();
        }
        System.exit(0);
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }


    private void button_1() {// 跳转登录界面
        Frame2();
        current.setVisible(false);
        frame2.setVisible(true);
        cards2.show(cards1, "ok");
        current = frame2;

    }

    private void button_2() {// 跳转注册界面
        Frame9();
        current.setVisible(false);
        frame9.setVisible(true);
        current = frame9;
    }

    private void button_3() {// 匹配账密
        Bank_ID = text1.getText();
        Password = new String(password1.getPassword());
        if (Bank_ID.length() == 0) {
            JOptionPane.showMessageDialog(frame2, "银行ID不能为空！", "错误",
                    JOptionPane.ERROR_MESSAGE);
        } else if (Password.length() == 0) {
            JOptionPane.showMessageDialog(frame2, "密码不能为空！", "错误",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            if (worker.check_login(Bank_ID, Password) == false) {
                JOptionPane.showMessageDialog(frame2, "密码或银行ID错误！", "错误",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                if (Bank_ID.equals("1000000000")) {
                    Frame10();
                    current.setVisible(false);
                    frame10.setVisible(true);
                    current = frame10;
                } else {
                    Frame3();
                    current.setVisible(false);
                    frame3.setVisible(true);
                    current = frame3;
                }
            }
        }
        
    }

    private void button_4() {// 忘记密码按钮
        JOptionPane.showMessageDialog(frame2, "请仔细回忆~", "忘记密码", 1);
    }

    private void button_5() {// 跳转查询
        Frame4();
        current.setVisible(false);
        frame4.setVisible(true);
        current = frame4;

        count = worker.check_money(Bank_ID);
    }

    private void button_6() {// 跳转取款
        Frame5();
        current.setVisible(false);
        frame5.setVisible(true);
        current = frame5;
    }

    private void button_7() {// 跳转存款
        Frame6();
        current.setVisible(false);
        frame6.setVisible(true);
        current = frame6;
    }

    private void button_8() {// 跳转转账
        Frame7();
        current.setVisible(false);
        frame7.setVisible(true);
        current = frame7;
    }

    private void button_9() {// 跳转修改信息
        Frame8();
        current.setVisible(false);
        frame8.setVisible(true);
        current = frame8;
    }

    private void button_10() {// 如果匹配成功则弹窗
        if (worker.logout(Bank_ID)) {
            JOptionPane.showMessageDialog(frame3, "注销成功！", "销户", JOptionPane.PLAIN_MESSAGE);
            button_1();
        }
    }

    private void button_11() {// 存款刷新
        button_5();
    }

    private void button_back1() {// 返回按钮
        current.setVisible(false);
        frame3.setVisible(true);
        current = frame3;
    }

    private void button_back2() {// 初始界面的返回按钮
        current.setVisible(false);
        frame1.setVisible(true);
        current = frame1;
    }

    private void button_12() {// 取款操作
        try {
            double withdraw = Double.parseDouble(text2.getText());
            if (withdraw != 0.0 && worker.withdraw_money(Bank_ID, withdraw)) {
                JOptionPane.showMessageDialog(frame5, "取款成功！", "取款", JOptionPane.PLAIN_MESSAGE);
            } else if (!worker.withdraw_money(Bank_ID, withdraw)) {
                JOptionPane.showMessageDialog(frame5, "余额不足！", "取款", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame5, "存入数额不能为0！", "错误", JOptionPane.ERROR_MESSAGE);
            }
            count = worker.check_money(Bank_ID);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame5, "请输入数字！", "格式错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void button_13() {// 存款操作
        try {
            double store = Double.parseDouble(text3.getText());
            if (store != 0.0) {
                worker.store_money(Bank_ID, store);
                JOptionPane.showMessageDialog(frame6, "存款成功！", "存款", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame6, "存入数额不能为0！", "错误", JOptionPane.ERROR_MESSAGE);
            }
            count = worker.check_money(Bank_ID);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame6, "请输入数字！", "格式错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void button_14() {// 转账操作
        try {
            String bank_id = text4.getText();
            double transfer = Double.parseDouble(text5.getText());
            if (transfer <= 0) {
                JOptionPane.showMessageDialog(frame7, "转账金额不得为0或负数！", "错误", 2);
            } else {
                if (bank_id.equals("root")) {
                    JOptionPane.showMessageDialog(frame7, "禁止向管理员转账！", "错误", 2);
                } else {
                    if (worker.transfer(Bank_ID, bank_id, transfer) == false)
                        JOptionPane.showMessageDialog(frame7, "转账失败！用户不存在或余额不足！", "转账", JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(frame7, "转账成功！", "转账", JOptionPane.PLAIN_MESSAGE);
                }
            }
            count = worker.check_money(Bank_ID);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame7, "请输入数字！", "格式错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void button_15() {// 修改信息操作
        try {
            String pas = new String(password2.getPassword());
            String birth = text6.getText();
            String phone = text7.getText();
            String sex = text_add2.getText();
            String username = text_add4.getText();

            if (pas.length() == 0 || birth.length() == 0 || phone.length() == 0 || sex.length() == 0
                    || username.length() == 0) {
                JOptionPane.showMessageDialog(frame8, "信息均为必填项！", "修改", JOptionPane.ERROR_MESSAGE);
            } else if (check_birth_1(birth) && check_birth_2(birth)) {
                if (pas.length() < 4) {
                    JOptionPane.showMessageDialog(frame7, "密码长度应不小于4位！", "格式错误", JOptionPane.ERROR_MESSAGE);
                } else if (birth.length() != 10) {
                    JOptionPane.showMessageDialog(frame7, "生日的输入格式应为xxxx-xx-xx！", "格式错误", JOptionPane.ERROR_MESSAGE);
                } else if (phone.length() != 11) {
                    JOptionPane.showMessageDialog(frame7, "电话号码的格式有误！", "格式错误", JOptionPane.ERROR_MESSAGE);
                } else if (!sex.equals("女") && !sex.equals("男")) {
                    JOptionPane.showMessageDialog(frame7, "性别栏应输入男或女！", "格式错误", JOptionPane.ERROR_MESSAGE);
                } else if (username.length() != 10) {
                    JOptionPane.showMessageDialog(frame7, "用户名格式有误！", "格式错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (check_birth_1(birth) && check_birth_2(birth)) {
                        System.out.println("fuck");
                        if (worker.update_info(Bank_ID, pas, birth, phone, sex, username)) {
                            JOptionPane.showMessageDialog(frame9, "修改成功！", "修改信息", JOptionPane.PLAIN_MESSAGE);
                            button_1();
                        } else {
                            JOptionPane.showMessageDialog(frame7, "修改信息失败！(错误1)", "修改信息", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame7, "修改信息失败！（错误2）", "修改信息", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void button_16() {// 注册提交
        try {
            String usr_name = text8.getText();
            String birth = text9.getText();
            String phone = text10.getText();
            String pass = new String(password3.getPassword());
            String sex = text_add1.getText();
            String person_id = text_add3.getText();

            if (usr_name.length() == 10 && birth.length() == 10 && phone.length() == 11 && pass.length() >= 4
                    && sex.length() == 1 && person_id.length() == 12) {
                if (check_birth_1(birth) && check_birth_2(birth)) {
                    Bank_ID = worker.check_bank_id(usr_name, birth, phone, pass, sex, person_id);
                    if (Bank_ID == "wrong") {
                        JOptionPane.showMessageDialog(frame9, "身份ID重复或格式错误!", "注册失败", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame9, "注册成功！请牢记你的银行ID：" + Bank_ID, "注册",
                                JOptionPane.PLAIN_MESSAGE);
                        button_1();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame9, "信息填写格式错误， 请按要求填写!", "注册失败", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame7, "注册失败！", "注册", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void button_17() {// 批量开户
    JFileChooser fc = new JFileChooser("C:\\");
    int val = fc.showOpenDialog(frame10);
    if (val == fc.APPROVE_OPTION) {
    System.out.println(fc.getSelectedFile().toString());
    int res = worker.bulk_signin(fc.getSelectedFile().toString());
    if (res == -1) {
    JOptionPane.showMessageDialog(frame10, "文件格式错误或不支持xlsx格式，请使用xls格式！",
    "文件格式错误",
    JOptionPane.ERROR_MESSAGE);
    } else {
    JOptionPane.showMessageDialog(frame10, "成功注册" + res + "个用户！", "成功",
    JOptionPane.PLAIN_MESSAGE);
    }
    }
    }

    private void button_18() {// 年终报告
    if (worker.createpdf(".\\REPORT.pdf")) {
    JOptionPane.showMessageDialog(frame10, "生成报告成功！文件在当前目录中。", "生成报告",
    JOptionPane.PLAIN_MESSAGE);
    } else {
    JOptionPane.showMessageDialog(frame10, "生成报告失败！", "生成报告",
    JOptionPane.ERROR_MESSAGE);
    }
    }

    private void button_19() {// 跳转导出xls界面
    Frame11();
    current.setVisible(false);
    frame11.setVisible(true);
    current = frame11;
    }

    private void button_20() {// 导出按钮
    String bank_id = text11.getText();
    if (worker.createxls(bank_id)) {
    JOptionPane.showMessageDialog(frame10, "生成查询文件成功！文件在当前目录中。", "生成查询文件",
    JOptionPane.PLAIN_MESSAGE);
    } else {
    JOptionPane.showMessageDialog(frame10, "生成文件失败！", "生成查询文件",
    JOptionPane.ERROR_MESSAGE);
    }
    }

    private void button_21() {// 返回管理员界面
        current.setVisible(false);
        frame10.setVisible(true);
        current = frame10;
    }

    private boolean check_birth_1(String birth) {// 检测是否成年
        // 当前时间
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DATE);
        m++;

        // 输入的生日
        int index1 = birth.indexOf("-");
        String str1 = birth.substring(0, index1);// 年
        int index2 = birth.indexOf("-", index1 + 1);
        String str2 = birth.substring(index1 + 1, index2);// 月
        String str3 = birth.substring(index2 + 1);// 日

        y -= 18;
        if (Integer.valueOf(str1) > y) {
            JOptionPane.showMessageDialog(frame9, "未满十八岁！", "注册失败", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (Integer.valueOf(str1) < y) {
            return true;
        } else {
            if (Integer.valueOf(str2) > m) {
                JOptionPane.showMessageDialog(frame9, "未满十八岁！", "注册失败", JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (Integer.valueOf(str2) < m) {
                return true;
            } else {
                if (Integer.valueOf(str3) > d) {
                    JOptionPane.showMessageDialog(frame9, "未满十八岁！", "注册失败", JOptionPane.ERROR_MESSAGE);
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    private boolean check_birth_2(String birth) {// 检测是否超过70岁
        // 当前时间
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DATE);
        m++;

        // 输入的生日
        int index1 = birth.indexOf("-");
        String str1 = birth.substring(0, index1);// 年
        int index2 = birth.indexOf("-", index1 + 1);
        String str2 = birth.substring(index1 + 1, index2);// 月
        String str3 = birth.substring(index2 + 1);// 日

        y -= 70;
        if (Integer.valueOf(str1) < y) {
            JOptionPane.showMessageDialog(frame9, "超过70岁老人无法申领！", "注册失败", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (Integer.valueOf(str1) > y) {
            return true;
        } else {
            if (Integer.valueOf(str2) < m) {
                JOptionPane.showMessageDialog(frame9, "超过70岁老人无法申领！", "注册失败", JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (Integer.valueOf(str2) > m) {
                return true;
            } else {
                if (Integer.valueOf(str3) < d) {
                    JOptionPane.showMessageDialog(frame9, "超过70岁老人无法申领！", "注册失败", JOptionPane.ERROR_MESSAGE);
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    public static void main(String[] args) {
        new UI("飞马银行", 300, 200);
    }
}