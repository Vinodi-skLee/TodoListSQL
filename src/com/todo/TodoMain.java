package com.todo;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
		
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		
		File f = new File("todolist.txt");
		if(f.isFile() != true) {
			System.out.println("todolist.txt 파일이 없습니다.");
		} else {
			try {
				TodoUtil.loadList(l,"todolist.txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("todolist.txt 파일 로딩 완료");
		}
		
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				System.out.println("제목 순서대로 정렬 되었습니다!");
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				System.out.println("제목 역순으로 정렬 되었습니다!");
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				System.out.println("날짜 순서대로 정렬 되었습니다!");
				isList = true;
				break;

			case "exit":
				TodoUtil.saveList(l,"todolist.txt");
				quit = true;
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			default:
				System.out.println("정확한 명력어를 입력하세요! [도움말 - help]");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
	}
}
