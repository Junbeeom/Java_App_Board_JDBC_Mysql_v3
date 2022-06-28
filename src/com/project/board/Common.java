package com.project.board;

import java.util.HashMap;
import java.util.Scanner;

public class Common {
    public static int result = 0;
    public static final String BOARD_NAME = "name";
    public static final String BOARD_TITLE = "title";
    public static final String BOARD_CONTENT = "content";

    public static final HashMap<Integer, String> typeHash = new HashMap<>();
    static {
        typeHash.put(1, BOARD_NAME);
        typeHash.put(2, BOARD_TITLE);
        typeHash.put(3, BOARD_CONTENT);
    }

    public String validation(String type, String value) {
        Scanner sc = new Scanner(System.in);

        switch (type) {
            case BOARD_NAME:
                //이름 유효성 체크
                String isKoreanCheck = "^[가-힣]*$";
                String isAlaphaCheck = "^[a-zA-Z]*$";

                if (value.matches(isKoreanCheck) || value.matches(isAlaphaCheck)) {
                    return value;
                } else {
                    System.out.println("올바른 형식을 입력하세요\n한글 및 영어만 입력하세요.");
                    value = sc.nextLine();
                    return this.validation(type, value);
                }

            case BOARD_TITLE:
                //제목 유효성 체크
                if (value.length() <= 12) {
                    return value;
                } else {
                    System.out.println("제목은 12글자 이하로 입력해야 합니다.\n다시 입력하세요.");
                    value = sc.nextLine();
                    return this.validation(type, value);
                }

            case BOARD_CONTENT:
                //내용 유효성 체크
                if (value.length() <= 200) {
                    return value;
                } else {
                    System.out.println("내용은 200자 이하로 작성할 수 있습니다.\n글자수에 맞게 다시 작성하세요");
                    value = sc.nextLine();
                    return this.validation(type, value);
                }

            default:
                break;
        }
        return value;
    }
}
