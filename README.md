# Java_App_Board_JDBC_Mysql_v3

# 1.프로젝트 개요
### 1.1 프로젝트 목적
- MySQL DBMS를 활용한 Java 게시판 Application 개발

### 1.2 목표 및 의의

#### 1.2.1 Java_Application_Board_v3
- JDBC Connector Mysql로 DB연결하기



# 2. 개발 환경
- IntelliJ IDEA(Ultimate Edition), gitHurb


# 3. 사용기술
- Java 11


# 4.프로젝트 설계

### 4.1 board 패키지
<img width="385" alt="스크린샷 2022-09-28 오후 1 48 23" src="https://user-images.githubusercontent.com/103010985/192689931-e5a04c63-cdd8-475b-ab93-f4e437d0def6.png">

### 4.2 database 패키지
<img width="342" alt="스크린샷 2022-09-28 오후 1 48 56" src="https://user-images.githubusercontent.com/103010985/192689996-ad96eb78-a5c1-486d-a5a1-0520ea7f2105.png">


# 5.기본 기능
- 등록 registered 
- 조회 listed
- 검색 searched
- 수정 modified
- 삭제 deleted



# 6.핵심 기능

### 6.1 Common Class 구현

```java
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
```

### 6.2 BoardService
```java

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

    

```

# 7.회고

### Java_App_Board_JDBC_Mysql_v3

1. 공통적으로 사용하는 메소드 뿐만 아니라 변수들도 중복을 없애고 직관성과 유지보수의 용이성을 높이기 위해 Common Class를 생성하게 되었다. 값이 재할당 되는 것은 변수로, 값이 불변한 것은 상수로 선언 하였고, 중복되는 변수와 상수, 로직들을 정리하니
전체적인 코드가 깔끔하고 직관적으로 보이게 되었다. 

2. Mysql connector Library를 추가하고 Connection하기까지 크게 어려움은 없었다. DB에서 Query가 실행되고 나서 사용자가 원하는 서비스가 정상적으로 실행 됐는지에 대한 Message를 던져주고 싶었다. 
DBmysql Class의 query 실행후 결과 값을 반환하는 executeUpdate(), executeQuery()을 활용하여 service단에서 값을 return 받아 if문을 활용하여 서비스 실행 후 결과를 알려주었다.

 




