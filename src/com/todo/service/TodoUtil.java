package com.todo.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList l) {
		String priority, level, title, category, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[새로운 할 일 목록 추가]\n"
				+ "우선순위 (1~4) > ");
		priority = sc.next();
		
		System.out.print("난이도 (상,중,하) > ");
		level = sc.next().trim();
		
		System.out.print("제목 > ");
		title = sc.next().trim();
		if (l.isDuplicate(title)) {
			System.out.printf("제목은 중복될 수 없습니다!");
			return;
		}
		
		System.out.print("카테고리 > ");
		category = sc.next().trim();
		sc.nextLine();
		System.out.print("내 용 > ");
		desc = sc.nextLine();
		System.out.print("마감일자 > ");
		due_date= sc.nextLine();
		
		TodoItem t = new TodoItem(priority, level, title, desc, category,due_date);
		if(l.addItem(t)>0)
			System.out.println("새롭게 추가 되었습니다!");
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "[기존의 할 일 목록 삭제]\n"
				+ "몇개의 항목을 삭제하시겠습니까? > ");
		
		int length = sc.nextInt();
		int[] ids = new int[length];
		System.out.print("삭제할 항목의 번호를 입력하세요. > ");
		for(int i=0; i<length; i++) {
			int index = sc.nextInt();
			ids[i] = index;
		}
		if(l.deleteItem(ids)>0) 
			System.out.println("삭제되었습니다.");
	}

	public static void updateItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[할 일 목록 수정]\n"
				+ "수정할 항목의 번호를 입력하세요 > ");
		int target = sc.nextInt();
		
		System.out.print("\n어떻게 변경하시겠습니까?"+"\n"
				+ "새 우선순위 (1~4) > ");
		String new_priority = sc.next().trim();
		

		System.out.print("새 난이도 (상,중,하) > ");
		String new_level = sc.next().trim();
		
		
		System.out.print("새 제목 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목은 중복될 수 없습니다!");
			return;
		}
		
		System.out.print("새 카테고리 > ");
		String new_category = sc.next().trim();
		sc.nextLine();
		System.out.print("새 내용 > ");
		String new_description = sc.nextLine();
		sc.nextLine();
		System.out.print("새 마감일자 > ");
		String new_due_date = sc.nextLine();
		
		TodoItem t = new TodoItem(new_priority, new_level, new_title, new_description, new_category,new_due_date);
		t.setNum(target);
		if(l.editItem(t)>0)
			System.out.println("변경 되었습니다!");
	}
	
	public static void updatePriority(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[할 일 목록 수정]\n"
				+ "수정할 항목의 번호를 입력하세요 > ");
		int target = sc.nextInt();
		
		System.out.print("\n어떻게 변경하시겠습니까?"+"\n"
				+ "우선순위 (1~4) > ");
		String new_priority = sc.next().trim();
		
		TodoItem t = new TodoItem(new_priority);
		t.setNum(target);
		if(l.editPriority(t)>0)
			System.out.println("변경 되었습니다!");
	}
	
	public static void findItem(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.", count);	
	}
	
	public static void findcateItem(TodoList l, String cate) {
		int count = 0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.", count);
	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listCate(TodoList l) {
		int count = 0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.", count);
	}
	
	public static void completeItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "몇개의 항목을 체크하시겠습니까? > ");
		
		int length = sc.nextInt();
		int[] ids = new int[length];
		System.out.print("체크할 항목의 번호를 입력하세요. > ");
		for(int i=0; i<length; i++) {
			int index = sc.nextInt();
			ids[i] = index;
		}
		if(l.completeItem(ids)>0)  
			System.out.println("완료 체크하였습니다.");
	}

	public static void listCompleted(TodoList l) {
		int count = 0;
		for (TodoItem item : l.getList()) {
			if(item.getIs_completed()==1) {
				System.out.println(item.toString());
				count++;
			}
		}
		System.out.printf("\n총 %d개의 항목이 완료되었습니다.", count);
	}
	
	public static void listNotComplete(TodoList l) {
		Scanner sc = new Scanner(System.in);
		int count = 0;
		for (TodoItem item : l.getList()) {
			if(item.getIs_completed()==0) {
				if(count%10==0) {
					System.out.println();
				}
				System.out.print(item.getTitle() + " ");
				count++;
			}
		} 
		System.out.printf("\n총 %d개의 항목이 남아있습니다.", count);
		System.out.print("\n\n목록을 자세히 보시겠습니까? (y/n) > ");
		String answer = sc.next();
		if(answer.equals("y")) {
			count = 0;
			for (TodoItem item : l.getList()) {
				if(item.getIs_completed()==0) {
					System.out.println(item.toString());
					count++;
				}
			}
			System.out.printf("\n[잘하고 계시네요! 조금만 더 화이팅!:)]", count);
		}
	}
	
	public static void deleteCompletedItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("\n완료된 목록을 삭제 하시겠습니까? (y/n) > ");
		String answer = sc.next();
		if(answer.equals("y")) {
			l.deleteCompletedItem(1); 
			System.out.println("삭제되었습니다!");
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void saveList(TodoList l, String todolist) {
		try {
			FileWriter fw = new FileWriter("todolist.txt");
			
			for (TodoItem item : l.getList()) {
				fw.write(item.toSaveString());
				System.out.println();
			}
			fw.close();
			System.out.println("모든 정보가 저장되었습니다 :) ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
