package com.todo.service;

import java.awt.event.ItemEvent;
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
		
		String title, category, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[새로운 할 일 목록 추가]\n"
				+ "제 목 > ");
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("제목은 중복될 수 없습니다!");
			return;
		}
		
		System.out.print("카테고리 > ");
		category = sc.nextLine();
		
		System.out.print("내 용 > ");
		desc = sc.nextLine();
		
		System.out.print("마감일자 > ");
		due_date= sc.nextLine();
		
		TodoItem t = new TodoItem(title, category, desc, due_date);
		list.addItem(t);
		System.out.println("새롭게 추가 되었습니다!");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "[기존의 할 일 목록 삭제]\n"
				+ "삭제할 목록의 번호를 입력하세요 > ");
		
		int target = sc.nextInt();
		boolean c = true;
		for (TodoItem item : l.getList()) {
			if (target == item.getNum()) {
				System.out.println(item.getNum()+". [" + item.getCategory() + "] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date());
				c = false;
				break;
			}
		}
		if(!c) {
			for (TodoItem item : l.getList()) {
				if (target == item.getNum()) {
					System.out.print("위 항목을 삭제하시겠습니까? (y/n) > ");
					String ans = sc.next();
					if(ans.equals("y")) {
						l.deleteItem(item);
						System.out.println("삭제 되었습니다!");
						int i = 1;
						for(TodoItem item2 : l.getList()) {
							item2.setNum(i++);
						}
						break;
					} else {
						System.out.println("항목을 그대로 유지합니다.");
						break;
					}
				}
			}
		}
	}


	public static void updateItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[할 일 목록 수정]\n"
				+ "수정할 항목의 번호를 입력하세요 > ");
		int target = sc.nextInt();
		boolean c = true;
		for (TodoItem item : l.getList()) {
			if (target == item.getNum()) {
				System.out.println(item.getNum()+". [" + item.getCategory() + "] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date());
				c = false;
				break;
			}
		}
		if(!c) {
			System.out.print("\n어떻게 변경하시겠습니까?"+"\n"
					+ "새 제목 > ");
			String new_title = sc.next();
			if (l.isDuplicate(new_title)) {
				System.out.println("제목은 중복될 수 없습니다!");
				return;
			}
			
			System.out.print("새 카테고리 > ");
			String new_category = sc.next();
			
			sc.nextLine();
			System.out.print("새 내용 > ");
			String new_description = sc.nextLine();
			
			System.out.print("새 마감일자 > ");
			String new_due_date = sc.nextLine();
			
			for (TodoItem item : l.getList()) {
				if (item.getNum()==target) {
					l.deleteItem(item);
					int i=1;
					for(TodoItem item2 : l.getList()) {
						item2.setNum(i++);
					}
					TodoItem t = new TodoItem(new_title, new_category, new_description, new_due_date);
					l.addItem(t);
					System.out.println("변경 되었습니다!");
					break;
				}
			}	
		}
	}

	public static void listAll(TodoList l) {
		int count = 0;
		for (TodoItem item : l.getList()) {
			count++;
		}
		System.out.println("[전체 목록, 총 "+count+"개]");
		count = 1;
		for (TodoItem item : l.getList()) {
			System.out.println(item.getNum()+". [" + item.getCategory() + "] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date());
		}
	}
	
	public static void listCate(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		ArrayList<String> e = new ArrayList<String>();
		
		int count = 0;
		
		for (TodoItem item : l.getList()) {
			if(!e.contains(item.getCategory())) {
				e.add(item.getCategory());
				count++;
			}
		}
		System.out.print(e);
		System.out.println();
		System.out.println("총 "+count+"개의 카테고리가 등록되어 있습니다.");
		
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
				
				TodoItem item = new TodoItem(title, category, desc, due_date, current_date);
				l.addItem(item);
			}
			br.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void findItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("제목과 내용 중 찾으실 키워드를 입력하세요 > ");
		String target = sc.next();
		int count = 0;
		
		for (TodoItem item : l.getList()) {
			String title = item.getTitle();
			String desc = item.getDesc();
			if(title.contains(target) || desc.contains(target)) {
				count++;
				System.out.println(item.getNum()+". [" + item.getCategory() + "] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date());
			}
		}
		System.out.println("총 "+count+"개의 항목을 찾았습니다.");
		
	}
	
public static void findcateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("카테고리 중 찾으실 키워드를 입력하세요 > ");
		String target = sc.next();
		int count = 0;
		
		for (TodoItem item : l.getList()) {
			String cate = item.getCategory();
			if(cate.contains(target)) {
				count++;
				System.out.println(item.getNum()+". [" + item.getCategory() + "] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date());
			}
		}
		System.out.println("총 "+count+"개의 항목을 찾았습니다.");
		
	}
}
