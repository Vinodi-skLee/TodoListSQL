package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList l) {
		String title, category, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[새로운 할 일 목록 추가]\n"
				+ "제 목 > ");
		title = sc.next();
		if (l.isDuplicate(title)) {
			System.out.printf("제목은 중복될 수 없습니다!");
			return;
		}
		
		System.out.print("카테고리 > ");
		category = sc.next();
		sc.nextLine();
		System.out.print("내 용 > ");
		desc = sc.nextLine().trim();
		System.out.print("마감일자 > ");
		due_date= sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc, category,due_date);
		if(l.addItem(t)>0)
			System.out.println("새롭게 추가 되었습니다!");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "[기존의 할 일 목록 삭제]\n"
				+ "삭제할 목록의 번호를 입력하세요 > ");
		
		int target = sc.nextInt();
		if(l.deleteItem(target)>0) 
			System.out.println("삭제되었습니다.");
	}

	public static void updateItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[할 일 목록 수정]\n"
				+ "수정할 항목의 번호를 입력하세요 > ");
		int target = sc.nextInt();
		
		System.out.print("\n어떻게 변경하시겠습니까?"+"\n"
				+ "새 제목 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목은 중복될 수 없습니다!");
			return;
		}
		
		System.out.print("새 카테고리 > ");
		String new_category = sc.next();
		sc.nextLine();
		System.out.print("새 내용 > ");
		String new_description = sc.nextLine().trim();
		System.out.print("새 마감일자 > ");
		String new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category,new_due_date);
		t.setNum(target);
		if(l.editItem(t)>0)
			System.out.println("변경 되었습니다!");
	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void loadList(TodoList l, String todolist) throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("todolist.txt"));
			
			String str;
			while ((str = br.readLine()) !=null) {
				StringTokenizer st = new StringTokenizer(str, "##");
				String num = st.nextToken();
				String title = st.nextToken();
				String category = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				TodoItem item = new TodoItem(title, desc, category, due_date, current_date);
				l.addItem(item);
			}
			br.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public static void completeItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "완료 체크할 번호를 입력하세요 > ");
		
		int target = sc.nextInt();
		if(l.completeItem(target)>0) 
			
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
}
