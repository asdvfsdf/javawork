import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.*;
import java.net.Socket;



public class Worker {

    String username = null;
    int port = 0;

    Socket mySocket;
    DataInputStream in = null;
    DataOutputStream out = null;

    public Worker(String username,int port){
        this.username = username;
        this.port = port;
    }

    public void run(){
        try{
            mySocket =new Socket(username,port);
            in = new DataInputStream(mySocket.getInputStream());
            out = new DataOutputStream(mySocket.getOutputStream());
        }catch(IOException e){

        }
    }

    public boolean check_login(String Bank_ID,String Password){
        boolean judge = false;

        try{
            out.writeInt(1);
            out.writeUTF(Bank_ID);
            out.writeUTF(Password);
            judge = in.readBoolean();

        }catch(IOException e){
            e.printStackTrace();
        }
        return judge;
    }

    public boolean logout(String Bank_ID){
        boolean judge = false;

        try{
            out.writeInt(2);
            out.writeUTF(Bank_ID);
            judge = in.readBoolean();

        }catch(IOException e){
            e.printStackTrace();
        }
        return judge;
    }

    public boolean withdraw_money(String Bank_ID,Double withdraw){
        boolean judge = false;

        try{
            out.writeInt(3);
            out.writeUTF(Bank_ID);
            out.writeDouble(withdraw);
            judge = in.readBoolean();

        }catch(IOException e){
            e.printStackTrace();
        }

        return judge;
    }

    public boolean store_money(String Bank_ID,double store){
        boolean judge = false;

        try{
            out.writeInt(4);
            out.writeUTF(Bank_ID);
            out.writeDouble(store);
            judge = in.readBoolean();

        }catch(IOException e){
            e.printStackTrace();
        }
        return judge;
    }

    public boolean transfer(String Bank_ID,String bank_id,double transfer){
        boolean judge = false;

        try{
            out.writeInt(5);
            out.writeUTF(Bank_ID);
            out.writeUTF(bank_id);
            out.writeDouble(transfer);
            judge = in.readBoolean();

        }catch(IOException e){
            e.printStackTrace();
        }
        return judge;
    }

    public boolean update_info(String Bank_ID,String pas,String birth,String phone,String sex,String username){///更新信息，
        boolean judge = false;

        try{
            out.writeInt(6);
            out.writeUTF(Bank_ID);
            out.writeUTF(pas);
            out.writeUTF(birth);
            out.writeUTF(phone);
            out.writeUTF(sex);
            out.writeUTF(username);
            judge = in.readBoolean();
        }catch(IOException e){
            e.printStackTrace();
        }
        return judge;
    }

    public String check_bank_id(String usr_name,String birth,String phone,String pass,String sex,String person_id){
        String Bank_ID = "wrong";
        try{
            out.writeInt(7);
            out.writeUTF(usr_name);
            out.writeUTF(birth);
            out.writeUTF(phone);
            out.writeUTF(pass);
            out.writeUTF(sex);
            out.writeUTF(person_id);
            Bank_ID = in.readUTF();

        }catch(IOException e){
            e.printStackTrace();
        }
        return Bank_ID;
    }

    public double check_money(String Bank_ID){
        double money = 0;

        try{
            out.writeInt(8);
            out.writeUTF(Bank_ID);
            money = in.readDouble();

        }catch(IOException e){
            e.printStackTrace();
        }
        return money;

    }

    public boolean createpdf(String file_name){
        try {
            out.writeInt(9);
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file_name));
            String account = in.readUTF();                            ///目前账户总数，今年新开户数，总余额
            String newaccount = in.readUTF();
            double money = in.readDouble();
            document.open();
            document.add(new Paragraph("Fei Ma Bank annual report"));
            document.add(new Paragraph("The total number of the account is: "+account));
            document.add(new Paragraph("The number of the new account is: "+newaccount));
            document.add(new Paragraph("The total cash in the bank are: "+money));
            document.close();
            return true;
        } catch (DocumentException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public int bulk_signin(String file_name) {
        int cnt = 0;
        try {
            Workbook book = Workbook.getWorkbook(new File(file_name));
            Sheet sheet = book.getSheet(0);
            int cols = sheet.getColumns();  // 取到表格的列数
            int rows = sheet.getRows();     // 取到表格的行数
            for(int i = 0; i < rows; i++) {
                String usr_name,birth,phone,pass,sex,person_id;
                usr_name = birth = phone = pass = sex = person_id = null;
                for(int j = 0; j < cols; j++) {
                    switch(j) {
                        case 0: usr_name = sheet.getCell(j, i).getContents(); break;
                        case 1: birth = sheet.getCell(j, i).getContents(); break;
                        case 2: phone = sheet.getCell(j, i).getContents(); break;
                        case 3: pass = sheet.getCell(j, i).getContents(); break;
                        case 4: sex = sheet.getCell(j, i).getContents(); break;
                        case 5: person_id = sheet.getCell(j, i).getContents(); break;
                    }
                }
                check_bank_id(usr_name,birth,phone,pass,sex,person_id);
                cnt++;
            }
        } catch (IOException e) {
            return -1;
        } catch (BiffException e) {
            return -1;
        }
        return cnt;
    }

    public boolean createxls(String bank_id){
        boolean judge = false;

        try{
            out.writeInt(10);
            out.writeUTF(bank_id);

            String username =in.readUTF();
            String userpasswd = in.readUTF();
            String indentify = in.readUTF();
            String phone = in.readUTF();
            String sex = in.readUTF();
            String birthday = in.readUTF();
            double usermoney = in.readDouble();

            WritableWorkbook book = null;
            try{
                book = Workbook.createWorkbook(new File(".\\info.xls"));
                WritableSheet sheet = book.createSheet("第一页",0);
                jxl.write.Label label1 = new jxl.write.Label(0,0,"用户ID");
                jxl.write.Label label2 = new jxl.write.Label(1,0,"用户名");
                jxl.write.Label label3 = new jxl.write.Label(2,0,"密码");
                jxl.write.Label label4 = new jxl.write.Label(3,0,"身份ID");
                jxl.write.Label label5 = new jxl.write.Label(4,0,"手机号");
                jxl.write.Label label6 = new jxl.write.Label(5,0,"性别");
                jxl.write.Label label7 = new jxl.write.Label(6,0,"出生日期");
                jxl.write.Label label8 = new jxl.write.Label(7,0,"账户余额");

                sheet.addCell(label1);
                sheet.addCell(label2);
                sheet.addCell(label3);
                sheet.addCell(label4);
                sheet.addCell(label5);
                sheet.addCell(label6);
                sheet.addCell(label7);
                sheet.addCell(label8);

                sheet.addCell(new jxl.write.Label(0,1,bank_id));
                sheet.addCell(new jxl.write.Label(1,1,username));
                sheet.addCell(new jxl.write.Label(2,1,userpasswd));
                sheet.addCell(new jxl.write.Label(3,1,indentify));
                sheet.addCell(new jxl.write.Label(4,1,phone));
                sheet.addCell(new jxl.write.Label(5,1,sex));
                sheet.addCell(new jxl.write.Label(6,1,birthday));
                sheet.addCell(new jxl.write.Label(7,1,Double.toString(usermoney)));

                book.write();
                judge = true;
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                try {
                    // 关闭
                    book.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



        }catch(IOException e){
            e.printStackTrace();
        }
        return judge;

    }


    public void thread_end() {
        try {
            out.writeInt(11);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

