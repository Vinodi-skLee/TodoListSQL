package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		l.importData("todolist.txt",l);
		boolean quit = false;
		
		Menu.displaymenu();
		do {
			Menu.prompt();
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
				
			case "edit_pr":
				TodoUtil.updatePriority(l);
				break;
				
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findItem(l, keyword);
				break;
			
			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.findcateItem(l, cate);
				break;
			
			case "ls":
				TodoUtil.listAll(l);
				break;
				
			case "ls_cate":
				TodoUtil.listCate(l);
				break;
			
			case "comp":
				TodoUtil.completeItem(l);
				break;
				
			case "ls_comp":
				TodoUtil.listCompleted(l);
				break;
			
			case "ls_notcomp":
				TodoUtil.listNotComplete(l);
				break;
				
			case "del_comp":
				TodoUtil.deleteCompletedItem(l);
				break;
				
			case "ls_name":
				System.out.println("제목 순서대로 정렬 되었습니다!");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("제목 역순으로 정렬 되었습니다!");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("날짜 순서대로 정렬 되었습니다!");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("날짜 역순으로 정렬 되었습니다!");
				TodoUtil.listAll(l, "due_date", 0);
				break;
				
			case "ls_pr":
				System.out.println("우선순위 순서대로 정렬 되었습니다!");
				TodoUtil.listAll(l, "priority", 1);
				break;
				
			case "ls_pr_desc":
				System.out.println("우선순위 역순으로 정렬 되었습니다!");
				TodoUtil.listAll(l, "priority", 0);
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
		} while (!quit);
	}
}
