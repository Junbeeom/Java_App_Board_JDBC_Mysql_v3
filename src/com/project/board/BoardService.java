package com.project.board;

import com.project.file.JsonFile;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;


public class BoardService {
    public BoardService() {}

    JsonFile jsonFile = new JsonFile();
    JSONObject jsonObject = new JSONObject();

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

    //조회
    public void listed() {
        LinkedHashMap<Integer, Board> listedHashMap = jsonFile.jsonReader();

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

    //검색
    public void searched(String searchValue, int searchIndex) {
        LinkedHashMap<Integer, Board> listedHashMap = jsonFile.jsonReader();

        boolean flag = false;
        switch (searchIndex) {

            //이름으로 검색
            case 1:
                for (int key : listedHashMap.keySet()) {
                    if (listedHashMap.get(key).getName().contains(searchValue)) {
                        listPrint(key);
                        flag = true;
                    }
                }
                if (!flag) {
                    System.out.println("등록된 작성자가 없습니다.");
                }
                break;

            //제목으로 검색
            case 2:
                for (int key : listedHashMap.keySet()) {
                    if (listedHashMap.get(key).getTitle().contains(searchValue)) {
                        listPrint(key);
                        flag = true;
                    }
                }
                if (!flag) {
                    System.out.println("등록된 제목이 없습니다.");
                }
                break;

            //내용으로 검색
            case 3:
                for (int key : listedHashMap.keySet()) {
                    if (listedHashMap.get(key).getContent().contains(searchValue)) {
                        listPrint(key);
                        flag = true;
                    }
                }
                if (!flag) {
                    System.out.println("등록된 내용이 없습니다.");
                }
                break;
            default:
                break;
        }
    }

    //수정
    public void modified(int number) {
        LinkedHashMap<Integer, Board> listedHashMap = jsonFile.jsonReader();
        Scanner sc = new Scanner(System.in);

        if (listedHashMap.get(number) == null) {
            System.out.println("존재하지 않는 게시글입니다");
        } else {
            System.out.println("1.이름 수정\n2.제목 수정\n3.내용 수정\n4.취소는 아무키 입력");

            int modifiedIndex = sc.nextInt();
            switch (modifiedIndex) {

                //이름 수정
                case 1:
                    System.out.println("수정하실 이름을 입력하세요");
                    sc.nextLine();
                    String newName = sc.nextLine();

                    newName = nameCheck(sc, newName);

                    String updateTs = ts();

                    HashMap<String, String> hmName = new HashMap<String, String>();
                    hmName.put("title", listedHashMap.get(number).getTitle());
                    hmName.put("content", listedHashMap.get(number).getContent());
                    hmName.put("name",  newName);
                    hmName.put("createdTs", listedHashMap.get(number).getCreatedTs());
                    hmName.put("updatedTs", updateTs);
                    hmName.put("deletedTs", "-");

                    jsonObject.put(number, hmName);

                    jsonFile.jsonWriter(jsonObject.toJSONString());
                    listedHashMap = jsonFile.jsonReader();

                    System.out.println(listedHashMap.get(number).getName() + "님의 게시글이 수정되었습니다.\n수정일시 : "  + updateTs);
                    break;

                //제목 수정
                case 2:
                    System.out.println("수정하실 제목을 입력하세요");
                    sc.nextLine();
                    String newTitle = sc.nextLine();

                    newTitle = titleCheck(sc, newTitle);
                    listedHashMap.get(number).setTitle(newTitle);

                    updateTs = ts();

                    HashMap<String, String> hmTitle = new HashMap<String, String>();
                    hmTitle.put("title", newTitle);
                    hmTitle.put("content", listedHashMap.get(number).getContent());
                    hmTitle.put("name",  listedHashMap.get(number).getName());
                    hmTitle.put("createdTs", listedHashMap.get(number).getCreatedTs());
                    hmTitle.put("updatedTs", updateTs);
                    hmTitle.put("deletedTs", "-");

                    jsonObject.put(number, hmTitle);

                    jsonFile.jsonWriter(jsonObject.toJSONString());
                    listedHashMap = jsonFile.jsonReader();

                    System.out.println(listedHashMap.get(number).getName() + "님의 게시글이 수정되었습니다.\n수정일시 : "  + updateTs);
                    break;

                //내용 수정
                case 3:
                    System.out.println("수정하실 내용을 입력하세요");
                    sc.nextLine();
                    String newContent = sc.nextLine();

                    newContent = contentCheck(sc, newContent);

                    updateTs = ts();

                    HashMap<String, String> hmContent = new HashMap<String, String>();
                    hmContent.put("title", listedHashMap.get(number).getName());
                    hmContent.put("content", newContent);
                    hmContent.put("name",  listedHashMap.get(number).getName());
                    hmContent.put("createdTs", listedHashMap.get(number).getCreatedTs());
                    hmContent.put("updatedTs", updateTs);
                    hmContent.put("deletedTs", "-");

                    jsonObject.put(number, hmContent);

                    jsonFile.jsonWriter(jsonObject.toJSONString());
                    listedHashMap = jsonFile.jsonReader();

                    System.out.println(listedHashMap.get(number).getName() + "님의 게시글이 수정되었습니다.\n수정일시 : "  + updateTs);
                    break;
                default:
                    System.out.println("취소 되었습니다");
                    break;
            }
        }
    }

    //삭제
    public void deleted(int number) {
        LinkedHashMap<Integer, Board> listedHashMap = jsonFile.jsonReader();
        Scanner sc = new Scanner(System.in);

        if (listedHashMap.get(number) == null) {
            System.out.println("존재하지 않는 게시글입니다");
        } else {
            System.out.println("삭제 1번, 취소 2번");

            int deletedIndex = sc.nextInt();
            switch (deletedIndex) {
                case 1:
                    String deleteTs = ts();

                    HashMap<String, String> registeredHm = new HashMap<String, String>();
                    registeredHm.put("title", listedHashMap.get(number).getTitle());
                    registeredHm.put("content", listedHashMap.get(number).getContent());
                    registeredHm.put("name", listedHashMap.get(number).getName());
                    registeredHm.put("createdTs",  listedHashMap.get(number).getCreatedTs());
                    registeredHm.put("updatedTs", listedHashMap.get(number).getUpdatedTs());
                    registeredHm.put("deletedTs", deleteTs);

                    JSONObject deleteData = new JSONObject();
                    deleteData.put(number, registeredHm);
                    jsonFile.deleteJsonWriter(deleteData.toJSONString());

                    jsonObject.remove(number);
                    jsonFile.jsonWriter(jsonObject.toJSONString());

                    System.out.println(listedHashMap.get(number).getName() + "님의 게시글이 삭제되었습니다.\n삭제일시 : "  + deleteTs);

                    listedHashMap.remove(number);
                    break;

                default:
                    System.out.println("취소 되었습니다");
                    break;
            }
        }
    }

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

    //시간 등록을 위한 메소드
    public String ts() {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date time = new Date();
        String ts = userFormat.format(time);

        return ts;
    }

    //게시글 내용 출력
    public void listPrint(int key) {
        LinkedHashMap<Integer, Board> listedHashMap = jsonFile.jsonReader();
        System.out.println("고유번호 : " + key);
        System.out.println("작 성 자 : " + listedHashMap.get(key).getName());
        System.out.println("제    목 : " + listedHashMap.get(key).getTitle());

        System.out.println("내    용 : ");
        StringTokenizer stk = new StringTokenizer(listedHashMap.get(key).getContent(), "\\n");
        while (stk.hasMoreTokens()) {
            System.out.println(stk.nextToken());
        }

        System.out.println("등록일시 : " + listedHashMap.get(key).getCreatedTs());
        System.out.println("수정일시 : " + listedHashMap.get(key).getUpdatedTs());
        System.out.println("==============================");
    }
}