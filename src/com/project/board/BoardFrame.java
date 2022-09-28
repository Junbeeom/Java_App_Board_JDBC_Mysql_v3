package com.project.board;
import static com.project.board.Common.*;

import java.sql.SQLException;
import java.util.Scanner;

public class BoardFrame {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        Common common = new Common();
        BoardService boardService = new BoardService();

        boolean flag = true;

        while(flag) {
            System.out.println("=================");
            System.out.println("1.게시글 작성하기");
            System.out.println("2.게시글 삭제하기");
            System.out.println("3.게시글 검색하기");
            System.out.println("4.게시글 수정하기");
            System.out.println("5.게시글 목록보기");
            System.out.println("6.    종료");
            System.out.println("=================");

            switch(sc.nextInt()) {
                //등록하기
                case 1:
                    System.out.println("작성자 이름을 입력하세요 : ");
                    //버퍼 비우기
                    sc.nextLine();

                    String userName = sc.nextLine() ;
                    userName = common.validation(BOARD_NAME, userName);

                    System.out.println("제목을 입력하세요 : ");
                    String userTitle = sc.nextLine();

                    userTitle = common.validation(BOARD_TITLE, userTitle);

                    System.out.println("내용을 입력하세요. 줄바꿈은 \\n을 입력하세요");
                    String userContent = sc.nextLine();

                    userContent = common.validation(BOARD_CONTENT, userContent);

                    boardService.registered(userTitle, userContent, userName);
                    break;

                //삭제하기
                case 2:
                    System.out.println("게시글의 고유번호를 입력하세요. ");
                    int number = sc.nextInt();

                    boardService.deleted(number);
                    break;

                //검색하기
                case 3:
                    System.out.println("작성자로 검색 1번\n제목으로 검색 2번\n내용으로 검색 3번\n취소 4번 입력");

                    number = sc.nextInt();
                    switch(typeHash.get(number)) {
                        case BOARD_NAME:
                            System.out.println("등록된 작성자의 이름을 입력하세요");
                            userName = sc.next();
                            userName = common.validation(BOARD_NAME, userName);

                            boardService.searched(BOARD_NAME, userName);
                            break;

                        case BOARD_TITLE:
                            System.out.println("등록된 게시글의 제목을 입력하세요");
                            userTitle = sc.next();
                            userTitle = common.validation(BOARD_TITLE, userTitle);
                            boardService.searched(BOARD_TITLE, userTitle);
                            break;

                        case BOARD_CONTENT:
                            System.out.println("등록된 게시글의 내용을 입력하세요");
                            userContent = sc.next();
                            userContent = common.validation(BOARD_CONTENT, userContent);
                            boardService.searched(BOARD_CONTENT, userContent);
                            break;

                        default:
                            break;
                    }
                    break;

                //수정하기
                case 4:
                    System.out.println("게시글의 고유번호를 입력하세요. ");
                    number = sc.nextInt();
                    boardService.modified(number);
                    break;

                //조회하기
                case 5:
                    boardService.listed();
                    break;

                default:
                    flag = false;
                    break;
            }
        }
    }
}