package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println(" < ToDoList 명령어 사용법 > ");
        System.out.println();
        System.out.println("[add] 새로운 할 일 목록 추가");
        System.out.println("[del] 기존의 할 일 목록 삭제");
        System.out.println("[edit] 할 일 목록 수정");
        System.out.println("[ls] 전체 할 일 목록 보기");
        System.out.println("[ls_name_asc] 제목 순서대로 정리하여 보기");
        System.out.println("[ls_name_desc] 제목 역순으로 정리하여 보기");
        System.out.println("[ls_date] 날짜 순서대로 정리하여 보기");
        System.out.println("[exit] 끝내기");
        
    }
    public static void prompt() {
    	System.out.println();
    	System.out.print("명령 입력 > ");
    }
}
