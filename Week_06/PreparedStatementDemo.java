package com.example.springbootdemo;

import cn.hutool.core.date.DateUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author qrn
 * @Date 2021/2/27 4:54 下午
 * @Version 1.0
 * 使用 PreparedStatement 批量插入 100w数据
 * 第一次 用时17s
 * 第二次 用时16s
 * 第三次 用时17s
 */
public class PreparedStatementDemo {

    //static int count = 0;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        conn();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start)/1000 + "秒");
    }

    public static void conn(){
        //1.导入驱动jar包
        //2.注册驱动(mysql5之后的驱动jar包可以省略注册驱动的步骤)
        //Class.forName("com.mysql.jdbc.Driver");
        //3.获取数据库连接对象
        Connection conn = null;
        PreparedStatement pstmt = null;
        {
            try {
                //"&rewriteBatchedStatements=true",一次插入多条数据，只插入一次
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?" + "&rewriteBatchedStatements=true","root","qrn1796789910");
                //4.定义sql语句
                String sql = "insert into user values(default,?,?,?,?,?,?)";
                //5.获取执行sql的对象PreparedStatement
                pstmt = conn.prepareStatement(sql);
                //6.不断产生sql
                for (int i = 0; i < 1000000; i++) {
                    pstmt.setString(1,(int)(Math.random()*1000000)+"");
                    pstmt.setString(2,(int)(Math.random()*1000000)+"");
                    pstmt.setString(3,(int)(Math.random()*1000000)+"");
                    pstmt.setString(4,(int)(Math.random()*1000000)+"");
                    pstmt.setString(5,(int)(Math.random()*1000000)+"");
                    pstmt.setString(6, DateUtil.now());
                    pstmt.addBatch();
                }
                //7.往数据库插入一次数据
                pstmt.executeBatch();
                System.out.println("添加1000000条信息成功！");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                //8.释放资源
                //避免空指针异常
                if(pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if(conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


}
