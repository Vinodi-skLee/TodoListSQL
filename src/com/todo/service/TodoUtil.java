package com.todo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[새로운 할 일 목록 추가]\n"
				+ "제 목 > ");
		
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("제목은 중복될 수 없습니다!");
			return;
		}
		
		System.out.print("내 용 > ");
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "[기존의 할 일 목록 삭제]\n"
				+ "제 목 > ");
		
		String title = sc.nextLine();
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("삭제 되었습니다!");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[할 일 목록 수정]\n"
				+ "어떤 항목을 변경하시겠습니까?\n"
				+ "제 목 > ");
		String title = sc.nextLine().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("일치하는 항목이 없습니다!");
			return;
		}

		System.out.print("어떻게 변경하시겠습니까?"+"\n"
				+ "제 목 > ");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목은 중복될 수 없습니다!\n");
			return;
		}
		
		System.out.print("내 용 > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("변경되었습니다!");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[할 일 목록]");
		for (TodoItem item : l.getList()) {
			System.out.println("[" + item.getTitle() + "] " + item.getDesc() + " - " + item.getCurrent_date());
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
				String title = st.nextToken();
				String desc = st.nextToken();
				String current_date = st.nextToken();
				
				TodoItem item = new TodoItem(title, desc, current_date);
				l.addItem(item);
			}
			br.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
