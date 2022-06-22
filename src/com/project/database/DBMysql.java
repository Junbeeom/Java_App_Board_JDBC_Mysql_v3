package com.project.database;

import java.io.StringReader;
import java.sql.*;

public class DBMysql {
    public DBMysql() {}

    private int no = 0;
    private String url = "jdbc:mysql://localhost:3306/board";
    private String user = "root";
    private String password = "26905031";
    private Connection conn = null;         //접속
    private PreparedStatement ps = null;    //쿼리문 실행
    private ResultSet rs = null;            //select문 결과

    public int getNo() {
        return no;
    }

    public void dbCreated(String title, String content, String name) {
        System.out.println("dbCreated 접속 : ");

        try { //예외처리 필수
            Class.forName("com.mysql.cj.jdbc.Driver"); //드라이버 로딩
            conn = DriverManager.getConnection(url, user, password); //접속 (정보가 정확하면 넘어옴)

            String sql = "INSERT INTO boardtable(title, content, name) VALUES(?, ?, ?)";
            ps = conn.prepareStatement(sql); //실행 객체 생성

            StringReader srTitle = new StringReader(title);
            StringReader srName = new StringReader(name);

            ps.setCharacterStream(1, srTitle, 20);
            ps.setString(2, content);
            ps.setCharacterStream(3, srName, 12);

            no = ps.executeUpdate();

            if (conn != null) {
                System.out.println("성공");
            } else {
                System.out.println("실패");
            }
        } catch (Exception e) { //예외처리
            e.printStackTrace();

        } finally {
            try {
                if (ps != null) ps.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

}
