package com.project.database;

import com.project.board.Board;

import java.io.StringReader;
import java.sql.*;
import java.util.LinkedHashMap;

public class Mysql {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/board";
        String user = "root";
        String password = "26905031";
        Connection conn = null; //접속
        Statement stmt = null; //쿼리문 실행
        ResultSet rs = null; // select문 결과

        System.out.println("User table 접속 : ");

        try { //예외처리 필수
            Class.forName("com.mysql.cj.jdbc.Driver"); //드라이버 로딩
            conn = DriverManager.getConnection(url, user, password); //접속 (정보가 정확하면 넘어옴)
            stmt = conn.createStatement(); //실행 객체 생성


            if (conn != null){System.out.println("성공");}
            else{System.out.println("실패");}


        } catch (Exception e) { //예외처리
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (stmt != null) stmt.close();
            }catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            }catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}