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
<img width="391" alt="스크린샷 2022-09-27 오후 7 56 20" src="https://user-images.githubusercontent.com/103010985/192507833-f5b205cb-a04f-42ab-8e4e-23a7167204bd.png">

### 4.2 file 패키지
<img width="431" alt="스크린샷 2022-09-27 오후 7 57 42" src="https://user-images.githubusercontent.com/103010985/192508072-72e2cbe8-256d-4432-9e0a-19ce538be4f4.png">

# 5.기본 기능
- 등록 registered 
- 조회 listed
- 검색 searched
- 수정 modified
- 삭제 deleted



# 6.핵심 기능

### 6.1 조회시 Pagination 기능 구현

```java
    //조회
    public void listed() {
        Scanner sc = new Scanner(System.in);
        int limit = 3;
        int offset;
        int cnt = 0;

        System.out.println("현재 페이지는 '1page'입니다.\n등록된 게시글의 수는 총 " + listedHashMap.size() + " 개 입니다. ");

        //조회 메소드 실행시 출력되는 기본 list
        for (int key : listedHashMap.keySet()) {
            listPrint(key);
            cnt++;

            if(cnt == limit) {
                break;
            }
        }

        //게시글이 4개부터 실행되는 로직
        if (listedHashMap.size() > limit) {
            System.out.println("페이지 이동은 1번, 메뉴로 이동는 2번");
            int switchNumber = sc.nextInt();

            switch (switchNumber) {
                case 1:
                    System.out.print("이동 가능한 페이지 개수는 " + listedHashMap.size() / limit + "개 입니다. \n이동하실 page를 입력하세요\n ");
                    int page = sc.nextInt();

                    offset = limit * page - limit;
                    cnt = 1;

                    System.out.println("================================");
                    System.out.println("현재 페이지는 " + page + "입니다");

                    for (int key : listedHashMap.keySet()) {
                        if(cnt > offset && cnt <= limit * page) {
                            listPrint(key);
                        }
                        cnt++;
                    }

                    //추가 페이지 이동 여부 확인
                    while (true) {
                        System.out.println("추가적인 페이지이동 1번, 취소2번");

                        if (sc.nextInt() == 1) {
                            System.out.print("이동 가능한 페이지 개수는 " + listedHashMap.size() / limit + "개 입니다. \n이동하실 page를 입력하세요\n ");
                            page = sc.nextInt();

                            offset = limit * page - limit;
                            cnt = 1;
                            System.out.println("================================");
                            System.out.println("현재 페이지는 " + page + "입니다");

                            for (int key : listedHashMap.keySet()) {
                                if(cnt > offset && cnt <= limit * page) {
                                    listPrint(key);
                                }
                                cnt++;
                            }
                        } else {
                            break;
                        }
                    }
                    break;

                default:
                    System.out.println("취소 되었습니다");
                    break;
            }
        }
    }
```

### 6.2 registered, searched, modified Method 호출시 Parameter 유효성 검증 Method
```java
    //제목 유효성 검증
    public String titleCheck (Scanner sc, String title){
        if (title.length() <= 12) {
            return title;
        } else {
            System.out.println("제목은 12글자 이하로 입력해야 합니다.\n다시 입력하세요.");
            title = sc.nextLine();
            return this.titleCheck(sc, title);
        }
    }
    
    //이름 유효성 검증
    public String nameCheck (Scanner sc, String name){
        String isKoreanCheck = "^[가-힣]*$";
        String isAlaphaCheck = "^[a-zA-Z]*$";
        if (name.matches(isKoreanCheck) || name.matches(isAlaphaCheck)) {
            return name;
        } else {
            System.out.println("올바른 형식을 입력하세요\n한글 및 영어만 입력하세요.");
            name = sc.nextLine();

            return this.nameCheck(sc, name);
        }
    }
    
    //내용 유효성 검증
    public String contentCheck (Scanner sc, String content){
        if (content.length() <= 200) {
            return content;
        } else {
            System.out.println("내용은 200자 이하로 작성할 수 있습니다.\n글자수에 맞게 다시 작성하세요");
            content = sc.nextLine();

            return this.contentCheck(sc, content);
        }
    }
```

### 6.3 json형태의 data Parsing
```java

    //등록
    public void registered(String userTitle, String userContent, String userName) {
        String createdts = ts();

        HashMap<String, String> registeredHm = new HashMap<String, String>();
        registeredHm.put("title", userTitle);
        registeredHm.put("content", userContent);
        registeredHm.put("name", userName);
        registeredHm.put("createdTs", createdts);
        registeredHm.put("updatedTs", "-");
        registeredHm.put("deletedTs", "-");

        jsonObject.put((jsonObject.size() + 1), registeredHm);
        jsonFile.jsonWriter(jsonObject.toJSONString());

        System.out.println("\n" + userName + "님의 게시글 등록이 완료 되었습니다.\n게시글 고유번호는 " + jsonObject.size() + "입니다.");
    }
    
    //json Data to Read
    public LinkedHashMap<Integer, Board> jsonReader() {
        LinkedHashMap<Integer, Board> listedHashMap = new LinkedHashMap<>();

        try(FileReader fileReader = new FileReader("./boardData.txt")) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);

            Iterator itr = jsonObject.keySet().iterator();

            int cnt = 1;

            while (itr.hasNext()) {
                HashMap<String, String> tmpJson = (HashMap<String, String>) jsonObject.get(itr.next());

                listedHashMap.put(cnt, new Board(tmpJson.get("title"), tmpJson.get("content"), tmpJson.get("name"), tmpJson.get("createdTs"), tmpJson.get("updatedTs"), tmpJson.get("deletedTs")));
                cnt++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return listedHashMap;
    }

```

# 7.회고

### 7.2 Java_Application_verson 2.1
1. 로컬 메모리에서 Data를 관리하면 컴파일 되는 시점마다 Data가 reset 되었다. File Class는 파일 생성 및 삭제 기능을 제공하고 있으며, 이를 활용하여 Data를 관리하고 싶었다. 
(.txt) file에서 Data를 관리할 때 효율적으로 관리하고자 Data 형태를 고민하였으며, 가장 적합해보이는 JSON Data Format으로 관리하게 되었다. 사용자가 등록한 게시글의 Data는 직렬화(serialize 영어로적을지 한글로 할지 택1)하여 File에 저장되도록 하였으며, List를 불러올 땐 직렬화된 데이터를 Parsing하여 HashMap 자료구조에 담아 출력하도록 구현하였다. 




