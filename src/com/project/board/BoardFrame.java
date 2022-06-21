package com.project.board;

import java.util.Scanner;

public class BoardFrame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BoardService boardService = new BoardService();

        boolean flag = true;

        while (flag) {
            System.out.println("=================");
            System.out.println("1.게시글 작성하기");
            System.out.println("2.게시글 삭제하기");
            System.out.println("3.게시글 검색하기");
            System.out.println("4.게시글 수정하기");
            System.out.println("5.게시글 목록보기");
            System.out.println("6.    종료");
            System.out.println("=================");

            switch (sc.nextInt()) {
                //등록하기
                case 1:
                    System.out.println("작성자 이름을 입력하세요 : ");
                    sc.nextLine();
                    String userName = sc.nextLine() ;
                    userName = boardService.nameCheck(sc, userName);


                    System.out.println("제목을 입력하세요 : ");
                    String userTitle = sc.nextLine();
                    userTitle = boardService.titleCheck(sc, userTitle);

                    System.out.println("내용을 입력하세요. 줄바꿈은 \\n을 입력하세요");
                    String userContent = sc.nextLine();
                    userContent = boardService.contentCheck(sc, userContent);

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
                    System.out.println("작성자로 검색 1번\n제목으로 검색 2번\n내용으로 검색 3번\n취소 아무키 입력");
                    int choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println("등록된 작성자의 이름을 입력하세요");
                            userName = sc.next();
                            boardService.nameCheck(sc, userName);
                            boardService.searched(userName, choice);
                            break;
                        case 2:
                            System.out.println("등록된 게시글의 제목을 입력하세요");
                            userTitle = sc.next();
                            boardService.contentCheck(sc, userTitle);
                            boardService.searched(userTitle, choice);
                            break;
                        case 3:
                            System.out.println("등록된 게시글의 내용을 입력하세요");
                            userContent = sc.next();
                            boardService.nameCheck(sc, userContent);
                            boardService.searched(userContent, choice);
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