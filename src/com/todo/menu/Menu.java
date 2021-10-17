package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println("\n < ToDoList 명령어 사용법 > \n");
        System.out.println("[add] 새로운 할 일 목록 추가");
        System.out.println("[del] 기존의 할 일 목록 삭제");
        System.out.println("[edit] 할 일 목록 변경");
        System.out.println("[edit_pr] 우선순위 변경"); //추가
        System.out.println("[ls] 전체 할 일 목록");
        System.out.println("[comp] 할 일 완료 체크");
        System.out.println("[del_comp] 완료된 할 일 전체삭제"); //추가
        System.out.println("[find] 키워드 검색");
        System.out.println("[find_cate] 카테고리 키워드 검색");
        System.out.println("[ls_cate] 카테고리 목록");
        System.out.println("[ls_comp] 완료된 목록");
        System.out.println("[ls_notcomp] 완료되지 않은 목록"); //추가
        System.out.println("[ls_name] 제목순 정렬");
        System.out.println("[ls_name_desc] 제목 역순 정렬");
        System.out.println("[ls_date] 날짜순 정렬");
        System.out.println("[ls_date_desc] 날짜 역순 정렬");
        System.out.println("[ls_pr] 우선순위 순서 정렬"); //추가
        System.out.println("[ls_pr_desc] 우선순위 역순 정렬"); //추가
        System.out.println("[exit] 끝내기");
        
    }
    public static void prompt() {
    	System.out.print("\nCommand > ");
    }
}
