package com.project.board;

import com.project.database.DBMysql;
import static com.project.board.Common.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class BoardService {
    public BoardService() {}

    DBMysql dbMysql = new DBMysql();
    Common common = new Common();

    //등록
    public void registered(String userTitle, String userContent, String userName) throws SQLException {
        common.result = dbMysql.dbCreated(userTitle, userContent, userName);

        if(common.result == 1) {
            System.out.println("\n" + userName + "님의 게시글 등록이 완료 되었습니다.");
        } else {
            System.out.println("등록 실패 했습니다.");
        }
    }

    //조회
    public void listed() throws SQLException {

        common.result = dbMysql.dbListed();

        if(common.result == 0) {
            System.out.println("조회를 실패하였습니다.");
        }
    }

    //검색
    public void searched(String type, String searchValue) throws SQLException {
        searchValue = common.validation(type, searchValue);

        switch (type) {
            //이름으로 검색
            case BOARD_NAME:
                searchValue = "%" + searchValue + "%";
                common.result = dbMysql.dbSearched(type, searchValue);

                if(common.result == 0) {
                    System.out.println("조회를 실패하였습니다.");
                }
                break;

            //제목으로 검색
            case BOARD_TITLE:
                searchValue = "%" + searchValue + "%";
                common.result = dbMysql.dbSearched(type, searchValue);

                if(common.result == 0) {
                    System.out.println("조회를 실패하였습니다.");
                }
                break;

            //내용으로 검색
            case BOARD_CONTENT:
                searchValue = "%" + searchValue + "%";
                common.result = dbMysql.dbSearched(type, searchValue);

                if(common.result == 0) {
                    System.out.println("조회를 실패하였습니다.");
                }
                break;

            default:
                break;
        }
    }

    //수정
    public void modified(int number) throws SQLException {

        common.result = dbMysql.dbUpdated(number);

        if(common.result == 0) {
            System.out.println("게시글이 없습니다.");
        } else {
            System.out.println("게시글 수정이 완료되었습니다.");
        }
    }


    //삭제
    public void deleted(int number) throws SQLException {

        common.result = dbMysql.dbDeleted(number);

        if(common.result == 1) {
            System.out.println("게시글이 삭제되었습니다.");
        } else {
            System.out.println("존재하지 않는 게시글입니다.");
        }
    }
}