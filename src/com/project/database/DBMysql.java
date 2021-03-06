package com.project.database;

import com.project.board.BoardService;
import com.project.board.Common;

import java.sql.*;
import java.io.StringReader;
import java.util.Scanner;

import static com.project.board.Common.*;


public class DBMysql {
    Common common = new Common();
    public DBMysql() {
    }

    private String url = "jdbc:mysql://localhost:3306/board";
    private String user = "root";
    private String password = "26905031";
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;


    //created method to INSERT DB
    public int dbCreated(String title, String content, String name) throws SQLException {
        common.result = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);

            String sql = "INSERT INTO boardtable(title, content, name) VALUES(?, ?, ?)";

            pstmt = conn.prepareStatement(sql);

            StringReader srTitle = new StringReader(title);
            StringReader srName = new StringReader(name);

            pstmt.setCharacterStream(1, srTitle, 20);
            pstmt.setString(2, content);
            pstmt.setCharacterStream(3, srName, 12);

            common.result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return common.result;
    }

    public int dbListed() throws SQLException {
        common.result = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT * FROM boardtable where is_deleted IS FALSE";

            pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            resultSet = pstmt.executeQuery();

            common.result = listPrint(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return common.result;
    }


    //delted method to DELETE DB
    public int dbDeleted(int no) throws SQLException {
        int result = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);

            String sql = "UPDATE boardtable SET deleted_ts = CURRENT_TIMESTAMP(), is_deleted = TRUE WHERE no = ?";

            pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            pstmt.setInt(1, no);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (conn != null) {
                conn.close();
            }

        }
        return result;
    }

    //updated method to UPDATE DB
    public int dbUpdated(int no) throws SQLException {
        Scanner sc = new Scanner(System.in);
        BoardService boardService = new BoardService();
        common.result = 1;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT * FROM boardtable WHERE no = ? AND is_deleted IS FALSE";
            pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            pstmt.setInt(1, no);
            resultSet = pstmt.executeQuery();

            if(!resultSet.next()) {
                common.result = 0;
                return  common.result;
            }
            System.out.println("????????? ?????? 1???\n?????? ?????? 2???\n?????? ?????? 3???\n?????? 4??? ??????");
            int modifiedIndex = sc.nextInt();

            switch (common.typeHash.get(modifiedIndex)) {
                //??????
                case BOARD_NAME:
                    System.out.println("???????????? ????????? ???????????????");
                    String modifiedValue = sc.next();
                    modifiedValue = common.validation(BOARD_NAME, modifiedValue);

                    sql = "UPDATE boardtable SET name = ?, updated_ts = CURRENT_TIMESTAMP() WHERE no = ?";
                    pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    StringReader srName = new StringReader(modifiedValue);

                    pstmt.setCharacterStream(1, srName);
                    pstmt.setInt(2, no);

                    common.result = pstmt.executeUpdate();
                    break;

                //??????
                case BOARD_TITLE:
                    System.out.println("???????????? ????????? ???????????????");
                    modifiedValue = sc.next();

                    modifiedValue = common.validation(BOARD_TITLE, modifiedValue);
                    sql = "UPDATE boardtable SET title = ?,  updated_ts = CURRENT_TIMESTAMP() WHERE no = ?";
                    pstmt = conn.prepareStatement(sql);

                    StringReader srTitle = new StringReader(modifiedValue);

                    pstmt.setCharacterStream(1, srTitle);
                    pstmt.setInt(2, no);

                    common.result = pstmt.executeUpdate();
                    break;

                //??????
                case BOARD_CONTENT:
                    sc.nextLine();
                    System.out.println("???????????? ????????? ???????????????");
                    modifiedValue = sc.nextLine();
                    modifiedValue = common.validation(BOARD_CONTENT, modifiedValue);

                    sql = "UPDATE boardtable SET content = ?,  updated_ts = CURRENT_TIMESTAMP() WHERE no = ?";

                    pstmt = conn.prepareStatement(sql);

                    StringReader srContent = new StringReader(modifiedValue);

                    pstmt.setCharacterStream(1, srContent);
                    pstmt.setInt(2, no);

                    common.result = pstmt.executeUpdate();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return common.result;
    }

    //search method to SELECT DB
    public int dbSearched(String type, String searchedValue) throws SQLException {
        common.result = 1;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);

            switch (type) {
                //?????? ??????
                case BOARD_NAME:
                    String sql = "SELECT * FROM boardtable WHERE name LIKE ? AND is_deleted IS FALSE";
                    pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    StringReader searchName = new StringReader(searchedValue);
                    pstmt.setCharacterStream(1, searchName);

                    resultSet = pstmt.executeQuery();

                    common.result= listPrint(resultSet);
                    break;

                //?????? ??????
                case BOARD_TITLE:
                    sql = "SELECT * FROM boardtable WHERE title LIKE ? AND is_deleted IS FALSE";
                    pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    StringReader searchTitle = new StringReader(searchedValue);
                    pstmt.setCharacterStream(1, searchTitle);

                    resultSet = pstmt.executeQuery();

                    common.result = listPrint(resultSet);
                    break;

                //?????? ??????
                case BOARD_CONTENT:
                    sql = "SELECT * FROM boardtable WHERE content LIKE ? AND is_deleted IS FALSE";
                    pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    StringReader searchContent = new StringReader(searchedValue);
                    pstmt.setCharacterStream(1, searchContent);

                    resultSet = pstmt.executeQuery();

                    common.result = listPrint(resultSet);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return common.result;
    }
    //print method to SELECT FROM BOARDTABLE
    public int listPrint(ResultSet resultSet) throws SQLException {
        common.result = 1;

        if(!resultSet.next()) {
            System.out.println("???????????? ????????????.");
        } else {
            resultSet.beforeFirst();

            while (resultSet.next()) {
                System.out.println("???????????? : " + resultSet.getInt("no"));
                System.out.println("??? ??? ??? : " + resultSet.getString("name"));
                System.out.println("???    ??? : " + resultSet.getString("title"));
                System.out.println("???    ??? : " + resultSet.getString("content"));
                System.out.println("???????????? : " + resultSet.getString("created_ts"));
                System.out.println("???????????? : " + resultSet.getString("updated_ts"));
                System.out.println("===============================================");
            }
        }

           if(resultSet.getMetaData().getColumnCount() == 0) {
               common.result = 0;
           }

        return common.result;
    }
}
