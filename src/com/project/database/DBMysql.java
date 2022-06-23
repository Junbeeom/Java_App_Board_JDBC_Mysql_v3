package com.project.database;

import com.project.board.Board;
import com.project.board.BoardService;

import java.io.StringReader;
import java.sql.*;
import java.util.LinkedHashMap;

public class DBMysql {
    public DBMysql() {}

    private int updateNo = 0;
    private int deleteNo = 0;
    private String url = "jdbc:mysql://localhost:3306/board";
    private String user = "root";
    private String password = "26905031";
    private Connection conn = null;         //접속
    private PreparedStatement ps = null;   //쿼리문 실행
    private ResultSet rs = null;          //select문 결과


    public int getUpdateNo() {
        return updateNo;
    }

    public int getDeleteNo() { return deleteNo; }

    //등록
    public void dbCreated(String title, String content, String name) {
        System.out.println("dbCreated실행, boardtable 접속 : ");

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

            updateNo = ps.executeUpdate();

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

    //조회 메소드
    public LinkedHashMap<Integer, Board> dblisted() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();

        try { //예외처리 필수
            Class.forName("com.mysql.cj.jdbc.Driver");                //드라이버 로딩
            conn = DriverManager.getConnection(url, user, password); //접속 (정보가 정확하면 넘어옴)
        } catch (Exception e) {                                     //예외처리
            e.printStackTrace();
        }

        String sql = "SELECT * FROM boardtable where deleted_ts IS NULL";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = ps.executeQuery()) {

                //테이블의 모든 건을 가져와야 한다.
                while (resultSet.next()) {

                    int id = resultSet.getInt("no");
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    String name = resultSet.getString("name");
                    String createdTs = resultSet.getString("created_ts");
                    String updatedTs = resultSet.getString("updated_ts");
                    String deletedTs = resultSet.getString("deleted_ts");

                    linkedHashMap.put(id, new Board(title, content, name, createdTs, updatedTs, deletedTs));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return linkedHashMap;
    }

    //삭제
    public void dbDeleted(int no) {
        BoardService boardService = new BoardService();

        try { //예외처리 필수
            Class.forName("com.mysql.cj.jdbc.Driver"); //드라이버 로딩
            conn = DriverManager.getConnection(url, user, password); //접속 (정보가 정확하면 넘어옴)

            String sql = "UPDATE boardtable SET deleted_ts = ? WHERE no = ?";

            ps = conn.prepareStatement(sql); //실행 객체 생성

            ps.setTimestamp(1, boardService.ts());
            ps.setInt(2, no);

            deleteNo = ps.executeUpdate();

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

    //수정
    public void dbmodified(int no, String modifiedValue, int modifiedIndex) {
        BoardService boardService = new BoardService();

        try { //예외처리 필수
            Class.forName("com.mysql.cj.jdbc.Driver"); //드라이버 로딩
            conn = DriverManager.getConnection(url, user, password); //접속 (정보가 정확하면 넘어옴)

            switch (modifiedIndex) {
                //이름
                case 1:
                    String sql = "UPDATE boardtable SET name = ?, updated_ts = ? WHERE no = ?";
                    ps = conn.prepareStatement(sql); //실행 객체 생성

                    StringReader srName = new StringReader(modifiedValue);

                    ps.setCharacterStream(1, srName);
                    ps.setTimestamp(2, boardService.ts());
                    ps.setInt(3, no);

                    deleteNo = ps.executeUpdate();
                    break;

                //제목
                case 2:
                    sql = "UPDATE boardtable SET title = ?, updated_ts = ? WHERE no = ?";
                    ps = conn.prepareStatement(sql); //실행 객체 생성

                    StringReader srTitle = new StringReader(modifiedValue);

                    ps.setCharacterStream(1, srTitle);
                    ps.setTimestamp(2, boardService.ts());
                    ps.setInt(3, no);

                    deleteNo = ps.executeUpdate();
                    break;

                //내용
                default:
                    sql = "UPDATE boardtable SET content = ?, updated_ts = ? WHERE no = ?";
                    ps = conn.prepareStatement(sql); //실행 객체 생성

                    StringReader srContent = new StringReader(modifiedValue);

                    ps.setCharacterStream(1, srContent);
                    ps.setTimestamp(2, boardService.ts());
                    ps.setInt(3, no);

                    deleteNo = ps.executeUpdate();
                    break;
            }

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
